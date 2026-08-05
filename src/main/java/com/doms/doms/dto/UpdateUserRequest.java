package com.doms.doms.dto;

import com.doms.doms.entity.Role;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private String fullName;
    private String email;
    private Role role;
    private boolean enabled;
}