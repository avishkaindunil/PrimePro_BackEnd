package net.primepro.primepro.service.impl;


import lombok.AllArgsConstructor;
import net.primepro.primepro.entity.Order;
import net.primepro.primepro.repository.OrderRepo;
import net.primepro.primepro.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;


    @Override
    public Order addOrder(Order order) {
        Order newOrder = new Order();

        newOrder.setId(order.getId());
        newOrder.setOrderDate(order.getOrderDate());
        newOrder.setStatus(order.getStatus());
        newOrder.setProducts(order.getProducts());

        return  orderRepo.save(newOrder);

    }

    @Override
    public Long deleteOrder(Long OrderID) {
        orderRepo.deleteById(OrderID);
        return OrderID;
    }

    @Override
    public List<Order> viewOrders() {
        return orderRepo.findAll();
    }

    @Override
    public Order updateOrder(Long Long, Order order) {

        order.setOrderDate(order.getOrderDate());
        order.setId(order.getId());
        order.setProducts(order.getProducts());
        order.setStatus(order.getStatus());

        return orderRepo.save(order);
    }


}
