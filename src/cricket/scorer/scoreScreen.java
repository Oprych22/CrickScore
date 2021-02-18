package cricket.scorer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;



public class scoreScreen extends Fragment implements OnClickListener {
	
	private LinearLayout ll;
	private FragmentActivity fa;
	private Game game = null ;
	int thisWillWork;
	private Player batsmanFacing ;
	private Player batsmanOut ;
	private int currentOver;
	Over activeOver;
	String[] teamOnePlayerStrings = new String[11];
	String[] teamTwoPlayerStrings = new String[11];
	String[] bowlerNameArray2;
	private int bowlerPicked = 11;
	onScreenChange mListener;
	boolean newMatch;
	private boolean matchStarted = false;

	
	
	private static final String TAG = "scoreScreen";
    /** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
		//newMatch = getArguments().getBoolean("newMatch");
		
		if (container == null) 
		{
			return null;
		}
		ll =  (LinearLayout) inflater.inflate(R.layout.scorescreen, container, false);
    	//fa = super.getActivity();
    	
    	setupButtons();
    	
    	
    	setHasOptionsMenu(true);
    	
    	currentOver = game.getMatch().getCurrentOver();
    	
    	Typeface tf = Typeface.createFromAsset(fa.getAssets(),
                "fonts/LEDBOARD.TTF");
        TextView tv = (TextView) ll.findViewById(R.id.overall_score_scorescreen);
        TextView tv2 = (TextView) ll.findViewById(R.id.overall_wickets_scorescreen);
        TextView tv3 = (TextView) ll.findViewById(R.id.overall_overs_scorescreen);
        tv.setTypeface(tf);
        tv2.setTypeface(tf);
        tv3.setTypeface(tf);
        
        TextView matchDetails = (TextView) ll.findViewById(R.id.matchDetails);
        matchDetails.setText(String.valueOf(game.getMatch().getMatchLength() + " Over Match at " + game.getMatch().getVenue()));
        
        //game = new Game();
        	
        
        
        screenSetup();	

        	
        return ll;
    }
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		fa = super.getActivity();
		
		
		game = Game.getInstance(fa);
		if (game.isInProgress() == false)
		{
    		game.addTeamToDatabase();
    		game.addMatchToDatabase();
  	      	game.addPlayersToDatabase();
  	        game.setInProgress(true);
		}
        batsmanFacing = game.getMatch().getTeamBatting(true).getFacing();
    	batsmanOut = batsmanFacing;

	}
    

    
    
    public void onBackPressed() 
    {
    	fa.onBackPressed();
    	AlertDialog.Builder builder = new AlertDialog.Builder(fa);
        builder.setMessage("Are you sure you want to exit?")
	       .setCancelable(false)
	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
	       {
	           public void onClick(DialogInterface dialog, int id) 
	           {
	                //this.finish();
	           }
	       })
	       .setNegativeButton("No", new DialogInterface.OnClickListener() 
	       {
	           public void onClick(DialogInterface dialog, int id) 
	           {
	                dialog.cancel();
	           }
	       });
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
    public void screenSetup()
    {
    	if (game.isMatchOver() == false)
        {
    
    		activeOver = game.match.getActiveOver();
    	
	    	TextView teamName1 = (TextView) ll.findViewById(R.id.team1_name_scorescreen); 
	    	TextView teamName2 = (TextView) ll.findViewById(R.id.team2_name_scorescreen); 
	    	teamName1.setText(game.getMatch().getTeamBatting(true).getName());
	    	teamName2.setText(game.getMatch().getTeamBatting(false).getName());
	    	
	    	//int currentOverWickets = 0;
	    	//double currentOverDeliveries = 0.0;
	    	for (int j = 1; j < 3; j++)
	    	{
	    		
		        for (int i = 0; i < 11; i++)
		        {
		        	/*
		        	int currentOverRuns = 0;
		        	currentOverWickets = 0;
		        	currentOverDeliveries = 0.0;
	        		
		        	if (game.getMatch().getTeam(j).getPlayerByPos(i+1) == game.getMatch().getNextBowler())
		        	{
		        		currentOverRuns = game.getMatch().getActiveOver().getOverRuns() + game.getMatch().getActiveOver().extrasBowlersFault();
		        		currentOverWickets = game.getMatch().getActiveOver().getOverWickets();
		        		//Log.e(TAG, "CurrentOverWickets: " + currentOverWickets);
		        		currentOverDeliveries =  (game.getMatch().getActiveOver().getDeliveries().size() * 0.1);
		        		
		        		//Log.e(TAG, "Current Over Deliveries" + currentOverDeliveries);
		        	}
		        	*/
		     	   teamOnePlayerStrings[i] = game.getMatch().getTeam(1).getPlayerByPos(i+1).getName();
		     	   teamTwoPlayerStrings[i] = game.getMatch().getTeam(2).getPlayerByPos(i+1).getName();
		     	  
