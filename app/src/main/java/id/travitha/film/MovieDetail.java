package id.travitha.film;

import android.content.*;
import android.graphics.*;
import android.os.*;
import android.renderscript.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.bitmap_recycle.*;
import com.bumptech.glide.load.resource.bitmap.*;
import de.hdodenhof.circleimageview.*;

import android.support.v7.widget.Toolbar;

public class MovieDetail extends AppCompatActivity
{
    //konstan
    public static String KEY_DATA = "cie yang baca sc ini,udah ngopi belon?";

    private static ModelDataAplikasi movieData;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_detail);
        //mengambil intent
        Intent itn = getIntent();
        Parcelable par = itn.getExtras().getParcelable(KEY_DATA);
        if(par!=null){
            //bertujuan untuk membedakan saat rotasi atau data baru
            movieData = (ModelDataAplikasi) par;
        }
        //mengatur Toolbar/ActionBar
        Toolbar toolbar = findViewById(R.id.moviedetailToolbar);
        setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mengatur semua view
        setupPoster();
        setupTitle();
        setupRelease();
        setupRating();
        setupPopularity();
        setupLanguage();
        setupGenre();
        setupOverview();
    }

    private void setupPoster(){
        //Mengambil alamat poster
        String urlPoster = movieData.POSTER;
        //deklarasi CIV
        CircleImageView civDetail = findViewById(R.id.moviedetailCVPoster);
        //mengatur besar border CIV
        civDetail.setBorderWidth(3);
        //mengatur warna Border CIV
        civDetail.setBorderColorResource(R.color.accent);
        //Load Gambar poster dengan Glide
        Glide.with(this).load(urlPoster).error(R.drawable.default_poster).into(civDetail);

    }

    private void setupTitle(){
        //membaca data Title
        String title = movieData.TITLE;
        //deklarasi Textview title
        TextView tvTitle = findViewById(R.id.moviedetailTVTitle);
        //mengatur text
        tvTitle.setText(title);
    }

    private void setupRelease(){
        //membaca data Release
        String release = movieData.RELEASE;
        // deklarasi TextView release
        TextView tvRelease = findViewById(R.id.moviedetailTVRelease);
        // mengatur text
        tvRelease.setText(release);
    }

    private void setupRating(){
        //mengambil data rating
        String rating = movieData.RATING;
        // deklarasi TextView Rating
        TextView tvRating = findViewById(R.id.moviedetailTVRating);
        // mengatur text
        tvRating.setText(rating);
    }

    private void setupPopularity(){
        String popularity = movieData.POPULARITY;
        TextView tvPopularity = findViewById(R.id.moviedetailTVPopularity);
        tvPopularity.setText(popularity);
    }

    private void setupLanguage(){
        String language = movieData.LANGUAGE;
        TextView tvLanguage = findViewById(R.id.moviedetailTVLanguage);
        tvLanguage.setText(language);
    }

    private void setupGenre(){
        LinearLayout llGenres = findViewById(R.id.moviedetailLLGenres);
        String genre = movieData.GENRE;
        //memecah String Genre menjadi array
        String[] genreExploder = genre.split(",");
        for(int i = 0;i<genreExploder.length;++i){

            View v = getLayoutInflater().inflate(R.layout.genre_layout,null);
            TextView tvGenres = v.findViewById(R.id.genrelayoutTextView);
            tvGenres.setText(genreExploder[i]);
            if(genreExploder[i].equals("")){
                tvGenres.setText(getResources().getString(R.string.unknown));
                tvGenres.setTextColor(getResources().getColor(R.color.error));
            }
            llGenres.addView(v);
        }
    }

    private void setupOverview(){
        String overview = movieData.OVERVIEW;
        TextView tvOverView = findViewById(R.id.moviedetailTVOverview);
        tvOverView.setText(overview);
    }

    @Override
    public void onBackPressed()
    {
        // Menutup Activity ketika User Kembali ke halaman awal
        finish();
        super.onBackPressed();
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		finish();
		return super.onOptionsItemSelected(item);
	}



}
