package com.shpp.p2p.cs.sburlachenko.assignment11;

public class Tests11 {
    public static void main(String[] args) {
        String[] a = {"2+5-1"}; // 6
        String[] b = {"2+5*2-1"}; // 11
        String[] c = {"2+5*2-1+6/3"}; // 13
        String[] d = {"5+x^2/4+x", "x=3"};  // 10.25
        String[] e = {"5+x^2/4+nx+y+z", "x=3", "y=1.75", "z=-8"};  // exception
        String[] f = {"5+x^2/4+x+y+z", "x=3", "y=1.75", "z=-8"};  // 4
        String[] g = {"5+x^2/4+x+y+z", "3=x", "y=1.75", "z=-8"};  // exception
        String[] h = {"5+x^2/4+x+y+z", "x=3", "y=1.75=9.45", "z=-8"};  // exception
        String[] i = {"5+x^2/4+x+y+z", "x=3", "y=1.75", "z/-8"};  // exception
        String[] j = {"5+x^2/4+x+9y+z", "x=3", "y=1.75", "z=-8"};  // exception
        String[] k = {"-a+-0.5", "a=-7"};  // 6.5
        String[] l = {"1+(2+3*(4+5-sin(45*cos(a))))/7", "a = -9.96"};  // 5.50530
        String[] m = {"1+ (2+ 3* (4+5-sin (45*cos(a) ))) /7", "a=-9.96"};  // 5.50530
        String[] n = {"1+ (2+ 3* (4+5-sin (45*cos(a) ))) /7", "a=-9.96=8=13"};  // 5.50530
        String[] o = {"5+x^3+y^4+z", "x=3", "y=0", "z=-8"};  // 24
        String[] p = {"5+ x ^3+ y^4+ z", "x=  3", "y= 2", "z  = -8"};  // 40
        String[] r = {"1+(2+3*(4+5-atan(45*log10(x))))/7", "x=5"};  // 4.4832
        String[] s = {"sqrt(x)^3", "x=625"};  // 15625
        String[] t = {"sqrt(x^3)", "x=4"};  // 8
        String[] v = {"x^2^3", "x=-4"};  // -65536


        //    Assignment11Part1.main(a);
        //    Assignment11Part1.main(b);
        //    Assignment11Part1.main(c);
        //    Assignment11Part1.main(d);
        //    Assignment11Part1.main(e);
        //    Assignment11Part1.main(f);
        //    Assignment11Part1.main(g);
        //    Assignment11Part1.main(h);
        //    Assignment11Part1.main(i);
        //    Assignment11Part1.main(j);
        //    Assignment11Part1.main(k);
        //    Assignment11Part1.main(l);
        //      Assignment11Part1.main(m);
        //    Assignment11Part1.main(n);
        //    Assignment11Part1.main(o);
        //    Assignment11Part1.main(p);
        //    Assignment11Part1.main(r);
        //    Assignment11Part1.main(s);
        //    Assignment11Part1.main(t);
              Assignment11Part1.main(v);

    }
}
