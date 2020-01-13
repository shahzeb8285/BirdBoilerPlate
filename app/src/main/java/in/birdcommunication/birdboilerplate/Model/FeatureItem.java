package in.birdcommunication.birdboilerplate.Model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;
import in.birdcommunication.birdboilerplate.Application;
import in.birdcommunication.birdboilerplate.R;
import in.birdcommunication.core.Utils.UtilsManager;


public class FeatureItem extends AbstractFlexibleItem<FeatureItem.ViewHolder> implements IFilterable<String> {
    private  String title;
    private Drawable icon;
    private String startColor;
    private String endColor;
    private boolean isLinear;
    private String subTitle;
    public FeatureItem( String title,Drawable  icon,String startColor,String endColor) {
        this.title = title;
        this.icon = icon;
        this.startColor = startColor;
        this.endColor = endColor;

    }


    public FeatureItem( String title,String subTitle,Drawable  icon,boolean isLinear) {
        this.title = title;
        this.icon = icon;
        this.subTitle = subTitle;
        this.isLinear = isLinear;
    }





    public String  getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }



    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FeatureItem) {
            FeatureItem inItem = (FeatureItem) o;
            return this.title.equals(inItem.title);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        if(isLinear){
            return R.layout.feature_item_linear;
        }
        return R.layout.feature_item;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads) {
        holder.mTitle.setText(title);
        holder.mIcon.setImageDrawable(icon);
        if(isLinear) {
            holder.mSubTitle.setText(subTitle);
        }else {
            makeGradient(holder.parent);

        }
//        if(adapter.hasFilter()) {
//            Context context = Application.getContext();
//            String filter = adapter.getFilter(String.class);
//            UtilsManager.getInstance().highlightText(holder.mTitle, getTitle(), filter,
//                    context.getResources().getColor(R.color.colorPrimary));
//
//        }


    }

    private void makeGradient(View view) {

        int[] colors = {Color.parseColor(startColor),Color.parseColor(endColor)};

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                colors);
        gd.setCornerRadius(0f);
        view.setBackground(gd);
    }

    @Override
    public boolean filter(String constraint) {
        return getTitle().trim().toLowerCase().contains(constraint.toLowerCase());
    }


    public class ViewHolder extends FlexibleViewHolder {
        private TextView mTitle,mSubTitle;
        private ImageView mIcon;

        private LinearLayout parent;

        private ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mTitle =  view.findViewById(R.id.title);
            mIcon = view.findViewById(R.id.icon);
            if(!isLinear){
                parent = view.findViewById(R.id.ly);

            }
            mSubTitle = view.findViewById(R.id.subTitle);
        }
    }
}
