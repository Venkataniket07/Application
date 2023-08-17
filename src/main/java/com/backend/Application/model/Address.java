package com.backend.Application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    @NotNull(message = "Apartment name cannot be null.")
    @Size(min = 3, max = 20, message = "Apartment name length should be between 3-20.")
    private String apartment;

    @NotNull(message = "Locality name cannot be null.")
    @Size(min = 3, max = 20, message = "Locality name length should be between 3-20.")
    private String locality;

    @NotNull(message = "City name cannot be null.")
    @Size(min = 3, max = 20, message = "city name length should be between 3-20.")
    private String city;

    @NotNull(message = "State name can't be null.")
    @Size(min = 3, max = 20, message = "state name length should be between 3-20.")
    private String state;

    private String country;

    @NotNull(message = "Pin-code can't be null.")
    @Size(min = 6, max = 6, message = "pin-code should be six digits.")
    private Long pinCode;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private Users users;
}
