package jd.slalom.jdmasterdetail1;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.slf4j.Logger;

import java.util.List;

class MovieAdapter extends BaseAdapter {
static private final Logger mLog = org.slf4j.LoggerFactory.getLogger(MovieAdapter.class);

//private Activity mActivity;
private android.view.LayoutInflater mInflater;
private List<Movie> mMovies;
private ImageLoader mImageLoader = AppController.getInstance().getImageLoader();

public MovieAdapter(Activity mActivity, List<Movie> mMovies){

	this.mMovies = mMovies;
	mInflater = (android.view.LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}

@Override public int getCount(){ return mMovies.size(); }

@Override public Object getItem(int position){ return mMovies.get( position ); }
@Override public long getItemId(int position){ return position; }

@Override public View getView(int position, View convertView, ViewGroup parent){
	Movie m = mMovies.get(position);
	//mLog.debug("movie:\t" + m.toString());

	if (convertView == null) convertView = mInflater.inflate(R.layout.list_row, null);


	((TextView) convertView.findViewById(R.id.movie_name))
			.setText(m.getMovie_name());
	((TextView) convertView.findViewById(R.id.rating))
			.setText("Rating: " + String.valueOf(m.getRating()));


	((NetworkImageView) convertView.findViewById(R.id.image_url))
			.setImageUrl(m.getImage_url(), mImageLoader);



return convertView;
}//getView

}//MovieAdapter
