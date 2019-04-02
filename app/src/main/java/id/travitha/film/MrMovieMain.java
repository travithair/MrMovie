package id.travitha.film;

import android.support.v7.app.*;
import android.os.*;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.*;
import id.travitha.film.Adapter.*;
import android.content.*;
import android.view.*;
import id.travitha.film.MrFragment.*;
import android.support.design.widget.BottomNavigationView;

public class MrMovieMain extends AppCompatActivity 
{
    private Toolbar toolBar;
    private TabLayout tabs = null;
    private ViewPager pages = null;
    //
    private AdapterPage pagesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO: Implement this method
        utils.setLocale(this);
        super.onCreate(savedInstanceState);
        
		home();
    }
	
	private void home(){
		//R.drawable.abc_ic_star_black_16dp
        setContentView(R.layout.mr_main);
        //toolbar/ActionBar
        toolBar = findViewById(R.id.mrmainToolBar);
        //setup Toolbar
        setSupportActionBar(toolBar);
        //tabs
        tabs = findViewById(R.id.mrmainTabs);
        //pages
        pages = findViewById(R.id.mrmainVPPager);
        //page adapter
        pagesAdapter = new AdapterPage(this,getSupportFragmentManager());
        //setup pagerAdapter
        pages.setAdapter(pagesAdapter);

        //setup tabs
        tabs.setupWithViewPager(pages);
        //tabs.setTabMode(tabs.MODE_SCROLLABLE);
	}
	
	

    //Refresh daftar
    private void refresh(){
        //intent alamat receiver
        Intent itn_nowPlaying = new Intent(FragmentNowPlaying.RECEIVER);
        Intent itn_upcoming = new Intent(FragmentUpcoming.RECEIVER);
        //put pesan
        itn_nowPlaying.putExtra(FragmentNowPlaying.CODE,FragmentNowPlaying.CODE_REFRESH);
        itn_upcoming.putExtra(FragmentUpcoming.CODE,FragmentUpcoming.CODE_REFRESH);
        //mengirim pesan
        sendBroadcast(itn_nowPlaying);
        sendBroadcast(itn_upcoming);
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //on menu item clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
			case R.id.menu_favourite:
				Intent itnFavourite = new Intent(this,Favorite.class);
				startActivity(itnFavourite);
				break;
            case R.id.menu_search:
                //menu Cari
                Intent itn = new Intent(this,FindMovie.class);
                startActivity(itn);
                break;
            case R.id.menu_refresh:
                refresh();
                break;
            case R.id.menu_language:
                //menu bahasa
                ViewLain.showLanguage(this);
                break;
            case R.id.menu_exit:
                //menu keluar
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        finish();
    }


}
