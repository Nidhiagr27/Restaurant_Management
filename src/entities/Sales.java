package entities;

public class Sales {
    private Order order;
    private double amount;

    public Sales(Order order, double amount) {
        this.order = order;
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
