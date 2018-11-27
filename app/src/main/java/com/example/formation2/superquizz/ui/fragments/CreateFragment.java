package com.example.formation2.superquizz.ui.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.formation2.superquizz.R;
import com.example.formation2.superquizz.model.Question;
import com.example.formation2.superquizz.broadcast.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings({"CanBeFinal", "EmptyMethod"})
public class CreateFragment extends Fragment {



    private RadioButton checkedButton ,rButton1, rButton2, rButton3, rButton4;
    private EditText editTextProposition1, editTextProposition2, editTextProposition3, editTextProposition4, editTextTitle;
    public OnCreateListener listener;
    FloatingActionButton fabValidate;

    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerReceiver();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create, container, false);

         rButton1 = rootView.findViewById(R.id.radio_button_proposition1);
         rButton2 = rootView.findViewById(R.id.radio_button_proposition2);
         rButton3 = rootView.findViewById(R.id.radio_button_proposition3);
         rButton4 = rootView.findViewById(R.id.radio_button_proposition4);
         fabValidate = rootView.findViewById(R.id.fab_validate);

         editTextProposition1 = rootView.findViewById(R.id.edit_text_proposition1);
         editTextProposition2 = rootView.findViewById(R.id.edit_text_proposition2);
         editTextProposition3 = rootView.findViewById(R.id.edit_text_proposition3);
         editTextProposition4 = rootView.findViewById(R.id.edit_text_proposition4);
         editTextTitle = rootView.findViewById(R.id.edit_text_title);



        rButton1.setOnClickListener(rButtonListener);
        rButton2.setOnClickListener(rButtonListener);
        rButton3.setOnClickListener(rButtonListener);
        rButton4.setOnClickListener(rButtonListener);
        fabValidate.setOnClickListener(fabListener);

        return rootView;
    }

    private View.OnClickListener rButtonListener =  v -> {
        if(checkedButton != null) {
            checkedButton.setChecked(false);
        }

        ((RadioButton) v).toggle();
        checkedButton = ((RadioButton) v);

    };

    private View.OnClickListener fabListener = v -> {
        boolean nothingChecked = false;
        boolean isEmpty = false;
        Toast toastChecked = Toast.makeText(this.getContext(),getString(R.string.toast_checked), Toast.LENGTH_SHORT);
        Toast toastEmpty = Toast.makeText(this.getContext(),getString(R.string.toast_empty), Toast.LENGTH_SHORT);
        List<String> stringsList = new ArrayList<>();
        String title = editTextTitle.getText().toString();
        String proposition1 = editTextProposition1.getText().toString();
        String proposition2 = editTextProposition2.getText().toString();
        String proposition3 = editTextProposition3.getText().toString();
        String proposition4 = editTextProposition4.getText().toString();
        stringsList.add(title);
        stringsList.add(proposition1);
        stringsList.add(proposition2);
        stringsList.add(proposition3);
        stringsList.add(proposition4);

        for(String aString : stringsList){
            if(aString.isEmpty()){
                toastEmpty.show();
                isEmpty = true;
            }
        }


        Question newQuestion = new Question(title);


        newQuestion.addProposition(proposition1);
        newQuestion.addProposition(proposition2);
        newQuestion.addProposition(proposition3);
        newQuestion.addProposition(proposition4);


            if (rButton1.isChecked()) {
                newQuestion.setGoodResponse(1);
            } else if (rButton2.isChecked()) {
                newQuestion.setGoodResponse(2);
            } else if (rButton3.isChecked()) {
                newQuestion.setGoodResponse(3);
            } else if (rButton4.isChecked()) {
                newQuestion.setGoodResponse(4);
            } else {
                nothingChecked = true;
            }


        if(nothingChecked || isEmpty) {
            toastChecked.show();
        } else {
            listener.questionCreated(newQuestion);
        }

    };

    public interface OnCreateListener {

        void questionCreated(Question q);
    }

    @Override
    public void onDetach(){
        super.onDetach();

        listener = null;
    }

    private void registerReceiver()
    {
        try
        {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(NetworkChangeReceiver.NETWORK_CHANGE_ACTION);
            getActivity().registerReceiver(internalNetworkChangeReceiver, intentFilter);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroy()
    {
        try
        {
            // Make sure to unregister internal receiver in onDestroy().
            getActivity().unregisterReceiver(internalNetworkChangeReceiver);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        super.onDestroy();
    }

    InternalNetworkChangeReceiver internalNetworkChangeReceiver = new InternalNetworkChangeReceiver();

    class InternalNetworkChangeReceiver extends BroadcastReceiver
    {
        private boolean previousValue;
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean currentValue = intent.getBooleanExtra("isOnline",false);

            if(!currentValue && currentValue !=previousValue){
              Toast offlineToast = Toast.makeText(getContext(),"No Internet connection",Toast.LENGTH_SHORT);
              offlineToast.show();
              previousValue = currentValue;
            } else if(currentValue != previousValue) {
                Toast onlineToast = Toast.makeText(getContext(),"Your are online", Toast.LENGTH_SHORT);
                onlineToast.show();
                previousValue=currentValue;
            }

        }
    }

}