		     	   //Log.v(TAG, playerStrings[j-1][i]);
		        }
		        
	    	}
	    	game.getMatch().getTeamBatting(true).calculateTeamScore();
	    	TextView overallScore = (TextView) ll.findViewById(R.id.overall_score_scorescreen); 
	    	TextView overs = (TextView) ll.findViewById(R.id.overall_overs_scorescreen);
	    	TextView wickets = (TextView) ll.findViewById(R.id.overall_wickets_scorescreen); 
	    	
	    	String score = "";
	    	String over = "";
	    	if (game.getMatch().getTeamBatting(true).getTeamRuns() < 10)
	    	{
	    		score = "00";
	    	}
	    	if (game.getMatch().getTeamBatting(true).getTeamRuns() < 100 && game.getMatch().getTeamBatting(true).getTeamRuns() >= 10)
	    	{
	    		score = "0";
	    	}
	    	
	    	if (game.getMatch().getCurrentOver() < 11)
	    	{
	    		over = "0";
	    	}
	    	
	    	overallScore.setText((score + String.valueOf(game.getMatch().getTeamBatting(true).getTeamRuns())).substring(0, 3));
	    	wickets.setText(String.valueOf(game.getMatch().getTeamBatting(true).getTeamWicketsLost()));
	    	overs.setText(String.valueOf(over +((game.getMatch().getCurrentOver() - 1) + (activeOver.getDeliveries().size() * 0.1))).substring(0, 4));
	    	
	    	TextView batFacing = (TextView) ll.findViewById(R.id.batsmanOnTop_Name_Scorescreen2); 
	    	TextView batFacingRuns = (TextView) ll.findViewById(R.id.batsmanOnTop_Runs_Scorescreen2); 
	    	TextView batFacingBalls = (TextView) ll.findViewById(R.id.batsmanOnTop_Balls_Scorescreen2);
	    	TextView batFacing4s = (TextView) ll.findViewById(R.id.batsmanOnTop_4s_Scorescreen2); 
	    	TextView batFacing6s = (TextView) ll.findViewById(R.id.batsmanOnTop_6s_Scorescreen2); 
	    	TextView batFacingRunRate = (TextView) ll.findViewById(R.id.batsmanOnTop_runRate_Scorescreen2); 
	    	
	    	TextView batNotFacing = (TextView) ll.findViewById(R.id.batsmanOnBot_Name_Scorescreen); 
	    	TextView batNotFacingRuns = (TextView) ll.findViewById(R.id.batsmanOnBot_Runs_Scorescreen); 
	    	TextView batNotFacingBalls = (TextView) ll.findViewById(R.id.batsmanOnBot_Balls_Scorescreen);
	    	TextView batNotFacing4s = (TextView) ll.findViewById(R.id.batsmanOnBot_4s_Scorescreen); 
	    	TextView batNotFacing6s = (TextView) ll.findViewById(R.id.batsmanOnBot_6s_Scorescreen); 
	    	TextView batNotFacingRunRate = (TextView) ll.findViewById(R.id.batsmanOnBot_runRate_Scorescreen); 
	    	
	    	batFacing.setText(game.getMatch().getTeamBatting(true).getFacing().getName() + "*");
	    	batFacingRuns.setText(String.valueOf(game.getMatch().getTeamBatting(true).getFacing().getBattingFigures()));
	    	batFacingBalls.setText(String.valueOf(game.getMatch().getTeamBatting(true).getFacing().getNumberOfDeliveries()));
	    	batFacing4s.setText( String.valueOf(game.getMatch().getTeamBatting(true).getFacing().getFours()));
	    	batFacing6s.setText(String.valueOf(game.getMatch().getTeamBatting(true).getFacing().getSixes()));
	    	
	
	    	
	    	double batRR = game.getMatch().getTeamBatting(true).getFacing().getStrikeRate();
	    	double notBatRR = game.getMatch().getTeamBatting(true).getNotFacing().getStrikeRate();
	    	
	    	if (String.valueOf(batRR).length() > 5)
	    	{
	    		String newString = (String.valueOf(batRR).substring(0, 5));
	    		batFacingRunRate.setText(newString);
	    	}
	    	else
	    	{
	    		batFacingRunRate.setText(String.valueOf(batRR));
	    	}
	    	
	    	if (String.valueOf(notBatRR).length() > 5)
	    	{
	    		String newString = (String.valueOf(notBatRR).substring(0, 5));
	    		batNotFacingRunRate.setText(newString);
	    	}
	    	else
	    	{
	    		batNotFacingRunRate.setText(String.valueOf(notBatRR));
	    	}
	    	
	    	
	    	
	    	
	    	
	    	batNotFacing.setText(String.valueOf(game.getMatch().getTeamBatting(true).getNotFacing().getName()));
	    	batNotFacingRuns.setText(String.valueOf(game.getMatch().getTeamBatting(true).getNotFacing().getBattingFigures()));
	    	batNotFacingBalls.setText(String.valueOf(game.getMatch().getTeamBatting(true).getNotFacing().getNumberOfDeliveries()));
	    	batNotFacing4s.setText( String.valueOf(game.getMatch().getTeamBatting(true).getNotFacing().getFours()));
	    	batNotFacing6s.setText(String.valueOf(game.getMatch().getTeamBatting(true).getNotFacing().getSixes()));
	    	
	    	TextView bowlerName = (TextView) ll.findViewById(R.id.Bowler_Name_Scorescreen2); 
	    	TextView bowlerOvers = (TextView) ll.findViewById(R.id.Bowler_Overs_Scorescreen2); 
	    	TextView bowlerRuns = (TextView) ll.findViewById(R.id.Bowler_Runs_Scorescreen2); 
	    	TextView bowlerWickets = (TextView) ll.findViewById(R.id.Bowler_Wickets_Scorescreen2);
	    	TextView bowlerMaidens = (TextView) ll.findViewById(R.id.Bowler_Maidens_Scorescreen2); 
	    	TextView bowlerEconomy = (TextView) ll.findViewById(R.id.Bowler_Economy_Scorescreen2); 
	 
	    	bowlerName.setText(String.valueOf(game.getMatch().getNextBowler().getName()));
	    	int figures = (int) game.getMatch().getNextBowler().getRunsConceded();
	    	int newRuns = activeOver.getOverRuns();
	    	int extrasBowler = activeOver.extrasBowlersFault();
	    	bowlerRuns.setText(String.valueOf((int) figures + newRuns + extrasBowler));
	    	bowlerWickets.setText(String.valueOf(game.getMatch().getNextBowler().getWicketsTaken() + activeOver.getOverWicketsForBowler()));
	    	bowlerMaidens.setText(String.valueOf(game.getMatch().getNextBowler().getMaidens()));
	    	bowlerOvers.setText(String.valueOf(game.getMatch().getNextBowler().getOvers().size() + (activeOver.getDeliveries().size() * 0.1)).substring(0, 3));
	    	double economy = 0.0;
	    	int overNoBalls = 0;
	    	int overLegByes = 0;
	    	int overByes = 0;
	    	int overWides = 0;
	    	if (activeOver.getDeliveries().size() > 0 || game.getMatch().getNextBowler().getOvers().size() > 0)
	    	{
	    		economy = ((int) game.getMatch().getNextBowler().getBowlingFigures() + activeOver.getOverRuns() + activeOver.extrasBowlersFault()) / (game.getMatch().getNextBowler().getOvers().size() + (activeOver.getDeliveries().size() * 0.1));
	    		for (int i = 0; i < game.match.getActiveOver().getDeliveries().size(); i++)
	    		{
	    			if ( activeOver.getDeliveries().get(i).getExtraType() == 3)
					{
	    				overByes += activeOver.getDeliveries().get(i).getExtraValue();
					}
					else if ( activeOver.getDeliveries().get(i).getExtraType() == 4)
					{
						overLegByes += activeOver.getDeliveries().get(i).getExtraValue();
					}
					else if ( activeOver.getDeliveries().get(i).getExtraType() == 2)
					{
						overWides += activeOver.getDeliveries().get(i).getExtraValue();
					}
					else if ( activeOver.getDeliveries().get(i).getExtraType() == 1)
					{
						overNoBalls += activeOver.getDeliveries().get(i).getExtraValue();
					}
	    			
	    		}
	    	}
	    	if (String.valueOf(economy).length() > 4)
	    	{
	    		bowlerEconomy.setText(String.valueOf(economy).substring(0, 5));
	    	}
	    	else
	    	{
	    		bowlerEconomy.setText(String.valueOf(economy));
	    	}
	    	game.getMatch().getTeamBatting(false).getTeamExtras();
	    	TextView bowlerExtras = (TextView) ll.findViewById(R.id.Bowler_Extras_Scorescreen2); 
	    	bowlerExtras.setText("Extras: lb " + String.valueOf(overLegByes + game.getMatch().getTeamBatting(false).getTeamLegByes()) + ", b " + 
	    										String.valueOf(overByes + game.getMatch().getTeamBatting(false).getTeamByes()) + ", nb " + 
	    										String.valueOf(overNoBalls + game.getMatch().getTeamBatting(false).getTeamNoBalls()) + ", w " +
	    										String.valueOf(overWides + game.getMatch().getTeamBatting(false).getTeamWides()));
	    	String currentOverString = "Current Over: ";
	    	for (int i = 0; i < activeOver.getDeliveries().size(); i++)
	    	{
	    		if (activeOver.getDeliveries().get(i).getRunValue() > 0)
	    		{
	    			currentOverString += activeOver.getDeliveries().get(i).getRunValue();
	    		}
	    		else if (activeOver.getDeliveries().get(i).getRunValue() == 0
	    				 && activeOver.getDeliveries().get(i).getExtraValue() == 0 
	    				 && activeOver.getDeliveries().get(i).getWicketType() == 99)
				 {
	    			currentOverString += ".";
				 }
	    		else if (activeOver.getDeliveries().get(i).getWicketType() < 5)
	    		{
	    			currentOverString += "W";
	    		}
	    		else if (activeOver.getDeliveries().get(i).getExtraValue() != 0)
	    		{
	    			currentOverString +=activeOver.getDeliveries().get(i).getExtraValue() + activeOver.getDeliveries().get(i).getExtraTypeStringShort();
	    		}
			
	    		
	    		
	    		currentOverString += " ";
	    	}
	    	
	    	TextView currentOver = (TextView) ll.findViewById(R.id.Bowler_currentOver_Scorescreen2); 
	    	currentOver.setText(currentOverString);
        }
    	else
    	{
    		TextView wickets = (TextView) ll.findViewById(R.id.overall_wickets_scorescreen); 
        	wickets.setText(String.valueOf(10));
    		
    	}
    	
    	if (game.isOneTeamOut())
    	{
    		
    		
    		CharSequence text = "Innings over! Switching Teams!";
    		int duration = Toast.LENGTH_LONG;
    		Toast toast = Toast.makeText(fa, text, duration);
    		toast.show();
    	}
    	if (thisWillWork == 1)
    	{
    		mListener.onScreenChange();
    	}
    	thisWillWork = 1;
    }
    
    @Override
    public void onAttach(Activity activity) 
    {
        super.onAttach(activity);
        try 
        {
            mListener = (onScreenChange) activity;
        } catch (ClassCastException e) 
        {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
    	thisWillWork = 0;
    }
    
    public interface onScreenChange 
    {
        public void onScreenChange();
    }
    
    public void extraButtonDialog()
    {
    	new AlertDialog.Builder(fa)
    	.setTitle(R.string.extra)
    	.setItems(R.array.extras, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialoginterface, int i) {
				
				extraButtonRunsDialog(i+1);
				
			}
		})
		.show();
    }
    
    public void batsmanCrossDialog()
    {
    	
    	new AlertDialog.Builder(fa)
    	.setTitle(R.string.batsmanCross)
    	.setCancelable(false)
    	.setItems(R.array.yesno, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialoginterface, int i) {
				
				if (i == 0)
				{
					game.getMatch().getTeamBatting(true).swapBatsman();
				}
				screenSetup();
				
			}
		})
		.show();
    }
    
    public void whichBatsmanIsOut()
    {
    	String[] bothBatsman = new String [2];
    	bothBatsman[0] = game.getMatch().getTeamBatting(true).getFacing().getName();
    	bothBatsman[1] = game.getMatch().getTeamBatting(true).getNotFacing().getName();
    	
    	
    	
    	new AlertDialog.Builder(fa)
    	.setTitle(R.string.batsmanOut)
    	.setItems(bothBatsman, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialoginterface, int i) {
				
				Log.e(TAG, "Batsman Out: " + i);
				if (i == 1)
				{
					// Batsman Not facing gets out
					batsmanFacing = game.getMatch().getTeamBatting(true).getFacing();
					game.getMatch().getTeamBatting(true).swapBatsman();
					batsmanOut = game.getMatch().getTeamBatting(true).getFacing();
					wicketTakerDialog(4, 0);
				}
				else
				{
					//Batman Facing gets out
					batsmanOut = game.getMatch().getTeamBatting(true).getFacing();
					wicketTakerDialog(4, 1);
					
				}
				
			}
		})
		.show();
    }
    
    public void extraButtonRunsDialog(int type)
    {
    	final int extraType = type;
    	new AlertDialog.Builder(fa)
    	.setTitle(R.string.noOfExtras)
    	.setItems(R.array.runs, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialoginterface, int i) {
				Log.d(TAG, "Clicked on: " + i);
				Delivery d = new Delivery(0, (i+1), true, extraType, batsmanFacing, currentOver);
				try {
					game.deliveryBowled(d, false);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e(TAG, "Delivery details = " + d.toString());
				screenSetup();
				
			}
		})
		.show();
    }
    
    public void getNewBowlerDialog()
    {
    	if (game.isOneTeamOut() == true)
    	{
    		bowlerPicked = 11;
    		game.setOneTeamOut(false);		
    	}
    	String[] bowlerNameArray;
    	bowlerNameArray2 = new String[10];
    	if (game.getMatch().getTeam(1).isCurrentlyBatting())
    	{
    		bowlerNameArray = teamTwoPlayerStrings;
    	}
    	else
    	{
    		bowlerNameArray = teamOnePlayerStrings;
    	}
    	List<String> list = new ArrayList<String>(Arrays.asList(bowlerNameArray));
    	for (int i = 0; i < list.size(); i++)
    	{
    		// Just changed from == to equals, should be a string
    		if (list.get(i).equals(game.getMatch().getPreviousBowler().getName()))
    		{
    			Log.e(TAG, "Same Bowler Chosen, " + game.getMatch().getPreviousBowler().getName());
    			list.remove(i);
    		}
    		else
    		{
    			Log.e(TAG, "Same Bowler Chosen, UH OH " + list.get(i).toString());
    		}
    		
    	}
    	bowlerNameArray2 = list.toArray(bowlerNameArray2);
    	/*for (int i = 0; i < bowlerNameArray2.length; i++)
    	{
    		Log.e(TAG, "BowlerName = " + bowlerNameArray2[i]);
    	}*/
    	
    	Log.e(TAG, "BOWLER Size" + bowlerNameArray2.length);
    	Log.v(TAG, "GetNewBowler Dialog Entered");
    	new AlertDialog.Builder(fa)
    	.setTitle(R.string.newBowlerEntry)
    	.setCancelable(false)
    	.setItems(bowlerNameArray2, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialoginterface, int i) 
			{
				if (i >= bowlerPicked)
				{
					i++;
				}
				Log.d(TAG, "Clicked on: " + i);
				Log.d(TAG, "CurrentBowler Before = = =: " + game.getMatch().getNextBowler().getName());
				game.getMatch().setNextBowler(game.getMatch().getTeamBatting(false).getPlayers().get(i));
				game.getMatch().getActiveOver().setBowler(game.getMatch().getTeamBatting(false).getPlayers().get(i));
				Log.d(TAG, "CurrentBowler After = = =: " + game.getMatch().getNextBowler().getName());
				bowlerPicked = i;
				screenSetup();
				
			}
		})
		.show(); 	
    }
    
    
    
    public void wicketButtonDialog()
    {
    	Log.v(TAG, "Wicket Dialog Entered");
    	new AlertDialog.Builder(fa)
    	.setTitle("How did " +game.getMatch().getTeamBatting(true).getFacing().getName() + " get out?")
    	.setItems(R.array.dismissals, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialoginterface, int i) {
				
				Log.d(TAG, "Clicked on: " + i);
				if (i == 1 || i == 2)
				{
					Log.d(TAG, "LBW or Bowled" + i);
					try {
						game.deliveryBowled(new Delivery(true, i, batsmanFacing, game.getMatch().getNextBowler(), batsmanFacing, currentOver, null), false);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					if (game.getMatch().isOverFinished())
					{
						Over thisOver = game.getMatch().getOverForBowler();
						Player currentPlayer = game.getMatch().getActiveOver().getBowler();
						//Log.v(TAG, "CurrentBowler = " + currentPlayer.getName());
						game.addOverToDatabase(thisOver, game.getMatch().getTeamBatting(false));
						
						currentPlayer.addOverToPlayer(thisOver);
						getNewBowlerDialog();
						game.getMatch().getTeamBatting(true).swapBatsman();
					}
					screenSetup();
				}
				else if (i == 4)
				{
					whichBatsmanIsOut();
					Log.d(TAG, "Other dismissals" + i);
					
				}
				else
				{
					Log.d(TAG, "Other dismissals" + i);
					wicketTakerDialog(i, 0);
				}
			}
		})
		.show();	
    }
    
    public void wicketTakerDialog(int wkt, final int batOut)
    {
    	final int wktType = wkt;
    	Log.v(TAG, "Wicket Taker Dialog Entered");
    	String[] bowlerNameArray;
    	bowlerNameArray2 = new String[10];
    	if (game.getMatch().getTeam(1).isCurrentlyBatting())
    	{
    		bowlerNameArray = teamTwoPlayerStrings;
    	}
    	else
    	{
    		bowlerNameArray = teamOnePlayerStrings;
    	}
    	
    	
    	new AlertDialog.Builder(fa)
    	.setTitle(R.string.wicketAssist)
    	.setItems(bowlerNameArray, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialoginterface, int i) {
				
				boolean rightManout = false;
				if (batOut == 0)
		    	{
					rightManout = true;
		    		//batsmanOut = game.getMatch().getTeamBatting(true).getFacing();
		    	}
				else
				Log.d(TAG, "Clicked on: " + i);
				try {
					game.deliveryBowled(new Delivery(true, wktType,  batsmanFacing, game.getMatch().getNextBowler(), batsmanOut, currentOver, game.getMatch().getTeamBatting(false).getPlayerByPos(i+1)), rightManout);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d(TAG, "Batsman Facing: " + batsmanFacing.getName());
				Log.d(TAG, "Batsman Out: " + batsmanOut.getName());
				// game.getMatch().getTeamBatting(false).getPlayerByPos(i+1) this is dismissal taker
				if (batOut == 0)
		    	{
					game.getMatch().getTeamBatting(true).swapBatsman();
		    	}
		    	
				if (game.getMatch().isOverFinished())
				{
					game.getMatch().getNextBowler().addOverToPlayer(activeOver);
					getNewBowlerDialog();
					game.getMatch().getTeamBatting(true).swapBatsman();
				}
				
				if ((wktType == 0 || wktType == 4) && batOut == 1)
		    	{
		    		batsmanCrossDialog();
		    		
		    	}
				
				screenSetup();
			}
		})
		.show();	
    }

	public void onClick(View v) {
		if (game.isMatchOver() == false )
        {
    
			Delivery d = null;
			batsmanFacing = game.getMatch().getTeamBatting(true).getFacing();
			currentOver = game.getMatch().getCurrentOver();
			
			switch (v.getId())
	    	{
	    	case R.id.wicketbutton:
	    		wicketButtonDialog();
	    		break;
	    		
	    	case R.id.extrabutton:
	    		extraButtonDialog();
	    		break;
	    		
	    	case R.id.norun:
	    		d=new Delivery(0, 0, false, 0, batsmanFacing, currentOver);
	    		break;
	    		
	    	case R.id.onerun:
	    		d = new Delivery(1, 0, false, 0, batsmanFacing, currentOver);
	    		break;
	    		
	    	case R.id.tworun:
	    		d = new Delivery(2, 0, false, 0, batsmanFacing, currentOver);
	    		break;
	    		
	    	case R.id.threerun:
	    		d = new Delivery(3, 0, false, 0, batsmanFacing, currentOver);
	    		break;
	    		
	    	case R.id.fourrun:
	    		d = new Delivery(4, 0, false, 0, batsmanFacing, currentOver);
	    		break;
	    		
	    	case R.id.fiverun:
	    		d = new Delivery(5, 0, false, 0, batsmanFacing, currentOver);
	    		break;
	    		
	    	case R.id.sixrun:
	    		d = new Delivery(6, 0, false, 0, batsmanFacing, currentOver);
	    		break;
	    		/*
	    	case R.id.sendData:
	    		try {
					JSONConverter.getInstance().wrapAndSend();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		
	    		break;
	    		*/
	    	}
			if (v.getId() != R.id.wicketbutton && v.getId() != R.id.extrabutton //&& v.getId() != R.id.sendData)
					)
			{
				Over thisOverBefore = game.getMatch().getActiveOver();
				Log.e(TAG, "Into the delivery bowled if");
				try {
					game.deliveryBowled(d, false);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (game.getMatch().isOverFinished())
				{
					
					// CHANGED OVER GET M<ETHOD
					Over thisOver = game.getMatch().getOverForBowler();
					Player currentPlayer = game.getMatch().getActiveOver().getBowler();
					Log.v(TAG, "CurrentBowler = " + currentPlayer.getName());
					game.addOverToDatabase(thisOver, game.getMatch().getTeamBatting(false));
					
					currentPlayer.addOverToPlayer(thisOver);
					getNewBowlerDialog();
					game.getMatch().getTeamBatting(true).swapBatsman();
				}
			}
			screenSetup();	
        }

		
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		inflater.inflate(R.menu.menu, menu);
	   // super.onCreateOptionsMenu(menu, inflater);
		
        
	    
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    // Handle item selection
	    switch (item.getItemId()) 
	    {
	        case R.id.send_data:
	        	try 
	        	{
					if (JSONConverter.getInstance().wrapAndSend())
					{
						CharSequence text = "Data Sent!";
			    		int duration = Toast.LENGTH_SHORT;
			    		Toast toast = Toast.makeText(fa, text, duration);
			    		toast.show();
					}
					else
					{
						CharSequence text = "Error: Data unable to Send!";
			    		int duration = Toast.LENGTH_LONG;
			    		Toast toast = Toast.makeText(fa, text, duration);
			    		toast.show();
					}
				} 
	        	catch (Exception e1) 
	        	{
					e1.printStackTrace();
					
				}
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	

    public void setupButtons()
    
    {
    	View extraButton= ll.findViewById(R.id.extrabutton);
        extraButton.setOnClickListener(this);
        View wicketButton= ll.findViewById(R.id.wicketbutton);
        wicketButton.setOnClickListener(this);
        View noRunButton= ll.findViewById(R.id.norun);
        noRunButton.setOnClickListener(this);
        View oneRunButton= ll.findViewById(R.id.onerun);
        oneRunButton.setOnClickListener(this);
        View twoRunButton= ll.findViewById(R.id.tworun);
        twoRunButton.setOnClickListener(this);
        View threeRunButton= ll.findViewById(R.id.threerun);
        threeRunButton.setOnClickListener(this);
        View fourRunButton= ll.findViewById(R.id.fourrun);
        fourRunButton.setOnClickListener(this);
        View fiveRunButton= ll.findViewById(R.id.fiverun);
        fiveRunButton.setOnClickListener(this);
        View sixRunButton= ll.findViewById(R.id.sixrun);
        sixRunButton.setOnClickListener(this);

        //View sendData= findViewById(R.id.sendData);
        //sendData.setOnClickListener(this);
    }

    
}