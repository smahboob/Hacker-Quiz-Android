package com.fandm.saad.hackerquiz.database;

import android.provider.BaseColumns;
public final class QuizContract {

    //this makes sure to use this class as a container only
    private QuizContract(){}

    public static class UserTable implements  BaseColumns{
        public static final String TABLE_NAME = "user_information";
        public static final String COLUMN_USER_FULL_NAME = "full_name";
        public static final String COLUMN_USER_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_USER_PYTHON_EASY_SCORE = "python_easy";
        public static final String COLUMN_USER_PYTHON_MED_SCORE = "python_med";
        public static final String COLUMN_USER_PYTHON_HARD_SCORE = "python_hard";
        public static final String COLUMN_USER_JAVA_EASY_SCORE = "ava_easy";
        public static final String COLUMN_USER_JAVA_MED_SCORE = "java_med";
        public static final String COLUMN_USER_JAVA_HARD_SCORE = "java_hard";
        public static final String COLUMN_USER_CPP_EASY_SCORE = "cpp_easy";
        public static final String COLUMN_USER_CPP_MED_SCORE = "cpp_med";
        public static final String COLUMN_USER_CPP_HARD_SCORE = "cpp_hard";
        public static final String COLUMN_USER_OOP_EASY_SCORE = "oop_easy";
        public static final String COLUMN_USER_OOP_MED_SCORE = "oop_med";
        public static final String COLUMN_USER_OOP_HARD_SCORE = "oop_hard";
    }

    //class for each table
    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question_statement";
        public static final String COLUMN_ANSWER_1 = "answer_1";
        public static final String COLUMN_ANSWER_2 = "answer_2";
        public static final String COLUMN_ANSWER_3 = "answer_3";
        public static final String COLUMN_ANSWER_4 = "answer_4";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_CORRECT_ANSWER = "correct_answer";
    }
}