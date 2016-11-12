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
	 * Missing Parameters
	 */
	ERROR_150,
	/**
	 * Database Unreachable
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
	ERROR_650;
}
