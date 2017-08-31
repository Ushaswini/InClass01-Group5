package example.com.inclass01;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created by Nitin on 8/30/2017.
 */

public class UpdateProfileAsyncTask extends AsyncTask<String,Void,Void> {

    String token;
    JSONObject parameters;
    IGetProfile iGetProfile;

    public interface IGetProfile{
        void getUserProfile();
    }



    public UpdateProfileAsyncTask(IGetProfile getProfile, String s, JSONObject userParams){
        iGetProfile = getProfile;
        token = s;
        parameters = userParams;
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            Log.d("tokenUpdate",token);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization","Bearer " + token);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("Content-Length", parameters.toString().getBytes().length + "");
            con.setRequestProperty("Accept","application/json");
            con.setDoOutput(true);
            OutputStream writer = con.getOutputStream();
            writer.write(parameters.toString().getBytes("UTF-8"));
            Log.d("userresponse",con.getResponseMessage() + con.getResponseCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        iGetProfile.getUserProfile();
    }


}
