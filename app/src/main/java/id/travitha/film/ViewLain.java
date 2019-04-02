package id.travitha.film;

import android.content.*;
import android.support.v7.app.*;
import android.widget.*;
import android.view.View.*;
import android.content.res.*;

public class ViewLain
{
    //static untuk menghindari double window dan akses gampang tanpa harus memanggil nama class
    private static AppCompatActivity act;
    private static String[] language = {"English","Indonesia"};
    private static MrMoviePreverense prefs;
    public static void showLanguage(AppCompatActivity ctx){
        //alertdialog baru
        act = ctx;
        prefs = new MrMoviePreverense(ctx);
        AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
        //array adapter dengan layout bawa'an android
        ArrayAdapter adapter = new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,language);
        alert.setCancelable(true);
        alert.setAdapter(adapter,onClick);
        alert.show();


    }

    private static DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener(){

        @Override
        public void onClick(DialogInterface di, int pos)
        {
            switch(pos){

                case 0:
                    prefs.setLocale(prefs.LOCALE_EN);
                    break;
                case 1:
                    prefs.setLocale(prefs.LOCALE_ID);
                    break;
            }
            act.startActivity(new Intent(act,MrMovieMain.class));
            act.finish();

        }


    };
}