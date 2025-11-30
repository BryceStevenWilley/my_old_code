package game;

import goodsky.*;
import java.awt.Color;
import javax.management.timer.Timer;

/* Game Object */
public class Game {
  GameObject clicker;
  GameObject character;
  GameObject enemy1;
  GameObject enemy2;
  GameObject missle = null;
  GameObject missle3 = null;
  GameObject missle5 = null;
  double respawn1x[] = new double[100];
  double respawn1y[] = new double[100];
  double respawn2x[] = new double[100];
  double respawn2y[] = new double[100];
  int points;
  Integer pointsObj;

  /* Write the game code below in the main method */
  void main() {
    // Start the Library. You must call this command.
    gs.start();

    // -------------------Create Screen-----------------//
    gs.setScreenSize(800, 600);
    gs.setScreenTitle("Space Wars!");
    gs.setBackgroundColor(Color.black);
    points = 0;
    pointsObj = null;
    boolean gameTypeChosen = false;
    short gameType = 0;
    clicker = new GameObject(gs.TRIANGLE, 20, Color.green);
    clicker.x = 300;
    clicker.y = 400;

    do {
      gs.sync();
      gs.text("Welcome to Galag-  I mean Space Wars!", 300, 20);

      gs.text("Choose a Game Type:", 300, 300);
      gs.text("Target Practice!", 300, 400);
      gs.text("Adventure Mode!", 300, 500);
      if (gs.getArrowKey(1))
        clicker.y = 400;
      if (gs.getArrowKey(3))
        clicker.y = 500;
      if (gs.getKey('q')) {
        if (clicker.y == 400)
          gameType = 1;
        if (clicker.y == 500)
          ;
        gameType = 2;
        gameTypeChosen = true;
      }
    } while (!gameTypeChosen);
    clicker.destroy();
    clicker = null;

    targetPractice();
  }

