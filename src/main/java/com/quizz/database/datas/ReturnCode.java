package com.quizz.database.datas;

public enum ReturnCode {
	/**
	 * No error
	 */
	ERROR_000,
	/**
	 * Unknown problem
	 */
	ERROR_050,
	/**
	 * Object Not Found
	 */
	ERROR_100,
	/**
	 * QuizzNOtFound
	 */
	ERROR_125,
	/**
	 * QuestionNOtFound
	 */
	ERROR_150,
	/**
	 * TmpResponseNOtFound
	 */
	ERROR_175,
	/**
	 * Missing Parameters
	 */
	ERROR_200,
	/**
	 * Pseudo already exist
	 */
	ERROR_300,
	/**
	 * Quizz already shared
	 */
	ERROR_325,
	/**
	 * Email already exist
	 */
	ERROR_350,
	/**
	 * Theme already exist
	 */
	ERROR_400,
	/**
	 * Quizz was not shared
	 */
	ERROR_450,
	/**
	 * Illegal Argument Exception
	 */
	ERROR_500,
	/**
	 * Exception
	 */
	ERROR_600,
	/**
	 * User inactive
	 */
	ERROR_650,
	/**
	 * Other error
	 **/
	ERROR_700;
}
