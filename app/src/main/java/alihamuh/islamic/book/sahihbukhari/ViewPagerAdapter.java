package alihamuh.islamic.book.sahihbukhari;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<CompleteEngUrduArabicHadith> engUrduArabicHadiths;

    public ViewPagerAdapter(FragmentManager fm, List<CompleteEngUrduArabicHadith> completeEngUrduArabicHadiths) {
        super(fm);
        this.engUrduArabicHadiths =completeEngUrduArabicHadiths;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.getInstance(engUrduArabicHadiths.get(position));
    }

    @Override
    public int getCount() {
        return engUrduArabicHadiths.size();
    }

}
