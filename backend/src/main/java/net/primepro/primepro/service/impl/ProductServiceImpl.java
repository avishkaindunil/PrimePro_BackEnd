package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.entity.Product;
import net.primepro.primepro.repository.ProductRepo;
import net.primepro.primepro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;


    @Override
    public Product addProduct(Product product) {
        Product newProduct = new Product();
        newProduct.setId(product.getId());
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setQuantity(product.getQuantity());

        return  productRepo.save(newProduct);
    }

    @Override
    public Long deleteProduct(Long productID) {
        productRepo.deleteById(productID);
        return productID;
    }

    @Override
    public List<Product> viewProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product updateOrder(Long Long, Product product) {

        product.setId(product.getId());
        product.setQuantity(product.getQuantity());
        product.setName(product.getName());
        product.setDescription(product.getDescription());
        product.setPrice(product.getPrice());

        return productRepo.save(product);
    }


}
