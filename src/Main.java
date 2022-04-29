import io.AccountsIO;
import io.IngredientIO;
import io.ReceipeIO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entities.CommandType;
import entities.Expense;
import entities.ExpenseType;
import entities.Ingredient;
import entities.Order;
import entities.PurchaseOrder;
import entities.Receipe;
import entities.Sales;
import exceptions.IngredientNotFoundException;
import exceptions.InsufficientIngredientException;
import exceptions.InsufficientMoneyException;
import exceptions.ReceipeNotFoundException;
import service.AccountHandler;
import service.IngredientHandler;
import service.ReceipeHandler;

public class Main {

    private static List<Sales> salesList;
    private static List<Expense> expenseList;
    private static double availableMoney;
    private static List<Ingredient> ingredientList;
    private static List<Receipe> receipeList;

    private static AccountHandler accountHandler;
    private static IngredientHandler ingredientHandler;
    private static ReceipeHandler receipeHandler;
    private static IngredientIO ingredientIO;
    private static ReceipeIO receipeIO;
    private static AccountsIO accountsIO;


    public static void main(String[] args) throws FileNotFoundException {

        salesList = new ArrayList<>();
        expenseList = new ArrayList<>();
        ingredientIO = new IngredientIO();
        receipeIO = new ReceipeIO();
        accountsIO = new AccountsIO();
        ingredientHandler = new IngredientHandler();
        accountHandler = new AccountHandler();
        receipeHandler = new ReceipeHandler();

        CommandType currentCommand = CommandType.NO_COMMAND;
        Ingredient selectedIngredient = null;
        double ingredientQty = 0;
        Receipe selectedReceipe = null;
        Map<Ingredient, Double> insufficientIngredients = null;

        ingredientList = ingredientIO.readIngredientList("resources/ingredients.txt");
        receipeList = receipeIO.readAllReceipes("resources/receipe.txt", ingredientList);
        availableMoney = accountsIO.readAccounts("resources/accounts.txt");

        System.out.println("Available money is " + availableMoney);

        while (true) {
            try {
                if (currentCommand == CommandType.NO_COMMAND) {
                    int selectedNumber = displayPrompt();
                    currentCommand = CommandType.values()[selectedNumber];
                } else if (currentCommand == CommandType.VIEW_AVAILABLE_INGREDIENTS) {
                    ingredientHandler.viewIngredients(ingredientList);
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.ORDER_SPECIFIC_INGREDIENT) {
                    selectedIngredient = selectIngredient();
                    currentCommand = CommandType.INPUT_INGREDIENT_QTY;
                }
                else if (currentCommand == CommandType.INPUT_INGREDIENT_QTY) {
                    ingredientQty = inputIngredientQty();
                    if (ingredientHandler.isPossibleToOrderIngredient(selectedIngredient,
                            ingredientQty, availableMoney)) {
                        System.out.println("Ordered successfully!");
                        purchaseIngredient(selectedIngredient, ingredientQty);
                        currentCommand = CommandType.NO_COMMAND;
                    }
                    else {
                        throw new InsufficientMoneyException();
                    }
                }
                else if (currentCommand == CommandType.VIEW_TOTAL_SALES) {
                    accountHandler.printSales(salesList);
                   currentCommand = CommandType.NO_COMMAND;
                }
                else if (currentCommand == CommandType.VIEW_TOTAL_EXPENSES) {
                    accountHandler.printExpenses(expenseList);
                    currentCommand = CommandType.NO_COMMAND;
                }
                else if (currentCommand == CommandType.VIEW_NET_PROFIT) {
                    accountHandler.printProfit(salesList, expenseList);
                    currentCommand = CommandType.NO_COMMAND;
                }
                else if (currentCommand == CommandType.PLACE_ORDER) {
                    selectedReceipe = selectReceipe();
                    receipeHandler.checkIfPossibleToPrepareReceipe(selectedReceipe, ingredientList);
                    currentCommand = CommandType.FINALIZE_ORDER;
                }
                else if (currentCommand == CommandType.ORDER_MULTIPLE_INGREDIENTS) {
                    ingredientHandler.isPossibleToOrderIngredients(insufficientIngredients,
                            availableMoney);
                    purchaseIngredients(insufficientIngredients);
                    currentCommand = CommandType.FINALIZE_ORDER;
                }
                else if (currentCommand == CommandType.FINALIZE_ORDER) {
                    finalizeOrder(selectedReceipe);
                    System.out.println("Order for " + selectedReceipe.getName() + " is finalized and ready to serve!");
                    currentCommand = CommandType.NO_COMMAND;
                }
                else if (currentCommand == CommandType.EXIT) {
                    System.exit(0);
                }
            }
            catch(InsufficientIngredientException ex) {
                insufficientIngredients = ex.getInsufficientIngredients();
                currentCommand = CommandType.ORDER_MULTIPLE_INGREDIENTS;
            }
            catch(InsufficientMoneyException ex) {
                System.out.println(ex.getMessage());
                currentCommand = CommandType.NO_COMMAND;
            }
            catch(Exception ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
                currentCommand = CommandType.NO_COMMAND;
            }
        }
    }

