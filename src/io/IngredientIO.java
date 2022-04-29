package io;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import entities.Ingredient;

public class IngredientIO {
    public List<Ingredient> readIngredientList(String filePath) throws FileNotFoundException {
        List<String> lines = CustomFileReader.readFile(filePath);

        List<Ingredient> result = new ArrayList<>();

        for(String line: lines) {
            String[] splitLine = line.split(" ");
            result.add(new Ingredient(splitLine[0], Double.parseDouble(splitLine[1]),
                    Double.parseDouble(splitLine[2])));
        }

        System.out.println("Read " + lines.size() + "ingredients");
        return result;
    }
}
