package in.birdcommunication.Smspure.Dashboard;

import android.os.Bundle;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import in.birdcommunication.auth.RouteActivity;
import in.birdcommunication.Smspure.Dashboard.Fragment.AccountFragment;
import in.birdcommunication.Smspure.Dashboard.Fragment.DashboardFragment;
import in.birdcommunication.Smspure.R;
import me.ibrahimsn.lib.SmoothBottomBar;

public class DashboardActivity extends RouteActivity {
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isRouteNeed()){
            setContentView(R.layout.activity_dashboard);
            makeFullScreenActivity();
//            bottomMenu = findViewById(R.id.bottom);
//            bottomMenu.add(new MeowBottomNavigation.Model(1, R.drawable.ic_launcher_foreground));
//            bottomMenu.add(new MeowBottomNavigation.Model(2, R.drawable.ic_launcher_foreground));
//            bottomMenu.show(1,true);
//            bottomMenu.setSelectedIconColor(getResources().getColor(R.color.colorPrimary));
            initViews();
        }

    }

    @Override
    public void initViews() {
        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),1);
        adapter.addFragment(new DashboardFragment(), "Dashboard");
        adapter.addFragment(new AccountFragment(), "Account");
        viewPager.setAdapter(adapter);
        SmoothBottomBar bottomBar = findViewById(R.id.bottom);
        bottomBar.setOnItemSelectedListener(i -> viewPager.setCurrentItem(i,true));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.setActiveItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}

class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mList = new ArrayList<>();
    private final List<String> mTitleList = new ArrayList<>();

    ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    public void addFragment(Fragment fragment, String title) {
        mList.add(fragment);
        mTitleList.add(title);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}

