/* File: Trainee.java
 * Last modified: 8/2/2016
 * Written by: Jacob Schwell
 * Description: The Trainee class creates an object that acts as a trainee in
 * 				Harpur's Ferry. Each one has an arrray of prefered shifts 
 * 				separated into two--one for day of the week and one for time of 
 * 				day. The Trainee's iterator holds the position in the array of 
 * 				the next preferred shift (for when the the shifts are organized)
 * 				the boolean ccPrec and drPrec indicate whether or not the 
 * 				specific trainee is precepting to be a crew chief or driver, 
 * 				respectively. Other variables should be explanatory by name; 
 * 				however, feel free to contact me if any questions arise.
 * 
 */

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Trainee implements ActionListener{
	public static final int NUM_PREFERENCES = 6;
	public static final int SHIFTS_PER_DAY = 7;
	public static final int REQUIRED_HOURS = 6;
	private static final int NOT_SET = 999;
	public static final String FILE_EXT = ".HFTL";
	public static final int DEFAULT_CC_WEIGHT = 5;
	public static final int DEFAULT_DR_WEIGHT = 5;
	public static final int DEFAULT_SEM_WEIGHT = 2;
	public static final int DEFAULT_PREF_WEIGHT = 6;
	
	int prefDay[];
	int prefTime[];
	int iterator;
	int semesters;
	int hours;
	int weightCCP, weightDRP, weightSem, weightPref; 
	boolean ccPrec;
	boolean drPrec;
	String name;

	// for GUI Builder
	JButton shiftButton[][];
	JButton submit;
	JTextField nameInput;
	JTextField semesterInput;
	JCheckBox ccPrecepting;
	JCheckBox drPrecepting;
	
	/**
	 * Initialize empty trainee with empty arrays of preferences
	 */
	Trainee(){
		prefDay = new int[NUM_PREFERENCES];
		prefTime = new int[NUM_PREFERENCES];
		iterator = 0;
		hours = 0;
		name = "";
		
		weightCCP = 5;
		weightDRP = 5;
		weightSem = 2;
		weightPref = 6;
		
		for (int i = 0; i < Trainee.NUM_PREFERENCES; i++){
			prefDay[i] = Trainee.NOT_SET;
			prefTime[i] = Trainee.NOT_SET;
		}
	}
	/**
	 * Initialize a new Trainee from a file (organized by save function in Base.java--should be moved eventually
	 * @param s - Scanner with Trainee list file
	 * @throws FileNotFoundException - throws error if file cannot be found
	 */
	Trainee(Scanner s, int weight_CCP, int weight_DRP, int weight_Sem, int weight_Pref) throws FileNotFoundException{
		this();
		int count = 0;
		int input;
		char ans;

		weightCCP = weight_CCP;
		weightDRP = weight_DRP;
		weightSem = weight_Sem;
		weightPref = weight_Pref;
		
		// get name from first line
		name = s.nextLine();
//		System.out.println("Trainee's Name: " + name);
		
		while (count < NUM_PREFERENCES){

			// get preference day

			input = s.nextInt();
		//	while (input >= 7) input = s.nextInt();
			prefDay[count] = input;

			// get preference time
			input = s.nextInt();
		//	while (input >= SHIFTS_PER_DAY) input = s.nextInt();
			prefTime[count] = input;
//			System.out.println("Preference #" + (count + 1)+" (" + prefDay[count] + ", " + prefTime[count] + ") ");			
			count++;

		}
		// get precepting info
		ans = s.next().charAt(0);
		if (ans == 'y' || ans == 'Y') ccPrec = true;
		else if (ans == 'n' || ans == 'N') ccPrec = false;
		
		ans = s.next().charAt(0);
		if (ans == 'y' || ans == 'Y') drPrec = true;
		else if (ans == 'n' || ans == 'N') drPrec = false;
		
		// # semesters
		semesters = s.nextInt();
		if (s.hasNextLine()) s.nextLine();
//		System.out.println(ccPrec?"is ":"is not " + "CC Precepting");
//		System.out.println(drPrec?"is ":"is not " + "Driver Precepting");
//		System.out.println("Has been in for " + semesters + " Semesters");
	}
	

	/**
	 * Fills preferences from command line
	 */
	public void fillPrefs(){
		Scanner s = new Scanner(System.in);
		int count = 0;
		int input;
		char ans;

		while (count < NUM_PREFERENCES){
			System.out.println("What is the trainee's name?\n");
			name = s.nextLine();
			System.out.println("Enter the day of the prefered shift (Sun = 0,...,Sat = 6)\n");
			input = s.nextInt();
			while (input >= 7) input = s.nextInt();
			prefDay[count] = input;
			
			System.out.println("Enter the time of the prefered shift (0000-0600 = 0,..,2100-0000 = 7)\n");
			input = s.nextInt();
			while (input >= SHIFTS_PER_DAY) input = s.nextInt();
			prefTime[count] = input;
			
			count++;
		}
		System.out.println("CC Precepting? (y/n)\n");
		ans = s.next().charAt(0);
		if (ans == 'y' || ans == 'Y') ccPrec = true;
		else if (ans == 'n' || ans == 'N') ccPrec = false;
		
		System.out.println("Driver Precepting? (y/n)\n");
		ans = s.next().charAt(0);
		if (ans == 'y' || ans == 'Y') drPrec = true;
		else if (ans == 'n' || ans == 'N') drPrec = false;
		
		System.out.println("How many semesters?\n");
		semesters = s.nextInt();
	}
	
	/**
	 * Get the current preference the Trainee is on
	 * @return 0 = first choice, Trainee.NUM_PREFERENCES-1 = last choice
	 */
	public int getIter(){
		return iterator;
	}
	
	/**
	 * Get the current preferred day
	 * @return integer from 0-6 for each day of the week based on the current preference
	 */
	public int getPrefDay(){
		return prefDay[iterator];
	}
	
	
	/**
	 * Get the preferred day of the specified choice
	 * @param i - integer choice number 
	 * @return day
	 */
	public int getPrefDay(int i){
		return prefDay[i];
	}
	
	
	
	/**
	 * Get the current preferred time during the preferred day
	 * @return integer from 0-Trainee.SHIFTS_PER_DAY-1, current chioce's time
	 */
	public int getPrefTime(){
		return prefTime[iterator];
	}
	
	/**
	 * Get the prefered time of the specified choice
	 * @param i - choice number between 0 & Trainee.NUM_PREFERENCES
	 * @return integer from 0-Trainee.SHIFTS_PER_DAY -1
	 */
	public int getPrefTime(int i){
		return prefTime[i];
	}
	
	/**
	 * Is the Trainee Crew Chief Precepting?
	 * @return boolean, whether or not the Trainee is CC Precepting
	 */
	public boolean isCCPrecepting(){
		return ccPrec;
	}
	
	/**
	 * Is the Trainee Driver Precepting?
	 * @return boolean, whether or not the Trainee is Driver Precepting
	 */
	public boolean isDrPrecepting(){
		return drPrec;
	}
	
	/**
	 * returns the number of hours the Trainee is doing weekly
	 * @return int: hours working per week
	 */
	public int getHours(){
		return hours;
	}

	/**
	 * Sets the number of hours the trainee will be working
	 * @param hr - int: number of hours
	 */
	public void setHours(int hr){
		hours = hr;
	}
	
	/**
	 * Adds hours to the Trainee's current amount 
	 * (shorter version of Trainee.sethours(Trainee.getHours+hr);) 
	 * @param hr - number of hours to add
	 */
	public void addHours(int hr){
		hours+=hr;
	}
	
	public void reduceHours(int hr){
		hours-=hr;
	}
	
	/**
	 * Gets the Trainee's name
	 * @return String, the trainee's name
	 */
	public String getName(){
		return name;
	}
	
	
	public int getCCPWeight(){
		return weightCCP;
	}
	public void setCCPWeight(int w){
		weightCCP = w;
	}
	public int getDRPWeight(){
		return weightDRP;
	}
	public void setDRPWeight(int w){
		weightDRP = w;
	}
	public int getPrefWeight(){
		return weightPref;
	}
	public void setPrefWeight(int w){
		weightPref = w;
	}
	
	public int getSemWeight(){
		return weightSem;
	}
	public void setSemWeight(int w){
		weightSem = w;
	}
	
	/**
	 * Checks the priorities of this trainee against another 
	 * @param t other trainee
	 * @return integer: 1 -> this trainee has a higher priority than the other
	 * 							passed as an argument.
	 * 					0 -> The other trainee has a higher priority than this
	 * 							one
	 * 				   -1 -> They both have the same priority
	 */
	public int hasHigherPriorityThan(Trainee t){
		int p1 = this.getPrefWeight()*this.getIter() + (this.numSemesters()*this.getSemWeight());
		int p2 = t.getPrefWeight()*t.getIter() + (t.numSemesters()*t.getSemWeight());
		
		if (this.isCCPrecepting()) p1+= this.getCCPWeight();
		if (this.isDrPrecepting()) p1+= this.getDRPWeight();
		if (t.isCCPrecepting()) p2+= t.getCCPWeight();
		if (t.isDrPrecepting()) p2+= t.getDRPWeight();
		
		if (p1 > p2) return 1;
		if (p1 == p2) return -1;
		return 0;
	}
	
	/**
	 * This version of hasHigherPriorityThan includes shifts to account for 
	 * whether or not the trainee would be able to precept on a shift or not
	 * and how how many ways he/she could precept
	 * @param t - Trainee comparing to
	 * @param s - Shift to determine priority for
	 * @return - 1-> this Trainee has a higher priority
	 * 			 0-> the other Trainee (t) has a higher priority
	 * 			-1-> they have the same priority
	 */
	public int hasHigherPriorityThan(Trainee t, Shift s){
		// Semesters in is not as important as precepting and the prefernce 
		// number the trainee is on becomes exponentially more important
		int CCPrec, DrPrec, numSem, choice;
		int p1, p2;
		
		// get numbers from this trainee
		CCPrec = this.hasCCPreceptor(s);
		DrPrec = this.hasDrPreceptor(s);
		numSem = this.numSemesters();
		choice = this.getIter()+1;
		p1 = 0;
		p1 += CCPrec * this.getCCPWeight();
		p1 += DrPrec * this.getDRPWeight();
		p1 += numSem * this.getSemWeight();
		p1 += choice * this.getPrefWeight();
		
		// get numbers for the trainee passed as parameter
		CCPrec = t.hasCCPreceptor(s);
		DrPrec = t.hasDrPreceptor(s);
		numSem = t.numSemesters();
		choice = t.getIter()+1;
		p2 = 0;
		p2 += CCPrec * t.getCCPWeight();
		p2 += DrPrec * t.getDRPWeight();
		p2 += numSem * t.getSemWeight();
		p2 += choice * t.getPrefWeight();
		
		
		System.out.println("------\n"+this.getName() + " has a priority of: " + p1 + "\n"
				+ t.getName() + " has a priority of: " + p2 + "\n-----\n");
		
		// return the appropriate value
		if (p1 > p2) return 1;
		if (p2 > p1) return 0;
		else return -1;
	}

	/**
	 * Gets the number of semesters the Trainee has been in the agency for
	 * @return int, number of semesters
	 */
	public int numSemesters(){
		return semesters;
	}
	
	/**
	 * Sets the iterator to a certain position
	 * @param i, integer number of position for the iterator
	 * 			0 = reset to first choice,
	 * 			Trainee.NUM_PREFERENCES = set to last choice
	 */
	public void setIterator(int i){
		iterator = i;
	}
	
	/**
	 * Move the trainee's preference iterator to the next choice
	 */
	public void nextChoice(){
		if (++iterator > NUM_PREFERENCES) iterator--;
	}

	/**
	 * returns a string representation of the trainee with comma separations
	 * between the information
	 */
	public String toString(){
		String s = "";
		s += name + "\n";
		for (int i = 0; i < NUM_PREFERENCES; i++){
			s+= prefDay[i] + "," + prefTime[i]+',';
		}
		if (ccPrec) s+= "y,";
		else s+="n,";
		
		if (drPrec) s+= "y,";
		else s+="n,";
		
		s+=semesters;
		
		return s;
	}
	
	/**
	 * Gets the trainee's information (except name) with tab-separated values
	 * @return
	 */
	public String getInfo(){
		String s = "";
		for (int i = 0; i < NUM_PREFERENCES; i++){
			s+= prefDay[i] + "\t" + prefTime[i]+'\t';
		}
		if (ccPrec) s+= "y\t";
		else s+="n\t";
		
		if (drPrec) s+= "y\t";
		else s+="n\t";
		
		s+=semesters;
		
		return s;
	}

	
	public String print(){
		String s = this.getName() + "\n";
		
		for (int i = 0; i < Trainee.NUM_PREFERENCES; i++){
			switch (this.getPrefDay(i)){
				case 0:
					s+="Su";
					break;
				case 1:
					s+="Mo";
					break;
				case 2:
					s+="Tu";
					break;
				case 3:
					s+="We";
					break;
				case 4:
					s+="Th";
					break;
				case 5:
					s+="Fr";
					break;
				case 6:
					s+="Sa";
					break;
			}

			s += " (";
			
			switch (this.getPrefTime(i)){
				case 0:
					s+="0000";
					break;
				case 1:
					s+="0600";
					break;
				case 2:
					s+="0900";
					break;
				case 3:
					s+="1200";
					break;
				case 4:
					s+="1500";
					break;
				case 5:
					s+="1800";
					break;
				case 6:
					s+="2100";
					break;
					
			}
			
			s+="), ";
		}

		
		if (ccPrec) s+= "\nCC, ";
		
		if (drPrec) s+= "Dr, ";
		
		s+=semesters + " Semesters";
		return s;
	}
	
	/**
	 * A GUI system to help build a trainee 
	 */
	public void GUIBuild(){
		JFrame frame = new JFrame("New Trainee");
		Container window = frame.getContentPane();
		nameInput = new JTextField("Name");
		semesterInput = new JTextField("# semesters");
		ccPrecepting = new JCheckBox("CC Precepting");
		drPrecepting = new JCheckBox("Driver Precepting");
		submit = new JButton("Submit");
		
		
		shiftButton = new JButton[8][SHIFTS_PER_DAY+1];
		
		
		submit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// Integer.parseInt() can throw an error if the value isn't strictly a number
				try {
					name = nameInput.getText();
					semesters = Integer.parseInt(semesterInput.getText());
					ccPrec = ccPrecepting.isSelected();
					drPrec = drPrecepting.isSelected();
					
					for (int d = 1; d < shiftButton.length; d++){
						for (int t = 1; t < shiftButton[0].length; t++){
							if (!shiftButton[d][t].getText().equals("#")){
								prefDay[Integer.parseInt(shiftButton[d][t].getText())-1] = d-1;
								prefTime[Integer.parseInt(shiftButton[d][t].getText())-1] = t-1;
							}
						}
					}
					frame.dispose();
				} catch (NumberFormatException nfe){
					JOptionPane.showMessageDialog(null, "Error: check input types. Probably caused by # semesters field, ensure it is entered as a number");
				}
				
			}
			
		});
		
		// keeps track of how many shifts were already selected
		iterator = 0;
		// set up all the buttons and add them to the window
		for (int d = 1; d < shiftButton.length; d++){
			for (int t = 1; t < shiftButton[0].length; t++){
				shiftButton[d][t] = new JButton("#");
				shiftButton[d][t].addActionListener(this);
				shiftButton[d][t].setActionCommand("pref"+d+","+t);
			}
		}
		
		// non-clickable buttons, but make it clear what buttons are what
		shiftButton[0][0] = new JButton("Time\\Day");
		shiftButton[1][0] = new JButton("Sunday");
		shiftButton[2][0] = new JButton("Monday");
		shiftButton[3][0] = new JButton("Tuesday");
		shiftButton[4][0] = new JButton("Wednesday");
		shiftButton[5][0] = new JButton("Thursday");
		shiftButton[6][0] = new JButton("Friday");
		shiftButton[7][0] = new JButton("Saturday");
		
		shiftButton[0][1] = new JButton("0000 - 0600");
		shiftButton[0][2] = new JButton("0600 - 0900");
		shiftButton[0][3] = new JButton("0900 - 1200");
		shiftButton[0][4] = new JButton("1200 - 1500");
		shiftButton[0][5] = new JButton("1500 - 1800");
		shiftButton[0][6] = new JButton("1800 - 2100");
		shiftButton[0][7] = new JButton("2100 - 0000");
		
		// set up the JFrame and Container with a GridBagLayout
		frame.setVisible(true);
		frame.setVisible(true);
		frame.setSize(800,400);
		frame.setLocationRelativeTo(null);
		window.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// fill all the elements as far out as they need to be
		c.fill = GridBagConstraints.HORIZONTAL;
		
		// Add all the elements in their correct positions
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		window.add(nameInput, c);
		
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 1;
		window.add(semesterInput, c);
		
		c.gridx = 5;
		c.gridy = 0;
		c.gridwidth = 1;
		window.add(ccPrecepting, c);
		
		c.gridx = 6;
		c.gridy = 0;
		c.gridwidth = 1;
		window.add(drPrecepting, c);
		
		c.gridx = 7;
		c.gridy = 0;
		c.gridwidth = 1;
		window.add(submit, c);
		
		for (int d = 0; d < shiftButton.length; d++){
			for (int t = 0; t < shiftButton[0].length; t++){
				c.gridx=d;
				c.gridy=t+1;
				window.add(shiftButton[d][t], c);
			}
		}
		
		//update values to match trainee's
		if (!name.equals("")){
			iterator = 0;
			for (int i = 0; i < Trainee.NUM_PREFERENCES; i++){
				if (prefDay[i] != Trainee.NOT_SET && prefTime[i] != Trainee.NOT_SET){
					System.out.println("Preference #" + i + "is at d:" + prefDay[i] + " t:"+prefTime[i]);
					shiftButton[prefDay[i]+1][prefTime[i]+1].setText(i+1+ "");
					iterator++;
				}
			}
			nameInput.setText(this.getName());
			semesterInput.setText(""+this.numSemesters());
			ccPrecepting.setSelected(this.isCCPrecepting());
			drPrecepting.setSelected(this.isDrPrecepting());
		}
		frame.setVisible(true);
	}
	
	/**
	 * Checks if the Shift passed to it has any of the correct preceptors
	 * @param s Shift to check
	 * @return int: number of preceptors on the shift
	 */
	public int hasPreceptor(Shift s){
		int n = 0;
		
		if (s.hasCCPrec() && this.isCCPrecepting()) n++;
		if (s.hasDrPrec() && this.isDrPrecepting()) n++;
		
		return n;
	}
	
	public int hasCCPreceptor(Shift s){
		if (s.hasCCPrec() && this.isCCPrecepting()) return 1;
		return 0;
	}
	public int hasDrPreceptor(Shift s){
		if (s.hasDrPrec() && this.isDrPrecepting()) return 1;
		return 0;
	}
	
	/**
	 * Saves a list of trainees to a file
	 * @param traineeList - the list of Trainees
	 * @param f - File destination
	 */
	public static void saveTraineeList(ArrayList<Trainee> traineeList, File f){
		PrintWriter writer;
		try {
			writer = new PrintWriter(f);
			writer.println("Weight_CC: " + traineeList.get(0).getCCPWeight());
			writer.println("Weight_DR: " + traineeList.get(0).getDRPWeight());
			writer.println("Weight_Sem: " + traineeList.get(0).getSemWeight());
			writer.println("Weight_Pref: " + traineeList.get(0).getPrefWeight());
			writer.println("----------------");
			for (int i = 0; i < traineeList.size(); i++){
				writer.println(traineeList.get(i).getName());
				writer.println(traineeList.get(i).getInfo());
			}
			writer.close();
			
		} catch (FileNotFoundException e1) {
			
			JOptionPane.showMessageDialog(null, "There was some sort of error when saving...");
		}
	}
	
	/**
	 * Opens a list of trainees
	 * @param s - a Scanner set to read the file where the Trainees are stored
	 * @return ArrayList<Trainee> - an ArrayList containing all the trainees
	 * 								from the file
	 */
	public static ArrayList<Trainee> openTraineeList(Scanner s){
		ArrayList<Trainee> traineeList = new ArrayList<Trainee>();
		// initialize with defaults
		int CCP_Weight = 5;
		int DRP_Weight = 5;
		int Sem_Weight = 2;
		int Pref_Weight = 6;
		
		try {
			String temp = "";
			while (s.hasNext()){
				temp = s.nextLine();
				if (temp.toLowerCase().contains("weight")){
					if(temp.toLowerCase().contains("cc")){
						CCP_Weight = Integer.parseInt(getFinalElementOf(temp));
					}
					if(temp.toLowerCase().contains("dr")){
						DRP_Weight = Integer.parseInt(getFinalElementOf(temp));
					}
					if(temp.toLowerCase().contains("sem")){
						Sem_Weight = Integer.parseInt(getFinalElementOf(temp));
					}
					if(temp.toLowerCase().contains("pref")){
						Pref_Weight = Integer.parseInt(getFinalElementOf(temp));
					}
				}
				else if (temp.contains("----")) break;	
			}
			if (temp.contains("----")){
				while (s.hasNext()){
					traineeList.add(new Trainee(s, CCP_Weight, DRP_Weight, Sem_Weight, Pref_Weight));
				}
			}
			else{
				s.reset();
				while (s.hasNext()){
					traineeList.add(new Trainee(s, CCP_Weight, DRP_Weight, Sem_Weight, Pref_Weight));
				}
			}
			
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Error Reading File" );
		}
			
		return traineeList;
	}
	
	public static String getFinalElementOf(String str){
		int i;
		
		for (i = str.length()-1; i >= 0; i--){
			if (str.charAt(i) == ' ') break;
		}
		return str.substring(i+1);
	}
	
	
	/**
	 * Action even for the GUI Buttons
	 */
	public void actionPerformed(ActionEvent e) {
		// look through all the shift buttons (other than the description ones)
		for (int d = 1; d < shiftButton.length; d++){
			for (int t = 1; t < shiftButton[0].length; t++){
				// if the ActionEvent came from one of the shift buttons
				if (e.getActionCommand().equals("pref"+d+","+t)){
					// check if has already been clicked
					if(shiftButton[d][t].getText().equals("#")){
						// make sure they don't select too many shifts
						if (iterator >= NUM_PREFERENCES) return;
						shiftButton[d][t].setText(iterator+1+"");
						iterator++;
					}
					else{
						// If it has already been selected, drop the number
						// put on all the  preferences after it, then reset its
						// face value to '#' and decrement the iterator
						for (int dd = 1; dd < shiftButton.length; dd++){
							for (int tt = 1; tt < shiftButton[0].length; tt++){
								if(!shiftButton[dd][tt].getText().equals("#")){
									if(Integer.parseInt(shiftButton[dd][tt].getText()) > Integer.parseInt(shiftButton[d][t].getText())){
										shiftButton[dd][tt].setText((Integer.parseInt(shiftButton[dd][tt].getText())-1)+"");
									}
								}
							}
						}
						
						shiftButton[d][t].setText("#");
						iterator--;
					}
				}
			}
		}
		// if the source was from the GUI submit button, try setting all the values to what was put on the GUI window
		/*if (e.getSource() == submit){
			// Integer.parseInt() can throw an error if the value isn't strictly a number
			try {
				name = nameInput.getText();
				semesters = Integer.parseInt(semesterInput.getText());
				ccPrec = ccPrecepting.isSelected();
				drPrec = drPrecepting.isSelected();
				
				for (int d = 1; d < shiftButton.length; d++){
					for (int t = 1; t < shiftButton[0].length; t++){
						if (!shiftButton[d][t].getText().equals("#")){
							prefDay[Integer.parseInt(shiftButton[d][t].getText())-1] = d-1;
							prefTime[Integer.parseInt(shiftButton[d][t].getText())-1] = t-1;
						}
					}
				}
			} catch (NumberFormatException nfe){
				JOptionPane.showMessageDialog(null, "Error: check input types. Probably caused by # semesters field, ensure it is entered as a number");
			}
		}*/
	}
}
