package controller;

import java.time.format.DateTimeFormatter;

/**
 * Constants used by the controller class.
 */
public class ControllerConstants {
	public static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
	public static final String STYLE_DESTINATION = "/styles/style.css";

	//Login attempt constants
	public static final String LOGIN_ATTEMPT_DESTINATION = "loginAttempts.txt";
	public static final String DATE_TIME_FORMAT = "dd.MM.yyyy\tHH:mm:ss\t";
	public static final String SUCCESSFUL_LOGIN = "SUCCEEDED";
	public static final String FAILED_LOGIN = "FAILED";
}