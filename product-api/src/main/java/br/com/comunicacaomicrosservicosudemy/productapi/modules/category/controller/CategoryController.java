package br.com.comunicacaomicrosservicosudemy.productapi.modules.category.controller;

import br.com.comunicacaomicrosservicosudemy.productapi.config.exception.SuccessResponse;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.category.dto.CategoryRequest;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.category.dto.CategoryResponse;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.category.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest request) {
        return categoryService.save(request);
    }

    @GetMapping
    public List<CategoryResponse> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("{id}")
    public CategoryResponse findById(@PathVariable Integer id){
        return categoryService.findByIdResponse(id);
    }
    @GetMapping("description/{description}")
    public List<CategoryResponse> findByDescription(@PathVariable(name = "description") String description){
        return categoryService.findByDescription(description);
    }

    @PutMapping("{id}")
    public CategoryResponse update(@RequestBody CategoryRequest request, @PathVariable Integer id) {
        return categoryService.update(request, id);
    }

    @DeleteMapping("{id}")
    public SuccessResponse delete(@PathVariable Integer id) {
        return categoryService.delete(id);
    }
}
