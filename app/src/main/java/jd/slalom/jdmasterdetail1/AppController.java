package jd.slalom.jdmasterdetail1;

import android.app.*;
import android.content.*;
import android.net.*;
import android.net.wifi.*;

import com.android.volley.*;
import com.android.volley.toolbox.*;


import static android.widget.Toast.*;
import static com.android.volley.toolbox.Volley.*;


public class AppController extends Application{
static public final String TAG = AppController.class.getSimpleName();
static private final org.slf4j.Logger mLog = org.slf4j.LoggerFactory.getLogger(
		AppController.class );
static private AppController mInstance;
private RequestQueue mRequestQueue;
private ImageLoader mImageLoader;

public static synchronized AppController getInstance(){ return mInstance; }

@Override public void onCreate(){
	super.onCreate();
	mInstance = this;
}

public RequestQueue getRequestQueue(){
	if ( mRequestQueue == null ){ mRequestQueue = newRequestQueue( getApplicationContext() ); }
	return mRequestQueue;
}

public ImageLoader getImageLoader(){
	getRequestQueue();
	if ( mImageLoader == null ){
		mImageLoader = new ImageLoader( mRequestQueue, new LruBitmapCache() );
	}
	return this.mImageLoader;
}

public < T > void addToRequestQueue( com.android.volley.Request< T > req, String tag ){
	// set the default tag if tag is empty
	req.setTag( android.text.TextUtils.isEmpty( tag ) ? TAG : tag );
	getRequestQueue().add( req );
}

public < T > void addToRequestQueue( com.android.volley.Request< T > req ){
	req.setTag( TAG );
	getRequestQueue().add( req );
}

public void cancelPendingRequests( Object tag ){
	if ( mRequestQueue != null ){ mRequestQueue.cancelAll( tag ); }
}


//public static boolean isAndroidOnline(){
public boolean isAndroidOnline(){
/* http://androidresearch.wordpress.com/2013/05/10/dealing-with-asynctask-and-screen-orientation/
 * http://developer.android.com/training/basics/network-ops/managing.html
 * http://stackoverflow.com/questions/7739624/android-connectivity-manager */
	ConnectivityManager connMgr = (ConnectivityManager) getSystemService(
			Context.CONNECTIVITY_SERVICE );

	android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	if ( networkInfo == null ){
		mLog.warn( "NO default network!" );
		return false;
	}
	mLog.trace( "network detected" );
	final boolean isConnected = networkInfo.isConnected();
	mLog.trace( "network " + ( isConnected ? "" : "  NOT!!  " ) + "connected" );
	return isConnected;
}// isAndroidOnline()


public void setMobileData( final boolean enabled ){
//http://stackoverflow.com/questions/9871762/android-turning-on-wifi-programmatically

	WifiManager wManager = (WifiManager) getSystemService( Context.WIFI_SERVICE );
	boolean wifi = wManager.setWifiEnabled( enabled );

	if ( enabled && wifi ){
		android.widget.Toast.makeText( this,
		                               "Wifi enabled",
		                               LENGTH_SHORT ).show();
	}

/*
	try{
		//http://stackoverflow.com/questions/11555366/enable-disable-data-connection-in-android-programmatically
		final ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		final Class<?> conmanClass = Class.forName(connMgr.getClass().getName());
		final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
		connectivityManagerField.setAccessible(true);
		final Object connectivityManager = connectivityManagerField.get(connMgr);
		final Class<?> connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
		final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
		setMobileDataEnabledMethod.setAccessible(true);

		setMobileDataEnabledMethod.invoke(connectivityManager, Boolean.valueOf(enabled));
	}
	catch( Exception e ){ mLog.error( "ERROR", e ); }
*/

}//setMobileDataEnabled


}//AppController

