package example.com.inclass01;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Nitin on 8/31/2017.
 */

public class SignupAsyncTask extends AsyncTask<String,Void,String> {

    HashMap<String,String> parameters;

    IGetMessage iGetMessage;

    public interface IGetMessage{
        void getSignUpMessage(String message);
    }

    public SignupAsyncTask(IGetMessage iMessage, HashMap<String,String> userParams){
        iGetMessage = iMessage;
        parameters = userParams;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(params[0]);


                con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                //con.setRequestProperty("Authorization", "Bearer " + token);
                //con = params[0].createConnection();
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(getEncodedParams());
                writer.flush();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            if(con.getResponseCode() == 200)
            return "Account created succesfully";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Account could not be created";
    }

    public String getEncodedParams(){
        StringBuilder sb = new StringBuilder();
        for (String key:parameters.keySet()) {
            try {
                String value = URLEncoder.encode(parameters.get(key),"UTF-8");
                if(sb.length() > 0){
                    sb.append("&");
                }
                sb.append(key + "=" + value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);
        iGetMessage.getSignUpMessage(message);
    }

}
