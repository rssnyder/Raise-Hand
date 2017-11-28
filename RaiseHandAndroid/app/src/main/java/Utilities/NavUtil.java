package Utilities;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.example.sae1.raisehand.R;

import Activities.ForgotPassword;
import Activities.LiveFeed;
import Activities.LoginActivity;
import Activities.MakeQuestion;
import Activities.ResetPassword;
import Teacher.TeacherClasses;
import Teacher.TeacherHomePage;
import Teacher.TeacherNotifications;
import Teacher.TeacherSettings;
import Teacher.TeacherStudents;

/**
 * This Class controls the navigation drawer.
 */
public class NavUtil {

    /**
     * Goes to the activity you select on the slide out menu.
     * @param nv The NavigationView
     * @param ActivityEnum The activity you're in
     * @param theContext Context of the activity
     * @param mDrawerLayout DrawerLayout
     */
    public static void setNavMenu(NavigationView nv, final ActivitiesNames ActivityEnum, final Context theContext, final DrawerLayout mDrawerLayout){

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.nav_home):
                        if(ActivityEnum == ActivityEnum.HOME){
                            mDrawerLayout.closeDrawers();
                            break;
                        }
                        else {
                            Intent teacherHome = new Intent(theContext, TeacherHomePage.class);
                            theContext.startActivity(teacherHome);
                            break;
                        }
                    case (R.id.nav_classes):
                        if(ActivityEnum == ActivityEnum.CLASSES){
                            mDrawerLayout.closeDrawers();
                            break;
                        }
                        else {
                            Intent teacherClasses = new Intent(theContext, TeacherClasses.class);
                            theContext.startActivity(teacherClasses);
                            break;
                        }
                    case (R.id.nav_notifications):
                        if(ActivityEnum == ActivityEnum.NOTIFICATIONS){
                            mDrawerLayout.closeDrawers();
                            break;
                        }
                        else {
                            Intent teacherNotifications =
                                    new Intent(theContext, TeacherNotifications.class);
                            theContext.startActivity(teacherNotifications);
                            break;
                        }
                    case (R.id.nav_students):
                        if(ActivityEnum == ActivityEnum.STUDENTS){
                            mDrawerLayout.closeDrawers();
                            break;
                        }
                        else {
                            Intent teacherStudents = new Intent(theContext, TeacherStudents.class);
                            theContext.startActivity(teacherStudents);
                            break;
                        }
                    case (R.id.nav_settings):
                        if(ActivityEnum == ActivityEnum.SETTINGS){
                            mDrawerLayout.closeDrawers();
                            break;
                        }
                        else {
                            Intent resetPassword = new Intent(theContext, ResetPassword.class);
                            theContext.startActivity(resetPassword);
                            break;
                        }
                    case (R.id.nav_live):
                        if(ActivityEnum == ActivityEnum.LIVE_SESSION){
                            mDrawerLayout.closeDrawers();
                            break;
                        }
                        else{
                            Intent liveSession = new Intent(theContext, LiveFeed.class);
                            theContext.startActivity(liveSession);
                            break;
                        }
                    case (R.id.nav_logout):
                        Intent loginPage = new Intent(theContext, LoginActivity.class);
                        theContext.startActivity(loginPage);
                        break;
                }
                return true;
            }
        });
    }
}
