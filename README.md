# MicroStrategy Web SDK Samples

This repository contains standalone Java samples demonstrating how to implement different MicroStrategty functioanlities programtically using the MicroStrategy WEB SDK

## Examples:

1- CreateObjectPrompt: In this sample the goal is to create an Object Prompt and add objects to its list programtically.
	Helper Classes:
		- SessionManagement - Create Intelligence Server session handling using the provided connection details. Includes close sessdion method as well.
		- ShortcutsFolderBrowsing - Helper class that loop through a specified folder of shortcut objects. the CreateObjectPrompt class uses the browseShortcutsFolder method to  find the the target object(s) and add it/them to the Objects list of the object prompt.

