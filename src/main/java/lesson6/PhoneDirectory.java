package lesson6;

import java.util.*;

public class PhoneDirectory {
    private final Map<String, List<String>> phoneMap = new HashMap<>();

    public void add(String name, String number) {
        if (phoneMap.containsKey(name)) {
           List<String> phones = phoneMap.get(name);
           phones.add(number);
        } else {
            List<String> phones = new ArrayList<>();
            phones.add(number);
            phoneMap.put(name, phones);
        }
    }

    public List<String> get(String name) {
        return phoneMap.getOrDefault(name, Collections.emptyList());
    }
}
