package sdk.mstr.object;

import java.io.FileInputStream;
import java.io.IOException;

import com.microstrategy.utils.FileDispenser;
import com.microstrategy.utils.StringUtils;
import com.microstrategy.utils.config.ConfigFileManager;
import com.microstrategy.utils.log.Level;
import com.microstrategy.utils.log.LogManager;
import com.microstrategy.utils.log.LoggerConfigObserver;
import com.microstrategy.web.objects.SimpleList;
import com.microstrategy.web.objects.WebFolder;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.WebSearch;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;
import com.microstrategy.webapi.EnumDSSXMLSearchDomain;
import com.microstrategy.webapi.EnumDSSXMLSearchFlags;

public class CreateSearchObject {

    private static final String CLASS_NAME = "sdk.mstr.object.CreateSearchObject";
    private static Level FINE = Level.FINE;
    private static Level WARN = Level.WARNING;
    private static Level ERROR = Level.SEVERE;
    
    //private static ConfigFileManager loggerConfigManager;


	public static void main(String[] args) throws WebObjectsException {
		// TODO Auto-generated method stub
		

        loadLoggerPropertiesFile("/Users/aosman/Development/apache-tomcat-8.5.37/webapps/111GA/src/sdk/mstr/object/logger.properties");
        
		String METHOD_NAME = "main method";
		
		CustomLog.logger.logp(Level.INFO, CLASS_NAME, METHOD_NAME, "Entering my custom function");

		
		if(args.length != 7 ) {
			
			System.out.println("Invalid Argument! Please check the passed parameters again!");
	        System.out.println("Proper Usage is: java -jar filename.jar \"IntelligenceServer_FQDN\" "
	        		+ "\"Project_Name\" \"username\" \"pwd\" \"shortcutFolderPath\" "
	        		+ "\"migrationFolderPath\" \"fileName\"");
	        
	        // Exit Program - -1 indicates failure - SMW to alert Admins
	        System.exit(-1);
		} else {
			
			String iServer_fqdn = args[0];
			String project_name = args[1];
			String username = args[2];
			String pwd = args[3];
			String shortcutsFolderID = args[4];
			String searchFolderID = args[5];
			String searchName = args[6];
			
			  //Session parameters
	        //Create a session using
		    WebIServerSession iServerSession = SessionManagement.getSession(iServer_fqdn, project_name, username, pwd);
	        WebObjectsFactory woFactory = iServerSession.getFactory();
	        WebObjectSource woSource = woFactory.getObjectSource();
	        
	        WebSearch wSearch = woSource.getNewSearchObject();

		     // asynchronized search
		     wSearch.setAsync(false ); 
		
		     // search for User Groups
		     //search.types().add(EnumDSSXMLObjectSubTypes.DssXmlSubTypeUserGroup);
		     // This line is telling the search to bring only the top level user group objects
		     //search.notUses(EnumDSSXMLObjectSubTypes.DssXmlSubTypeUserGroup );
		     
		     // search on project
		     wSearch.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainProject);
		     wSearch.setSearchFlags(EnumDSSXMLSearchFlags.DssXmlSearchUsedByOneOf);

		     
		     SimpleList containsList = wSearch.usedBy();
		     
		     // Testing passing hardcoded shortcut IDs
	         //Get the WebShortcut by ID
//		     WebShortcut wShortcut = (WebShortcut)woSource.getObject("061E2A5744AF0A27677925BCEE702546",EnumDSSXMLObjectTypes.DssXmlTypeShortcut);                    
//	         wShortcut.populate();
//	         
//		     WebShortcut wShortcut2 = (WebShortcut)woSource.getObject("05D7E3AE4DC4809035A546AB4E9496A1",EnumDSSXMLObjectTypes.DssXmlTypeShortcut);                    
//	         wShortcut2.populate();
		     
//		     containsList.add(wShortcut);
//		     containsList.add(wShortcut2);
		     
//		     System.out.println("containsList size: " + containsList.size());

	         ShortcutsFolderBrowsing.constructSearchutsList(iServerSession, shortcutsFolderID, containsList);
	        

//	         System.out.println("containsList size after: " + containsList.size());

				
	         WebFolder folder = (WebFolder) woSource.getObject(searchFolderID, EnumDSSXMLObjectTypes.DssXmlTypeFolder);
	         woSource.saveAs(wSearch,searchName, folder,true);
	         System.out.println("Search Object created!");
	         
	         // Exit when complete - 0 indicates normal termination and no errors
	         System.exit(0);			

	         			
		}
	
	}
	
//	   private static void configureLoggerConfigManager(String resourceName, String filePath) throws IOException {
//	        // TODO need to refactor this
//	        // Observer pattern here is responsible for triggering the LogManager#readConfiguration
//	        // as well as dispensing of file. Unfortunately, the observer doesn't quite work properly
//	        // anymore as it no longer monitors the file for changes, unless we explicitly tell it to
//	        // check.
//	        // One solution would be to extract the readConfiguration out explicitly
//	        // after #configureLoggerConfigManager, in which case, we don't need
//	        // to dispense logger.properties since #configureLoggerConfigManager
//	        // creates a logger.properties anyway.
//	        loggerConfigManager = ConfigFileManager.manage(filePath);
//	        loggerConfigManager.setDispenser(new FileDispenser(resourceName));
//	        loggerConfigManager.addObserver(new LoggerConfigObserver());
//	        loggerConfigManager.getFile(); // dispense if not already present
//	    }
	
	private static void loadLoggerPropertiesFile (String pathToFile) {
		
        FileInputStream fis = null;

        // ~ Need to load the logger.properties file passed as argument..
        
        // Custom logger.properties location can only be ABSOLUTE
		pathToFile = StringUtils.replaceString(pathToFile, "\\", "/");
		
		System.out.println("This is loadLoggerPropertiesFile");
		System.out.println("pathToFile: " + pathToFile);

		
        try {
			LogManager.getLogManager().readConfiguration(fis = new FileInputStream(pathToFile));
        } catch (IOException e) {
            LogManager.generateLogSettingError(e);
        } catch (IllegalArgumentException e) {
            LogManager.generateLogSettingError(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }

//        if (Log.logger.isLoggable(Level.FINE))
//            Log.logger.exiting(CLASS_NAME, _methodName);		
	}


}
