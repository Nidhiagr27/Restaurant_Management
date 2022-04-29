package io;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Ingredient;
import entities.Receipe;
import exceptions.InvalidIngredientException;
import exceptions.ReceipeNotFoundException;

public class ReceipeIO {
    public List<Receipe> readAllReceipes(String filePath, List<Ingredient> ingredientList) throws FileNotFoundException {
        List<String> lines = CustomFileReader.readFile(filePath);
        List<Receipe> receipeList = new ArrayList<>();

        for(String line: lines) {
            String[] splitLine = line.split(" ");
            String receipeName = splitLine[0];
            double amount = Double.parseDouble(splitLine[1]);
            Map<Ingredient, Double> composition = new HashMap<>();

            for(int i = 2; i < splitLine.length; i += 2) {
                String ingredientName = splitLine[i];
                double qty = Double.parseDouble(splitLine[i+1]);

                boolean flag = false;
                for(int j = 0; j < ingredientList.size(); j++) {
                    if (ingredientList.get(j).getName().equals(ingredientName)) {
                        flag = true;
                        composition.put(ingredientList.get(j), qty);
                        break;
                    }
                }

                if (flag == false) {
                    throw new InvalidIngredientException("Ingredient " + ingredientName + " not found!");
                }
            }

            Receipe receipe = new Receipe(receipeName, composition, amount);
            receipeList.add(receipe);
        }
        System.out.println("Read " + receipeList.size() + " receipes");
        return receipeList;
    }
}
