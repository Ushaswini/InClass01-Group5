package example.com.inclass01;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by Nitin on 8/30/2017.
 */

public class GetTokenTask extends AsyncTask<RequestParams, Void, String> {



    public interface IGetTokenString{
        void getTokenForUser(String s);
    }

    IGetTokenString iGetTokenString;

    public GetTokenTask(IGetTokenString tokenString){
        iGetTokenString = tokenString;
    }

    @Override
    protected String doInBackground(RequestParams... params) {
        StringBuilder sb = new StringBuilder();
        String result = "";
        try {

            HttpURLConnection con = params[0].createConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line = "";

            while ((line = reader.readLine()) != null){
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONUtil.parseTokenJSON(sb.toString());
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        iGetTokenString.getTokenForUser(s);
    }
}
