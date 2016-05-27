 import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Trainee implements ActionListener{
	public static final int NUM_PREFERENCES = 6;
	public static final int SHIFTS_PER_DAY = 7;
	int prefDay[];
	int prefTime[];
	int iterator;
	int semesters;
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
	
	Trainee(){
		prefDay = new int[NUM_PREFERENCES];
		prefTime = new int[NUM_PREFERENCES];
		iterator = 0;
		
	}
	
	Trainee(Scanner s) throws FileNotFoundException{
		this();
		int count = 0;
		int input;
		char ans;

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
	
	
	public int getIter(){
		return iterator;
	}
	
	public int getPrefDay(){
		return prefDay[iterator];
	}
	public int getPrefTime(){
		return prefTime[iterator];
	}
	public boolean isCCPrecepting(){
		return ccPrec;
	}
	public boolean isDrPrecepting(){
		return drPrec;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean hasHigherPriorityThan(Trainee t){
		int p1 = this.getIter()+(this.numSemesters()/2);
		int p2 = t.getIter()+(t.numSemesters()/2);
		
		if (p1 > p2) return true;
		
		return false;
	}

	public int numSemesters(){
		return semesters;
	}
	
	public void setIterator(int i){
		iterator = i;
	}
	public void nextChoice(){
		if (++iterator > NUM_PREFERENCES) iterator--;
	}
	
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

	public void GUIBuild(){
		JFrame frame = new JFrame("New Trainee");
		Container window = frame.getContentPane();
		nameInput = new JTextField("Name");
		semesterInput = new JTextField("# semesters");
		ccPrecepting = new JCheckBox("CC Precepting");
		drPrecepting = new JCheckBox("Driver Precepting");
		submit = new JButton("Submit");
		
		
		shiftButton = new JButton[8][SHIFTS_PER_DAY+1];
		
		
		submit.addActionListener(this);
		submit.setActionCommand("submit");
		
		iterator = 0;
		for (int d = 1; d < shiftButton.length; d++){
			for (int t = 1; t < shiftButton[0].length; t++){
				shiftButton[d][t] = new JButton("#");
				shiftButton[d][t].addActionListener(this);
				shiftButton[d][t].setActionCommand("pref"+d+","+t);
			}
		}
		
		shiftButton[0][0] = new JButton("Time\\Day");
		shiftButton[1][0] = new JButton("Sunday");
		shiftButton[2][0] = new JButton("Monday");
		shiftButton[3][0] = new JButton("Tuesday");
		shiftButton[4][0] = new JButton("Wednesday");
		shiftButton[5][0] = new JButton("Thursday");
		shiftButton[6][0] = new JButton("Friday");
		shiftButton[7][0] = new JButton("Saturday");
		
		shiftButton[0][1] = new JButton("0000");
		shiftButton[0][2] = new JButton("0600");
		shiftButton[0][3] = new JButton("0900");
		shiftButton[0][4] = new JButton("1200");
		shiftButton[0][5] = new JButton("1500");
		shiftButton[0][6] = new JButton("1800");
		shiftButton[0][7] = new JButton("2100");
		
		frame.setVisible(true);
		frame.setVisible(true);
		frame.setSize(800,600);
		frame.setLocationRelativeTo(null);
		window.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
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
		
		
		
		//while (iterator < NUM_PREFERENCES){}
		//iterator = 0;
		
		// add information
		
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		for (int d = 1; d < shiftButton.length; d++){
			for (int t = 1; t < shiftButton[0].length; t++){
				if (e.getActionCommand().equals("pref"+d+","+t)){
					
					if(shiftButton[d][t].getText().equals("#")){
						if (iterator >= NUM_PREFERENCES) return;
						shiftButton[d][t].setText(iterator+1+"");
						iterator++;
					}
					else{
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
		
		if (e.getSource() == submit){
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
		}
	}
	
	
	
}
