package alihamuh.islamic.book.sahihbukhari;


import android.os.Parcel;
import android.os.Parcelable;

public class CompleteEngUrduArabicHadith implements Parcelable {

    private String arabicHadith;
    private String englishHadith;
    private String englishNarrator;
    private String urduHadith;
    private String ref1hadith;
    private String ref2hadith;
    private String ref3hadith;
    private String hadithNo;

    protected CompleteEngUrduArabicHadith(Parcel in) {
        arabicHadith = in.readString();
        englishHadith = in.readString();
        englishNarrator = in.readString();
        urduHadith = in.readString();
        ref1hadith = in.readString();
        ref2hadith = in.readString();
        ref3hadith = in.readString();
        hadithNo   = in.readString();
    }

    public static final Creator<CompleteEngUrduArabicHadith> CREATOR = new Creator<CompleteEngUrduArabicHadith>() {
        @Override
        public CompleteEngUrduArabicHadith createFromParcel(Parcel in) {
            return new CompleteEngUrduArabicHadith(in);
        }

        @Override
        public CompleteEngUrduArabicHadith[] newArray(int size) {
            return new CompleteEngUrduArabicHadith[size];
        }
    };

    public CompleteEngUrduArabicHadith() {

    }

    public void setArabichadith(String arabic){
        this.arabicHadith =arabic;
    }

    public void setEnglishhadith(String english){
        this.englishHadith=english;
    }

    public void setEnglishNarrator(String narrator){
        this.englishNarrator=narrator;
    }

    public void setUrduHadith(String urdu){
        this.urduHadith=urdu;
    }

    public void setRef1hadith(String ref1){
        this.ref1hadith=ref1;
    }

    public void setRef2hadith(String ref2){
        this.ref2hadith=ref2;
    }

    public void setRef3hadith(String ref3) {
        this.ref3hadith = ref3;
    }

    public void setHadithNo(String hadithNoHadith){
        this.hadithNo=hadithNoHadith;
    }

    public String getArabicHadith() {
        return arabicHadith;
    }

    public String getEnglishHadith() {
        return englishHadith;
    }

    public String getEnglishNarrator() {
        return englishNarrator;
    }

    public String getUrduHadith() {
        return urduHadith;
    }

    public String getRef1hadith() {
        return ref1hadith;
    }

    public String getRef2hadith() {
        return ref2hadith;
    }

    public String getRef3hadith() {
        return ref3hadith;
    }

    public String getHadithNo() {
        return hadithNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(arabicHadith);
        parcel.writeString(englishHadith);
        parcel.writeString(englishNarrator);
        parcel.writeString(urduHadith);
        parcel.writeString(ref1hadith);
        parcel.writeString(ref2hadith);
        parcel.writeString(ref3hadith);
        parcel.writeString(hadithNo);
    }
}
