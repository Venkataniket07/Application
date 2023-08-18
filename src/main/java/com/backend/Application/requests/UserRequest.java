package com.backend.Application.requests;

import com.backend.Application.model.Address;
import com.backend.Application.model.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private Date userRegisterDate;

    private Roles roles;

    private List<Address> addresses;
}
