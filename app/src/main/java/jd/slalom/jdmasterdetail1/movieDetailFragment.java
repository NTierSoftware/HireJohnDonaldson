package jd.slalom.jdmasterdetail1;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import jd.slalom.jdmasterdetail1.dummy.DummyContent;

/**
 * A fragment representing a single movie detail screen.
 * This fragment is either contained in a {@link movieListActivity}
 * in two-pane mode (on tablets) or a {@link movieDetailActivity}
 * on handsets.
 */
public class movieDetailFragment extends Fragment {
/**
 * The fragment argument representing the item ID that this fragment
 * represents.
 */
//public static final String ARG_ITEM_ID = "item_id";

//The movie content this fragment is presenting.
private Movie mItem;

//Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon screen orientation changes).
public movieDetailFragment(){}

@Override
public void onCreate(Bundle savedInstanceState){
super.onCreate(savedInstanceState);

	Bundle bundle = getActivity().getIntent().getExtras();
	if (bundle == null ) bundle = getArguments();

	mItem = (Movie)bundle.getParcelable(Movie.class.getSimpleName());

	CollapsingToolbarLayout appBarLayout =
			(CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);

	if (appBarLayout != null) { appBarLayout.setTitle(mItem.movie_name); }
}//onCreate

//@Override
public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
	View rootView = inflater.inflate(R.layout.movie_detail, container, false);

	// Show the movie content as text in a TextView.
	if (mItem != null){ ((TextView) rootView.findViewById(R.id.movie_detail)).setText(mItem.description);
	}

	return rootView;
}//onCreateView

}//class movieDetailFragment
