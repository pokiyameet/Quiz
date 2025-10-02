package com.example.Quiz.controller;

import com.example.Quiz.dtos.QuestionDTO;
import com.example.Quiz.model.Quiz;
import com.example.Quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService service;

    @PostMapping("/start")
    public Quiz start(@RequestParam String userName, @RequestParam(defaultValue = "5") int count) {
        return service.startQuiz(userName, count);
    }

    @GetMapping("/{quizId}/next")
    public QuestionDTO next(@PathVariable int quizId) {
        return service.getNextQuestion(quizId);
    }

    @PostMapping("/{quizId}/answer")
    public String answer(@PathVariable int quizId,
                         @RequestParam int questionId,
                         @RequestParam String selectedAnswer) {
        return service.submitAnswer(quizId, questionId, selectedAnswer);
    }

    @GetMapping("/{quizId}/finish")
    public String finish(@PathVariable int quizId) {
        return service.finishQuiz(quizId);
    }
}
