package com.backend.Application.model;

import com.backend.Application.model.enums.Roles;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @Pattern(regexp = "[A-Z]", message = "Password should contain at least one uppercase letter.")
    @Pattern(regexp = "[a-z]", message = "Password should contain at least one lowercase letter.")
    @Pattern(regexp = "[0-9]", message = "Password should contain at least one numeric digit.")
    @Pattern(regexp = "[!@#$%^&*()-+=]", message = "Password should contain at least one special character.")
    private String password;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+com$", message = "Please enter a valid email-id. email-id should contain '@' and '.com'")
    private String email;

    @NotNull
    @Size(min = 3, max = 20, message = "first name length should be between 3-20.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Customer name should not contain special characters or numbers.")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20, message = "last name length should be between 3-20.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Customer name should not contain special characters or numbers.")
    private String lastName;

    @Size(min = 10, max = 10, message = "Phone number should be 10 digits.")
    @Column(unique = true)
    @Pattern(regexp = "\\d{10}", message = "Phone number must consist of digits only.")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must start with 6, 7, 8, or 9 and must consist of 10 digits.")
    private Long phoneNumber;

    @NotNull
    private Date userRegisterDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles roles;

    @JsonManagedReference
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    public void saveCreationDate() {
        userRegisterDate = new Date();
    }
}
