# MicroStrategy Web SDK Samples

This repository contains standalone Java samples demonstrating how to implement different MicroStrategty functioanlities programtically using the MicroStrategy WEB SDK

## Examples:

1- CreateObjectPrompt: In this sample the goal is to create an Object Prompt and add objects to its list programtically. Object Prompts can be used as a container object to create Object Manager package. For more details please refer to:
 - https://www2.microstrategy.com/producthelp/Current/SystemAdmin/WebHelp/Lang_1033/Content/Copying_objects_in_a_batch__Update_packages.htm
 - https://community.microstrategy.com/s/article/KB36167-How-to-create-an-Object-Manager-package-from-the-command?language=en_US
### Helper Classes:
- **SessionManagement** - Create Intelligence Server session handling using the provided connection details. Includes close sessdion method as well.
- **ShortcutsFolderBrowsing** - Helper class that loop through a specified folder of shortcut objects. the CreateObjectPrompt class uses the browseShortcutsFolder method to  find the the target object(s) and add it/them to the Objects list of the object prompt.

### To Run:
1- Import the classes to your MicroStrategy Web Project in Eclipse (or your favorite IDE)
2- Make sure classes are "included" in the Build Configuration
3- Edit the Java Run Configuration and specify the Run Arguments as follows: *"Intelligence_Server" "project" "username" "pwd" "shortcutsFolderID" "containerFolderID"
