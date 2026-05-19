package com.cs1opnu.adventure.factory;
import com.cs1opnu.adventure.model.Item;
import com.cs1opnu.adventure.model.NPC;
import com.cs1opnu.adventure.model.Player;
import com.cs1opnu.adventure.model.Room;
import java.util.UUID;
public class GameObjectFactory {
    private static String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
    public static Room createRoom(String name, String description) {
        String id = generateId();
        return new Room(id, name, description);
    }
    public static Item createItem(String name, String description, boolean isPickable, boolean isKey) {
        String id = generateId();
        return new Item(id, name, description, isPickable, isKey);
    }
    public static Player createPlayer(String name) {
        String id = generateId();
        return new Player(id, name);
    }
    public static NPC createNPC(String name, String description, String dialogue) {
        String id = generateId();
        return new NPC(id, name, description, dialogue);
    }
}