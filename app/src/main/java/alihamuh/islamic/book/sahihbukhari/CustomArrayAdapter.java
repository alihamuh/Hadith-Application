package alihamuh.islamic.book.sahihbukhari;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import alihamuh.islamic.book.sahihbukhari.Font_helpers.FontText;


public class CustomArrayAdapter extends BaseAdapter {
    private final Context context;
    private final String[] eng;
    private final String[] arb;
    private final int[] srNum;
    LayoutInflater inflater;


    public CustomArrayAdapter(Context context, String[] eng, String[] arb, int[] srNum) {
        this.context = context;
        this.eng = eng;
        this.arb =arb;
        this.srNum=srNum;
        inflater =(LayoutInflater.from(context));

    }


    @Override
    public int getCount() {
        return eng.length;
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

        private TextView eng;
        private FontText arb;
   }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();

        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)convertView = vi.inflate(R.layout.custom_button,parent,false);

        mViewHolder.eng= (TextView)convertView.findViewById(R.id.engBtn);
        mViewHolder.arb = (FontText)convertView.findViewById(R.id.arbBtn);


        mViewHolder.eng.setText((position)+1+". "+eng[position]);
        mViewHolder.arb.setText(arb[position]);




        convertView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //int code_pg_no;
                //int total =556;
                //int converter;

                //int res2Id= context.getResources().getIdentifier(srNum[position],"integer",context.getApplicationContext().getPackageName());

                //int page_no = context.getApplicationContext().getResources().getInteger(res2Id);
                //converter = page_no-1;
                //code_pg_no =total-converter;

                Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
                MainActivity.ITEM =srNum[position];
                MainActivity.HADITH_NO=1;
                context.startActivity(i);
            }
        });

        return convertView;
    }





}

