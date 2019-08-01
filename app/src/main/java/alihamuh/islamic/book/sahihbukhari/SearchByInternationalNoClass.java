package alihamuh.islamic.book.sahihbukhari;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import alihamuh.islamic.book.sahihbukhari.DataBase_Helpers.MyDataBaseHelper;

public class SearchByInternationalNoClass {

    private ArrayList<InternationalNumberingQueryClass> possibleRefNo = new ArrayList<>();
    Context context;

    public SearchByInternationalNoClass(Context mContext){
        this.context=mContext;
    }

    public void InternationNODialogBox(){

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        final View view = layoutInflater.inflate(R.layout.internation_number_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(false);

        final EditText InternationalNo = (EditText) view.findViewById(R.id.international_No);

        Button cancelButton = (Button) view.findViewById(R.id.cancel);
        Button goButton = (Button) view.findViewById(R.id.go);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchingDataBaseforInternationalNumber(InternationalNo);

            }
        });


        alertDialog.setView(view);
        alertDialog.show();
    }



    public void fetchingDataBaseforInternationalNumber(EditText interNo){

        MyDataBaseHelper mDBHlpr = new MyDataBaseHelper(context);
        //CommonSQLiteUtilities.logDatabaseInfo(mDBHlpr.getWritableDatabase());

        String data =interNo.getText().toString();
        Cursor result = searchdata(data);

        result.moveToFirst();
        if (result.moveToFirst()) {
            //sendQuery(result);

            RefQuery(result,data);

        }else{
                 /*
                if(data>0 && data<7564){
                    Cursor result2 = searchdata2(""+data);
                    sendQuery(result2);
                 }else {
                */
            final AlertDialog error2 = new AlertDialog.Builder(context, R.style.AlertDialogStyle).create();
            error2.setMessage("Please Enter a Valid Hadith Number");
            error2.setTitle("Error");
            error2.show();
            //}
        }

    }


    public void RefQuery(Cursor cursor,String data){


        if (cursor.moveToFirst()) {
            do {


                String orignalRefNo = cursor.getString(cursor.getColumnIndex("Reference"));
                String chapterRef = cursor.getString(cursor.getColumnIndex("Reference1"));
                int chapter = cursor.getInt(cursor.getColumnIndex("Chapter"));

                InternationalNumberingQueryClass INC= new InternationalNumberingQueryClass();

                INC.setChapter(chapter);
                INC.setChapterRef(chapterRef);
                INC.setOrignalRefNo(orignalRefNo);

                possibleRefNo.add(INC);

            } while (cursor.moveToNext());


            for(InternationalNumberingQueryClass inc:possibleRefNo){
                if(inc.getOrignalRefNo().contains(",")){
                    String[] refNos=inc.getOrignalRefNo().split(",");
                    for(String ref:refNos){
                        if(ref.replaceAll(" ","").equals(data)){


                            int chapterHadithNo = 0;
                            if (inc.getChapter() == 65) {
                                chapterHadithNo = Integer.parseInt(inc.getChapterRef().replaceAll("Book " + inc.getChapter() + ", Hadith ", ""));
                            } else {
                                chapterHadithNo = Integer.parseInt(inc.getChapterRef().replaceAll(" Book " + inc.getChapter() + ", Hadith ", ""));
                            }

                            MainActivity.ITEM = inc.getChapter();
                            MainActivity.HADITH_NO = chapterHadithNo;
                            context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
                        }
                    }
                }else{


                    if(inc.getOrignalRefNo().equals(data)){
                        int chapterHadithNo = 0;
                        if (inc.getChapter() == 65) {
                            chapterHadithNo = Integer.parseInt(inc.getChapterRef().replaceAll("Book " + inc.getChapter() + ", Hadith ", ""));
                        } else {
                            chapterHadithNo = Integer.parseInt(inc.getChapterRef().replaceAll(" Book " + inc.getChapter() + ", Hadith ", ""));
                        }

                        MainActivity.ITEM = inc.getChapter();
                        MainActivity.HADITH_NO = chapterHadithNo;
                        context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
                    }

                }
            }



        }




    }

    public Cursor searchdata(String arg1){

        MyDataBaseHelper mDBHlpr =new MyDataBaseHelper(context);

        Cursor cursor = mDBHlpr.getReadableDatabase().query("hadith", new String[]{"Chapter","Reference1","Reference"},"Reference" + " LIKE ?",new String[]{"%"+arg1+"%"},null,null,null );
        return cursor;

    }

}
