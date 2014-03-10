package in.informationworks.quizapplication.db;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;




public class DBHelper extends SQLiteOpenHelper {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email_id";
    private final Context myContext;
    private SQLiteDatabase myDataBase;
    
    private static final String DB_NAME = "quizapt.sqlite";    
    public static final String USERS_TABLE_NAME = "user";
    private static String DB_PATH = "/data/in.informationworks.quizaapplication.db/databases/";
       
   
    public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}	
       
        /*
         Created database by copying it from Assets folder. If database already exists, it does nothing.
         */
        public void createDataBase() throws Exception{

        	boolean dbExist = checkDataBase();
     
        	if(!dbExist){
        		//do nothing - database already exist
        	
            	this.getReadableDatabase();
     
            	try {           		
        			copyDataBase();        			
        		} 
            	catch (IOException e) {
            		throw new Exception("Error copying database");
            	}	
        	}
        }
        
        /*
         * Check if the database already exist to avoid re-copying the file each time you open the application.
         * @return true if it exists, false if it doesn't.
         */
        private boolean checkDataBase(){
     
        	SQLiteDatabase checkDB = null;
     
        	try
        	{
        		String myPath = DB_PATH + DB_NAME;
        		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          	}
        	catch(SQLiteException e){
        		//database does't exist yet.
        	}
    		if(checkDB != null){
    			checkDB.close();
    		}

    		return checkDB != null ? true : false;
    	}

        /*
         * Copies your database from your local assets-folder to the just created empty database in the
         * system folder, from where it can be accessed and handled.
         * This is done by transferring byte stream.
         */
        private void copyDataBase() throws IOException{

    		//Open your local database as the input stream
    		InputStream myInput = myContext.getAssets().open(DB_NAME);

    		// Path to the just created empty database
    		String outFileName = DB_PATH + DB_NAME;

    		//Open the empty db as the output stream
    		OutputStream myOutput = new FileOutputStream(outFileName);

    		//transfer bytes from the input file to the output file
    		byte[] buffer = new byte[1024];
    		int length;
    		while ((length = myInput.read(buffer))>0){
    			myOutput.write(buffer, 0, length);
    		}

    		//Close the streams
    		myOutput.flush();
    		myOutput.close();
    		myInput.close();

    	}
        
        public void openDataBase() throws SQLException{
    		//Open the database
    		
    		String myPath = DB_PATH + DB_NAME;
    		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}
        
        /*
         * Closes database connection
         */
        @Override
    	public synchronized void close() {
    		if(myDataBase != null)
    			myDataBase.close();
    		super.close();
    	}

        @Override
    	public void onCreate(SQLiteDatabase db) {
    	}

    	@Override
    	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	}
}

