package com.shpp.p2p.cs.sburlachenko.assignment10;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assignment10Part1Ext {

    public static void main(String[] args) {
        HashMap<String,Double> variables = new HashMap<>();
        String formula = args[0];
        System.out.println("Formula:     " + formula);
        for (int i = 1; i < args.length ; i++) {
            String str = args[i];
            System.out.println("Variables " + i + ": " + args[i]);
            String[] st = str.split("=");
            variables.put(st[0],Double.parseDouble(st[1]));
        }
        System.out.println("Result:      " + calculate(formula, variables));
    }

    public static double calculate(String formula, HashMap<String,Double> variables) {
        for (Map.Entry<String, Double> pair: variables.entrySet()) {
            formula = formula.replaceAll(pair.getKey(), pair.getValue().toString());
        }
        System.out.println("Expression:  " + formula);
        List<Symbol> symbols = parsing(formula);
        Buffer buffer = new Buffer(symbols);
        return expression(buffer);
    }

    public enum SymbolType {
        BR_LEFT, BR_RIGHT, PLUS, MINUS, MUL, DIV, DEGREE, NUMBER, END;
    }

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
        public int getPosition() {
            return position;
        }
    }

    public static List<Symbol> parsing(String text) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        int position = 0;
        while (position< text.length()) {
            char ch = text.charAt(position); // take one char
            switch (ch) {
                case '(': symbols.add(new Symbol(SymbolType.BR_LEFT, ch));
                    position++;
                    continue;
                case ')': symbols.add(new Symbol(SymbolType.BR_RIGHT, ch));
                    position++;
                    continue;
                case '+': symbols.add(new Symbol(SymbolType.PLUS, ch));
                    position++;
                    continue;
                case '-': symbols.add(new Symbol(SymbolType.MINUS, ch));
                    position++;
                    continue;
                case '*': symbols.add(new Symbol(SymbolType.MUL, ch));
                    position++;
                    continue;
                case '/': symbols.add(new Symbol(SymbolType.DIV, ch));
                    position++;
                    continue;
                case '^': symbols.add(new Symbol(SymbolType.DEGREE, ch));
                    position++;
                    continue;
                default:
                    if ((ch <= '9' && ch >= '0') || ch == '.') {
                        StringBuilder sb = new StringBuilder();
                        do {    // first number
                            sb.append(ch);
                            position++;
                            if (position >= text.length()) { // end of line
                                break;
                            }
                            ch = text.charAt(position);
                        } while ((ch <= '9' && ch >= '0') || ch == '.');
                        symbols.add(new Symbol(SymbolType.NUMBER, sb.toString())); // add the digits into a number
                    } else {
                        if (ch != ' ') {
                            throw new RuntimeException("Unknown symbol: " + ch);
                        }
                        position++;
                    }
            }
        }
        symbols.add(new Symbol(SymbolType.END, ""));
        return symbols;
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

    public static double plusMinus(Buffer symbols) {
        double value = mulDiv(symbols);
        while (true) {
            Symbol symbol = symbols.next();
            switch (symbol.type) {
                case PLUS -> value += mulDiv(symbols);
                case MINUS -> value -= mulDiv(symbols);
                case END, BR_RIGHT -> {
                    symbols.back();
                    return value;
                }
                default -> throw new RuntimeException("Unknown symbol: " + symbol.value + " at position: " + symbols.getPosition());
            }
        }
    }

    public static double mulDiv(Buffer symbols) {
        double value = degree(symbols);
        while (true) {
            Symbol symbol = symbols.next();
            switch (symbol.type) {
                case MUL -> value *= degree(symbols);
                case DIV -> value /= degree(symbols);
                case END, BR_RIGHT, PLUS, MINUS -> {
                    symbols.back();
                    return value;
                }
                default -> throw new RuntimeException("Unknown symbol: " + symbol.value + " at position: " + symbols.getPosition());
            }
        }
    }

    public static double degree(Buffer symbols) {
        double value = numberOrExpression(symbols);
        while (true) {
            Symbol symbol = symbols.next();
            switch (symbol.type) {
                case DEGREE -> value = Math.pow(value, numberOrExpression(symbols));
                case END, BR_RIGHT, PLUS, MINUS, MUL, DIV -> {
                    symbols.back();
                    return value;
                }
                default -> throw new RuntimeException("Unknown symbol: " + symbol.value + " at position: " + symbols.getPosition());
            }
        }
    }

    public static double numberOrExpression(Buffer symbols) {
        Symbol symbol = symbols.next();
        switch (symbol.type) {
            case MINUS: double value = numberOrExpression(symbols); // // unary minus
                return -value;
            case NUMBER: return Double.parseDouble(symbol.value);
            case BR_LEFT: value = plusMinus(symbols);
                symbol = symbols.next();
                if (symbol.type != SymbolType.BR_RIGHT) {
                    throw new RuntimeException("Unknown symbol: " + symbol.value + " at position: " + symbols.getPosition());
                }
                return value;
            default:
                throw new RuntimeException("Unknown symbol: " + symbol.value + " at position: " + symbols.getPosition());
        }
    }
}
