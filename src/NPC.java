package com.cs1opnu.adventure.model;
public class NPC extends GameObject {
    private String dialogue;
    public NPC(String id, String name, String description, String dialogue) {
        super(id, name, description);
        this.dialogue = dialogue;
    }
    public String getDialogue() { return dialogue; }
}