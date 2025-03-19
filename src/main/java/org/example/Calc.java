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

        int a = 0;
        int b = 0;
        if (bits.length <= 2) {
            a = Integer.parseInt(bits[0]);
            b = Integer.parseInt(bits[1]);
        }

        int sum = 0;
        for (int i = 0; i < bits.length; i++) {
            sum += Integer.parseInt(bits[i]);
        }

        if (plus) {
            return sum;
        } else if (minus) {
            return a - b;
        }

        throw new RuntimeException("올바른 계산식이 아닙니다.");
    }
}
