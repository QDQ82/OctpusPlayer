package com.CNSI.OctopusPlayer.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.CNSI.OctopusPlayer.MainActivity;
import com.CNSI.OctopusPlayer.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_share, container, false);

        ((MainActivity) MainActivity.mainactivity).SetVisibleMenu(false);


        return root;
    }
}