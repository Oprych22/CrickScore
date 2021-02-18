package cricket.scorer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;;

public class CricketScorerActivity extends Activity implements OnClickListener
{
    /** Called when the activity is first created. */
	
	ProgressDialog progressDialog;
	int myProgress = 0;
	private Game game = Game.getInstance(this);
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        View newMatchButton = findViewById(R.id.new_match_button);
        newMatchButton.setOnClickListener(this);
          
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
        
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Match ...");
        
    }
    @Override
    public void onPause()
    {
    	super.onPause();
    	progressDialog.dismiss();
    	
    }
    
    public void onResume(Bundle savedInstanceState)
    {
    	progressDialog.dismiss();
    	
    }
    
    public void onClick(View v)
    {
    	switch (v.getId())
    	{
    	case R.id.new_match_button:
    		
    		progressDialog.show();
    		startMatch(true);
    		break;
    	case R.id.exit_button:
    		this.finish();
    		break;
    		
    	}
    }
    
 
    
    public void startMatch(boolean newMatch)
    {
    	Intent i = new Intent(this, ViewPagerFragmentActivity.class);
    	i.putExtra("cricket.scorer.newMatch", newMatch);
		startActivity(i);
    	
    }
 }
    