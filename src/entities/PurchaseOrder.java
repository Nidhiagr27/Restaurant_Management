package entities;

import java.util.Map;

public class PurchaseOrder {
        private double amount;
        private Map<Ingredient, Double> composition;

    public PurchaseOrder(double amount, Map<Ingredient, Double> composition) {
        this.amount = amount;
        this.composition = composition;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Map<Ingredient, Double> getComposition() {
        return composition;
    }

    public void setComposition(Map<Ingredient, Double> composition) {
        this.composition = composition;
    }
}
