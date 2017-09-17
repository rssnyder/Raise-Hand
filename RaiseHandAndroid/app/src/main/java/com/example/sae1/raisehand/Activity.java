package com.example.sae1.raisehand;
import android.app.Application;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import android.content.Context;
/**
 * Created by sae1 on 9/17/17.
 * Using source code from tutorialsbuzz.com
 */

public class Activity extends Application {
    private RequestQueue mRequestQueue;
    private static Activity mInstance;
    private static Context mCtx;

    private Activity(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized Activity getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Activity(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
