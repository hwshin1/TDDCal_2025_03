package org.example;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {
    public static int run(String exp) {
        boolean par = exp.contains("(");

        if (par) {
            // 괄호 제거 메서드
            exp = stripOuterBrackets(exp);
            return run(exp);
        }

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

    static String stripOuterBrackets(String e) {
        if (e.charAt(0) == '(' && e.charAt(e.length() - 1) == ')') {
            return e.substring(1, e.length() - 1);
        }
        return e;
    }
}
