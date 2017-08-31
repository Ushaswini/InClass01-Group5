package example.com.inclass01;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Nitin on 8/30/2017.
 */

public class LogoutAsyncTask extends AsyncTask<RequestParams,Void,Void> {
    @Override
    protected Void doInBackground(RequestParams... params) {
        try {
            HttpURLConnection con = params[0].createConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
