package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.beans.QuizzBean;
import com.quizz.database.beans.ResponseBean;
import com.quizz.database.beans.ThemeBean;
import com.quizz.database.beans.UserBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.datas.Visibility;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.Quizz;
import com.quizz.database.modeles.Response;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Theme;
import com.quizz.database.repository.QuestionRepository;
import com.quizz.database.repository.QuizzRepository;
import com.quizz.database.repository.UserRepository;
import com.quizz.database.services.QuizzService;


@Slf4j
@Service
public class QuizzServiceImpl implements QuizzService {
    
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private QuizzRepository quizzRepository;
    
    @Override
    public ReturnObject addQuizz(String name, Visibility visibility, Collection<Question> questions){

        log.info("Add quizz [name: " + name + "]");

        ReturnObject object = new ReturnObject();
        
        Quizz quizz = new Quizz(name, visibility, questions);
        // The quizz does not exist
        QuizzBean q = quizz.convertToBean();
        try {
            // Test if name was already used
            if (quizzRepository.findByName(name) != null) {
                log.info("Quizz [name: " + name + "] already exist");
                object.setCode(ReturnCode.ERROR_400);
                return object;
            }
            
            for (Question item : questions) {
                if (!questionRepository.exists(item.getId())){
                    log.info("Question [id: " + item.getId() + "] doesn't exist");
                    object.setCode(ReturnCode.ERROR_150);
                    return object;
                }
            }
            
            // Save method was automatically managed by CrudRepository
            QuizzBean quizzBean = quizzRepository.save(q);
            quizz = new Quizz(quizzBean.getId(), quizzBean.getName(), Visibility.valueOf(quizzBean.getIsVisible()), questions);

            object.setCode(ReturnCode.ERROR_000);
            log.info("Quizz successfully added");
            
        } catch (IllegalArgumentException e) {
            object.setCode(ReturnCode.ERROR_500);
            log.error("Impossible to add Quizz [name: " + name + "], " + ReturnCode.ERROR_500, e);
        } catch (RuntimeException e) {
            object.setCode(ReturnCode.ERROR_200);
            log.error("Impossible to add Quizz [name: " + name + "], " + ReturnCode.ERROR_200, e);
        } catch (Exception e) {
            object.setCode(ReturnCode.ERROR_050);
            log.error("Impossible to add Quizz [name: " + name + "], " + ReturnCode.ERROR_050, e);
        }

        object.setObject(quizz);
        return object;
    }
	
	/**
	 * Return a list of Quizz 
	 * 
	 * @param pseudo : pseudo of an user
	 * @return {@link List<Quizz>}
	 */
	@Override
	public ReturnObject getAllQuizzesByPseudo(String pseudo) {
		log.info("Get all quizzes by pseudo");
		ReturnObject object = new ReturnObject();
		
		try {
			List<Quizz> listQuizzes = new ArrayList<Quizz>();
			UserBean userBean = userRepository.findByQuestionPseudo(pseudo);
			QuestionBean questionBean;
			
			// Recovers Quizz associated with user questions
			for (QuestionBean qBean : new ArrayList<QuestionBean>(userBean.getQuestion())) {
				questionBean = questionRepository.findById(qBean.getId());
				for (QuizzBean quizzBean : new ArrayList<QuizzBean>(questionBean.getQuizzs())) {
					if (!checkIfQuizzInList(listQuizzes, quizzBean.getId())) {
						listQuizzes.add(getQuizzByQuizzBean(quizzBean));
					}
				}
			} 
			if (listQuizzes == null || listQuizzes.size() == 0) {
				object.setCode(ReturnCode.ERROR_050);
			} else {
				object.setObject(listQuizzes);
				object.setCode(ReturnCode.ERROR_000);
			}
		}
		catch(Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("An exception has occured when calling getAllQuizzesByPseudo [pseudo: " + pseudo + "], " + ReturnCode.ERROR_050);
		}
		
		return object;
	}
	
	/**
	 * Return a Quizz by name 
	 * 
	 * @param name : name of a quizz
	 * @return {@link Quizz}
	 */
	@Override
	public ReturnObject getQuizzByName(String name) {
		log.info("Get quizz by name");
		ReturnObject object = new ReturnObject();
		
		try {
			QuizzBean quizzBean = quizzRepository.findByName(name);
			if (quizzBean == null) {
				object.setCode(ReturnCode.ERROR_050);
			} else {
				object.setObject(getQuizzByQuizzBean(quizzBean));
				object.setCode(ReturnCode.ERROR_000);
			}
		}
		catch(Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("An exception has occured when calling getQuizzByName [name: " + name + "], " + ReturnCode.ERROR_050);
		}
		
		return object;
	}
	
	/**
	 * Private method to check if a Quizz is in a list of Quizz (Check by id)
	 * 
	 * @param lQuizzes : a list of Quizz
	 * @param idQuizz : id of a Quizz
	 * @return boolean : true / false if Quizz in the list or not
	 */
	private boolean checkIfQuizzInList(List<Quizz> lQuizzes, Integer idQuizz) {
		for (Quizz qB : lQuizzes) {
			if (qB.getId() == idQuizz) {
				return true;
			} 
		}
		return false;
	}
	
	/**
	 * Private method to convert QuizzBean to Quizz
	 * 
	 * @param QuizzBean bean
	 * @return {@link Quizz}
	 */
	private Quizz getQuizzByQuizzBean(QuizzBean bean) {
		Quizz quizz = new Quizz();
		quizz.setId(bean.getId());
		quizz.setName(bean.getName());
		Visibility vis = null;
		for (Visibility v : Visibility.values()) {
			if (v.getId() == Integer.parseInt(bean.getIsVisible())) {
				vis = v;
			}
		}
		quizz.setIsVisible(vis);
		
		Collection<Question> questions = new ArrayList<Question>();
		if (bean.getQuestions() != null) {
			for (QuestionBean question : bean.getQuestions()) {
				Question q = new Question();
				q.setId(question.getId());
				q.setPseudo(question.getPseudo());
				q.setLabel(question.getLabel());
				q.setExplanation(question.getExplanation());
				
				if (question.getThemes() != null) {
					Collection<Theme> themes = new ArrayList<Theme>();
					for (ThemeBean theme : new ArrayList<ThemeBean>(question.getThemes())) {
						Theme t = new Theme();
						t.setId(theme.getId());
						t.setName(theme.getName());
						themes.add(t);
					}
					q.setThemes(themes);
				}
				
				if (question.getResponses() != null) {
					Collection<Response> responses = new ArrayList<Response>();
					for (ResponseBean response : new ArrayList<ResponseBean>(question.getResponses())) {
						Response r = new Response();
						r.setId(response.getId());
						r.setIsValide(response.getIsValide());
						r.setLabel(response.getLabel());
						responses.add(r);
					}
					q.setResponses(responses);
				}
				
				questions.add(q);
			}
		}
		quizz.setQuestions(questions);
		
		return quizz;
	}
	
	@Override
	public ReturnObject deleteQuizzById(Integer id) {
		log.info("Delete Quiz [id: " + id + "]");
		ReturnObject object = new ReturnObject();
		try {
			quizzRepository.delete(id);
			object.setCode(ReturnCode.ERROR_000);
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_100);
		}
		return object;
	}

}
