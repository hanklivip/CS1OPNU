package com.cs1opnu.adventure.model;
import com.cs1opnu.adventure.observer.Observer;
import java.util.ArrayList;
import java.util.List;
public class Player extends GameObject implements Observer {
    private Room currentRoom;
    private List<Item> inventory;
    private List<String> notifications;
    public Player(String id, String name) {
        super(id, name, "Player " + name);
        this.inventory = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }
    public boolean pickUpItem(Item item) {
        if (item.isPickable()) {
            inventory.add(item);
            return true;
        }
        return false;
    }
    public boolean dropItem(Item item) {
        return inventory.remove(item);
    }
    public boolean hasItem(String itemId) {
        return inventory.stream().anyMatch(item -> item.getId().equals(itemId));
    }
    @Override
    public void update(String message) {
        notifications.add(message);
    }
    public List<String> getAndClearNotifications() {
        List<String> copy = new ArrayList<>(notifications);
        notifications.clear();
        return copy;
    }
    public Room getCurrentRoom() { return currentRoom; }
    public void setCurrentRoom(Room currentRoom) { this.currentRoom = currentRoom; }
    public List<Item> getInventory() { return inventory; }
}