package com.fandm.saad.hackerquiz;

import android.provider.BaseColumns;
public final class QuizContract {

    //this makes sure to use this class as a container only
    private QuizContract(){}

    //class for each table
    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question_statement";
        public static final String COLUMN_ANSWER_1 = "answer_1";
        public static final String COLUMN_ANSWER_2 = "answer_2";
        public static final String COLUMN_ANSWER_3 = "answer_3";
        public static final String COLUMN_ANSWER_4 = "answer_4";
        public static final String COLUMN_CORRECT_ANSWER = "correct_answer";
    }
}