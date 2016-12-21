package com.quizz.database.datas;

public enum Visibility {
	/**
	 * Only you can access to your quizzs
	 */
	PRIVATE(2),
	/**
	 * Only friends of quizz creator can access to your quizzs
	 */
	PROTECTED(1),
	/**
	 * Everything can access to your quizzs
	 */
	PUBLIC(0);
	
	private Integer id;
	
	private Visibility(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public Visibility getVisibility(Integer id) {
		if(id==0) {
			return Visibility.PUBLIC;
		} else if (id==1) {
			return Visibility.PROTECTED;
		} else {
			return Visibility.PRIVATE;
		}
	}
}
