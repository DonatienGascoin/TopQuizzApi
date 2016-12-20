package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.datas.Visibility;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.User;
import com.quizz.database.services.AppService;
import com.quizz.database.services.QuestionService;
import com.quizz.database.services.QuizzService;
import com.quizz.database.services.ResponseService;
import com.quizz.database.services.StatisticService;
import com.quizz.database.services.ThemeService;
import com.quizz.database.services.UserService;

@Service
public class AppServiceImpl implements AppService {	
    
    private static final String SEPARATOR_QUIZZ = ",";
    
	private static final int LITTLESTRINGLIMIT = 50;
	
	private static final int BIGSTRINGLIMIT = 50;
	
	private static final String SEPARATOR = "|";
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private ThemeService themeService;
    
    @Autowired
    private QuizzService quizzService;
    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private ResponseService responseService;
    
    @Autowired
    private StatisticService statisticService;

    @Override
    public ReturnObject getUser(String pseudo) {
            return userService.getUser(pseudo);
    }

    @Override
    public ReturnObject addUser(String pseudo, String mail, String password) {
            return userService.addUser(pseudo, mail, password);
    }

    @Override
	public ReturnObject editUser(String pseudo, String mail, String password, Boolean active, Collection<User> friends, Collection<Question> questions) {
		return userService.editUser(pseudo, mail, password, active, friends, questions);
	}

	@Override
	public ReturnObject deleteUser(String pseudo) {
		return userService.deleteUser(pseudo);
	}

	@Override
	public ReturnObject getUserByMail(String mail) {
		return userService.getUserByMail(mail);
	}

	@Override
	public ReturnObject getAllThemesByUser(String pseudo) {
		ReturnObject obj = userService.getUser(pseudo);
		if (obj.getObject() != null) {
			return themeService.getAllThemesByUser((Collection<Question>) ((User) obj.getObject()).getQuestions());
		}
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject changePassword(String password, String email) {
		return userService.changePassword(password, email);
	}

	@Override
	public ReturnObject checkUserCredentials(String pseudo, String password) {
		return userService.checkUserCredentials(pseudo, password);
	}

	@Override
	public ReturnObject getAllQuizzesByPseudo(String pseudo) {
		return quizzService.getAllQuizzesByPseudo(pseudo);
	}

	@Override
	public ReturnObject getQuizzByName(String name) {
		return quizzService.getQuizzByName(name);
	}

	@Override
	public ReturnObject activeUser(String mail) {
		return userService.activeUser(mail);
	}
	
	public ReturnObject getAllThemes() {
		return themeService.getAllThemes();
	}
	
	@Override
    public ReturnObject addTheme(String name) {
            return themeService.addTheme(name);
    }

    @Override
    public ReturnObject deleteTheme(int id) {
            return themeService.deleteTheme(id);
    }

    @Override
    public ReturnObject getThemeByName(String name) {
        return themeService.getThemeByName(name);
    }


    @Override
    public Question getQuestionByQuestionBean(QuestionBean bean){
        return questionService.getQuestionByQuestionBean(bean);
    }
    
    @Override
    public ReturnObject addQuizz(String name, Visibility visibility, String questions){
        
        String[] split = StringUtils.split(questions, SEPARATOR_QUIZZ);
        Collection<Question> questionList = new ArrayList<Question>();
        
        for (String split1 : split) {
            ReturnObject obj = questionService.findById(Integer.parseInt(split1));
            QuestionBean test = (QuestionBean) obj.getObject();
            Question test2 = getQuestionByQuestionBean(test);
            questionList.add(test2);
        }
        return quizzService.addQuizz(name,visibility, questionList);
    }

	@Override
	public ReturnObject deleteQuizzById(Integer id) {
		return quizzService.deleteQuizzById(id);
	}
		
	@Override
	public ReturnObject addTmpResponse(String number, String pseudo, String label, Boolean isValide) {
		ReturnObject obj = new ReturnObject();
		if(StringUtils.isEmpty(number) || StringUtils.isEmpty(pseudo) || StringUtils.isEmpty(label)){
			obj.setCode(ReturnCode.ERROR_150);
			return obj;
		}
		if(label.length() > LITTLESTRINGLIMIT || pseudo.length() > LITTLESTRINGLIMIT ){
			obj.setCode(ReturnCode.ERROR_500);
		}
		obj = userService.getUser(pseudo);
		if (obj.getObject() != null) {
			return responseService.addTmpResponse(pseudo + SEPARATOR + number, label, isValide);
		}
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject addQuestion(String pseudo, String label, String themes, String explanation) {
		ReturnObject obj = new ReturnObject();
		if (StringUtils.isEmpty(pseudo) || StringUtils.isEmpty(label) || StringUtils.isEmpty(themes) || StringUtils.isEmpty(explanation)) {
			obj.setCode(ReturnCode.ERROR_150);
			return obj;
		}
		if(explanation.length() > BIGSTRINGLIMIT || label.length() > LITTLESTRINGLIMIT){
			obj.setCode(ReturnCode.ERROR_500);
		}
		obj = userService.getUser(pseudo);
		if (obj.getObject() != null) {
			//Add question
			obj = questionService.addQuestion(pseudo, label, explanation);
			 
			// Add themes
			if(ReturnCode.ERROR_000.equals(obj.getCode()) && obj.getObject() != null){
				String[] split = StringUtils.split(themes, SEPARATOR);
				for(String str : split){
					themeService.addThemeWithIdQuestion(str, ((Question)obj.getObject()).getId());
				}
			}
			
			// Add responses
			if(ReturnCode.ERROR_000.equals(obj.getCode()) && obj.getObject() != null){
				responseService.linkTmpResponse(((Question)obj.getObject()).getId(), pseudo);
			}
		}
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject getTenLastScoreForQuizz(String pseudo, Integer quizzId) {
		ReturnObject obj = userService.getUser(pseudo);
		if (obj.getObject() != null) {
			return statisticService.getTenLastScoreForQuizz(pseudo, quizzId);
		}
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject addScoreForQuizz(String pseudo, Integer quizzId, String quizzName, Integer nbRightAnswers, Integer nbQuestions) {
		ReturnObject obj = userService.getUser(pseudo);
		if (obj.getObject() != null) {
			return statisticService.addScoreForQuizz(pseudo, quizzId, quizzName, nbRightAnswers, nbQuestions);
		}
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}
}
