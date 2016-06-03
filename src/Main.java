import java.io.File;
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

			// If the list is empty, make sure everyone has the correct # of 
			// hours
			if (list1.size() == 0){
				loopCount++;
				
				error = "The following trainees did not get at least one shift: ";
				for (int d = 0; d < 7; d++){
					for (int t = 0; t < Trainee.SHIFTS_PER_DAY; t++){
						Trainee temp;
						
						// if no Trainee, skip
						if (!shift[d][t].hasTrainee()) continue;
						temp = shift[d][t].getTrainee();
						System.out.println(temp.getName() + " has " + temp.getHours() + " hours");
						// if they have 6 hours, no problem
						if (temp.getHours() == 6) continue;

						// if they have 9 hours remove the 3-hour shift
						else if (temp.getHours() == 9 && t != Shift.OVERNIGHT){
							temp.reduceHours(3);
							shift[d][t].setTrainee(null);
						}
						// if 12 hours (2 overnights) find the one they prefer least
						else if (temp.getHours() == 12){
							for (int p = Trainee.NUM_PREFERENCES-1; p >= 0; p--){
								if (shift[temp.getPrefDay(p)][temp.getPrefTime(p)].hasTrainee()){
									if (shift[temp.getPrefDay(p)][temp.getPrefTime(p)].getTrainee().equals(temp)){
										shift[temp.getPrefDay(p)][temp.getPrefTime(p)].setTrainee(null);
										temp.reduceHours(6);
									}
								}
							}
						}
						else if (temp.getHours() < 6){
							list1.add(temp);
						}

					}
				}
				continue;
			}

			// make sure the trainee hasn't gone through all preferences:
			if (list1.get(0).getIter() == Trainee.NUM_PREFERENCES){
				error+=list1.get(0).getName();
				error+=", ";
				list1.remove(0);
				continue;
			}
			day = list1.get(0).getPrefDay();
			time = list1.get(0).getPrefTime();

			// check if the first trainee in the queue's choice is taken
			if (shift[day][time].hasTrainee()){
				// if no preceptors on shift, go to priority
				if (!shift[day][time].hasCCPrec() && !shift[day][time].hasDrPrec()){
					// Trainee already on shift has a higher priority
					if (shift[day][time].getTrainee().hasHigherPriorityThan(list1.get(0)) == 1){
						list1.get(0).nextChoice();
						continue;
					}
					// equal priorities, leave the one on shift and get the trainee's next preference
					else if (shift[day][time].getTrainee().hasHigherPriorityThan(list1.get(0)) == 1){
						list1.get(0).nextChoice();
						continue;
					}
					else{
						list1.add(shift[day][time].getTrainee());
						shift[day][time].setTrainee(list1.get(0));
						list1.get(0).nextChoice();
						list1.remove(0);
					}
				}
				// If the trainee already there has a preceptor and the new 
				// one doesn't, the original gets to keep it
				else if (shift[day][time].getTrainee().hasPreceptor(shift[day][time]) > 0 
						&& list1.get(0).hasPreceptor(shift[day][time]) == 0){
					list1.get(0).nextChoice();
					continue;
				}
				// if the new trainee can have a preceptor on this shift, and
				// the one already there does't have one give it to the 
				// precepting trainee.
				else if (shift[day][time].getTrainee().hasPreceptor(shift[day][time]) == 0 
						&& list1.get(0).hasPreceptor(shift[day][time]) > 0){

					list1.add(shift[day][time].getTrainee());
					shift[day][time].setTrainee(list1.get(0));
					list1.get(0).nextChoice();
					list1.remove(0);
				}
				// If both or neither can/will precept, go to priority
				else{
					if (shift[day][time].getTrainee().hasHigherPriorityThan(list1.get(0)) == 1){
						list1.get(0).nextChoice();
						continue;
					}
					else if (shift[day][time].getTrainee().hasHigherPriorityThan(list1.get(0))== -1){
						list1.get(0).nextChoice();
						continue;
					}
					else{
						list1.add(shift[day][time].getTrainee());
						shift[day][time].setTrainee(list1.get(0));
						list1.get(0).nextChoice();
						list1.remove(0);
						continue;
					}
				}

			}
			else{
				shift[day][time].setTrainee(list1.get(0));
				list1.get(0).nextChoice();
				list1.remove(0);
				if (time == Shift.OVERNIGHT){
					// remove it from the rest of the list so they only get one shift
					list1.remove(shift[day][time]);

				}
			}

		}


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
