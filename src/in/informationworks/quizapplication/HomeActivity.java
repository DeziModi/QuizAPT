package in.informationworks.quizapplication;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;


public class HomeActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		Button playBtn=(Button) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(this);
        Button logoutBtn=(Button) findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View V) {
		Intent i;
		switch(V.getId()){
		
		case R.id.playBtn:
			i=new Intent(this,CategoryActivity.class);
			startActivity(i);
			break;
		case R.id.logoutBtn:
			i=new Intent(this,LoginActivity.class);
			startActivity(i);
			break;
		
		}
	}
}