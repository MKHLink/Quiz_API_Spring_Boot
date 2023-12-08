package com.cooksys.quiz_api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.mappers.AnswerMapper;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

	private final QuizRepository quizRepository;
	private final QuizMapper quizMapper;

	private final QuestionRepository questionRepository;
	private final QuestionMapper questionMapper;

	private final AnswerRepository answerRepository;
	private final AnswerMapper answerMapper;

	@Override
	public List<QuizResponseDto> getAllQuiz() {
		List<Quiz> quiz = quizRepository.findAll();
		List<QuizResponseDto> quizesRetured = new ArrayList<>();

		quizesRetured.addAll(quizMapper.entitiesToDtos(quiz));
		return quizesRetured;
	}

	@Override
	public QuizResponseDto postQuiz(QuizRequestDto quizRequestDto) {
		Quiz quiz = quizMapper.requestDtoToEntity(quizRequestDto);
		quizRepository.saveAndFlush(quiz);
		List<Question> questions = quiz.getQuestions();
		for (Question q : questions) {
			q.setQuiz(quiz);
			questionRepository.save(q);
			List<Answer> answer = new ArrayList<>();
			answer.addAll(q.getAnswers());
			for (Answer a : answer) {
				a.setQuestion(q);
				answerRepository.save(a);
			}
		}
		return quizMapper.entityToDto(quiz);

	}

	@Override
	public QuizResponseDto deleteQuiz(Long id) {
		Quiz quiz = quizRepository.getById(id);

		quizRepository.delete(quiz);
		return quizMapper.entityToDto(quiz);
	}

	@Override
	public QuizResponseDto renameQuiz(Long id, String newname) {
		Quiz quiz = quizRepository.getById(id);
		quiz.setName(newname);
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quiz));
	}

	@Override
	public QuestionResponseDto randomQ(Long id) {
		Quiz quiz = quizRepository.getById(id);
		List<Question> q = new ArrayList<>();
		q.addAll(quiz.getQuestions());
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(q.size());
		return questionMapper.entityToDto(q.get(index));
	}

	@Override
	public QuizResponseDto newQuiz(Long id, QuestionRequestDto questionRequestDto) {
		Quiz quiz = quizRepository.getById(id);
		Question quest = questionMapper.questionDtoToEntity(questionRequestDto);
		quiz.getQuestions().add(quest);
		QuizResponseDto qz = quizMapper.entityToDto(quizRepository.saveAndFlush(quiz));
		quest.setQuiz(quiz);
		questionRepository.save(quest);
		List<Answer> a = new ArrayList<>();
		a.addAll(quest.getAnswers());
		for (Answer x : a) {
			x.setQuestion(quest);
			answerRepository.save(x);
		}
		return quizMapper.entityToDto(quizRepository.getById(id));
	}

	@Override
	public QuestionResponseDto deleteQuest(Long id, Long questionID) {
		Quiz quiz = quizRepository.getById(id);
		System.out.println(id);
		QuestionResponseDto qdto = null;
		List<Question> quest = new ArrayList<>();
		quest.addAll(quiz.getQuestions());
		for (Question q : quest) {
			if (q.getId() == questionID) {
				quiz.getQuestions().remove(q);
				questionRepository.delete(q);
				quizRepository.save(quiz);
				qdto = questionMapper.entityToDto(q);
				break;
			}
		}
		return qdto;
	}

}
