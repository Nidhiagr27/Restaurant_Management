package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Ingredient;
import entities.Receipe;
import exceptions.InsufficientIngredientException;

public class ReceipeHandler {
    public void checkIfPossibleToPrepareReceipe(Receipe receipe, List<Ingredient> ingredientList) {
        Map<Ingredient, Double> composition = receipe.getComposition();
        Map<Ingredient, Double> insufficientIngredient = new HashMap<>();
        for(Ingredient ing : ingredientList) {
            if (composition.containsKey(ing)) {
                double qtyUsed = composition.get(ing);
                if (qtyUsed > ing.getQty()) {
                    insufficientIngredient.put(ing, qtyUsed - ing.getQty());
                }
            }
        }

        if (insufficientIngredient.size() > 0) {
            throw new InsufficientIngredientException(insufficientIngredient);
        }
    }
}