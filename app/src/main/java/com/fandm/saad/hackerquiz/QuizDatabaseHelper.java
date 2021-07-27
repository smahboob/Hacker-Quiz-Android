package com.fandm.saad.hackerquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import com.fandm.saad.hackerquiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HackerQuiz.db";
    private static final int DATABASE_VERSION = 3;
    private SQLiteDatabase db;

    //constructor
    public QuizDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //on create is called when the first time u call the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

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
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    //change database version or uninstall if u update database questions
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    //this creates new question objects and adds them to the database
    private void fillQuestionsTable() {
        Question q1 = new Question("What is a correct syntax to output \"Hello World\" in Python?",
                "echo(\"Hello World\")", "touch(\"Hello World\")", "print(\"Hello World\")", "std:out(\"Hello World\")",
                "python", "easy",3);
        addQuestion(q1);

        Question q2 = new Question("What is a correct syntax to output \"Hello World\" in Java?",
                "System.print(\"Hello World\")", "System.out.println(\"Hello World\")", "print(\"Hello World\")", "System.out.write(\"Hello World\")",
                "java", "easy", 2);
        addQuestion(q2);


        Question q3 = new Question("How do you create a variable with the floating number 2.8?",
                "x = floating_number(2.8)", "x = 2.8", "x = \"2.8\"", "All of the above",
                "python", "easy" ,2);
        addQuestion(q3);

        Question q4 = new Question("What is a correct syntax to return the first character in a string?",
                "x = sub(\"HelloWorld\")", "x = \"HelloWorld\"[0]", "sub(\"HelloWorld\",0,1)", "\"HelloWorld\"[-1]",
                "python", "easy",2);
        addQuestion(q4);

        Question q5 = new Question("Which operator can be used to compare two values?",
                "<>", "><", "==", "=", "python", "easy", 3);
        addQuestion(q5);
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
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    //this gets all the questions from the database
    public List<Question> getAllQuestions(String quiz_type) {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
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


    public ArrayList<Question> getQuestionOfType(String category) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{category};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_CATEGORY + " = ?", selectionArgs);
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

    public ArrayList<Question> getQuestionsOfDifficulty(String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);
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

}
