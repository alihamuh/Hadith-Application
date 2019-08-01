package alihamuh.islamic.book.sahihbukhari;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;

import alihamuh.islamic.book.sahihbukhari.DataBase_Helpers.MyDataBaseHelper;

public class DataBaseTask extends AsyncTask <Integer,Void,ArrayList<CompleteEngUrduArabicHadith>> {

    private Context mContext;
    CommonAppClass appClass =new CommonAppClass();
    //private Cursor result;

    public DataBaseTask(Context context){
        this.mContext=context;
    }


    @Override
    protected ArrayList<CompleteEngUrduArabicHadith> doInBackground(Integer... indext) {
        ArrayList<CompleteEngUrduArabicHadith> completeChapter = new ArrayList<>();

        MyDataBaseHelper mDBHlpr =new MyDataBaseHelper(mContext);
        //CommonSQLiteUtilities.logDatabaseInfo(mDBHlpr.getWritableDatabase());


       Cursor result = mDBHlpr.getReadableDatabase().query("hadith",
                new String[]{"ArabicSanad","ArabicHadith","ArabicHadith2","EnglishSanad","EnglishHadith","UrduHadith","Reference","Reference1","Reference2"}
                ,"chapter = "+indext[0],null,null,null,null);

        result.moveToFirst();

        if(result.moveToFirst()){
            do{

                CompleteEngUrduArabicHadith row = new CompleteEngUrduArabicHadith();

                if(!appClass.getNightmode()) {
                    row.setArabichadith("<font color=#737373>" + result.getString(result.getColumnIndex("ArabicSanad"))
                            + "</font> <font color=#000000>" + result.getString(result.getColumnIndex("ArabicHadith"))
                            + "</font><font color=#737373>" + result.getString(result.getColumnIndex("ArabicHadith2")) + "</font>");
                }else{
                    row.setArabichadith("<font color=#737373>" + result.getString(result.getColumnIndex("ArabicSanad"))
                            + "</font> <font color=#fafafa>" + result.getString(result.getColumnIndex("ArabicHadith"))
                            + "</font><font color=#737373>" + result.getString(result.getColumnIndex("ArabicHadith2")) + "</font>");

                }
                row.setEnglishhadith(result.getString(result.getColumnIndex("EnglishHadith")));

                row.setEnglishNarrator("</font> <font color=#737373>"+result.getString(result.getColumnIndex("EnglishSanad"))+"</font>");

                if(indext[0]==64||indext[0]==65){
                    row.setUrduHadith("حدیث موجود نہیں۔");
                }else {
                    row.setUrduHadith(result.getString(result.getColumnIndex("UrduHadith")));
                }

                row.setRef1hadith("Sahih-Al-Bukhari Hadith No:"+result.getString(result.getColumnIndex("Reference")));

                row.setRef2hadith(result.getString(result.getColumnIndex("Reference1")));

                row.setRef3hadith(result.getString(result.getColumnIndex("Reference2")));

                if(indext[0]==65){
                    row.setHadithNo(result.getString(result.getColumnIndex("Reference1")).replaceAll("Book " + indext[0] + ", Hadith ", ""));
                }else {
                    row.setHadithNo(result.getString(result.getColumnIndex("Reference1")).replaceAll(" Book " + indext[0] + ", Hadith ", ""));
                }

                completeChapter.add(row);

            }while(result.moveToNext());

        }



        result.close();
        mDBHlpr.close();


/*
        SQLiteConnection sqLiteConnection=null;

        SQLiteStatement sqLiteStatement=null;

        try
        {
            File databaseFile = mContext.getDatabasePath("bukhari.db");
            sqLiteConnection=new SQLiteConnection(databaseFile);
            sqLiteConnection.open();
            sqLiteStatement=sqLiteConnection.prepare("SELECT ArabicSanad," +
                    "ArabicHadith," +
                    "ArabicHadith2," +
                    "EnglishSanad," +
                    "EnglishHadith," +
                    "UrduHadith,Reference," +
                    "Reference1," +
                    "Reference2 " +
                    "FROM hadith " +
                    "WHERE chapter="+indext[0]);

            //sqLiteStatement.bind(1, "id");
            //sqLiteStatement.step();
            while(sqLiteStatement.step()) {

                CompleteEngUrduArabicHadith row = new CompleteEngUrduArabicHadith();

                row.setArabichadith("<font color=#737373>"+ sqLiteStatement.columnString(0)
                        +"</font> <font color=#000000>"+sqLiteStatement.columnString(1)
                        +"</font><font color=#737373>"+sqLiteStatement.columnString(2)+"</font>");

                row.setEnglishhadith("<font color=#000000>"+sqLiteStatement.columnString(4)+"</font>");

                row.setEnglishNarrator("<font color=#737373>"+sqLiteStatement.columnString(3)+"</font>");

                row.setUrduHadith("<font color=#000000>"+sqLiteStatement.columnString(5)+"</font>");

                row.setRef1hadith("Sahih-Al-Bukhari Hadith No:"+sqLiteStatement.columnString(6));

                row.setRef2hadith(sqLiteStatement.columnString(7));

                row.setRef3hadith(sqLiteStatement.columnString(8));

                row.setHadithNo(sqLiteStatement.columnString(7).replaceAll(" Book "+indext[0]+", Hadith ",""));

                completeChapter.add(row);

            }



            //sqLiteConnection.
            //String def=sqLiteStatement.columnString(0);
            //String jhg =sqLiteStatement.
            //TextView e =(TextView)findViewById(R.id.text1);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            if(sqLiteStatement!=null)
                sqLiteStatement.dispose();
            if(sqLiteConnection!=null)
                sqLiteConnection.dispose();
        }
*/

        return completeChapter;




    }





}
