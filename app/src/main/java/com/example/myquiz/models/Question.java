package com.example.myquiz.models;

public class Question {
    String question;
    String imageUrlQuestion;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    String correctAnswer;

    public Question(String question, String imageUrlQuestion, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.question = question;
        this.imageUrlQuestion = imageUrlQuestion;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getImageUrlQuestion() {
        return imageUrlQuestion;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
