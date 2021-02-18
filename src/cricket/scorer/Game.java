package cricket.scorer;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

public class Game  {

	private static Game game = null;
	private ArrayList<Delivery> deliveries;
	Match match;
	private SQLConnect connection;
	private JSONSend jsonsend;
	private static final String TAG = "Game";
	boolean matchOver= false;
	boolean bothSidesOut= false;
	Context gameContext;
	int ourCurrentOver ;
	boolean oneTeamOut;
	boolean inProgress;
	
	
	
	private Game(Context c)
	{
		gameContext = c;
		match = new Match();
		ourCurrentOver = match.getCurrentOver();
		connection = SQLConnect.getInstance(c);
		match.getTeam(1).setCurrentlyBatting(true);
		match.getTeam(1).getPlayerByPos(1).setBattingState(1);
		match.getTeam(1).getPlayerByPos(2).setBattingState(2);
		
		match.getTeam(1).getPlayerByPos(1).setName("Andrew Strauss");
        match.getTeam(1).getPlayerByPos(2).setName("Alistair Cook");
        match.getTeam(1).getPlayerByPos(3).setName("Jonathan Trott");
        match.getTeam(1).getPlayerByPos(4).setName("Ian Bell");
        match.getTeam(1).getPlayerByPos(5).setName("Kevin Pietersen");
        match.getTeam(1).getPlayerByPos(6).setName("Eoin Morgan");
        match.getTeam(1).getPlayerByPos(7).setName("Matt Prior");
        match.getTeam(1).getPlayerByPos(8).setName("Stuart Broad");
        match.getTeam(1).getPlayerByPos(9).setName("Tim Bresnan");
        match.getTeam(1).getPlayerByPos(10).setName("Graeme Swann");
        match.getTeam(1).getPlayerByPos(11).setName("James Anderson");
        
        match.getTeam(2).getPlayerByPos(1).setName("HDRL Thirimanne");
        match.getTeam(2).getPlayerByPos(2).setName("Tilikratne Dilshan");
        match.getTeam(2).getPlayerByPos(3).setName("Kumar Sangakkara");
        match.getTeam(2).getPlayerByPos(4).setName("Mahela Jayawardene");
        match.getTeam(2).getPlayerByPos(5).setName("TT Samaraweera");
        match.getTeam(2).getPlayerByPos(6).setName("Angelo Matthews");
        match.getTeam(2).getPlayerByPos(7).setName("HAPW Jayawardene");
        match.getTeam(2).getPlayerByPos(8).setName("S Randiv");
        match.getTeam(2).getPlayerByPos(9).setName("KTGD Prasad");
        match.getTeam(2).getPlayerByPos(10).setName("HMRKB Herath");
        match.getTeam(2).getPlayerByPos(11).setName("RAS Lakmal");
        
		/*match.getTeam(1).getPlayerByPos(1).setName("Home Player 1");
        match.getTeam(1).getPlayerByPos(2).setName("Home Player 2");
        match.getTeam(1).getPlayerByPos(3).setName("Home Player 3");
        match.getTeam(1).getPlayerByPos(4).setName("Home Player 4");
        match.getTeam(1).getPlayerByPos(5).setName("Home Player 5");
        match.getTeam(1).getPlayerByPos(6).setName("Home Player 6");
        match.getTeam(1).getPlayerByPos(7).setName("Home Player 7");
        match.getTeam(1).getPlayerByPos(8).setName("Home Player 8");
        match.getTeam(1).getPlayerByPos(9).setName("Home Player 9");
        match.getTeam(1).getPlayerByPos(10).setName("Home Player 10");
        match.getTeam(1).getPlayerByPos(11).setName("Home Player 11");
        
        match.getTeam(2).getPlayerByPos(1).setName("Away Player 1");
        match.getTeam(2).getPlayerByPos(2).setName("Away Player 2");
        match.getTeam(2).getPlayerByPos(3).setName("Away Player 3");
        match.getTeam(2).getPlayerByPos(4).setName("Away Player 4");
        match.getTeam(2).getPlayerByPos(5).setName("Away Player 5");
        match.getTeam(2).getPlayerByPos(6).setName("Away Player 6");
        match.getTeam(2).getPlayerByPos(7).setName("Away Player 7");
        match.getTeam(2).getPlayerByPos(8).setName("Away Player 8");
        match.getTeam(2).getPlayerByPos(9).setName("Away Player 9");
        match.getTeam(2).getPlayerByPos(10).setName("Away Player 10");
        match.getTeam(2).getPlayerByPos(11).setName("Away Player 11");*/
        oneTeamOut = false;
        deliveries =  new ArrayList<Delivery>();
        jsonsend = JSONSend.getInstance();
        inProgress = false;
        
	}
	
	
	
