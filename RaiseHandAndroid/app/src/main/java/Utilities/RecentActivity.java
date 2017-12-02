package Utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

import Activities.VolleyMainActivityHandler;

/**
 * This is used to get recent activity to make notifications
 * @author sae1
 */

public class RecentActivity extends Activity {
    private static String TAG= RecentActivity.class.getSimpleName();
    private static String tag_string_req= "string_req";
    private ArrayList<Question> recentQuestions;
    private ProgressDialog pDialog;
    public RecentActivity(){

    }

}
