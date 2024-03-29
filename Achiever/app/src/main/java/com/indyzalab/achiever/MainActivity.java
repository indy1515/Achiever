package com.indyzalab.achiever;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.parse.ParseUser;

import java.lang.reflect.Field;


public class MainActivity extends ActionBarActivity implements LoginFragment.OnFragmentInteractionListener,
        QuestLogFragment.OnFragmentInteractionListener{




    public static final int LOGIN_FRAGMENT = 0;
    public static final int QUEST_LOG_FRAGMENT = 1;
    public static final int MAP_FRAGMENT = 2;
    public static final int PROFILE_FRAGMENT = 3;
    public static final int QUEST_DETAIL_FRAGMENT = 4;
    public static final int CONFIRM_FRAGMENT = 5;
    private final int FRAGMENT_SIZE = QUEST_LOG_FRAGMENT + 1;
    public static int current_fragment = 1;
    private Fragment[] fragments;
    private static final String FRAGMENT_TAG = "Fragment";
    public static FragmentManager fragmentManager;
    public Activity mContext;

    private ParseUser currentUser;

    private Toolbar toolbar;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        addButton = (FloatingActionButton) findViewById(R.id.float_addBtn);
        initializingFragment();

        mContext = this;

        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setScrimColor(getResources().getColor(R.color.drawer_overlay));

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setNavigationIcon(R.drawable.ic_action_navigation_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);




    }

    public FloatingActionButton getFloatingActionButton() {


        return addButton;

    }
    public void reset(){
        CoordinatorLayout coordinator = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.app_bar);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        int[] consumed = new int[2];
        behavior.onNestedFling(coordinator, appbar, null, 0, -1000, true);
    }
    private TextView getActionBarTextView() {
        TextView titleTextView = null;

        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return titleTextView;
    }
    public void initializingFragment(){
        fragments = new Fragment[FRAGMENT_SIZE];
        fragments [LOGIN_FRAGMENT] = new LoginFragment();
        fragments [QUEST_LOG_FRAGMENT] = new QuestLogFragment();
//        fragments [MAP_FRAGMENT] = new MapPageFragment();
//        fragments[PROFILE_FRAGMENT] = new ProfileFragment();
//        fragments [QUEST_DETAIL_FRAGMENT] = new QuestDetailFragment();
//        fragments[CONFIRM_FRAGMENT] = new ConfirmFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for(int i = 0; i < fragments.length; i++) {

            transaction.hide(fragments[i]);

        } transaction.commit();
    }


    public void showFragment(int fragmentIndex, boolean addToBackStack) {

        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        current_fragment = fragmentIndex;
        for (int i = 0; i < fragments.length; i++) {

            Log.i("MainActivity", "fragment index: " + fragmentIndex + " i: " + i);
            if (i == fragmentIndex) {
                if(fragmentIndex == QUEST_DETAIL_FRAGMENT || fragmentIndex == LOGIN_FRAGMENT){
                    // Hide Tab
//                    hideTab();
                }else{
                    // Show Tab
//                    showTab();
                }
                transaction.replace(R.id.container, fragments[i],FRAGMENT_TAG+i);
                transaction.show(fragments[i]);

//                BadgeView badge = new BadgeView(this, actionBar.getTabAt(1));
//                badge.setText("1");
//                badge.show();

            } else {

                transaction.hide(fragments[i]);

            }

        }

        if (addToBackStack) {

            transaction.addToBackStack(null);

        }
        try{
            transaction.commit();
        }catch(Exception e){

        }

        //Setup Action Bar
//        Log.i("Create actionbar Tab", "Tab Created "+"Index: "+MAIN);

//        if(fragmentIndex == MAIN) {
//
////        	createTab();
//
//        }

//        updateTabIcon(fragmentIndex);

    }
    @Override
    protected void onStart() {
        super.onStart();


    }


    @Override
    protected void onResumeFragments() {

        super.onResumeFragments();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // send to login fragment
        } else {
            // Already login
        }
        Log.i("onResumeFragment", "Resume Fragment");
        if (currentUser != null) {
            // if the session is already open, try to show the selection fragment
            Log.i("MainActivity","Resume "+current_fragment);
            Log.i("MainActivity","AccessToken: "+accessToken);
            if(current_fragment == LOGIN_FRAGMENT)
                current_fragment = QUEST_LOG_FRAGMENT;

            showFragment(current_fragment, false);

//            userSkippedLogin = false;
//            showTab();
//        } else if (userSkippedLogin) {
//            Log.i("MainActivity","Skip Login");
//
//            showFragment(QUEST_LOG_FRAGMENT, false);
//            showTab();
        } else {
            // otherwise present the splash screen and ask the user to login, unless the user explicitly skipped.
            Log.i("User Skip","Show Login");
//        	showFragment(COURSES, false);
            showFragment(LOGIN_FRAGMENT, false);
//            hideTab();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_map){
            reset();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }



}
