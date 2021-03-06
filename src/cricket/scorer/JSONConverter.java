package cricket.scorer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class JSONConverter 
{

	private static final String TAG = "JSONConverter";
	private static JSONConverter convert = null;
	JSONArray deliveries;
	JSONArray overs;
	JSONArray players;
	JSONArray match;
	JSONArray teams;
	JSONSend jsonsend;
	boolean dataHold = false;
	JSONObject objectToSend ;
	
	
	private JSONConverter()
	{
		createArrays();
		jsonsend = JSONSend.getInstance();
	}
	
	public static JSONConverter getInstance()
	{
		if(convert == null)
		{
			convert = new JSONConverter();
		}
		return convert;

	}
	
	public void addDeliveryToJSONArray (Delivery d, int matchID, String playerDismissed, String playerAssist, String deliveryID, SQLiteDatabase database, int overNumber) throws JSONException 
	{
		JSONObject newDelivery = new JSONObject();
		try
		{
			newDelivery.put("Runs", d.getRunValue() );
			
			if (d.isWicketTaken())
			{
				newDelivery.put("Wicket", d.isWicketTaken());
				newDelivery.put("Wicket_Type",d.getWicketTypeString());
				newDelivery.put("Player_Dismissed", playerDismissed);
				newDelivery.put("Player_Assist", playerAssist);
			}
			else
			{
				newDelivery.put("Wicket", "0");
				newDelivery.put("Wicket_Type", "NULL");
				newDelivery.put("Player_Dismissed", "NULL");
				newDelivery.put("Player_Assist", "NULL");
			}
			if (d.getExtraValue() != 0)
			{
				newDelivery.put("Extras", d.getExtraValue());
				newDelivery.put("Extra_Type",d.getExtraTypeString());
			}
			else
			{
				newDelivery.put("Extras", "0");
				newDelivery.put("Extra_Type", "NULL");
			}
			
			newDelivery.put("Match_ID", matchID);
			newDelivery.put("Player_facing", d.getBatsman().getID(database));
			newDelivery.put("Over_Number", overNumber);
			
			newDelivery.put("_id", deliveryID);
			
			Log.e(TAG, "JSON Delivery: " + newDelivery.toString());
			
			deliveries.put(newDelivery);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addPlayerToJSONArray(Player p, Team team, int team_ID, String player_ID) throws JSONException
	{
		JSONObject newPlayer = new JSONObject();
		try
		{
			newPlayer.put("Name", p.getName() );
			newPlayer.put("Batting_Number", p.getBattingPosition());
			newPlayer.put("Team_ID", team_ID);
			newPlayer.put("_id", player_ID);
			
			Log.e(TAG, "JSON Player: " + newPlayer.toString());
			
			players.put(newPlayer);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addTeamToJSONArray(Team t, String team_ID)  throws JSONException
	{
		JSONObject newTeam = new JSONObject();
		try
		{
			newTeam.put("Name", t.getName() );
			newTeam.put("Location", t.getLocation());
			newTeam.put("_id", team_ID);
			
			Log.e(TAG, "JSON Team: " + newTeam.toString());
			
			teams.put(newTeam);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void addOverToJSONArray(Over o, Team t, Match m, String team_ID, int match_ID, String bowler_ID, String over_ID)  throws JSONException
	{
		
		JSONObject newOver = new JSONObject();
		try
		{
			newOver.put("_id", over_ID );
			newOver.put("Team_Bowling", team_ID);
			newOver.put("Match_ID", match_ID);
			newOver.put("Number", m.getCurrentOver()-1);
			newOver.put("Player_Bowling", bowler_ID);
			
			Log.e(TAG, "JSON Over: " + newOver.toString());
			
			overs.put(newOver);
			Log.e(TAG, "JSON Overs print: " + overs.toString());
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void addMatchToJSONArray(Match matchObject, int matchID, SQLiteDatabase database) throws JSONException
	{
		JSONObject newMatch = new JSONObject();
		try
		{
			newMatch.put("_id", matchID);
			newMatch.put("Scorers", matchObject.getScorers());
			newMatch.put("Umpires", matchObject.getUmpires());
			newMatch.put("Date", matchObject.getDate());
			newMatch.put("Home_Team", matchObject.getTeam(1).getID(database));
			newMatch.put("Away_Team", matchObject.getTeam(2).getID(database));
			newMatch.put("Match_Length", matchObject.getMatchLength());
			newMatch.put("Venue", matchObject.getVenue());
			
			match.put(newMatch);
			Log.e(TAG, "JSON Overs print: " + overs.toString());
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean wrapAndSend() throws Exception
	{
		if (dataHold == false)
		{
			objectToSend = new JSONObject();
			
			
			objectToSend.put("**Deliveries", deliveries);
			
			objectToSend.put("**Overs", overs);
			
			objectToSend.put("**Player", players);
			
			objectToSend.put("**Match", match);
			
			objectToSend.put("**Teams", teams);
		}
		
		if (jsonsend.sendJSONToServer(objectToSend))
		{
			createArrays();
			dataHold = false;
			return true;
		}
		else
		{
			dataHold = true;
			return false;
		}
		
	}
	
	public void createArrays()
	{
		teams = new JSONArray();
		match = new JSONArray();
		players = new JSONArray();
		deliveries = new JSONArray();
		overs = new JSONArray();
	}
	
}
