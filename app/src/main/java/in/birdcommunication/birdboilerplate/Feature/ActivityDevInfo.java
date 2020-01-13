package in.birdcommunication.birdboilerplate.Feature;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import in.birdcommunication.auth.RouteActivity;
import in.birdcommunication.birdboilerplate.R;

public class ActivityDevInfo extends RouteActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        initViews();
    }

    @Override
    public void initViews() {
        findViewById(R.id.gmail).setOnClickListener(this);
        findViewById(R.id.fb).setOnClickListener(this);
        findViewById(R.id.twitter).setOnClickListener(this);
        findViewById(R.id.instagram).setOnClickListener(this);
        findViewById(R.id.website).setOnClickListener(this);
        findViewById(R.id.whatsapp).setOnClickListener(this);
        findViewById(R.id.address).setOnClickListener(this);
        findViewById(R.id.phone1).setOnClickListener(this);
        findViewById(R.id.phone2).setOnClickListener(this);
        findViewById(R.id.phone3).setOnClickListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gmail:
                email();
                break;
            case R.id.fb:
                goToSite("https://www.facebook.com/commbird");
                break;
            case R.id.twitter:
                goToSite("https://twitter.com/commbird");
                break;
            case R.id.instagram:
                goToSite("https://www.instagram.com/birdcommunication/");
                break;
            case R.id.website:
                goToSite("https://www.birdcommunication.in");
                break;
            case R.id.whatsapp:
                goToSite("https://wa.me/917210007080");
                break;
            case R.id.address:
                goToSite("https://g.page/bird-communication?gm");
                break;
            case R.id.phone1:
            case R.id.phone2:
            case R.id.phone3:
                dial(((TextView)view).getText().toString());
                break;

        }


    }

    private void email(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setDataAndType(Uri.parse("mailto:infobirdcommunication.in"),"text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Email from "+getResources().getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, "Place your email message here ...");
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    private void dial(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phone));
        startActivity(intent);
    }
    private void goToSite(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
