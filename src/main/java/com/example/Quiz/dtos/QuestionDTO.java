package com.example.Quiz.dtos;

public class QuestionDTO {
    private Integer questionId;
    private String text;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public QuestionDTO(Integer questionId, String text, String optionA,
                       String optionB, String optionC, String optionD) {
        this.questionId = questionId;
        this.text = text;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    // Getters
    public Integer getQuestionId() { return questionId; }
    public String getText() { return text; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
}

