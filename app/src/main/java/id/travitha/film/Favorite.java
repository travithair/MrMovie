package id.travitha.film;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;
import android.os.*;
import java.util.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import android.content.*;
import android.support.v7.widget.*;
import id.travitha.film.MrFragment.*;
import id.travitha.film.Adapter.*;
import com.bumptech.glide.*;

public class Favorite extends AppCompatActivity
{
	private ArrayList<ModelDataAplikasi> list;
	private Toolbar toolbar;
	private TextView tvNoFavourite;
	private RecyclerView rv;
	private FavouriteAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setTitle(getResources().getString(R.string.favorite));
		setContentView(R.layout.page_favorite);
		toolbar = findViewById(R.id.pagefavoriteToolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		tvNoFavourite = findViewById(R.id.pagefavoriteTVNoFavourite);
		rv = findViewById(R.id.pagefavoriteRecyclerView);
		list = getFavoriteData();
		setList();
	}
	
	private void setList(){
		if(list.size()!=0){
			adapter = new FavouriteAdapter(list);
			rv.setLayoutManager(new GridLayoutManager(this,2));
			rv.setAdapter(adapter);
			rv.setVisibility(View.VISIBLE);
			tvNoFavourite.setVisibility(View.GONE);
		}else{
			tvNoFavourite.setVisibility(View.VISIBLE);
			rv.setVisibility(View.GONE);
		}
	}
	
	private ArrayList<ModelDataAplikasi> getFavoriteData(){
		ArrayList<ModelDataAplikasi> result = new MrMovieDataBaseHelper(this).readData();
		return result;
	}
	
	private class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.Holder> implements OnClickListener{
		private ArrayList<ModelDataAplikasi> list;
		public FavouriteAdapter(ArrayList<ModelDataAplikasi> list){
			this.list = list;
		}
		
		public class Holder extends RecyclerView.ViewHolder{
			private View v;
			private TextView tvTitle,tvRelease;
			private ImageView ivPoster;
			private ImageButton ibMore;
			public Holder(View v){
				super(v);
				this.v = v;
				this.tvTitle = v.findViewById(R.id.rowfavouriteTVTitle);
				this.tvRelease = v.findViewById(R.id.rowfavouriteTVRelease);
				this.ivPoster = v.findViewById(R.id.rowfavouriteIVCover);
				this.ibMore = v.findViewById(R.id.rowfavouriteIBMore);
			}
			
		}

		@Override
		public Favorite.FavouriteAdapter.Holder onCreateViewHolder(ViewGroup p, int p2)
		{
			View v = LayoutInflater.from(p.getContext()).inflate(R.layout.row_favourite,p,false);
			return new Holder(v);
		}

		@Override
		public void onBindViewHolder(Favorite.FavouriteAdapter.Holder h, int p)
		{
			String title = list.get(p).TITLE;
			String release = list.get(p).RELEASE;
			String poster_path = list.get(p).POSTER;
			h.tvTitle.setText(title);
			h.tvRelease.setText(release);
			
			h.v.setId(1512);
			h.v.setTag(p);
			h.v.setOnClickListener(this);
			
			h.ibMore.setTag(p);
			h.ibMore.setOnClickListener(this);
			//load poster
			Glide.with(h.v.getContext()).load(poster_path).error(R.drawable.default_poster).into(h.ivPoster);
		}

		@Override
		public int getItemCount()
		{
			return list.size();
		}

		@Override
		public void onClick(View v)
		{
			
			int position = (int)v.getTag();
			ModelDataAplikasi data = list.get(position);
			
			switch(v.getId()){
				case 1512:
					Intent itn = new Intent(v.getContext(),MovieDetail.class);
					itn.putExtra(MovieDetail.KEY_DATA,data);
					v.getContext().startActivity(itn);
					break;
				case R.id.rowfavouriteIBMore:
					updateData(position);
					MrMovieDataBaseHelper dbHelper = new MrMovieDataBaseHelper(v.getContext());
					dbHelper.deleteData(data.ID);
					
					list.remove(position);
					notifyDataSetChanged();
					
			}
			
			
		}

		private void updateData(int pos){
			ArrayList<ModelDataAplikasi> f1 = FragmentNowPlaying.listMovie;
			ArrayList<ModelDataAplikasi> f2 = FragmentUpcoming.listMovie;
			ArrayList<ModelDataAplikasi> f3 = FindMovie.movieList;
			ModelDataAplikasi model = list.get(pos);
			utils.matchID(f1,model.ID);
			utils.matchID(f2,model.ID);
			utils.matchID(f3,model.ID);
			FragmentNowPlaying.rvAdapter.notifyDataSetChanged();
			FragmentUpcoming.rvAdapter.notifyDataSetChanged();
			AdapterRV adapter = FindMovie.adapter;
			if(adapter!=null){
				adapter.notifyDataSetChanged();
			}
		}
		
	}

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		finish();
		return super.onOptionsItemSelected(item);
	}
	
	
	
}
