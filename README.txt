# README for HF_Schedule



### INSTRUCTIOSN FOR USE ####
- When the program is started, a window with some buttons and a text area will apear:
	- "Add Trainee" -> Allows the user to create a new trainee by:
		1) entering the name
		2) entering the numer of semesters in the agency (as an integer)
		3) checking whether he/she is CC and/or Driver precepting
		4) clicking the buttons corresponding to their shift preferences (in order)
	- "Open Trainee List" -> Open an existing list of trainees, so the user does not have 
				 to rebuild it every time
		** NOTE: opening a list will append all trainees, which may create duplicates
	- "Save Trainee List" -> allows the user to save the current list of Trainees
	- "Build Schedule" -> Opens a window for the user to enter which shifts have what kind of preceptors
		1) check the boxes corresponding to shifts  with Crew Chief Preceptors
		2) click next
		3) check the boxes corresponding to shifts with Driver Preceptors
		4) click submit
	- "Open Schedule" -> allows the user to open an existing preceptor schedules
	- "Save Schedule" -> allows the user to save the current preceptor schedule
	- "Refresh" -> refreshes the log (text area). This is useful after adding a new trainee to update 
		       the information displayed
	- "Remove Trainee" -> allows the user to remove one trainee at a time
		1) Enter the number next the trainee you wish to remove into the popup box
	- "Run" -> uses the list of trainees and the preceptor schedule to generate a schedule for 
		   the trainees
		** Note: After running, A window will pop-up to allow the user to save the schedule as a
			 .csv file, which can be opened in Excel. 
- Key Commands from base:
	- A or a -> Add or modify Trainee
	- B or b -> Build or edit preceptor schedule	
	- R or r -> Refresh info log
	- Q or q -> Quit program
	- G or g -> Run the scheduling program
	- P or p -> Edit priority weights 
	- Delete -> Remove a Trainee from the list

#### THINGS FOR FUTURE UPDATES ####
- Update scheduling algorithm to give higher priorities to trainees with a 3-hr shift right before or after the current shift.
- Make the preceptor schedule builder look better (use buttons rather than check-boxes).
#### UPDATES #####
6/7/2016
# Trainee.java
- Added:
	public int hasHigherPriorityThan(Trainee t, Shift s)

# Shift.java
- Added:
	public void setTrainee(Trainee t, int s)
	public void removeTrainee(int s)
	public Trainee replaceTraineeWith(Trainee t, int s)
- Modified:
	BuildPreceptorSchedule() to modify currently selected schedule instead of starting from scratch

# Main.java
	Organization section is signficantly simpler; however, some modifications are still requried to check for more than the required number of hours and try to better include every trainee.

6/9/2016
# All files
- Several changes made as noted in the commit notes

8/4/2016
# All files
- Brief descriptions about each file.

8/6/2016
# Base.java
- Added key commands to work along with the buttons

8/7/2016
# Main.java and Shift.java
- removed automatic save window when the program finishes building schedule. Clicking the final schedule will bring up the save prompt. The original code is surrounded by block comments (/* ... */) 

8/12/2016
# Base.java
- Type H for a help menu with all the key commands
- Added Key listener to the info log in order to correct an issue with the key listener not working once any button or the info log on the base was clicked

# Main.java
- Only shows warning message if a trainee didn't make it onto the schedule


8/15/2016
#Base.java Shift.java Trainee.java
- Added file extensions for trainee lists and preceptor schedules in their respective files
- When saving files in Base.java, the functions check to make sure the files end with the appropriate extension. If not, it will replace the given extension with the appropriate one.

9/9/2016
# All files
- Added slider menu to modify priorities
- Fixed a lot of things, so now the thing works as it should a lot better
- Added delete key for removing a trainee more easily
- Other bug fixes and improvements
