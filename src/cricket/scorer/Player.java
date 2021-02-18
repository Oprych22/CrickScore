package cricket.scorer;
import java.util.ArrayList; 

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Player 
{
	private ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
	private ArrayList<Over> overs = new ArrayList<Over>();
	private static final String TAG = "Player";
	private String name;
	private boolean isCaptain = false;
	private int battingPosition;
	private int battingFigures = 0;
	private double bowlingFigures = 0.00;
	private int runsConceded = 0;
	private int fours = 0;
	private int sixes = 0;
	/*private boolean out = false;
	private boolean notFacing = false;
	private boolean facing = false;*/
	// battingState indicates what the batsman's current batting state is,
	// 0 = not active
	// 1 = facing
	// 2 = notFacing
	// 3 = Out
	private int battingState = 0;
	private int howOut = 99;
	private int wicketsTaken = 0;
	private String ID = "NONE";
	
	
	
	
	
	public int getRunsConceded() {
		return runsConceded;
	}

	public void setRunsConceded(int runsConceded) {
		this.runsConceded = runsConceded;
	}

	public int getWicketsTaken() {
		return wicketsTaken;
	}

	public void setWicketsTaken(int wicketsTaken) {
		this.wicketsTaken = wicketsTaken;
	}

	private int maidens = 0;
	
	public enum battingState { notActive, facing, notFacing, out};
	
	
	public int getFours() {
		return fours;
	}

	public void setFours(int fours) {
		this.fours = fours;
	}

	public int getSixes() {
		return sixes;
	}

	public void setSixes(int sixes) {
		this.sixes = sixes;
	}
	
	public String fullWicketInfo()
	{
		if (deliveries.size() == 0 && (battingState == 2 || battingState == 1))
		{
			return "Not Out";
		}
		else if (deliveries.size() == 0 && battingState != 3)
		{
			return "Yet To Bat";
		}
			
		Delivery wicketBall =  deliveries.get(deliveries.size()-1);
		String returnString = "";
		if (battingState == 3)
		{
			switch (howOut)
			{
			case 0:
				returnString = "c " + wicketBall.getWicketAssist().getName() + " b " + wicketBall.getWicketTaker().getName();
				break;
		
			case 1:
				returnString = "b " + wicketBall.getWicketTaker().getName();
				break;
			
			case 2:
				returnString =  "lbw b " + wicketBall.getWicketTaker().getName();
				break;
				
			case 3:
				returnString =  "st " + wicketBall.getWicketAssist().getName() + " b " + wicketBall.getWicketAssist().getName();
				break;
				
			case 4:	
				returnString =   "ro " + wicketBall.getWicketAssist().getName();
				break;
			}
			
		}
		else if (battingState == 2 || battingState == 1)
		{
			returnString =  "Not Out";
		}
		else 
		{
			returnString =  "";
		}
		return returnString ;
		
	}

	public String getWicketStatus()
	{
		String wicketString = "";
		switch (howOut)
    	{
    	case 0:
    		wicketString = "Caught   ";
    		break;
    	case 1:
    		wicketString = "Bowled   ";
    		break;
    	case 2:
    		wicketString = "LBW      ";
    		break;
    	case 3:
    		wicketString = "Stumped";
    		break;
    	case 4:
    		wicketString = "Run Out";
    		break;
    	case 111:
    		wicketString = "Not Out";
    		break;
		default:
			wicketString = "";
			break;
    	}
		
		return wicketString;
	}
	
	public int getMaidens() {
		return maidens;
	}



	public void setMaidens(int maidens) {
		this.maidens = maidens;
	}
	
	public int getHowOut() {
		return howOut;
	}



	public void setHowOut(int howOut) {
		this.howOut = howOut;
	}



	public int getBattingState() {
		return battingState;
	}


	public void setBattingState(int battingState) {
		this.battingState = battingState;
		if (battingState == 2 || battingState == 1)
		{
			
			Log.v("Player", "setBattingState" + battingState);
			howOut = 111;
		}
	}


	public void setBowlingFigures(double bowlingFigures) {
		this.bowlingFigures = bowlingFigures;
	}


	public Player(String n, int batPos)
	{
		name = n;
		battingPosition = batPos;
	}
	
	public double getStrikeRate()
	
	{
		if (battingFigures > 0)
		{
			double returnThis = ((double)battingFigures / getNumberOfDeliveries()) * 100;
			return returnThis;
			
		}
		return 0;
	}
	

	public void addDeliveryToPlayer(Delivery d)
	{
		deliveries.add(d);
		/*if (d.isWicketTaken())
		{
			battingState = 3;
		}*/
		battingFigures = battingFigures + d.getRunValue();
		if (d.getExtraType() == 4)
		{
			battingFigures = battingFigures + d.getExtraValue();
		}
		if (d.getRunValue() == 4)
		{
			fours+=1;
			
		}
		else if (d.getRunValue() == 6)
		{
			sixes+=1;
		}
		
	}
	
	public void addOverToPlayer(Over o)
	{
		Over thisOver = o;
		overs.add(o);
		if (o.getOverRuns() == 0)
		{
			maidens++;
		}
		for (int i = 0; i < o.getDeliveries().size(); i++)
		{
			if (o.getDeliveries().get(i).extraAgainstTheBowler())
			{
				bowlingFigures += o.getDeliveries().get(i).getExtraValue();
				runsConceded += o.getDeliveries().get(i).getExtraValue();
			}
		}
		bowlingFigures += o.getOverResult();
		runsConceded += o.getOverRuns();
		
		wicketsTaken += o.getOverWicketsForBowler() ;
		Log.e(TAG,  "Over wickets were: " + o.getOverWickets());
		
	}

	public ArrayList<Delivery> getDeliveries() 
	{
		return deliveries;
	}


	public void setDeliveries(ArrayList<Delivery> deliveries) 
	{
		this.deliveries = deliveries;
	}
	
	public int getNumberOfDeliveries()
	{
		int deliveriesNotFaced = 0;
		Log.e(TAG, name + " has " + deliveries.size() + " Deliveries");
		for (int i = 0; i < deliveries.size(); i++)
		{
			
			if (!deliveries.get(i).getBatsman().equals(this))
			{
				deliveriesNotFaced++;
			}
		}

		return deliveries.size() - deliveriesNotFaced;
	}

	public ArrayList<Over> getOvers() 
	{
		return overs;
	}


	public void setOvers(ArrayList<Over> overs) 
	{
		this.overs = overs;
	}


	public String getName() 
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public boolean isCaptain() 
	{
		return isCaptain;
	}


	public void setCaptain(boolean isCaptain) 
	{
		this.isCaptain = isCaptain;
	}


	public int getBattingPosition() 
	{
		return battingPosition;
	}


	public void setBattingPosition(int battingPosition) 
	{
		this.battingPosition = battingPosition;
	}


	public int getBattingFigures()
	{
		return battingFigures;
	}


	public void setBattingFigures(int battingFigures)
	{
		this.battingFigures = battingFigures;
	}
	

	public void addBattingFigures(int battingFigures)
	{
		this.battingFigures += battingFigures;
	}


	public double getBowlingFigures()
	{
		return bowlingFigures;
	}
	
	public String getID(SQLiteDatabase database)
	{
		/*
		if (ID.equalsIgnoreCase("NONE") == false)
		{
			Log.e(TAG, "ID already checked");
			return ID;
		}
		*/
		Cursor cursor = database.rawQuery("SELECT * FROM PLAYER WHERE NAME = \"" + name + "\"", null);
		cursor.moveToFirst();
		String newName = cursor.getString(cursor.getColumnIndex("Name"));
		String newID  = cursor.getString(cursor.getColumnIndex("_id"));
		int fjks = 1;
		if (cursor.getString(cursor.getColumnIndex("Name")).equalsIgnoreCase(name))
		{
			ID = cursor.getString(cursor.getColumnIndex("_id"));
			Log.e(TAG, "ID SET AS " + ID);
		}
		while (cursor.moveToNext()) 
		{
			//Log.e(TAG, cursor.getString(cursor.getColumnIndex("Name")));
			//Log.e(TAG, "Length of above name" + cursor.getString(cursor.getColumnIndex("Name")).length());
			//Log.e(TAG, name);
			//Log.e(TAG, "Length of above name" + name.length());
			if (cursor.getString(cursor.getColumnIndex("Name")).equalsIgnoreCase(name))
			{
				ID = cursor.getString(cursor.getColumnIndex("_id"));
				Log.e(TAG, "ID SET AS " + ID);
				break;
			}
            
       }
        cursor.close();
        Log.e(TAG, "RETURNING ID " + ID);
        return ID;
	}


	public void setBowlingFigures(float b)
	{
		bowlingFigures = b;
	}
	
	public void addBowlingFigures(float b)
	{
		bowlingFigures += b;
	}
	
	public String overToString()
	{
		return  "      " + overs.size() + "     MaidensHere      "  + bowlingFigures;
	}
	public String currentBattingFigures()
	{
		String firstTabs = "\t\t\t\t\t\t\t\t\t";
		String tabs = "\t\t\t\t";
		String nameReturn = name;
		

		if (battingState == 1 || battingState == 2 || battingState == 3)
		{
			if (battingState == 1)
			{
				nameReturn += "*";
			}
			
			int numberOfTabs = nameReturn.length() / 3;
			//Log.e(TAG, "Tab NameLength " + name + ": " + nameReturn.length());
			//Log.e(TAG, "Number of Tabs for " + name + ": " + numberOfTabs);
			
			for (int i = 0; i < numberOfTabs; i++)
			{
				//Log.e(TAG, name + " Before" + firstTabs + ".");
				firstTabs = firstTabs.substring(0, firstTabs.length()-1);
				//Log.e(TAG, name + " After" + firstTabs + ".");
			}
			
			nameReturn +=  firstTabs + getNumberOfDeliveries() + tabs + 
	    			       battingFigures + tabs +
	    			       this.getWicketStatus() ;
			
			if (battingState == 3)
			{
				nameReturn += tabs + deliveries.get(deliveries.size()-1).getWicketTaker().getName();
			}
		}
		return nameReturn;
	}
	public String currentBowlingFigures(int isBowling)
	{
		
		if (isBowling == 1)
		{
		return name +                                                                      "*                                      " + 
			   overs.size() + overs.get(overs.size()).getDeliveries() +                    "                        "   +
			   getMaidens() +                                                              "                         " +
			   ((int) getBowlingFigures() + overs.get(overs.size()).getOverRuns()) + "                          " +
			   (getWicketsTaken() + overs.get(overs.size()).getOverWickets() );
		}
		else 
		{
			return name +                                "                                       " + 
					   overs.size() +                    "                        "   +
					   getMaidens() +                    "                         " +
					   ((int) getBowlingFigures()) +     "                          " +
					   (getWicketsTaken()  );
		}
	}

}
