package com.fandm.saad.hackerquiz;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {

    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_MEDIUM = "Medium";
    public static final String DIFFICULTY_HARD = "Hard";

    private String question_statement;
    private String answer_1;
    private String answer_2;
    private String answer_3;
    private String answer_4;
    private String category;
    private String difficulty;
    private int correct_answer;

    public Question(){

    }

    public Question(String question_statement, String answer_1, String answer_2, String answer_3, String answer_4, String category, String difficulty, int correct_answer) {
        this.question_statement = question_statement;
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.answer_4 = answer_4;
        this.category = category;
        this.difficulty = difficulty;
        this.correct_answer = correct_answer;
    }


    protected Question(Parcel in) {
        question_statement = in.readString();
        answer_1 = in.readString();
        answer_2 = in.readString();
        answer_3 = in.readString();
        answer_4 = in.readString();
        category = in.readString();
        difficulty = in.readString();
        correct_answer = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question_statement);
        dest.writeString(answer_1);
        dest.writeString(answer_2);
        dest.writeString(answer_3);
        dest.writeString(answer_4);
        dest.writeString(category);
        dest.writeString(difficulty);
        dest.writeInt(correct_answer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestion_statement() {
        return question_statement;
    }

    public void setQuestion_statement(String question_statement) {
        this.question_statement = question_statement;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAnswer_1() {
        return answer_1;
    }

    public void setAnswer_1(String answer_1) {
        this.answer_1 = answer_1;
    }

    public String getAnswer_2() {
        return answer_2;
    }

    public void setAnswer_2(String answer_2) {
        this.answer_2 = answer_2;
    }

    public String getAnswer_3() {
        return answer_3;
    }

    public void setAnswer_3(String answer_3) {
        this.answer_3 = answer_3;
    }

    public String getAnswer_4() {
        return answer_4;
    }

    public void setAnswer_4(String answer_4) {
        this.answer_4 = answer_4;
    }

    public int getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(int correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public static String[] getAllDifficultyLevel(){
        return new String[] {DIFFICULTY_EASY, DIFFICULTY_MEDIUM, DIFFICULTY_HARD};
    }
}
