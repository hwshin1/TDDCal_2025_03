package org.example;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {
    public static boolean debug = true;
    public static int runCallCount = 0;

    public static int run(String exp) {
        runCallCount++;

        exp = exp.trim();
        // 괄호 제거 메서드
        exp = stripOuterBrackets(exp);

        // -( 패턴이라면 해석 가능하게
        if (isCaseMinusBracket(exp)) {
            // () * -1 이런식으로 바꾼다.
            exp = exp.substring(1) + " * -1";
        }

        // 디버그
        if (debug) {
            System.out.printf("exp(%d): %s\n", runCallCount, exp);
        }

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

    private static String stripOuterBrackets(String e) {
        int count = 0;
        while (e.charAt(count) == '(' && e.charAt(e.length() - 1 - count) == ')') {
            count++;
        }

        if (count == 0) {
            return e;
        }

        return e.substring(count, e.length() - count);
    }

    private static int findSplitPointIndex(String exp) {
        int index = findSplitPointByIndex(exp, '+');

        if (index >= 0) {
            return index;
        }
        return findSplitPointByIndex(exp, '*');
    }

    private static int findSplitPointByIndex(String exp, char findChar) {
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

    private static boolean isCaseMinusBracket(String exp) {
        if (!exp.startsWith("-(")) {
            return false;
        }

        int bracketsCount = 0;

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c == '(') {
                bracketsCount++;
            } else if (c == ')') {
                bracketsCount--;
            }

            if (bracketsCount == 0) {
                if (exp.length() - 1 == i) {
                    return true;
                }
            }
        }

        return false;
    }
}
