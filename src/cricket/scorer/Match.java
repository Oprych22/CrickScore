package cricket.scorer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.util.Log;


public class Match {
	
	private static final String TAG = "Match";
	
	private ArrayList<Over> overs;	
	private ArrayList<Team> teams;	
	private String venue;
	private String date;
	private int matchLength;
	private int currentOver;
	private Player nextBowler;
	private Player previousBowler;
	private boolean switchOver = false;
	private String scorers;
	private String umpires;
	private int totalOversBowled = 0;
	
	public Match()
	{
		
		Log.e("Match", "Match constructor beginning");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        this.date = dateFormat.format(date);
		matchLength = 50;
		venue = "Lords";
		currentOver = 1;
		overs = new ArrayList<Over>();
		teams = new ArrayList<Team>();
		teams.add( new Team("England", 1));
		teams.add( new Team("Sri Lanka", 2));
		nextBowler = teams.get(1).getPlayerByPos(11);
		previousBowler = nextBowler;
		scorers = "Scorer 1, Scorer 2";
		umpires = "Umpire 1, Umpire 2";
		
		
	}
	
	public String getUmpires() {
		return umpires;
	}





	public void setUmpires(String umpires) {
		this.umpires = umpires;
	}


	public int getHighestOverNumber()
	{
		return overs.size();
	}


	public String getScorers() {
		return scorers;
	}





	public void setScorers(String scorers) {
		this.scorers = scorers;
	}





	public Player getPreviousBowler() {
		return previousBowler;
	}



	public void setPreviousBowler(Player previousBowler) {
		this.previousBowler = previousBowler;
	}



	public int getCurrentOver() {
		return currentOver;
	}
	public Over returnCurrentOver() {
		return overs.get(currentOver-1);
	}


	public void setCurrentOver(int currentOver) {
		this.currentOver = currentOver;
	}


	public Player getNextBowler() {
		return nextBowler;
	}


	public void setNextBowler(Player next) {
		//Log.e(TAG, "Previous Bowler before " + previousBowler.getName());
		this.nextBowler = next;
		//Log.e(TAG, "Previous Bowler after after" + nextBowler.getName());
		previousBowler = nextBowler;
		//Log.e(TAG, "Previous Bowler after " + previousBowler.getName());
	}


	
	
	
	public Team getTeam(int j)
	{
		for (int i = 0; i < teams.size(); i++)
		{
			if (teams.get(i).getTeamID() == j)
			{
				return teams.get(i);
			}
		}
		return null;
	}
	
	public Over getOverForBowler()
	{
		return overs.get(overs.size()-1);
	}
	
	public Over getActiveOver()
	{
		
		if (overs.size() == 0)
		{
			Over firstOver = new Over();
			firstOver.setBowler(nextBowler);
			overs.add(firstOver);
		}
		
		int currentOverUsed = overs.size()-1;
		/*
		if (switchOver)
		{
			Over newOver = new Over();
			newOver.setBowler(nextBowler);
			overs.add(newOver);
			totalOversBowled++;
			return newOver;
		}
		*/
		//Log.v(TAG, "oversize: " + overs.size());
		if (overs.get(currentOverUsed).overFinished())
		{
			//Log.e(TAG, "ToString: "+ getOverDeliveriesString(overs.get(currentOverUsed)));
			//Log.e(TAG, "New Over Created");
			//Log.e(TAG, "ONLY RUNS ONCe");
			currentOver++;
			Over newOver = new Over(nextBowler, currentOver);
			newOver.setBowler(nextBowler);
			overs.add(newOver);
			
			totalOversBowled++;
			return newOver;
			
		}
		
		else 
		{
			//Log.e("Match", "Over we resferred to overfinished: " + overs.get(currentOverUsed));
			return overs.get(currentOverUsed);
		}
		
	}
	
	
	
	public int getTotalOversBowled() {
		return totalOversBowled;
	}





	public void setTotalOversBowled(int totalOversBowled) {
		this.totalOversBowled = totalOversBowled;
	}





	public boolean isSwitchOver() {
		return switchOver;
	}



	public void setSwitchOver(boolean switchOver) {
		this.switchOver = switchOver;
	}



	public boolean isOverFinished()
	{
		if (overs.size() == 0)
		{
			Over firstOver = new Over();
			firstOver.setBowler(nextBowler);
			overs.add(firstOver);
		}
		int currentOverUsed = overs.size()-1;
		boolean returnThis = false;
		if (overs.get(currentOverUsed).overFinished() == false)
		{
			returnThis = false;
		}
		else if (overs.get(currentOverUsed).overFinished())
		{
			returnThis = true;
		}
		
		return returnThis;
		
	}
	

	public ArrayList<Over> getOvers() 
	{
		return overs;
	}

	public void setOvers(ArrayList<Over> overs) 
	{
		this.overs = overs;
	}

	public ArrayList<Team> getTeams()
	{
		return teams;
	}

	public void setTeams(ArrayList<Team> teams) 
	{
		this.teams = teams;
	}

	public String getVenue() 
	{
		return venue;
	}

	public void setVenue(String venue) 
	{
		this.venue = venue;
	}

	public String getDate() 
	{
		return date;
	}

	public void setDate(String date) 
	{
		this.date = date;
	}

	public int getMatchLength() 
	{
		return matchLength;
	}

	public void setMatchLength(int matchLength) 
	{
		this.matchLength = matchLength;
	}
	
	public Team getTeamBatting(boolean b)
	{
		Team returnTeam = new Team("NewTeam", 1);
		for (int i = 0; i < teams.size(); i++)
		{
			if (teams.get(i).isCurrentlyBatting() && b)
			{
				returnTeam = teams.get(i);
			}
			else if (teams.get(i).isCurrentlyBatting() == false && b == false)
			{
				returnTeam = teams.get(i);
			}
		}
		return returnTeam;
	}
	
	public String getOverDeliveriesString(Over o)
	{
		String returnString = "";
		for (int i = 0; i < o.getDeliveries().size(); i++)
		{
			returnString += o.getDeliveries().get(i).toString();
		}
		
		return returnString;
	}
}
