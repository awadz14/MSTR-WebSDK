/**
 * MicroStrategy SDK
 *
 * Copyright @ 2001-2020 MicroStrategy Incorporated. All Rights Reserved.
 *
 * MICROSTRATEGY MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THIS CODE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MICROSTRATEGY SHALL NOT
 * BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
 * MODIFYING OR DISTRIBUTING THIS CODE OR ITS DERIVATIVES.
 * 
 * Written By: Ahmed Osman
 */

package sdk.mstr.object;




import com.microstrategy.web.beans.EnumPromptsBeanEvents;
import com.microstrategy.web.objects.SimpleList;
import com.microstrategy.web.objects.WebDisplayUnits;
import com.microstrategy.web.objects.WebFolder;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectInfo;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebShortcut;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;


/**
 *
 * <p>Title: Browsing Shortcuts Folder</p>
 * <p>Description: Displays the contents for the folder for the given project.</p>
 * <p>Company: Microstrategy, Inc.</p>
 */
public class ShortcutsFolderBrowsing {

	/**
     * Use Web Objects to fetch the contents from the Shared Reports Folder.
     * Note: Developers should modify the values 
     */
    public static String browseShortcutsFolder(WebIServerSession serverSession, String shortcutsFolderID) {
       
    	// List of objects to return
    	String objectList = "";
    	
    	//Create internal variables
        WebFolder folder = null;
        
        //String objectType = "";
        //int objectTypeValue;
        //String shortcutsFolderID = "";

        //Create a session using
        //WebIServerSession serverSession = SessionManagement.getSession();
        
        
        WebObjectSource wObjSource = serverSession.getFactory().getObjectSource();

        try {
            //Use ID for Shortcuts Folder and populate WebFolder
            //Setting
            //sharedFolderID = objSource.getFolderID(EnumDSSXMLFolderNames.DssXmlFolderNamePublicReports);
            //folder = (WebFolder) objSource.getObject(sharedReportsFolderID, EnumDSSXMLObjectTypes.DssXmlTypeFolder);

            folder = (WebFolder) wObjSource.getObject(shortcutsFolderID, EnumDSSXMLObjectTypes.DssXmlTypeFolder);

            //Set number of elements to fetch
            folder.setBlockBegin(1);
            folder.setBlockCount(50);

            //Fetch Contents from Intelligence Server
            folder.populate();

            //If the folder has any contents, display them
            if (folder.size() > 0) {
                //Extract folder contents
                WebDisplayUnits units = folder.getChildUnits();

                // Loop through Shortcuts
                // Returns the object referenced by each shortcut.
                // Construct the elements of ObjectList for the Object prompt
                //Print folder contents
                //System.out.println("\n\n\nPrinting folder contents:");
                
                if (units != null) {
                    for (int i = 0; i < units.size(); i++) {
                    	
                    	/*
                        switch (units.get(i).getDisplayUnitType()) {
                        case EnumDSSXMLObjectTypes.DssXmlTypeFolder:
                            objectType = "Folder";
                            break;
                        case EnumDSSXMLObjectTypes.DssXmlTypeReportDefinition:
                            objectType = "Report";
                            break;
                        case EnumDSSXMLObjectTypes.DssXmlTypeDocumentDefinition:
                            objectType = "Document";
                            break;
                        case EnumDSSXMLObjectTypes.DssXmlTypeFilter:
                            objectType = "Filter";
                            break;
                        case EnumDSSXMLObjectTypes.DssXmlTypeTemplate:
                            objectType = "Template";
                            break;
                        case EnumDSSXMLObjectTypes.DssXmlTypeAttribute:
                            objectType = "Attribute";
                            break;
                        case EnumDSSXMLObjectTypes.DssXmlTypeMetric:
                            objectType = "Metric";
                            break;
                        case EnumDSSXMLObjectTypes.DssXmlTypeFact:
                            objectType = "Fact";
                            break;
                        } */
                        //System.out.println("\t" + objectType + ": " + units.get(i).getDisplayName());
                    	
                    	if(units.get(i).getDisplayUnitType() == EnumDSSXMLObjectTypes.DssXmlTypeShortcut) 
                    	{
                    		WebObjectInfo obj = (WebObjectInfo) units.get(i);
                    		
                    		//Populate WebShortcut
                            WebShortcut wShortcut = (WebShortcut)obj;
                            wShortcut.populate();
                            
                            WebObjectInfo targetObj = wShortcut.getTarget();
                            
                            System.out.println("Target Object Name: " + targetObj.getDisplayName());
                            System.out.println("Target Object Type: " + targetObj.getDisplayUnitType());
                            
                            if(!(i + 1 < units.size())) {
                                //last iteration
                            	System.out.println("last iteration");
                                objectList = objectList+(targetObj.getID()
                              		  +"~"+targetObj.getDisplayUnitType()
                                +"~"+targetObj.getDisplayName());
                              }else {
                                  objectList = objectList+(targetObj.getID()
                                		  +"~"+targetObj.getDisplayUnitType()
                                  +"~"+targetObj.getDisplayName()
                                  +EnumPromptsBeanEvents.UNIT_SEPARATOR);
                              }
                            System.out.println("ObjectList: " + objectList);


                            
                            //Test Save the object with new name - write back to MD
                            //wObjSource.save(wShortcut, "new_shortcut");                    		
                    	}
                    }
                }
            }
        } catch (WebObjectsException ex) {
            System.out.println("\nError while fetching folder contents: " + ex.getMessage());
        }
        
		System.out.println("Done constructing objectList!");
		return objectList;

        //Close the session to clean up resources on the Intelligence Server
        //SessionManagement.closeSession(serverSession);
    }
    
    
    
    public static SimpleList constructSearchutsList(WebIServerSession serverSession, String shortcutsFolderID, SimpleList shortcutsList) {
        
    	
    	//Create internal variables
        WebFolder folder = null;
        
        
        WebObjectSource wObjSource = serverSession.getFactory().getObjectSource();

        try {
        	
            folder = (WebFolder) wObjSource.getObject(shortcutsFolderID, EnumDSSXMLObjectTypes.DssXmlTypeFolder);

            //Set number of elements to fetch
            folder.setBlockBegin(1);
            folder.setBlockCount(50);

            //Fetch Contents from Intelligence Server
            folder.populate();

            //If the folder has any contents, display them
            if (folder.size() > 0) {
                //Extract folder contents
                WebDisplayUnits units = folder.getChildUnits();

                // Loop through Shortcuts and construct the List                
                if (units != null) {
                    for (int i = 0; i < units.size(); i++) {
                    	
                    	if(units.get(i).getDisplayUnitType() == EnumDSSXMLObjectTypes.DssXmlTypeShortcut) 
                    	{
                    		WebObjectInfo obj = (WebObjectInfo) units.get(i);
                    		
                    		//Populate WebShortcut
                            WebShortcut wShortcut = (WebShortcut)obj;
                            wShortcut.populate();
                            //System.out.println("Shortcut name: " + wShortcut.getDisplayName());
                            
                            shortcutsList.add(wShortcut);
                            //System.out.println("Array List size: " + shortcutsList.size());
                 		
                    	}
                    }
                }
            }
        } catch (WebObjectsException ex) {
            System.out.println("\nError while fetching folder contents: " + ex.getMessage());
        }
        
		System.out.println("Done constructing shortcutsList!");
		
		return shortcutsList;

    }

}
