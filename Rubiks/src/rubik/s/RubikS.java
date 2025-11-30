/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rubik.s;

import java.util.Scanner;

/**
 *
 * @author bryce
 */
public class RubikS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String str = "";
        ThreeByThreeReal first = new ThreeByThreeReal();
        first.dispSide("top");
        first.dispSide("front");
        first.dispSide("bottom");
        first.dispCube();
        str = input.nextLine();
        first.topClockwise();
        first.dispCube();
        str = input.nextLine();
        first.topCounterClockwise();
        first.dispCube();
        str = input.nextLine();
        first.bottomClockwise();
        first.dispCube();
        str = input.nextLine();
        first.bottomCounterClockwise();
        first.dispCube();
        str = input.nextLine();

    }
}

abstract class RubikImagine {
    abstract public void leftClockwise();

    abstract public void leftCounterClockwise();

    abstract public void rightClockwise();

    abstract public void rightCounterClockwise();

    abstract public void topClockwise();

    abstract public void topCounterClockwise();

    abstract public void bottomClockwise();

    abstract public void bottomCounterClockwise();

    abstract public void frontClockwise();

    abstract public void frontCounterClockwise();

    abstract public void backClockwise();

    abstract public void backCounterClockwise();

    abstract public void dispSide(String s);

    abstract public void dispCube();

    abstract public void resetCube();

}

class SpaceSavers extends ThreeByThreeReal {
    /*
     * This class is to save space for lengthy, repetitive code that can be entered
     * once.
     * 
     */
    public static void assignAll(int[][] side, int color) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                side[i][j] = color;

    }

}

class ThreeByThreeReal extends RubikImagine {
    /*
     * Different integers stand for different colors.
     * White=1
     * Red= 2
     * Green=3
     * Orange=4
     * Blue=5
     * Yellow=6
     */
    protected int[][] top;
    protected int[][] front;
    protected int[][] left;
    protected int[][] back;
    protected int[][] right;
    protected int[][] bottom;
    char c;

    public ThreeByThreeReal() {
        System.out.println("Creating new 3 by 3 Rubik's cube!");

        top = new int[3][3];
        SpaceSavers.assignAll(top, 1);

        front = new int[3][3];
        SpaceSavers.assignAll(front, 2);

        left = new int[3][3];
        SpaceSavers.assignAll(left, 3);

        back = new int[3][3];
        SpaceSavers.assignAll(back, 4);

        right = new int[3][3];
        SpaceSavers.assignAll(right, 5);

        bottom = new int[3][3];
        SpaceSavers.assignAll(bottom, 6);

    }

    /*
     * On the following turn methods, the sides are dealt with as such:
     * - the front, left, back, and right faces are viewed straight on
     * with the bottom facing down and the top up.
     * - The top is viewed the front face down.
     * - The bottom is viewed with the front face facing up, and the back
     * face facing down. All other faces (left and right) are facing
     * the same direction
     * 
     */
    public void leftClockwise() { // Turns the left face (set as green default) clockwise.
        System.out.println("Left Face Clockwise");
        int temp1 = top[0][0];
        int temp2 = top[1][0];
        int temp3 = top[2][0];
        int temp4 = left[0][0];
        int temp5 = left[0][1];
        for (int i = 0; i < 3; i++) {
            top[i][0] = back[2 - i][0];
            back[2 - i][0] = bottom[i][0];
            bottom[i][0] = front[i][0];
        }
        front[0][0] = temp1;
        front[1][0] = temp2;
        front[2][0] = temp3;

        left[0][0] = left[2][0];
        left[2][0] = left[2][2];
        left[2][2] = left[0][2];
        left[0][2] = temp4;

        left[0][1] = left[1][0];
        left[1][0] = left[2][1];
        left[2][1] = left[1][2];
        left[1][2] = temp5;

    }

    public void leftCounterClockwise() { // Turns the left face (set as green default) counterclockwise.
        System.out.println("Left Face Counter Clockwise");
        int temp1 = top[0][0];
        int temp2 = top[1][0];
        int temp3 = top[2][0];
        int temp4 = left[0][0];
        int temp5 = left[0][1];
        for (int i = 0; i < 3; i++) {
            top[i][0] = front[i][0];
            front[i][0] = bottom[i][0];
            bottom[i][0] = back[2 - i][0];
        }
        back[2][0] = temp1;
        back[1][0] = temp2;
        back[0][0] = temp3;

        left[0][0] = left[0][2];
        left[0][2] = left[2][2];
        left[2][2] = left[2][0];
        left[2][0] = temp4;

        left[0][1] = left[1][2];
        left[1][2] = left[2][1];
        left[2][1] = left[1][0];
        left[1][0] = temp5;

    }

