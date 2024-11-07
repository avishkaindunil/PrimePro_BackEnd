package net.primepro.primepro.service;

import net.primepro.primepro.entity.Product;

import java.util.List;

public interface ProductService {

    Product addProduct (Product product);
    Long deleteProduct(Long productID);
    List<Product> viewProducts();
    Product updateOrder(Long Long, Product product);

}