    public static int displayPrompt() {
        System.out.println("Please select one of the following commands");
        System.out.println("1. View Available Ingredients");
        System.out.println("2. Order Specific Ingredient");
        System.out.println("3. View Total Sales");
        System.out.println("4. View Total Expenses");
        System.out.println("5. View Net Profit");
        System.out.println("6. Place Order");
        System.out.println("7. Exit From The Program");

        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static Ingredient selectIngredient() {
        System.out.println("Please enter the ingredient you want to order: ");
        Scanner scanner = new Scanner(System.in);
        String ingredientName = scanner.nextLine();

        for(int i = 0; i < ingredientList.size(); i++) {
            if (ingredientList.get(i).getName().equals(ingredientName)) {
                return ingredientList.get(i);
            }
        }

        throw new IngredientNotFoundException("Ingredient " + ingredientName + " not found!");
    }

    public static Receipe selectReceipe() {
        System.out.println("Please enter the receipe you want to order: ");
        Scanner scanner = new Scanner(System.in);
        String receipeName = scanner.nextLine();

        for(int i = 0; i < receipeList.size(); i++) {
            if (receipeList.get(i).getName().equals(receipeName)) {
                return receipeList.get(i);
            }
        }

        throw new ReceipeNotFoundException("Receipe " + receipeName + " not found!");
    }

    public static double inputIngredientQty() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }

    public static void purchaseIngredient(Ingredient ingredientOrdered, double qtyOrdered) {
        for(int i = 0; i < ingredientList.size(); i++) {
            if (ingredientList.get(i).getName().equals(ingredientOrdered.getName())) {
                double oldQty = ingredientList.get(i).getQty();
                double oldiQty=ingredientOrdered.getQty();
                ingredientList.get(i).setQty(oldQty + qtyOrdered);

            }
        }
        double totalAmount = ingredientOrdered.getRate()*qtyOrdered;
        Map<Ingredient, Double> composition = new HashMap<>();
        composition.put(ingredientOrdered, qtyOrdered);
        PurchaseOrder po = new PurchaseOrder(totalAmount, composition);
        expenseList.add(new Expense(totalAmount, po, ExpenseType.PURCHASE));
        availableMoney -= totalAmount;
    }

    public static void purchaseIngredients(Map<Ingredient, Double> ingredientsToOrder) {
        double moneySpent = 0.0;
        for(int i = 0; i < ingredientList.size(); i++) {
            if (ingredientsToOrder.containsKey(ingredientList.get(i))) {
                double oldQty = ingredientList.get(i).getQty();
                double qtyToOrder = ingredientsToOrder.get(ingredientList.get(i));
                moneySpent += qtyToOrder*ingredientList.get(i).getRate();
                ingredientList.get(i).setQty(oldQty + qtyToOrder);
            }
        }
        PurchaseOrder po = new PurchaseOrder(moneySpent, ingredientsToOrder);
        Expense expense = new Expense(moneySpent, po, ExpenseType.PURCHASE);
        expenseList.add(expense);
        availableMoney -= moneySpent;
    }

    public static void finalizeOrder(Receipe receipe) {
        Map<Ingredient, Double> composition = receipe.getComposition();
        for(int i = 0; i < ingredientList.size(); i++) {
            Ingredient currentIngredient = ingredientList.get(i);
            if (composition.containsKey(currentIngredient)) {
                double oldQty = currentIngredient.getQty();
                ingredientList.get(i).setQty(oldQty - composition.get(currentIngredient));
            }
        }

        Order order = new Order(receipe, receipe.getAmount());
        Sales sale = new Sales(order, receipe.getAmount());

        salesList.add(sale);
        availableMoney += receipe.getAmount();
    }
}