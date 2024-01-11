package com.shpp.p2p.cs.sburlachenko.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * The program counts the number of syllables in a word
 */
public class SyllableCounting extends TextProgram {
    public void run() {
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */
        while (true) {
            String word = readLine("Enter a single word: ");
            println("  Syllable count: " + syllablesInWord(word));
        }
    }

    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    private int syllablesInWord(String word) {
        int numSyllables = 0;
        String upperCaseWord = word.toUpperCase();  //Set everything to upper case
        for (int i = 1; i < upperCaseWord.length() - 1; i++) {  //The loop will run from 1 to the character before the last
            char ch = upperCaseWord.charAt(i);
            char c = (upperCaseWord.charAt(i - 1));
            //Only adds if the char is in the index, and if there is no other letter in the index fore i
            if ("AEIOUY".indexOf(ch) >= 0 && "AEIOUY".indexOf(c) == -1) {
                numSyllables++;
            }
        }
        char first = upperCaseWord.charAt(0);  //Check the first character
        char last = upperCaseWord.charAt(upperCaseWord.length() - 1);  //Check the last character
        if ("AIOUY".indexOf(last) >= 0) {   //Add if the last char is not 'E'
            numSyllables++;
        }
        if ("AEIOUY".indexOf(first) >= 0) {  //Add if the first character is in the index
            numSyllables++;
        }
        if (numSyllables <= 0) {  //There must be at least one syllable
            numSyllables = 1;
        }
        return numSyllables;
    }
}


