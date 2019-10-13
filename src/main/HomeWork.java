package main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HomeWork {
    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 3, 2, 3, 4, 5, 2, 3};
        System.out.println(minValue(array));
        List<Integer> integer = Arrays.stream(array)
                .boxed()
                .collect(Collectors.toList());
        oddOrEven(integer).stream()
                .forEach(System.out::print);
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .sorted()
                .distinct()
                .reduce(0, (x, y) -> x * 10 + y);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("Sum-> " + sum);
        return integers.stream().filter(s -> sum % 2 != s % 2).collect(Collectors.toList());
    }
}
