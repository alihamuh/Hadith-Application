package alihamuh.islamic.book.sahihbukhari;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;

import alihamuh.islamic.book.sahihbukhari.DataBase_Helpers.MyDataBaseHelper;

public class DataBaseBooktask extends AsyncTask<Context,Void,ArrayList<ChapterNames>> {
    @Override
    protected ArrayList<ChapterNames> doInBackground(Context... contexts) {
        ArrayList<ChapterNames> chapterNames = new ArrayList<>();

        MyDataBaseHelper mDBHlpr =new MyDataBaseHelper(contexts[0]);
        //CommonSQLiteUtilities.logDatabaseInfo(mDBHlpr.getWritableDatabase());


        Cursor result = mDBHlpr.getReadableDatabase().query("chapter",new String[]{"ChapterEnglish","ChapterArabic"}
                ,null,null,null,null,null);

        result.moveToFirst();

        if(result.moveToFirst()){
            do{

                ChapterNames row = new ChapterNames();

                row.setArabicNames(result.getString(result.getColumnIndex("ChapterArabic")));

                row.setEngNames(result.getString(result.getColumnIndex("ChapterEnglish")));


                chapterNames.add(row);

            }while(result.moveToNext());

        }



        result.close();
        mDBHlpr.close();
        return chapterNames;
    }
}
