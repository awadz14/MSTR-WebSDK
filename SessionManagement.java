/**
 * MicroStrategy SDK
 *
 * Copyright � 2001-2006 MicroStrategy Incorporated. All Rights Reserved.
 *
 * MICROSTRATEGY MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THIS SAMPLE CODE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MICROSTRATEGY SHALL NOT
 * BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
 * MODIFYING OR DISTRIBUTING THIS SAMPLE CODE OR ITS DERIVATIVES.
 */

package sdk.mstr.object;


import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.utils.log.Level;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectsException;


/**
 *
 * <p>Title: SessionManagementSample</p>
 * <p>Description: Helper class to manage a MicroStrategy Session. </p>
 * <p>Company: Microstrategy, Inc.</p>
 */
public class SessionManagement {
    private static WebObjectsFactory factory = null;
    private static WebIServerSession serverSession = null;

    /**
     * Creates a new session that can be reused by other classes
     * @param object_id 
     * @param pwd 
     * @param username 
     * @param project_name 
     * @param iServer_fqdn 
     * @return WebIServerSession
     */
    public static WebIServerSession getSession(String iServer_fqdn, String project_name, String username, String pwd) {
        if (serverSession == null) {
            //create factory object
            factory = WebObjectsFactory.getInstance();
            serverSession = factory.getIServerSession();

            //Set up session properties
            serverSession.setServerName(iServer_fqdn); //Should be replaced with the name of an Intelligence Server
            serverSession.setServerPort(0);
            serverSession.setProjectName(project_name); //Project where the session should be created
            serverSession.setLogin(username); //User ID
            serverSession.setPassword(pwd); //Password

            //Initialize the session with the above parameters
            try {
                System.out.println("\nSession created with ID: " + serverSession.getSessionID());
            } catch (WebObjectsException ex) {
  	            CustomLog.logger.logp(Level.WARNING, "SessionManagement", "getSession", ex.getMessage());
                handleError(null, "Error creating session:" + ex.getMessage());

            }
        }
        //Return session
        return serverSession;
    }

    /**
     * Close Intelligence Server Session
     * @param serverSession WebIServerSession
     */
    public static void closeSession(WebIServerSession serverSession) {
        try {
            serverSession.closeSession();
        } catch (WebObjectsException ex) {
            System.out.println("Error closing session:" + ex.getMessage());
            return;
        }
        System.out.println("Session closed.");
    }

    /**
     * Print out error message, close session and abort execution
     * @param serverSession WebIServerSession
     * @param message String
     */
    public static void handleError(WebIServerSession serverSession, String message) {
        System.out.println(message);
        if (serverSession != null) {
            closeSession(serverSession);
        }
        System.exit(0);
    }

}

