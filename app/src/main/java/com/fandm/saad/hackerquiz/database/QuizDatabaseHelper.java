package com.fandm.saad.hackerquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
                UserTable.COLUMN_USER_PHONE_ID + " TEXT, " +
                UserTable.COLUMN_USER_PYTHON_EASY_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_PYTHON_MED_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_PYTHON_HARD_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_JAVA_EASY_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_JAVA_MED_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_JAVA_HARD_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_CPP_EASY_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_CPP_MED_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_CPP_HARD_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_OOP_EASY_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_OOP_MED_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_OOP_HARD_SCORE + " TEXT" + ")";

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
                QuestionsTable.COLUMN_CORRECT_ANSWER + " INTEGER" + ")";

        //executes the above SQL statement
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        fillQuestionsTable();
    }

    //add a new user to the user table
    public void add_new_user(User user){

        this.db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserTable.COLUMN_USER_FULL_NAME, user.getFull_name());
        cv.put(UserTable.COLUMN_USER_PHONE_ID, user.getAndroid_device_id());

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
    public boolean user_already_exists(String android_device_id){
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{android_device_id};
        String query = "SELECT * FROM " + UserTable.TABLE_NAME + " WHERE " + UserTable.COLUMN_USER_PHONE_ID + " = ?";

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
        //python easy
        Question q1 = new Question("What is a correct syntax to output \"Hello World\" in Python?",
                "echo(\"Hello World\")", "touch(\"Hello World\")", "print(\"Hello World\")", "std:out(\"Hello World\")",
                "python", Question.DIFFICULTY_EASY,3);
        addQuestion(q1);

        Question q2 = new Question("How do you create a variable with the floating number 2.8?",
                "x = floating_number(2.8)", "x = 2.8", "x = \"2.8\"", "All of the above",
                "python", Question.DIFFICULTY_EASY,2);
        addQuestion(q2);

        Question q3 = new Question("What is a correct syntax to return the first character in a string?",
                "x = sub(\"HelloWorld\")", "x = \"HelloWorld\"[0]", "sub(\"HelloWorld\",0,1)", "\"HelloWorld\"[-1]",
                "python", Question.DIFFICULTY_EASY,2);
        addQuestion(q3);

        Question q4 = new Question("Which operator can be used to compare two values?",
                "<>", "><", "==", "=",
                "python", Question.DIFFICULTY_EASY, 3);
        addQuestion(q4);


        //python medium
        Question q5 = new Question("Which of the following are objects of built-in type that are mutable?",
                "Lists", "Strings", "Tuples", "User Input",
                "python", Question.DIFFICULTY_MEDIUM, 1);
        addQuestion(q5);

        Question q6 = new Question("Which of the following is raised when a generated error does not fall into any category?",
                "RuntimeError", "SystemError", "NotImplementedError", "TypeError",
                "python", Question.DIFFICULTY_MEDIUM, 1);
        addQuestion(q6);

        Question q7 = new Question("Which of the following is the Base class for all errors that occur for numeric calculation?",
                "StandardError", "ZeroDivisionError", "FloatingPointError", "ArithmeticError",
                "python", Question.DIFFICULTY_MEDIUM, 4);
        addQuestion(q7);

        Question q8 = new Question("Which set method will keep only the items that are present in both sets?",
                "intersection_update() ", "symmetric_difference_update()", "discard()", "issubset()",
                "python", Question.DIFFICULTY_MEDIUM, 1);
        addQuestion(q8);

        //python hard
        Question q9 = new Question("Select the correct code to create a button under a parent window with command processButton?",
                "Button(set.text= ''Hello'' )", "Button(window ,text= ''Ok'' ,fg= ''black '')", "Button(window ,text= ''Hello'' ,command=processButton)", "Button(text= ''hello'' ,command=processButton)",
                "python", Question.DIFFICULTY_HARD, 3);
        addQuestion(q9);

        Question q10 = new Question("Create a class named Car, which will inherit the properties and methods from the Automobile class?",
                "class Automobile(Car):", "class Car(Automobile):", "Automobile Class(Car):", "class Car(inherit=Automobile)",
                "python", Question.DIFFICULTY_HARD, 2);
        addQuestion(q10);

        Question q11 = new Question("Which of the following commands would return the 7 first characters of the file in variable f?",
                "f = open(\"myFile.txt\", \"r\").readLine(7)", "f = open(\"myFile.txt\", \"r\").read(7)", "f = open(\"myFile.txt\", \"r\").reachCharArray(7)", "f = open(\"myFile.txt\", \"r\")[7]",
                "python", Question.DIFFICULTY_HARD, 2);
        addQuestion(q11);

        Question q12 = new Question("What does the following function return: \n x = lambda a : a + 10?",
                "Loop over argument a 10 times", "Take a negative a argument and convert it to a positive integer", "Multiply argument a by 2, and add 10 to the result.", "Add 10 to argument a, and return the result",
                "python", Question.DIFFICULTY_HARD, 4);
        addQuestion(q12);

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

    //get full user information provided their phone id
    public User getFullUserInformation(String android_id){
        User user = new User();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{android_id};
        String query = "SELECT * FROM " + UserTable.TABLE_NAME + " WHERE " + UserTable.COLUMN_USER_PHONE_ID + " = ?";
        Cursor c = db.rawQuery(query,selectionArgs);

        if (c.moveToFirst()) {
            user.setFull_name((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_FULL_NAME))));
            user.setAndroid_device_id((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_PHONE_ID))));

            user.setPython_score_easy((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_PYTHON_EASY_SCORE))));
            user.setPython_score_medium((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_PYTHON_MED_SCORE))));
            user.setPython_score_hard((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_PYTHON_HARD_SCORE))));

            user.setJava_score_easy((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_JAVA_EASY_SCORE))));
            user.setJava_score_medium((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_JAVA_MED_SCORE))));
            user.setJava_score_hard((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_JAVA_HARD_SCORE))));

            user.setCpp_score_easy((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_CPP_EASY_SCORE))));
            user.setCpp_score_medium((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_CPP_MED_SCORE))));
            user.setCpp_score_hard((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_CPP_HARD_SCORE))));

            user.setOop_score_easy((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_OOP_EASY_SCORE))));
            user.setOop_score_medium((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_OOP_MED_SCORE))));
            user.setOop_score_hard((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_OOP_HARD_SCORE))));
        }
        c.close();
        return user;
    }


    public void updateUserScore(User user){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserTable.COLUMN_USER_FULL_NAME, user.getFull_name());
        cv.put(UserTable.COLUMN_USER_PHONE_ID, user.getAndroid_device_id());
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

        String whereClause = UserTable.COLUMN_USER_PHONE_ID +"='";
        database.update(UserTable.TABLE_NAME, cv, whereClause+user.getAndroid_device_id()+"'", null);
        database.close();
    }

}
