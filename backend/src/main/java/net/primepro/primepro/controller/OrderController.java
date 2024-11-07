package net.primepro.primepro.controller;


import net.primepro.primepro.entity.Order;
import net.primepro.primepro.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/add")
    public Order add_order(@RequestBody Order order) {
        return  orderService.addOrder(order);

    }

    @DeleteMapping("/order/delete/{id}")
    public String delete_order(@PathVariable("id") Long id){
        Long O_id = orderService.deleteOrder(id);
        return("Deleted Order"+O_id);
    }

    @GetMapping("/order/get-all")
    public List<Order> get_all(){
        return orderService.viewOrders();
    }

    @PutMapping("/order/update/{id}")
    public Order updateOrder(@PathVariable("id") Long id,@RequestBody Order order){
        return  orderService.updateOrder(id, order);
    }

}
