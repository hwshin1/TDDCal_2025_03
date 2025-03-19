package org.example;

public class Calc {
    public static int run(String exp) {
        boolean plus = exp.contains("+");
        boolean minus = exp.contains("-");

        String[] bits = null;

        if (plus) {
            bits = exp.split(" \\+ ");
        } else if (minus) {
            bits = exp.split(" - ");
        }

        int a = Integer.parseInt(bits[0]);
        int b = Integer.parseInt(bits[1]);

        if (plus) {
            return a + b;
        } else if (minus) {
            return a - b;
        }

        throw new RuntimeException("올바른 계산식이 아닙니다.");
    }
}
