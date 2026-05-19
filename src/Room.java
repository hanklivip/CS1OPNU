package com.cs1opnu.adventure.model;
import com.cs1opnu.adventure.observer.Observer;
import com.cs1opnu.adventure.observer.Subject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Room extends GameObject implements Subject {
    private Map<String, Room> exits; // 方向 -> 房间
    private List<Item> items;
    private List<Player> players;
    private List<NPC> npcs;
    private boolean isLocked;
    private String keyId; // 解锁所需物品ID
    public Room(String id, String name, String description) {
        super(id, name, description);
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
        this.players = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.isLocked = false;
    }
    public void addExit(String direction, Room room) {
        exits.put(direction.toLowerCase(), room);
    }
    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }
    public void addItem(Item item) {
        items.add(item);
        notifyObservers("You see one on the ground" + item.getName());
    }
    public boolean removeItem(Item item) {
        return items.remove(item);
    }
    public void addPlayer(Player player) {
        players.add(player);
        registerObserver(player);
        notifyObservers(player.getName() + " entered the room");
    }
    public void removePlayer(Player player) {
        players.remove(player);
        removeObserver(player);
        notifyObservers(player.getName() + " exit the room");
    }
    public void addNPC(NPC npc) {
        npcs.add(npc);
    }
    @Override
    public void registerObserver(Observer observer) {
    }
    @Override
    public void removeObserver(Observer observer) {
    }
    @Override
    public void notifyObservers(String message) {
        for (Player player : players) {
            player.update(message);
        }
    }
    public Map<String, Room> getExits() { return exits; }
    public List<Item> getItems() { return items; }
    public List<Player> getPlayers() { return players; }
    public List<NPC> getNpcs() { return npcs; }
    public boolean isLocked() { return isLocked; }
    public void setLocked(boolean locked) { isLocked = locked; }
    public String getKeyId() { return keyId; }
    public void setKeyId(String keyId) { this.keyId = keyId; }
}