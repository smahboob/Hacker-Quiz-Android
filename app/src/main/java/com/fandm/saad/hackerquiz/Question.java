package com.fandm.saad.hackerquiz;

public class Question {

    private String question_statement;
    private String answer_1;
    private String answer_2;
    private String answer_3;
    private String answer_4;
    private int correct_answer;

    public Question(){

    }

    public Question(String question_statement, String answer_1, String answer_2, String answer_3, String answer_4, int correct_answer) {
        this.question_statement = question_statement;
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.answer_4 = answer_4;
        this.correct_answer = correct_answer;
    }

    public String getQuestion_statement() {
        return question_statement;
    }

    public void setQuestion_statement(String question_statement) {
        this.question_statement = question_statement;
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
}
