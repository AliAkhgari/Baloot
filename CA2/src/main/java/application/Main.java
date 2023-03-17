package application;

import controllers.*;
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

        // todo: better name for setEntities
        baloot.setEntities();

        app.routes(() -> {
            try {
                new commoditiesController(baloot).getCommodities(app);
                new commoditiesController(baloot).getCommodity(app);
                new providerController(baloot).getProvider(app);
                new userController(baloot).getUser(app);
                new userController(baloot).increaseUserCredit(app);
                new voteCommentController(baloot).voteComment(app);
                new rateCommodityController(baloot).rateCommodity(app);
                new addToBuyListController(baloot).addToBuyList(app);
                new removeFromBuyListController(baloot).removeFromBuyList(app);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
