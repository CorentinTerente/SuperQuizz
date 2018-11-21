package com.example.formation2.superquizz.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
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
import com.example.formation2.superquizz.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.formation2.superquizz.ui.activities.MainActivity.questionList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RadioButton checkedButton ,rButton1, rButton2, rButton3, rButton4;
    private EditText editTextProposition1, editTextProposition2, editTextProposition3, editTextProposition4, editTextTitle;

    public CreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create, container, false);

         rButton1 = rootView.findViewById(R.id.radio_button_proposition1);
         rButton2 = rootView.findViewById(R.id.radio_button_proposition2);
         rButton3 = rootView.findViewById(R.id.radio_button_proposition3);
         rButton4 = rootView.findViewById(R.id.radio_button_proposition4);
         FloatingActionButton fabValidate = rootView.findViewById(R.id.fab_validate);

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
        Toast toastChecked = Toast.makeText(this.getContext(),"Must check a proposition", Toast.LENGTH_SHORT);
        Toast toastEmpty = Toast.makeText(this.getContext(),"Text field should no be empty", Toast.LENGTH_SHORT);
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
            }
        }


        Question newQuestion = new Question(title);


        newQuestion.addProposition(proposition1);
        newQuestion.addProposition(proposition2);
        newQuestion.addProposition(proposition3);
        newQuestion.addProposition(proposition4);

        if(rButton1.isChecked()){
            newQuestion.setBonneReponse(proposition1);
        } else if (rButton2.isChecked()) {
            newQuestion.setBonneReponse(proposition2);
        } else if (rButton3.isChecked()) {
            newQuestion.setBonneReponse(proposition3);
        } else if (rButton4.isChecked()) {
            newQuestion.setBonneReponse(proposition4);
        } else {
            nothingChecked = true;
        }

        if(nothingChecked){
            toastChecked.show();
        } else {
            questionList.save(newQuestion);
            Intent intent = new Intent(this.getContext(),MainActivity.class);
            startActivity(intent);
        }

        //TODO: send to DB
    };

}
