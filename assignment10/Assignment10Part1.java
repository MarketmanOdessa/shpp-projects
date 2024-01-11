package com.shpp.p2p.cs.sburlachenko.assignment10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 The first argument to be given to the programme is a mathematical expression.
 The other parameters are optional and look like (variable name = value).
 The program substitutes the variables into the expression and outputs the result of the expression itself.
 */

public class Assignment10Part1 {
    /**
     * Main method: substitutes variables in the formula and outputs the result.
     * @param args Formula and variables(optional).
     */
    public static void main(String[] args) {
        HashMap<String, Double> variables = new HashMap<>();
        String formula = args[0];
        System.out.println("Formula:     " + formula);
        for (int i = 1; i < args.length; i++) {        // read all the variables
            String str = args[i];  // temporary string to collect variables
            System.out.println("Variables " + i + ": " + args[i]);
            String[] st = str.split("=");   // divide by variable name and value
         try {
             variables.put(st[0], Double.parseDouble(st[1]));
         }
         catch (RuntimeException e) {
             System.out.println("Incorrect variable input (variable name = value)");
         }
        }
        System.out.println("Result:      " + calculate(formula, variables));
    }

    /**
     * @param formula   formula from the first parameter
     * @param variables variables from the other parameters (optional)
     * @return final result
     */
    public static double calculate(String formula, HashMap<String, Double> variables) {
        for (Map.Entry<String, Double> pair : variables.entrySet()) {  // overwriting with replacement of variables with their values
            formula = formula.replaceAll(pair.getKey(), pair.getValue().toString());
        }  //  replace the variable name with the value
        System.out.println("Expression:  " + formula);
        List<Symbol> symbols = parsing(formula); // parsed formula is saved in an array
        Buffer buffer = new Buffer(symbols); // save the array to the buffer
        return expression(buffer); // final result
    }

    public enum SymbolType {
        PLUS, MINUS, MUL, DIV, DEGREE, NUMBER, END;
    }

    /**
     * Сlass whose objects are symbols of a certain type
     */
    public static class Symbol {
        SymbolType type;
        String value;

        public Symbol(SymbolType type, String value) {     // if number
            this.type = type;
            this.value = value;
        }

        public Symbol(SymbolType type, Character value) {   // if char
            this.type = type;
            this.value = value.toString();
        }
    }

    /**
     * Сlass is responsible for traversing an array of characters and remembering its position
     */
    public static class Buffer {
        private int position;
        public List<Symbol> symbols;

        public Buffer(List<Symbol> symbols) {
            this.symbols = symbols;
        }

        public Symbol next() {
            return symbols.get(position++);
        }

        public void back() {
            position--;
        }
    }

    /**
     * method parses each character of the text
     *
     * @param text formula
     * @return arrey of objects Symbol
     */
    public static List<Symbol> parsing(String text) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        int position = 0;
        while (position < text.length()) {
            char ch = text.charAt(position); // take one char
            switch (ch) {
                case '+':
                    symbols.add(new Symbol(SymbolType.PLUS, ch));
                    position++;
                    continue;
                case '-':
                    symbols.add(new Symbol(SymbolType.MINUS, ch));
                    position++;
                    continue;
                case '*':
                    symbols.add(new Symbol(SymbolType.MUL, ch));
                    position++;
                    continue;
                case '/':
                    symbols.add(new Symbol(SymbolType.DIV, ch));
                    position++;
                    continue;
                case '^':
                    symbols.add(new Symbol(SymbolType.DEGREE, ch));
                    position++;
                    continue;
                default: // numbers
                    if ((ch >= '0' && ch <= '9') || ch == '.') {
                        StringBuilder sb = new StringBuilder();
                        do {    //  each digit of the number
                            sb.append(ch);
                            position++;
                            if (position >= text.length()) { // end of line
                                break;
                            }
                            ch = text.charAt(position);
                        } while ((ch >= '0' && ch <= '9') || ch == '.');  // all digits of the number
                        symbols.add(new Symbol(SymbolType.NUMBER, sb.toString())); // add the digits into a number
                    } else {
                        if (ch != ' ') {
                            throw new RuntimeException("Unknown symbol: " + ch);
                        }
                        position++;
                    }
            }
        }
        symbols.add(new Symbol(SymbolType.END, ""));  // end of text parsing
        return symbols;
    }


    public static double number(Buffer symbols) {
        Symbol symbol = symbols.next();
        switch (symbol.type) {
            case MINUS:
                double value = number(symbols);
                return -value;  // unary minus
            case NUMBER:
                return Double.parseDouble(symbol.value);
            default:
                throw new RuntimeException("Unknown symbol: " + symbol.value);
        }
    }


    public static double degree(Buffer symbols) {
        double value = number(symbols);
        while (true) {
            Symbol symbol = symbols.next();
            switch (symbol.type) { // if next symbol '^'
                case DEGREE -> value = Math.pow(value, number(symbols));
                case END, PLUS, MINUS, MUL, DIV -> {
                    symbols.back();
                    return value;
                }
                default -> throw new RuntimeException("Unknown symbol: " + symbol.value);
            }
        }
    }

    public static double mulDiv(Buffer symbols) {
        double value = degree(symbols);
        while (true) {
            Symbol symbol = symbols.next();
            switch (symbol.type) { // if next symbol '*' or '/'
                case MUL -> value *= degree(symbols);
                case DIV -> value /= degree(symbols);
                case END, PLUS, MINUS -> {
                    symbols.back();
                    return value;
                }
                default -> throw new RuntimeException("Unknown symbol: " + symbol.value);
            }
        }
    }

    public static double plusMinus(Buffer symbols) {
        double value = mulDiv(symbols);
        while (true) {
            Symbol symbol = symbols.next();
            switch (symbol.type) { // if next symbol '+' or '-'
                case PLUS -> value += mulDiv(symbols);
                case MINUS -> value -= mulDiv(symbols);
                case END -> {
                    symbols.back();
                    return value;
                }
                default -> throw new RuntimeException("Unknown symbol: " + symbol.value);
            }
        }
    }

    public static double expression(Buffer symbols) {
        Symbol symbol = symbols.next();
        if (symbol.type == SymbolType.END) {
            return 0;
        } else {
            symbols.back();
            return plusMinus(symbols);
        }
    }
}
