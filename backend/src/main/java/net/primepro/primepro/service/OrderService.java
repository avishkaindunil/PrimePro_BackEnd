package net.primepro.primepro.service;

import net.primepro.primepro.entity.Orders;

import java.util.List;

public interface OrderService {

    Orders addOrder(Orders orders);
    Long deleteOrder(Long OrderID);
    List<Orders> viewOrders();
    Orders updateOrder(Long Long, Orders orders);
}
