# CS1OPNU
# Module Code: CS1OPNU
# Assignment report Title: Multi-Player Text Adventure Game Project
# Student Number: 202483710017/33804435
# Actual hrs spent for the assignment: 15
# Which Artificial Intelligence tools used: DouBao

---

## Implementation Highlights
This coursework develops a complete **multi-player text-based adventure game** using pure Java, fully complying with module requirements including object-oriented programming, layered software design, and three mandatory design patterns: Singleton, Observer and Factory. The application enables multiple players to explore interconnected game rooms, collect items, interact with NPCs, communicate with each other, unlock locked rooms with keys, and achieve the predefined win condition by finding the hidden treasure.

The project adopts a clear layered architecture, separating user interface, business logic service, object factory, observer event system, global singleton state and data model. Inheritance is implemented through a parent abstract class `GameObject`, which is extended by `Room`, `Player`, `Item` and `NPC`, demonstrating proper use of inheritance and composition. Code hygiene is strictly followed with meaningful package structure, consistent naming conventions, detailed inline comments, clear indentation and well-organised file layout.

The Singleton pattern controls the global game state to maintain one unified game world during runtime. The Observer pattern triggers automatic notifications when players enter or leave rooms, pick up items or send public messages to other participants. The Factory pattern encapsulates the creation of all game entities and generates unique IDs for every game object. Unit tests are provided to verify factory creation logic and core game behaviours. AI tools were utilised to support the design of layered structure and observer implementation, helping to build a scalable and maintainable system while meeting all assessment rules.

## Introduction
This project constructs a console-driven multi-player text adventure game. Key implemented features include a fully connected game world with multiple themed rooms, collectable items and functional key items, interactive NPCs with custom dialogue, simultaneous multi-player support through multy threads, player movement and inventory systems, room locking and unlocking mechanics, public in-game chat, and a clear treasure-hunting win condition. The development follows object-oriented principles, layered software architecture and standard code organisation. 
## Requirements
Implementation requirements sorted by priority:
1. Design and build multiple interconnected game rooms with descriptions and directional exits.
2. Implement game items including normal collectable items and special key items.
3. Create non-player characters with readable dialogue interaction.
4. Support multiple players running simultaneously with independent command-line consoles.
5. Implement core player actions: move between rooms, pick up items, view inventory, talk to NPCs and send public messages.
6. Apply the Singleton pattern to manage global game world runtime state.
7. Apply the Observer pattern to broadcast real-time game events to all players in the same room.
8. Apply the Factory pattern to centralise creation of rooms, players, items and NPCs with unique ID generation.
9. Use class inheritance by defining a base `GameObject` superclass for all game entities.
10. Organise the whole project into layered architecture and maintain professional code hygiene.
11. Develop unit tests to validate factory object creation and essential game logic.

## Design
### System Architecture Diagram
```mermaid
graph TD
    UI[UI Layer - Player Console]
    Service[Service Layer - Game Engine & Command Parser]
    Factory[Factory Layer - GameObject Factory]
    Observer[Observer Layer - Observer & Subject]
    Singleton[Singleton Layer - Game State]
    Model[Model Layer - Room Player Item NPC GameObject]

    UI --> Service
    Service --> Factory
    Service --> Singleton
    Service --> Observer
    Factory --> Model
    Observer --> Model
    Singleton --> Model
