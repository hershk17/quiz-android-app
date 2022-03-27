package com.example.quizandroidapp;

public class Question {
    private Integer description;
    private Boolean answer;
    private Integer colour;

    public Question(int description, Boolean answer) {
        this.description = description;
        this.answer = answer;
    }

    public Integer getDescription() {
        return description;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public Integer getColour() {
        return colour;
    }

    public void setColour(Integer colour) {
        this.colour = colour;
    }
}
