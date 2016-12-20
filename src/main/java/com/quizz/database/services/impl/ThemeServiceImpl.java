package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

	/**
	 * Return name, id, question (id)
	 * 
	 * @return {@link Theme}
	 */
	@Override
	public ReturnObject getThemeByName(String name) {
		log.info("Get Theme [name: " + name + "]");
		ReturnObject object = new ReturnObject();
		Theme theme = null;
		try {
			ThemeBean findByName = themeRepository.findByName(name);
			theme = getThemeByThemeBean(findByName);
			if (theme == null) {
				object.setCode(ReturnCode.ERROR_100);
			} else {
				object.setCode(ReturnCode.ERROR_000);
			}
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to get Theme [name: " + name + "], " + ReturnCode.ERROR_500, e);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to get Theme [name: " + name + "], " + ReturnCode.ERROR_200, e);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to get Theme [name: " + name + "], " + ReturnCode.ERROR_050, e);
		}
		object.setObject(theme);
		return object;
	}

	@Override
	public ReturnObject getAllThemes() {
		log.info("Get all Themes");
		ReturnObject object = new ReturnObject();
		List<Theme> themes = new ArrayList<Theme>();
		Boolean isInList;
		try {
			List<ThemeBean> findAll = themeRepository.findAll();
			for (ThemeBean themeBean : findAll) {
				Theme theme = new Theme();

				theme.setId(themeBean.getId());
				theme.setName(themeBean.getName());
				// To delete duplicate themes
				isInList = false;
				for (Theme t : themes) {
					if (t.getName().equals(theme.getName())) {
						isInList = true;
					}
				}
				if (!isInList) {
					themes.add(theme);
				}
			}
			object.setCode(ReturnCode.ERROR_000);
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to get all Themes " + ReturnCode.ERROR_500);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to get all Themes " + ReturnCode.ERROR_200);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to get all Themes " + ReturnCode.ERROR_050);
		}
		object.setObject(themes);

		return object;
	}

	public ReturnObject addTheme(String name) {

		log.info("Add theme [name: " + name + "]");

		ReturnObject object = new ReturnObject();

		Theme theme = new Theme();
		theme.setName(name);

		// The theme does not exist
		ThemeBean t = theme.convertToBean();
		try {
			// Test if name was already used
			if (themeRepository.findByName(name) != null) {
				log.info("Theme [name: " + name + "] already exist");
				object.setCode(ReturnCode.ERROR_400);
				return object;
			}

			// Save method was automatically managed by CrudRepository
			ThemeBean themeBean = themeRepository.save(t);
			theme = getThemeByThemeBean(themeBean);

			object.setCode(ReturnCode.ERROR_000);
			log.info("Theme successfully added");
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to add Theme [name: " + name + "], " + ReturnCode.ERROR_500, e);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to add Theme [name: " + name + "], " + ReturnCode.ERROR_200, e);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to add Theme [name: " + name + "], " + ReturnCode.ERROR_050, e);
		}
		object.setObject(theme);
		return object;
	}

	public ReturnObject addThemeWithIdQuestion(String name, int idQuestion) {
		log.info("Add theme [name: " + name + " idQuestion : " + idQuestion + "]");

		ReturnObject object = new ReturnObject();

		Theme theme = new Theme();
		theme.setName(name);
		theme.setIdQuestion(idQuestion);

		// The theme does not exist
		ThemeBean t = theme.convertToBean();
		try {
			// Save method was automatically managed by CrudRepository
			ThemeBean themeBean = themeRepository.save(t);
			theme = new Theme(themeBean.getId(), themeBean.getName(), themeBean.getIdQuestion());

			object.setCode(ReturnCode.ERROR_000);
			log.info("Theme successfully added" + "[name: " + name + " idQuestion : " + idQuestion + "]");
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to add Theme [name: " + name + " idQuestion : " + idQuestion + "], " + ReturnCode.ERROR_500, e);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to add Theme [name: " + name + " idQuestion : " + idQuestion + "], " + ReturnCode.ERROR_200, e);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to add Theme [name: " + name + " idQuestion : " + idQuestion + "], " + ReturnCode.ERROR_050, e);
		}
		object.setObject(theme);
		return object;
	}

	public ReturnObject deleteTheme(int id) {
		log.info("Delete Theme [id: " + id + "]");

		ReturnObject object = new ReturnObject();
		try {
			themeRepository.delete(id);
			object.setCode(ReturnCode.ERROR_000);
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_100);
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

	@Override
	public ReturnObject getAllThemesByUser(Collection<Question> questions) {
		log.info("Get all themes for user");
		ReturnObject object = new ReturnObject();
		try {
			ThemeSet listTheme = new ThemeSet();
			if (CollectionUtils.isNotEmpty(questions)) {
				for (Question question : questions) {
					Collection<ThemeBean> tmp = themeRepository.findByIdQuestion(question.getId());
					for (ThemeBean themeBean : tmp) {
						listTheme.addToSet(getThemeByThemeBean(themeBean));
					}
				}
				object.setObject(listTheme);
				object.setCode(ReturnCode.ERROR_000);
			} else {
				object.setCode(ReturnCode.ERROR_100);
				log.info("This user dont have theme, " + ReturnCode.ERROR_100);
			}

		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_700);
		}

		return object;
	}
	
	
	private class ThemeSet extends TreeSet<Theme>{
		
		private static final long serialVersionUID = 1L;

		public void addToSet(Theme theme){
			boolean isPossible = true;
			for(Theme t: this){
				if(t.getName().equals(theme.getName())){
					isPossible = false;
				}
			}
			if(isPossible){
				this.add(theme);
			}
		}
	}
}
