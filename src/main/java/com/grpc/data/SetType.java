package com.grpc.data;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class SetType {
    public static void main(String[] args) {
        // Create a set
        Set<String> set = new HashSet<>();

        // Add elements to the set
        set.add("apple");
        set.add("banana");
        set.add("banana");
        set.add("apple");

        // Print the set
        System.out.println("Set: " + set);
//        System.out.println(Set.of("apple", "banana","banana", "apple"));

        // Check if an element exists in the set
        if (set.contains("banana")) {
            System.out.println("Set contains banana");
        } else {
            System.out.println("Set does not contain banana");
        }

        // Remove an element from the set
        set.remove("banana");
        System.out.println("Set after removing banana: " + set);

        Set<String> linkedSet = new LinkedHashSet<>();
        linkedSet.add("apple");
        linkedSet.add("banana");
        linkedSet.add("banana");
        System.out.println(linkedSet);
        linkedSet.clear();
    }
}
