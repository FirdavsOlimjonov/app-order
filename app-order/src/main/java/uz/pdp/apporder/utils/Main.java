package uz.pdp.apporder.utils;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        int[] a = {1, 2, 1};

        int[] ints1 = Arrays.stream(new int[]{1,1}).flatMap(e -> Arrays.stream(a)).toArray();
        System.out.println(Arrays.toString(ints1));
    }
}
