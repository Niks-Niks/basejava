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
        int[] array = Arrays.stream(values).sorted().distinct().toArray();
        int result = 0;
        for (int i = array.length - 1, n = 0; i >= 0; --i, n++) {
            int pos = (int) Math.pow(10, i);
            result += array[n] * pos;
        }
        return result;
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum-> " + sum);
        List<Integer> result = integers.stream().filter(s -> sum % 2 == 0 ? s % 2 != 0 : s % 2 == 0).collect(Collectors.toList());
        return result;
    }
}
