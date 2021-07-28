package com.fandm.saad.hackerquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import androidx.annotation.Nullable;
import com.fandm.saad.hackerquiz.database.QuizContract.*;
import com.fandm.saad.hackerquiz.models.Question;
import com.fandm.saad.hackerquiz.models.User;

import java.util.ArrayList;
import java.util.List;

public class QuizDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HackerQuiz.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    //constructor
    public QuizDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //on create is called when the first time u call the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserTable.TABLE_NAME + " ( " +
                UserTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserTable.COLUMN_USER_FULL_NAME + " TEXT, " +
                UserTable.COLUMN_USER_PYTHON_EASY_SCORE + " INTEGER, " +
                UserTable.COLUMN_USER_PYTHON_MED_SCORE + " INTEGER, " +
                UserTable.COLUMN_USER_PYTHON_HARD_SCORE + " INTEGER, " +
                UserTable.COLUMN_USER_JAVA_EASY_SCORE + " INTEGER, " +
                UserTable.COLUMN_USER_JAVA_MED_SCORE + " INTEGER, " +
                UserTable.COLUMN_USER_JAVA_HARD_SCORE + " INTEGER, " +
                UserTable.COLUMN_USER_CPP_EASY_SCORE + " INTEGER, " +
                UserTable.COLUMN_USER_CPP_MED_SCORE + " INTEGER, " +
                UserTable.COLUMN_USER_CPP_HARD_SCORE + " INTEGER, " +
                UserTable.COLUMN_USER_OOP_EASY_SCORE + " INTEGER, " +
                UserTable.COLUMN_USER_OOP_MED_SCORE + " INTEGER, " +
                UserTable.COLUMN_USER_OOP_HARD_SCORE + " INTEGER" +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_1 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_2 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_4 + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY + " TEXT, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CORRECT_ANSWER + " INTEGER" +
                ")";

        //executes the above SQL statement
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        Log.d("TAG", "execute sql create statements.");

        fillQuestionsTable();
    }

    //add a new user to the user table
    public void add_new_user(User user){
        ContentValues cv = new ContentValues();
        cv.put(UserTable.COLUMN_USER_FULL_NAME, user.getFull_name());
        cv.put(UserTable.COLUMN_USER_PHONE_NUMBER, user.getPhone_number());

        cv.put(UserTable.COLUMN_USER_PYTHON_EASY_SCORE, user.getPython_score_easy());
        cv.put(UserTable.COLUMN_USER_PYTHON_MED_SCORE, user.getPython_score_medium());
        cv.put(UserTable.COLUMN_USER_PYTHON_HARD_SCORE, user.getPython_score_hard());

        cv.put(UserTable.COLUMN_USER_JAVA_EASY_SCORE, user.getJava_score_easy());
        cv.put(UserTable.COLUMN_USER_JAVA_MED_SCORE, user.getJava_score_medium());
        cv.put(UserTable.COLUMN_USER_JAVA_HARD_SCORE, user.getJava_score_hard());

        cv.put(UserTable.COLUMN_USER_CPP_EASY_SCORE, user.getCpp_score_easy());
        cv.put(UserTable.COLUMN_USER_CPP_MED_SCORE, user.getCpp_score_medium());
        cv.put(UserTable.COLUMN_USER_CPP_HARD_SCORE, user.getCpp_score_hard());

        cv.put(UserTable.COLUMN_USER_OOP_EASY_SCORE, user.getOop_score_easy());
        cv.put(UserTable.COLUMN_USER_OOP_MED_SCORE, user.getOop_score_medium());
        cv.put(UserTable.COLUMN_USER_OOP_HARD_SCORE, user.getOop_score_hard());

        db.insert(UserTable.TABLE_NAME, null, cv);
    }

    //this would check in the database if this user_id and user_email are already in the system
    public boolean user_already_exists(String phone_number){
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{phone_number};
        String query = "SELECT * FROM " + UserTable.TABLE_NAME + " WHERE " + UserTable.COLUMN_USER_PHONE_NUMBER + " = ?";

        Cursor c = db.rawQuery(query,selectionArgs);

        boolean answer = c.getCount() != 0;
        c.close();
        return answer;
    }

    //change database version or uninstall if u update database questions
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    //this creates new question objects and adds them to the database
    private void fillQuestionsTable() {
        Question q1 = new Question("What is a correct syntax to output \"Hello World\" in Python?",
                "echo(\"Hello World\")", "touch(\"Hello World\")", "print(\"Hello World\")", "std:out(\"Hello World\")",
                "python", Question.DIFFICULTY_EASY,3);
        addQuestion(q1);

        Question q2 = new Question("What is a correct syntax to output \"Hello World\" in Java?",
                "System.print(\"Hello World\")", "System.out.println(\"Hello World\")", "print(\"Hello World\")", "System.out.write(\"Hello World\")",
                "java", Question.DIFFICULTY_EASY, 2);
        addQuestion(q2);


        Question q3 = new Question("How do you create a variable with the floating number 2.8?",
                "x = floating_number(2.8)", "x = 2.8", "x = \"2.8\"", "All of the above",
                "python", Question.DIFFICULTY_EASY,2);
        addQuestion(q3);

        Question q4 = new Question("What is a correct syntax to return the first character in a string?",
                "x = sub(\"HelloWorld\")", "x = \"HelloWorld\"[0]", "sub(\"HelloWorld\",0,1)", "\"HelloWorld\"[-1]",
                "python", Question.DIFFICULTY_EASY,2);
        addQuestion(q4);

        Question q5 = new Question("Which operator can be used to compare two values?",
                "<>", "><", "==", "=",
                "python", Question.DIFFICULTY_EASY, 3);
        addQuestion(q5);

        Question q6 = new Question("Which of the following are objects of built-in type that are mutable?",
                "Lists", "Strings", "Tuples", "User Input",
                "python", Question.DIFFICULTY_MEDIUM, 1);
        addQuestion(q6);
    }

    //this inserts the questions into the database
    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion_statement());
        cv.put(QuestionsTable.COLUMN_ANSWER_1, question.getAnswer_1());
        cv.put(QuestionsTable.COLUMN_ANSWER_2, question.getAnswer_2());
        cv.put(QuestionsTable.COLUMN_ANSWER_3, question.getAnswer_3());
        cv.put(QuestionsTable.COLUMN_ANSWER_4, question.getAnswer_4());
        cv.put(QuestionsTable.COLUMN_CATEGORY, question.getCategory());
        cv.put(QuestionsTable.COLUMN_CORRECT_ANSWER, question.getCorrect_answer());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    //this gets all the questions from the database
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String query = "SELECT * FROM " + QuestionsTable.TABLE_NAME;
        Cursor c = db.rawQuery(query,null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion_statement(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setAnswer_1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_1)));
                question.setAnswer_2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_2)));
                question.setAnswer_3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_3)));
                question.setAnswer_4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_4)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
                question.setCorrect_answer(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CORRECT_ANSWER)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    //questions of certain type only
    public ArrayList<Question> getQuestionOfType(String category) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{category};
        String query = "SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.COLUMN_CATEGORY + " = ?";
        Cursor c = db.rawQuery(query,selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion_statement(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setAnswer_1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_1)));
                question.setAnswer_2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_2)));
                question.setAnswer_3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_3)));
                question.setAnswer_4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_4)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCorrect_answer(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CORRECT_ANSWER)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    //questions of certain difficulty only
    public ArrayList<Question> getQuestionsOfDifficulty(String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        String query = "SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?";
        Cursor c = db.rawQuery(query,selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion_statement(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setAnswer_1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_1)));
                question.setAnswer_2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_2)));
                question.setAnswer_3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_3)));
                question.setAnswer_4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_4)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCorrect_answer(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CORRECT_ANSWER)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    //this method extracts out the questions with the given difficulty and category
    public ArrayList<Question> getQuestionsOfCategoryAndDifficulty(String difficulty, String category) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty,category};
        String query = "SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ? " + " AND " + QuestionsTable.COLUMN_CATEGORY + " = ?";

        Cursor c = db.rawQuery(query,selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion_statement(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setAnswer_1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_1)));
                question.setAnswer_2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_2)));
                question.setAnswer_3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_3)));
                question.setAnswer_4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_4)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCorrect_answer(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CORRECT_ANSWER)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    //get full user information provided their phone number
    public User getFullUserInformation(String phone_number){
        User user = new User();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{phone_number};
        String query = "SELECT * FROM " + UserTable.TABLE_NAME + " WHERE " + UserTable.COLUMN_USER_PHONE_NUMBER + " = ?";
        Cursor c = db.rawQuery(query,selectionArgs);

        if (c.moveToFirst()) {
            user.setFull_name((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_FULL_NAME))));
            user.setPhone_number((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_PHONE_NUMBER))));

            user.setPython_score_easy((c.getInt(c.getColumnIndex(UserTable.COLUMN_USER_PYTHON_EASY_SCORE))));
            user.setPython_score_medium((c.getInt(c.getColumnIndex(UserTable.COLUMN_USER_PYTHON_EASY_SCORE))));
            user.setPython_score_hard((c.getInt(c.getColumnIndex(UserTable.COLUMN_USER_PYTHON_EASY_SCORE))));

            user.setJava_score_easy((c.getInt(c.getColumnIndex(UserTable.COLUMN_USER_JAVA_EASY_SCORE))));
            user.setJava_score_medium((c.getInt(c.getColumnIndex(UserTable.COLUMN_USER_JAVA_MED_SCORE))));
            user.setJava_score_easy((c.getInt(c.getColumnIndex(UserTable.COLUMN_USER_JAVA_HARD_SCORE))));

            user.setCpp_score_easy((c.getInt(c.getColumnIndex(UserTable.COLUMN_USER_CPP_EASY_SCORE))));
            user.setCpp_score_medium((c.getInt(c.getColumnIndex(UserTable.COLUMN_USER_CPP_MED_SCORE))));
            user.setCpp_score_hard((c.getInt(c.getColumnIndex(UserTable.COLUMN_USER_CPP_HARD_SCORE))));

            user.setOop_score_easy((c.getInt(c.getColumnIndex(UserTable.COLUMN_USER_OOP_EASY_SCORE))));
            user.setOop_score_medium((c.getInt(c.getColumnIndex(UserTable.COLUMN_USER_OOP_MED_SCORE))));
            user.setOop_score_hard((c.getInt(c.getColumnIndex(UserTable.COLUMN_USER_OOP_HARD_SCORE))));
        }
        c.close();
        return user;
    }

}
