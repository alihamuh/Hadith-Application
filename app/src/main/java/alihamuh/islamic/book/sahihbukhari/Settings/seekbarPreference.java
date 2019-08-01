package alihamuh.islamic.book.sahihbukhari.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import alihamuh.islamic.book.sahihbukhari.R;

public class seekbarPreference extends Preference {
    private SeekBar seekBar;
    private TextView tv;
    String value;

    public seekbarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setWidgetLayoutResource(R.layout.seekbarpreferencelayout);
        value =attrs.getAttributeValue(2);

    }

    @Override
    protected View onCreateView(ViewGroup parent) {




        return super.onCreateView(parent);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);


    }

    @Override
    public View getView(View convertView, ViewGroup parent)
    {
        View v = super.getView(convertView, parent);

        tv =(TextView)v.findViewById(R.id.counterText);
        seekBar= (SeekBar)v.findViewById(R.id.seekbar);


        final SharedPreferences pref =PreferenceManager.getDefaultSharedPreferences(getContext());
        final SharedPreferences.Editor editor =pref.edit();



        int seekbarvalue =pref.getInt(value,5);
        tv.setText(""+(seekbarvalue+15));

        seekBar.setProgress(seekbarvalue+15);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv.setText(""+(i+15));
                editor.putInt(value,i);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        return v;
    }



}