import java.util.ArrayList;

public class Order {
    private int orderID;
    private String orderDate;
    private Customer customer;
    private ArrayList<Product> product;

    public Order(int orderID, String orderDate, Customer customer, ArrayList<Product> product) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customer = customer;
        this.product = product;
    }
}
