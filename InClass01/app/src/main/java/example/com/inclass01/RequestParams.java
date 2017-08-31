package example.com.inclass01;

/**
 * Created by Nitin on 6/6/2017.
 */

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;


/**
 * Created by Nitin on 6/4/2017.
 */

public class RequestParams {

    String methodType, baseUrl;
    HashMap<String,String> params = new HashMap<String, String>();

    public RequestParams(String methodType, String baseUrl) {
        this.methodType = methodType;
        this.baseUrl = baseUrl;
    }

    public HttpURLConnection createConnection() throws IOException {
        HttpURLConnection con = null;
        if(methodType == "GET") {
            URL url = new URL(getEncodedURL());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(methodType);
        }else{
            URL url = new URL(this.baseUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(methodType);
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(getEncodedParams());
            writer.flush();
        }
        return con;
    }

    public void addParams(String key,String value){
        this.params.put(key,value);
    }

    public String getEncodedParams(){
        StringBuilder sb = new StringBuilder();
        for (String key:params.keySet()) {
            try {
                String value = URLEncoder.encode(params.get(key),"UTF-8");
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

    public String getEncodedURL(){
        return this.baseUrl + "?" + this.getEncodedParams();
    }
}

