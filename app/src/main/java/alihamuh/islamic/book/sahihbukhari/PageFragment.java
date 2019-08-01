package alihamuh.islamic.book.sahihbukhari;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PageFragment extends Fragment {

    private String arabicResource;
    private CompleteEngUrduArabicHadith completeEngUrduArabicHadith;
    //CommonAppClass Myapp=new CommonAppClass();

    private static final float[] NEGATIVE = {
            -1.0f,     0,     0,    0, 255, // red
            0, -1.0f,     0,    0, 255, // green
            0,     0, -1.0f,    0, 255, // blue
            0,     0,     0, 1.0f,   0  // alpha
    };

    //public static Boolean nightMode=false;
    //public static Boolean highlight=false;





    public static PageFragment getInstance(CompleteEngUrduArabicHadith completeEngUrduArabicHadith) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putParcelable("completeHadith_source",completeEngUrduArabicHadith);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        arabicResource = getArguments().getString("arabic_source");
        completeEngUrduArabicHadith=getArguments().getParcelable("completeHadith_source");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view =inflater.inflate(R.layout.fragment_page, container, false);
        //view.setRotationY(180);



                return view;

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences setting=PreferenceManager.getDefaultSharedPreferences(getContext());

        int urdu = setting.getInt("urdFontSize",20);
        int eng  = setting.getInt("engFontSize",20);
        int arb  = setting.getInt("arbFontSize",20);

        Boolean arbText = setting.getBoolean("arbText",true);
        Boolean engText = setting.getBoolean("engText",true);
        Boolean urdText = setting.getBoolean("urdText",true);



             TextView hadithNo = (TextView) view.findViewById(R.id.hadithNo);
             hadithNo.setText(completeEngUrduArabicHadith.getHadithNo());
         if(arbText) {
             TextView tv = (TextView) view.findViewById(R.id.textview);
             tv.setVisibility(View.VISIBLE);
             tv.setText(Html.fromHtml(completeEngUrduArabicHadith.getArabicHadith()));
             tv.setTextSize(arb + 15);

         }else{
             TextView tv = (TextView) view.findViewById(R.id.textview);
             tv.setVisibility(View.GONE);
         }

         if(engText) {
             TextView tv2 = (TextView) view.findViewById(R.id.textview2);
             tv2.setText(Html.fromHtml(completeEngUrduArabicHadith.getEnglishNarrator()));
             tv2.setTextSize(eng + 15);
             tv2.setVisibility(View.VISIBLE);
             TextView tv3 =(TextView)view.findViewById(R.id.textview3);
             tv3.setText(Html.fromHtml(completeEngUrduArabicHadith.getEnglishHadith()));
             tv3.setTextSize(eng+15);
             tv3.setVisibility(View.VISIBLE);
         }else{
             TextView tv2 = (TextView) view.findViewById(R.id.textview2);
             tv2.setText("");
             tv2.setVisibility(View.GONE);
             TextView tv3 =(TextView)view.findViewById(R.id.textview3);
             tv3.setText("");
             tv3.setVisibility(View.GONE);
         }




         if(urdText) {
             TextView urduTextView = (TextView) view.findViewById(R.id.textview4);
             urduTextView.setText(Html.fromHtml(completeEngUrduArabicHadith.getUrduHadith()));
             urduTextView.setTextSize(urdu + 15);
             urduTextView.setVisibility(View.VISIBLE);
         }else{
             TextView urduTextView = (TextView) view.findViewById(R.id.textview4);
             urduTextView.setText("");
             urduTextView.setVisibility(View.GONE);
         }
        TextView tv4 = (TextView)view.findViewById(R.id.ref1);
        tv4.setText(completeEngUrduArabicHadith.getRef1hadith());

        TextView tv5 = (TextView)view.findViewById(R.id.ref2);
        tv5.setText(completeEngUrduArabicHadith.getRef2hadith());

        TextView tv6 = (TextView)view.findViewById(R.id.ref3);
        tv6.setText(completeEngUrduArabicHadith.getRef3hadith());

        ImageButton localHadithShareButton = (ImageButton)view.findViewById(R.id.local_share_button);

        localHadithShareButton.setOnClickListener(onShowPopupMenu());

        //registerForContextMenu(localHadithShareButton);


/*
        localHadithShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().openContextMenu(v);
                onShowPopupMenu();
            }
        });
*/

        /*
        TouchImageView imageView = (TouchImageView) view.findViewById(R.id.fragmentImageView);

        BitmapFactory.Options o = new BitmapFactory.Options();
        //o.inSampleSize = -1;
        o.inDither = false;
        bitmap = BitmapFactory.decodeResource(getResources(), imageResource, o);
        imageView.setImageBitmap(bitmap);
        if(Myapp.getNightmode()) {
            imageView.setColorFilter(new ColorMatrixColorFilter(NEGATIVE));
        }
        if(Myapp.getHighlight()){
            imageView.setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }

        //onTouchListenersforHidingAndShowingToolbar(imageView);
        */

    }

