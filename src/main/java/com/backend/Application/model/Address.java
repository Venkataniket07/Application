package com.backend.Application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressId")
    private int addressId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Column(name = "street")
    @NotNull(message = "street name cannot be null.")
    @Size(min = 3, max = 20, message = "street name length should be between 3-20.")
    private String streetName;

    @Column(name = "city")
    @NotNull(message = "city name cannot be null.")
    @Size(min = 3, max = 20, message = "city name length should be between 3-20.")
    private String cityName;

    @Column(name = "district")
    @NotNull(message = "district name cannot be null.")
    @Size(min = 3, max = 20, message = "district name length should be between 3-20.")
    private String distName;

    @Column(name = "state")
    @NotNull(message = "state name can't be null.")
    @Size(min = 3, max = 20, message = "state name length should be between 3-20.")
    private String stateName;

    @Size(min = 6, max = 6, message = "pin-code should be six digits.")
    private Long pinCode;

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", streetName='" + streetName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", distName='" + distName + '\'' +
                ", stateName='" + stateName + '\'' +
                ", pinCode=" + pinCode +
                '}';
    }
}