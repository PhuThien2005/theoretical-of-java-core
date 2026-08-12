package practice.e_commerce;

public class E_CommerceDemo {
    public static void main(String[] args) {

        PhysicalProduct p1 = new PhysicalProduct("Laptop Dell", 15_000_000, 2.5, 50000);
        PhysicalProduct p2 = new PhysicalProduct("Sách OOP", 250_000, 0.5, 25000);
        DigitalProduct d1 = new DigitalProduct("Khóa học Java Online", 3_000_000, "neith.url");
        Order o1 = new Order("An");
        o1.addProduct(p1);
        o1.addProduct(d1);
        o1.setDiscountStrategy(new PercentageDiscount(10, 200_000));
        o1.checkout();

        Order o2 = new Order("Bình");
        o2.addProduct(p2);
        o2.setDiscountStrategy(new FixedAmountDiscount(50_000));

        System.out.println(o1);
        System.out.println(o2);
        OrderService orderService = new OrderService();
        orderService.addOrder(o1);
        orderService.addOrder(o2);
        System.out.println("Revenue: " + orderService.calculateTotalRevenue());
    }
}
