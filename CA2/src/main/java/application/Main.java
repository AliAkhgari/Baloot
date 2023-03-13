package application;

import controllers.commoditiesController;
import controllers.providerController;
import controllers.userController;
import database.Database;
import entities.Comment;
import entities.Commodity;
import entities.Provider;
import entities.User;
import io.javalin.Javalin;

import java.io.IOException;
import java.net.URL;

import static defines.endpoints.SERVER_PORT;


public class Main {
    public static void main(String[] args) {
        Baloot baloot = new Baloot();

        Javalin app = Javalin.create().start(SERVER_PORT);

        baloot.setEntities();


            app.routes(() -> {
                // Register the CommodityController
                try {
                    new commoditiesController(baloot).getCommodities(app);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Register the UserController
//                new userController().register(app);
//
//                // Register the OrderController
//                new providerController().register(app);
            });


        // showing pages
    }
}
