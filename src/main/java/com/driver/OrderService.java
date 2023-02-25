package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(DeliveryPartner order) {
        orderRepository.addPartner(order);
    }

    public void addOrderPartnerPair(String orderId, String partnerId)
    {
        orderRepository.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public List<Order> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public int getOrderCountByPartnerId(String partnerId)
    {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public DeliveryPartner getPartnerById(String partnerId)
    {
        return orderRepository.getPartnerById(partnerId);
    }


    public int getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String partnerId, LocalTime time) {
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(partnerId, String.valueOf(time));
    }

    public LocalTime getLastDeliveryTimeByPartnerId(String partnerId) {
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }


    public void deletePartnerById(String partnerId)
    {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
