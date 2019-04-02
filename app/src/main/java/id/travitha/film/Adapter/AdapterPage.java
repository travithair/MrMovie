package id.travitha.film.Adapter;

import android.support.v4.app.*;
import java.util.*;
import id.travitha.film.MrFragment.*;
import id.travitha.film.*;
import android.content.*;
import android.view.*;
import android.support.v4.view.*;

public class AdapterPage extends FragmentPagerAdapter
{

    // adapter untuk page

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    public AdapterPage(Context ctx,FragmentManager fm){
        super(fm);
        //deklarasi fragment now playing
        Fragment nowPlaying = new FragmentNowPlaying();
        // mengambil resource String now playing
        String t_nowPlaying = ctx.getResources().getString(R.string.now_playing);
        //menambahkan fragment now playing
        fragments.add(nowPlaying);
        //menambahkan title fragment now playing
        titles.add(t_nowPlaying);
        //deklarasi fragment upcoming
        Fragment upComing = new FragmentUpcoming();
        // mengambil resource String Upcoming
        String t_upcoming = ctx.getResources().getString(R.string.upcoming);
        //menambahkan fragment upcoming
        fragments.add(upComing);
        //menambahkan title fragment upcoming
        titles.add(t_upcoming);
    }

    @Override
    public int getCount()
    {
        // TODO: Implement this method
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position)
    {
        // TODO: Implement this method
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return titles.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View)object);

    }





}