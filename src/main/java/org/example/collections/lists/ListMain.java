package org.example.collections.lists;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListMain {
    public static void main(String[] args) {
        // arraylist
        List<String> accounts = new ArrayList<>();
        accounts.add("user one");
        accounts.add("user two");
        accounts.add("user two");

        System.out.println("list 0:" + accounts.get(0));
        System.out.println("list 1:" + accounts.get(1));
        System.out.println("list 2:" + accounts.get(2));
    }
}