/*
    @SuppressLint("ClickableViewAccessibility")
    public void onTouchListenersforHidingAndShowingToolbar(TouchImageView imageView){

        imageView.setOnTouchListener(new View.OnTouchListener() {
            private float pointX;
            private float pointY;
            private int tolerance = 100;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        return false; //This is important, if you return TRUE the action of swipe will not take place.
                    case MotionEvent.ACTION_DOWN:
                        pointX = event.getX();
                        pointY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        boolean sameX = pointX + tolerance > event.getX() && pointX - tolerance < event.getX();
                        boolean sameY = pointY + tolerance > event.getY() && pointY - tolerance < event.getY();

                        if(sameX && sameY){
                        //The user "clicked" certain point in the screen or just returned to the same position an raised the finger
                        if (((AppCompatActivity)getActivity()).getSupportActionBar().isShowing()) {

                            //toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

                        } else {
                            ((AppCompatActivity)getActivity()).getSupportActionBar().show();
                            //toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                        }

                        if(((AppCompatActivity)getActivity()).findViewById(R.id.seebar_layout).getVisibility()==View.VISIBLE){
                            ((AppCompatActivity)getActivity()).findViewById(R.id.seebar_layout).setVisibility(View.GONE);
                        }else{
                            ((AppCompatActivity)getActivity()).findViewById(R.id.seebar_layout).setVisibility(View.VISIBLE);
                        }

                        }

                }
                return false;
            }
        });

    }

*/


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id ==R.id.bookmark_setting){
            //Log.d("BOOKMARK",""+completeEngUrduArabicHadith.getHadithNo());
            //Log.d("BOOKMARK2",completeEngUrduArabicHadith.getRef2hadith().replaceAll(", Hadith "+completeEngUrduArabicHadith.getHadithNo(),"").replaceAll(" Book " ,"" ));

            bookmarkMaker();


        }else if(id==R.id.nightmode_setting){

        }else if(id ==R.id.action_settings){

        }else if(id ==R.id.bookmarkView_setting){

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
            getActivity().getMenuInflater().inflate(R.menu.local_hadith_menu,menu);

    }




    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.local_hadith_share:
                Toast.makeText(getActivity(), "One", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }



    private View.OnClickListener onShowPopupMenu() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.setOnMenuItemClickListener(onPopupMenuClickListener());
                popupMenu.inflate(R.menu.local_hadith_menu);
                popupMenu.show();
            }
        };
    }

    private PopupMenu.OnMenuItemClickListener onPopupMenuClickListener() {
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.local_hadith_share:
                        share();
                        return true;
                    case R.id.local_hadith_copy:
                        
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label",completeHadith());

                        clipboard.setPrimaryClip(clip);

                        return true;
                    default:
                        return false;
                }
            }
        };
    }



    public void share(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = completeHadith()+"\n\n"+
                "http://play.google.com/store/apps/details?id="+ getActivity().getPackageName();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Zavia By Ashfaq Ahmed");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public String completeHadith(){
        String hadith=Html.fromHtml(completeEngUrduArabicHadith.getArabicHadith())+
                "\n\n"+Html.fromHtml(completeEngUrduArabicHadith.getEnglishNarrator())+
                "\n"+Html.fromHtml(completeEngUrduArabicHadith.getEnglishHadith())+
                "\n\n"+Html.fromHtml(completeEngUrduArabicHadith.getUrduHadith())+
                "\n\n"+completeEngUrduArabicHadith.getRef1hadith()+
                "\n"+completeEngUrduArabicHadith.getRef2hadith()+
                "\n"+completeEngUrduArabicHadith.getRef3hadith()+"\n";

        return hadith;
    }

    public void bookmarkMaker(){

        int bookmarkHadithNo= Integer.parseInt(completeEngUrduArabicHadith.getHadithNo());
        String bookMarkChapter=completeEngUrduArabicHadith.getRef2hadith().replaceAll(", Hadith "+completeEngUrduArabicHadith.getHadithNo(),"").replaceAll(" Book " ,"" );
        int bookMArkChapterNo =Integer.parseInt(bookMarkChapter);

        Boolean isBookmarked=false;   //default value

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();


        for(int index =1; index<=settings.getInt("bookmark_no",0);index++){

            int chapterBookMarked=settings.getInt("ChapterBookmark_"+index,0);
            int hadithBookMarked=settings.getInt("HadithBookmark_"+index,0);

            if(chapterBookMarked == bookMArkChapterNo && hadithBookMarked == bookmarkHadithNo){
                isBookmarked =true;
            }else{
                isBookmarked=false;
            }
        }

          if(isBookmarked) {

            Toast.makeText(getActivity().getApplicationContext(), "Already Bookmarked", Toast.LENGTH_SHORT).show();

        }else {

              editor.putInt("bookmark_no", settings.getInt("bookmark_no", 0) + 1);
              editor.apply();

              editor.putInt("HadithBookmark_" + settings.getInt("bookmark_no", 0), bookmarkHadithNo);
              editor.apply();

              editor.putInt("ChapterBookmark_" + settings.getInt("bookmark_no", 0), bookMArkChapterNo);
              editor.apply();

              Toast.makeText(getActivity().getApplicationContext(), "Bookmarked", Toast.LENGTH_SHORT).show();

          }
        Log.d("BOOKMARK",""+bookmarkHadithNo);
        Log.d("BOOKMARK2",""+bookMArkChapterNo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }




}