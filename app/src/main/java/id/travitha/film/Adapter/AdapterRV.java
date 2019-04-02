package id.travitha.film.Adapter;

import android.content.*;
import android.graphics.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.bumptech.glide.*;
import java.util.*;
import id.travitha.film.*;
import android.graphics.drawable.*;

public class AdapterRV extends RecyclerView.Adapter<AdapterRV.Holder> implements OnClickListener
{
    private ArrayList<ModelDataAplikasi> data;
    private int layout;
    public AdapterRV(ArrayList<ModelDataAplikasi> data,int layout){
        this.data = data;
        this.layout=layout;
    }

    @Override
    public AdapterRV.Holder onCreateViewHolder(ViewGroup parent, int xxx)
    {
        // inlate layout row_film.xml
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(layout,parent,false));
    }

    @Override
    public void onBindViewHolder(AdapterRV.Holder h, int p)
    {
        //
        //mengambil data dari array data
        String title = data.get(p).TITLE;
        String release = data.get(p).RELEASE;
        String genre = data.get(p).GENRE;
        String poster = data.get(p).POSTER;
        String t_unknown = h.v.getContext().getString(R.string.unknown);
        //mengatur title
        h.tvTitle.setText(title);
        //mengatur release
        h.tvRelease.setText(release);
        //mengatur genre
        if(genre.equals("")){
            h.tvGenre.setText(t_unknown);
            h.tvGenre.setTextColor(h.v.getContext().getResources().getColor(R.color.error));
        }else{
            h.tvGenre.setText(genre);
            h.tvGenre.setTextColor(h.v.getContext().getResources().getColor(R.color.movie_item_other));
        }
        //mengatur poster dengan Glide
        Glide.with(h.v.getContext()).load(poster).error(R.drawable.default_poster).into(h.ivPoster);

        //mengisi posisi
        h.v.setTag(p);
        h.v.setId(1512);
        //mengatur alamat ketika di klik
        h.v.setOnClickListener(this);

        h.ibFavourite.setTag(p);
        h.ibFavourite.setOnClickListener(this);
        int favourite = data.get(p).favourite;
        if(favourite==0){
            h.ibFavourite.setImageResource(R.drawable.ic_unfavourite);
        }else{
            h.ibFavourite.setImageResource(R.drawable.ic_favourite);
        }

        h.ibFavourite.setBackground(gd(h.v.getContext()));
    }

    @Override
    public int getItemCount()
    {
        //
        return data.size();
    }

    @Override
    public void onClick(View v)
    {
        //mengirim item yang di klik ke MovieDetail
        MrMovieDataBaseHelper dbHelper = new MrMovieDataBaseHelper(v.getContext());

        int position = (int) v.getTag();
        ModelDataAplikasi model = data.get(position);
        //ArrayList<ModelDataAplikasi> dbFaourite = dbHelper.readData();
        switch(v.getId()){
            case 1512:

                Intent itn = new Intent(v.getContext(),MovieDetail.class);
                itn.putExtra(MovieDetail.KEY_DATA,model);
                v.getContext().startActivity(itn);
                break;
            case R.id.rowmovieIBFavourite:
                int favourite = model.favourite;
                if(favourite==0){
                    dbHelper.putData(model);
                    favourite = 1;
                }else{
                    dbHelper.deleteData(model.ID);
                    favourite = 0;
                }
                ModelDataAplikasi inpModel = model;
                inpModel.setFavourite(favourite);
                data.remove(position);
                data.add(position,inpModel);
                notifyDataSetChanged();
        }

    }

    //class holder
    class Holder extends RecyclerView.ViewHolder{
        View v; //untuk parent
        TextView tvTitle,tvRelease,tvGenre; // untuk title,release dan genre
        ImageView ivPoster; //untuk Poster
        ImageButton ibFavourite;
        public Holder(View v){
            super(v);
            //deklarasi View
            this.v = v;
            tvTitle = v.findViewById(R.id.rowMovieTvTitle);
            tvRelease = v.findViewById(R.id.rowMovieTVRelease);
            ivPoster = v.findViewById(R.id.rowMovieIVCover);
            tvGenre = v.findViewById(R.id.rowMovieTVGenre);
            ibFavourite = v.findViewById(R.id.rowmovieIBFavourite);
        }
    }

    GradientDrawable gd(Context ctx){
        GradientDrawable result = new GradientDrawable();
        result.setShape(result.OVAL);
        result.setColor(ctx.getResources().getColor(R.color.primary));
        result.setCornerRadius(50);
        return result;
    }

}