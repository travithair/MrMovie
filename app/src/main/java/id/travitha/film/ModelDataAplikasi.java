package id.travitha.film;

import android.os.*;

public class ModelDataAplikasi implements Parcelable
{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public ModelDataAplikasi createFromParcel(Parcel parcel)
        {
            // TODO: Implement this method
            return new ModelDataAplikasi(parcel);
        }

        @Override
        public ModelDataAplikasi[] newArray(int p1)
        {
            // TODO: Implement this method
            return new ModelDataAplikasi[p1];
        }


    };
    @Override
    public int describeContents()
    {
        // TODO: Implement this method
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int p2)
    {
        // menulis parcel
		parcel.writeString(this.ID);
		
        parcel.writeString(this.TITLE);

        parcel.writeString(this.POSTER);

        parcel.writeString(this.GENRE);

        parcel.writeString(this.RATING);

        parcel.writeString(this.POPULARITY);

        parcel.writeString(this.LANGUAGE);

        parcel.writeString(this.RELEASE);

        parcel.writeString(this.OVERVIEW);
		
		parcel.writeInt(this.favourite);
    }

	public String ID;
    public String TITLE;
    public String POSTER;
    public String RELEASE;
    public String GENRE;
    public String RATING;
    public String POPULARITY;
    public String LANGUAGE;
    public String OVERVIEW;
	public int favourite;

    //konstruktor parcel
    public ModelDataAplikasi(Parcel parcel){
		this.ID = parcel.readString();
        this.TITLE = parcel.readString();
        this.POSTER = parcel.readString();
        this.GENRE = parcel.readString();
        this.RATING  = parcel.readString();
        this.POPULARITY = parcel.readString();
        this.LANGUAGE = parcel.readString();
        this.RELEASE = parcel.readString();
        this.OVERVIEW = parcel.readString();
		this.favourite = parcel.readInt();

    }

    //konstruktor String
    public ModelDataAplikasi(String id,String TITLE,String POSTER,String RELEASE,String GENRE,String RATING,String POPULARITY,String LANGUAGE,String OVERVIEW,int favourite){
        this.ID = id;
		this.TITLE = TITLE;
        this.POSTER = POSTER;
        this.RELEASE = RELEASE;
        this.GENRE = GENRE;
        this.RATING = RATING;
        this.POPULARITY = POPULARITY;
        this.LANGUAGE = LANGUAGE;
        this.OVERVIEW = OVERVIEW;
		this.favourite = favourite;
    }

    public ModelDataAplikasi(){}



	public ModelDataAplikasi setId(String id){
		this.ID = id;
		return this;
	}
    public ModelDataAplikasi setTITLE(String title){
        this.TITLE = title;
        return this;
    }

    public ModelDataAplikasi setPOSTER(String poster){
        this.POSTER = poster;

        return this;
    }

    public ModelDataAplikasi setRELEASE(String release){
        this.RELEASE = release;

        return this;
    }

    public ModelDataAplikasi setGENRE(String genre){
        this.GENRE = genre;

        return this;
    }

    public ModelDataAplikasi setRATING(String rating){
        this.RATING = rating;


        return this;
    }

    public ModelDataAplikasi setLANGUAGE(String language){
        this.LANGUAGE = language;


        return this;
    }

    public ModelDataAplikasi setOVERVIEW(String overview){
        this.OVERVIEW = overview;

        return this;
    }

    public ModelDataAplikasi setPOPULARITY(String popularity){
        this.POPULARITY = popularity;

        return this;
    }

	public ModelDataAplikasi setFavourite(int favourite){
		this.favourite = favourite;
		return this;
	}
}
