package org.example;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {
    public static int run(String exp) {
        exp = exp.trim();
        // 괄호 제거 메서드
        exp = stripOuterBrackets(exp);

        // 단일항이 들어오면 바로 리턴
        if (!exp.contains(" ")) {
            return Integer.parseInt(exp);
        }

        boolean multi = exp.contains("*");
        boolean plus = exp.contains("+");
        boolean needToSplit = exp.contains("(") || exp.contains(")");
        boolean needToCompound = multi && plus;

        if (needToSplit) {
            int splitPointIndex = findSplitPointIndex(exp);

            String firstPart = exp.substring(0, splitPointIndex);
            String secondPart = exp.substring(splitPointIndex + 1);

            char operator = exp.charAt(splitPointIndex);
            exp = Calc.run(firstPart) + " " + operator + " " + Calc.run(secondPart);
            return Calc.run(exp);
        } else if (needToCompound) {
            String[] bits = exp.split(" \\+ ");

            String newExp = Arrays.stream(bits)
                    .mapToInt(Calc::run)
                    .mapToObj(e -> e + "")
                    .collect(Collectors.joining(" + "));

            return run(newExp);
        } else if (multi) {
            String[] bits = exp.split(" \\* ");

            int multiplier = 1;
            for (int i = 0; i < bits.length; i++) {
                multiplier *= Integer.parseInt(bits[i]);
            }

            return multiplier;
        } else {
            exp = exp.replace("- ", "+ -");

            String[] bits = exp.split(" \\+ ");

            int sum = 0;
            for (int i = 0; i < bits.length; i++) {
                sum += Integer.parseInt(bits[i]);
            }
            return sum;
        }

//        throw new RuntimeException("올바른 계산식이 아닙니다.");
    }

    static String stripOuterBrackets(String e) {
        int count = 0;
        while (e.charAt(count) == '(' && e.charAt(e.length() - 1 - count) == ')') {
            count++;
        }

        if (count == 0) {
            return e;
        }

        return e.substring(count, e.length() - count);
    }

    static int findSplitPointIndex(String exp) {
        int index = findSplitPointByIndex(exp, '+');

        if (index >= 0) {
            return index;
        }
        return findSplitPointByIndex(exp, '*');
    }

    static int findSplitPointByIndex(String exp, char findChar) {
        int bracketsCount = 0;

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c == '(') {
                bracketsCount++;
            } else if (c == ')') {
                bracketsCount--;
            } else if (c == findChar) {
                if (bracketsCount == 0) return i;
            }
        }
        return -1;
    }
}