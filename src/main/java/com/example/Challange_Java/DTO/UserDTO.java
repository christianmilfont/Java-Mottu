package com.example.Challange_Java.DTO;

import com.example.Challange_Java.Entities.User;
import com.example.Challange_Java.Entities.User.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    private Long id;

    private String username;

    //private String email;
    
    private String password;


    private User.Role role;
}
