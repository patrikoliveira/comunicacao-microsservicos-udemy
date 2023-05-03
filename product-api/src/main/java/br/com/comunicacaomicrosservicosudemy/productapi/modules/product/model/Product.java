package br.com.comunicacaomicrosservicosudemy.productapi.modules.product.model;

import br.com.comunicacaomicrosservicosudemy.productapi.modules.category.model.Category;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.product.dto.ProductRequest;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.supplier.model.Supplier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "FK_SUPPLIER", nullable = false)
    private br.com.comunicacaomicrosservicosudemy.productapi.modules.supplier.model.Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "FK_CATEGORY ", nullable = false)
    private Category category;

    @Column(name = "QUANTITY_AVAILABLE", nullable = false)
    private Integer quantityAvailable;
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersiste() {
        createdAt = LocalDateTime.now();
    }

    public static Product of(ProductRequest request, Category category, Supplier supplier) {
        return Product
                .builder()
                .name(request.getName())
                .quantityAvailable(request.getQuantityAvailable())
                .supplier(supplier)
                .category(category)
                .build();
    }

    public void updateStock(Integer quantity) {
        quantityAvailable = quantityAvailable - quantity;
    }
}
