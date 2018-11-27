package com.example.formation2.superquizz.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.formation2.superquizz.R;
import com.example.formation2.superquizz.database.QuestionsDatabaseHelper;
import com.example.formation2.superquizz.model.Question;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
@SuppressWarnings("EmptyMethod")
public class ScoreFragment extends Fragment {


    public ScoreFragment() {
        // Required empty public constructor
    }


    private PieChart chart;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_score, container, false);

        getActivity().setTitle("PieChartActivity");


        chart = rootView.findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(10f);
        chart.setTransparentCircleRadius(61f);

        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);

        chart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(12f);

        updateChart();
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


    private void updateChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        QuestionsDatabaseHelper dbHelper = QuestionsDatabaseHelper.getInstance(getContext());
        List<Question> unAnsweredQuestion = new ArrayList<>();
        List<Question> allQuestions = dbHelper.getAllQuestion();
        List<Question> correctAnsweredQuestions = new ArrayList<>();
        List<Question> wrongAnsweredQuestions = new ArrayList<>();

        for (Question question : allQuestions) {
            if(question.getHaveRespond() == 0) {

                unAnsweredQuestion.add(question);

            } else if (question.verifyResponse(question.getUserResponse())) {

                    correctAnsweredQuestions.add(question);

                } else if (!question.verifyResponse(question.getUserResponse())) {

                    wrongAnsweredQuestions.add(question);
                }

        }

        int correctAnswersCount = correctAnsweredQuestions.size();
        int wrongAnswersCount = wrongAnsweredQuestions.size();
        int unansweredQuestionCount = unAnsweredQuestion.size();

        int total = allQuestions.size();

        ArrayList<PieEntry> questionEntries = new ArrayList<>();


        questionEntries.add(new PieEntry((float)correctAnswersCount/(float)(total),getString(R.string.pie_chart_good_answer)));
        questionEntries.add(new PieEntry((float)wrongAnswersCount/(float)(total),getString(R.string.pie_chart_wrong_answer)));
        questionEntries.add(new PieEntry((float)unansweredQuestionCount/(float)(total),getString(R.string.pie_chart_todo_questions)));


        PieDataSet dataSet = new PieDataSet(questionEntries, "");

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(getResources().getColor(R.color.primaryColor,null));
        colors.add(getResources().getColor(R.color.secondaryColor,null));
        colors.add(Color.LTGRAY);

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

}
