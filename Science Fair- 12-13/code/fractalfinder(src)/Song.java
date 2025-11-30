package fractalfinder;

import java.util.ArrayList;

public class Song {
    private ArrayList song;
    private int notes = 0; // the number of notes of the song
    private int beat = 0; // The number of beats, in the song, or the length
    private ArrayList intervals;
    private int totalIntervals = 0;

    public Song(int y) {
        System.out.println("Making a new Song!");
        notes = 0;
        song = new ArrayList(y);
        intervals = new ArrayList(y * 2);
    }

    public Song(Song s) {
        System.out.println("Calling a Song Copy Constructor");
        song = (ArrayList) s.song.clone();
        notes = s.notes;
        beat = s.beat;
        intervals = (ArrayList) s.intervals.clone();
        totalIntervals = s.totalIntervals;
    }

    @Override
    public String toString() {
        String str = "[";
        for (int i = 0; i < notes; i++)
            str += song.get(i) + ", ";
        str += "]";
        return str;
    }

    public void writeNote(int place, char n, int oct) {
        System.out.println("Adding a new Note to the song.");
        Note insert = new Note(n, oct);
        song.add(place, insert);
        notes++;

    }

    public void writeNote(int place, char n, char sf, int oct) {
        System.out.println("Adding a new Note to the song.");
        Note insert = new Note(n, sf, oct);
        song.add(place, insert);
        notes++;
    }

    public void writeNote(int place, char n, char sf, int oct, double ori) {
        System.out.println("Adding a new Note to the song.");
        Note insert = new Note(n, sf, oct, ori);
        song.add(place, insert);
        notes++;
    }

    public void editNote(int place, double ori) {
        System.out.println("Editing an established note/ adding orgin.");
        String name = getNoteName(place);
        char n = name.charAt(0);
        char sf = name.charAt(1);
        int oct = (int) name.charAt(2) - 48;
        Note plus = new Note(n, sf, oct, ori);
        song.set(place, plus);
    }

    public void deleteNote(int place) {
        System.out.println("Deleting Note at " + place + ".");
        song.remove(place);
        notes--;
        // change places?
    }

    public void findIntervalsFirst() {
        Note lyric;
        Note lyric2;
        int sub1;
        int sub2;
        for (int i = 1; i < song.size(); i++) {
            lyric = (Note) song.get(i);
            lyric2 = (Note) song.get(i - 1);
            intervals.add(new Integer(Math.abs(lyric2.getDistance() - lyric.getDistance())));
        }

        for (int i = 0; i < intervals.size(); i++) {
            totalIntervals += ((Integer) intervals.get(i)).intValue();
        }
    }

    public void findIntervalsAnotherTime() {
        Note lyric;
        Note lyric2;
        int sub1;
        int sub2;
        for (int i = 1; i < song.size(); i++) {
            lyric = (Note) song.get(i);
            lyric2 = (Note) song.get(i - 1);
            intervals.set(i - 1, new Integer(Math.abs(lyric2.getDistance() - lyric.getDistance())));
        }
        int u = intervals.size() - 1;
        while (intervals.size() + 1 != song.size()) {
            intervals.remove(u);
            u--;
        }
        ;
        totalIntervals = 0;
        for (int i = 0; i < intervals.size(); i++) {
            totalIntervals += ((Integer) intervals.get(i)).intValue();
        }
    }

    public ArrayList getSong() {
        return song;
    }

    public ArrayList getIntervals() {
        return intervals;
    }

    public int getSingleInterval(int place) {
        return (Integer) intervals.get(place);
    }

    public int getNumberOfNotes() {
        return notes;
    }

    public int getTotalIntervals() {
        return totalIntervals;
    }

    public String getNoteName(int place) {
        Note lyric = (Note) song.get(place);
        return lyric.getName();
    }

    public int getNoteDistance(int place) {
        return ((Note) song.get(place)).getDistance();
    }

    public double getNoteHertz(int place) {
        Note lyric = (Note) song.get(place);
        return lyric.getHertz();
    }

    public double getNoteOrgin(int place) {
        Note lyric = (Note) song.get(place);
        return lyric.getOrgin();
    }

