package in.birdcommunication.birdboilerplate.Dashboard.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import in.birdcommunication.auth.Manager.AuthManager;
import in.birdcommunication.auth.RegisterActivity;
import in.birdcommunication.birdboilerplate.Feature.ActivityDevInfo;
import in.birdcommunication.birdboilerplate.Model.FeatureItem;
import in.birdcommunication.birdboilerplate.R;
import in.birdcommunication.core.Utils.Constants;

public class AccountFragment extends Fragment implements FlexibleAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private FlexibleAdapter<FeatureItem> mAdapter;
    private List<FeatureItem> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_account, container, false);
        initView(v);
        return v;
    }


    private void initView(View v) {
        CircleImageView circleImageView = v.findViewById(R.id.avatar);
        circleImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_account));
        TextView nameTV = v.findViewById(R.id.name);
        nameTV.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), RegisterActivity.class);
            startActivity(intent);
        });
        recyclerView = v.findViewById(R.id.recyclerView);
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

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
//        EmptyViewHelper.create(mAdapter,
//                getView().findViewById(R.id.empty_view),
//                getView().findViewById(R.id.filter_view));
        recyclerView.setNestedScrollingEnabled(false);

        mAdapter.addListener(this);

    }

    private List<FeatureItem> getItems() {
        List<FeatureItem> list = new ArrayList<>();
        list.add(new FeatureItem(
                Constants.CONTACT_DEV,
                "Contact us,Call,Email or visit our website",
                getContext().getResources().getDrawable(R.drawable.ic_call),
                true));

        list.add(new FeatureItem(Constants.INFO,
                "Information about "+getContext().getResources().getString(R.string.app_name),
                getContext().getResources().getDrawable(R.drawable.ic_info),
                true));

        list.add(new FeatureItem(Constants.CURRENT_VERSION,
                "V1",
                getContext().getResources().getDrawable(R.drawable.ic_dot),
                true));

        //start colorA400 end color 600 from https://www.materialpalette.com/colors
        return list;
    }

    @Override
    public boolean onItemClick(View view, int position) {
        FeatureItem featureItem = mAdapter.getItem(position);
        if(featureItem !=null){
            Intent intent = null;
            switch (featureItem.getTitle()){
                case Constants.INFO:
                    break;

                case Constants.CONTACT_DEV:
                    intent = new Intent(getContext(), ActivityDevInfo.class);
                    break;

                case Constants.CURRENT_VERSION:
                    break;

            }
            if(intent !=null){
                Objects.requireNonNull(getContext()).startActivity(intent);
            }
        }
        return false;
    }
}