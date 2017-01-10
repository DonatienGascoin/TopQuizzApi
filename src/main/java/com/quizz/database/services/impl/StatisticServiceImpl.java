package com.quizz.database.services.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.StatisticBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Statistic;
import com.quizz.database.repository.StatisticRepository;
import com.quizz.database.services.StatisticService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatisticServiceImpl implements StatisticService {

	@Autowired
	private StatisticRepository statisticRepository;

	@Override
	public ReturnObject getTenLastScoreForQuizz(String pseudo, Integer quizzId) {
		log.info("Get statistic for  [pseudo: " + pseudo + "quizz Id: " + quizzId + "]");
		ReturnObject object = new ReturnObject();

		try {
			Set<Statistic> statistics = new TreeSet<Statistic>();
			Collection<StatisticBean> statisticBeans = statisticRepository.findTop10ByPseudoAndAndQuizzIdOrderByDateDesc(pseudo, quizzId);
			if (CollectionUtils.isNotEmpty(statisticBeans)) {
				for (StatisticBean statisticBean : statisticBeans) {
					statistics.add(getStatisticToStatisticBean(statisticBean));
				}
				
				object.setObject(statistics);
			}else{
				object.setObject(statistics);
			}
			object.setCode(ReturnCode.ERROR_000);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to get Statistic [pseudo: " + pseudo + ",quizzId: " + quizzId + "], " + ReturnCode.ERROR_050);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to get Statistic [pseudo: " + pseudo + ",quizzId: " + quizzId + "], " + ReturnCode.ERROR_050);
		}
		return object;
	}

	@Override
	public ReturnObject addScoreForQuizz(String pseudo, Integer quizzId, String quizzName, Integer nbRightAnswers, Integer nbQuestions) {
		log.info("Add statistic for  [pseudo: " + pseudo + "quizz Id: " + quizzId + "]");
		
		ReturnObject object = new ReturnObject();


		// The theme does not exist
		try {
			StatisticBean sb = new StatisticBean(pseudo, quizzId, quizzName, nbRightAnswers, nbQuestions, new Date());

			// Save method was automatically managed by CrudRepository
			StatisticBean sbRet = statisticRepository.save(sb);
			if(sbRet != null){
				object.setObject(getStatisticToStatisticBean(sbRet));
				object.setCode(ReturnCode.ERROR_000);
				log.info("Statistic successfully added");
			}else{
				object.setCode(ReturnCode.ERROR_050);
			}
			
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to add Statistic [pseudo: " + pseudo + ",quizzId: " + quizzId + "], " + ReturnCode.ERROR_500, e);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to add Statistic [pseudo: " + pseudo + ",quizzId: " + quizzId + "], " + ReturnCode.ERROR_200, e);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to add Statistic [pseudo: " + pseudo + ",quizzId: " + quizzId + "], " + ReturnCode.ERROR_050, e);
		}
		return object;
	}

	private Statistic getStatisticToStatisticBean(StatisticBean sb) {
		Statistic stat = new Statistic();
		if(sb != null){
			stat.setId(sb.getId());
			stat.setNbQuestions(sb.getNbQuestions());
			stat.setPseudo(sb.getPseudo());
			stat.setQuizzId(sb.getQuizzId());
			stat.setNbRightAnswers(sb.getNbRightAnswers());
			stat.setDate(sb.getDate());
			stat.setQuizzName(sb.getQuizzName());
		}
		return stat;
	}
}
