package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.ThemeBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Theme;
import com.quizz.database.repository.ThemeRepository;
import com.quizz.database.services.ThemeService;

@Slf4j
@Service
public class ThemeServiceImpl implements ThemeService {

	@Autowired
	private ThemeRepository themeRepository;
	
	@Override
	public ReturnObject getAllThemes() {
		log.info("Get all User");
		ReturnObject object = new ReturnObject();
		List<Theme> themes = new ArrayList<Theme>();
		Boolean isInList;
		try {
			List<ThemeBean> findAll = themeRepository.findAll();
			for (ThemeBean themeBean : findAll) {
				Theme theme = new Theme();

				theme.setId(themeBean.getId());
				theme.setName(themeBean.getName());
				isInList = false;
				for(Theme t : themes) {
					if(t.getName().equals(theme.getName())) {
						isInList = true;
					}
				}
				if(!isInList) {
					themes.add(theme);
				}
			}
			object.setCode(ReturnCode.ERROR_000);
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to get all User " + ReturnCode.ERROR_500);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to get all User " + ReturnCode.ERROR_200);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to get all User " + ReturnCode.ERROR_050);
		}
		object.setObject(themes);

		return object;
	}
}
