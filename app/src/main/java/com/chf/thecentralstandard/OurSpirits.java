package com.chf.thecentralstandard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OurSpirits extends Fragment {

    public static Fragment newInstance(Context context) {
        OurSpirits f = new OurSpirits();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.our_spirits, null);
        return root;
    }

}
