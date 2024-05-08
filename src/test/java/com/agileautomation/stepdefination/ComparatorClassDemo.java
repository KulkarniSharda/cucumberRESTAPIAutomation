package com.agileautomation.stepdefination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorClassDemo {
    public static void main(String[] arg) {
        List<Integer> intArray = new ArrayList<>();
        intArray.add(10);
        intArray.add(50);
        intArray.add(70);
        intArray.add(30);
        System.out.println(intArray);
//asc order
        Collections.sort(intArray);
        //regular sorting
        System.out.println(intArray);
//desc order
        Collections.reverse(intArray);
        System.out.println(intArray);
        Integer a = intArray.get(0);
        Integer b = intArray.get(1);
        System.out.println(a.compareTo(b));
        Comparator<Integer> comparator = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };

        Comparator<Integer> comp1 = (var1, var2) -> a.compareTo(b);
        Comparator<Integer> comp2 = Integer::compareTo;
        Collections.sort(intArray, comparator);
        Collections.sort(intArray, Collections.reverseOrder(comparator));
    }
}
