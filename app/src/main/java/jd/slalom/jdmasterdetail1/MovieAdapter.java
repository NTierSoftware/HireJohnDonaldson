package jd.slalom.jdmasterdetail1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.slf4j.Logger;

import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;

//class MovieAdapter extends BaseAdapter {
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
static private final Logger mLog = getLogger(MovieAdapter.class);

private movieListActivity mActivity;
//private LayoutInflater mInflater;
private List<Movie> mMovies;
private ImageLoader mImageLoader = AppController.getInstance().getImageLoader();

public MovieAdapter(movieListActivity mActivity, List<Movie> mMovies){
	this.mActivity = mActivity;
	this.mMovies = mMovies;
	//mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}

public int getCount(){ return mMovies.size(); }

public Object getItem(int position){ return mMovies.get( position ); }

@Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
	View view = LayoutInflater.from(mActivity)
			            .inflate(R.layout.movie_list_content, parent, false);
	return new ViewHolder(view);
}//onCreateViewHolder


@Override public void onBindViewHolder(final ViewHolder holder, int position){
	holder.bindMovie(position);
	//holder.mItem = mMovies.get(position);
	mLog.debug("onBindViewHolder:\t" + holder.mItem.toString());
	//holder.mIdView.setText(mMovies.get(position).id);
	holder.mMovienameView.setText(mMovies.get(position).movie_name);

	holder.mView.setOnClickListener(new View.OnClickListener(){
		@Override public void onClick(View v){
			if (mActivity.mTwoPane) {
				Bundle bundle = new Bundle();
				bundle.putParcelable( Movie.class.getSimpleName(), holder.mItem );

				movieDetailFragment fragment = new movieDetailFragment();
				fragment.setArguments( bundle );
				mActivity.getSupportFragmentManager().beginTransaction()
						.replace(R.id.movie_detail_container, fragment)
						.commit();
			}//if
			else {
				Context context = v.getContext();
				Intent intent = new Intent(context, movieDetailActivity.class)
						                .putExtra( Movie.class.getSimpleName(), holder.mItem );
				//intent.putExtra(movieDetailFragment.ARG_ITEM_ID, holder.mItem.id);

				context.startActivity(intent);
			}//else
		}//onClick
	});
}//onBindViewHolder


@Override public long getItemId(int position){ return position; }
@Override public int getItemCount() { return mMovies.size(); }

public class ViewHolder extends RecyclerView.ViewHolder {
//	private final Logger mLog = getLogger(ViewHolder.class);

	public final View mView;
	//public final TextView mIdView
	public final TextView mMovienameView, mRatingView;
	public final NetworkImageView mImageurl;
	//private int curPosition = this.getLayoutPosition();
	public Movie mItem;

	public ViewHolder(View view) {
		super(view);
		mView = view;
		//mIdView = (TextView) view.findViewById(R.id.id);
		mImageurl = (NetworkImageView) view.findViewById(R.id.image_url);

		//if (curPosition <= RecyclerView.NO_POSITION) curPosition = 0;
		//mItem = mMovies.get( curPosition );

		mMovienameView = (TextView) view.findViewById(R.id.movie_name);
		mRatingView  = (TextView) view.findViewById(R.id.rating);
	}

	//https://www.bignerdranch.com/blog/recyclerview-part-1-fundamentals-for-listview-experts///
	public void bindMovie(final int position){
//		public void bindMovie(Movie movie){
		mItem =  mMovies.get(position);
		mImageurl.setImageUrl(mItem.image_url, mImageLoader);
		mLog.debug("ViewHolder.bindMovie:\t" + mItem.toString());
	}

	@Override public String toString(){ return "ViewHolder:\t" + mItem.toString(); }
}//class ViewHolder

}//MovieAdapter
