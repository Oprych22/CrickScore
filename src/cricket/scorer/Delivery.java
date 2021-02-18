package cricket.scorer;

public class Delivery
{
	private static final String TAG = "Delivery";
	
	private int runValue= 0;
	private boolean extra = false;
	private int extraValue = 0;
	private int extraType = 0;
	private boolean wicketTaken = false;
	private Player batsman =  null;
	private Player wicketTaker = null;
	private Player wicketLost = null;
	private int wicketType = 99;
	private boolean legalDelivery = true;
	private int overNumber = 1;
	private Player wicketAssist = null;
	
	

	public Delivery(boolean wk, int wktType, Player bat, Player wkttkr, Player wktlst, int overNum, Player wikAssist )
	{
		
		wicketTaken = wk;
		wicketType = wktType;
		batsman = bat;
		wicketTaker = wkttkr;
		wicketLost = wktlst;
		overNumber = overNum;
		wicketAssist = wikAssist;
	}
	
	public Delivery(int rv, int ev, boolean ex,  int extype, Player bat, int overNum)
	{
		runValue = rv;
		extra = ex;
		extraValue = ev;
		extraType = extype;
		batsman = bat;
		overNumber = overNum;
		if (extype == 1 || extype == 2)
		{
			legalDelivery = false;
		}
	}
	
	
	
	public Player getWicketAssist() {
		return wicketAssist;
	}

	public void setWicketAssist(Player wicketAssist) {
		this.wicketAssist = wicketAssist;
	}

	public boolean isLegalDelivery() {
		return legalDelivery;
	}


	public void setLegalDelivery(boolean legalDelivery) {
		this.legalDelivery = legalDelivery;
	}


	public void setExtra(boolean extra) {
		this.extra = extra;
	}
    

	public int getOverNumber() {
		return overNumber;
	}

	public void setOverNumber(int overNumber) {
		this.overNumber = overNumber;
	}

	public int getWicketType() {
		return wicketType;
	}
	
	public String getWicketTypeString()
	{
		String wicketString = "";
		switch (wicketType)
    	{
    	case 0:
    		wicketString = "Caught";
    		break;
    	case 1:
    		wicketString = "Bowled";
    		break;
    	case 2:
    		wicketString = "LBW";
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
	
	public boolean extraAgainstTheBowler()
	{
		if (extraType == 1 || extraType == 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String getExtraTypeString()
	{
		String extraString = "";
		switch (extraType)
    	{
    	case 0:
    		extraString = "";
    		break;
    	case 1:
    		extraString = "No Ball";
    		break;
    	case 2:
    		extraString = "Wide";
    		break;
    	case 3:
    		extraString = "Bye(s)";
    		break;
    	case 4:
    		extraString = "Leg-Bye(s)";
    		break;
    	default:
    		extraString = "";
			break;
    	}
		
		return extraString;
	}
	
	public String getExtraTypeStringShort()
	{
		String extraString = "";
		switch (extraType)
    	{
    	case 0:
    		extraString = "";
    		break;
    	case 1:
    		extraString = "nb";
    		break;
    	case 2:
    		extraString = "w";
    		break;
    	case 3:
    		extraString = "b";
    		break;
    	case 4:
    		extraString = "lb";
    		break;
    	default:
    		extraString = "";
			break;
    	}
		
		return extraString;
	}


	public void setWicketType(int wicketType) {
		this.wicketType = wicketType;
	}


	public boolean isExtra() 
	{
		return extra;
	}


	public void setExtras(boolean extra) 
	{
		this.extra = extra;
	}


	public int getRunValue() 
	{
		return runValue;
	}


	public void setRunValue(int runValue) 
	{
		this.runValue = runValue;
	}


	public int getExtraValue() 
	{
		return extraValue;
	}


	public void setExtraValue(int extraValue) 
	{
		this.extraValue = extraValue;
	}


	public int getExtraType() 
	{
		return extraType;
	}


	public void setExtraType(int extraType) 
	{
		this.extraType = extraType;
	}


	public boolean isWicketTaken() 
	{
		return wicketTaken;
	}


	public void setWicketTaken(boolean wicketTaken) 
	{
		this.wicketTaken = wicketTaken;
	}


	public Player getBatsman() 
	{
		return batsman;
	}


	public void setBatsman(Player batsman) 
	{
		this.batsman = batsman;
	}


	public Player getWicketTaker() 
	{
		return wicketTaker;
	}


	public void setWicketTaker(Player wicketTaker) 
	{
		this.wicketTaker = wicketTaker;
	}


	public Player getWicketLost() 
	{
		return wicketLost;
	}


	public void setWicketLost(Player wicketLost) 
	{
		this.wicketLost = wicketLost;
	}

	@Override
	public String toString()
	{
		return "RunValue= " + runValue + " extraValue= " + extraValue + " extraType= " + extraType + " wicketTaken= " + 
				wicketTaken + " wicketLost= " + wicketLost + " wicketType= " + wicketType + " LegalDelivery" + legalDelivery;
	}
	
}

