package com.example.Quiz.service;

import com.example.Quiz.dtos.QuestionDTO;
import com.example.Quiz.model.Question;
import com.example.Quiz.model.Quiz;
import com.example.Quiz.repository.QuestionRepository;
import com.example.Quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private QuestionRepository questionRepo;


    private Map<Integer, Queue<Question>> quizQuestionMap = new HashMap<>();


    public Quiz startQuiz(String userName, int count) {
        List<Question> questions = questionRepo.findRandomQuestions(count);

        Quiz quiz = new Quiz();
        quiz.setUserName(userName);
        quiz.setTotalQuestions(questions.size());
        quiz.setScore(0);
        quiz.setPassed(false);

        quiz = quizRepo.save(quiz);

        quizQuestionMap.put(quiz.getQuizId(), new LinkedList<>(questions));

        return quiz;
    }


    public QuestionDTO getNextQuestion(int quizId) {
        Queue<Question> queue = quizQuestionMap.get(quizId);
        if (queue == null || queue.isEmpty()) return null;

        Question q = queue.peek();
        return new QuestionDTO(q.getQuestionId(), q.getText(), q.getOptionA(), q.getOptionB(), q.getOptionC(), q.getOptionD());
    }

    public String submitAnswer(int quizId, int questionId, String selectedAnswer) {
        Queue<Question> queue = quizQuestionMap.get(quizId);
        if (queue == null || queue.isEmpty()) return "No more questions";

        Question current = queue.poll();
        if (current.getQuestionId() != questionId) {
            return "Question mismatch!";
        }

        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));

        boolean correct = current.getCorrectAnswer().equalsIgnoreCase(selectedAnswer);
        if (correct) {
            quiz.setScore(quiz.getScore() + 1);
        }

        quizRepo.save(quiz);

        return correct ? " Correct!" : " Wrong!";
    }

    public String finishQuiz(int quizId) {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.setPassed(quiz.getScore() >= quiz.getTotalQuestions() / 2);
        quizRepo.save(quiz);

        return "User: " + quiz.getUserName() +
                " | Score: " + quiz.getScore() + "/" + quiz.getTotalQuestions() +
                " | Result: " + (quiz.isPassed() ? "PASS ✅" : "FAIL ");
    }
}
