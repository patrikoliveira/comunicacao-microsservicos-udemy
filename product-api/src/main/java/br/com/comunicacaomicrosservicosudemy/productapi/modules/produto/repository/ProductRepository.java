package br.com.comunicacaomicrosservicosudemy.productapi.modules.produto.repository;

import br.com.comunicacaomicrosservicosudemy.productapi.modules.produto.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
