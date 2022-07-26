package com.example.quizapplication.RecyclerViewAdapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapplication.DataBase.DataBaseHelper;
import com.example.quizapplication.Models.AnswerModel;
import com.example.quizapplication.Models.QuestionModel;
import com.example.quizapplication.R;

import java.util.List;

public class QuestionsRecyclerViewAdapter extends RecyclerView.Adapter<QuestionsRecyclerViewAdapter.QuestionsViewHolder> {

    private List<QuestionModel> questionModelList;
    private DataBaseHelper dataBaseHelper;

    public QuestionsRecyclerViewAdapter(List<QuestionModel> list) {
        questionModelList = list;
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.questions_cardbox, viewGroup, false);

        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTv_question().setText(questionModelList.get(position).getQuestion());
        viewHolder.getTv_questionsCounter().setText((position + 1) + ".");

        dataBaseHelper = new DataBaseHelper(viewHolder.tv_questionsCounter.getContext());
        List<AnswerModel> answerModelList = dataBaseHelper.getAnswerByQuestionId(questionModelList.get(position));
        try {
            viewHolder.getRb_firstAnswer().setText(answerModelList.get(0).getAnswer());
            viewHolder.getRb_secondAnswer().setText(answerModelList.get(1).getAnswer());
            viewHolder.getRb_thirdAnswer().setText(answerModelList.get(2).getAnswer());
            viewHolder.getRb_fourthAnswer().setText(answerModelList.get(3).getAnswer());
        } catch (Exception e) {
            Log.d("here", "here");
        }

        RadioButton [] radioButtons =  {viewHolder.getRb_firstAnswer(),
                viewHolder.getRb_secondAnswer(),
                viewHolder.getRb_thirdAnswer(),
                viewHolder.getRb_fourthAnswer()};
        for(int i = 0; i < radioButtons.length && i < answerModelList.size(); i++) {
            try {
                radioButtons[i].setText(answerModelList.get(i).getAnswer());
                radioButtons[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            buttonView.setBackground(viewHolder.getRb_fourthAnswer().getResources().getDrawable(R.drawable.rectangle_2));
                            buttonView.setTextColor(viewHolder.getTv_question().getResources().getColor(R.color.white));
                            return;
                        }
                        buttonView.setBackground(viewHolder.getRb_fourthAnswer().getResources().getDrawable(R.drawable.rectangle_1_shape));
                        buttonView.setTextColor(viewHolder.getTv_question().getResources().getColor(R.color.black));
                    }
                });
            } catch (Exception e) {
                Log.d("hi", "hi");
            }
        }

    }

    public int getItemCount() {
        return questionModelList.size();
    }

    public static class QuestionsViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_questionsCounter;
        private final TextView tv_question;
        private final RadioButton rb_firstAnswer;
        private final RadioButton rb_secondAnswer;
        private final RadioButton rb_thirdAnswer;
        private final RadioButton rb_fourthAnswer;

        public QuestionsViewHolder(View view) {
            super(view);
            // define click listeners and initialize global values.

            tv_question = view.findViewById(R.id.tv_question);
            tv_questionsCounter = view.findViewById(R.id.tv_questionCounter);
            rb_firstAnswer = view.findViewById(R.id.rb_firstAnswer);
            rb_secondAnswer = view.findViewById(R.id.rb_secondAnswer);
            rb_thirdAnswer = view.findViewById(R.id.rb_thirdAnswer);
            rb_fourthAnswer = view.findViewById(R.id.rb_fourthAnswer);

        }

        public TextView getTv_question() {
            return tv_question;
        }

        public TextView getTv_questionsCounter() {
            return tv_questionsCounter;
        }

        public RadioButton getRb_firstAnswer() {
            return rb_firstAnswer;
        }

        public RadioButton getRb_secondAnswer() {
            return rb_secondAnswer;
        }

        public RadioButton getRb_thirdAnswer() {
            return rb_thirdAnswer;
        }

        public RadioButton getRb_fourthAnswer() {
            return rb_fourthAnswer;
        }
    }
}
