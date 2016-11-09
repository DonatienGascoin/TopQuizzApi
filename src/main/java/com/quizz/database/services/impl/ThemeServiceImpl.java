package com.quizz.database.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.Theme;
import com.quizz.database.repository.ThemeRepository;
import com.quizz.database.services.ThemeService;

@Service
public class ThemeServiceImpl implements ThemeService {
	
	@Autowired
	private ThemeRepository themeRepository;

	@Override
	public java.util.List<Theme> getAllThemes() {
		return themeRepository.findAll();
	}
}
