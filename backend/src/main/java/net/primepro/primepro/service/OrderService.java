package net.primepro.primepro.service;

import net.primepro.primepro.entity.Order;

import java.util.List;

public interface OrderService {

    Order addOrder(Order order);
    Long deleteOrder(Long OrderID);
    List<Order> viewOrders();
    Order updateOrder(Long Long, Order order);
}
