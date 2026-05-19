package com.cs1opnu.adventure.singleton;
import com.cs1opnu.adventure.model.Player;
import com.cs1opnu.adventure.model.Room;
import java.util.ArrayList;
import java.util.List;
public class GameState {
    private static final GameState instance = new GameState();
    private List<Room> rooms;
    private List<Player> players;
    private Room startingRoom;
    private boolean isGameRunning;
    private String winConditionItemId; // 胜利条件：获得该物品
    private GameState() {
        this.rooms = new ArrayList<>();
        this.players = new ArrayList<>();
        this.isGameRunning = true;
    }
    public static GameState getInstance() {
        return instance;
    }
    public void addRoom(Room room) {
        rooms.add(room);
        if (startingRoom == null) {
            startingRoom = room;
        }
    }
    public void addPlayer(Player player) {
        players.add(player);
        player.setCurrentRoom(startingRoom);
        startingRoom.addPlayer(player);
    }
    public boolean checkWinCondition(Player player) {
        return player.hasItem(winConditionItemId);
    }
    public void reset() {
        rooms.clear();
        players.clear();
        startingRoom = null;
        isGameRunning = true;
        winConditionItemId = null;
    }
    public List<Room> getRooms() { return rooms; }
    public List<Player> getPlayers() { return players; }
    public Room getStartingRoom() { return startingRoom; }
    public void setStartingRoom(Room startingRoom) { this.startingRoom = startingRoom; }
    public boolean isGameRunning() { return isGameRunning; }
    public void setGameRunning(boolean gameRunning) { isGameRunning = gameRunning; }
    public String getWinConditionItemId() { return winConditionItemId; }
    public void setWinConditionItemId(String winConditionItemId) { this.winConditionItemId = winConditionItemId; }
}