package jd.slalom.jdmasterdetail1;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

class Movie {
//static final Logger mLog = org.slf4j.LoggerFactory.getLogger(Movie.class);

private String movie_name, image_url, description;
private double rating;

public Movie(){
	this.movie_name = "JD test movie name";
	this.image_url = "http://resizing.flixster.com/s8kIQtOhr36lGPkcUGCVeqVWw9Y=/180x270/dkpu1ddg7pbsk.cloudfront.net/movie/11/19/01/11190143_ori.jpg";
	this.rating = 10;
	this.description = "JD test descr";

}

public Movie(String movie, String imageUrl, double rating, String desc){
	this.movie_name = movie;
	this.image_url = imageUrl;
	this.rating = rating;
	this.description = desc;
}

public Movie(JSONObject JSON) throws JSONException {
	this(   JSON.getString("movie_name"),
			JSON.getString("image_url"),
			JSON.getDouble("rating"),
			JSON.getString("description")
	);
}

public String getMovie_name(){ return movie_name;}

public String getImage_url(){ return image_url; }

public double getRating(){ return rating; }
public String getDescription() { return description; }

@Override
public String toString(){ return new com.google.gson.Gson().toJson(this); }
}//clase Movie

