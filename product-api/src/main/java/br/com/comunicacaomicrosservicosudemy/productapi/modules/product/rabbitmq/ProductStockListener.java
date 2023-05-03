package br.com.comunicacaomicrosservicosudemy.productapi.modules.product.rabbitmq;

import br.com.comunicacaomicrosservicosudemy.productapi.modules.product.dto.ProductStockDTO;
import br.com.comunicacaomicrosservicosudemy.productapi.modules.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductStockListener {
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void recieveProductStockMessage(ProductStockDTO product) throws JsonProcessingException {
        log.info("Recieving message with data: {} and TransactionID: {}",
                objectMapper.writeValueAsString(product),
                product.getTransactionId());
        productService.updateProductStock(product);
    }
}
