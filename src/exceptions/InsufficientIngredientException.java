package exceptions;

import java.util.Map;

import entities.Ingredient;

public class InsufficientIngredientException extends RuntimeException {
    private Map<Ingredient, Double> insufficientIngredients;

    public InsufficientIngredientException(Map<Ingredient, Double> insufficientIngredients) {
        this.insufficientIngredients = insufficientIngredients;
    }

    public Map<Ingredient, Double> getInsufficientIngredients() {
        return insufficientIngredients;
    }
}
