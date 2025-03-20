package org.example.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamsMain {
    public static void main(String[] args) {
        List<String> myList = new ArrayList<>();

        myList.add("Conta poupança");
        myList.add("Conta corrente");
        myList.add("Conta poupança");

        myList.stream()
                .filter(value -> filterList(value))
                .forEach(value -> System.out.println(value));

        // size of result list after apply the filter
        System.out.println(myList.stream()
                .filter(value -> filterList(value)).count());

        // remove duplicated elements transforming the list to a set
        myList.stream()
                .filter(value -> filterList(value))
                .collect(Collectors.toSet())
                .forEach(value -> System.out.println(value));

        // Stream has a parallelism approaches to work with the collections, so it is more
        // optimized use streams instead of a normal for
        myList.stream()
                .filter(value -> filterList(value))
                .forEach(value -> System.out.println(value));
    }

    public static Boolean filterList(String value) {
        return value.contains("poupança");
    }
}
