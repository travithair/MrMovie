package id.travitha.film.MrFragment;

import android.os.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import id.travitha.film.*;
import id.travitha.film.Adapter.*;
import java.util.*;
import android.content.*;

public class FragmentUpcoming extends Fragment
{

    //konstan
    public static final String CODE = "KODE";
    public static final int CODE_REFRESH = 1;
    public static final String RECEIVER = "Up_Comming";
    //
    private LoadListFilm loadMovie;
    public static ArrayList<ModelDataAplikasi> listMovie = new ArrayList<>();
    private RecyclerView rv;
    public static AdapterRV rvAdapter;
    private GridLayoutManager glManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.fragment_upcoming,container,false);
        rv = v.findViewById(R.id.fragmentupcomingRecyclerView);

        //handle rotasi dan pertama kali di buka
        if(listMovie.size()==0){
            //jika array movie kosong
            //load data dr api
            loadMovie();
        }else{
            //jika array movie sudah memiliki isi/data
            //menampilkan daftar
            setupRV();
        }

        //mendaftar receiver untuk menerima pesan
        getContext().registerReceiver(receiver,new IntentFilter(RECEIVER));
		setRetainInstance(true);
        return v;
    }

    private void loadMovie(){
        loadMovie = new LoadListFilm(getContext(),RECEIVER,loadMovie.linkApiUpComing);
        loadMovie.execute("");
    }

    private void setupRV(){
        glManager = new GridLayoutManager(getContext(),2);
        rvAdapter = new AdapterRV(listMovie,R.layout.row_movie_grid);
        rv.setLayoutManager(glManager);
        rv.setAdapter(rvAdapter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context p1, Intent p2)
        {
            int code = p2.getIntExtra(CODE,0);
            if(code == CODE_REFRESH){
                //memperbahrui daftar
                loadMovie();
            }else{
                //mengambil hasil
                listMovie = loadMovie.result;
                //mengatur recyclerView
                setupRV();
                //hentikan asynctask
                loadMovie.cancel(true);
            }
        }


    };

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        getContext().unregisterReceiver(receiver);
    }




}
