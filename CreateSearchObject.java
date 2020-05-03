package sdk.mstr.object;

import java.util.ArrayList;

import com.microstrategy.web.objects.SimpleList;
import com.microstrategy.web.objects.WebFolder;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.WebSearch;
import com.microstrategy.web.objects.WebShortcut;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;
import com.microstrategy.webapi.EnumDSSXMLSearchDomain;
import com.microstrategy.webapi.EnumDSSXMLSearchFlags;

public class CreateSearchObject {

	public static void main(String[] args) throws WebObjectsException {
		// TODO Auto-generated method stub
		
		
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
	     
         //get the shortcut ID
         //Get the WebShortcut by ID
	     WebShortcut wShortcut = (WebShortcut)woSource.getObject("061E2A5744AF0A27677925BCEE702546",EnumDSSXMLObjectTypes.DssXmlTypeShortcut);                    
         wShortcut.populate();
         
	     WebShortcut wShortcut2 = (WebShortcut)woSource.getObject("05D7E3AE4DC4809035A546AB4E9496A1",EnumDSSXMLObjectTypes.DssXmlTypeShortcut);                    
         wShortcut2.populate();
	     
	     //System.out.println("containsList size: " + containsList.size());

         //containsList = ShortcutsFolderBrowsing.constructSearchutsList(iServerSession, shortcutsFolderID, containsList);
        
//         ArrayList<WebShortcut> myShortcutsList =  ShortcutsFolderBrowsing.constructSearchutsList(iServerSession, shortcutsFolderID);
//         for (int i = 0; i < myShortcutsList.size(); i++) {
//             System.out.println("Shortcutname: " + myShortcutsList.get(i).getDisplayName());
//    	     containsList.add(myShortcutsList.get(i));
//           }
        
	     containsList.add(wShortcut);
	     containsList.add(wShortcut2);


         System.out.println("containsList size after: " + containsList.size());

			
         WebFolder folder = (WebFolder) woSource.getObject(searchFolderID, EnumDSSXMLObjectTypes.DssXmlTypeFolder);
         woSource.saveAs(wSearch,searchName, folder,true);
         System.out.println("Search Object created!");

        

	}

}
