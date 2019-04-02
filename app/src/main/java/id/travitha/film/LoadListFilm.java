package id.travitha.film;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

public class LoadListFilm extends AsyncTask<String,String,ArrayList<ModelDataAplikasi>>
{

    private Context ctx;
    private String receiver;
    private ProgressDialog pd;
    private String linkApi;
    public static String linkApiSearch = "https://api.themoviedb.org/3/search/movie?api_key=86b262c1bc1a8b0b6a44d7cec9dcde96&language=en-US&query=";
    public static String linkApiNowPlaying = "https://api.themoviedb.org/3/movie/now_playing?api_key=86b262c1bc1a8b0b6a44d7cec9dcde96&language=en-US";
    public static String linkApiUpComing = "https://api.themoviedb.org/3/movie/upcoming?api_key=86b262c1bc1a8b0b6a44d7cec9dcde96&language=en-US";
    public LoadListFilm(Context ctx,String receiver,String linkApi){
        //cara akses new LoadListFilm(Context,nama receiver,nama film);
        //hasil list film akan dikirim ke receiver
        this.ctx = ctx;
        this.receiver = receiver;
        this.linkApi = linkApi;
        String t_loading = ctx.getResources().getString(R.string.loading);
        String t_pleaseWait = ctx.getResources().getString(R.string.please_wait);
        pd = new ProgressDialog(ctx);
        pd.setTitle(t_loading);
        pd.setMessage(t_pleaseWait);
        pd.setCancelable(false);
	
    }

    @Override
    protected void onPreExecute()
    {
        // TODO: Implement this method
        super.onPreExecute();
        pd.show();
    }

    @Override
    protected ArrayList<ModelDataAplikasi> doInBackground(String[] p1)
    {
        // TODO: Implement this method
        String judul = p1[0];
        String dataJson = ambilDataJson(judul);
        if(dataJson!=null){
            return bacaJson(dataJson);
        }else{
            //jika judul yang dicari tidak ada makan mengembelikan array kosong agar tidak error
            return new ArrayList<ModelDataAplikasi>();
        }
    }

    @Override
    protected void onPostExecute(ArrayList<ModelDataAplikasi> result)
    {
        // TODO: Implement this method
        //menutup loading
        pd.dismiss();
        this.result = result;
        //mengirim pesan selesai
        Intent itn = new Intent(receiver);
        ctx.sendBroadcast(itn);
        super.onPostExecute(result);
    }




    private String ambilDataJson(String judulFilm){
        judulFilm = judulFilm.replace(" ","+");
        //fungsi atau method yang bertugas mengambil dan baca data dari api
        String hasilString = null; //untuk hasil akhir
        //link api
        String linkApi = this.linkApi;
       // String linkExample = "http://localhost:8080/response.js";
        //vitha 5c5b7be243fd9d7d4e5076252f640530
        //deye 86b262c1bc1a8b0b6a44d7cec9dcde96
        try
        {
            //membuat objek URL harus menggunakan try karena rawan error
            URL objekURL = new URL(linkApi.concat(judulFilm));
            HttpURLConnection koneksi = (HttpURLConnection) objekURL.openConnection();
            koneksi.setRequestMethod("GET");
            koneksi.setRequestProperty("User-Agent","Mozilla/5.0 (Linux; U; Android 4.4.4; en-us; GT-I9500 Build/KTU84Q) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/46.0.2490.85 Mobile Safari/537.36 XiaoMi/MiuiBrowser/2.1.1 x-miorigin:b");
            koneksi.setRequestProperty("Host",objekURL.getHost());
            //mengambil respon dari link api
            InputStream inpis = koneksi.getInputStream();
            //membaca data inpis menggunakan InputStreamReader
            InputStreamReader inpisReader = new InputStreamReader(inpis);
            //convert data inputstream tadi kedalam String
            BufferedReader pembaca = new BufferedReader(inpisReader); //inpisReader y bukan inpis kk suka salah pas belajar
            //karena program membaca baris per baris
            //baca nya pake perulangan
            StringBuilder penampung = new StringBuilder(); //menampung data String yang dibaca
            //kondisi jika line habis maka perulangan berhenti
            String bacaBaris = null;
            while((bacaBaris=pembaca.readLine())!=null){
                penampung.append(bacaBaris); //menampung bacaBaris (data)
            }

            hasilString = penampung.toString();//mengisi hasil akhir

        }
        catch (Exception e)
        {
            //jika error tidak ada program disini
            //Toast.makeText(ctx,e.toString(),0).show();
        }

        //jika error pengembalian null
        return hasilString;
    }


    private ArrayList<ModelDataAplikasi> bacaJson(String dataJson){
        String link_poster = "https://image.tmdb.org/t/p/w92";
        //mulai membaca data yang di ambil dari method ambilDataJson()
        ArrayList<ModelDataAplikasi> hasilArray = new ArrayList<>();
        try
        {
			MrMovieDataBaseHelper dbHelper = new MrMovieDataBaseHelper(ctx);
			ArrayList<ModelDataAplikasi> favourites = dbHelper.readData();
            JSONObject objRoot = new JSONObject(dataJson);
            // mengambil array json dari objRoot/dasar
            JSONArray jsonArrayFilm = objRoot.getJSONArray("results"); //key array dari json adalah results
            //melakukan perulangan untuk mengambil array menjadi String
            //perulangan dengan kondisi berdasarkan banyak item array
            for(int i = 0;i<jsonArrayFilm.length();++i){
                JSONObject objekFilm = jsonArrayFilm.getJSONObject(i); //objek film berisi title,genre,sinopsis dll yang berkaitan dengan film
				String id = objekFilm.getString("id");
				String title = objekFilm.getString("title"); //KEY title
                String poster = objekFilm.getString("poster_path");
                String overview = objekFilm.getString("overview");
                String release = objekFilm.getString("release_date");
                String genre = objekFilm.getString("genre_ids").replace("[","").replace("]",""); //menghapus karakter yang tidak di inginkan
                String popularity = objekFilm.getString("popularity");
                String language = objekFilm.getString("original_language").toUpperCase(); // karena response js dalam bentuk huruf kecil,Menjadikan Huruf Besar
                String rating = objekFilm.getString("vote_average");
				int favourite = 0;
				for(int x=0;x<favourites.size();++x){
					String ids = favourites.get(x).ID;
					if(ids.equals(id)){
						favourite = 1;
					}
				}
                //menampung data kedalam array
                ModelDataAplikasi modelku = new ModelDataAplikasi();
                modelku.setTITLE(title) //menambah title
                        .setRELEASE(release) //menambah tahun release
                        .setLANGUAGE(language) //menambah bahasa
                        .setPOSTER(link_poster.concat(poster)) //menambah Poster berupa link
                        .setPOPULARITY(popularity) //menambah popularitas
                        .setGENRE(genre) //menambah genre
                        .setOVERVIEW(overview) // menambah Sinopsis
                        .setRATING(rating) //menambah vote/rating
						.setId(id).setFavourite(favourite);
                //menampung modelku dalam array
                hasilArray.add(modelku);
            }
        }
        catch (Exception e)
        {
            //	Toast.makeText(ctx,e.toString(),0).show();
        }

        return hasilArray;
    }

    public ArrayList<ModelDataAplikasi> result;


}
