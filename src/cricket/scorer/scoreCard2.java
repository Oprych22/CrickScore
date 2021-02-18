package cricket.scorer;

import android.view.LayoutInflater;
import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



public class scoreCard2 extends Fragment implements OnClickListener 
{
	
	private Game game;
	private LinearLayout ll;
	private FragmentActivity fa;
	private Player batsmanFacing;
	private Player batsmanOut;
	private int currentOver;
	String[] teamOnePlayerStrings = new String[11];
	String[] teamTwoPlayerStrings = new String[11];
	String[] bowlerNameArray2;
	private int bowlerPicked = 11;
	
	private static final String TAG = "scoreCard";
    /** Called when the activity is first created. */

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	if (container == null) {
			return null;
		}
    	ll = (LinearLayout) inflater.inflate(R.layout.scorecard2, container, false);
    	fa = super.getActivity();
    	
        
        game = Game.getInstance(fa);
        batsmanFacing = game.getMatch().getTeamBatting(true).getFacing();
        currentOver = game.getMatch().getCurrentOver();
        batsmanOut = batsmanFacing;
        screenSetup();
        TextView battingName = (TextView) ll.findViewById(R.id.teamBatting_Scorecard2);
        battingName.setText(game.getMatch().getTeam(2).getName());
        
       
		
