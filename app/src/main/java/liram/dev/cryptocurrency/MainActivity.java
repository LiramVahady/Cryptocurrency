package liram.dev.cryptocurrency;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import liram.dev.cryptocurrency.database.DatabaseService;
import liram.dev.cryptocurrency.databinding.ActivityMainBinding;
import liram.dev.cryptocurrency.ui.IntroActivity;
import liram.dev.cryptocurrency.ui.favoriteTracker.FavoriteTrackerFragment;
import liram.dev.cryptocurrency.ui.marketcap.MarketCapFragment;
import liram.dev.cryptocurrency.ui.portfolio.PortfolioFragment;
import liram.dev.cryptocurrency.utility.UserPreferencesManager;


public class MainActivity extends AppCompatActivity {


    private  ActivityMainBinding activityMainBinding;
    //TODO: Back button not reload view - solve it!
    private final Fragment marketFragment = new MarketCapFragment();
    private final Fragment portfolioFragment = new PortfolioFragment();
    private final Fragment favoriteTrackerFragment = new FavoriteTrackerFragment();
    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment currentActive = marketFragment;
    private final String MARKET_TAG = "market";
    private final String PORTFOLIO_TAG = "portfolio";
    private final String FAVORITE_TAG = "favorite";

    private BottomNavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View root = activityMainBinding.getRoot();
        setContentView(root);
        runApp();
    }

    private void runApp(){
        configureViews();
    }


    private void configureViews() {
        setBottomNavigation();
        SharedPreferences preferences = getSharedPreferences(getString(R.string.USER_LOG_PREFERENCES_KEY), Context.MODE_PRIVATE);
        boolean log = preferences.getBoolean(getString(R.string.USER_LOG_PREFERENCES_KEY), false);
        System.out.println("Log " + log);
        if (!log){
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
            finish();
        }else {
            /*
                That's solution to common issue with BottomNavigation:
                If I use directly with replace.commit(), each time when user click on item in bottomNav
                the view will reloaded again, and this worse situation I want to avoid that problem.
                below in the onItemSelected method we just hide the previous fragment and show the current
                in this way it's work perfect the view doesn't reload over and over again!
                 fm.beginTransaction().add -> it's stack behave so i add it with TAG unique name
                 and hide or show depend on the current fragment.
             */
            fm.beginTransaction().add(R.id.frame, favoriteTrackerFragment, FAVORITE_TAG).commit();
            fm.beginTransaction().add(R.id.frame, portfolioFragment, PORTFOLIO_TAG).commit();
            fm.beginTransaction().add(R.id.frame, marketFragment, MARKET_TAG).commit();
            fm.beginTransaction().detach(portfolioFragment).commit();
            setUserId();
        }

    }

    private void setUserId(){
        final String USER_ID_KEY = getApplication().getString(R.string.USER_ID);
        String stringValue = UserPreferencesManager.getStringValue(this, USER_ID_KEY);
        DatabaseService.getInstance().updateCurrentUserId(stringValue);
    }

    private void setBottomNavigation() {
        navView = this.findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_market, R.id.navigation_portfolio, R.id.navigation_favorite_tracker)
                .build();
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId){
                    case R.id.navigation_market:
                        fm.beginTransaction().hide(currentActive).show(marketFragment).commit();
                        fm.beginTransaction().detach(portfolioFragment).commit();
                        currentActive = marketFragment;
                         return true;
                    case R.id.navigation_portfolio:
                        fm.beginTransaction().hide(currentActive).show(portfolioFragment).commit();
                        fm.beginTransaction().attach(portfolioFragment).commit();
                        currentActive = portfolioFragment;
                        return true;
                    case R.id.navigation_favorite_tracker:
                        fm.beginTransaction().hide(currentActive).show(favoriteTrackerFragment).commit();
                        currentActive = favoriteTrackerFragment;
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}