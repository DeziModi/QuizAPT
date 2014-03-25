package in.informationworks.quizapplication;

import in.informationworks.quizapplication.db.DBHelper;
import in.informationworks.quizapplication.db.DataAccess;
import in.informationworks.quizapplication.LoginActivity;
import in.informationworks.quizapplication.HomeActivity;
import in.informationworks.quizapplication.R;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.app.Activity;
import android.content.Intent;

public class SplashActivity extends Activity {

	DBHelper dbHelper;
	ProgressBar progressBar;
	private DataAccess dao;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		 
		try
		{
			new Thread()
			{
				@Override
				public void run()
				{
					try
					{
						synchronized(this)
						{
							dbHelper = new DBHelper(SplashActivity.this);
							dbHelper.createDataBase();
							dbHelper.close();
						}
						sleep(2000);
					}
					catch(Exception ex)
					{
						return;
					}
					
					dao = new DataAccess(SplashActivity.this);
					final int userId = dao.getUserid();
					
					
					if(userId == -1)
					{
						Intent intent = new Intent();
						intent.setClass(SplashActivity.this, LoginActivity.class);
						startActivity(intent);
						finish();
						
					}
					else
					{
						Intent intent = new Intent();
						intent.setClass(SplashActivity.this, HomeActivity.class);
						startActivity(intent);
						finish();
					}
					finish();
				}
			}.start();
		}
		catch(Exception e)
		{
			//TODO:hande exception
		}
	}
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
}