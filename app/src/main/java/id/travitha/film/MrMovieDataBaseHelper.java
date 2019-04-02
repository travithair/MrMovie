package id.travitha.film;
import android.database.sqlite.*;
import android.content.*;
import java.util.*;
import android.database.*;

public class MrMovieDataBaseHelper extends SQLiteOpenHelper
{
	private static final String DB_NAME = "MR_MOVIE_DATABASE";
	private static final int DB_VERSION = 1;
	
	private final String 
	TABLE_NAME = "FAVOURITES",
	FIELD_ID = "ID",
	FIELD_TITLE="TITLE",
	FIELD_POSTER="POSTER_PATH",
	FIELD_RELEASE="RELEASE_DATE",
	FIELD_GENRE="GENRE",
	FIELD_RATING="RATING",
	FIELD_POPULARITY="POPULARITY",
	FIELD_LANGUAGE="LANGUAGE",
	FIELD_OVERVIEW="OVERVIEW",
	FIELD_FAVOURITE="FAVOURITE";
	
	
	public MrMovieDataBaseHelper(Context ctx){
		super(ctx,DB_NAME,null,DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String query = "CREATE TABLE "+TABLE_NAME
		+"("+
		FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
		FIELD_TITLE+ " TEXT,"+
		FIELD_POSTER+" TEXT,"+
		FIELD_RELEASE+" TEXT,"+
		FIELD_GENRE+" TEXT,"+
		FIELD_RATING+" TEXT,"+
		FIELD_POPULARITY+" TEXT,"+
		FIELD_LANGUAGE+" TEXT,"+
		FIELD_OVERVIEW+" TEXT,"+
		FIELD_FAVOURITE+" INTEGER"+
		")";
		
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int p2, int p3)
	{
		String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
		db.execSQL(query);
		onCreate(db);
	}
	
	//simpan data
	public void putData(ModelDataAplikasi data){
		
		String id = data.ID;
		String title = data.TITLE;
		String poster = data.POSTER;
		String release = data.RELEASE;
		String genre = data.GENRE;
		String rating = data.RATING;
		String popularity = data.POPULARITY;
		String language = data.LANGUAGE;
		String overview = data.OVERVIEW;
		String favourite = String.valueOf(data.favourite);
		ContentValues values = new ContentValues();
		values.put(FIELD_ID,id);
		values.put(FIELD_TITLE,title);
		values.put(FIELD_POSTER,poster);
		values.put(FIELD_RELEASE,release);
		values.put(FIELD_GENRE,genre);
		values.put(FIELD_RATING,rating);
		values.put(FIELD_POPULARITY,popularity);
		values.put(FIELD_LANGUAGE,language);
		values.put(FIELD_OVERVIEW,overview);
		values.put(FIELD_FAVOURITE,favourite);
		SQLiteDatabase db = getWritableDatabase();
		db.insert(TABLE_NAME,null,values);
		
		//db.update(TABLE_NAME,values,FIELD_ID+"=?",new String[]{id});
		db.close();
	}

	//baca data
	public ArrayList<ModelDataAplikasi> readData(){
		ArrayList<ModelDataAplikasi> result = new ArrayList<>();
		SQLiteDatabase db = getReadableDatabase();
		String query = "SELECT*FROM "+TABLE_NAME;
		Cursor cs = db.rawQuery(query,null);
		
		try{
		cs.moveToFirst();
		do{
			String id = cs.getString(cs.getColumnIndex(FIELD_ID));
			String title = cs.getString(cs.getColumnIndex(FIELD_TITLE));
			String poster = cs.getString(cs.getColumnIndex(FIELD_POSTER));
			String release = cs.getString(cs.getColumnIndex(FIELD_RELEASE));
			String genre = cs.getString(cs.getColumnIndex(FIELD_GENRE));
			String rating = cs.getString(cs.getColumnIndex(FIELD_RATING));
			String popularity = cs.getString(cs.getColumnIndex(FIELD_POPULARITY));
			String language = cs.getString(cs.getColumnIndex(FIELD_LANGUAGE));
			String overview = cs.getString(cs.getColumnIndex(FIELD_OVERVIEW));
			int favourite = cs.getInt(cs.getColumnIndex(FIELD_OVERVIEW));
			ModelDataAplikasi data = new ModelDataAplikasi();
			data.setId(id).
			setTITLE(title).
			setPOSTER(poster).
			setRELEASE(release).
			setGENRE(genre).
			setRATING(rating)
			.setPOPULARITY(popularity).
			setLANGUAGE(language).
			setOVERVIEW(overview).
			setFavourite(favourite);
			
			result.add(data);
		}while(cs.moveToNext());
		db.close();
		}catch(Exception e){}
		return result;
	}
	
	//hapus
	public void deleteData(String id){
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_NAME,FIELD_ID+"=?",new String[]{id});
		db.close();
	}
	
	
}
