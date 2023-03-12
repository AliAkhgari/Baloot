package application;

import database.Database;
import entities.Commodity;
import entities.Provider;
import entities.User;

import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        DataParser dataParser = new DataParser(database);

        try {
            dataParser.getUsersList();
            dataParser.getProvidersList();
            dataParser.getCommoditiesList();

//            for (User user: database.getUsers()) {
//                System.out.println(user.getUsername());
//            }
//
//            for (Provider provider: database.getProviders()) {
//                System.out.println(provider.getName());
//            }
//
//            for (Commodity commodity: database.getCommodities()) {
//                System.out.println(commodity.getName());
//            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // showing pages
    }
}
