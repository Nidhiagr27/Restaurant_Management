package service;

import java.util.List;
import java.util.Map;

import entities.Ingredient;
import exceptions.InsufficientMoneyException;

public class IngredientHandler {
    public void viewIngredients(List<Ingredient> ingredientList) {
        for(Ingredient ingredient: ingredientList) {
            System.out.println(ingredient.toString());
        }
    }

    public boolean isPossibleToOrderIngredient(Ingredient ingredient, double qty, double availableMoney) {
        if (availableMoney >= ingredient.getRate()*qty) {
            return true;
        }
        else {
            return false;
        }
    }

    public void isPossibleToOrderIngredients(Map<Ingredient, Double> ingredientsToOrder, double availableMoney) {
        double moneyRequired = 0.0;
        for(Ingredient ingredient: ingredientsToOrder.keySet()) {
            double qtyToOrder = ingredientsToOrder.get(ingredient);
            double rate = ingredient.getRate();
            moneyRequired += rate*qtyToOrder;
        }

        if (availableMoney < moneyRequired) {
            throw new InsufficientMoneyException();
        }
    }
}