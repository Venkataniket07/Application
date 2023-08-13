package com.backend.Application.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int customerId;

    @NotNull
    @Size(min = 3, max = 20, message = "customer name length should be between 3-20.")
    private String customerName;

    @Size(min = 10, max = 10, message = "Phone number should be 10 digits.")
    @Column(unique = true)
    private Long phoneNumber;

    @Column(nullable = false)
    private Date customerRegisterDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_credentials_id", referencedColumnName = "id")
    private UserCredentials userCredentials;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", customerRegisterDate=" + customerRegisterDate +
                ", userCredentials=" + userCredentials +
                '}';
    }

    @PrePersist
    public void saveCreationDate() {
        customerRegisterDate = new Date();
    }
}
