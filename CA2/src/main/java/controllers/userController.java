package controllers;

import entities.Baloot;
import entities.User;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class userController {

    private final Baloot baloot;

    public userController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void getUser(Context ctx) {
        String user_id = ctx.pathParam("user_id");

        User user = this.baloot.get_user(user_id);

    }
}
