//package com.driver;
//
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//
//@Repository
//public class OrderRepository {
//
//    HashMap<String,Order> orderDB;
//    HashMap<String,DeliveryPartner> partnerDB;
//    HashMap<String, List<String>> orderPartnerDB;
//    HashMap<String,String> orderPartnerPair;
//
//    public OrderRepository(){
//        this.orderDB = new HashMap<>();
//        this.partnerDB = new HashMap<>();
//        this.orderPartnerDB = new HashMap<>();
//        this.orderPartnerPair = new HashMap<>();
//    }
//
//    public void addOrder(Order order){
//        orderDB.put(order.getId(),order);
//    }
//
//    public void addPartner(String partnerId){
//        DeliveryPartner partner = new DeliveryPartner(partnerId);
//        partnerDB.put(partnerId,partner);
//    }
//
//    public void addOrderPartnerPair(String orderId, String partnerId){
//        //This is basically assigning that order to that partnerId
//        if(orderDB.containsKey(orderId) && partnerDB.containsKey(partnerId)){
//            List<String> orders = new ArrayList<>();
//            if(orderPartnerDB.containsKey(partnerId))  orders = orderPartnerDB.get(partnerId);
//
//            orders.add(orderId);
//            orderPartnerDB.put(partnerId,orders);
//            orderPartnerPair.put(orderId,partnerId);
//            DeliveryPartner partner = partnerDB.get(partnerId);
//            partner.setNumberOfOrders(orders.size());
//        }
//    }
//
//    public Order getOrderById(String orderId){
//        //order should be returned with an orderId.
//        return orderDB.get(orderId);
//    }
//
//    public DeliveryPartner getPartnerById(String partnerId){
//        //deliveryPartner should contain the value given by partnerId
//        return partnerDB.get(partnerId);
//    }
//
//    public Integer getOrderCountByPartnerId(String partnerId){
//        //orderCount should denote the orders given by a partner-id
//        Integer countOfOrder = 0 ;
//        if(orderPartnerDB.containsKey(partnerId))
//            countOfOrder = orderPartnerDB.get(partnerId).size();
//        return countOfOrder;
//    }
//
//    public List<String> getOrdersByPartnerId(String partnerId){
//        //orders should contain a list of orders by PartnerId
//        List<String> orders = new ArrayList<>();
//        if(orderPartnerDB.containsKey(partnerId))
//            orders = orderPartnerDB.get(partnerId);
//        return orders;
//    }
//
//    public List<String> getAllOrders(){
//        //Get all orders
//        return new ArrayList<>(orderDB.keySet());
//    }
//
//    public Integer getCountOfUnassignedOrders(){
//        Integer countOfOrders = orderPartnerPair.size();
//        //Count of orders that have not been assigned to any DeliveryPartner
//        return orderDB.size() - countOfOrders;
//    }
//
//    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
//
//        Integer countOfOrders = 0;
//        //countOfOrders that are left after a particular time of a DeliveryPartner
//        Integer hour = Integer.parseInt(time.substring(0,2));
//        Integer min = Integer.parseInt(time.substring(3));
//        Integer currTime = (hour*60)+min;
//        for(String orderId:orderPartnerDB.get(partnerId)){
//            if(orderDB.get(orderId).getDeliveryTime()>currTime)
//                countOfOrders++;
//        }
//        return countOfOrders;
//    }
//
//    public String getLastDeliveryTimeByPartnerId(String partnerId){
//        //Return the time when that partnerId will deliver his last delivery order.
//        List<Integer> timeList = new ArrayList<>();
//        for(String orderId:orderPartnerDB.get(partnerId)){
//            timeList.add(orderDB.get(orderId).getDeliveryTime());
//        }
//        int lastDeliveryTime = Collections.max(timeList);
//        int hour = lastDeliveryTime/60;
//        int min = lastDeliveryTime%60;
//        String hourInStr = String.valueOf(hour);
//        String minInStr = String.valueOf(min);
//        if(hourInStr.length()==1){
//            hourInStr = String.format("0%s",hourInStr);
//        }
//        if(minInStr.length()==1){
//            minInStr = String.format("0%s",minInStr);
//        }
//        return String.format("%s:%s",hourInStr,minInStr);
//    }
//
//    public void deletePartnerById(String partnerId){
//        //Delete the partnerId
//        //And push all his assigned orders to unassigned orders.
//        if(orderPartnerDB.containsKey(partnerId)){
//            for(String orderId:orderPartnerDB.get(partnerId)){
//                if(orderPartnerPair.containsKey(orderId))
//                    orderPartnerPair.remove(orderId);
//            }
//            orderPartnerDB.remove(partnerId);
//        }
//        if(partnerDB.containsKey(partnerId))
//            partnerDB.remove(partnerId);
//    }
//
//    public void deleteOrderById(String orderId){
//        //Delete an order and also
//        // remove it from the assigned order of that partnerId
//        String partnerId = orderPartnerPair.get(orderId);
//        List<String> orders = orderPartnerDB.get(partnerId);
//        orders.remove(orderId);
//        orderPartnerDB.put(partnerId,orders);
//        orderPartnerPair.remove(orderId);
//
//        DeliveryPartner partner = partnerDB.get(partnerId);
//        partner.setNumberOfOrders(orders.size());
//        if(orderDB.containsKey(orderId))
//            orderDB.remove(orderId);
//    }
//}
//

