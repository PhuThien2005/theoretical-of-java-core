package practice.e_commerce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import practice.e_commerce.*;

public class OrderService {
    private List<Order> orders;

    public OrderService() {
        orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        ArrayList<Order> orderByStatus = new ArrayList<>();
        for (Order order : orders) {
            if (status.equals(order.getOrderStatus())) {
                orderByStatus.add(order);
            }
        }
        return orderByStatus;
    }

    public BigDecimal calculateTotalRevenue() {
        BigDecimal total = new BigDecimal(0);
        for (Order order : orders) {
            if (OrderStatus.PAID.equals(order.getOrderStatus())) {
                total = total.add(order.calculateTotal());
            }
        }
        return total;
    }
}
