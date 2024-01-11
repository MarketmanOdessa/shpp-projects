package com.shpp.p2p.cs.sburlachenko.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This program parses csv
 */
public class CSVParser extends TextProgram {
    public void run() {
        String filename = "food-origins.csv";  // write any of your CSV file
        println(extractColumn(filename, 0));  // choose any column
        println(extractColumn(filename, 1));  // or add lines to display multiple columns at the same time
    }

    /**
     * @param line: one line from file
     * @return array of values from string
     */
    private ArrayList<String> fieldsIn(String line) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> list = new ArrayList<>();
        int flag = -1;  // character location  in/out quotes (-1/ 1)
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);  // take a character from a string
            if (ch == '"') {
                flag = -flag;  // change character location in/out quotes.
                continue;
            }
            if (ch == ',' && flag < 0) {  // comma outside quotes
                list.add(sb.toString());  // collect characters into a string and add to an array
                sb.setLength(0);  // clean
                continue;
            }
            sb.append(ch);  // add one character
        }
        list.add(sb.toString());  // collect characters into a string and add to an array
        return list;
    }

    /**
     * @param filename    file name
     * @param columnIndex column number
     * @return all values that are in the column
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> ar;  // temporary array to store values from one string
        ArrayList<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while (true) {
                String s = br.readLine();  // read one line from a file
                if (s == null) break;
                ar = fieldsIn(s);  // save all values from one row to a temporary array
                result.add(ar.get(columnIndex)); // add one value from desired column
            }
        } catch (IOException e) {  // FileNotFoundException extends IOException
            return null; // if file not found
        }
        return result;
    }
}
