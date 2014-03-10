package in.informationworks.quizapplication;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;


public class ScoreBoardActivity extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_board);
		
		Button homeBtn=(Button) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i = new Intent(ScoreBoardActivity.this,HomeActivity.class);
		startActivity(i);
		
	}
	
}
