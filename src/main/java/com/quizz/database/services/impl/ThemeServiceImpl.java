package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.ThemeBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Theme;
import com.quizz.database.repository.ThemeRepository;
import com.quizz.database.repository.UserRepository;
import com.quizz.database.services.ThemeService;

import lombok.extern.slf4j.Slf4j;
import sun.invoke.empty.Empty;

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
    public ReturnObject getThemeByName(String name){
        log.info("Get Theme [name: " + name + "]");
		ReturnObject object = new ReturnObject();
		Theme theme = null;
		try {
			ThemeBean findByName = themeRepository.findByName(name);
			theme = getThemeByThemeBean(findByName);
                        if (theme == null){
                            object.setCode(ReturnCode.ERROR_100);
                        }else {
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

    /**
    * Return name, id, question (id)
    * 
    * @return {@link Theme}
    */
    public ReturnObject getAllTheme(){
        log.info("Get all Theme");
        ReturnObject object = new ReturnObject();
        List<Theme> themes = new ArrayList<Theme>();
        try {
                List<ThemeBean> findAll = themeRepository.findAll();
                for (ThemeBean themeBean : findAll) {
                        Theme theme = new Theme();

                        theme.setName(themeBean.getName());
                        theme.setId(themeBean.getId());

                        themes.add(theme);
                }
                if (themes.isEmpty()){
                    object.setCode(ReturnCode.ERROR_100);
                }else{
                    object.setCode(ReturnCode.ERROR_000);
                }
        } catch (RuntimeException e) {
                object.setCode(ReturnCode.ERROR_200);
                log.error("Impossible to get all Theme " + ReturnCode.ERROR_200, e);
        } catch (Exception e) {
                object.setCode(ReturnCode.ERROR_050);
                log.error("Impossible to get all Theme " + ReturnCode.ERROR_050, e);
        }
        object.setObject(themes);

        return object;

    }

    public ReturnObject addTheme(String name){

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
            theme = new Theme(themeBean.getId(), themeBean.getName());

            if (theme == null){
                object.setCode(ReturnCode.ERROR_050); 
            }else{
                object.setCode(ReturnCode.ERROR_000); 
            }
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

    public ReturnObject deleteTheme(int id){
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
           if(bean != null){
               theme = new Theme();
                   theme.setName(bean.getName());
                   theme.setId(bean.getId());
           }
           return theme;
   }
}