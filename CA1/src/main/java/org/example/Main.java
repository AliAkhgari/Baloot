package org.example;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Baloot baloot = new Baloot();
        CommandHandler commandHandler = new CommandHandler(baloot);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            System.out.println(commandHandler.handleCommand(input));
        }
    }
}
