package cricket.scorer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	private static final String TAG = "MySQLiteHelper";
	
	public static final String TABLE_MATCH = "Match";
	public static final String MATCH_ID = "_id";
	public static final String MATCH_UMPIRES = "Umpires";
	public static final String MATCH_LENGTH = "Match_Length";
	public static final String MATCH_SCORERS = "Scorers";
	public static final String MATCH_DATE = "Date";
	public static final String MATCH_VENUE = "Venue";
	public static final String MATCH_TEAM1 = "Home_Team";
	public static final String MATCH_TEAM2 = "Away_Team";
	
	public static final String TEAM_ID = "_id";
	public static final String TABLE_TEAM = "Team";
	public static final String TEAM_NAME = "Name";
	public static final String TEAM_LOCATION = "Location";
	
	public static final String TABLE_OVER = "Over";
	public static final String OVER_ID = "_id";
	public static final String OVER_NUMBER = "Number";
	public static final String OVER_TEAMBOWLING = "Team_Bowling";
	public static final String OVER_MATCHID = "Match_ID";
	public static final String OVER_PLAYERBOWLING = "Player_Bowling";
	
	public static final String TABLE_PLAYER = "Player";
	public static final String PLAYER_ID = "_id";
	public static final String PLAYER_NAME = "Name";
	public static final String PLAYER_BATTINGNUMBER = "Batting_Number";
	public static final String PLAYER_TEAMID = "Team_ID";
	
	public static final String TABLE_DELIVERY = "Delivery";
	public static final String DELIVERY_NUMBER = "_id";
	public static final String DELIVERY_RUNS = "Runs";
	public static final String DELIVERY_EXTRAS = "Extras";
	public static final String DELIVERY_EXTRATYPE = "Extra_Type";
	public static final String DELIVERY_WICKET = "Wicket";
	public static final String DELIVERY_WICKETTYPE = "Wicket_Type";
	public static final String DELIVERY_MATCHID = "Match_ID";
	public static final String DELIVERY_PLAYERFACING = "Player_Facing";
	public static final String DELIVERY_OVERNUMBER = "Over_Number";
	public static final String DELIVERY_PLAYERDISMISSED = "Player_Dismissed";
	public static final String DELIVERY_PLAYERASSIST = "Player_Assist";
	
	public static final String MATCH_FOREIGN_KEY_TEAM1 = "FOREIGN KEY(Home_Team) REFERENCES " + TABLE_TEAM + "(" + TEAM_ID + ")";
	public static final String MATCH_FOREIGN_KEY_TEAM2 = "FOREIGN KEY(Away_Team) REFERENCES " + TABLE_TEAM + "(" + TEAM_ID + ")";
	
	public static final String OVER_FOREIGN_KEY_TEAMBOWL = "FOREIGN KEY(Team_Bowling) REFERENCES " + TABLE_TEAM + "(" + TEAM_ID + ")";
	public static final String OVER_FOREIGN_KEY_MATCHID  = "FOREIGN KEY(Match_ID) REFERENCES " + TABLE_MATCH + "(" + MATCH_ID + ")";
	public static final String OVER_FOREIGN_KEY_PLAYERBOWL = "FOREIGN KEY(Player_Bowling) REFERENCES " + TABLE_PLAYER + "(" + PLAYER_ID + ")";
	
	public static final String PLAYER_FOREIGN_KEY_TEAMID = "FOREIGN KEY(Team_ID) REFERENCES " + TABLE_TEAM + "(" + TEAM_ID + ")";
	
	public static final String DELIVERY_FOREIGN_KEY_MATCHID = "FOREIGN KEY(Match_ID) REFERENCES " + TABLE_MATCH + "(" + MATCH_ID + ")";
	public static final String DELIVERY_FOREIGN_KEY_PLAYERFACING = "FOREIGN KEY(Player_Facing) REFERENCES " + TABLE_PLAYER + "(" + PLAYER_ID + ")";
	public static final String DELIVERY_FOREIGN_KEY_OVERNUMBER = "FOREIGN KEY(Over_Number) REFERENCES " + TABLE_OVER + "(" + OVER_NUMBER + ")";
	public static final String DELIVERY_FOREIGN_KEY_PLAYERDISMISSEDID = "FOREIGN KEY(Player_Dismissed) REFERENCES " + TABLE_PLAYER + "(" + PLAYER_ID + ")";
	public static final String DELIVERY_FOREIGN_KEY_PLAYERASSISTID = "FOREIGN KEY(Player_Assist) REFERENCES " + TABLE_PLAYER + "(" + PLAYER_ID + ")";
	
	public static final String NULL_IN_BRACKETS = "\"NULL\"";
	

	private static final String DATABASE_NAME = "cricketmatch.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_MATCH_TABLE = 
			"create table "
			+ TABLE_MATCH + "( " 
			+ MATCH_ID + " integer primary key autoincrement, " 
			+ MATCH_LENGTH + ", "
			+ MATCH_UMPIRES + ", " 
			+ MATCH_SCORERS + ", "
			+ MATCH_DATE + ", " 
			+ MATCH_VENUE + ", "
			+ MATCH_TEAM1 + ", "
			+ MATCH_TEAM2 + ", "
			+ MATCH_FOREIGN_KEY_TEAM1 + ", "
			+ MATCH_FOREIGN_KEY_TEAM2 + ");";
			
	private static final String DATABASE_TEAM_TABLE = 
			"create table "
			+ TABLE_TEAM + "( " 
			+ TEAM_ID + " integer primary key autoincrement, " 
			+ TEAM_NAME + ", " 
			+ TEAM_LOCATION + ");";
			
	private static final String DATABASE_OVER_TABLE = 
			"create table "
			+ TABLE_OVER + "( " 
			+ OVER_ID + " integer primary key, "
			+ OVER_NUMBER + ", "
			+ OVER_TEAMBOWLING + ", "
			+ OVER_MATCHID + ", "
			+ OVER_PLAYERBOWLING + ", "
			+ OVER_FOREIGN_KEY_TEAMBOWL + ", "
			+ OVER_FOREIGN_KEY_MATCHID  + ", "
			+ OVER_FOREIGN_KEY_PLAYERBOWL + ");";

			//+ " text not null);";
	
	private static final String DATABASE_PLAYER_TABLE = 
			"create table "
			+ TABLE_PLAYER + "( " 
			+ PLAYER_ID + " integer primary key autoincrement, " 
			+ PLAYER_NAME + ", " 
			+ PLAYER_BATTINGNUMBER + ", "
			+ PLAYER_TEAMID + ", "
			+ PLAYER_FOREIGN_KEY_TEAMID + ");";
			
	private static final String DATABASE_DELIVERY_TABLE = 
			"create table "
			+ TABLE_DELIVERY + "( " 
			+ DELIVERY_NUMBER + " integer primary key autoincrement, " 
			+ DELIVERY_RUNS + ", " 
			+ DELIVERY_EXTRAS + ", "
			+ DELIVERY_EXTRATYPE + ", "
			+ DELIVERY_WICKET +  ", "
			+ DELIVERY_WICKETTYPE + ", "
			+ DELIVERY_MATCHID + ", "
			+ DELIVERY_PLAYERFACING + ", "
			+ DELIVERY_OVERNUMBER + ", "
			+ DELIVERY_PLAYERDISMISSED + ", "
			+ DELIVERY_PLAYERASSIST + ", "
			+ DELIVERY_FOREIGN_KEY_MATCHID + ", "
			+ DELIVERY_FOREIGN_KEY_PLAYERFACING + ", "
			+ DELIVERY_FOREIGN_KEY_OVERNUMBER +  ", "
			+ DELIVERY_FOREIGN_KEY_PLAYERDISMISSEDID + ", " 
			+ DELIVERY_FOREIGN_KEY_PLAYERASSISTID +  ");";
	
	//private static final String DATABASE_ADDTEAM1 = "";

	public MySQLiteHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		makeTables(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) 
	{
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS" + TABLE_MATCH);
		//onCreate(database);
	}
	
	public void dropTables(SQLiteDatabase database)
	{
		database.execSQL("DROP TABLE " + TABLE_MATCH);
		database.execSQL("DROP TABLE " + TABLE_TEAM);
		database.execSQL("DROP TABLE " + TABLE_PLAYER);
		database.execSQL("DROP TABLE " + TABLE_OVER);
		database.execSQL("DROP TABLE " + TABLE_DELIVERY);
		makeTables(database);
	}
	
	public void makeTables(SQLiteDatabase database)
	{

		database.execSQL(DATABASE_TEAM_TABLE);
		Log.e(TAG, DATABASE_TEAM_TABLE);
		database.execSQL(DATABASE_MATCH_TABLE);
		Log.e(TAG, DATABASE_MATCH_TABLE);
		database.execSQL(DATABASE_DELIVERY_TABLE);
		Log.e(TAG, DATABASE_DELIVERY_TABLE);
		database.execSQL(DATABASE_OVER_TABLE);
		Log.e(TAG, DATABASE_OVER_TABLE);
		database.execSQL(DATABASE_PLAYER_TABLE);
	}
	public void removeData(SQLiteDatabase database)
	{
		database.execSQL("DELETE FROM " + TABLE_MATCH);
		database.execSQL("DELETE FROM " + TABLE_DELIVERY);
		database.execSQL("DELETE FROM " + DATABASE_OVER_TABLE);
		database.execSQL("DELETE FROM " + DATABASE_PLAYER_TABLE);
		database.execSQL("DELETE FROM " + DATABASE_TEAM_TABLE);
	}
	
	

}
