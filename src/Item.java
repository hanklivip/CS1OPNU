package com.cs1opnu.adventure.model;
public class Item extends GameObject {
    private boolean isPickable;
    private boolean isKey;
    public Item(String id, String name, String description, boolean isPickable, boolean isKey) {
        super(id, name, description);
        this.isPickable = isPickable;
        this.isKey = isKey;
    }
    public boolean isPickable() { return isPickable; }
    public boolean isKey() { return isKey; }
}