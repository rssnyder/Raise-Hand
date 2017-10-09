package app;
import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Scanner;

import utils.Question;
import utils.Reply;
import utils.Topics;
import utils.URLS;

/**
 * Created by sae1 on 9/17/17.
 * Using source code from tutorialsbuzz.com
 */

public class MainActivity extends Application {
    public static final String TAG= MainActivity.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static MainActivity mInstance;
    ArrayList<Topics> t = new ArrayList<Topics>();


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public static synchronized MainActivity getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag){
        if (mRequestQueue!=null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}