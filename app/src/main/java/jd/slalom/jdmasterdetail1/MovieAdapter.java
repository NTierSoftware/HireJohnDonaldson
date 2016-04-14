package jd.slalom.jdmasterdetail1;

import android.app.Activity;
import android.content.Context;
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

//class MovieAdapter extends BaseAdapter {
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
static private final Logger mLog = org.slf4j.LoggerFactory.getLogger(MovieAdapter.class);

private movieListActivity mActivity;
private LayoutInflater mInflater;
private List<Movie> mMovies;
private ImageLoader mImageLoader = AppController.getInstance().getImageLoader();

public MovieAdapter(movieListActivity mActivity, List<Movie> mMovies){
	this.mActivity = mActivity;
	this.mMovies = mMovies;
	mInflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}

//@Override
public int getCount(){ return mMovies.size(); }

//@Override
public Object getItem(int position){ return mMovies.get( position ); }

@Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
	View view = LayoutInflater.from(parent.getContext())
			            .inflate(R.layout.movie_list_content, parent, false);
	return new ViewHolder(view);
}//onCreateViewHolder


@Override public void onBindViewHolder(final ViewHolder holder, int position){
	holder.mItem = mMovies.get(position);
	holder.mIdView.setText(mMovies.get(position).id);
//		holder.mContentView.setText(mMovies.get(position).content);
	holder.mMovienameView.setText(mMovies.get(position).movie_name);

	holder.mView.setOnClickListener(new View.OnClickListener(){
		@Override public void onClick(View v){
			if (mActivity.mTwoPane) {
				android.os.Bundle arguments = new android.os.Bundle();
				arguments.putString(movieDetailFragment.ARG_ITEM_ID, holder.mItem.id);
				movieDetailFragment fragment = new movieDetailFragment();
				fragment.setArguments(arguments);
				mActivity.getSupportFragmentManager().beginTransaction()
						.replace(R.id.movie_detail_container, fragment)
						.commit();
			}//if
			else {
				Context context = v.getContext();
				android.content.Intent intent = new android.content.Intent(context, movieDetailActivity.class);
				intent.putExtra(movieDetailFragment.ARG_ITEM_ID, holder.mItem.id);

				context.startActivity(intent);
			}//else
		}//onClick
	});
}//onBindViewHolder


@Override public long getItemId(int position){ return position; }
@Override public int getItemCount() { return mMovies.size(); }

//@Override
/*
public View getView(int position, View convertView, ViewGroup parent){
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
*/

public class ViewHolder extends RecyclerView.ViewHolder {
	public final View mView;
	public final TextView mIdView, mMovienameView;
	//		public DummyContent.DummyItem mItem;
	public Movie mItem;

	public ViewHolder(View view) {
		super(view);
		mView = view;
		mIdView = (TextView) view.findViewById(R.id.id);
//			mContentView = (TextView) view.findViewById(R.id.content);
		mMovienameView = (TextView) view.findViewById(R.id.movie_name);
	}

	//		@Override public String toString(){ return super.toString() + " '" + mContentView.getText() + "'"; }
	@Override public String toString(){ return super.toString() + " '" + mMovienameView.getText() + "'"; }
}//class ViewHolder

}//MovieAdapter
