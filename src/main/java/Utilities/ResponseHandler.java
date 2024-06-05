package Utilities;

import com.google.gson.JsonObject;

public class ResponseHandler {

    public static JsonObject createSuccessResponse(String message, JsonObject data) {
        JsonObject response = new JsonObject();
        response.addProperty("result", "OK");
        JsonObject responseData = new JsonObject();
        responseData.addProperty("message", message);
        if (data != null) {
            responseData.add("data", data);
        }
        response.add("response", responseData);
        return response;
    }

    public static JsonObject createErrorResponse(String message) {
        JsonObject response = new JsonObject();
        response.addProperty("result", "ERROR");
        JsonObject responseData = new JsonObject();
        responseData.addProperty("message", message);
        response.add("response", responseData);
        return response;
    }
}
