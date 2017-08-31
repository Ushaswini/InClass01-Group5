package example.com.inclass01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    Button btnCreate;
    EditText pwd;
    EditText confirmPwd;
    EditText name;
    EditText age;
    EditText weight;
    EditText addr;
    EditText email;
    HashMap<String,String> userParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnCreate = (Button) findViewById(R.id.buttonCreateAcc);
        pwd = (EditText)findViewById(R.id.editTextPwd);
        confirmPwd = (EditText)findViewById(R.id.editTextConfirmPwd);
        name = (EditText) findViewById(R.id.editTextSUser);
        age = (EditText)findViewById(R.id.editTextAge);
        weight = (EditText)findViewById(R.id.editTextWeight);
        addr = (EditText)findViewById(R.id.editTextAaddress);
        email = (EditText)findViewById(R.id.editTextEmail);

        userParams = new HashMap<>();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(validateInput().length() == 0 || validateInput().equals("")){
                    Toast.makeText(SignUpActivity.this,validateInput(),Toast.LENGTH_SHORT);
                }else if(!matchPasswords(pwd.getText().toString(),confirmPwd.getText().toString())){
                    Toast.makeText(SignUpActivity.this,"Passwords do not match",Toast.LENGTH_SHORT);
                }else if(validateInput().length() == 0 || validateInput().equals("")){
                    Toast.makeText(SignUpActivity.this,validateInput(),Toast.LENGTH_SHORT);
                }else{*/

                    userParams.put("Name",name.getText().toString());
                    userParams.put("Age",age.getText().toString());
                    userParams.put("Weight",weight.getText().toString());
                    userParams.put("Address",addr.getText().toString());
                    userParams.put("Email",email.getText().toString());
                    userParams.put("Password",pwd.getText().toString());
                    userParams.put("ConfirmPassword",confirmPwd.getText().toString());

                    new SignupAsyncTask(new SignupAsyncTask.IGetMessage() {
                        @Override
                        public void getSignUpMessage(String message) {
                            Toast.makeText(SignUpActivity.this,message,Toast.LENGTH_SHORT).show();
                            if(message.equals("Account created succesfully")) {
                            Log.d("message",message);
                                finish();
                            }
                        }
                    },userParams).execute("http://apidevelopment-inclass01.azurewebsites.net/api/Account/Register");
                //}
            }
        });

    }

    public boolean matchPasswords(String pwd1, String pwd2){
        if(pwd1.equals(pwd2)){
            return true;
        }
        return false;
    }

   public String validateInput(){
        if(name.getText().toString().equals("") || name.getText().toString().length() == 0){
            return "Please enter proper name";
        }else if(age.getText().toString().equals("") || age.getText().toString().length() == 0){
            return "Please enter proper age";
        }else if(weight.getText().toString().equals("") || weight.getText().toString().length() == 0){
            return "Please enter proper weight";
        }else if(addr.getText().toString().equals("") || addr.getText().toString().length() == 0){
            return "Please enter proper adress";
        }else if(pwd.getText().toString().equals("") || pwd.getText().toString().length() == 0){
            return "Please enter proper password";
        }else if(confirmPwd.getText().toString().equals("") || confirmPwd.getText().toString().length() == 0){
            return "Please enter proper confirm password";
        }
        return "";
    }


}
