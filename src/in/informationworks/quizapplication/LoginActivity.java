package in.informationworks.quizapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import in.informationworks.quizapplication.HomeActivity;
import in.informationworks.quizapplication.db.DBHelper;
import in.informationworks.quizapplication.db.DataAccess;

public class LoginActivity extends Activity implements OnClickListener {
	EditText edtxtEmail;
	EditText edtxtPassword;
	Button btnLogin;
	long check;
	String email;
	String password;
	DBHelper DB = null;
	DataAccess dao;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        btnLogin = (Button)findViewById(R.id.btn_Login);
        edtxtEmail = (EditText)findViewById(R.id.email_txt);
		edtxtPassword = (EditText)findViewById(R.id.password_txt);
		
        btnLogin.setOnClickListener(this);
        Button btn_newuser=(Button) findViewById(R.id.btn_newuser);
        btn_newuser.setOnClickListener(this);
        dao = new DataAccess(this);
        
    }

	@Override
	public void onClick(View v)
	{
		Intent i;
		switch(v.getId()) {
		case R.id.btn_Login:
			
			if (validateLoginInput()) {
				validateLogin(email, password);
			}
			break;
		
		case R.id.btn_newuser:
			i=new Intent(this,RegisterActivity.class);
			startActivity(i);
			break;
		}
			
	}
	
	/*
	 Checks if Email id and password are entered or not.
	 */
	private boolean validateLoginInput() {
		
		boolean valid = true;
		
		email = edtxtEmail.getText().toString();
		password = edtxtPassword.getText().toString();
		if(email.equals("") || email == null) {
			valid = false;
			Toast.makeText(getApplicationContext(), "Please enter your Email address", Toast.LENGTH_SHORT).show();
		}
		else if(password.equals("") || password == null) {
			valid = false;
			Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
		}
		return valid;	
	}	
	/*
	 If login credentials matches, user gets logged in and if not, error message is displayed.
	 */
	 private void validateLogin(String email, String password) {
		 int user_id = dao.ValidateCredentialAndGetId(email, password);
		 if(user_id == -1)
		 {
			 Toast.makeText(getApplicationContext(), "Login failed. Try again.", Toast.LENGTH_SHORT).show();
		 }
		 else
		 {
			
			 Intent in = new Intent(getBaseContext(), HomeActivity.class);
			 startActivity(in);
			 finish(); 
		 }
	 }   
}