package example.com.inclass01;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nitin on 8/27/2017.
 */

public class GetProfileAsyncTask extends AsyncTask<String,Void,UserProfile> {

    IGetProfile iGetProfile;
    String token;

    public GetProfileAsyncTask(IGetProfile iProfile, String s) {
        iGetProfile = iProfile;
        token = s;
    }

    public interface IGetProfile{
        void getUserProfile(UserProfile userProfile);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected UserProfile doInBackground(String... params) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection  con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization","Bearer " + token);
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line = "";

            while ((line = reader.readLine()) != null){
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
           return JSONUtil.parseUserJSON(sb.toString());
    }

    @Override
    protected void onPostExecute(UserProfile userProfile) {
        super.onPostExecute(userProfile);
        iGetProfile.getUserProfile(userProfile);
    }
}
