package example.com.inclass01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    Button saveChanges;
    UserProfile user;
    String token;
    EditText name;
    EditText age;
    EditText weight;
    EditText addr;
    JSONObject userParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = (EditText) findViewById(R.id.editTextSUser);
        age = (EditText)findViewById(R.id.editTextAge);
        weight = (EditText)findViewById(R.id.editTextWeight);
        addr = (EditText)findViewById(R.id.editTextAaddress);
        userParams = new JSONObject();//new HashMap<>();

        if(getIntent().getExtras() != null){
            user = (UserProfile) getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
            name.setText(user.getName());
            age.setText(user.getAge() + "");
            weight.setText(user.getWeight() + "");
            addr.setText(user.getAddress());
            token = getIntent().getExtras().getString(MainActivity.TOKEN_KEY);

        }

        saveChanges = (Button)findViewById(R.id.buttonCreateAcc);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    userParams.put("Name",(((EditText) findViewById(R.id.editTextSUser)).getText()).toString());
                userParams.put("Age",(((EditText) findViewById(R.id.editTextAge)).getText()).toString());
                Log.d("user",(((EditText) findViewById(R.id.editTextSUser)).getText()).toString());
                userParams.put("Address",(((EditText) findViewById(R.id.editTextAaddress)).getText()).toString());
                userParams.put("Weight",(((EditText) findViewById(R.id.editTextWeight)).getText()).toString());
                userParams.put("Email","test@gmail.com");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("token1",token);
                new UpdateProfileAsyncTask(new UpdateProfileAsyncTask.IGetProfile() {
                    @Override
                    public void getUserProfile() {
                        new GetProfileAsyncTask(new GetProfileAsyncTask.IGetProfile() {
                            @Override
                            public void getUserProfile(UserProfile userProfile) {
                                //Log.d("user",user.toString());
                                Log.d("user",userProfile.getName().toString());
                                user = userProfile;
                                Intent intent = new Intent(EditProfileActivity.this,ProfileActivity.class);
                                intent.putExtra(MainActivity.USER_KEY,user);
                                intent.putExtra(MainActivity.TOKEN_KEY,token);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }, token).execute("http://apidevelopment-inclass01.azurewebsites.net/api/Account/UserInfo");
                    }
                }, token, userParams).execute("http://apidevelopment-inclass01.azurewebsites.net/api/Account/UpdateProfile");


            }
        });
    }
}