  void targetPractice() {

    // ---------------------Create Characters--------------//
    character = new GameObject("galaga-large.jpg");
    character.scale(.15, .15);
    character.x = 300;
    character.y = 540;
    int characterSpeedX = 0;
    int characterSpeedY = 0;
    boolean characterWall = false;

    enemy1 = new GameObject("enemy.jpg");
    enemy1.scale(.10, .10);
    enemy1.x = 300;
    enemy1.y = 200;
    for (int i = 0; i < 100; i++) {
      respawn1x[i] = Math.random() * 800;
      respawn1y[i] = Math.random() * 400;
    }
    int respawnPosition1 = 0;
    enemy2 = new GameObject("enemy.jpg");
    enemy2.scale(.10, .10);
    enemy2.x = 100;
    enemy2.y = 200;
    for (int i = 0; i < 100; i++) {
      respawn2x[i] = Math.random() * 800;
      respawn2y[i] = Math.random() * 400;
    }
    int respawnPosition2 = 0;
    // --------Create Weapon Objects--------//
    final double missleSpeed = 7;
    long lastclick = System.currentTimeMillis();
    long keydelay = 250; // wait 250 miliseconds

    /* Main Game Loop */

    while (gs.gameloop()) {
      gs.sync();

      /* Display Point Amount */
      pointsObj = new Integer(points);
      gs.text("Points: ", 200, 20);
      gs.text(pointsObj.toString(), 240, 20);

      // ---------Movement Commands--------//
      enemy1.y++;
      enemy2.y++;
      if (enemy1.y > 600) {
        enemy1.destroy();
      }
      if (enemy2.y < 600) {
        enemy2.destroy();
      }
      if (gs.getArrowKey(0) || gs.getArrowKey(1) || gs.getArrowKey(2) || gs.getArrowKey(3)) {
        if (gs.getArrowKey(0)) {
          characterSpeedX = -5;
          characterSpeedY = 0;
        }
        if (gs.getArrowKey(2)) {
          characterSpeedX = 5;
          characterSpeedY = 0;
        }
        /*
         * if (gs.getArrowKey(1)) { \
         * characterSpeedY = -5; commented to restrict y-movement; expansion later?
         * characterSpeedX = 0;
         * }
         * if (gs.getArrowKey(3)) { |
         * characterSpeedY = 5; |
         * characterSpeedX = 0; /
         * }
         */
      } else {
        characterSpeedX = 0;
        characterSpeedY = 0;
      }

      // ---Moving the object---------//
      character.x += characterSpeedX;
      // character.y += characterSpeedY; commented to restrict y-movement; expansion
      // later?

      // -----------Setting object Limits--------//
      if (character.x < 0) {
        character.x = 0;
      }
      if (character.x > 800) {
        character.x = 800;
      }
      if (character.y > 600) {
        character.y = 600;
      }
      if (character.y < 0) {
        character.y = 0;
      }

      // --------Missle Systems------------//
      // Get the delay since the last missile
      long delay = System.currentTimeMillis() - lastclick;

      // Missle 1
      if (delay > keydelay && missle == null && gs.getKey('z')) {
        missle = new GameObject(gs.SQUARE, 3, Color.red);
        missle.scale(1.0, 8.0);
        missle.x = character.x;
        missle.y = character.y - 20;

        // Store the last click so you can keep track of the delay
        delay = 0;
        lastclick = System.currentTimeMillis();
      }

      if (missle != null) {
        missle.y -= missleSpeed;
        if (missle.y < 0) {
          missle.destroy();
          missle = null;
        }
      }
      // Missile 1 hit
      if (missle != null && (missle.isOverlap(enemy1, 1) || missle.isOverlap(enemy2, 1))) // when missile hits enemy1
      {
        if (missle.isOverlap(enemy1, 1)) {
          enemy1.destroy();
          enemy1 = null;
          enemy1 = new GameObject("enemy.jpg");
          enemy1.scale(.10, .10);
          if (respawnPosition1 > 99)
            respawnPosition1 -= 100;
          enemy1.x = respawn1x[respawnPosition1];
          enemy1.y = respawn2y[respawnPosition1];
          respawnPosition1++;
        }
        if (missle.isOverlap(enemy2, 1)) {
          enemy2.destroy();
          enemy2 = null;
          enemy2 = new GameObject("enemy.jpg");
          enemy2.scale(.10, .10);
          if (respawnPosition2 > 99)
            respawnPosition2 -= 100;
          enemy2.x = respawn2x[respawnPosition2];
          enemy2.y = respawn2y[respawnPosition2];
          respawnPosition2++;
        }
        missle.destroy();
        missle = null;
        points += 10;
      }

      // Missle 3
      if (delay > keydelay && missle3 == null && gs.getKey('z') && missle != null) {
        missle3 = new GameObject(gs.SQUARE, 3, Color.red);
        missle3.scale(1.0, 8.0);
        missle3.x = character.x;
        missle3.y = character.y - 20;

        // Store the last click so you can keep track of the delay
        delay = 0;
        lastclick = System.currentTimeMillis();
      }

      if (missle3 != null) {
        missle3.y -= missleSpeed;
        if (missle3.y < 0) {
          missle3.destroy();
          missle3 = null;
        }
      }
      if (missle3 != null && (missle3.isOverlap(enemy1, 1) || missle3.isOverlap(enemy2, 1))) // when missile hits enemy1
      {
        if (missle3.isOverlap(enemy1, 1)) {
          enemy1.destroy();
          enemy1 = null;
          enemy1 = new GameObject("enemy.jpg");
          enemy1.scale(.10, .10);
          if (respawnPosition1 > 99)
            respawnPosition1 -= 100;
          enemy1.x = respawn1x[respawnPosition1];
          enemy1.y = respawn2y[respawnPosition1];
          respawnPosition1++;
        }
        if (missle3.isOverlap(enemy2, 1)) {
          enemy2.destroy();
          enemy2 = null;
          enemy2 = new GameObject("enemy.jpg");
          enemy2.scale(.10, .10);
          if (respawnPosition2 > 99)
            respawnPosition2 -= 100;
          enemy2.x = respawn2x[respawnPosition2];
          enemy2.y = respawn2y[respawnPosition2];
          respawnPosition2++;
        }
        missle3.destroy();
        missle3 = null;
        points += 10;
      }
      // Missle 5;
      if (delay > keydelay && missle5 == null && gs.getKey('z') && missle != null && missle3 != null) {
        missle5 = new GameObject(gs.SQUARE, 3, Color.red);
        missle5.scale(1.0, 8.0);
        missle5.x = character.x;
        missle5.y = character.y - 20;

        // Store the last click so you can keep track of the delay
        delay = 0;
        lastclick = System.currentTimeMillis();
      }

      if (missle5 != null) {
        missle5.y -= missleSpeed;
        if (missle5.y < 0) {
          missle5.destroy();
          missle5 = null;
        }
      }

      if (missle5 != null && (missle5.isOverlap(enemy1, 1) || missle5.isOverlap(enemy2, 1))) // when missile hits enemy1
      {
        if (missle5.isOverlap(enemy1, 1)) {
          enemy1.destroy();
          enemy1 = null;
          enemy1 = new GameObject("enemy.jpg");
          enemy1.scale(.10, .10);
          if (respawnPosition1 > 99)
            respawnPosition1 -= 100;
          enemy1.x = respawn1x[respawnPosition1];
          enemy1.y = respawn2y[respawnPosition1];
          respawnPosition1++;
        }
        if (missle5.isOverlap(enemy2, 1)) {
          enemy2.destroy();
          enemy2 = null;
          enemy2 = new GameObject("enemy.jpg");
          enemy2.scale(.10, .10);
          if (respawnPosition2 > 99)
            respawnPosition2 -= 100;
          enemy2.x = respawn2x[respawnPosition2];
          enemy2.y = respawn2y[respawnPosition2];
          respawnPosition2++;
        }
        missle5.destroy();
        missle5 = null;
        points += 10;
      }

    }
  }

  /* This is the main entry point. All it does is start the game */
  public static void main(String[] args) {
    Game gm = new Game();
    gm.main();
  }
}
