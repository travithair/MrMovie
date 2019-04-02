package id.travitha.film;

import android.content.*;
import android.content.res.*;
import java.util.*;
import android.util.*;
import android.app.*;

public class utils
{
    public static void setLocale(Context ctx){
        //preferense
        MrMoviePreverense prefs = new MrMoviePreverense(ctx);
        //resources
        Resources res = ctx.getResources();
        //nama bahasa
        String locale_string = prefs.getLocale();
        if(locale_string!=null){
            //locale
            Locale locale = new Locale(locale_string);
            //config
            Configuration conf = res.getConfiguration();
            //display metriks
            DisplayMetrics metrics = res.getDisplayMetrics();
            //atur locale
            conf.locale = locale;
            //update pengaturan
            res.updateConfiguration(conf,metrics);
        }
    }
	
	public static void matchID(ArrayList<ModelDataAplikasi> list,String idx){
		for(int i = 0;i<list.size();++i){
			ModelDataAplikasi model = list.get(i);
			String id = model.ID;
			if(id.equals(id)){
				model.setFavourite(0);
				list.remove(i);
				list.add(i,model);
			}
		}

	}
}
