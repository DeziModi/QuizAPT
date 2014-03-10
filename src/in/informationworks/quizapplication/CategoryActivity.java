package in.informationworks.quizapplication;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;


public class CategoryActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);
		
		TextView category= (TextView) findViewById(R.id.category);
		category.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
	
	Intent i = new Intent(CategoryActivity.this,SubcategoryActivity.class);
	startActivity(i);
    }
}
