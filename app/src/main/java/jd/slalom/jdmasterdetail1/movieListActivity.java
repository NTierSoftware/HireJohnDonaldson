package jd.slalom.jdmasterdetail1;

import android.app.Activity;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;


import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

//import jd.slalom.jdmasterdetail1.dummy.*;

// An activity representing a list of movies. This activity has different presentations for handset and tablet-size devices.
// On handsets, the activity presents a list of items, which when touched, lead to a {@link movieDetailActivity} representing
// item details. On tablets, the activity presents the list of items and item details side-by-side using two vertical panes.
public class movieListActivity extends AppCompatActivity{
static private final Logger mLog = LoggerFactory.getLogger(movieListActivity.class);
static private final LoggerContext mLoggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
static private final ContextInitializer mContextInitializer	= new ContextInitializer( mLoggerContext );

//Whether or not the activity is in two-pane mode, i.e. running on a tablet device.
public boolean mTwoPane;
private List<Movie> mMovieList = new ArrayList<>();
private MovieAdapter mMovieAdapter;
private Button btnDel, btnLoad;
private android.app.ProgressDialog progress;
private RecyclerView recyclerView;

@Override protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_movie_list);

	Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);
	toolbar.setTitle(getTitle());

	FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
	fab.setOnClickListener(new View.OnClickListener(){
		@Override public void onClick(View view){
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
		}//onClick
	});

	recyclerView = (RecyclerView)findViewById(R.id.movie_list);
//	assert recyclerView != null;

	mMovieAdapter = new MovieAdapter(this, mMovieList);
	recyclerView.setAdapter( mMovieAdapter );
	//setupRecyclerView((RecyclerView) recyclerView);

// The detail container view will be present only in the large-screen layouts (res/values-w900dp).
// If this view is present, then the activity should be in two-pane mode.
	mTwoPane = (findViewById(R.id.movie_detail_container) != null);

	btnLoad = (Button) findViewById(R.id.btnLoad);
	progress = new android.app.ProgressDialog(this);
	btnDel = (Button) findViewById(R.id.btnDel);
	btnDel.setOnLongClickListener(new android.view.View.OnLongClickListener() {
		@Override public boolean onLongClick(View v){
			if (!isEmpty()) {
				mMovieList.clear();
				mMovieAdapter.notifyDataSetChanged();
				isEmpty();
			}
			return true;
		}
	});

}//onCreate

private boolean isEmpty(){
	if (mMovieList.isEmpty()) {
		btnDel.setClickable(false);
		btnDel.setEnabled(false);

		makeText(this,
				getResources().getString(R.string.emptyList),
				LENGTH_SHORT).show();
		btnLoad.setClickable(true);
		btnLoad.setEnabled(true);
		return true;
	}
	btnDel.setEnabled(true);
	btnDel.setClickable(true);
	return false;
}

@Override protected void onRestart(){
	super.onRestart();
	// Reload Logback log: http://stackoverflow.com/questions/3803184/setting-logback-appender-path-programmatically/3810936#3810936
	mLoggerContext.reset();

	try{ mContextInitializer.autoConfig(); } //I prefer autoConfig() over JoranConfigurator.doConfigure() so I don't need to find the file myself.
	catch( ch.qos.logback.core.joran.spi.JoranException X ){ X.printStackTrace(); }
	//setMobileDataEnabled( true );
}//onRestart()

//@Override protected void onStart(){ super.onStart(); }//onStart

private void clearProgress() { if (progress != null) { progress.dismiss(); } }

/* @Override public boolean onCreateOptionsMenu(Menu menu){
	getMenuInflater().inflate(R.menu.main, menu);
return true;
} */

@Override protected void onStop(){
	super.onStop();
	mLog.trace("onStop():\t");
	clearProgress();
	progress = null;
	mLoggerContext.stop();//flush log
}// onStop()

@Override public void onDestroy(){
	super.onDestroy();
	mLog.trace("onDestroy():\t");
	clearProgress();
	mLoggerContext.stop();//flush log
	//mUtility.close();
}

static private final String HTTPURL = "http://private-05248-rottentomatoes.apiary-mock.com/";
public void btnLoadClicked(View view) {
final Activity toastActivity = this;
mLog.debug("btnLoadClicked");
progress.setMessage("Loading. . .");
progress.show();
final JSONObject GET = null;
JsonObjectRequest request = new JsonObjectRequest(HTTPURL, GET,
       new Response.Listener<JSONObject>() {
       //new Listener<JsonObject>() {
           public void onResponse(JSONObject response) {
          // public void onResponse(JsonObject response) {
               mLog.debug("onResponse:\t" + response.toString());
	           mMovieList.clear();
               int numMovies = 0;
               try {
	               //to view JSON use http://codebeautify.org/jsonviewer#
	               JSONArray moviesArr = response.getJSONArray("movies");
                   //"Log the JSON to the console."
                   mLog.trace("JSON:\t" + moviesArr.toString());

                   numMovies = moviesArr.length();
                   for (int i = 0; i < numMovies; i++) {
                       //JSONObject JSON = moviesArr.getJSONObject(i);

                       //mLog.debug("JSON:\t" + JSON.toString());
                       //Movie movie = new Movie(JSON);
	                   //new Movie(JSON)
	                   //JsonObject x =  moviesArr.getJSONObject(j);
	                   mMovieList.add( Movie.fromJson( moviesArr.getJSONObject(i) ) );
                   }//for
               }//try
               catch (JSONException X) { mLog.error("onResponse:\t" + X.getMessage()); }


	           mMovieAdapter.notifyDataSetChanged();
               if (!isEmpty()) {
                   btnLoad.setEnabled(false);
                   btnLoad.setClickable(false);
               }
               clearProgress();
               makeText(toastActivity,
                       Integer.toString(numMovies) + " movies loaded.",
                       LENGTH_SHORT).show();

           }//onResponse
       }//Listener

       ,new Response.ErrorListener() {
@Override public void onErrorResponse (com.android.volley.VolleyError X){
mLog.error("\nonErrorResponse\n");
X.printStackTrace();
mLog.error("\nonErrorResponse\n");
clearProgress();
}//onErrorResponse
}//ErrorListener
);//JsonObjectRequest

AppController.getInstance().addToRequestQueue(request);

}//btnLoadClicked

public void btnDelClicked(View view) {
	btnLoad.setClickable(true);
	btnLoad.setEnabled(true);

	if ( isEmpty() ) return;

	makeText(this,
			getResources().getString(R.string.deleteAll ),
			LENGTH_SHORT).show();

	mMovieList.remove(0);
	mMovieAdapter.notifyDataSetChanged();
	isEmpty();
}//btnDelClicked


}//class movieListActivity
