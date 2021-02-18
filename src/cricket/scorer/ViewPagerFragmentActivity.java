package cricket.scorer;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

import cricket.scorer.scoreScreen.onScreenChange;


public class ViewPagerFragmentActivity extends FragmentActivity implements onScreenChange
{
	private static final String TAG = "VIEWPAGER";
	private PagerAdapter mPagerAdapter;
	List<Fragment> fragments;
	boolean newMatch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.viewpager_layout);
	
	// initialize the pager
		newMatch = getIntent().getBooleanExtra("cricket.scorer.newMatch", false);
		this.initialisePaging();
		
	}
	
	/**
	* Initialize the fragments to be paged
	*/
	private void initialisePaging() 
	{
		
		fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, scoreScreen.class.getName()));
		fragments.add(Fragment.instantiate(this, scoreCard.class.getName()));
		fragments.add(Fragment.instantiate(this, scoreCard2.class.getName()));
		this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);
		
		ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
		pager.setAdapter(this.mPagerAdapter);
		/*
		Fragment newFragment = getSupportFragmentManager().findFragmentById(fragments.get(1).getId());
		Bundle bundl = new Bundle();
		bundl.putBoolean("newMatch", newMatch);
		
		newFragment.setArguments(bundl);
		*/
	}
	
	public void onSelected()
	{
		Fragment newFragment = new scoreCard();
		FragmentManager newManager = getSupportFragmentManager();
		FragmentTransaction transaction = newManager.beginTransaction();

		transaction.replace(R.id.first_scorecard, newFragment);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
	    transaction.commitAllowingStateLoss();
	}

	public void onScreenChange() 
	{

		Fragment newFragment = getSupportFragmentManager().findFragmentByTag(fragments.get(1).getTag());
		
		newFragment.onResume();
	
	}
	
	public  boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	


}