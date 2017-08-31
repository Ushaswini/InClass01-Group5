package example.com.inclass01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button login;
    TextView createAcc;
    UserProfile user;
    final static String USER_KEY = "USER";
    final static String TOKEN_KEY = "TOKEN";
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.buttonLogin);
        createAcc = (TextView)findViewById(R.id.textViewSignUp);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = ((EditText) findViewById(R.id.editTextUsername)).getText().toString();
                String pwd = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
                Log.d("demo",username);
                Log.d("demo",pwd);
                final RequestParams params = new RequestParams("POST","http://apidevelopment-inclass01.azurewebsites.net/Token");
                params.addParams("username",username);
                params.addParams("password",pwd);
                params.addParams("grant_type","password");

               // final RequestParams params1 = new RequestParams("GET","http://apidevelopment-inclass01.azurewebsites.net/api/Account/UserInfo");
                /*params1.addParams("username",username);
                params1.addParams("password",pwd);
                params1.addParams("grant_type","password");*/

                new GetTokenTask(new GetTokenTask.IGetTokenString() {
                    @Override
                    public void getTokenForUser(String s) {
                        token = s;
                        //Toast.makeText(MainActivity.this,token,Toast.LENGTH_LONG).show();
                        Log.d("demo",token);

                        if(token.length() != 0 && token != null) {
                            //params1.addParams("Authorization","Bearer " + token);
                            new GetProfileAsyncTask(new GetProfileAsyncTask.IGetProfile() {
                                @Override
                                public void getUserProfile(UserProfile userProfile) {
                                    //Log.d("user",user.toString());
                                    user = userProfile;
                                    Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                                    intent.putExtra(USER_KEY,user);
                                    intent.putExtra(TOKEN_KEY,token);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }, token).execute("http://apidevelopment-inclass01.azurewebsites.net/api/Account/UserInfo");
                        }else{
                            Toast.makeText(MainActivity.this,"Username/Password is incorrect",Toast.LENGTH_LONG).show();
                        }
                    }
                }).execute(params);
              /*  new GetProfileAsyncTask(new GetProfileAsyncTask.IGetProfile() {
                    @Override
                    public void getUserProfile(UserProfile userProfile) {
                        user = userProfile;
                    }
                }).execute();*/
                //Toast.makeText(MainActivity.this,token,Toast.LENGTH_LONG).show();

                /*Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                intent.putExtra(USER_KEY,user);
                startActivity(intent);*/
            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}
