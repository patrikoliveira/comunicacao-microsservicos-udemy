package br.com.comunicacaomicrosservicosudemy.productapi.modules.product.service;

import br.com.comunicacaomicrosservicosudemy.productapi.config.exception.SuccessResponse;
import br.com.comunicacaomicrosservicosudemy.productapi.config.exception.ValidationException;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.category.service.CategoryService;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.product.dto.ProductRequest;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.product.dto.ProductResponse;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.product.model.Product;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.product.repository.ProductRepository;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.supplier.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class ProductService {
    private static final Integer ZERO = 0;

    private ProductRepository productRepository;
    private SupplierService supplierService;
    private CategoryService categoryService;


    public ProductResponse findByIdResponse(Integer id) {
        return ProductResponse.of(findById(id));
    }

    public List<ProductResponse> findAll() {
        return productRepository
                .findAll()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByName(String name) {
        if (isEmpty(name)) {
            throw new ValidationException("The product name must be informed.");
        }
        return productRepository
                .findByNameIgnoreCaseContaining(name)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplierId(Integer supplierId) {
        if (isEmpty(supplierId)) {
            throw new ValidationException("The product supplier ID must be informed.");
        }
        return productRepository
                .findBySupplierId(supplierId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        if (isEmpty(categoryId)) {
            throw new ValidationException("The product category ID must be informed.");
        }
        return productRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public Product findById(Integer id) {
        validateInformedId(id);
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no supplier for the given ID."));
    }

    public ProductResponse save(ProductRequest request) {
        validateProductDataInformed(request);
        validateCategoryAndSupplierIdInformed(request);

        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());

        var product = productRepository.save(Product.of(request, category, supplier));
        return ProductResponse.of(product);
    }

    public ProductResponse update(ProductRequest request, Integer id) {
        validateInformedId(id);
        validateProductDataInformed(request);
        validateCategoryAndSupplierIdInformed(request);

        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());
        var product = Product.of(request, category, supplier);
        product.setId(id);

        productRepository.save(product);
        return ProductResponse.of(product);
    }

    private void validateProductDataInformed(ProductRequest request) {
        if (isEmpty(request.getName())) {
            throw new ValidationException("The product's name was not informed.");
        }
        if (isEmpty(request.getQuantityAvailable())) {
            throw new ValidationException("The product's quantity was not informed.");
        }
        if (request.getQuantityAvailable() <= ZERO) {
            throw new ValidationException("The quantity should not be less or equal to zero.");
        }
    }

    private void validateCategoryAndSupplierIdInformed(ProductRequest request) {
        if (isEmpty(request.getCategoryId())) {
            throw new ValidationException("The category ID was not informed.");
        }

        if (isEmpty(request.getSupplierId())) {
            throw new ValidationException("The supplier ID was not informed.");
        }
    }

    public Boolean existsByCategoryId(Integer categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }
    public Boolean existsBySupplierId(Integer supplierId) {
        return productRepository.existsBySupplierId(supplierId);
    }

    public SuccessResponse delete(Integer id) {
        validateInformedId(id);

        productRepository.deleteById(id);
        return SuccessResponse.create("The product was deleted.");
    }

    private void validateInformedId(Integer id) {
        if (isEmpty(id)) {
            throw new ValidationException("The product ID must be informed.");
        }
    }
}