package jd.slalom.jdmasterdetail1;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

class Movie implements Parcelable{
//static final Logger mLog = org.slf4j.LoggerFactory.getLogger(Movie.class);

static private int idcounter = 0;

public String id, image_url, movie_name, description;
public double rating;
//static public String classname;
/*
private String id, movie_name, image_url, description;
private double rating;
*/

/*
public Movie(){
	id = Integer.toString(idcounter++);
	this.movie_name = "JD test movie name";
	this.image_url = "http://resizing.flixster.com/s8kIQtOhr36lGPkcUGCVeqVWw9Y=/180x270/dkpu1ddg7pbsk.cloudfront.net/movie/11/19/01/11190143_ori.jpg";
	this.rating = 10;
	this.description = "JD test descr";
}
*/

public Movie(){
	id = Integer.toString(idcounter++);
	//classname = getClass().getSimpleName();
}//cstr

public Movie(String movie, String imageUrl, double rating, String desc){
	this();
	this.movie_name = movie;
	this.image_url = imageUrl;
	this.rating = rating;
	this.description = desc;
}//cstr

/*
public Movie(JSONObject JSON) throws JSONException {
	this(   JSON.getString("movie_name"),
			JSON.getString("image_url"),
			JSON.getDouble("rating"),
			JSON.getString("description")
	);
}
*/

/*
public String getId() { return id; }
public String getMovie_name(){ return movie_name;}
public String getImage_url(){ return image_url; }
public double getRating(){ return rating; }
public String getDescription() { return description; }
*/

@Override public String toString(){ return toJson(); }

public String toJson() { return new Gson().toJson(this); }

//public static Movie fromJson(String json) { return new Gson().fromJson(json, Movie.class);}
public static Movie fromJson(JSONObject json) { return new Gson().fromJson(json.toString(), Movie.class);}

/*// http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
public Bundle toBundle() {
	Bundle b = new Bundle();
	//b.putParcelable();
	b.putString(classname, this.toJson());
	return b;
}*/

//public static Movie fromBundle(Bundle b){ return Movie.fromJson(b.get() .getString(classname)); }
//public static Movie fromBundle(Bundle b){ return Movie.fromJson((JSONObject)(b.get(classname) )); }

//https://dzone.com/articles/using-android-parcel

/** Used to give additional hints on how to process the received parcel.*/
// ignore for now
@Override public int describeContents(){ return 0; }

@Override public void writeToParcel(Parcel pc, int flags){
	pc.writeString(id);
	pc.writeString(movie_name);
	pc.writeString(image_url);
	pc.writeString(description);
	pc.writeDouble(rating);
}//writeToParcel

/** Static field used to regenerate object, individually or as arrays */
public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
	public Movie createFromParcel(Parcel pc) { return new Movie(pc); }

	public Movie[] newArray(int size){ return new Movie[size]; }
};//CREATOR

/**Ctor from Parcel, reads back fields IN THE ORDER they were written */
public Movie(Parcel pc){
	id         = pc.readString();
	movie_name = pc.readString();
	image_url  = pc.readString();
	description= pc.readString();
	rating     = pc.readDouble();
}

}//clase Movie

