package br.com.comunicacaomicrosservicosudemy.productapi.modules.produto.model;

import br.com.comunicacaomicrosservicosudemy.productapi.modules.produto.dto.CategoryRequest;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.produto.dto.CategoryResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "CATEGORY")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public static Category of(CategoryRequest request) {
        var category = new Category();
        BeanUtils.copyProperties(request, category);

        return category;
    }
}
