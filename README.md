# SchoolGame

## General

A school project in 12th grade, making a game with the libGDX game engine. It uses the 16x16 dungeon tileset by 0x72 (https://0x72.itch.io/16x16-dungeon-tileset).

## Controls

In game, you can move the character by pressing W, A, S or D. If you want to skip a turn, press SPACE. If the player is adjecent to a monster, the player loses and the game ends, however, if you press Q before moving next to the monster, you can defeat it and recieve points. You might also encounter Iitems laying on the ground, those are simply consumed by stepping on them. You can press ESC any time to return to the Homescreen, but your points are lost if you do so.

## Enemies

### Bat
![bat](https://github.com/user-attachments/assets/ee33fd00-a8db-4524-ab43-89f898629ec9)

The bat always moves around in a circle.

### Orc
![orc](https://github.com/user-attachments/assets/4ee4a506-2532-4528-8bf4-98ffef73c4dc)

Orcs detect a player from up to 7 tiles away and move in their direction, as long as nothing is in their way.

### Fire
![elemental_fire](https://github.com/user-attachments/assets/06b80090-c533-41bc-a197-603f30f1b68e)

The Fire likes to go up and down the hallways, turning around when there is an obstacle.

### Small Demon
![imp](https://github.com/user-attachments/assets/c5353a3f-e32e-4047-bc35-6d7eee953745)

Currently, the small demon behaves like the bat. Suggestions are welcome.

### Dark Knight
![dark_knight](https://github.com/user-attachments/assets/f83d98fc-fa2e-4f38-8a32-df0815d4455b)

The dark knight currently has the most complex pathfinding, he uses the A*-Algorithm to calculate the shortest path to the player and allways chooses the next tile in the current shortest path.

## Items

### Coin
![coin](https://github.com/user-attachments/assets/d64e84b0-8965-424b-98ed-3c9a08819f93)

The coin gives you 1 point when collected.

### Silver Treasure
![silver_treasure](https://github.com/user-attachments/assets/56a6fd16-6d7f-40d0-9559-8ee022739d07)

The silver treasure gives you 5 points when collected.

### Gold Treasure
![gold_treasure](https://github.com/user-attachments/assets/84f38eba-8eda-400b-b15c-df3d55d07f56)

The gold treasure gives you 10 ponts when collected.

### Speed Potion
![speed_potion](https://github.com/user-attachments/assets/d26d89a6-78f9-4624-be06-1842555b88e4)

The speed potion increases your movment range to 3 tiles for a single turn. If you do not want its effects, you can skip it by pressing SPACE

## Playing

There are 3 different ways to play this game:

### IDE

Download the sourcecode, open it in an IDE (Intellij, Eclipse) and execute the main method in lwjgl3/src/main/java/io/github/simonreilich/lwjgl3/Lwjgl3Launcher.java

### JAR

From releases, download SchoolGame-1.0.0.jar and doubleclick it. For this option to work, you have to have Java installed and correctly configured.

### Construto Bundle

If you dont have Java on your machine, you can download SchoolGame-winX64.zip, unzip it, navigate to SchoolGame-winX64\SchoolGame-1.0.0-winX64\SchoolGame.exe and doubleclick it. This should work without installing any extra software, but only on Windows.
