package mobile.bny.javatools;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {

    /**
     *
     * @param map Map: the map to be checked
     * @return boolean: true if map is not null AND contains 1 or more elements
     */
    public static boolean notNullOrEmpty(Map<?,?> map){
        return map != null && map.size() > 0;
    }

    /**
     *
     * @param array Array: the array to be checked
     * @return boolean: true if array is not null AND contains 1 or more elements
     */
    public static boolean notNullOrEmpty(Object[] array){
        return array != null && array.length > 0;
    }

    /**
     *
     * @param collection Collection: the collection to be checked
     * @return boolean: true if collection is not null AND contains 1 or more elements
     */
    public static boolean notNullOrEmpty(Collection<?> collection){
        return collection != null && collection.size() > 0;
    }

    /**
     *
     * @param map Map: the map to be checked
     * @return boolean: true if map is null or contains 0 elements
     */
    public static boolean isNullOrEmpty(Map<?,?> map){
        return !notNullOrEmpty(map);
    }

    /**
     *
     * @param array Array: the array to be checked
     * @return boolean: true if array is null or contains 0 elements
     */
    public static boolean isNullOrEmpty(Object[] array){
        return !notNullOrEmpty(array);
    }

    /**
     *
     * @param collection Collection: the collection to be checked
     * @return boolean: true if collection is null OR contains 0 elements
     */
    public static boolean isNullOrEmpty(Collection<?> collection){
        return !notNullOrEmpty(collection);
    }

    /**
     *
     * @param array Array: the array to be converted
     * @param seperator String: the string to use to seperate the elements
     * @return String: a string with the elements of the array, seperated by the given seperator
     */
    public static String stringify(Object[] array, String seperator){
        if(notNullOrEmpty(array)){
            StringBuilder builder = new StringBuilder();

            for (Object o : array) {
                builder.append(o);
                builder.append(seperator);
            }

            builder.setLength(builder.length() - 1);

            return builder.toString();

        } else
            return null;

    }

    /**
     *
     * @param collection Collection: the collection to be converted
     * @param seperator String: the string to use to seperate the elements
     * @return String: a string with the elements of the collection, seperated by the given seperator
     */
    public static String stringify(Collection<?> collection, String seperator){
        if(notNullOrEmpty(collection)){
            StringBuilder builder = new StringBuilder();

            for (Object o : collection) {
                builder.append(o);
                builder.append(seperator);
            }

            builder.setLength(builder.length() - 1);

            return builder.toString();

        } else
            return null;

    }

    /**
     *
     * @param map Map: the map to be converted
     * @param seperator String: the string to use to seperate the elements
     * @return String: a string with the elements of the map as "key = value", seperated by the given seperator
     */
    public static String stringify(Map<?,?> map, String seperator){
        if(notNullOrEmpty(map)){
            StringBuilder builder = new StringBuilder();

            for (Map.Entry<?,?> entry : map.entrySet()) {
                builder.append(entry.getKey());
                builder.append(" = ");
                builder.append(entry.getValue());
                builder.append(seperator);
            }

            builder.setLength(builder.length() - 1);

            return builder.toString();

        } else
            return null;

    }
}
