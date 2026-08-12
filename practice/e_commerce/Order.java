package practice.e_commerce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private String orderId;
    private String customerName;

    private OrderStatus orderStatus;

    private List<Product> items;

    private DiscountStrategy discountStrategy;

    @Override
    public String toString() {
        return "Customer Name: " + customerName + ", Subtotal: " + calculateSubtotal() + ", Total: "
                + calculateTotal() + ", Status: " + orderStatus;
    }

    public Order(String customerName) {
        orderId = UUID.randomUUID().toString();
        this.customerName = customerName;
        items = new ArrayList<>();
        orderStatus = OrderStatus.NEW;
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    public void addProduct(Product product) {
        if (orderStatus == OrderStatus.NEW) {
            items.add(product);
        }
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public BigDecimal calculateSubtotal() {
        BigDecimal total = new BigDecimal(0);
        for (Product item : items) {
            total = total.add(item.calculateFinalPrice());
        }
        return total;
    }

    public BigDecimal calculateTotal() {
        if (discountStrategy != null) {
            return discountStrategy.applyDiscount(calculateSubtotal());
        }
        return calculateSubtotal();
    }

    public void checkout() {
        try {
            if (items.isEmpty()) {
                throw new IllegalStateException("Không thể thanh toán đơn hàng rỗng!");
            } else {
                orderStatus = OrderStatus.PAID;
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Product> getItems() {
        return items;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getOrderId() {
        return orderId;
    }
}
