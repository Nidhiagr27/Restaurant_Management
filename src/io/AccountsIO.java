package io;

import java.io.FileNotFoundException;
import java.util.List;

public class AccountsIO {
    public double readAccounts(String filePath) throws FileNotFoundException {
        List<String> lines = CustomFileReader.readFile(filePath);
        return Double.parseDouble(lines.get(0));
    }
}