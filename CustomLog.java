/**
 * 
 */
package sdk.mstr.object;

import com.microstrategy.utils.log.ConsoleHandler;
import com.microstrategy.utils.log.ExtendedFormatter;
import com.microstrategy.utils.log.Level;
import com.microstrategy.utils.log.Logger;
import com.microstrategy.utils.log.LoggerConfigurator;

/**
 * @author aosman
 *
 */

/**
* The Log class is the MicroStrategy standard way of implementing logging
* for SDK Java customizations.  It exposes <code>logger</code>, which
* features all necessary methods needed for logging.
*
* This class needs the <code>WEB-INF/xml/logger.properties</code> file to
* be configured accordingly.
*
* @see com.microstrategy.utils.log.LoggerConfigurator
* @version 1.1
*/
public class CustomLog extends LoggerConfigurator {
	
	/*
	 * The main object that features all necessary methods for logging.
	 */
	public static final Logger logger = new CustomLog().createLogger();
	
	@Override
	protected Logger createLogger() {
		// Boostrapping needed as logging infrastructure may not be initialized.
		Logger l = super.createLogger();
		l.setLevel(Level.SEVERE);
		ConsoleHandler console = new ConsoleHandler();
		console.setFormatter(new ExtendedFormatter());
		console.setLevel(Level.ALL);
		l.addHandler(console);
		return l;
	}
	
	
//	public Log() {
//        super();
//   }
//
//   public static boolean isLoggable(Level theLevel)
//   {
//        return Log.logger.isLoggable(theLevel);
//   }
//
//
//   public static void generateLog(Level theLevel, String className, String method, String msg){
//        Log.logger.logp(theLevel, className, method, msg);
//   }
   
   

}
