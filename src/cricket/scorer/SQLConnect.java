package cricket.scorer;

import java.util.ArrayList;
import org.json.JSONException;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class SQLConnect {

	private static SQLConnect sqlconnect = null;
	private static final String TAG = "SQLCONNECT";
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private JSONConverter jsonconverter;
	private Context c;
	private String[] deliveryColumns = { "_id", "Runs", "Extras", "Wicket"};
	private int matchID;
	private ArrayList<String> teamName;
	private ArrayList<Integer> teamID;
	
	private SQLConnect(Context context) 
	{
		this.c = context;
		jsonconverter = JSONConverter.getInstance();
		
	}
	
	public static SQLConnect getInstance(Context context)
	{
		if(sqlconnect == null)
		{
			sqlconnect = new SQLConnect(context);
		}
		return sqlconnect;
	}

	public void open() throws SQLException 
	{
		if (this.c == null)
		{
			Log.d(TAG, "Context is null");
		}
		this.dbHelper = new MySQLiteHelper(this.c);
		this.database = this.dbHelper.getWritableDatabase();
		
	}

	public void close() 
	{
		this.database.close();
	}
	
	public void createMatchDB(Match match)
	{
		
		ContentValues values = new ContentValues();
		values.put("Scorers", match.getScorers());
		values.put("Umpires", match.getUmpires());
		values.put("Date", match.getDate());
		values.put("Home_Team", match.getTeam(1).getID(database));
		values.put("Away_Team", match.getTeam(2).getID(database));
		values.put("Match_Length", match.getMatchLength());
		values.put("Venue", match.getVenue());
		database.insertOrThrow("Match", null, values);
		
		getMatch();
		try {
			jsonconverter.addMatchToJSONArray(match, matchID, database);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void getMatch()
	{
		Log.e(TAG, "Match Info " + database.query("Match", null, null, null, null, null, "_id").toString());
		Cursor cursor = database.rawQuery("SELECT * FROM Match", null);
		cursor.moveToLast();
		//String[] result = new String[cursor.getCount()];
        //int i = 0;
       // while (cursor.moveToNext()) {
           // cursor.getString(cursor.getColumnIndex("Match"));
            Log.e(TAG, "Match ID : " + cursor.getString(cursor.getColumnIndex("_id")));
            Log.e(TAG, "Match Scorers: " + cursor.getString(cursor.getColumnIndex("Scorers")));
            Log.e(TAG, "Match Umpires: " + cursor.getString(cursor.getColumnIndex("Umpires")));
            Log.e(TAG, "Match Date: " + cursor.getString(cursor.getColumnIndex("Date")));
            Log.e(TAG, "Match Venue: " + cursor.getString(cursor.getColumnIndex("Venue")));
            Log.e(TAG, "Match Length: " + cursor.getString(cursor.getColumnIndex("Match_Length")));
            Log.e(TAG, "Match Team1: " + cursor.getString(cursor.getColumnIndex("Home_Team")));
            Log.e(TAG, "Match Team2: " + cursor.getString(cursor.getColumnIndex("Away_Team")));
            matchID = cursor.getInt(cursor.getColumnIndex("_id"));
       // }
        cursor.close();
		//Log.e(TAG, "All Deliveries " + database.rawQuery("SELECT * FROM DELIVERY", null).toString());
	}
	
	public void createDeliveryDB(Delivery delivery, Match match)
	{
		ContentValues values = new ContentValues();
		values.put("Runs", delivery.getRunValue() );
		values.put("Extras", delivery.getExtraValue());
		values.put("Wicket", delivery.isWicketTaken());
		values.put("Wicket_Type", delivery.getWicketTypeString());
		values.put("Extra_Type", delivery.getExtraTypeString());
		values.put("Match_ID", matchID);
		values.put("Player_facing", delivery.getBatsman().getName());
		values.put("Over_Number", delivery.getOverNumber());
		String playerDismissed = "";
		String playerAssist = "";
		if (delivery.isWicketTaken())
		{
			playerDismissed = delivery.getWicketLost().getID(database);
			
			if (delivery.getWicketType() == 0 || delivery.getWicketType() == 3 || delivery.getWicketType() == 4)
			{
				playerAssist = delivery.getWicketAssist().getID(database);
				
			}
			else
			{
				playerAssist = "";
			}
		}
		values.put("Player_Assist", playerAssist);
		values.put("Player_Dismissed", playerDismissed);
		Log.e(TAG, "Values: " + values.toString());
		database.insertOrThrow("Delivery", null, values);
		
		Cursor cursor = database.rawQuery("SELECT * FROM DELIVERY", null);
		cursor.moveToLast();
		
		try {
			jsonconverter.addDeliveryToJSONArray(delivery, matchID, playerDismissed, playerAssist, cursor.getString(cursor.getColumnIndex("_id")), database, match.getTotalOversBowled());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void getLastDeliveries()
	{
		//Log.e(TAG, "All Deliveries " + database.query("DELIVERY", null, null, null, null, null, "_id").toString());
		Cursor cursor = database.rawQuery("SELECT * FROM DELIVERY", null);
		cursor.moveToLast();
		//String[] result = new String[cursor.getCount()];
        //int i = 0;
       // while (cursor.moveToNext()) {
            cursor.getString(cursor.getColumnIndex("Runs"));
            Log.e(TAG, "DB ID : " + cursor.getString(cursor.getColumnIndex("_id")));
            Log.e(TAG, "DB Extras: " + cursor.getString(cursor.getColumnIndex("Extras")));
            Log.e(TAG, "DB Wicket: " + cursor.getString(cursor.getColumnIndex("Wicket")));
            Log.e(TAG, "DB RUNS: " + cursor.getString(cursor.getColumnIndex("Runs")));
            Log.e(TAG, "DB ExtraType: " + cursor.getString(cursor.getColumnIndex("Extra_Type")));
            Log.e(TAG, "DB WicketType: " + cursor.getString(cursor.getColumnIndex("Wicket_Type")));
            Log.e(TAG, "DB MatchID: " + cursor.getString(cursor.getColumnIndex("Match_ID")));
            Log.e(TAG, "DB Player Facing: " + cursor.getString(cursor.getColumnIndex("Player_Facing")));
            Log.e(TAG, "DB Over Number: " + cursor.getString(cursor.getColumnIndex("Over_Number")));
            Log.e(TAG, "DB Player Dismissed: " + cursor.getString(cursor.getColumnIndex("Player_Dismissed")));
            Log.e(TAG, "DB Player Assist: " + cursor.getString(cursor.getColumnIndex("Player_Assist")));
            //i++;
       // }
        cursor.close();
		//Log.e(TAG, "All Deliveries " + database.rawQuery("SELECT * FROM DELIVERY", null).toString());
	}
	

	
	public void createPlayerDB(Player player, Team team)
	{
		int team_ID = 0;
		for (int i = 0; i < teamName.size(); i++)
		{
			//Log.e(TAG, "teamname[i]: " + teamName[i]);
			//Log.e(TAG, "team.getName(): " + team.getName());
			if (teamName.get(i).equalsIgnoreCase(team.getName()))
			{
				//Log.e(TAG, "teamID[i]: " + teamID.get(i));
				team_ID = teamID.get(i);
				break;
			}
		}
		ContentValues values = new ContentValues();
		values.put("Name", player.getName());
		values.put("Batting_Number", player.getBattingPosition());
		values.put("Team_ID", team_ID );
		
		Log.e(TAG, "Values: " + values.toString());
		database.insertOrThrow("Player", null, values);
		
		Cursor cursor = database.rawQuery("SELECT * FROM Player", null);
		cursor.moveToLast();
		
		try 
		{
			jsonconverter.addPlayerToJSONArray(player, team,  team_ID, cursor.getString(cursor.getColumnIndex("_id")));
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void getAllPlayers()
	{
		
		Cursor cursor = database.rawQuery("SELECT * FROM PLAYER", null);
		cursor.moveToFirst();
		while (cursor.moveToNext()) 
		{
            cursor.getString(cursor.getColumnIndex("Player"));
            Log.e(TAG, "Player ID: " + cursor.getString(cursor.getColumnIndex("_id")));
            Log.e(TAG, "Player Name: " + cursor.getString(cursor.getColumnIndex("Name")));
            Log.e(TAG, "Player bat: " + cursor.getString(cursor.getColumnIndex("Batting_Number")));
            Log.e(TAG, "Team ID " + cursor.getString(cursor.getColumnIndex("Team_ID")));
            
       }
        cursor.close();
	}
	
	public void createTeamDB(Team team, boolean firstTime)
	{
		ContentValues values = new ContentValues();
		values.put("Name", team.getName());
		values.put("Location", team.getLocation());
		
		Log.e(TAG, "Values: " + values.toString());
		database.insertOrThrow("Team", null, values);
		
		
		if (firstTime)
		{
			getTeamDB();
		}
		
		try 
		{
			jsonconverter.addTeamToJSONArray(team, team.getID(database));
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getTeamDB()
	{
		teamName = new ArrayList<String>();
		teamID = new ArrayList<Integer>();
		
		Cursor cursor = database.rawQuery("SELECT * FROM Team", null);
		//Log.e(TAG, "cursor length: " + cursor.getColumnCount());
		cursor.moveToFirst();
		teamName.add(cursor.getString(cursor.getColumnIndex("Name")));
        teamID.add(cursor.getInt(cursor.getColumnIndex("_id")));
		while (cursor.moveToNext()) 
		{
            //cursor.getString(cursor.getColumnIndex("Team"));
           //Log.e(TAG, "Team ID: " + cursor.getString(cursor.getColumnIndex("_id")));
            //Log.e(TAG, "Team Name: " + cursor.getString(cursor.getColumnIndex("Name")));
            //Log.e(TAG, "Team Location: " + cursor.getString(cursor.getColumnIndex("Location")));
            
            teamName.add(cursor.getString(cursor.getColumnIndex("Name")));
            teamID.add(cursor.getInt(cursor.getColumnIndex("_id")));
       }
        cursor.close();
	}
	
	public void createOverDB(Over over, Team team, Match match)
	{
		
		ContentValues values = new ContentValues();
		values.put("Number", match.getTotalOversBowled());
		values.put("Team_Bowling", team.getID(database));
		values.put("Match_ID", matchID);
		values.put("Player_Bowling", over.getBowler().getID(database));
		//Log.e(TAG, "Over Player Bowling: " + match.getNextBowler().getName());
		Log.e(TAG, "Over Values: " + values.toString());
		database.insertOrThrow("Over", null, values);
		Cursor cursor = database.rawQuery("SELECT * FROM OVER", null);
		cursor.moveToLast();
		
		try 
		{
			jsonconverter.addOverToJSONArray(over, team, match, team.getID(database), matchID, over.getBowler().getID(database), cursor.getString(cursor.getColumnIndex("_id")));
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void replaceTables()
	{
		dbHelper.dropTables(this.database);
	}
	
	public void removeData()
	{
		dbHelper.removeData(this.database);
	}
	
	
}
