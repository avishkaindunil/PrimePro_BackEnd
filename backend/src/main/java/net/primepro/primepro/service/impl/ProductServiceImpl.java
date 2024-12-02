package net.primepro.primepro.service.impl;

import net.primepro.primepro.dto.ProductDto;
import net.primepro.primepro.entity.Product;
import net.primepro.primepro.repository.ProductRepository;
import net.primepro.primepro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setQuantity(0);
        product.setUnitType(productDto.getUnitType());

        return productRepository.save(product);
    }
}
