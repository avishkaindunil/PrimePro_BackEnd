package net.primepro.primepro.controller;


import net.primepro.primepro.entity.Order;
import net.primepro.primepro.entity.Product;
import net.primepro.primepro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product/add")
    public Product add_booking(@RequestBody Product product) {
        return  productService.addProduct(product);

    }

    @GetMapping("/product/get-all")
    public List<Product> getProducts(){
        return productService.viewProducts();
    }

    @PutMapping("/product/update/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return productService.updateOrder(id, product);
    }

    @DeleteMapping("/product/delete/{productID}")
    public String deleteProduct(@PathVariable Long productID){
        Long id = productService.deleteProduct(productID);
        return ("Deleted product"+productID);
    }


}
