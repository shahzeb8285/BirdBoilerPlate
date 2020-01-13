package in.birdcommunication.core.Utils;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UtilsManager {
    private static volatile UtilsManager sSoleInstance;

    //private constructor.
    private UtilsManager(){

        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static UtilsManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (UtilsManager.class) {
                if (sSoleInstance == null) sSoleInstance = new UtilsManager();
            }
        }

        return sSoleInstance;
    }


    public  void highlightText(@NonNull final TextView textView,
                               @Nullable final String originalText,
                               @Nullable String constraint,
                               @ColorInt int color) {
        constraint = constraint.toLowerCase();
        int start = (originalText.toLowerCase()).indexOf(constraint);
        if (start != -1) {
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(originalText);
            spanText(originalText, constraint, color, start, spanText);
            textView.setText(spanText, TextView.BufferType.SPANNABLE);
        } else {
            textView.setText(originalText, TextView.BufferType.NORMAL);
        }
    }



    private  void spanText(@NonNull final String originalText,
                           @NonNull String constraint,
                           @ColorInt int color, int start,
                           @NonNull final Spannable spanText) {
        do {
            int end = start + constraint.length();
            spanText.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = (originalText.toLowerCase()).indexOf(constraint, end + 1); // +1 skips the consecutive span
        } while (start != -1);
    }

}
