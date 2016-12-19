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
	 * QuestionNOtFound
	 */
	ERROR_150,
	/**
	 * Missing Parameters
	 */
	ERROR_200,
	/**
	 * Pseudo already exist
	 */
	ERROR_300,
	/**
	 * Email already exist
	 */
	ERROR_350,
    /**
     * Theme already exist
     */
    ERROR_400,
	/**
	 * Illegal Argument Exception
	 */
	ERROR_500,
	/**
	 * User inactive
	 */
	ERROR_650,
	/**
	 * Other error
	 **/
	ERROR_700;
}
