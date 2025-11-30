#!/bin/bash

javac -verbose -d target  src/fractalfinder/FractalFinder.java src/fractalfinder/Song.java
java -cp target fractalfinder.FractalFinder

