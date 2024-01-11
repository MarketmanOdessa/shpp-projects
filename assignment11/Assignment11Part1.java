package com.shpp.p2p.cs.sburlachenko.assignment11;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.pow;

/**
 * The first argument to be given to the programme is a mathematical expression.
 * The other parameters are optional and look like (variable name = value).
 * The program substitutes the variables into the expression and outputs the result of the expression itself.
 */

public class Assignment11Part1 {

    public static HashMap<String, IAction> function;

    /**
     * Main method: substitutes variables in the formula and outputs the result.
     *
     * @param args Formula and variables(optional).
     */
    public static void main(String[] args) {
        function = getFunctionMap();
        HashMap<String, Double> variables = new HashMap<>();
        String formula = args[0];
        System.out.println("Formula:     " + formula);
        for (int i = 1; i < args.length; i++) { // read all the variables
            String str = args[i].replaceAll(" ", "");   // temporary string to collect variables
            System.out.println("Variables " + i + ": " + args[i]);
            String[] st = str.split("=");   // divide by variable name and value
            try {
                variables.put(st[0], Double.parseDouble(st[1]));
            } catch (RuntimeException e) {
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
        for (HashMap.Entry<String, Double> pair : variables.entrySet()) {   // overwriting with replacement of variables with their values
            formula = formula.replaceAll(pair.getKey(), pair.getValue().toString());
        }   //  replace the variable name with the value
        System.out.println("Expression:  " + formula);
        ArrayList<Symbol> symbols = parsing(formula);    // parsed formula is saved in an array
        Buffer buffer = new Buffer(symbols);    // save the array to the buffer
        return expression(buffer);  // final result
    }

    public enum SymbolType {
        FUNCTION, BR_LEFT, BR_RIGHT, PLUS, MINUS, MUL, DIV, DEGREE, NUMBER, END;
    }

    public interface IAction {
        double calculate(Double numb);
    }

    /**
     * @return Map where the keys are the names of the functions and the values are the functions themselves
     */
    public static HashMap<String, IAction> getFunctionMap() {
        HashMap<String, IAction> functionArr = new HashMap<>();
        functionArr.put("sin", Math::sin);
        functionArr.put("cos", Math::cos);
        functionArr.put("tan", Math::tan);
        functionArr.put("atan", Math::atan);
        functionArr.put("log", Math::log10);
        functionArr.put("log2", args -> Math.log(args) / Math.log(2));
        functionArr.put("sqrt", Math::sqrt);
        return functionArr;
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
        public ArrayList<Symbol> symbols;

        public Buffer(ArrayList<Symbol> symbols) {
            this.symbols = symbols;
        }

        public Symbol next() {
            return symbols.get(position++);
        }

        public void back() {
            position--;
        }

        public int getPosition() {  // to determine the position of the exception in the text
            return position;
        }
    }

    /**
     * Method for parsing a formula
     *
     * @param text a ready-made expression with substituted variables
     * @return disassembled formula
     */
    public static ArrayList<Symbol> parsing(String text) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        int position = 0;
        while (position < text.length()) {
            char ch = text.charAt(position); // take one char
            switch (ch) {
                case '(':
                    symbols.add(new Symbol(SymbolType.BR_LEFT, ch));
                    position++;
                    continue;
                case ')':
                    symbols.add(new Symbol(SymbolType.BR_RIGHT, ch));
                    position++;
                    continue;
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
                default:
                    if ((ch <= '9' && ch >= '0') || ch == '.') {    // add the characters to a number
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
                            if (ch >= 'a' && ch <= 'z'
                                    || ch >= 'A' && ch <= 'Z') {
                                StringBuilder sb = new StringBuilder();
                                do {
                                    sb.append(ch);
                                    position++;
                                    if (position >= text.length()) {
                                        break;
                                    }
                                    ch = text.charAt(position);
                                } while (ch >= 'a' && ch <= 'z');
                                if (function.containsKey(sb.toString())) {  // If the combination of letters is the same as the function name
                                    symbols.add(new Symbol(SymbolType.FUNCTION, sb.toString()));
                                } else {
                                    throw new RuntimeException("Unexpected character before: " + ch);
                                }
                            }
                        } else {
                            position++;
                        }
                    }
            }
        }
        symbols.add(new Symbol(SymbolType.END, ""));
        return symbols;
    }

    /**
     * Method for calculating the function
     *
     * @param symbolsBuffer character set
     * @return function result
     */
    public static double func(Buffer symbolsBuffer) {
        String name = symbolsBuffer.next().value;
        Symbol symbol = symbolsBuffer.next();
        if (symbol.type != SymbolType.BR_LEFT) {
            throw new RuntimeException("Wrong function call syntax at " + symbol.value);
        }
        double argFunc = 0.0;
        symbol = symbolsBuffer.next();
        if (symbol.type != SymbolType.BR_RIGHT) {
            symbolsBuffer.back();
            argFunc = expression(symbolsBuffer);
            symbol = symbolsBuffer.next();
        }
        return function.get(name).calculate(argFunc);
    }

    /**
     * The method returns the final result after all addition/subtraction operations have been completed
     *
     * @param symbols
     * @return finale result
     */
    public static double expression(Buffer symbols) {
        Symbol symbol = symbols.next();
        if (symbol.type == SymbolType.END) {
            return 0;
        } else {
            symbols.back();
            return plusMinus(symbols);
        }
    }

    /**
     * The method performs addition/subtraction after multiplication/subtraction
     *
     * @param symbols
     * @ addition/subtraction result
     */
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
                default ->
                        throw new RuntimeException("Unknown symbol: " + symbol.value + " at position: " + symbols.getPosition());
            }
        }
    }

    /**
     * The method performs divisions/multiplications after the addition to a power and before the addition/subtraction
     *
     * @param symbols
     * @return The result of the product of the numbers
     */
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
                default ->
                        throw new RuntimeException("Unknown symbol: " + symbol.value + " at position: " + symbols.getPosition());
            }
        }
    }

    /**
     * The method expands a number or expression before multiplication/division
     *
     * @param symbols
     * @return the value of the expression raised to a power
     */
    public static double degree(Buffer symbols) {
        double value = numberOrExpression(symbols);
        while (true) {
            Symbol symbol = symbols.next();
            switch (symbol.type) {
                case DEGREE -> value = pow(value, numberOrExpression(symbols));
                case END, BR_RIGHT, PLUS, MINUS, MUL, DIV -> {
                    symbols.back();
                    return value;
                }
                default ->
                        throw new RuntimeException("Unknown symbol: " + symbol.value + " at position: " + symbols.getPosition());
            }
        }
    }

    /**
     * If numeric characters come in, the method parses them into a double.
     * Counts the expression inside the brackets.
     * And calls the function method if the expression is a function.
     *
     * @param symbols individual characters or expressions
     * @return expression value
     */
    public static double numberOrExpression(Buffer symbols) {
        Symbol symbol = symbols.next();
        switch (symbol.type) {
            case FUNCTION:
                symbols.back();
                return func(symbols);
            case MINUS:
                double value = numberOrExpression(symbols); // unary minus
                return -value;
            case NUMBER:
                return Double.parseDouble(symbol.value);
            case BR_LEFT:
                value = plusMinus(symbols);
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
