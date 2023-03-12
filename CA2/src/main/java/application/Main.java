package application;

import database.Database;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // getting list of users, providers, and ...
        Database database = new Database();
        try {
            database.getUsersList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // showing pages
    }
}
