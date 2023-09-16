package com.backend.Application.requests;

import com.backend.Application.model.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String email;

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private Date userRegisterDate;

    private Roles roles;
}
