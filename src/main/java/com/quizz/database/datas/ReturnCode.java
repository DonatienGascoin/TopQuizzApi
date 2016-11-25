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
	 * ObjectNotFound
	 */
	ERROR_100,
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
         * Theme already exist
         */
        ERROR_400,
	/**
	 * IllegalArgumentException
	 */
	ERROR_500;
}
