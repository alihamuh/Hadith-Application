package alihamuh.islamic.book.sahihbukhari;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.util.ArrayList;

import alihamuh.islamic.book.sahihbukhari.DataBase_Helpers.MyDataBaseHelper;
import alihamuh.islamic.book.sahihbukhari.Settings.SettingActivity;

public class MainHadithContentpage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String[] engArray ;
    private String[] arbArray ;
    private int[] srNumArray;
    InterstitialAd mInterstitialAd;
    ListView bookListView;
    private ArrayList<InternationalNumberingQueryClass> possibleRefNo = new ArrayList<>();
    public static CommonAppClass appClass = new CommonAppClass();


    private static String file_url = "https://github.com/alihamuh/book1-database/blob/master/bukhari.db.zip?raw=true";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //appClass.setNightmode(appClass.getNightmode());
        //Toast.makeText(this,"Night Mode is "+appClass.getNightmode(),Toast.LENGTH_SHORT ).show();
        //Utils.changeToTheme(this,appClass.getNightmode());
        Utils.onActivityCreateSetTheme(this);
        //setTheme(R.style.NightAppThem_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hadith_contentpage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        File f = getApplicationContext().getDatabasePath("bukhari.db");
        long filesizeinMB= (f.length())/(1024*1024);
        if(f.exists() && filesizeinMB>=25){
            //Log.d("DB","db file exists");
            //MyDataBaseHelper db = new MyDataBaseHelper(this);
            //CommonSQLiteUtilities.logDatabaseInfo(db.getWritableDatabase());



        MyDataBaseHelper mDBHelper = new MyDataBaseHelper(this);
        mDBHelper.getReadableDatabase();
        mDBHelper.close();




        DataBaseBooktask chapters = new DataBaseBooktask();
        chapters.execute(this);
        ArrayList<ChapterNames> chapterNames = new ArrayList<>();

        chapterNames =chapters.doInBackground(this);
        ///////////////////////////////////////////////////////////////////////////////////////////////

        engArray = new String[97];
        arbArray = new String[97];
        srNumArray= new int[97];
        int page_no;

        for(int index=0; index<97; index++){
            page_no=index+1;

            engArray[index]= chapterNames.get(index).getEngNames();
            arbArray[index]= chapterNames.get(index).getArabicNames();
            srNumArray[index]=page_no;

        }


        bookListView =(ListView)findViewById(R.id.bookListView);
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, engArray,arbArray,srNumArray);
        bookListView.setAdapter(adapter);


        }else {
            DownloadFileFromURL abc = new DownloadFileFromURL(this);
            abc.execute(file_url);
        }


        //////////////////////////////////////////////////////////////////////////////////////////////


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        initializingFullScreenandBannerAds();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            moveTaskToBack(true);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_hadith_contentpage, menu);
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
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));
        }else if(id == R.id.nightmode_setting_2){

            if(appClass.getNightmode()) {
                //appclass.setNightmode(true);
                Utils.changeToTheme(MainHadithContentpage.this, false);

            }else{
                //appclass.setNightmode(false);
                Utils.changeToTheme(MainHadithContentpage.this, true);
            }

            //this will be changed because nightmode change is taking place on all activities
            appClass.setHadithpageNighMode(!appClass.getHadithpageNighMode());
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bookmark_intro) {
            Intent i = new Intent(this, BookMark.class);
            this.startActivity(i);
        } else if (id == R.id.nav_mainAbout) {

            aboutAlertdialog();

        } else if (id == R.id.nav_mainDonate) {

            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.show();

        } else if (id == R.id.nav_mainShare) {

            share();

        } else if (id == R.id.nav_mainRate) {

            rateApp();

        } else if (id == R.id.nav_start_intro) {
            MainActivity.ITEM = 1;
            MainActivity.HADITH_NO = 1;
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if (id == R.id.nav_resume_intro) {

            SharedPreferences pref = getApplicationContext().getSharedPreferences("Hadith_No", 0);

            MainActivity.ITEM = pref.getInt("resumeChapter", 1);
            MainActivity.HADITH_NO = pref.getInt("resumeHadith", 1);
            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(in);

        } else if (id == R.id.nav_page_intro) {

           SearchByChapterNoClass SBCNC =new SearchByChapterNoClass(this);
           SBCNC.startSearch();

        } else if (id == R.id.nav_settings_intro) {
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));
        } else if (id == R.id.nav_international_intro) {


            SearchByInternationalNoClass SBINC=new SearchByInternationalNoClass(this);

            SBINC.InternationNODialogBox();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void initializingFullScreenandBannerAds(){
        MobileAds.initialize(this, getStringResourceByName("ad_app_id"));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getStringResourceByName("main_inter_ad"));
        //To Load Gogole Admob Interstitial Ad
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdView mAdView = findViewById(R.id.main_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    private String getStringResourceByName(String aString) {
        int resId = getResources().getIdentifier(aString, "string", getApplicationContext().getPackageName());
        return getString(resId);
    }


    public void aboutAlertdialog(){

        LayoutInflater li= LayoutInflater.from(this);
        final View aboutView= li.inflate(R.layout.about_dialog_box,null);

        android.app.AlertDialog aboutAlertDialog = new android.app.AlertDialog.Builder(this).create();
        aboutAlertDialog.setCancelable(true);
        aboutAlertDialog.setView(aboutView);
        aboutAlertDialog.show();


    }

    public void rateApp(){
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }

    }

    public void share(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Sahih Bukhari is a collection of sayings and deeds of Prophet Muhammad (pbuh), also known as the Sunnah." +
                "Download Sahih Bukhari now in Urdu, English and Arabic."+
                "http://play.google.com/store/apps/details?id="+ this.getPackageName();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Sahih Bukhari");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
