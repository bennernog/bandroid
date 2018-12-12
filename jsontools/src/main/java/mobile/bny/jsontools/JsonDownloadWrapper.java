package mobile.bny.jsontools;

public class JsonDownloadWrapper{
    String result;
    JsonResponse response;

    public JsonDownloadWrapper(JsonResponse response) {
        this.response = response;
    }

    public JsonDownloadWrapper(String result, JsonResponse response) {
        this.result = result;
        this.response = response;
    }

    public String getResult() {
        return result;
    }

    public JsonResponse getResponse() {
        return response;
    }
}
