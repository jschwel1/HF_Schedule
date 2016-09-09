/*
 * File: Main.java
 * Last Modified: 08/02/2016
 * Written by: Jacob Schwell
 * Description: This file combines the other three. Upon start-up, the main 
 * 				function only creates a Base object. If the user wants  to
 * 				organize the Trainees in, the function to do that--run--is also
 * 				in this file since it utilizes multiple other files.
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main {

	/**
	 * Main function. Starts  the Base window
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException{
		Base b = new Base();			
	}

	/**
	 * run() sorts the trainees into the Shift matrix passed
	 * @param tList - ArrayList<Trainee>: List of all the trainees to organize
	 * @param schedule - Matrix of Shifts: holds preceptors currently on shift
	 * and will store Trainees after completion
	 * @throws FileNotFoundException - Displays Schedule and offers option to save it, so File Not Found Error might happen
	 */
	public static void run(ArrayList<Trainee> tList, Shift[][] schedule) throws FileNotFoundException{
		ArrayList<Trainee> list1 = new ArrayList<Trainee>();
		ArrayList<Trainee> missingShift = new ArrayList<Trainee>();
		Shift[][] shift = schedule;
		String error = "The following trainees did not get at least one shift: ";
		int loopCount = 0;
		
		// Assure all priority weights are the same (as taken from the first
		// trainee in the list)
		if (tList.size() > 0){
			int CCP_Weight = tList.get(0).getCCPWeight();
			int DrP_Weight = tList.get(0).getDRPWeight();
			int Sem_Weight = tList.get(0).getSemWeight();
			int Pref_Weight = tList.get(0).getPrefWeight();
			
			for (Trainee t: tList){
				t.setCCPWeight(CCP_Weight);
				t.setDRPWeight(DrP_Weight);
				t.setSemWeight(Sem_Weight);
				t.setPrefWeight(Pref_Weight);
				
				t.setIterator(0);
				t.setHours(0);
				
			}
		}
		else {}
		
		// clear out all trainees from the schedule (in case of re-run)
		for (int i = 0; i < schedule.length; i++){
			for (int j = 0; j < schedule[0].length; j++){
				if (schedule[i][j].hasTrainee())
					schedule[i][j].removeTrainee(j);
			}
		}
		
		
		// two three hour shifts...
		list1.addAll(tList);
		list1.addAll(tList);
		
		for (int i = 0; i < schedule.length; i++){
			for (int j = 0; j < schedule[0].length; j++){
				shift[i][j] = schedule[i][j];
			}
		}
		
		
		// fill schedule with first-shifts
		/* 	loopCount < 5 -> 5 is an arbitrary number essentially. It is used in
			the event that switching trainees farther down the arraylist opened
			up a spot for one that had previously not been able to get a shift. */
		while (list1.size() > 0 || loopCount < 5){
			int day;
			int time;
			Trainee thisOne;
			
			
			if (list1.size() == 0){
				if (missingShift.size() == 0) break;
				
				loopCount++;
				list1.addAll(missingShift);
				missingShift.clear();
				
				for (Trainee t: list1){
					t.setIterator(0);
				}
				
				continue;
			}
			
			thisOne = list1.get(0);
			
			// If the trainee has enough hours, remove him/her from the list
			if (thisOne.getHours() >= Trainee.REQUIRED_HOURS){
				list1.remove(0);
				continue;
			}
			
			// If the tainee exhausted all preferences
			if (thisOne.getIter() == Trainee.NUM_PREFERENCES){
				missingShift.add(thisOne);
				list1.remove(0);
				continue;
			}
			
			day = thisOne.getPrefDay();
			time = thisOne.getPrefTime();
			
			if (!shift[day][time].hasTrainee()){
				shift[day][time].setTrainee(thisOne, time);
				list1.remove(0);
				continue;
			}
			
			if (shift[day][time].getTrainee().hasHigherPriorityThan(thisOne, shift[day][time]) == 1){
				thisOne.nextChoice();
				continue;
			}
			else if (shift[day][time].getTrainee().hasHigherPriorityThan(thisOne, shift[day][time]) == -1){
				thisOne.nextChoice();
				continue;
			}
			else{
				list1.add(shift[day][time].replaceTraineeWith(thisOne, time));
				list1.remove(0);
				continue;
			}	
			
		}
		
		for (Trainee t: missingShift){
			error+= t.getName() + ", ";
		}
		
		
		// ======================= END OF ORGANIZATION ==================== //
		// ======================= DISPLAY & SAVE RESULTS ================= //
		// show error message in case someone did not make it onto the schedule
		if (error.endsWith(", "))
			JOptionPane.showMessageDialog(null, error, "Trainees missing at least one shift:", JOptionPane.ERROR_MESSAGE);

		/* Print out schedule graphically and save as .csv file */
		Shift.showSchedule(shift);
		
	}


}
