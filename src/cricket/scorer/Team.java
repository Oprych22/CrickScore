package cricket.scorer;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Team 
{
	private static final String TAG = "Team";
	private ArrayList<Player> players;
	private String name = "New Team";
	private String teamScore = "0-0";
	private int teamRuns = 0;
	private int teamWicketsLost = 0;
	private int teamID;
	private boolean currentlyBatting = false;
	private int playersOut = 0;
	private String location;
	private String ID = "NULL";
	private int teamByes = 0;
	private int teamLegByes = 0;
	private int teamWides = 0;
	private int teamNoBalls = 0;
	
	


	public Team(String tn, int ti)
	{
		
		name = tn;
		teamID = ti;
		location = "Default location";
		players = new ArrayList<Player>();
		for (int i = 1; i <= 11; i++)
		{
			players.add(new Player("Player " + i, i));
		}
	}
	
	
	
	public int getTeamLegByes() {
		return teamLegByes;
	}



	public void setTeamLegByes(int teamLegByes) {
		this.teamLegByes = teamLegByes;
	}



	public int getTeamWides() {
		return teamWides;
	}



	public void setTeamWides(int teamWides) {
		this.teamWides = teamWides;
	}



	public int getTeamNoBalls() {
		return teamNoBalls;
	}



	public void setTeamNoBalls(int teamNoBalls) {
		this.teamNoBalls = teamNoBalls;
	}
	
	public int getTeamByes() {
		return teamByes;
	}



	public void setTeamByes(int teamByes) {
		this.teamByes = teamByes;
	}



	public void getTeamExtras()
	{
		teamByes = 0;
		teamLegByes = 0;
		teamWides = 0;
		teamNoBalls = 0;
		for (int i = 0; i < players.size(); i++)
		{
			for (int k = 0; k < players.get(i).getOvers().size(); k++)
			{
				for (int j = 0; j < players.get(i).getOvers().get(k).getDeliveries().size(); j++)
				{
					if ( players.get(i).getOvers().get(k).getDeliveries().get(j).getExtraType() == 3)
					{
						teamByes += players.get(i).getOvers().get(k).getDeliveries().get(j).getExtraValue();
					}
					else if ( players.get(i).getOvers().get(k).getDeliveries().get(j).getExtraType() == 4)
					{
						teamLegByes += players.get(i).getOvers().get(k).getDeliveries().get(j).getExtraValue();
					}
					else if ( players.get(i).getOvers().get(k).getDeliveries().get(j).getExtraType() == 2)
					{
						teamWides += players.get(i).getOvers().get(k).getDeliveries().get(j).getExtraValue();
					}
					else if ( players.get(i).getOvers().get(k).getDeliveries().get(j).getExtraType() == 1)
					{
						teamNoBalls += players.get(i).getOvers().get(k).getDeliveries().get(j).getExtraValue();
					}
				}
			}
			
		}
		
		
	}
	
	
	
	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public boolean isCurrentlyBatting() {
		return currentlyBatting;
	}


	public void setCurrentlyBatting(boolean currentlyBatting) {
		this.currentlyBatting = currentlyBatting;
	}

	public int getTeamID() {
		return teamID;
	}


	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}


	public void addPlayerToTeam(Player p)
	{
		players.add(p);
	}
	
	
	public ArrayList<Player> getPlayers() {
		return players;
	}


	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public Player getPlayerByPos(int j)
	{
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getBattingPosition() == j)
			{
				return players.get(i);
			}
		}
		return null;
	}
	
	
	public Player getNextBatsman()
	{
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getBattingState() == 3)
			{
				return players.get(i);
			}
		}
		return null;
	}
	
	public Player getFacing()
	{
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getBattingState() == 1)
			{
				Log.v("Player", "facing" + players.get(i).getName() );
				return players.get(i);
			}
		}
		return null;
	}
	
	public Player getNotFacing()
	{
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getBattingState() == 2)
			{
				Log.v("Player", "facing" + players.get(i).getName() );
				return players.get(i);
			}
		}
		return null;
	}

	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getTeamScore() 
	{
		calculateTeamScore();
		Log.e(TAG, "TeamRuns = " + teamRuns);
		teamScore = teamRuns + "-" + teamWicketsLost;
		return teamScore;
	}
	
	public String getID(SQLiteDatabase database)
	{
		if (ID.equalsIgnoreCase("NULL") == false)
		{
			return ID;
		}
		Cursor cursor = database.rawQuery("SELECT * FROM Team", null);
		cursor.moveToFirst();
		if (cursor.getString(cursor.getColumnIndex("Name")).equalsIgnoreCase(name))
		{
			ID = cursor.getString(cursor.getColumnIndex("_id"));
		}
		while (cursor.moveToNext()) 
		{
			if (cursor.getString(cursor.getColumnIndex("Name")).equalsIgnoreCase(name))
			{
				ID = cursor.getString(cursor.getColumnIndex("_id"));
			}
            
       }
        cursor.close();
        return ID;
	}
	
	public void calculateTeamScore() 
	{
		teamRuns = 0;
		teamWicketsLost = 0;
		for (int i = 0; i < players.size(); i++ )
		{
			teamRuns += players.get(i).getBattingFigures();
			
			if (players.get(i).getBattingState() == 3)
			{
				teamWicketsLost++;
			}
			for (int j = 0; j < players.get(i).getDeliveries().size(); j++)
			{
				if (players.get(i).getDeliveries().get(j).isExtra() && players.get(i).getDeliveries().get(j).getExtraType() != 4)
				{
					//Log.e(TAG, "An Extra but not a leg bye");
					teamRuns += players.get(i).getDeliveries().get(j).getExtraValue();
					
				}
			}
		}
	}
	
	public boolean deliveryStuff(Delivery d)
	{
		if (d.getRunValue() % 2 != 0)
		{
			Log.v("Team", "Team: Did we get here odd run value ");
			swapBatsman();
			
		}
		if (d.getExtraValue() % 2 == 0 && d.getExtraValue() != 0 && d.isWicketTaken() == false)
		{
			Log.v("Team", "Team: Did we get here odd run value Extra");
			swapBatsman();
			
		}
		if (d.isWicketTaken())
		{
			Log.v(TAG, d.getWicketLost().getName());
			for (int i = 0; i < players.size(); i++)
			{
				Log.v(TAG, "Player's batting state " + players.get(i).getName() + "  " +  players.get(i).getBattingState());

				if (players.get(i).equals(d.getWicketLost()))
				{
					Log.v(TAG, "Do we get in here battingstate = 1 loop");
					players.get(i).setBattingState(3);
					players.get(i).setHowOut(d.getWicketType());
					Log.v(TAG, "wicketType " + d.getWicketType() );
					playersOut++;

				}
				else 
				
				
				if (players.get(i).getBattingState() == 0)
				{
					Log.v(TAG, "Do we get in here battingstate = 0 loop");
					players.get(i).setBattingState(1);
					break;
				}
			}
		}
		if (playersOut == 10)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void swapBatsman()
	{
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getBattingState() == 1)
			{
				players.get(i).setBattingState(2);
				Log.v("Team", "Team: Did we get here 2");
				Log.v("Team", "Team: Did we get here 2" + players.get(i).getName());
				
			}
			else if (players.get(i).getBattingState() == 2)
			{
				players.get(i).setBattingState(1);
				Log.v("Team", "Team: Did we get here 3");
				Log.v("Team", "Team: Did we get here 2" + players.get(i).getName());
			}
		}
	}

	public int getTeamRuns() {
		return teamRuns;
	}

	public void setTeamRuns(int teamRuns) {
		this.teamRuns = teamRuns;
	}

	public int getTeamWicketsLost() {
		return teamWicketsLost;
	}

	public void setTeamWicketsLost(int teamWicketsLost) {
		this.teamWicketsLost = teamWicketsLost;
	}
	
	
}
