package in.informationworks.quizapplication;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;


public class QuizquestionActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quizquestions);
		
		Button prvBtn=(Button) findViewById(R.id.prvBtn);
        prvBtn.setOnClickListener(this);
        Button nextBtn=(Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View V) {
		Intent i;
		switch(V.getId()){
		
		case R.id.prvBtn:
			i=new Intent(this,SubcategoryActivity.class);
			startActivity(i);
			break;
		case R.id.nextBtn:
			i=new Intent(this,ScoreBoardActivity.class);
			startActivity(i);
			break;
		
		}
		}
	}
