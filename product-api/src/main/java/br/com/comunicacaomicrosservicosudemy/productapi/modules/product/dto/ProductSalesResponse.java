package br.com.comunicacaomicrosservicosudemy.productapi.modules.product.dto;

import br.com.comunicacaomicrosservicosudemy.productapi.modules.category.dto.CategoryResponse;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.product.model.Product;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.supplier.dto.SupplierResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalesResponse {
    private Integer id;
    private String name;
    @JsonProperty("quantity_available")
    private Integer quantityAvailable;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/mm/yyyy HH:mm:ss")
    private LocalDateTime createAt;
    private SupplierResponse supplier;
    private CategoryResponse category;
    private List<String> sales;

    public static ProductSalesResponse of(Product product, List<String> sales) {
        return ProductSalesResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .quantityAvailable(product.getQuantityAvailable())
                .createAt(product.getCreatedAt())
                .supplier(SupplierResponse.of(product.getSupplier()))
                .category(CategoryResponse.of(product.getCategory()))
                .sales(sales)
                .build();
    }
}
