package net.primepro.primepro.service;

import net.primepro.primepro.dto.ProductDto;
import net.primepro.primepro.entity.Product;

public interface ProductService {
    Product addProduct(ProductDto productDto);
}
