package in.informationworks.quizapplication;

import in.informationworks.quizapplication.db.DBHelper;
import in.informationworks.quizapplication.db.DataAccess;
import in.informationworks.quizapplication.model.Quiz;

import java.util.List;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class CategoryActivity extends ListActivity {
	DBHelper db;
	DataAccess dao;
	Cursor c;
	List<Quiz> quizzes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dao = new DataAccess(this);
        quizzes = dao.getAllQuizzes();
        ArrayAdapter<Quiz> adapter = new ArrayAdapter<Quiz>(this,R.layout.category,R.id.category ,quizzes);
        setListAdapter(adapter);
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		Intent i = new Intent();
		i.setClass(CategoryActivity.this, QuizquestionActivity.class);
		i.putExtra(Utility.QUIZ_ID, quizzes.get(position).getId());
		startActivity(i);   
		}
}
