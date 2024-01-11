package com.shpp.p2p.cs.sburlachenko.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * Part 5. Additions
 */
public class Algorism extends TextProgram {
    public void run() {
        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first number:  ");
            String n2 = readLine("Enter second number: ");
            println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
            println();
        }
    }

    /**
     * Given two string representations of non-negative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private String addNumericStrings(String n1, String n2) {
        return Long.toString(Long.parseLong(n1.trim()) + Long.parseLong(n2.trim())); // one string solution :)
    }
}
