
package com.doms.doms.dto;

import com.doms.doms.entity.Role;
import lombok.Data;

@Data
public class CreateUserRequest {

    private String fullName;

    private String email;

    private String password;

    private Role role;
}