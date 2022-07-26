package com.example.quizapplication.RecyclerViewAdapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapplication.Models.QuestionModel;
import com.example.quizapplication.Models.QuizModel;
import com.example.quizapplication.QuestionsActivity;
import com.example.quizapplication.R;

import java.util.List;

public class QuizRecyclerViewAdapter extends RecyclerView.Adapter<QuizRecyclerViewAdapter.QuizViewHolder> {

    private List<QuizModel> quizModelList;

    public QuizRecyclerViewAdapter(List<QuizModel> list) {
        quizModelList = list;
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.quiz_card, viewGroup, false);

        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.getTv_quizTopic().setText(quizModelList.get(position).getTopic());
        viewHolder.getCv_quizHolder().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(viewHolder.getCv_quizHolder().getContext(), QuestionsActivity.class);
                i.putExtra("quizId", quizModelList.get(position).getId());
                viewHolder.getCv_quizHolder().getContext().startActivity(i);
            }
        });
    }

    public int getItemCount() {
        return quizModelList.size();
    }

    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_quizTopic;
        private final CardView cv_quizHolder;

        public QuizViewHolder(View view) {
            super(view);
            // define click listeners and initialize global values.

            tv_quizTopic = view.findViewById(R.id.tv_quizTopic);
            cv_quizHolder = view.findViewById(R.id.cv_quizHolder);
        }

        public CardView getCv_quizHolder() {
            return cv_quizHolder;
        }

        public TextView getTv_quizTopic() {
            return tv_quizTopic;
        }
    }
}
