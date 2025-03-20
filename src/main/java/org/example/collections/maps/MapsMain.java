package org.example.collections.maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsMain {
    public static void main(String[] args) {
        Map<String, Integer> countryCodes = new HashMap<>();
        countryCodes.put("Brazil", 55);
        countryCodes.put("USA", 1);
        countryCodes.put("China", 86);

        System.out.println(countryCodes);
        System.out.println(countryCodes.get("Brazil"));
        System.out.println(countryCodes.keySet());

        countryCodes.keySet().forEach(key -> System.out.println(key + ": " + countryCodes.get(key)));

        // Warning: the map override the existing values
        countryCodes.put("Brazil", 550);
        System.out.println(countryCodes.get("Brazil"));

        // just return null when the key does not exist on the map, it does not throw an exception
        System.out.println(countryCodes.get("Brasil"));
    }
}
