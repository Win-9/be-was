package model;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

public class Model {
    public static Map<String, Object> modelMap = Maps.newConcurrentMap();

    public static void addAttribute(String name, Object object) {
        modelMap.put(name, object);
    }

    public static void removeAttribute(String name) {
        modelMap.remove(name);
    }

    public static Optional<Object> getAttribute(String name) {
        return Optional.ofNullable(modelMap.get(name));
    }
}
