package com.cooksys.quiz_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

	private final QuizService quizService;

	@GetMapping
	public List<QuizResponseDto> getAllQuiz() {
		return quizService.getAllQuiz();
	}

	@PostMapping
	public QuizResponseDto postQuiz(@RequestBody QuizRequestDto quizRequestDto) {
		return quizService.postQuiz(quizRequestDto);
	}

	@DeleteMapping("/{id}")
	public QuizResponseDto deleteQuiz(@PathVariable Long id) {
		return quizService.deleteQuiz(id);
	}

	@PatchMapping("/{id}/rename/{newname}")
	public QuizResponseDto renameQuiz(@PathVariable Long id, @PathVariable String newname) {
		return quizService.renameQuiz(id, newname);
	}

	@GetMapping("/{id}/random")
	public QuestionResponseDto randomQ(@PathVariable Long id) {
		return quizService.randomQ(id);
	}

	@PatchMapping("{id}/add")
	public QuizResponseDto newQuiz(@PathVariable Long id, @RequestBody QuestionRequestDto questionRequestDto) {
		return quizService.newQuiz(id, questionRequestDto);
	}

	@DeleteMapping("/{id}/delete/{questionID}")
	public QuestionResponseDto deleteQuest(@PathVariable Long id, @PathVariable Long questionID) {
		return quizService.deleteQuest(id, questionID);
	}
}