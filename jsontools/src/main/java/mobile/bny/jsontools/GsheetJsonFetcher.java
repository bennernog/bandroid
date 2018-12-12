package mobile.bny.jsontools;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by BNY on 11/11/2017.
 */

public class GsheetJsonFetcher {

    private static final String BASE_URL = "https://script.google.com/macros/s/AKfycbwW949EsesMnaVOn8FFwzVgRUR9akju2cvVK-8cTESoqaV5klY/exec?id=%s";

    /**
     * This method fetches your published Google sheet as a JSON-string.
     *
     * @param sheetId String: the id for your sheet, as in https://docs.google.com/spreadsheets/d/>>>your_sheet_id<<</edit#gid=0.
     *
     * @return String: JSON formatted string of your Google Sheet.
     */
    public static JsonDownloadWrapper getJSONString(String sheetId) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(String.format(BASE_URL, sheetId))
                    .build();
            Response response = client.newCall(request).execute();

            return new JsonDownloadWrapper(response.body().string(), JsonResponse.SUCCESS);

        } catch (@NonNull IOException e) {
            e.printStackTrace();
            return new JsonDownloadWrapper(JsonResponse.FAILURE);
        }
    }

    /**
     * Method that returns a request URL that fetches a JSON representation of a published Google sheet
     * @param sheetId String: the id for your sheet, as in https://docs.google.com/spreadsheets/d/>>>your_sheet_id<<</edit#gid=0.
     * @return String: URL of google script that fetches JSON
     */
    public static String getURLString(String sheetId){
        return TextUtils.isEmpty(sheetId) ? null : String.format(BASE_URL,sheetId);
    }
}
