#!/bin/bash

javac -verbose -cp lib/GoodSkyGames_v1.3.jar -d target  src/game/Game.java
cp lib/GoodSkyGames_v1.3.jar target
java -cp target game.Game

