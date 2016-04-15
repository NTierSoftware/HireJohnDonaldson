package jd.slalom.jdmasterdetail1;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class AppController extends Application{

private RequestQueue mRequestQueue;
private ImageLoader mImageLoader;

private static AppController mInstance;

@Override public void onCreate(){
	super.onCreate();
	mInstance = this;
}

public static synchronized AppController getInstance(){ return mInstance; }

public RequestQueue getRequestQueue(){
	if (mRequestQueue == null) { mRequestQueue = newRequestQueue(getApplicationContext()); }
return mRequestQueue;
}

public ImageLoader getImageLoader(){
	getRequestQueue();
	if (mImageLoader == null) { mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache()); }
return this.mImageLoader;
}


public static final String TAG = AppController.class.getSimpleName();

public <T> void addToRequestQueue(com.android.volley.Request<T> req, String tag){
	// set the default tag if tag is empty
	req.setTag(android.text.TextUtils.isEmpty(tag) ? TAG : tag);
	getRequestQueue().add(req);
}

public <T> void addToRequestQueue(com.android.volley.Request<T> req){
	req.setTag(TAG);
	getRequestQueue().add(req);
}

public void cancelPendingRequests(Object tag){
	if (mRequestQueue != null) { mRequestQueue.cancelAll(tag); }
}
}//AppController

