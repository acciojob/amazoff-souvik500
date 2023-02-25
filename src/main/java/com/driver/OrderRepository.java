package com.driver;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository
{

    private final Map<String, Order> orders = new HashMap<>();
    private final Map<String, DeliveryPartner> partners = new HashMap<>();
    private final Map<String, String> orderPartnerPairs = new HashMap<>();
    private final Map<String, Integer> partnerOrderCount = new HashMap<>();

    /**
     *
     * @param order
     */
    public void addOrder(Order order) {
        orders.put(order.getId(), order);
    }

    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }

    public List<Order> getAllOrders() {
        return orders.values().stream().collect(Collectors.toList());
    }

    public void deleteOrderById(String orderId) {
        Order order = orders.get(orderId);
        if (order != null) {
            orders.remove(orderId);
            String partnerId = orderPartnerPairs.get(orderId);
            if (partnerId != null) {
                orderPartnerPairs.remove(orderId);
                int count = partnerOrderCount.get(partnerId) - 1;
                if (count == 0) {
                    partners.remove(partnerId);
                    partnerOrderCount.remove(partnerId);
                } else {
                    partnerOrderCount.put(partnerId, count);
                }
            }
        }
    }

    public void addPartner(DeliveryPartner partner) {
        partners.put(partner.getId(), partner);
        partnerOrderCount.put(partner.getId(), 0);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partners.get(partnerId);
    }

    public void deletePartnerById(String partnerId) {
        partners.remove(partnerId);
        partnerOrderCount.remove(partnerId);
        orderPartnerPairs.entrySet().removeIf(entry -> entry.getValue().equals(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderPartnerPairs.put(orderId, partnerId);
        partnerOrderCount.put(partnerId, partnerOrderCount.get(partnerId) + 1);
    }

    public int getOrderCountByPartnerId(String partnerId) {
        return (int) orderPartnerPairs.values().stream().filter(partnerId::equals).count();
    }

    public List<Order> getOrdersByPartnerId(String partnerId) {
        return orderPartnerPairs.entrySet().stream()
                .filter(entry -> entry.getValue().equals(partnerId))
                .map(entry -> orders.get(entry.getKey()))
                .collect(Collectors.toList());
    }

    public int getCountOfUnassignedOrders() {
        return (int) orders.values().stream().filter(order -> !orderPartnerPairs.containsKey(order.getId())).count();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String partnerId, String time) {
        return (int) orderPartnerPairs.entrySet().stream()
                .filter(entry -> entry.getValue().equals(partnerId))
                .map(entry -> orders.get(entry.getKey()))
                .filter(order -> order.getDeliveryTime().compareTo(LocalTime.parse(time)) > 0)
                .count();
    }

    public LocalTime getLastDeliveryTimeByPartnerId(String partnerId) {
        return orderPartnerPairs.entrySet().stream()
                .filter(entry -> entry.getValue().equals(partnerId))
                .map(entry -> orders.get(entry.getKey()))
                .max((o1, o2) -> o1.getDeliveryTime().compareTo(o2.getDeliveryTime()))
                .map(Order::getDeliveryTime)
                .orElse(null);
    }
}
