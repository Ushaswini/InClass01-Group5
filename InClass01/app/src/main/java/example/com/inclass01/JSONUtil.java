package example.com.inclass01;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nitin on 6/6/2017.
 */

public class JSONUtil {

    public static UserProfile parseUserJSON(String s){
        UserProfile user = new UserProfile();
        try {
            JSONObject root = new JSONObject(s);
            //JSONArray newsArray = root.getJSONArray("articles");
            //for(int i = 0; i < newsArray.length(); i++) {
                //JSONObject newsJSONObject = newsArray.getJSONObject(i);
                /*Article article = new Article();
                article.setTitle(newsJSONObject.getString("title"));
                article.setAuthor(newsJSONObject.getString("author"));
                article.setPublishedAt(newsJSONObject.getString("publishedAt"));
                article.setDescrition(newsJSONObject.getString("description"));
                article.setImageToUrl(newsJSONObject.getString("urlToImage"));*/

            if(root.has("Name")){
                user.setName(root.getString("Name"));
            }
            if(root.has("Address")) {
                user.setAddress(root.getString("Address"));
            }
            if(root.has("Age")) {
                user.setAge(root.getDouble("Age"));
            }
            if(root.has("Weight")) {
                user.setWeight(root.getDouble("Weight"));
            }


                //articleList.add(article);
            //}

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static String parseTokenJSON(String s)  {
        JSONObject root;// = null;
        String token = "";
        try {
            root = new JSONObject(s);
            Log.d("demo",s);
            token = root.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return token;
    }
}
