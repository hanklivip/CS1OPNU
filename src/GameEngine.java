package com.cs1opnu.adventure.service;
import com.cs1opnu.adventure.model.Item;
import com.cs1opnu.adventure.model.NPC;
import com.cs1opnu.adventure.model.Player;
import com.cs1opnu.adventure.model.Room;
import com.cs1opnu.adventure.singleton.GameState;
import java.util.Optional;
public class GameEngine {
    private GameState gameState;
    private CommandParser commandParser;
    public GameEngine() {
        this.gameState = GameState.getInstance();
        this.commandParser = new CommandParser();
    }
    public String processCommand(Player player, String input) {
        String[] parts = commandParser.parse(input);
        if (parts.length == 0) {
            return "Please enter a valid command";
        }
        String command = parts[0].toLowerCase();
        String argument = parts.length > 1 ? parts[1] : "";
        return switch (command) {
            case "look" -> look(player);
            case "go" -> go(player, argument);
            case "take" -> take(player, argument);
            case "inventory", "inv" -> inventory(player);
            case "talk" -> talk(player, argument);
            case "say" -> say(player, argument);
            case "help" -> help();
            case "quit" -> quit(player);
            default -> "\n" + "Unknown command, type 'help' to view available commands.";
        };
    }
    private String look(Player player) {
        Room room = player.getCurrentRoom();
        StringBuilder sb = new StringBuilder();
        sb.append("You are at ").append(room.getName()).append("\n");
        sb.append(room.getDescription()).append("\n\n");
        sb.append("Exit: ");
        room.getExits().keySet().forEach(dir -> sb.append(dir).append(" "));
        sb.append("\n");
        if (!room.getItems().isEmpty()) {
            sb.append("You see: ");
            room.getItems().forEach(item -> sb.append(item.getName()).append(" "));
            sb.append("\n");
        }
        if (!room.getNpcs().isEmpty()) {
            sb.append("Here is: ");
            room.getNpcs().forEach(npc -> sb.append(npc.getName()).append(" "));
            sb.append("\n");
        }
        long otherPlayers = room.getPlayers().stream().filter(p -> !p.equals(player)).count();
        if (otherPlayers > 0) {
            sb.append("Left ").append(otherPlayers).append(" players here\n");
        }
        return sb.toString();
    }
    private String go(Player player, String direction) {
        if (direction.isEmpty()) {
            return "\n" + "Where do you want to go? Usage: go [direction]";
        }
        Room currentRoom = player.getCurrentRoom();
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            return "\n" + "There is no road in that direction.";
        }
        if (nextRoom.isLocked()) {
            if (player.hasItem(nextRoom.getKeyId())) {
                nextRoom.setLocked(false);
                currentRoom.notifyObservers(player.getName() + " use key open " + direction + " door");
            } else {
                return "That door is locked, you need a key";
            }
        }
        currentRoom.removePlayer(player);
        nextRoom.addPlayer(player);
        player.setCurrentRoom(nextRoom);
        if (gameState.checkWinCondition(player)) {
            gameState.setGameRunning(false);
            return "Congratulations! You've found the legendary treasure! You win the game! ";
        }
        return look(player);
    }
    private String take(Player player, String itemName) {
        if (itemName.isEmpty()) {
            return "\n" + "What would you like to take? Usage: take [item name]";
        }
        Room room = player.getCurrentRoom();
        Optional<Item> itemOpt = room.getItems().stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst();
        if (itemOpt.isEmpty()) {
            return "Here is no " + itemName;
        }
        Item item = itemOpt.get();
        if (player.pickUpItem(item)) {
            room.removeItem(item);
            room.notifyObservers(player.getName() + " pick up " + item.getName());
            return "You pick up " + item.getName();
        } else {
            return "You cannot pick up " + item.getName();
        }
    }
    private String inventory(Player player) {
        if (player.getInventory().isEmpty()) {
            return "Your inventory is empty";
        }
        StringBuilder sb = new StringBuilder("Your Inventory:\n");
        player.getInventory().forEach(item -> {
            sb.append("- ").append(item.getName());
            if (item.isKey()) {
                sb.append(" (key)");
            }
            sb.append(": ").append(item.getDescription()).append("\n");
        });
        return sb.toString();
    }
    private String talk(Player player, String npcName) {
        if (npcName.isEmpty()) {
            return "Who do you want to talk to? Usage: talk [NPC name]";
        }
        Room room = player.getCurrentRoom();
        Optional<NPC> npcOpt = room.getNpcs().stream()
                .filter(npc -> npc.getName().equalsIgnoreCase(npcName))
                .findFirst();

        if (npcOpt.isEmpty()) {
            return "Here is no " + npcName;
        }
        return npcOpt.get().getName() + " say: " + npcOpt.get().getDialogue();
    }
    private String say(Player player, String message) {
        if (message.isEmpty()) {
            return "What do you want to say? Usage: say [message]";
        }

        Room room = player.getCurrentRoom();
        room.notifyObservers(player.getName() + " say: " + message);
        return "You say: " + message;
    }
    private String help() {
        return """
                Valid commands:
                look - View current room information
                go [direction] - Move in the specified direction (e.g., go north)
                take [item name] - Pick up an item
                inventory/inv - View inventory
                talk [NPC name] - Talk to NPC
                say [text] - Talk to other players in the room
                help - Show help
                quit - Exit the game
                """;
    }
    private String quit(Player player) {
        Room room = player.getCurrentRoom();
        room.removePlayer(player);
        gameState.getPlayers().remove(player);
        return "You exit the game";
    }
}