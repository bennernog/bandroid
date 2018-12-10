package mobile.bny.bandroidapp.data;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

import mobile.bny.javatools.StringUtils;

/**
 * Created by BNY on 25/11/2017.
 */

public class User{
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String email;
    private String gender;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static class Keys{
        public static final String ID = "id";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String EMAIL = "email";
        public static final String GENDER = "gender";
        public static final String AVATAR = "avatar";
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %s\nFirst Name: %s\nLast Name: %s\nGender: %s\nEmail: %s\nAvatar url: %s",
                id,firstName,lastName,gender,email,avatar
        );

    }

    public static class UserSerializer implements JsonSerializer<User> {

        @Override
        public JsonElement serialize(User user, Type type, JsonSerializationContext context) {
            return user.getSerializedJSON(context);
        }
    }

    private JsonObject getSerializedJSON(JsonSerializationContext context) {

        JsonObject object = new JsonObject();

        object.add("Name", context.serialize(getId()));
        object.add("Name", context.serialize(StringUtils.simpleFormat(" ", new Object[]{ getLastName() , getFirstName() })));
        object.add("Name", context.serialize(getEmail()));

         return object;
    }
}
