package alihamuh.islamic.book.sahihbukhari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import alihamuh.islamic.book.sahihbukhari.Settings.SettingActivity;

import static alihamuh.islamic.book.sahihbukhari.MainHadithContentpage.appClass;

public class MainActivity extends AppCompatActivity {


    private ArrayList<CompleteEngUrduArabicHadith> completeEngUrduArabicHadiths = new ArrayList<>();
    private ViewPager hadithViewPager;
    public static int ITEM;
    public static int HADITH_NO;
    private int lowerIndex=ITEM;
    private int upperIndex=ITEM;
    InterstitialAd mInterstitialAd;
    private Boolean lowerChapter=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Utils.onActivityCreateSetTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        completeEngUrduArabicHadiths.addAll(fetchDatafromDatabase());



        sendingDataToAdapterandInitializingViewpager(0);


        hadithViewPager.addOnPageChangeListener(viewPagerListener());



            settingLowerChapter(HADITH_NO);





         initializingFullScreenandBannerAds();
        //CommonSQLiteUtilities.logCursorColumns(cur);


        //SQLiteConnection db = new SQLiteConnection(new File("/tmp/database"));


        //motionEventforViewpager();



    }

    @Override
    public void onBackPressed() {
        //Log.d("CDA", "onBackPressed Called");
        appClass.setHadithpageNo(0);
        Intent i = new Intent(this, MainHadithContentpage.class);
        //MainActivity.ITEM =srNum[position];
        this.startActivity(i);
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

            appClass.setHadithpageNo(hadithViewPager.getCurrentItem());
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));

        }else if(id==R.id.nightmode_setting){

            //this setHadithpageNo will be used in settinglowerchapter when the class is reinitialized
            appClass.setHadithpageNo(hadithViewPager.getCurrentItem());

            if(!appClass.getHadithpageNighMode()) {

                Utils.changeToTheme(MainActivity.this, false);

            }else{

                Utils.changeToTheme(MainActivity.this, true);
            }

             appClass.setHadithpageNighMode(!appClass.getHadithpageNighMode());




        }else if(id ==R.id.bookmark_setting){



        }else if(id ==R.id.bookmarkView_setting){
            Intent i = new Intent(this, BookMark.class);
            this.startActivity(i);
        }else if(id==R.id.search_setting){

               searchOptions();
        }


        return super.onOptionsItemSelected(item);
    }


    public void searchOptions(){

        LayoutInflater layoutInflater = LayoutInflater.from(this);

        final View view = layoutInflater.inflate(R.layout.search_option_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(true);


        Button InternationaButton = (Button) view.findViewById(R.id.search_international);
        Button ChapterButton = (Button) view.findViewById(R.id.search_chapter);

        InternationaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchByInternationalNoClass SBINC=new SearchByInternationalNoClass(MainActivity.this);

                SBINC.InternationNODialogBox();
            }
        });

        ChapterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchByChapterNoClass SBCNC =new SearchByChapterNoClass(MainActivity.this);
                SBCNC.startSearch();
            }
        });


        alertDialog.setView(view);
        alertDialog.show();


    }


   int cursorPosition;
    Boolean fromLeft=true;
    public ViewPager.OnPageChangeListener viewPagerListener(){

        ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener(){


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == completeEngUrduArabicHadiths.size()-1 && upperIndex<97){

                    upperIndex++;
                    lowerChapter=false;
                    completeEngUrduArabicHadiths.addAll(fetchDatafromDatabase());

                    int currentItem=hadithViewPager.getCurrentItem();

                    FragmentStatePagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), completeEngUrduArabicHadiths);

                    hadithViewPager.setAdapter(adapter);
                    hadithViewPager.setCurrentItem(currentItem);
                }

                if(position == 0 &&lowerIndex>1){
                    lowerIndex--;
                    lowerChapter=true;
                    ArrayList<CompleteEngUrduArabicHadith> completeEngUrduArabicHadiths2 = new ArrayList<>();

                    completeEngUrduArabicHadiths2.addAll(fetchDatafromDatabase());

                    int currentItem=completeEngUrduArabicHadiths2.size();
                    completeEngUrduArabicHadiths2.addAll(completeEngUrduArabicHadiths);

                    completeEngUrduArabicHadiths=completeEngUrduArabicHadiths2;

                    FragmentStatePagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),completeEngUrduArabicHadiths2);

                    hadithViewPager.setAdapter(adapter);
                    hadithViewPager.setCurrentItem(currentItem);

                }
                    if(cursorPosition>position){

                        fromLeft=false;
                        //Log.d("fromRight",""+fromLeft);

                    }else if(cursorPosition<position){

                        fromLeft=true;
                        //Log.d("fromLeft",""+fromLeft);
                    }
               cursorPosition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        return changeListener;
    }


    public ArrayList<CompleteEngUrduArabicHadith> fetchDatafromDatabase(){

        DataBaseTask task =new DataBaseTask(this);

        if(lowerChapter){
            task.execute(lowerIndex);
            return task.doInBackground(lowerIndex);
        }
         task.execute(upperIndex);
        return task.doInBackground(upperIndex);


         //return completeChapter;
    }

    public void sendingDataToAdapterandInitializingViewpager(int CurrentPage){

        hadithViewPager = (ViewPager)findViewById(R.id.view_pager);

    }

    public void settingLowerChapter(int hadithNo){

        lowerIndex--;
        lowerChapter=true;
        ArrayList<CompleteEngUrduArabicHadith> completeEngUrduArabicHadiths2 = new ArrayList<>();

        completeEngUrduArabicHadiths2.addAll(fetchDatafromDatabase());

        //because the normal position is hadith one and that will be equal to size
        int currentItem=completeEngUrduArabicHadiths2.size()-1+hadithNo;

        completeEngUrduArabicHadiths2.addAll(completeEngUrduArabicHadiths);

        completeEngUrduArabicHadiths=completeEngUrduArabicHadiths2;

        FragmentStatePagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),completeEngUrduArabicHadiths2);

        hadithViewPager.setAdapter(adapter);

        if(appClass.getHadithpageNo()==0){    //in other words nightmode button has not been pressed yet

        hadithViewPager.setCurrentItem(currentItem);

        }else{

            hadithViewPager.setCurrentItem(appClass.getHadithpageNo());

        }

    }


    public void initializingFullScreenandBannerAds(){

        AdView mAdView = findViewById(R.id.page_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    private String getStringResourceByName(String aString) {
        int resId = getResources().getIdentifier(aString, "string", getApplicationContext().getPackageName());
        return getString(resId);
    }


    @Override
    protected void onStop() {
        super.onStop();

        TextView rawHadithNo =(TextView)findViewById(R.id.hadithNo);
        int hadithNo = Integer.parseInt(rawHadithNo.getText().toString());

        TextView rawResumePage = (TextView)findViewById(R.id.ref2);
        String resumePage =rawResumePage.getText().toString();

        int resumePageChapter = Integer.parseInt(resumePage.replaceAll(", Hadith "+hadithNo,"").replaceAll(" Book " ,"" ));

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Hadith_No", 0);
        SharedPreferences.Editor editor = pref.edit();
        if(fromLeft){
            if(hadithNo!=1) {
                editor.putInt("resumeChapter", resumePageChapter);
                editor.putInt("resumeHadith", hadithNo + 1);
                int test = hadithNo + 1;
            }else{
                editor.putInt("resumeChapter", resumePageChapter);
                editor.putInt("resumeHadith", hadithNo);
                int test = hadithNo;
            }
        //Log.d("Chap_Hadith NO",""+test);
        //Log.d("Chapter",""+resumePageChapter);
        }else{
            editor.putInt("resumeChapter",resumePageChapter);
            editor.putInt("resumeHadith",hadithNo-1);
            int test =hadithNo-1;
            //Log.d("Chap_Hadith NO",""+test);
            //Log.d("Chapter",""+resumePageChapter);
        }

        editor.apply();

    }


    @Override
    protected void onStart() {
        super.onStart();
           if(appClass.getInSettings()) {
               startActivity(new Intent(getApplicationContext(), MainActivity.class));
               appClass.setInSettings(false);
           }
    }
}
