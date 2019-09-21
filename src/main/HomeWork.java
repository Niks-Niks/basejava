package main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HomeWork {
    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 3, 2, 3, 4, 5, 2, 3};
        System.out.println(minValue(array));
        List<Integer> integer = Arrays.stream(array).boxed().collect(Collectors.toList());
        oddOrEven(integer).stream().forEach(System.out::print);
    }

    private static int minValue(int[] values) {
        int arrays = Arrays.stream(values).distinct().min().getAsInt();
        return arrays;
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        return getList(integers, sum);
    }

    private static List<Integer> getList(List<Integer> integers, int sum) {
        List<Integer> result;
        if (sum % 2 == 0) {
            result = integers.stream().filter(s -> s % 2 != 0).collect(Collectors.toList());
        } else {
            result = integers.stream().filter(s -> s % 2 == 0).collect(Collectors.toList());
        }
        return result;
    }
}
