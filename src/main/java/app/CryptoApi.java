package app;

import app.controller.CryptoCurrency;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;

public class CryptoApi {

    public static CryptoCurrency[] cryptoArr;
    public final static int cryptoArrLength = 20;


    public static void main(String[] args) throws UnirestException, JSONException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("https://api.livecoinwatch.com/coins/list")
                .header("x-api-key", "2038e638-d524-484d-9881-43944290c22f")
                .header("Content-Type", "application/json")
                .body("{\"currency\":\"RON\",\"sort\":\"rank\",\"order\":\"ascending\",\"offset\":0,\"limit\":"+cryptoArrLength+",\"meta\":false}")
                .asString();

        System.out.println(response.getBody());
        String json=response.getBody(); // place your json format here in double Quotes with proper escapes .......
        Gson gson = new Gson();
        cryptoArr = gson.fromJson(json, CryptoCurrency[].class);
        for(CryptoCurrency crypto : cryptoArr) {
            System.out.println(crypto);
        }

    }

}
