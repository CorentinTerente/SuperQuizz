package com.example.formation2.superquizz.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.formation2.superquizz.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
@SuppressWarnings("EmptyMethod")
public class SettingsFragment extends Fragment {



    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
       SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
       CheckBox saveCb = rootView.findViewById(R.id.switch_save_answer);


        saveCb.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {

                SharedPreferences.Editor editor = mSettings.edit();
                if(isChecked) {
                    editor.putBoolean("saveAnswer", true);

                } else {
                    editor.putBoolean("saveAnswer", false);
                }
                editor.apply();
        });



       return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