    public double getNoteLength(int place) {
        Note lyric = (Note) song.get(place);
        return lyric.getLength();
    }

}

class Note {
    private String name = ""; // The name of the note, ex.- C4, Bb3, etc...
    private int disFromC = 0; // the distance of the note from C0, or the lowest note on a piano.
    private double hertz = 0; // the frequency of the note
    private double orgin = 0; // the beat in the song on which the note is begun.
    private double length = 0; // the length of the note- half, whole, quater
                               // rests are defined as the period between the old
                               // note and the beginning of the new note
    int interval;

    @Override
    public String toString() {
        return "" + name + " ";
    }

    public Note() {
        System.out.println("Creating a default note!");
    }

    public Note(char n, int oct) {
        n = Character.toLowerCase(n);
        System.out.println("Creating a tuned note!");
        name = "" + n + oct;
        disFromC = oct * 12;
        switch (n) {
            case 'a':
                disFromC += 9;
                break;
            case 'b':
                disFromC += 11;
                break;
            case 'c':
                disFromC += 0;
                break;
            case 'd':
                disFromC += 2;
                break;
            case 'e':
                disFromC += 4;
                break;
            case 'f':
                disFromC += 5;
                break;
            case 'g':
                disFromC += 7;
                break;
        }
        hertz = 16.35117682 * Math.pow(1.059464, disFromC);
        length = 1;
    }

    public Note(char n, char sf, int oct) {
        n = Character.toLowerCase(n);
        System.out.println("Creating a tuned note!");
        name = "" + n + sf + oct;
        disFromC = (oct * 12);
        switch (n) {
            case 'a':
                disFromC += 9;
                break;
            case 'b':
                disFromC += 11;
                break;
            case 'c':
                disFromC += 0;
                break;
            case 'd':
                disFromC += 2;
                break;
            case 'e':
                disFromC += 4;
                break;
            case 'f':
                disFromC += 5;
                break;
            case 'g':
                disFromC += 7;
                break;
        }

        if (sf == '#') // sf= sharp flat
            disFromC += 1;
        else if (sf == 'b')
            disFromC -= 1;
        hertz = 16.35117682 * Math.pow(1.059464, disFromC);
        length = 1;
    }

    public Note(char n, int oct, double ori) {
        Note temp = new Note(n, oct);
        name = temp.getName();
        disFromC = temp.getDistance();
        hertz = temp.getHertz();
        orgin = ori;
    }

    public Note(char n, char sf, int oct, double ori) {
        n = Character.toLowerCase(n);
        System.out.println("Creating a fully constructed note!");
        disFromC = oct * 12;
        switch (n) {
            case 'a':
                disFromC += 9;
                break;
            case 'b':
                disFromC += 11;
                break;
            case 'c':
                disFromC += 0;
                break;
            case 'd':
                disFromC += 2;
                break;
            case 'e':
                disFromC += 4;
                break;
            case 'f':
                disFromC += 5;
                break;
            case 'g':
                disFromC += 7;
                break;
        }
        if (sf == '#')
            disFromC += 1;

        else if (sf == 'b')
            disFromC -= 1;
        name = "" + n + sf + oct;
        hertz = 16.35117682 * Math.pow(1.059464, disFromC);
        orgin = ori;

    }

    public Note(char n, int oct, double ori, double len) {
        Note temp = new Note(n, oct, ori);
        name = temp.getName();
        disFromC = temp.getDistance();
        hertz = temp.getHertz();
        orgin = temp.getOrgin();
        length = len;
    }

    public Note(char n, char sf, int oct, double ori, double len) {
        Note temp = new Note(n, oct, ori);
        name = temp.getName();
        disFromC = temp.getDistance();
        hertz = temp.getHertz();
        orgin = temp.getOrgin();
        length = len;
    }

    public void setName(char n, char sf, int oct) {
        name = "" + n + sf + oct;
    }

    public String getName() {
        return name;
    }

    public int getDistance() {
        return disFromC;
    }

    public double getHertz() {
        return hertz;
    }

    public double getOrgin() {
        return orgin;
    }

    public double getLength() {
        return length;
    }

}
