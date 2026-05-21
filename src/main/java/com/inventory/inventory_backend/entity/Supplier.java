package com.inventory.inventory_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String name;

    @Size(max = 100)
    @Column(name = "contact_name", length = 100)
    private String contactName;

    @Email(message = "El email no tiene formato válido")
    @Size(max = 100)
    @Column(length = 100)
    private String email;

    @Size(max = 20)
    @Column(length = 20)
    private String phone;

    @Size(max = 255)
    @Column(length = 255)
    private String address;

    @OneToMany(mappedBy = "supplier", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
