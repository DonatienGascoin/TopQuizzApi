package com.quizz.database.services.impl;

import com.quizz.database.modeles.ReturnObject;
import org.springframework.stereotype.Service;
import com.quizz.database.datas.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;

import com.quizz.database.services.QuizzService;
import com.quizz.database.modeles.Quizz;
import com.quizz.database.beans.QuizzBean;
import com.quizz.database.repository.QuizzRepository;
import com.quizz.database.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import com.quizz.database.datas.Visibility;
import com.quizz.database.modeles.Question;
import java.util.Collection;

@Slf4j
@Service
public class QuizzServiceImpl implements QuizzService {
    
    @Autowired
    private QuizzRepository quizzRepository;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Override
    public ReturnObject addQuizz(String name, Visibility visibility, Collection<Question> questions){

        log.info("Add quizz [name: " + name + "]");

        ReturnObject object = new ReturnObject();
        
        Quizz quizz = new Quizz(name, visibility.name(), questions);
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
            quizz = new Quizz(quizzBean.getId(), quizzBean.getName(), quizzBean.getIsVisible(), questions);

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

}
