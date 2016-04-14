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
public static final String ARG_ITEM_ID = "item_id";

//The movie content this fragment is presenting.
//private DummyContent.DummyItem mItem;
private Movie mItem;

//Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon screen orientation changes).
public movieDetailFragment(){}

@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	if (getArguments().containsKey(ARG_ITEM_ID)) {
		// Load the dummy content specified by the fragment
		// arguments. In a real-world scenario, use a Loader
		// to load content from a content provider.
//		mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
		mItem = new Movie();

		Activity activity = this.getActivity();
		CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
		if (appBarLayout != null) { appBarLayout.setTitle(mItem.movie_name); }
	}
}

@Override
public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
	View rootView = inflater.inflate(R.layout.movie_detail, container, false);

	// Show the movie content as text in a TextView.
	if (mItem != null){ ((TextView) rootView.findViewById(R.id.movie_detail)).setText(mItem.getDescription());
	}

	return rootView;
}
}
