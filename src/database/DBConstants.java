package database;

import java.time.ZoneOffset;

/**
 * Constants used by the DBConnection class
 */
public class DBConstants {
	//Timestamp pattern
	public static final String TIMESTAMP_PATTERN	=	"YYYY-MM-dd HH:mm:ss";
	
	//Appointment Windows
	public static final int TIME_INTERVAL 	= 15;
	public static final String TIME_UNIT	= "MINUTE";

	//Time zone constants
	public static final String ZONE_PREFIX = "UTC";
	public static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

	//Supported countries
	public static final String SUPPORTED_COUNTRIES  = "\'United States\', \'United Kingdom\', \'Canada\'";
}