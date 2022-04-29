package entities;

public class Expense {
    private double amount;
    private PurchaseOrder purchaseOrder;
    private ExpenseType expenseType;

    public Expense(double amount, PurchaseOrder purchaseOrder, ExpenseType expenseType) {
        this.amount = amount;
        this.purchaseOrder = purchaseOrder;
        this.expenseType = expenseType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }
    /*@Override
    public String toString() {

        return null;
    }*/
}
