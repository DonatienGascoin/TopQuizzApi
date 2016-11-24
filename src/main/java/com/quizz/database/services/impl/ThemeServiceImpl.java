package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.ThemeBean;
import com.quizz.database.beans.UserBean;
import com.quizz.database.controllers.ThemeController;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Theme;
import com.quizz.database.repository.ThemeRepository;
import com.quizz.database.services.ThemeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ThemeServiceImpl implements ThemeService {
	
	@Autowired
	private ThemeRepository themeRepository;

	@Override
	public ReturnObject getAllThemes() {
		ReturnObject object = new ReturnObject();
		List<ThemeBean> tmp = new ArrayList<ThemeBean>();
		List<Theme> themeList = new ArrayList<Theme>(); 
		try { 
			tmp = themeRepository.findAll();
			
			for (ThemeBean tB : tmp) {
				Theme th = new Theme();
				th.setId(tB.getId());
				th.setName(tB.getName());
				themeList.add(th);
			}
			
			if (tmp == null || tmp.size() == 0) {
				object.setCode(ReturnCode.ERROR_100);
				log.info("No themes on DB when calling getAllThemes");
			} else {
				object.setCode(ReturnCode.ERROR_000);
				log.info("No problem when calling getAllThemes");
			}
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_700);
		}
		object.setObject(themeList);
		return object;
	}
}
