package com.smitcoderx.jomkes;

public class MemeModelClass {

    private String mURL;
    private String mCreator;
    private int mUpVotes;

    public MemeModelClass(String URL, String creator, int upVotes) {
        this.mURL = URL;
        this.mCreator = creator;
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
}
