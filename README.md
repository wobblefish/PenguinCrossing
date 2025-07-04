# Penguin Crossing

Penguin Crossing is a Frogger-style arcade game originally developed as a Java Applet for a school project, and later refactored to run as a standalone desktop Java application. You control a penguin, dodging enemy sprites ("baddies") to reach the finish line. The game features custom graphics and sound effects.

## Features
- Playable as a desktop Java application (no longer requires a browser or applet support)
- Arrow-key controls for movement
- Multiple enemy types with movement patterns
- Win and game-over screens, with sound effects and music
- All assets (sprites, music, SFX) are included in the `src/assets` directory

## Java Concepts Explored
This project demonstrates several core Java concepts and APIs:
- **Swing GUI:** The game interface is built using Swing components such as `JFrame`, `JLabel`, `JButton`, and layout managers. All game visuals and interactions are managed with Swing.
- **Threads for Animation:** Multiple `Thread` instances are used to animate enemy sprites independently, providing concurrent movement and real-time gameplay.
- **Event-Driven Programming:** Implements `KeyListener` for keyboard controls and `ActionListener` for button actions, enabling responsive user interaction.
- **Audio Management:** Uses `AudioClip` and file-based audio loading to play background music and sound effects.
- **Legacy Applet Support:** The codebase retains the ability to run as a Java Applet for historical reference, though the primary entry point is now the desktop application (`main` method).

## Controls
- **Arrow Keys:** Move the penguin up, down, left, or right

## Gameplay
- The goal is to move the penguin from the starting position to the finish line at the top of the screen.
- Avoid all enemy sprites ("baddies"). Colliding with any enemy ends the game.
- Reaching the finish line triggers a win screen and plays a victory sound.

## Assets
Assets are located in `src/assets/` and its subdirectories:
- **sprites/**: Contains penguin and enemy images
- **music/**: Background music tracks (`zen.wav`, `howweroll.wav`, `break1.wav`)
- **sfx/**: Sound effects for win/loss (`applause.wav`, `fail.wav`)
- **background.jpg, title.jpg, winpic.jpg**: Background and screen images


## Notes
- The codebase still contains legacy Applet code for reference, but the main entry point is now the `main` method.
- All game logic, UI, and asset management are handled in `PenguinCrossing.java`.


