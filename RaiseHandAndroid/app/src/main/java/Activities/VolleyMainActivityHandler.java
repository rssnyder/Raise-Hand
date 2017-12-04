package Activities;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
/**
 * This class is what enables volley to work. This code
 * was given to us by our professor.
 *
 */

public class VolleyMainActivityHandler extends Application {
    public static final String TAG= VolleyMainActivityHandler.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static VolleyMainActivityHandler mInstance;

    /**
     *
     * This method starts the volley request
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     *
     * @return current activity handler
     */
    public static synchronized VolleyMainActivityHandler getInstance() {
        return mInstance;
    }

    /**
     *
     * @return current request queue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     *
     * @param req url for processing the request
     * @param tag string to describe the request
     * @param <T> type of request
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     *
     * @param req url for processing the request
     * @param <T> type of request
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * cancel the current request
     * @param tag the previous tag for the request
     */
    public void cancelPendingRequests(Object tag){
        if (mRequestQueue!=null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}