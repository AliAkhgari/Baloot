package application;

import io.javalin.Javalin;

import static defines.Endpoints.SERVER_PORT;

// todo: remove prints

public class Main {
    public static void main(String[] args) {
        Baloot baloot = new Baloot();

        Javalin app = Javalin.create().start(SERVER_PORT);

        baloot.fetchAndStoreDataFromAPI();

        Server server = new Server(baloot, app);
        server.start();
    }
}
