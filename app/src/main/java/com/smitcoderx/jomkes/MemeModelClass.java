package com.smitcoderx.jomkes;

public class MemeModelClass {

    private String mURL;
    private String mCreator;
    private String mTitle;
    private int mUpVotes;

    public MemeModelClass(String URL, String creator, String title, int upVotes) {
        this.mURL = URL;
        this.mCreator = creator;
        this.mTitle = title;
        this.mUpVotes = upVotes;
    }

    public String getURL() {
        return mURL;
    }

    public String getCreator() {
        return mCreator;
    }

    public int getUpVotes() {
        return mUpVotes;
    }

    public String getTitle() {
        return mTitle;
    }
}