    public void rightClockwise() { // Turns the right face (set as blue default) clockwise, which is similar to
                                   // turning the left face counterclockwise.

        System.out.println("Right Face Clockwise");
        int temp1 = top[0][2];
        int temp2 = top[1][2];
        int temp3 = top[2][2];
        int temp4 = right[0][0];
        int temp5 = right[0][1];
        for (int i = 0; i < 3; i++) {
            top[i][2] = front[i][2];
            front[i][2] = bottom[i][2];
            bottom[i][2] = back[2 - i][2];
        }
        back[2][2] = temp1;
        back[1][2] = temp2;
        back[0][2] = temp3;

        right[0][0] = right[0][2];
        right[0][2] = right[2][2];
        right[2][2] = right[2][0];
        right[2][0] = temp4;

        right[0][1] = right[1][2];
        right[1][2] = right[2][1];
        right[2][1] = right[1][0];
        right[1][0] = temp5;

    }

    public void rightCounterClockwise() { // Turns the right face (set as blue default) counterclockwise, which is
                                          // similar
                                          // to turning to left face clockwise.
        System.out.println("Right Face Counter Clockwise");
        int temp1 = top[0][2];
        int temp2 = top[1][2];
        int temp3 = top[2][2];
        int temp4 = right[0][0];
        int temp5 = right[0][1];
        for (int i = 0; i < 3; i++) {
            top[i][2] = back[2 - i][2];
            back[2 - i][2] = bottom[i][2];
            bottom[i][2] = front[i][2];
        }
        front[0][2] = temp1;
        front[1][2] = temp2;
        front[2][2] = temp3;

        right[0][0] = right[2][0];
        right[2][0] = right[2][2];
        right[2][2] = right[0][2];
        right[0][2] = temp4;

        right[0][1] = right[1][0];
        right[1][0] = right[2][1];
        right[2][1] = right[1][2];
        right[1][2] = temp5;

    }

    public void topClockwise() { // Turn the top face (set as the white default) clockwise.
        System.out.println("Top Face Clockwise");
        int temp1 = front[0][0];
        int temp2 = front[0][1];
        int temp3 = front[0][2];
        int temp5 = top[0][0];
        int temp6 = top[0][1];
        for (int i = 0; i < 3; i++) {
            front[0][i] = right[0][i];
            right[0][i] = back[0][i];
            back[0][i] = left[0][1];
        }
        left[0][0] = temp1;
        left[0][1] = temp2;
        left[0][2] = temp2;

        top[0][0] = top[2][0];
        top[2][0] = top[2][2];
        top[2][2] = top[0][2];
        top[0][2] = temp5;

        top[0][1] = top[1][0];
        top[1][0] = top[2][1];
        top[2][1] = top[1][2];
        top[1][2] = temp6;
    }

    public void topCounterClockwise() {
        System.out.println("Top Face Counter Clockwise");
        int temp1 = front[0][0];
        int temp2 = front[0][1];
        int temp3 = front[0][2];
        int temp5 = top[0][0];
        int temp6 = top[0][1];
        for (int i = 0; i < 3; i++) {
            front[0][i] = left[0][i];
            left[0][i] = back[0][i];
            back[0][i] = right[0][1];
        }
        right[0][0] = temp1;
        right[0][1] = temp2;
        right[0][2] = temp3;

        top[0][0] = top[0][2];
        top[0][2] = top[2][2];
        top[2][2] = top[2][0];
        top[2][0] = temp5;

        top[0][1] = top[1][2];
        top[1][2] = top[2][1];
        top[2][1] = top[1][0];
        top[1][0] = temp6;
    }

    public void bottomClockwise() {
        System.out.println("Bottom Face Clockwise");
        int temp1 = front[2][0];
        int temp2 = front[2][1];
        int temp3 = front[2][2];
        int temp4 = bottom[0][0];
        int temp5 = bottom[0][1];
        for (int i = 0; i < 3; i++) {
            front[2][i] = left[2][i];
            left[2][i] = back[2][i];
            back[2][i] = right[2][i];
        }
        right[2][0] = temp1;
        right[2][1] = temp2;
        right[2][2] = temp3;

        bottom[0][0] = bottom[2][0];
        bottom[2][0] = bottom[2][2];
        bottom[2][2] = bottom[0][2];
        bottom[0][2] = temp4;

        bottom[0][1] = bottom[1][0];
        bottom[1][0] = bottom[2][1];
        bottom[2][1] = bottom[1][2];
        bottom[1][2] = temp5;
    }

