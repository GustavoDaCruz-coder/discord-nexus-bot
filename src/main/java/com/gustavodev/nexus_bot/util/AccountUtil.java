package com.gustavodev.nexus_bot.util;

import com.gustavodev.nexus_bot.model.Account;
import com.opencsv.CSVReader;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class AccountUtil {

    @SneakyThrows
    public static List<Account> readData() {
        CSVReader csvReader = new CSVReader(new FileReader(Constants.DATA_DIRECTORY));
        List<Account> accountList = new ArrayList<>();
        String[] line;

        while ((line = csvReader.readNext()) != null) {
            Account account = new Account();

            account.setId(line[7]);
            account.setPlataform(line[0]);
            account.setUsername(line[1]);
            account.setPassword(line[2]);
            account.setMfa(Boolean.parseBoolean(line[3]));
            account.setOwner(line[4]);
            account.setDescription(line[5]);
            account.setActive(Boolean.parseBoolean(line[6]));

            accountList.add(account);
        }
        csvReader.close();
        return accountList;
    }

    public static String encrypt(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    public static String getTextByIndex(String text, int index) {
        return text.split(" ")[index];
    }

    public static String decrypt(String encryptedText) {
        return Arrays.toString(Base64.getDecoder().decode(encryptedText));
    }

}
