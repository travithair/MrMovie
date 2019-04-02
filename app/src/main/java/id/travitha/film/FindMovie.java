package id.travitha.film;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import id.travitha.film.Adapter.AdapterRV;
import android.view.*;

public class FindMovie extends AppCompatActivity implements SearchView.OnQueryTextListener
{
    private String RECEIVER = "Find_Movie"; //untuk alamat receiver ketika LoadListFilm mengambil data dari api
    private Toolbar toolbar;
    private SearchView search; //untuk pencarian
    private RecyclerView rv; //untuk penampil daftar film
    private LinearLayoutManager lManager; //untuk daftar list kebawah 1 item
    //jika ingin dua item seperti kemarin gunakan GridLayoutManager

    public static AdapterRV adapter;
    public static ArrayList<ModelDataAplikasi> movieList = new ArrayList<>();

    private MrMoviePreverense prefs;

    private LoadListFilm loadMovie; //mengambil data dari api
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        //mengatur page title
        setTitle(R.string.search);
        setContentView(R.layout.find_movie);
        //registrasi receiver untuk menerima daftar film
        registerReceiver(receiver,new IntentFilter(RECEIVER));
        //deklarasi View
        toolbar = findViewById(R.id.mainToolBar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        search = findViewById(R.id.mainSearch);
        rv = findViewById(R.id.mainRV);
        //deklarasi lain nya
        lManager = new LinearLayoutManager(this);
        prefs = new MrMoviePreverense(this);
        //pengaturan konfig search dan rv di bungkus dengan method
        setupSearch();
        //setupRV(getExample());
        
        //load daftar film ketika pertama di buka
        loadMovie =  new LoadListFilm(this,RECEIVER,loadMovie.linkApiSearch);
        String latest_query = prefs.getLatestQuery();
        if(movieList.size()==0){
            //jika daftar film belum ada
            loadMovie.execute(latest_query);
        }else{
            //jika data daftar film sudah ada atau terload sebelunya
            setupRV(movieList);
        }
    }

    private void setupSearch(){
        //mengatur hint
        search.setQueryHint(getResources().getString(R.string.search_hint));
        //setup agar searchview tetap terbuka
        search.setIconifiedByDefault(false);
        //setup agar keyboard tidak otomatis terbuka ketika membuka apk
        search.setFocusable(false);
        search.setSubmitButtonEnabled(true);
        //mengatur callback ketika keyword dimasukan
        search.setOnQueryTextListener(this);

    }

    private void setupRV(ArrayList<ModelDataAplikasi> arr){
        //mengatur layout manager untuk RecyclerView
        rv.setLayoutManager(lManager);
        //mendeklarasikan adapter untuk RecyclerView
        adapter = new AdapterRV(arr,R.layout.row_movie);
        //mengatur adapter untuk RecyclerView
        rv.setAdapter(adapter);


    }

    BroadcastReceiver receiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context ctx, Intent itn){
            //ketika LoadListFilm selesai mengambil data dari api maka pesan akan dikirim kesini
            movieList = loadMovie.result;// (ArrayList<ModelDataAplikasi>) itn.getBundleExtra("data").getSerializable("dataFilm");
            //mengisi daftar berdasarkan data yang di ambil dari api
            setupRV(movieList);
            //kondisi ketika hasil tidak kosong
            if(movieList.size()!=0){
                //menulis query terakhir ke SharedPreferense
                prefs.putLatestQuery(query);
            }
        }

    };

    @Override
    public boolean onQueryTextChange(String que)
    {
        // aksi ketika user mengetik judul film
        //karakter yabg tidak diperbolehkan
        String character = "*,(,),!,\",\',?,$,&,¥,®,^,€,£,©,±,×,÷,•,~,`,´,=,[,],¡,<,>,¢,|,\\,¿,:,;,%";
        //menjadikan array
        String[] characterArr = character.split(",");
        //melakukan perulangan berdasarkan vanyak karakter
        for(int i=0;i<characterArr.length;++i){
            if(que.contains(characterArr[i])){
                //jika memiliki karakter yang tidak di inginkan
                //menonaktifkan tombol submit/cari
                search.setSubmitButtonEnabled(false);
                //mengubah background berwarna error
                search.setBackgroundResource(R.drawable.search_error);
                break;
            }else{
                //jika tidak memiliki karakter yang tidak di inginkan
                //mengaktifkan tombol submit/cari
                search.setSubmitButtonEnabled(true);
                //mengubah background normal
                search.setBackgroundResource(R.drawable.search);
            }
        }
        return false;
    }

    String query = null; //penyimpanan query sementara ketika user mengetik query baru
    @Override
    public boolean onQueryTextSubmit(String que)
    {
        //menghindari kesalahan user menekan tombol submit sebelum mengisi judul film
        if(que!=null&&que.length()!=0){
            //deklarasi loadFilm
            loadMovie = new LoadListFilm(this,RECEIVER,loadMovie.linkApiSearch);
            //memulai permintaan ke Api
            loadMovie.execute(new String[]{que});
            query = que;
        }
        return false;

    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		finish();
		return super.onOptionsItemSelected(item);
	}








}
