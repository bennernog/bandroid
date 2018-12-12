package mobile.bny.bandroidapp.data;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;

//import mobile.bny.androidtools.InternetConnection;
//import mobile.bny.jsontools.GsheetJsonFetcher;
//import mobile.bny.jsontools.GsheetJsonPoster;
//import mobile.bny.jsontools.JsonDownloadWrapper;
//import mobile.bny.jsontools.JsonResponse;
//import mobile.bny.jsontools.JsonUtils;


/**
 * Created by BNY on 25/11/2017.
 * Data created on http://mockaroo.com/
 */

public class UserData {

    /** Set share options to anyone with link can view and published the sheet to web
    *   document url: https://docs.google.com/spreadsheets/d/1sAsK4eYLnFPgNd47hvJj9sjyYPKKkg9tFehgaleUOA8/edit#gid=1413508137
    *   document url contains sheetId
    */

    private static final String sheetId = "1sAsK4eYLnFPgNd47hvJj9sjyYPKKkg9tFehgaleUOA8";
    private static final String sheetName = "result";

    private ArrayList<User> data;
    public ArrayList<User> getData() {
        return data;
    }
    public void setData(ArrayList<User> data) {
        this.data = data;
    }

    public static UserData DOWNLOAD(){
        Gson gson = new Gson();
//        JsonDownloadWrapper downloadWrapper = GsheetJsonFetcher.getJSONString(sheetId);
//
//        if(downloadWrapper.getResponse() == JsonResponse.SUCCESS) {
//
//            String jsonString = downloadWrapper.getResult();
//            return jsonString != null ? gson.fromJson(jsonString, UserData.class) : null;
//
//        } else
            return null;
    }

//    public static JsonResponse UPLOAD(Context context, ArrayList<User> users){
//
//        if (InternetConnection.isConnected(context)) {
//
//            Gson gson = JsonUtils.getGson(User.class, new User.UserSerializer());
//
//            String jsonString = gson.toJson(users);
//
//            return GsheetJsonPoster.postJSONString(sheetId,sheetName,jsonString);
//
//        } else return JsonResponse.NO_CONNECTION;
//    }
}
