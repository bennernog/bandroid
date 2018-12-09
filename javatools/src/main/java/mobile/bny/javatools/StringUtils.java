package mobile.bny.javatools;

/**
 * Created by BNY on 29/11/2017.
 */

public class StringUtils {



    /**
     * Function checks if param is null or empty
     *
     * @param s String
     * @return boolean true if input is not null and not an empty string
     * parameter.
     */
    public static boolean notNullOrEmpty(String s){
        return s != null && s.length()>0;
    }

    /**
     * Function checks if param is null or empty
     *
     * @param s String
     * @return boolean true if input is  null or  an empty string
     * parameter.
     */
    public static boolean isNullOrEmpty(String s){
        return !notNullOrEmpty(s);
    }

    /**
     * Function checks if param is empty
     *
     * @param s String
     * @return boolean true if input is not null and an empty string
     * parameter.
     */
    public static boolean isEmpty(String s){
        return s != null && s.length() == 0;
    }

    /**
     * Return a not null string.
     *
     * @param s String
     * @return empty string if it is null otherwise the string passed in as
     * parameter.
     */
    public static String nonNull(String s) {

        if (s == null) {
            return "";
        }
        return s;
    }

    /**
     *
     * @param objects the objects to list in the string
     * @return a string with the desired objects, seperated by " - "
     */
    public static String simpleFormat(Object... objects) {
        return simpleFormat(" - ", objects);
    }

    /**
     *
     * @param seperator String -> the string to use to seperate the objects
     * @param objects the objects to list in the string, can be of different types
     * @return a string with the desired objects, seperated by the given seperator
     */
    public static String simpleFormat(String seperator, Object... objects){
        if(objects == null)
            return "NULL";
        if(objects.length == 0)
            return "EMPTY";
        else {
            StringBuilder builder = new StringBuilder(String.format("%s",objects[0]));
            for(int i = 1; i < objects.length; i++){
                builder.append(seperator);
                builder.append(objects[i]);
            }
            return builder.toString();
        }
    }

    /**
     * Turns a string to Title case
     * @param s String: the string to be turned to Title case
     * @return String: the string in title case
     */
    public static String toTitleCase(String s){

        if (notNullOrEmpty(s)) {

            boolean space = true;
            StringBuilder builder = new StringBuilder(s);

            for (int i = 0; i < builder.length(); ++i) {
                char c = builder.charAt(i);
                if (space) {
                    if (!Character.isWhitespace(c)) {
                        builder.setCharAt(i, Character.toTitleCase(c));
                        space = false;
                    }
                } else if (Character.isWhitespace(c) || c == '.' || c == '!' || c == '?') {
                    space = true;
                } else {
                    builder.setCharAt(i, Character.toLowerCase(c));
                }
            }

            return builder.toString();

        } else return s;
    }

    /**
     * Turns a string to Sentece case
     * @param s String: the string to be turned to Sentence case
     * @return String: the string in Sentence case
     */
    public static String toSentenceCase(String s){

        if (notNullOrEmpty(s)) {

            int position = 0;
            boolean capitalize = true;
            StringBuilder builder = new StringBuilder(s.toLowerCase());

            while (position < builder.length()) {

                if (builder.charAt(position) == '.' || builder.charAt(position) == '!' || builder.charAt(position) == '?' ) {
                    capitalize = true;
                } else if (capitalize && !Character.isWhitespace(builder.charAt(position))) {
                    builder.setCharAt(position, Character.toUpperCase(builder.charAt(position)));
                    capitalize = false;
                }
                position++;
            }

            return builder.toString();

        } else return s;
    }
}
