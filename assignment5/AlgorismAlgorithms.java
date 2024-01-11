package com.shpp.p2p.cs.sburlachenko.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * The program adds two numbers. Even if these numbers are too big.
 */
public class AlgorismAlgorithms extends TextProgram {
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
        if (n1.length() != n2.length()) {    // if the length of the numbers is not equal, then two options are possible
            if (n1.length() > n2.length()) {
                StringBuilder n2Builder = new StringBuilder(n2);
                for (int j = n1.length() - n2Builder.length(); j > 0; j--) {
                    n2Builder.insert(0, 0); // add zeros to the beginning until the length is equal
                }
                n2 = n2Builder.toString();
            } else {  // if the second is longer than the first
                StringBuilder n1Builder = new StringBuilder(n1);
                for (int j = n2.length() - n1Builder.length(); j > 0; j--) {
                    n1Builder.insert(0, 0); // add zeros to the beginning until the length is equal
                }
                n1 = n1Builder.toString();
            }
        }
        char[] a = reverse(n1).toCharArray();
        char[] b = reverse(n2).toCharArray();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        int y = 0;
        while (i < a.length) {
            int x = (Integer.parseInt(a[i] + "") + Integer.parseInt(b[i] + "") + y);
            String s = x + "";
            if (s.length() > 1 && i < a.length - 1) {  // if the sum of the digits is two-digit and there are more digits ahead
                x = Integer.parseInt(s.toCharArray()[1] + ""); // second digit
                y = Integer.parseInt(s.toCharArray()[0] + ""); // first digit(1)
            } else {
                y = 0; // single digit (first digit(0))
            }
            sb.insert(0, x); // add the resulting digit (number) to the beginning
            i++;  // next digit
        }
        return sb.toString();
    }

    /**
     * @param s number
     * @return revers number
     */
    public static String reverse(String s) {
        StringBuilder sb = new StringBuilder();
        int j = s.length() - 1;
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(j));
            j--;
        }
        return sb.toString();
    }
}
