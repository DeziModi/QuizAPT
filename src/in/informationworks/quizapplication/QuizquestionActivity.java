package in.informationworks.quizapplication;

import in.informationworks.quizapplication.db.DataAccess;
import in.informationworks.quizapplication.model.Option;
import in.informationworks.quizapplication.model.Question;
import in.informationworks.quizapplication.model.Quiz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


public class QuizquestionActivity extends Activity {
	
	Button prvBtn;
	Button nxtBtn;
	int currentQuestionIndex = 0;
	int currentOptionIndex = 0;
	long quizId;
	int totalQuestions;
	int position;
	int noOfOptions;
	long optionId;
	int score = 0;
	TextView questionTextView;
	TextView quizNameTextView;
	RadioGroup RadioGroup;
	Question currentQue;
	Option currentOption;
	DataAccess dao;
	Quiz quiz;
	Option option;
	Question question;
	List<Question> quesList;
	List<Option> optList;
	final RadioButton[] optionRB = new RadioButton[10];
	long currentQueId = 0;
	Context context;
	boolean checkAnswer;
	String currentDateandTime;
	
	Calendar c;
	long attemptId;
	

	@Override
	
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.quizquestions);
		
			dao = new DataAccess(this);
			
			
			quizId = getIntent().getExtras().getLong(Utility.QUIZ_ID);
			totalQuestions = getIntent().getExtras().getInt(Utility.NO_OF_QUESTIONS);
			
			Time now = new Time(Time.getCurrentTimezone());
			now.setToNow();
			//attemptDate = now.format(new Date());
			//attemptTime = now.format(new Time());
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			currentDateandTime = dateFormat.format(new Date());
			
			attemptId = dao.insertQuizAttempt(quizId, dao.getUserid(), currentDateandTime);
			
			currentQuestionIndex = 0;
			quiz = dao.getQuiz(quizId);
			quesList = dao.getAllQuestions(quizId);
			
			questionTextView = (TextView)findViewById(R.id.question);
			quizNameTextView = (TextView) findViewById(R.id.QuizName);
			nxtBtn = (Button) findViewById(R.id.nextBtn); 
			prvBtn = (Button) findViewById(R.id.prvBtn);
			RadioGroup = (RadioGroup) findViewById (R.id.radioGroup);
			
			setQuestionView();
			
			nxtBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					currentQuestionIndex++;
					if(currentQuestionIndex == (totalQuestions)) {
						Intent intent = new Intent(QuizquestionActivity.this, ScoreBoardActivity.class);
						intent.putExtra(Utility.USER_SCORE, score);
						intent.putExtra(Utility.DATE_AND_TIME, currentDateandTime);
						startActivity(intent);
						finish();
					} else {
						setQuestionView();
					}
				}
			});
			
			prvBtn.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					currentQuestionIndex--;
					//Hide previous button if on first question
					if(currentQuestionIndex == 0) 
					{
						prvBtn.setEnabled(false);
					}
					else
					{
						prvBtn.setEnabled(true);
					}
					setQuestionView();
				}
			});
		}
		
		private void setQuestionView() {
			currentQue = quesList.get(currentQuestionIndex);
			
			questionTextView.setText(currentQue.getQuestion());
			quizNameTextView.setText(quiz.getName());
			
			if(currentQuestionIndex == 0) 
			{
				prvBtn.setEnabled(false);
			}
			else
			{
				prvBtn.setEnabled(true);
			}
			
			optList = dao.getAllOptions(currentQue.getQueId());		
			noOfOptions = optList.size();
			RadioGroup.removeAllViews();
			
			for (int i=0; i<noOfOptions; i++) {
				currentOption = optList.get(i);
				optionRB[i] = new RadioButton(this);
				RadioGroup.addView(optionRB[i]);
				optionRB[i].setText(currentOption.getOptTxt());
				/*if( optionRB[i].isChecked() == true)
				{
					radioGroup.check(R.id.radioGroup);
					optionRB[i].setChecked(true);
				}
				else
				{
					radioGroup.check(R.id.radioGroup);
					optionRB[i].setChecked(true);
				}*/
			}
			
			currentQueId = currentQuestionIndex;
			
			RadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					
					int selectedOptionIndex = RadioGroup.indexOfChild(findViewById(RadioGroup.getCheckedRadioButtonId()));
					currentOption = optList.get(selectedOptionIndex);
					optionId = currentOption.getOptId();
					checkAnswer = dao.checkCorrectnessOfAnswer(optionId);
					if(checkAnswer == true)
					{
						score ++;
					}
					else
					{
						//do nothing!
					}
					
					//dao.insertAttemptDetail(optionId, attemptId);
					
					/*if( optionRB[selectedOptionIndex].isChecked() == true)
					{
						//radioGroup.check(R.id.radioGroup);
						optionRB[selectedOptionIndex].setChecked(true);
						attemptId = dao.insertQuizAttempt(quizId, spa.getUserId(), attemptTime, attemptDate);
					}
					else
					{
						//radioGroup.check(-1);
						optionRB[selectedOptionIndex].setChecked(true);
					}*/
				}
			});
		}
		
		@Override
		public void onBackPressed()
		{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);
	 
				alertDialogBuilder.setTitle("Do you want to quit the Quiz?");
	 
				alertDialogBuilder
					.setMessage("Click yes to quit!")
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							QuizquestionActivity.this.finish();
						}
					  })
					.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
	 
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					alertDialog.show();
		}
	}
