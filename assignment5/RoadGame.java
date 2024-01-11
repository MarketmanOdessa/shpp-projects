package com.shpp.p2p.cs.sburlachenko.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The idea of the game is to make a word from the letters
 * that you see on the license plate of a passing car and in the same order in which they are located on it.
 */
public class RoadGame extends TextProgram {
    List<String> list = new ArrayList<>();

    public void run() {
        try {   // read all lines from the file and write them to list
            BufferedReader br = new BufferedReader(new FileReader("en-dictionary.txt"));
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                list.add(line);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true) {  // read three letters and display words
            String letters = readLine("Enter a three letters: ");
            println("  Words: " + words(letters.toLowerCase()));
        }
    }

    /**
     * @param letters: 3 letters
     * @return words
     */
    private List<String> words(String letters) {
        List<String> result = new ArrayList<>();
        // split the string into three characters
        char c0 = letters.charAt(0);
        char c1 = letters.charAt(1);
        char c2 = letters.charAt(2);
        for (String s : list) {  // check every word
            int i0 = s.indexOf(c0); // if the word has the first character
            int i1 = s.indexOf(c1, i0 + 1); // and after it there is a second character
            int i2 = s.indexOf(c2, i1 + 1); // and after the second there is a third
            if (i0 >= 0 && i1 > i0 && i2 > i1) { // and they are in the right order
                result.add(s);  // then add the word to the list of results
            }
        }
        return result;
    }
}

