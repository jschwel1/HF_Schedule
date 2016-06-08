import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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
		// two three hour shifts...
		list1.addAll(tList);
		list1.addAll(tList);
		
		for (int i = 0; i < schedule.length; i++){
			for (int j = 0; j < schedule[0].length; j++){
				shift[i][j] = schedule[i][j];
			}
		}
		
		
		// fill schedule with first-shifts
		while (list1.size() > 0 && loopCount < 5){
			int day;
			int time;
			Trainee thisOne = list1.get(0);
			
			
			// If the trainee has enough hours, remove him/her from the list
			if (thisOne.getHours() >= Trainee.REQUIRED_HOURS){
				list1.remove(0);
				continue;
			}
			
			// If the tainee exhausted all preferences
			if (thisOne.getIter() == Trainee.NUM_PREFERENCES){
				missingShift.add(thisOne);
				error += thisOne.getName() + ", ";
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
			}	
			
			
		}
		// ======================= END OF ORGANIZATION ==================== //
		// ======================= DISPLAY & SAVE RESULTS ================= //
		// show error message in case someone did not make it onto the schedule
		JOptionPane.showMessageDialog(null, error, "Trainees missing at least one shift:", JOptionPane.ERROR_MESSAGE);

		/* Print out schedule graphically and save as .csv file */
		Shift.showSchedule(shift);
		// Open FileChooser to save this schedule
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save this schedule");
		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			PrintWriter writer;

			if (!fc.getSelectedFile().getName().endsWith(".csv")) 
				writer = new PrintWriter(fc.getSelectedFile().getPath()+ ".csv");
			else writer = new PrintWriter(fc.getSelectedFile());
			char delim = ',';

			writer.println(delim+"Sunday"+delim+"Monday"+delim+"Tuesday"+delim+"Wednesday"+delim+"Thursday"+delim+"Friday"+delim+"Saturday");

			for (int i = 0; i < Trainee.SHIFTS_PER_DAY; i++){
				switch (i){
				case 0:
					writer.print("\"0000-0600\"");
					break;
				case 1:
					writer.print("\"0600-0900\"");
					break;
				case 2:
					writer.print("\"0900-1200\"");
					break;
				case 3:
					writer.print("\"1200-1500\"");
					break;
				case 4:
					writer.print("\"1500-1800\"");
					break;
				case 5:
					writer.print("\"1800-2100\"");
					break;
				case 6:
					writer.print("\"2100-0000\"");
					break;
				default:
					writer.print("####");
				}
				for (int j = 0; j < 7; j++){
					if (schedule[i][j].hasTrainee())
						writer.print(delim+schedule[i][j].getTrainee().getName());
					else
						writer.print(delim+"");

				}
				writer.println();
			}
			writer.close();
		}
	}


}
