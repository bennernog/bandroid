package mobile.bny.jsontools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {

    public static String stringify(ArrayList<?> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static String stringify(HashMap<?,?> map){
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public static <T> Collection<T> listify(String input){
        Gson gson = new Gson();

        Type type = new TypeToken<Collection<T>>(){}.getType();

        return gson.fromJson(input, type);
    }

    public static <K,V> Map<K,V> mapify(String input){
        Gson gson = new Gson();

        Type type = new TypeToken<Map<K,V>>(){}.getType();

        return gson.fromJson(input, type);
    }

    public static Gson getGson(Class type, JsonSerializer serializer){
        return new GsonBuilder().registerTypeAdapter(type, serializer).create();
    }

}
