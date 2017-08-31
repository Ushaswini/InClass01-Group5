package example.com.inclass01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import static example.com.inclass01.R.styleable.MenuItem;

public class ProfileActivity extends AppCompatActivity {

    TextView name,age,weight,address;
    String token;
    UserProfile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (TextView) findViewById(R.id.textViewNameValue);
        age = (TextView) findViewById(R.id.textViewAgeValue);
        weight = (TextView) findViewById(R.id.textViewWtValue);
        address = (TextView) findViewById(R.id.textViewAddrvalue);

        if(getIntent().getExtras() != null){
            user = (UserProfile) getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
            name.setText(user.getName());
            age.setText(user.getAge() + "");
            weight.setText(user.getWeight() + "");
            address.setText(user.getAddress());
            token = getIntent().getExtras().getString(MainActivity.TOKEN_KEY);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            RequestParams params = new RequestParams("POST","http://apidevelopment-inclass01.azurewebsites.net/api/Account/Logout");
            params.addParams("Authorization","Bearer " + token);
            new LogoutAsyncTask().execute(params);
            finish();
        }else if (id == R.id.editButton){
            Intent intent = new Intent(ProfileActivity.this,EditProfileActivity.class);
            intent.putExtra(MainActivity.TOKEN_KEY,token);
            intent.putExtra(MainActivity.USER_KEY,user);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
