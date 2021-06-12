package com.shovon.navigationdrawer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.shovon.navigationdrawer.CustomAdapter;
import com.shovon.navigationdrawer.MyConstants;
import com.shovon.navigationdrawer.R;

public class FragmentSound extends Fragment {
    public static String TAG_NAME_A = "a";
    ListView listView;
    int[] sound = new int[MyConstants.TOTAL_SOUND_A];
    String[] soundName;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sound_fragment, container, false);
        this.listView = (ListView) view.findViewById(R.id.listview);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle((CharSequence) getResources().getString(C0489R.string.str_Fragment_sounds));
        for (int i = 0; i < MyConstants.TOTAL_SOUND_A; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(TAG_NAME_A);
            sb.append(String.valueOf(i));
            this.sound[i] = getResources().getIdentifier(sb.toString(), "raw", getActivity().getPackageName());
        }
        this.listView.setAdapter(new CustomAdapter(getActivity(), GetResources.ArrayResourceA(getActivity(), this.soundName), this.sound, TAG_NAME_A));
        return view;
    }
}