package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
class OrderRepository
{
    private Map<String, Order> orderDb = new HashMap<>();
    private Map<String, DeliveryPartner> partnerDb = new HashMap<>();

    private Map<String, List<String>> partnerOrderDb = new HashMap<>();
    private Map<String, String> orderPartnerPair = new HashMap<>();


    public void addOrder(Order order) {orderDb.put(order.getId(), order);}


    public void addPartner(String partner)
    {
        DeliveryPartner pId = new DeliveryPartner(partner);
        partnerDb.put(pId.getId(),pId);
    }

    public void addOrderPartnerPair(String oId, String pId)
    {
        if (orderDb.containsKey(oId) && partnerDb.containsKey(pId))
        {
            List<String> orderList = new ArrayList<>();

            if (partnerOrderDb.containsKey(pId)) orderList = partnerOrderDb.get(pId);

            orderList.add(oId);

            partnerOrderDb.put(pId,orderList);
            orderPartnerPair.put(oId, pId);

            DeliveryPartner partner = partnerDb.get(pId);
            partner.setNumberOfOrders(orderList.size());
            partnerDb.put(pId,partner);
        }
    }

    public Order getOrderById(String oId) { return orderDb.get(oId);}

    public DeliveryPartner getPartnerById(String pId) {return partnerDb.get(pId);}


    public int getOrderCountByPartnerId(String pId)
    {
        List<String> orderList = new ArrayList<>();
        if (partnerOrderDb.containsKey(pId)) orderList = partnerOrderDb.get(pId);
        return orderList.size();
    }

    public List<String> getOrdersByPartnerId(String pId) {return partnerOrderDb.get(pId);}

    public List<String> getAllOrders()
    {
        List<String> ol = new ArrayList<>();
        for (String order: orderDb.keySet()) ol.add(order);
        return ol;
    }

    public int getCountOfUnassignedOrders()
    {
        //Allorder list - everypartner order carring
        int countParticularPartner = 0;
        int count_AllOrder = orderDb.keySet().size();

        for (String pId: partnerOrderDb.keySet())
        {
            countParticularPartner += partnerOrderDb.get(pId).size();
        }
        return count_AllOrder - countParticularPartner;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String pId)
    {
        int count = 0;

        String [] t = time.split(":");
        int convertTime = Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);

        if (partnerOrderDb.containsKey(pId))
        {
            for (String oId: partnerOrderDb.get(pId))
            {
                if (orderDb.get(oId).getDeliveryTime() < convertTime) count++;
            }
        }
        return count;
    }


    public String getLastDeliveryTimeByPartnerId(String pId)
    {
        List<Integer> time = new ArrayList<>();
        if (partnerOrderDb.containsKey(pId))
        {
            for (String oId: partnerOrderDb.get(pId))
            {
                Order order = orderDb.get(oId);
                time.add(order.getDeliveryTime());
            }
        }
        int maxTime = Collections.max(time);

        //convert int time to String time like (HH:MM)
        String startTime = "00:00";
        int HH = maxTime / 60 + Integer.parseInt(startTime.substring(0,2));
        int MM = maxTime % 60 + Integer.parseInt(startTime.substring(3));

        String newtime = HH+":"+MM;
        return newtime;
    }

    public void deletePartnerById(String pId)
    {
        HashSet<String> hs = new HashSet<>();
        if (partnerOrderDb.containsKey(pId) && partnerDb.containsKey(pId))
        {
            for (String oId: partnerOrderDb.get(pId)) //hs.add(oId);
            {
                if(orderPartnerPair.containsKey(oId)) orderPartnerPair.remove(oId);
            }
            partnerOrderDb.remove(pId);
            partnerDb.remove(pId);
        }
    }


    public void deleteOrderById(String oId)
    {
        String pId = null;
        List<String> list = new ArrayList<>();
        int size = 0;
        if (orderDb.containsKey(oId))
        {
            if (orderPartnerPair.containsKey(oId)) pId = orderPartnerPair.get(oId);
            orderPartnerPair.remove(oId);
            if (partnerOrderDb.containsKey(pId)) list = partnerOrderDb.get(pId);
            list.remove(oId);
            partnerOrderDb.put(pId,list);

            DeliveryPartner partner = partnerDb.get(pId);
            partner.setNumberOfOrders(list.size());
            partnerDb.put(pId,partner);
        }
        orderDb.remove(oId);
    }
}