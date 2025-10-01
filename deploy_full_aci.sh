#!/bin/bash

set -e

# ================= CONFIGS =================
APP_NAME="mottu"
RESOURCE_GROUP="rg-${APP_NAME}"
LOCATION="eastus"
ACI_APP_NAME="${APP_NAME}-app"
ACI_DB_NAME="${APP_NAME}-db"
SUFFIX=$(cat /dev/urandom | tr -dc 'a-z0-9' | fold -w6 | head -n1)
ACR_NAME="${APP_NAME}acr${SUFFIX}"
IMAGE_NAME="${ACR_NAME}.azurecr.io/${APP_NAME}:latest"
VNET_NAME="${APP_NAME}-vnet"
SUBNET_NAME="${APP_NAME}-subnet"
PORT=8080
MYSQL_PORT=3306
MYSQL_ROOT_PASSWORD="senha123"
MYSQL_DATABASE="mottu_challenge"

# ================= LOGIN =================
echo "🔐 Verificando login no Azure..."
az account show &>/dev/null || az login

# ================= RESOURCE GROUP =================
echo "📁 Criando resource group..."
az group create --name $RESOURCE_GROUP --location $LOCATION

# ================= VNET & SUBNET =================
echo "🌐 Criando VNET e subnet..."
az network vnet create \
  --resource-group $RESOURCE_GROUP \
  --name $VNET_NAME \
  --address-prefixes 10.0.0.0/16 \
  --subnet-name $SUBNET_NAME \
  --subnet-prefix 10.0.0.0/24

SUBNET_ID=$(az network vnet subnet show \
  --resource-group $RESOURCE_GROUP \
  --vnet-name $VNET_NAME \
  --name $SUBNET_NAME \
  --query id -o tsv)

# ================= ACR =================
echo "📦 Criando ACR..."
az acr create --resource-group $RESOURCE_GROUP --name $ACR_NAME --sku Basic --admin-enabled true
az acr login --name $ACR_NAME

# ================= BUILD E PUSH DO APP =================
echo "🐳 Buildando imagem do app..."
docker build -t $IMAGE_NAME .
docker push $IMAGE_NAME

# ================= MYSQL (ACI) =================
echo "🛢️ Criando MySQL container..."
az container create \
  --resource-group $RESOURCE_GROUP \
  --name $ACI_DB_NAME \
  --image mysql:8.0 \
  --cpu 1 --memory 1.5 \
  --vnet $VNET_NAME \
  --subnet $SUBNET_NAME \
  --ports $MYSQL_PORT \
  --environment-variables \
      MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD \
      MYSQL_DATABASE=$MYSQL_DATABASE \
  --restart-policy Always

echo "⏳ Aguardando MySQL inicializar (30s)..."
sleep 30

# ================= CREDENCIAIS DO ACR =================
ACR_USERNAME=$(az acr credential show -n $ACR_NAME --query username -o tsv)
ACR_PASSWORD=$(az acr credential show -n $ACR_NAME --query passwords[0].value -o tsv)

# ================= APLICACAO JAVA =================
echo "🚀 Criando container da aplicação..."
az container create \
  --resource-group $RESOURCE_GROUP \
  --name $ACI_APP_NAME \
  --image $IMAGE_NAME \
  --cpu 1 --memory 1.5 \
  --vnet $VNET_NAME \
  --subnet $SUBNET_NAME \
  --ports $PORT \
  --ip-address public \
  --registry-login-server "${ACR_NAME}.azurecr.io" \
  --registry-username $ACR_USERNAME \
  --registry-password $ACR_PASSWORD \
  --environment-variables \
      SPRING_PROFILES_ACTIVE=docker \
      MYSQL_HOST=$ACI_DB_NAME \
      MYSQL_PORT=$MYSQL_PORT \
      MYSQL_DB=$MYSQL_DATABASE \
      MYSQL_USER=root \
      MYSQL_PASSWORD=$MYSQL_ROOT_PASSWORD

echo "⏳ Aguardando aplicação subir (30s)..."
sleep 30

# ================= RESULTADOS =================
APP_IP=$(az container show --resource-group $RESOURCE_GROUP --name $ACI_APP_NAME --query ipAddress.ip -o tsv)
echo "✅ A aplicação está publicada em:"
echo "🌐 http://$APP_IP:$PORT"

echo ""
echo "📄 Logs da aplicação:"
az container logs --resource-group $RESOURCE_GROUP --name $ACI_APP_NAME
