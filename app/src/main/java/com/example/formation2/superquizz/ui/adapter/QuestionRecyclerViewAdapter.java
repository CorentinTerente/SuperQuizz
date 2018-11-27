package com.example.formation2.superquizz.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.formation2.superquizz.R;
import com.example.formation2.superquizz.model.Question;
import com.example.formation2.superquizz.ui.fragments.QuestionListFragment.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Question} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder> {

    private final List<Question> mValues;
    private final OnListFragmentInteractionListener mListener;

    public QuestionRecyclerViewAdapter(List<Question> questionList, OnListFragmentInteractionListener listener) {
        mValues = questionList;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getTitle());
        Picasso.with(holder.mImage.getContext()).load(holder.mItem.getImageUrl()).resize(50,50).centerCrop().into(holder.mImage);
        holder.mView.setOnClickListener(v -> {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onQuestionListSelected(holder.mItem);
                    }
                }
        );
        holder.mView.setOnLongClickListener(v->{
            mListener.OnQuestionLongPressed(holder.mItem);
            return  true;
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView mContentView;
        public Question mItem;
        public ImageView mImage;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
            mImage = view.findViewById(R.id.image_view_author);
        }

    }
}
