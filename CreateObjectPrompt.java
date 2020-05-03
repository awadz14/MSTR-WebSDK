package sdk.mstr.object;


import com.microstrategy.web.beans.BeanFactory;
import com.microstrategy.web.beans.EnumPromptsBeanEvents;
import com.microstrategy.web.beans.GenericRequestKeys;
import com.microstrategy.web.beans.GenericWebEvent;
import com.microstrategy.web.beans.PromptDefinitionBean;
import com.microstrategy.web.beans.RequestKeys;
import com.microstrategy.web.beans.WebBeanException;
import com.microstrategy.web.beans.WebException;
import com.microstrategy.web.objects.EnumWebPromptType;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebSessionInfo;

public class CreateObjectPrompt {

	public static void main(String[] args) throws WebBeanException, WebException {
		// TODO Auto-generated method stub
		
		// 5 arguments
		// Java -jar iServer_fqdn project_name username pwd object_id
		// Potential arguments to add is Folder ID where object to be saved
		
		String iServer_fqdn = args[0];
		String project_name = args[1];
		String username = args[2];
		String pwd = args[3];
		String shortcutsFolderID = args[4];
		String containerFolderID = args[5];
		
		String objectsList = "";
		
	     for(int i = 0; i < args.length; i++) {
	         System.out.println("args[" + i + "]: " + args[i]);
	      }
	     
	    // Create MicroStrategy session against Intelligence Server using provided connection details
	    WebIServerSession iServerSession = SessionManagement.getSession(iServer_fqdn, project_name, username, pwd);
	    
	    objectsList = ShortcutsFolderBrowsing.browseShortcutsFolder(iServerSession, shortcutsFolderID);
		createPrompt(iServerSession, objectsList, containerFolderID);
	    
        //Close the session to clean up resources on the Intelligence Server
        SessionManagement.closeSession(iServerSession);
	}
	
	
	
	public static void createPrompt(WebIServerSession iServerSession, String objectListIds, String destinationFolderID) throws WebBeanException, WebException {
		
			BeanFactory bf = BeanFactory.getInstance();
			PromptDefinitionBean pdb = (PromptDefinitionBean) bf.newBean("PromptDefinitionBean"); 
			pdb.setSessionInfo((WebSessionInfo) iServerSession);
	
			//set to web object prompt
			pdb.setPromptType(EnumWebPromptType.WebPromptTypeObjects);
	
	
			RequestKeys frameKeys = new GenericRequestKeys();
			//This event corresponds to the "Use a pre-defined list of objects" option when creating an Object prompt
			// For more details see 'setElementsAvailList' in the PromptDefinitionBean of the event handler reference 
			frameKeys.add(GenericWebEvent.URL_EVENT_NAME, "4097016");
			frameKeys.add(GenericWebEvent.URL_SOURCE_NAME, "pdb.4097016");
			
			// A list of elements. In this example only Customer is added. 
			//frameKeys.add("objectsList", "8D679D3C11D3E4981000E787EC6DE8A4~12~Customer"+EnumPromptsBeanEvents.UNIT_SEPARATOR+"3829143A49782B823E4579B1D54749F8~4~NameMaxRevenue");
			
			frameKeys.add("objectsList", objectListIds);


			pdb.handleRequest(frameKeys);
			pdb.collectData();
			pdb.setName("testprompt_4");
			// Save the prompt in the Public Objects folder
			//pdb.saveAs("testprompt", "testpromt", "98FE182C2A10427EACE0CD30B6768258", true); 
			
			pdb.saveAs("testprompt_4", "testpromt_4", destinationFolderID, true);
			
			System.out.println("Done! Container Object is created");


		}

}
