package com.cooksys.quiz_api.services;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;

public interface QuizService {

	List<QuizResponseDto> getAllQuiz();

	QuizResponseDto postQuiz(QuizRequestDto quizRequestDto);

	QuizResponseDto deleteQuiz(Long id);

	QuizResponseDto renameQuiz(Long id, String newname);

	QuestionResponseDto randomQ(Long id);

	QuizResponseDto newQuiz(Long id, QuestionRequestDto questionRequestDto);

	QuestionResponseDto deleteQuest(Long id, Long questionID);

	
 



}
