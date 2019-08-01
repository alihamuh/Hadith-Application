package alihamuh.islamic.book.sahihbukhari;

public class InternationalNumberingQueryClass {

    private String orignalRefNo ="";
    private String chapterRef = "";
    private int chapter = 0;




    public int getChapter() {
        return chapter;
    }

    public String getChapterRef() {
        return chapterRef;
    }

    public String getOrignalRefNo() {
        return orignalRefNo;
    }

    public void setChapterRef(String chapterRef) {
        this.chapterRef = chapterRef;
    }


    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public void setOrignalRefNo(String orignalRefNo) {
        this.orignalRefNo = orignalRefNo;
    }
}
