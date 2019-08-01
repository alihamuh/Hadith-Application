package alihamuh.islamic.book.sahihbukhari;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class CommonAppClass extends Application {

    private static Context context;
    private static volatile Boolean nightmode =false;
    private static volatile Boolean highlight=false;
    private static volatile Boolean checkboxShown=false;
    private static volatile Boolean hadithpageNighMode=true;
    private static volatile int hadithpageNo=0;
    private static ArrayList<Integer> checks=new ArrayList<Integer>();
    private int noOfPages=1;
    private static Boolean InSettings=false;

    public void onCreate() {
        super.onCreate();
        CommonAppClass.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return CommonAppClass.context;
    }


    public Boolean getNightmode(){
        return this.nightmode;
    }

    public void setNightmode(Boolean nightmodeChanged){

        nightmode=nightmodeChanged;
    }

    public static void setHadithpageNighMode(Boolean hadithpageNighMode) {
        CommonAppClass.hadithpageNighMode = hadithpageNighMode;
    }

    public static Boolean getHadithpageNighMode() {
        return hadithpageNighMode;
    }

    public static void setHadithpageNo(int hadithpageNo) {
        CommonAppClass.hadithpageNo = hadithpageNo;
    }

    public static int getHadithpageNo() {
        return hadithpageNo;
    }

    public Boolean getHighlight(){
        return this.highlight;
    }

    public void setHighlight(Boolean highlightChanged){
        highlight=highlightChanged;
    }

    public Boolean getCheckboxShown() {
        return this.checkboxShown;
    }

    public void setCheckboxShown(Boolean checkboxShownChanged){
        checkboxShown=checkboxShownChanged;
    }

    public int getNoOfPages(){
        return 773;
    }

    public void setNoOfPages(int pages){
        noOfPages=pages;
    }

    public ArrayList<Integer> getChecks(){
        return this.checks;
    }
    public void setChecks(ArrayList<Integer> checksChanged){
        checks=checksChanged;
    }

    public static void setInSettings(Boolean inSettings) {
        InSettings = inSettings;
    }

    public static Boolean getInSettings() {
        return InSettings;
    }
}
