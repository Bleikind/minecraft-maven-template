package de.bleikind.util;

import org.reflections.Reflections;

import java.util.Set;

public class Helper {

    /**
     * Collecting all classes in a given package.
     * @param packageName relative path to package.
     * @param superClass Fetch for specific super class.
     * @param <T> The type of super class
     * @return The classes with the super class.
     */

    public static <T> Set<Class<? extends T>> getClasses(String packageName, Class<T> superClass){
        try {
            return new Reflections(packageName).getSubTypesOf(superClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}