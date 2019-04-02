package id.travitha.film;

import android.content.*;

public class MrMoviePreverense
{
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;
    private final String prefsName = "MrMoviePreferense";
    private Context ctx;
    public MrMoviePreverense(Context ctx){
        //deklarasi SharedPreferense
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(prefsName,0);
        edit = prefs.edit();
    }

    private final String key_latest_query = "que";
    public boolean putLatestQuery(String que){
        // di panggil ketika mengisi query
        return edit.putString(key_latest_query,que).commit();
    }

    public String getLatestQuery(){
        //di panggil ketika mengambil query terakhir yang di cari
        return prefs.getString(key_latest_query,"");
    }

    public void resetQuery(){
        //di panggil ketika query yang di ketik tidak memiliki hasil
        edit.remove(key_latest_query);
    }
    //konstan
    private final String locale = "locale";
    public static final String LOCALE_EN = "en";
    public static final String LOCALE_ID = "in";
    public boolean setLocale(String LOCALE){
        //menyimpan string locale
        return edit.putString(locale,LOCALE).commit();
    }

    public String getLocale(){
        //mengambil string locale
        return prefs.getString(locale,null);//default mengikuti pengaturan perangkat
        //
    }

    public void removeLocalPrefs(){
        edit.remove(locale).commit();
    }

	private final String favourite= "favourite";
	public boolean putFavorite(String f){
		f = prefs.getString(favourite,"")+","+f;
		return edit.putString(favourite,f).commit();
	}
	
	public String[] getFavourites(){
		return prefs.getString(favourite,"").split(",");
	}
}