        return ll;
    }
    
    public void onResume(Bundle savedInstanceState)
    {
    	
    	 screenSetup();
    }
    
    public void screenSetup()
    {
    	TableLayout table = (TableLayout) ll.findViewById(R.id.ScoreCard2_Table);
    	
    	/*************************************************************************/
        /*			Batting creation                                             */
        /*************************************************************************/
    	
    	TableRow rowBatting = new TableRow(fa);
    	
    	TextView batting = new TextView(fa);
        TextView ballsH = new TextView(fa);
        TextView runsH = new TextView(fa);
        TextView foursH = new TextView(fa);
        TextView sixesH = new TextView(fa);
        TextView strikeRateH = new TextView(fa);
        
        batting.setText("Batting");
        batting.setTextColor(Color.WHITE);
        batting.setWidth(100);
        batting.setTextSize(20);
        
        runsH.setText("R");
        runsH.setTextColor(Color.WHITE);
        runsH.setWidth(100);
        runsH.setTextSize(20);
        
        ballsH.setText("B");
        ballsH.setTextColor(Color.WHITE);
        ballsH.setWidth(100);
        ballsH.setTextSize(20);
        
        foursH.setText("4s");
        foursH.setTextColor(Color.WHITE);
        foursH.setWidth(100);
        foursH.setTextSize(20);
        
        sixesH.setText("6s");
        sixesH.setTextColor(Color.WHITE);
        sixesH.setWidth(100);
        sixesH.setTextSize(20);
        
        strikeRateH.setText("S/R");
        strikeRateH.setTextColor(Color.WHITE);
        strikeRateH.setWidth(100);
        strikeRateH.setTextSize(20);
        
        rowBatting.setBackgroundColor(Color.BLACK);
        rowBatting.addView(batting);
        rowBatting.addView(runsH);
        rowBatting.addView(ballsH);
        rowBatting.addView(foursH);
        rowBatting.addView(sixesH);
        rowBatting.addView(strikeRateH);
        
        // add the TableRow to the TableLayout
        table.addView(rowBatting,new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    	
    	for (int i = 0; i < game.getMatch().getTeam(2).getPlayers().size(); i++)
    	{
    		Player currentPlayer = game.getMatch().getTeam(2).getPlayerByPos(i+1);
    		// create a new TableRow
            TableRow row = new TableRow(fa);
   
            TextView name = new TextView(fa);
            TextView balls = new TextView(fa);
            TextView runs = new TextView(fa);
            TextView fours = new TextView(fa);
            TextView sixes = new TextView(fa);
            TextView strikeRate = new TextView(fa);
            if (game.getMatch().getTeam(2).isCurrentlyBatting())
            {
	            if (currentPlayer.getName().equals(game.getMatch().getTeam(2).getFacing().getName()))
	            {
	            
		            name.setText(String.valueOf(currentPlayer.getName()) + "*");
		            
		            
	            }
	            else
	            {
	            	name.setText(String.valueOf(currentPlayer.getName()));      
	
	            }
            }
            else
            {
            	name.setText(String.valueOf(currentPlayer.getName()));      

            }
            name.setTextColor(Color.WHITE);
            name.setWidth(100);
            name.setTextSize(20);
            
            balls.setText(String.valueOf(currentPlayer.getDeliveries().size()));
            balls.setTextColor(Color.WHITE);
            balls.setWidth(100);
            balls.setTextSize(20);
            
            runs.setText(String.valueOf(currentPlayer.getBattingFigures()));
            runs.setTextColor(Color.WHITE);
            runs.setWidth(100);
            runs.setTextSize(20);
            
            fours.setText(String.valueOf(currentPlayer.getFours()));
            fours.setTextColor(Color.WHITE);
            fours.setWidth(100);
            fours.setTextSize(20);
            
            sixes.setText(String.valueOf(currentPlayer.getSixes()));
            sixes.setTextColor(Color.WHITE);
            sixes.setWidth(100);
            sixes.setTextSize(20);
            
            if (String.valueOf(currentPlayer.getStrikeRate()).length() > 5)
	    	{
            	 strikeRate.setText(String.valueOf(currentPlayer.getStrikeRate()).substring(0, 5));
	    	}
	    	else
	    	{
	    		 strikeRate.setText(String.valueOf(currentPlayer.getStrikeRate()));
	    	}
            

            strikeRate.setTextColor(Color.WHITE);
            strikeRate.setWidth(100);
            strikeRate.setTextSize(20);
            
            // Change Color based on row
            TableRow rowHowOut = new TableRow(fa);
            if (i % 2 == 0)
            {
            	row.setBackgroundColor(R.color.another_green);
            	rowHowOut.setBackgroundColor(R.color.another_green);
            }
            
     
            // add the TextView and the CheckBox to the new TableRow
            row.addView(name);
            row.addView(runs);
            row.addView(balls);
            row.addView(fours);
            row.addView(sixes);
            row.addView(strikeRate);
     
            // add the TableRow to the TableLayout
            table.addView(row,new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
            
            
            
            TextView howOut = new TextView(fa);
            howOut.setTextColor(Color.WHITE);
            howOut.setWidth(200);
            howOut.setText(currentPlayer.fullWicketInfo());
            howOut.setTextColor(Color.LTGRAY);
            
            rowHowOut.addView(howOut);
            table.addView(rowHowOut,new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    	}
    	
    	TableRow totalScore = new TableRow(fa);
    	
    	TextView teamScore = new TextView(fa);
        TextView scorePadding = new TextView(fa);
        TextView scorePadding2 = new TextView(fa);
        TextView scorePadding3 = new TextView(fa);
        TextView scorePadding4 = new TextView(fa);
        TextView allRuns = new TextView(fa);
        
        teamScore.setText("Total");
        teamScore.setTextColor(Color.WHITE);
        teamScore.setWidth(100);
        teamScore.setTextSize(20);
        
        allRuns.setText(String.valueOf(game.getMatch().getTeam(2).getTeamRuns()));
        allRuns.setTextColor(Color.WHITE);
        allRuns.setWidth(100);
        allRuns.setTextSize(20);
        
        scorePadding.setWidth(100);
        scorePadding2.setWidth(100);
        scorePadding3.setWidth(100);
        scorePadding4.setWidth(100);

        
        totalScore.setBackgroundColor(Color.BLACK);
        totalScore.addView(teamScore);
        totalScore.addView(allRuns);
        totalScore.addView(scorePadding);
        totalScore.addView(scorePadding2);
        totalScore.addView(scorePadding3);
        totalScore.addView(scorePadding4);
        
    
        table.addView(totalScore,new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        
        TableRow heightPad = new TableRow(fa);
        TextView moreHeightPad = new TextView(fa);
        moreHeightPad.setHeight(20);
        heightPad.addView(moreHeightPad);
        table.addView(heightPad,new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        
        /*************************************************************************/
        /*			BOWLING creation                                             */
        /*************************************************************************/
        TableRow rowBowling = new TableRow(fa);
    	
    	TextView bowling = new TextView(fa);
        TextView overs = new TextView(fa);
        TextView maidens = new TextView(fa);
        TextView runs = new TextView(fa);
        TextView wickets = new TextView(fa);
        TextView Economy = new TextView(fa);
        
        bowling.setText("Bowling");
        bowling.setTextColor(Color.WHITE);
        bowling.setWidth(100);
        bowling.setTextSize(20);
        
        overs.setText("O");
        overs.setTextColor(Color.WHITE);
        overs.setWidth(100);
        overs.setTextSize(20);
        
        maidens.setText("M");
        maidens.setTextColor(Color.WHITE);
        maidens.setWidth(100);
        maidens.setTextSize(20);
        
        runs.setText("R");
        runs.setTextColor(Color.WHITE);
        runs.setWidth(100);
        runs.setTextSize(20);
        
        wickets.setText("W");
        wickets.setTextColor(Color.WHITE);
        wickets.setWidth(100);
        wickets.setTextSize(20);
        
        Economy.setText("Economy");
        Economy.setTextColor(Color.WHITE);
        Economy.setWidth(100);
        Economy.setTextSize(20);
        
        rowBowling.setBackgroundColor(Color.BLACK);
        rowBowling.addView(bowling);
        rowBowling.addView(overs);
        rowBowling.addView(maidens);
        rowBowling.addView(runs);
        rowBowling.addView(wickets);
        rowBowling.addView(Economy);
        
        // add the TableRow to the TableLayout
        table.addView(rowBowling,new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        
        int changeColour = 1;
        
        for (int i = 0; i < game.getMatch().getTeam(1).getPlayers().size(); i++)
    	{
        	Player bowler = game.getMatch().getTeam(1).getPlayerByPos(i+1);
        	int hasHeBowled = 0;
        	int runsBowler = 0;
        	double ballsBowler = 0;
        	int maidensBowler = 0;
        	int wicketsBowler = 0;
        	double economyBowler = 0;
        	
        	if (bowler.getOvers().size() > 0 )
        	{
        		runsBowler += bowler.getRunsConceded();
            	ballsBowler = bowler.getOvers().size();
            	maidensBowler = bowler.getMaidens();
            	wicketsBowler += bowler.getWicketsTaken();
            	economyBowler += ((int) bowler.getBowlingFigures() ) / (bowler.getOvers().size() );
        	}
        	else
        	{
        		hasHeBowled++;
        	}
        	if (game.getMatch().getNextBowler().getName().equals(game.getMatch().getTeam(1).getPlayerByPos(i+1).getName()))
        	{
        		runsBowler += game.match.getActiveOver().getOverRuns() + game.match.getActiveOver().extrasBowlersFault();
            	ballsBowler += game.match.getActiveOver().getDeliveries().size() * 0.1;
            	wicketsBowler += game.match.getActiveOver().getOverWickets();
            	double newEconomyBowler = (game.match.getActiveOver().getOverRuns() + game.match.getActiveOver().extrasBowlersFault()) / 
						(game.match.getActiveOver().getDeliveries().size() * 0.1);
            	economyBowler = (economyBowler + newEconomyBowler) / 2;
            	//economyBowler += ((int) bowler.getBowlingFigures() ) / (bowler.getOvers().size() );
            	
        	
    	    	//bowlerOvers.setText(String.valueOf(game.getMatch().getNextBowler().getOvers().size() + ((double) game.match.getActiveOver().getDeliveries().size() * 0.1)).substring(0, 3));
        		
        	}
        	else
        	{
        		
        		hasHeBowled++;
        	}
        	
        	if (hasHeBowled >= 2)
        	{
        		continue;
        		
        	}
        	
        	
        	   
            TextView bowlerName = new TextView(fa);
            TextView bowlerBalls = new TextView(fa);
            TextView bowlerRuns = new TextView(fa);
            TextView bowlerMaidens = new TextView(fa);
            TextView bowlerWickets = new TextView(fa);
            TextView bowlerEconomy = new TextView(fa);
            
            if (game.getMatch().getNextBowler().getName().equals(game.getMatch().getTeam(1).getPlayerByPos(i+1).getName()))
            {
            
            	bowlerName.setText(String.valueOf(bowler.getName()) + "*");
	            
	            
            }
            else
            {
            	bowlerName.setText(String.valueOf(bowler.getName()));      

            }
            bowlerName.setTextColor(Color.WHITE);
            bowlerName.setWidth(100);
            bowlerName.setTextSize(20);
            
            bowlerBalls.setText(String.valueOf(ballsBowler).substring(0, 3));
            bowlerBalls.setTextColor(Color.WHITE);
            bowlerBalls.setWidth(100);
            bowlerBalls.setTextSize(20);
            
            bowlerRuns.setText(String.valueOf(runsBowler));
            bowlerRuns.setTextColor(Color.WHITE);
            bowlerRuns.setWidth(100);
            bowlerRuns.setTextSize(20);
            
            bowlerMaidens.setText(String.valueOf(maidensBowler));
            bowlerMaidens.setTextColor(Color.WHITE);
            bowlerMaidens.setWidth(100);
            bowlerMaidens.setTextSize(20);
            
            bowlerWickets.setText(String.valueOf(wicketsBowler));
            bowlerWickets.setTextColor(Color.WHITE);
            bowlerWickets.setWidth(100);
            bowlerWickets.setTextSize(20);
            
            if (String.valueOf(economyBowler).length() > 5)
	    	{
            	bowlerEconomy.setText(String.valueOf(economyBowler).substring(0, 5));
	    	}
	    	else
	    	{
	    		bowlerEconomy.setText(String.valueOf(economyBowler));
	    	}
            if (String.valueOf(economyBowler).equals("NaN"))
            {
            	bowlerEconomy.setText(String.valueOf(0.0));
            }
            
            bowlerEconomy.setTextColor(Color.WHITE);
            bowlerEconomy.setWidth(100);
            bowlerEconomy.setTextSize(20);
            
            // Change Color based on row
            TableRow bowlerRow = new TableRow(fa);
            if (changeColour % 2 == 0)
            {
            	bowlerRow.setBackgroundColor(R.color.another_green);
            }
            
     
            // add the TextView and the CheckBox to the new TableRow
            bowlerRow.addView(bowlerName);
            bowlerRow.addView(bowlerBalls);
            bowlerRow.addView(bowlerMaidens);
            bowlerRow.addView(bowlerRuns);
            bowlerRow.addView(bowlerWickets);
            bowlerRow.addView(bowlerEconomy);
     
            // add the TableRow to the TableLayout
            table.addView(bowlerRow,new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
            changeColour++;
  
    	}
    }
    
    
    

	public void onClick(View v) 
	{
		
		screenSetup();		
	}
	
  
    
}