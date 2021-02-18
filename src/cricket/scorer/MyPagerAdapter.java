package cricket.scorer;


import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter 
{

	private final List<Fragment> fragments;
	
	/**
	* @param fm
	* @param fragments2
	*/
	public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments2) 
	{
		super(fm);
		this.fragments = fragments2;
	}
	
	/*
	* (non-Javadoc)
	*
	* @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	*/
	@Override
	public Fragment getItem(int position) 
	{

		return this.fragments.get(position);
	}
	
	/*
	* (non-Javadoc)
	*
	* @see android.support.v4.view.PagerAdapter#getCount()
	*/
	@Override
	public int getCount() 
	{
		return this.fragments.size();
	}
}