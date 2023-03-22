package br.com.comunicacaomicrosservicosudemy.productapi.modules.category.controller;

import br.com.comunicacaomicrosservicosudemy.productapi.modules.category.dto.CategoryRequest;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.category.service.CategoryService;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.category.dto.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

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
}
