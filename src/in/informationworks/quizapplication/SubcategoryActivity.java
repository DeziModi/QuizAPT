package in.informationworks.quizapplication;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class SubcategoryActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subcategory);

		TextView subcategory= (TextView) findViewById(R.id.subcategory);
		subcategory.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
	
	Intent i = new Intent(SubcategoryActivity.this,QuizquestionActivity.class);
	startActivity(i);
	}

}
