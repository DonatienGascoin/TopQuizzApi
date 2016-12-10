package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.beans.QuizzBean;
import com.quizz.database.beans.ResponseBean;
import com.quizz.database.beans.ThemeBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.datas.Visibility;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.Quizz;
import com.quizz.database.modeles.Response;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Theme;
import com.quizz.database.services.QuestionService;
import com.quizz.database.repository.QuestionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public ReturnObject addQuestion(String pseudo, String label, Collection<Theme> themes, String explanation) {
		log.info("Add question [pseudo: " + pseudo + ", question: " + label + "]");
		ReturnObject object = new ReturnObject();

		try {
			Collection<ThemeBean> themesBean = new ArrayList<ThemeBean>();
			for(Theme t: themes){
				themesBean.add(t.convertToBean());
			}
			
			QuestionBean q = new QuestionBean(pseudo, label, explanation, null, themesBean, null);

			// Save method was automatically managed by CrudRepository
			QuestionBean questionBean = questionRepository.save(q);
			if (questionBean != null) {
				object.setObject(getQuestionByQuestionBean(questionBean));
				object.setCode(ReturnCode.ERROR_000);
				log.info("Question successfully added");
			} else {
				object.setCode(ReturnCode.ERROR_050);
				log.error("Impossible to add Question [pseudo: " + pseudo + ", question: [label:" + label + ",pseudo:"
						+ pseudo + ",explanation:" + explanation + "]], " + ReturnCode.ERROR_050);
			}
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to add Question [pseudo: " + pseudo + ", question: [label:" + label + ",pseudo:"
					+ pseudo + ",explanation:" + explanation + "]], " + ReturnCode.ERROR_200);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to add Question [pseudo: " + pseudo + ", question: [label:" + label + ",pseudo:"
					+ pseudo + ",explanation:" + explanation + "]], " + ReturnCode.ERROR_050);
		}
		return object;
	}

	private Question getQuestionByQuestionBean(QuestionBean bean) {
		Question q = new Question();
		if (bean != null) {
			q.setId(bean.getId());
			q.setLabel(bean.getLabel());
			q.setPseudo(bean.getPseudo());
			q.setExplanation(bean.getExplanation());

			if (CollectionUtils.isNotEmpty(bean.getQuizzs())) {
				Collection<Quizz> quizzs = new ArrayList<Quizz>();
				for (QuizzBean quizzB : bean.getQuizzs()) {
					Quizz qu = new Quizz();
					qu.setId(quizzB.getId());
					qu.setIsVisible(Visibility.valueOf(quizzB.getIsVisible()));
					qu.setName(quizzB.getName());
					
					if(CollectionUtils.isNotEmpty(quizzB.getQuestions())){
						Collection<Question> questions2 = new ArrayList<Question>();
						for (QuestionBean question2: quizzB.getQuestions()) {
							questions2.add(getQuestionByQuestionBean(question2));
						}
						qu.setQuestions(questions2);
					}
					quizzs.add(qu);
				}
				q.setQuizzs(quizzs);
			}

			if (CollectionUtils.isNotEmpty(bean.getResponses())) {
				Collection<Response> responses = new ArrayList<Response>();
				for(ResponseBean responseB: bean.getResponses()){
					responses.add(new Response(responseB.getId(), responseB.getLabel(), responseB.getIsValide(), responseB.getIdQuestion()));
				}
				q.setResponses(responses);
			}

			if (CollectionUtils.isNotEmpty(bean.getThemes())) {
				Collection<Theme> themes = new ArrayList<Theme>();
				for(ThemeBean themeB: bean.getThemes()){
					themes.add(new Theme(themeB.getId(), themeB.getName()));
				}
				q.setThemes(themes);
			}
		}

		return q;
	}
}
