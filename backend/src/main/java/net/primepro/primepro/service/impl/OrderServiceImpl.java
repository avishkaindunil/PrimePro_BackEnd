package net.primepro.primepro.service.impl;


import lombok.AllArgsConstructor;
import net.primepro.primepro.entity.Orders;
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
    public Orders addOrder(Orders orders) {
        Orders newOrders = new Orders();

        newOrders.setId(orders.getId());
        newOrders.setOrderDate(orders.getOrderDate());
        newOrders.setStatus(orders.getStatus());
        newOrders.setProducts(orders.getProducts());

        return  orderRepo.save(newOrders);

    }

    @Override
    public Long deleteOrder(Long OrderID) {
        orderRepo.deleteById(OrderID);
        return OrderID;
    }

    @Override
    public List<Orders> viewOrders() {
        return orderRepo.findAll();
    }

    @Override
    public Orders updateOrder(Long Long, Orders orders) {

        orders.setOrderDate(orders.getOrderDate());
        orders.setId(orders.getId());
        orders.setProducts(orders.getProducts());
        orders.setStatus(orders.getStatus());

        return orderRepo.save(orders);
    }


}
