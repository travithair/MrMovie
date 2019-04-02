package id.travitha.film.MrFragment;

import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import id.travitha.film.*;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.*;
import java.util.*;
import android.content.*;
import id.travitha.film.Adapter.*;

public class FragmentNowPlaying extends Fragment
{
    //konstan
    public static final String CODE = "KODE";
    public static final int CODE_REFRESH = 1;
    // nama Receiver
    public static final String RECEIVER = "Now_Playing";
    //data
    public static ArrayList<ModelDataAplikasi> listMovie = new ArrayList<>();

    //adapter
    public static AdapterRV rvAdapter;



    //Movie Loader
    private LoadListFilm movieLoader;

    //View
    private RecyclerView rv;
    private GridLayoutManager glManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.fragment_now_playing,container,false);
        rv = v.findViewById(R.id.fragmentnowplayingRecyclerView);

        if(listMovie.size()==0){
            loadMovie();
        }else{
            setupRV(listMovie);
        }
        //Mendaftarkan Receiver
        getContext().registerReceiver(receiver,new IntentFilter(RECEIVER));
		setRetainInstance(true);
        return v;
    }




    private void setupRV(ArrayList<ModelDataAplikasi> listMovie){
        rvAdapter = new AdapterRV(listMovie,R.layout.row_movie_grid);
        glManager = new GridLayoutManager(getContext(),2);
        rv.setLayoutManager(glManager);
        rv.setAdapter(rvAdapter);
    }

    private void loadMovie(){
        movieLoader = new LoadListFilm(getContext(),RECEIVER,movieLoader.linkApiNowPlaying);
        movieLoader.execute("");
    }

    private BroadcastReceiver receiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context ctx, Intent itn)
        {
            int code = itn.getIntExtra(CODE,0);
            if(code == CODE_REFRESH){
                //memperbahrui daftar
                loadMovie();
            }else{
                //mengambil hasil
                listMovie = movieLoader.result;
                //mengatur recyclerView
                setupRV(listMovie);
                //hentikan asynctask
                movieLoader.cancel(true);
            }

        }


    };

    @Override
    public void onDestroyView()
    {
        // TODO: Implement this method
        super.onDestroyView();
        getContext().unregisterReceiver(receiver);

    }



}
