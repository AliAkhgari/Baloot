package application;

import io.javalin.Javalin;

import static defines.Endpoints.SERVER_PORT;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(SERVER_PORT);

        Baloot.getInstance().fetchAndStoreDataFromAPI();

        Server server = new Server(Baloot.getInstance(), app);
        server.start();
    }
}
