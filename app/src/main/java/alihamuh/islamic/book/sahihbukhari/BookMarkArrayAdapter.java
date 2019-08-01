package alihamuh.islamic.book.sahihbukhari;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class BookMarkArrayAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Integer> hadith;
    private final ArrayList<Integer> chapter;
    LayoutInflater inflater;
    CommonAppClass Myapp=new CommonAppClass();

    int TOTAL_NO_OF_PAGES=Myapp.getNoOfPages();
    int TOTAL_PAGES_MINUS_ONE=TOTAL_NO_OF_PAGES-1;


    public BookMarkArrayAdapter(Context context,ArrayList<Integer> hadith,ArrayList<Integer> chapter) {
        this.context = context;
        this.hadith = hadith;
        this.chapter = chapter;
        inflater =(LayoutInflater.from(context));

    }


    @Override
    public int getCount() {
        return hadith.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder{

        private TextView page;
        private CheckBox chkbx;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();

        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)convertView = vi.inflate(R.layout.bookmark_button,parent,false);

        mViewHolder.page = (TextView)convertView.findViewById(R.id.bookmark_page);
        mViewHolder.chkbx=(CheckBox)convertView.findViewById(R.id.checkbox);


        final int hadithNo = hadith.get(position);
        final int chapterNo = chapter.get(position);

        mViewHolder.page.setText("Book "+chapterNo+", Hadith "+hadithNo);



        convertView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
                MainActivity.ITEM = chapterNo;
                MainActivity.HADITH_NO= hadithNo;
                context.startActivity(i);
            }
        });

        if(Myapp.getCheckboxShown()){
            mViewHolder.chkbx.setVisibility(View.VISIBLE);
        }


        mViewHolder.chkbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((CheckBox)v).isChecked()){
                    Myapp.getChecks().set(position, 1);
                }
                else{
                    Myapp.getChecks().set(position, 0);
                }
            }
        });

        return convertView;
    }




}


