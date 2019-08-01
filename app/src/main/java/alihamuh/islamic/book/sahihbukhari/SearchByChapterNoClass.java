package alihamuh.islamic.book.sahihbukhari;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchByChapterNoClass {

    Context context;

    public SearchByChapterNoClass(Context mContext){

        this.context=mContext;
    }


    public void startSearch(){

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        final View view = layoutInflater.inflate(R.layout.dialog_box, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(false);

        final EditText hadithNo = (EditText) view.findViewById(R.id.hadith_No);
        final EditText chapterNo = (EditText) view.findViewById(R.id.chapter_No);

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

                searchByChapterNo(hadithNo, chapterNo);

            }
        });


        alertDialog.setView(view);
        alertDialog.show();


    }


    public void searchByChapterNo(EditText hadith, EditText chapter){

        if(hadith.getText().toString().equals("") || chapter.getText().toString().equals("")){
            final AlertDialog error2 = new AlertDialog.Builder(context, R.style.AlertDialogStyle).create();
            error2.setMessage("Please Enter a Chapter/Hadith Number");
            error2.setTitle("Error");
            error2.show();

        }else {




            int hadithInt = Integer.parseInt(hadith.getText().toString());
            int chapterInt = Integer.parseInt(chapter.getText().toString());

            if (chapterInt > 97 || chapterInt < 1) {
                final AlertDialog error = new AlertDialog.Builder(context, R.style.AlertDialogStyle).create();
                error.setMessage("Please Enter a Valid Chapter Number");
                error.show();
            }else {

                MainActivity.ITEM = chapterInt;
                MainActivity.HADITH_NO = hadithInt;
                context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
            }
        }

    }

}
