package cricket.scorer;

import java.util.ArrayList;

public class Over 
{
	private static final String TAG = "Over";
	
	private Player bowler = null;
	private ArrayList<Delivery> deliveries;
	private int overNumber;
	private int overRuns;
	private int overExtras;
	private int overWickets;
	private boolean overFinished;
	
	public Over()
	{
		deliveries =  new ArrayList<Delivery>();
	}
	
	public Over(Player bwl, int overNum)
	{
		bowler = bwl;
		overNumber = overNum;
		deliveries =  new ArrayList<Delivery>();
		overFinished = false;
	}
	
	public boolean overFinished()
	{
		int legalDeliveriesBowled = 0;

		for (int i = 0; i < deliveries.size(); i++)
		{

			if (deliveries.get(i).isLegalDelivery())
			{
				legalDeliveriesBowled++;

			}
			else
			{

			}
		}
		if (legalDeliveriesBowled == 6)
		{
			overFinished = true;
		}
		return overFinished;
	}
	
	
	public boolean isOverFinished() {
		return overFinished;
	}

	public void setOverFinished(boolean overFinished) {
		this.overFinished = overFinished;
	}

	public boolean removeDelivery()
	{
		int arraySize = deliveries.size();
		deliveries.remove(deliveries.get(arraySize));
		if (deliveries.get(arraySize) ==  null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Player getBowler() 
	{
		return bowler;
	}

	public void setBowler(Player bowler) 
	{
		this.bowler = bowler;
	}

	public void addDelivery(Delivery d) {
		deliveries.add(d);
	}

	public ArrayList<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(ArrayList<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public int getOverNumber() 
	{
		return overNumber;
	}

	public void setOverNumber(int overNumber) 
	{
		this.overNumber = overNumber;
	}

	public int getOverRuns() 
	{
		overRuns = 0;
		for (int i = 0; i < deliveries.size(); i++ )
		{
			overRuns += deliveries.get(i).getRunValue();
		}
		return overRuns;
	}

	public void setOverRuns(int overRuns) 
	{
		this.overRuns = overRuns;
	}

	public int getOverExtras() 
	{
		overExtras = 0;
		for (int i = 0; i < deliveries.size(); i++ )
		{
			overExtras += deliveries.get(i).getExtraValue();
		}
		return overExtras;
	}

	public void setOverExtras(int overExtras) 
	{
		this.overExtras = overExtras;
	}

	public int getOverWickets() 
	{
		overWickets = 0;
		for (int i = 0; i < deliveries.size(); i++ )
		{
			if (deliveries.get(i).isWicketTaken())
			{
				overWickets++;
			}
		}
		return overWickets;
	}
	
	public int getOverWicketsForBowler() 
	{
		overWickets = 0;
		for (int i = 0; i < deliveries.size(); i++ )
		{
			if (deliveries.get(i).isWicketTaken() && deliveries.get(i).getWicketType() != 4)
			{
				overWickets++;
			}
		}
		return overWickets;
	}

	public void setOverWickets(int overWickets) 
	{
		this.overWickets = overWickets;
	}

	public double getOverResult()
	{
		double overResult = getOverRuns() + getOverExtras() + getOverWickets();
		return overResult;
	}
	
	public int extrasBowlersFault()
	{
		int extras = 0;
		for (int i = 0; i < deliveries.size(); i++)
		{
			if (deliveries.get(i).extraAgainstTheBowler())
			{
				extras += deliveries.get(i).getExtraValue();
			}
		}
		return extras;
	}
	
}
