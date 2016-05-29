# README for HF_Schedule



#### SCHEDULING ALGORITH ####
- Store all trainees in an arraylist (two copies of each)
- As long as there is a trainee, loop through
-- If the trainee past his/her final choice, remove him/her from the list and continue
-- If there is no trainee in that trainees current preference, but him/her there, then take their next choice and continue
-- OTHERWISE
	- If there are no preceptors on their current preferred, go to priority
	- If there is only one trainee who would have a preceptor he/she gets it
	- If they're both precepting, go to priority
-- Whenever placing a new trainee in a shift, if it's an overnight,
	- remove any other instance of him/her from the list
	- if replacing another trainee, also add him/her twice back into the list






#### INSTRUCTIOSN FOR USE ####
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



#### THINGS FOR FUTURE UPDATES ####
- "Build Schedule" button should modify the existing schedule rather than start from scratch every time
- Fix scheduling algorith

#### UPDATES #####
