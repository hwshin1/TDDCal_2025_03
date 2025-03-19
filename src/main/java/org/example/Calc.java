package org.example;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {
    public static int run(String exp) {
        exp = stripOuterBrackets(exp);

        // 단일항이 들어오면 바로 리턴
        if (!exp.contains(" ")) {
            return Integer.parseInt(exp);
        }
        System.out.println("exp1: " + exp);
        boolean multi = exp.contains("*");
        boolean plus = exp.contains("+");

        boolean needToCompound = multi && plus;

        String[] bits = null;

        if (needToCompound) {
            bits = exp.split(" \\+ ");

            String newExp = Arrays.stream(bits)
                    .mapToInt(Calc::run)
                    .mapToObj(e -> e + "")
                    .collect(Collectors.joining(" + "));

            return run(newExp);
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

    static String stripOuterBrackets(String exp) {
        int outerBracketsCount = 0;

        while (exp.charAt(outerBracketsCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketsCount) == ')') {
            outerBracketsCount++;
        }

        if (outerBracketsCount == 0) return exp;

        return exp.substring(outerBracketsCount, exp.length() - outerBracketsCount);
    }
}
