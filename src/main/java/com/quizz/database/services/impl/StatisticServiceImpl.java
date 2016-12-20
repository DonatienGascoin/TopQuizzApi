package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.StatisticBean;
import com.quizz.database.beans.ThemeBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Statistic;
import com.quizz.database.modeles.Theme;
import com.quizz.database.repository.StatisticRepository;
import com.quizz.database.services.StatisticService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatisticServiceImpl implements StatisticService {

	@Autowired
	private StatisticRepository statisticRepository;

	private Statistic getStatisticToStatisticBean(StatisticBean sb) {
		log.info("Convert bean to statistic");
		Statistic stat = new Statistic();
		stat.setId(sb.getId());
		stat.setNbQuestions(sb.getNbQuestions());
		stat.setPseudo(sb.getPseudo());
		stat.setQuizzId(sb.getQuizzId());
		stat.setNbRightAnswers(sb.getNbRightAnswers());

		return stat;
	}
}
