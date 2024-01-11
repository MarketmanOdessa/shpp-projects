package com.shpp.p2p.cs.sburlachenko.assignment4;


public class MyClass{
    public static void main(String[] args) {
        double a = 3;
        double b = 2.5;
        double c = -0.5;

// discriminant
        double d = b * b - 4 * a * c;
        // two roots
        if (d > 0) {
            double x1, x2;
            x1 = (-b - Math.sqrt(d)) / (2 * a);
            x2 = (-b + Math.sqrt(d)) / (2 * a);
            System.out.println("There are two roots: " + x1 + " and " + x2);
            // one root
        } else if (d == 0) {
            System.out.println( "x1 =" + (-b / (2 * a)));
            System.out.println( "x2 =" + (-b / (2 * a)));

            double x = -b / (2 * a);
            System.out.println("There is one root: " + x);
            // no real roots
        } else {
            System.out.println("There are no real roots");
            System.out.println("x1 =" + ((-b - Math.sqrt(d)) / (2 * a)));
            System.out.println("x2 =" + ((-b + Math.sqrt(d)) / (2 * a)));
        }
    }
}


