package com.cs1opnu.adventure.ui;
import com.cs1opnu.adventure.model.Player;
import com.cs1opnu.adventure.service.GameEngine;
import com.cs1opnu.adventure.singleton.GameState;
import java.util.List;
import java.util.Scanner;
public class PlayerConsole implements Runnable {
    private Player player;
    private GameEngine gameEngine;
    private Scanner scanner;
    public PlayerConsole(Player player) {
        this.player = player;
        this.gameEngine = new GameEngine();
        this.scanner = new Scanner(System.in);
    }
    @Override
    public void run() {
        System.out.println("\nWelcome to the text adventure game，" + player.getName() + "!");
        System.out.println("Type 'help' to view valid commands.");
        System.out.println(gameEngine.processCommand(player, "look"));

        while (GameState.getInstance().isGameRunning()) {
            List<String> notifications = player.getAndClearNotifications();
            for (String notification : notifications) {
                System.out.println("\n[notification] " + notification);
            }
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();
            String result = gameEngine.processCommand(player, input);
            System.out.println("\n" + result);
            if (input.equalsIgnoreCase("quit")) {
                break;
            }
        }

        System.out.println("Game over, goodbye!");
    }
}