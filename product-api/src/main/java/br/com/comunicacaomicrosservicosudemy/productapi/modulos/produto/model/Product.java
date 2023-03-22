package br.com.comunicacaomicrosservicosudemy.productapi.modulos.produto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "FK_SUPPLIER", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "FK_CATEGORY ", nullable = false)
    private Category category;

    @Column(name = "QUANTITY_AVAILABLE", nullable = false)
    private Integer quantityAvailable;
}
