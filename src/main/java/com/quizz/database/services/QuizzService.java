package com.quizz.database.services;

import com.quizz.database.datas.Visibility;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import java.util.Collection;

public interface QuizzService {
    
    public ReturnObject addQuizz(String name, Visibility visibility, Collection<Question> questions);

}
