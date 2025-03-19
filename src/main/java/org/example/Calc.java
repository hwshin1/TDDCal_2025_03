package org.example;

public class Calc {
    public static int run(String exp) {
        System.out.println("exp1: " + exp);
        boolean multi = exp.contains("*");
        boolean plus = exp.contains("+");

        boolean needToCompound = multi && plus;

        String[] bits = null;

        if (needToCompound) {
            bits = exp.split(" \\+ ");

            return Integer.parseInt(bits[0]) + run(bits[1]);
        } else if (multi) {
            bits = exp.split(" \\* ");

            int multiplier = 1;
            for (int i = 0; i < bits.length; i++) {
                multiplier *= Integer.parseInt(bits[i]);
            }

            return multiplier;
        } else {
            exp = exp.replace("- ", "+ -");
            System.out.println("exp2: " + exp);

            bits = exp.split(" \\+ ");

            int sum = 0;
            for (int i = 0; i < bits.length; i++) {
                sum += Integer.parseInt(bits[i]);
            }
            return sum;
        }
//        throw new RuntimeException("올바른 계산식이 아닙니다.");
    }
}