    public void bottomCounterClockwise() {
        System.out.println("Bottom Face Counter Clockwise");
        int temp1 = front[2][0];
        int temp2 = front[2][1];
        int temp3 = front[2][2];
        int temp4 = bottom[0][0];
        int temp5 = bottom[0][1];
        for (int i = 0; i < 3; i++) {
            front[2][i] = right[2][i];
            right[2][i] = back[2][i];
            back[2][i] = left[2][i];
        }
        left[2][0] = temp1;
        left[2][1] = temp2;
        left[2][2] = temp3;

        bottom[0][0] = bottom[0][2];
        bottom[0][2] = bottom[2][2];
        bottom[2][2] = bottom[2][0];
        bottom[2][0] = temp4;

        bottom[0][1] = bottom[1][2];
        bottom[1][2] = bottom[2][1];
        bottom[2][1] = bottom[1][0];
        bottom[1][0] = temp5;
    }

    public void frontClockwise() {
        System.out.println("Front Face Clockwise");
        int temp1 = top[2][0];
        int temp2 = top[2][1];
        int temp3 = top[2][2];
        int temp4 = 0;
        int temp5 = 0;
        for (int i = 0; i < 3; i++) {
            top[2][i] = left[i][2];
            left[i][2] = bottom[0][i];
            bottom[0][i] = right[i][0];
        }
        right[0][0] = temp1;

    }

    public void frontCounterClockwise() {
    }

    public void backClockwise() {
    }

    public void backCounterClockwise() {
    }

    @Override
    public void dispSide(String s) {
        int[][] side;
        System.out.println("Displaying " + s + " side \n");
        if (s.equals("bottom"))
            s = "down";
        c = s.charAt(0);
        switch (c) {
            case 't':
                side = top;
                break;
            case 'f':
                side = front;
                break;
            case 'l':
                side = left;
                break;
            case 'r':
                side = right;
                break;
            case 'b':
                side = back;
                break;
            case 'd':
                side = bottom;
                break;
            default:
                System.out.println("Input not understood.  Please try again.");
                side = new int[0][0];

        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(side[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void dispCube() {
        int i = 0;
        int j = 0;
        System.out.println("Displaying Entire Cube \n\n");

        while (i < 3) {
            System.out.println("      " + top[i][0] + " " + top[i][1] + " " + top[i][2]);
            i++;
        }

        for (int y = 0; y < 3; y++)
            System.out.println("" + left[y][0] + " " + left[y][1] + " " + left[y][2] +
                    " " + front[y][0] + " " + front[y][1] + " " + front[y][2] + " " + right[y][0] +
                    " " + right[y][1] + " " + right[y][2]);
        do {
            System.out.println("      " + bottom[j][0] + " " + bottom[j][1] + " " + bottom[j][2]);
            j++;
        } while (j < 3);
        for (int y = 0; y < 3; y++) {
            System.out.print("      ");
            for (int o = 0; o < 3; o++)
                System.out.print(back[2 - y][o] + " ");
            System.out.println();
        }
    }

    public void resetCube() {
        for (int i = 0; i < 3; i++)
            ;

    }

    public void mixCube() {
        int y = 0;
        for (int i = 0; i < 100; i++) {
            y = ((int) Math.ceil(Math.random() * 10));
            switch (y) {
                case 1:
                    this.leftClockwise();
                    break;
                case 2:
                    this.leftCounterClockwise();
                    break;
                case 3:
                    this.rightClockwise();
                    break;
                case 4:
                    this.rightCounterClockwise();
                    break;
                case 5:
                    this.topClockwise();
                    break;
                case 6:
                    this.topCounterClockwise();
                    break;
                case 7:
                    this.bottomClockwise();
                    break;
                case 8:
                    this.bottomCounterClockwise();
                    break;
                case 9:
                    this.frontClockwise();
                    break;
                case 10:
                    this.frontCounterClockwise();
                    break;
                case 11:
                    this.backClockwise();
                    break;
                case 12:
                    this.backCounterClockwise();
                    break;
            }
        }
    }
}