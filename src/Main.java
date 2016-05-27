import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Main {
	public static void main(String[] args) throws FileNotFoundException{
		Base b = new Base();
		
		
//		run();
		
//		JFileChooser fc = new JFileChooser();
//		File f = new File("");
//		if (fc.showOpenDialog(null) == fc.APPROVE_OPTION) f = fc.getSelectedFile();
//		else System.exit(1);
//		Scanner s = new Scanner(f);
//		System.out.println("First: ");
//		Trainee t1 = new Trainee(s);
//		System.out.println("Second: ");
//		Trainee t2 = new Trainee(s);
//		
//		System.out.println(t1);
//		System.out.println(t2);
		
		
	}
	public static void run() throws FileNotFoundException{
		ArrayList<Trainee> list1 = new ArrayList<Trainee>();
		ArrayList<Trainee> list2 = new ArrayList<Trainee>();
		Shift[][] shift = new Shift[7][8];
		String error = "The following trainees did not get at least one shift: ";
		
		/* Fill list1 with Trainees and fill their preference */
		JFileChooser fc = new JFileChooser();
		File f = new File("");
		if (fc.showOpenDialog(null) == fc.APPROVE_OPTION) f = fc.getSelectedFile();
		else System.exit(1);
		Scanner s = new Scanner(f);
		while (s.hasNextLine()){
			list1.add(new Trainee(s));
		}
		
		// set up schedule
		if (fc.showOpenDialog(null) == fc.APPROVE_OPTION) f = fc.getSelectedFile();
		else System.exit(1);
		s = new Scanner(f);
		for (int d = 0; d < 7; d++){
			for (int t = 0; t < 8; t++){
				
			}
		}
		
		
		// fill schedule with first-shifts
		while (list1.size() > 0){
			int day = list1.get(0).getPrefDay();
			int time = list1.get(0).getPrefTime();
			
			
			// make sure the trainee hasn't gone through all preferences:
			if (list1.get(0).getIter() == Trainee.NUM_PREFERENCES){
				error+=list1.get(0).getName();
				error+=", ";
				list1.remove(0);
				continue;
			}
			
			// check if the first trainee in the queue's top choice is taken
			if (shift[day][time].hasTrainee()){
				// if no preceptors on shift, go to priority
				if (!shift[day][time].hasCCPrec() && !shift[day][time].hasDrPrec()){
					if (shift[day][time].getTrainee().hasHigherPriorityThan(list1.get(0))){
						list1.get(0).nextChoice();
						continue;
					}
					else{
						list1.add(shift[day][time].getTrainee());
						shift[day][time].setTrainee(list1.get(0));
						
					}
				}
				// check that the correct preceptor is on shift
				else if (((shift[day][time].hasCCPrec() && shift[day][time].getTrainee().isCCPrecepting())
							|| (shift[day][time].hasDrPrec() && shift[day][time].getTrainee().isDrPrecepting()))
						&& !((list1.get(0).isCCPrecepting() && shift[day][time].hasCCPrec()) 
							|| (list1.get(0).isDrPrecepting() && shift[day][time].hasDrPrec()))){
					list1.get(0).nextChoice();
					continue;
				}
				else if (((shift[day][time].hasCCPrec() && shift[day][time].getTrainee().isCCPrecepting())
						|| (shift[day][time].hasDrPrec() && shift[day][time].getTrainee().isDrPrecepting()))
					&& !((list1.get(0).isCCPrecepting() && shift[day][time].hasCCPrec()) 
						|| (list1.get(0).isDrPrecepting() && shift[day][time].hasDrPrec()))){
					list1.add(shift[day][time].getTrainee());
					shift[day][time].setTrainee(list1.get(0));
				}
				// neither will be precepting what they want, go to priority
				else{
					if (shift[day][time].getTrainee().hasHigherPriorityThan(list1.get(0))){
						list1.get(0).nextChoice();
						continue;
					}
					else{
						list1.add(shift[day][time].getTrainee());
						shift[day][time].setTrainee(list1.get(0));
						list1.remove(0);
						continue;
					}
				}
				
			}
			else{
				shift[day][time].setTrainee(list1.get(0));
				
			}
			list1.get(0).nextChoice();
			list2.add(list1.get(0));
			list1.remove(0);
		}
		
		// fill schedule with second-shifts (Pretty much identical to the first loop
	/*	while (list2.size() > 0){
			
		}
	*/	
		/* Print out schedule graphically and/or in excel/.csv file */
		
		
		for (int d = 0; d < 7; d++){
			for (int t = 0; t < 8; t++){
				System.out.print(shift[d][t].getTrainee().getName() + "\t");
			}
			System.out.println();
		}
	}
	
	public static void run(ArrayList<Trainee> tList, Shift[][] schedule) throws FileNotFoundException{
		ArrayList<Trainee> list1 = new ArrayList<Trainee>();
		ArrayList<Trainee> list2 = new ArrayList<Trainee>();
		Shift[][] shift = schedule;
		String error = "The following trainees did not get at least one shift: ";
		list1.addAll(tList);
		
		// fill schedule with first-shifts
		while (list1.size() > 0){
			int day = list1.get(0).getPrefDay();
			int time = list1.get(0).getPrefTime();
			
			
			// make sure the trainee hasn't gone through all preferences:
			if (list1.get(0).getIter() == Trainee.NUM_PREFERENCES){
				error+=list1.get(0).getName();
				error+=", ";
				list1.remove(0);
				continue;
			}
			
			// check if the first trainee in the queue's top choice is taken
			if (shift[day][time].hasTrainee()){
				// if no preceptors on shift, go to priority
				if (!shift[day][time].hasCCPrec() && !shift[day][time].hasDrPrec()){
					if (shift[day][time].getTrainee().hasHigherPriorityThan(list1.get(0))){
						list1.get(0).nextChoice();
						continue;
					}
					else{
						list1.add(shift[day][time].getTrainee());
						shift[day][time].setTrainee(list1.get(0));
						
					}
				}
				// check that the correct preceptor is on shift
				else if (((shift[day][time].hasCCPrec() && shift[day][time].getTrainee().isCCPrecepting())
							|| (shift[day][time].hasDrPrec() && shift[day][time].getTrainee().isDrPrecepting()))
						&& !((list1.get(0).isCCPrecepting() && shift[day][time].hasCCPrec()) 
							|| (list1.get(0).isDrPrecepting() && shift[day][time].hasDrPrec()))){
					list1.get(0).nextChoice();
					continue;
				}
				else if (((shift[day][time].hasCCPrec() && shift[day][time].getTrainee().isCCPrecepting())
						|| (shift[day][time].hasDrPrec() && shift[day][time].getTrainee().isDrPrecepting()))
					&& !((list1.get(0).isCCPrecepting() && shift[day][time].hasCCPrec()) 
						|| (list1.get(0).isDrPrecepting() && shift[day][time].hasDrPrec()))){
					list1.add(shift[day][time].getTrainee());
					shift[day][time].setTrainee(list1.get(0));
				}
				// neither will be precepting what they want, go to priority
				else{
					if (shift[day][time].getTrainee().hasHigherPriorityThan(list1.get(0))){
						list1.get(0).nextChoice();
						continue;
					}
					else{
						list1.add(shift[day][time].getTrainee());
						shift[day][time].setTrainee(list1.get(0));
						list1.remove(0);
						continue;
					}
				}
				
			}
			else{
				shift[day][time].setTrainee(list1.get(0));
				
			}
			list1.get(0).nextChoice();
			list2.add(list1.get(0));
			list1.remove(0);
		}
		
		// fill schedule with second-shifts (Pretty much identical to the first loop
		while (list2.size() > 0){
			int day = list2.get(0).getPrefDay();
			int time = list2.get(0).getPrefTime();
			
			
			// make sure the trainee hasn't gone through all preferences:
			if (list2.get(0).getIter() == Trainee.NUM_PREFERENCES){
				error+=list2.get(0).getName();
				error+=", ";
				list2.remove(0);
				continue;
			}
			
			// check if the first trainee in the queue's top choice is taken
			if (shift[day][time].hasTrainee()){
				// if no preceptors on shift, go to priority
				if (!shift[day][time].hasCCPrec() && !shift[day][time].hasDrPrec()){
					if (shift[day][time].getTrainee().hasHigherPriorityThan(list2.get(0))){
						list2.get(0).nextChoice();
						continue;
					}
					else{
						list2.add(shift[day][time].getTrainee());
						shift[day][time].setTrainee(list1.get(0));
						
					}
				}
				// check that the correct preceptor is on shift
				else if (((shift[day][time].hasCCPrec() && shift[day][time].getTrainee().isCCPrecepting())
							|| (shift[day][time].hasDrPrec() && shift[day][time].getTrainee().isDrPrecepting()))
						&& !((list2.get(0).isCCPrecepting() && shift[day][time].hasCCPrec()) 
							|| (list2.get(0).isDrPrecepting() && shift[day][time].hasDrPrec()))){
					list2.get(0).nextChoice();
					continue;
				}
				else if (((shift[day][time].hasCCPrec() && shift[day][time].getTrainee().isCCPrecepting())
						|| (shift[day][time].hasDrPrec() && shift[day][time].getTrainee().isDrPrecepting()))
					&& !((list2.get(0).isCCPrecepting() && shift[day][time].hasCCPrec()) 
						|| (list2.get(0).isDrPrecepting() && shift[day][time].hasDrPrec()))){
					list2.add(shift[day][time].getTrainee());
					shift[day][time].setTrainee(list2.get(0));
				}
				// neither will be precepting what they want, go to priority
				else{
					// if trainee already on the schedule has a higher priority, keep them....
					if (shift[day][time].getTrainee().hasHigherPriorityThan(list1.get(0))){
						list1.get(0).nextChoice();
						continue;
					}
					// if the new trainee has a higher priority, move the existing one to the
					// end of the list and replace him/her with a new one
					else{
						list2.add(shift[day][time].getTrainee());
						shift[day][time].setTrainee(list2.get(0));
						list2.remove(0);
						continue;
					}
				}
				
			}
			// if there is no trainee on that day and time, put this one in
			else{
				shift[day][time].setTrainee(list2.get(0));
				
			}
			list2.remove(0);
		}
		
		// show error message in case someone did not make it onto the schedule
		JOptionPane.showMessageDialog(null, error, "Trainees missing at least one shift:", JOptionPane.ERROR_MESSAGE);
		
		/* Print out schedule graphically and/or in excel/.csv file */
		Shift.showSchedule(shift);
		// Open FileChooser to save this schedule
	}

	


	
}
