package in.informationworks.quizapplication.db;

import java.util.ArrayList;
import java.util.List;

import in.informationworks.quizapplication.db.DBHelper;
import in.informationworks.quizapplication.model.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DataAccess {

	private SQLiteDatabase db;
	private DBHelper dbHelper = null;
	Context context;
	
	
	public DataAccess(Context context) {
		dbHelper = new DBHelper(context);
		
	}
	
	public long insertUser(String name, String email, String pass) {
		long id = -1;
		try {
			ContentValues values = new ContentValues();
			values.put("name", name);
			values.put("password", pass);
			values.put("email_id", email);
			
			db = dbHelper.getWritableDatabase();
			id = db.insert(DBHelper.USERS_TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	/*
	 Compare entered email id and password with the ones in database to check if they are correct or not.
	 */
	public boolean ValidateLoginCredentials(String email, String password) {
		 db = dbHelper.getReadableDatabase();
		 String[] columns = {"_id"};
		 String selection = "email_id=? AND password=?";
		 String[] selectionArgs = {email,password};
		 Cursor cursor = null;
	 	 cursor = db.query(DBHelper.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
			 
	 	 int numberOfRows = cursor.getCount();
	 
	 	 if(numberOfRows <= 0)
	 	 {
	 		 return false;
	 	 }
	 	 else
	 		 return true;
	}
	
	public int ValidateCredentialAndGetId(String email, String password) {
		int user_id = -1;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] columns = {"_id"};
		String selection = "email_id=? AND password=?";
		String[] selectionArgs = {email,password};
		Cursor cursor = null;
		cursor = db.query(DBHelper.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		
		int numberOfRows = cursor.getCount();
		
		if(numberOfRows <= 0)
	 	 {
	 		 return user_id;
	 	 }
	 	 else
	 	 {
	 		cursor.moveToFirst();
	 		user_id = cursor.getInt(cursor.getColumnIndex("_id"));
	    	return user_id;
	 	 }
	}
	//Checks if answer is correct or not
		public boolean checkCorrectnessOfAnswer(long optId) {
			boolean isCorrect = false;
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			String[] columns = {"correct"};
			String selection = "_id = ?";
			String[] selectionArg = {String.valueOf(optId)};
			
			Cursor cursor = null;
			cursor = db.query(DBHelper.OPTION_TABLE_NAME, columns, selection, selectionArg, null, null, null);
			if(cursor.getCount()  > 0) {
				cursor.moveToFirst();
				//trueOrFalse = cursor.getString(cursor.getColumnIndex("correct"));
				isCorrect = cursor.getString(cursor.getColumnIndex("correct")).equalsIgnoreCase("true");
				
				
			}
			cursor.close();
			return isCorrect;
		}
	/*
	 Checks before creating a new user if user with the same email id exists or not.
	 */
	public boolean CheckIfUserAlreadyExist(String email) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		 String[] columns = {"_id"};
		 String selection = "email_id=?";
		 String[] selectionArg = {email};
		 Cursor cursor = null;
	 	 cursor = db.query(DBHelper.USERS_TABLE_NAME, columns, selection, selectionArg, null, null, null);
	 	 
	 	 int numberOfRows = cursor.getCount();
	 	 
	 	 if(numberOfRows <= 0) {
	 		 return false;
	 	 }
	 	 else
	 		 return true;
	 }

	
	public long updateUser(ContentValues values, int userId) {
		long id = -1;
		try {
			db = dbHelper.getWritableDatabase();
			id = db.update(DBHelper.USERS_TABLE_NAME, values,
					BaseColumns._ID + "=" + userId, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public User getUserById(int userId) { 
		
		User user = null;
		
		// open db
		
		// user retrive 
		
		// return
		
		return user;
		
	}
	
	public int getUserid() {
		int userId = -1;
		try {
			db = dbHelper.getReadableDatabase();
			if (db != null) {
				Cursor cursor = db.rawQuery("select _id from "
						+ DBHelper.USERS_TABLE_NAME, null);
				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					userId = cursor.getInt(cursor.getColumnIndex("_id"));
				}
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbHelper.close();
		}
		return userId;
	}
	public long getQueId(long quizId) {
		long queId = -1;
		db = dbHelper.getReadableDatabase();
		
		return queId;
	}
	
	
	public User getUser(int userId) {
		User user = null;
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from "
					+ DBHelper.USERS_TABLE_NAME + " where _id = '"
					+ String.valueOf(userId) + "'", null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				user = cursorToUser(cursor);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public boolean isRegistered(int userId) {
		boolean isRegistered = false;
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from "
					+ DBHelper.USERS_TABLE_NAME + " where _id = '"
					+ String.valueOf(userId) + "'", null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				isRegistered = cursor.getString(
						cursor.getColumnIndex("registered")).equalsIgnoreCase(
						"1") ? true : false;
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isRegistered;
	}

	//Gets Quiz name
	public Quiz getQuiz(long quizId) {
		Quiz quiz = null;
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from "
					+ DBHelper.QUIZZES_TABLE_NAME + " where _id = '"
					+ String.valueOf(quizId) + "'", null);
			cursor.moveToFirst();
			if (!cursor.isAfterLast()) {
				quiz = cursorToQuiz(cursor);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quiz;
	}
	
	//Gets a list of Quizzes	
    public List<Quiz> getAllQuizzes() {
    	List<Quiz> quizList = new ArrayList<Quiz>();
    	try {
    		db = dbHelper.getReadableDatabase();
    		Cursor cursor = db.query(DBHelper.QUIZZES_TABLE_NAME, null, null, null, null, null, null);
    		cursor.moveToFirst();
    		while(!cursor.isAfterLast()) {
    			Quiz quiz = cursorToQuiz(cursor);
    			quizList.add(quiz);
    			cursor.moveToNext();
    		}
    		cursor.close();
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return quizList;
    }
    
    //Gets time allowed to complete the quiz
    
    public int getTimeAllowed(long quizId) {
    	int cnt = -1;
		int timeAllowed = -1;
		db = dbHelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			if (db != null) {
				
				cursor = db.rawQuery("select count(_id) as quecnt from "
						+ DBHelper.QUESTION_TABLE_NAME
						+ " where quiz_id = ?", new String[] { String.valueOf(quizId) });
				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					cnt = cursor.getInt(cursor.getColumnIndex("quecnt"));
				}
				
				cursor = db.rawQuery("select time_allowed_per_question_in_minutes from " 
						+ DBHelper.QUIZZES_TABLE_NAME 
						+ " where _id = ?",
						new String[] { String.valueOf(quizId) });
				
				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					timeAllowed = cursor.getInt(cursor.getColumnIndex("time_allowed_per_question_in_minutes"));
				}
				
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt*timeAllowed;
	}
    
    //Gets no. of questions in the Quiz
    
    public int getNumberOfQuestionsInQuiz(long quizId) {
		int cnt = -1;
		try {
			db = dbHelper.getReadableDatabase();
			if (db != null) {
				Cursor cursor = db.rawQuery("select count(_id) as quecnt from "
						+ DBHelper.QUESTION_TABLE_NAME
						+ " where quiz_id = ?", new String[] { String.valueOf(quizId) });
				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					cnt = cursor.getInt(cursor.getColumnIndex("quecnt"));
				}
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
    
    public int getNumberOfOptionsInQuestion(long queId) {
    	int cnt = -1;
    	try {
    		db = dbHelper.getReadableDatabase();
    		if (db != null){
    			Cursor cursor = db.rawQuery("select count(_id) as optcnt from " 
    					+ DBHelper.OPTION_TABLE_NAME
    					+ " where que_id = ?", new String[] { String.valueOf(queId)});
    			if (cursor.getCount() > 0) {
    				cursor.moveToFirst();
    				cnt = cursor.getInt(cursor.getColumnIndex("optcnt"));
    			}
    			cursor.close();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return cnt;
    }
    
    public List<Option> getOptions(long queId) {
		List<Option> options = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db != null) {
				Cursor cursor = db.query(DBHelper.OPTION_TABLE_NAME,
						null, "q_id = " + "'" + queId + "'", null, null, null,
						null);
				if (cursor.getCount() > 0) {
					options = new ArrayList<Option>();
					cursor.moveToFirst();
					while (!cursor.isAfterLast()) {
						Option option = cursorToOption(cursor);
						options.add(option);
						cursor.moveToNext();
					}
				}
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return options;
	}
    
    //Gets all the questions of selected quiz.
    public List<Question> getAllQuestions(long quizId) {
		List<Question> quesList = new ArrayList<Question>();
		db = dbHelper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("select * from "
				+ DBHelper.QUESTION_TABLE_NAME
				+ " where quiz_id = ?", new String[] { String.valueOf(quizId) });
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
		do {
		Question quest = new Question();
		quest.setQueId(cursor.getLong(0));
		quest.setQuizId(cursor.getLong(1));
		quest.setQuestion(cursor.getString(2));
		
		quesList.add(quest);
		} while (cursor.moveToNext());
		}
		// return quest list
		return quesList;
	}
      
    public List<Option> getAllOptions(long queId) {
    	List<Option> optList = new ArrayList<Option>();
    	db = dbHelper.getReadableDatabase();
    	Cursor cursor = db.rawQuery("select * from "
    			+ DBHelper.OPTION_TABLE_NAME
    			+ " where que_id = ?", new String[] {String.valueOf(queId) });
    	if (cursor.moveToFirst()) {
    		do {
    			Option option = new Option();
    			option.setOptId(cursor.getLong(0));
    			option.setQueId(cursor.getLong(1));
    			option.setOptTxt(cursor.getString(2));
    			option.setCorrect(cursor.getString(3));
    			optList.add(option);
    		} while (cursor.moveToNext());
    	}
    	return optList;
    }
    
    public Question getQuestion(long quizId) {
    	Question question = null;
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from "
					+ DBHelper.QUESTION_TABLE_NAME + " where _id = '"
					+ String.valueOf(quizId) + "'", null);
			cursor.moveToFirst();
			if (!cursor.isAfterLast()) {
				question = cursorToQuestion(cursor);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return question;
	}
    
    public long insertQuizAttempt(long quizId, long userId, String currentDateandTime) {
		long id = -1;
		try {
			ContentValues values = new ContentValues();
			values.put("quiz_id", quizId);
			values.put("user_id", userId);
			values.put("attempt_time", currentDateandTime);
			
			db = dbHelper.getWritableDatabase();
			id = db.insert(DBHelper.ATTEMPTS_TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
    
	public long insertAttemptDetails(long optionId, long attemptId) {
		long id= -1;
		try {
			ContentValues values = new ContentValues();
			values.put("option_id", optionId);
			values.put("attempt_id", attemptId);
			
			db = dbHelper.getWritableDatabase();
			id = db.insert(DBHelper.ATTEMPT_DETAILS_TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;
	}
    
   /* public int getNumberOfCorrectAnswersForAttempt(String attemptId) {
		int correctCnt = 0;
		try {
			db = dbHelper.getReadableDatabase();
			if (db != null) {
				Cursor questionsCursor = db.rawQuery(
						"select distinct q_id from "
								+ DataSQLHelper.ATTEMPTS_TABLE_NAME
								+ " where a_id = '" + attemptId + "'", null);
				if (questionsCursor.getCount() > 0) {
					questionsCursor.moveToFirst();
					while (!questionsCursor.isAfterLast()) {
						if (isCorrectAttempt(attemptId,
								questionsCursor.getString(questionsCursor
										.getColumnIndex("q_id"))))
							correctCnt++;
						questionsCursor.moveToNext();
					}
				}
				questionsCursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return correctCnt;
	}*/
    
	public User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ROWID)));
		user.setName(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)));
		user.setEmail(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EMAIL)));
		return user;
	}
	
	public Quiz cursorToQuiz(Cursor cursor) {
		Quiz quiz = new Quiz();
		quiz.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.QUIZ_ID)));
		quiz.setName(cursor.getString(cursor.getColumnIndex(DBHelper.QUIZ_NAME)));
		return quiz;
	}
	
	public Question cursorToQuestion(Cursor cursor) {
		Question question = new Question();
		question.setQueId(cursor.getInt(cursor.getColumnIndex(DBHelper.QUES_ID)));
		question.setQuizId(cursor.getInt(cursor.getColumnIndex(DBHelper.QUIZ_ID)));
		question.setQuestion(cursor.getString(cursor.getColumnIndex(DBHelper.QUES_TXT)));
		return question;
	}
	
	public Option cursorToOption(Cursor cursor) {
		Option option = new Option();
		option.setOptId(cursor.getInt(cursor.getColumnIndex(DBHelper.OPT_ID)));
		option.setQueId(cursor.getInt(cursor.getColumnIndex(DBHelper.QUES_ID)));
		option.setOptTxt(cursor.getString(cursor.getColumnIndex(DBHelper.OPT_TXT)));
		option.setCorrect(cursor.getString(cursor.getColumnIndex(DBHelper.OPT_CORRECT)));
		return option;
	}
	
}