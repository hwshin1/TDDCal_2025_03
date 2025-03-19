package org.example;

public class Calc {
    public static int run(String exp) {
        System.out.println("exp1: " + exp);
        exp = exp.replace("- ", "+ -");

        System.out.println("exp2: " + exp);

        String[] bits = exp.split(" \\+ ");

        int sum = 0;
        for (int i = 0; i < bits.length; i++) {
            sum += Integer.parseInt(bits[i]);
        }
        return sum;

//        throw new RuntimeException("올바른 계산식이 아닙니다.");
    }
}
