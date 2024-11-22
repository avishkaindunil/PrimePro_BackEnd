package net.primepro.primepro.controller;


import net.primepro.primepro.entity.Orders;
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
    public Orders add_order(@RequestBody Orders orders) {
        return  orderService.addOrder(orders);

    }

    @DeleteMapping("/order/delete/{id}")
    public String delete_order(@PathVariable("id") Long id){
        Long O_id = orderService.deleteOrder(id);
        return("Deleted Order"+O_id);
    }

    @GetMapping("/order/get-all")
    public List<Orders> get_all(){
        return orderService.viewOrders();
    }

    @PutMapping("/order/update/{id}")
    public Orders updateOrder(@PathVariable("id") Long id, @RequestBody Orders orders){
        return  orderService.updateOrder(id, orders);
    }

}