	public boolean isInProgress() {
		return inProgress;
	}



	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}



	public static Game getInstance(Context c)
	{
		if(game == null)
		{
			game = new Game(c);

		}
		return game;
	}
	
	
	public void newGame(Context c)
	{
		game = new Game(c);
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
	
	public void addPlayersToDatabase()
	{
		connection.open();
		for (int i = 0; i < 2; i++)
        {
        	for (int j = 0; j < 11; j++)
        	{
        		connection.createPlayerDB(match.getTeam(i+1).getPlayerByPos(j+1), match.getTeam(i+1));
        	
        	}
        }
       // connection.getAllPlayers();
        connection.close();
	}
	
	public void addMatchToDatabase()
	{
		connection.open();
		connection.createMatchDB(match);
		connection.getMatch();
		connection.close();
	}
	
	public void addTeamToDatabase()
	{
		connection.open();
		connection.createTeamDB(match.getTeam(1), true);
		connection.createTeamDB(match.getTeam(2), false);
		connection.getTeamDB();
		connection.close();
	}
	
	public void addOverToDatabase(Over over, Team team)
	{
		connection.open();
		connection.createOverDB(over, team, match);
		for (int i = 0; i < deliveries.size(); i++)
		{
			 connection.createDeliveryDB(deliveries.get(i), match);
		}
		deliveries.clear();
		deliveries = new ArrayList<Delivery>();
		//connection.getOverDB();
		connection.close();
	}
	
	public int getHighestOverNumber()
	{
		return match.getOvers().size();
	}
	
	public void deliveryBowled(Delivery d, boolean otherBatDelivery) throws JSONException
	{
		if (matchOver != true)
		{
		
			//Log.v("Game", "Team: " + match.getTeamBatting(true));
			//Log.v("Game", "Facing: " + match.getTeamBatting(true).getFacing());
			if (otherBatDelivery)
			{
				match.getTeamBatting(true).getNotFacing().addDeliveryToPlayer(d);
				match.getTeamBatting(true).getFacing().addDeliveryToPlayer(d);
				Log.v("Game", "Not Facing: " + match.getTeamBatting(true).getNotFacing().getName());
			}
			else
			{
				match.getTeamBatting(true).getFacing().addDeliveryToPlayer(d);
				Log.v("Game", "Facing: " + match.getTeamBatting(true).getFacing().getName());
			}
			boolean bowledOut = match.getTeamBatting(true).deliveryStuff(d);
			match.getActiveOver().addDelivery(d);
			connection.open();
			//connection.removeData();
			deliveries.add(d);
			//connection.getLastDeliveries();
			if (ourCurrentOver != match.getCurrentOver())
			{
				// Add last over bowled to database;
				
			}
			
			Log.e(TAG, "currentOver = " + ourCurrentOver);
			
			connection.close();
			if (ourCurrentOver == match.getMatchLength())
			{
				
				bowledOut = true;
			}
			if (bowledOut)
			{
				if (bothSidesOut)
				{
					matchOver = true;
				}
				else
				{

					Over thisOver = game.getMatch().getActiveOver();
					thisOver.setOverFinished(true);
					Player currentPlayer = thisOver.getBowler();
					Log.v(TAG, "CurrentBowler = " + currentPlayer.getName());
					Team teamSwitch = game.getMatch().getTeamBatting(false);
					
					oneTeamOut = true;
					Log.e(TAG, "Where do we break 1");
					match.getTeam(1).setCurrentlyBatting(false);
					Log.e(TAG, "Where do we break 1");
					match.getTeam(2).setCurrentlyBatting(true);
					Log.e(TAG, "Where do we break 1");
					match.getTeam(2).getPlayerByPos(1).setBattingState(1);
					Log.e(TAG, "Where do we break 1");
					match.getTeam(2).getPlayerByPos(2).setBattingState(2);
					Log.e(TAG, "Where do we break 1");
					match.setSwitchOver(true);
					match.setNextBowler(match.getTeam(1).getPlayerByPos(11));
					Log.e(TAG, "Where do we break 1");
					match.getActiveOver();
					game.addOverToDatabase(thisOver, teamSwitch);
					currentPlayer.addOverToPlayer(thisOver);
					Log.e(TAG, "Where do we break 1");
					bothSidesOut = true;
					Log.e(TAG, "Where do we break 1");
					match.setSwitchOver(false);
					match.setCurrentOver(1);
				}
			}
			
		}
	}

	public boolean isOneTeamOut() {
		return oneTeamOut;
	}

	public void setOneTeamOut(boolean oneTeamOut) {
		this.oneTeamOut = oneTeamOut;
	}

	public boolean isMatchOver() {
		return matchOver;
	}

	public void setMatchOver(boolean matchOver) {
		this.matchOver = matchOver;
	}

	
}
