package in.birdcommunication.birdboilerplate.Dashboard.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import in.birdcommunication.auth.Manager.AuthManager;
import in.birdcommunication.auth.RegisterActivity;
import in.birdcommunication.birdboilerplate.Dashboard.DashboardActivity;
import in.birdcommunication.birdboilerplate.Model.FeatureItem;
import in.birdcommunication.birdboilerplate.R;
import in.birdcommunication.core.Utils.EmptyViewHelper;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private FlexibleAdapter<FeatureItem> mAdapter;
    private List<FeatureItem> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_dashboard, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        TextView wishTv = v.findViewById(R.id.wish);
        TextView nameTV = v.findViewById(R.id.name);
        nameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = v.findViewById(R.id.recyclerView);

        Log.i("Ddd", "initView: "+getWish());
        wishTv.setText(getWish());
        nameTV.setText(AuthManager.getInstance().getName());
        initRecyclerView();
    }

    private String getWish(){
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if(timeOfDay < 12){
            return "Good Morning";
        }else if(timeOfDay < 16){
            return "Good Afternoon";
        }else if(timeOfDay < 21){
            return "Good Evening";
        }else {
            return "Good Night";
        }
    }
    private void initRecyclerView(){
        list = getItems();
        if(mAdapter == null){
            mAdapter = new FlexibleAdapter<>(list);
        }else{
            mAdapter.addItems(0,list);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(mAdapter);
//        EmptyViewHelper.create(mAdapter,
//                getView().findViewById(R.id.empty_view),
//                getView().findViewById(R.id.filter_view));
        recyclerView.setNestedScrollingEnabled(false);

        mAdapter.addListener(this);

    }

    private List<FeatureItem> getItems() {
        List<FeatureItem> list = new ArrayList<>();
        list.add(new FeatureItem("Manage Students",
                getContext().getResources().getDrawable(R.drawable.ic_account),"#fb8723",
                "#ffc336"));
        list.add(new FeatureItem("Manage ffdfd"
                ,getContext().getResources().getDrawable(R.drawable.ic_menu),"#fa565e",
                "#ff8f48"));

        list.add(new FeatureItem("Manage ffdfd"
                ,getContext().getResources().getDrawable(R.drawable.ic_user),"#197dd0",
                "#17b0df"));
        list.add(new FeatureItem("Manage Students",
                getContext().getResources().getDrawable(R.drawable.ic_account),"#fb8723",
                "#ffc336"));
        list.add(new FeatureItem("Manage ffdfd"
                ,getContext().getResources().getDrawable(R.drawable.ic_menu),"#fa565e",
                "#ff8f48"));



        //start colorA400 end color 600 from https://www.materialpalette.com/colors
        return list;
    }
}