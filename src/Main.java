package com.cs1opnu;
import com.cs1opnu.adventure.factory.GameObjectFactory;
import com.cs1opnu.adventure.model.Item;
import com.cs1opnu.adventure.model.NPC;
import com.cs1opnu.adventure.model.Player;
import com.cs1opnu.adventure.model.Room;
import com.cs1opnu.adventure.singleton.GameState;
import com.cs1opnu.adventure.ui.PlayerConsole;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        initializeGameWorld();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter player1's name: ");
        String player1Name = scanner.nextLine().trim();
        Player player1 = GameObjectFactory.createPlayer(player1Name);
        GameState.getInstance().addPlayer(player1);
        System.out.print("Please enter player2's name (press enter to continue): ");
        String player2Name = scanner.nextLine().trim();
        Player player2 = null;
        if (!player2Name.isEmpty()) {
            player2 = GameObjectFactory.createPlayer(player2Name);
            GameState.getInstance().addPlayer(player2);
        }
        Thread player1Thread = new Thread(new PlayerConsole(player1));
        player1Thread.start();
        if (player2 != null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread player2Thread = new Thread(new PlayerConsole(player2));
            player2Thread.start();
        }
    }
    private static void initializeGameWorld() {
        GameState gameState = GameState.getInstance();
        Room entrance = GameObjectFactory.createRoom("Cave Entrance", "\n" + "You stand at the entrance of an ancient cave, sunlight streaming in from behind. Ahead lies a dark passage.");
        Room hall = GameObjectFactory.createRoom("Hall", "This is a spacious stone hall with ancient murals on the walls.");
        Room library = GameObjectFactory.createRoom("Library", "This used to be a library, and the bookshelves are filled with dusty books.");
        Room treasureRoom = GameObjectFactory.createRoom("Treasure Room", "A glittering treasure room! The legendary treasure is right here!");
        entrance.addExit("north", hall);
        hall.addExit("south", entrance);
        hall.addExit("east", library);
        hall.addExit("west", treasureRoom);
        library.addExit("west", hall);
        treasureRoom.addExit("east", hall);
        treasureRoom.setLocked(true);
        Item torch = GameObjectFactory.createItem("Torch", "A burning torch can illuminate a dark place.", true, false);
        Item key = GameObjectFactory.createItem("Golden Key", "A gleaming golden key, seemingly capable of opening a door.", true, true);
        Item treasure = GameObjectFactory.createItem("Golden Treasure", "A box of glittering gold—this is the legendary treasure!", true, false);
        gameState.setWinConditionItemId(treasure.getId());
        treasureRoom.setKeyId(key.getId());
        entrance.addItem(torch);
        library.addItem(key);
        treasureRoom.addItem(treasure);
        NPC oldMan = GameObjectFactory.createNPC("Old man", "An old man wearing a tattered robe", "The key to the treasure room is hidden in the library. Beware of the traps there!");
        hall.addNPC(oldMan);
        gameState.addRoom(entrance);
        gameState.addRoom(hall);
        gameState.addRoom(library);
        gameState.addRoom(treasureRoom);
    }
}