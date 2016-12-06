package com.quizz.database.services.impl;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.ThemeBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.Question;
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
	public ReturnObject getAllThemesByUser(Collection<Question> questions) {
		ReturnObject object = new ReturnObject();
		try {
			Set<Theme> listTheme = new TreeSet<Theme>();
			if (CollectionUtils.isNotEmpty(questions)) {
				for (Question question : questions) {
					Collection<ThemeBean> tmp = themeRepository.findByIdQuestion(question.getId());
					for (ThemeBean themeBean : tmp) {
						listTheme.add(getThemeByThemeBean(themeBean));
					}
				}
				object.setObject(listTheme);
				object.setCode(ReturnCode.ERROR_000);
			} else {
				object.setCode(ReturnCode.ERROR_100);
				log.info("This user dont have theme");

			}
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_700);
		}

		return object;
	}

	/**
	 * Convert ThemeBean to Theme
	 * 
	 * @param bean
	 * @return {@link Theme}
	 */
	private Theme getThemeByThemeBean(ThemeBean bean) {
		Theme theme = null;
		if (bean != null) {
			theme = new Theme();
			theme.setName(bean.getName());
			theme.setId(bean.getId());
			theme.setIdQuestion(bean.getIdQuestion());
		}
		return theme;
	}
}
