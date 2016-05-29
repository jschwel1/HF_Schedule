import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Shift{
	boolean drPrec;
	boolean ccPrec;
	Trainee trainee;
	public static int buildState;
	
	
	/**
	 * Build a Shift object without any preceptors or Trainees
	 */
	Shift(){
		drPrec = false;
		ccPrec = false;
		trainee = null;
	}
	
	/**
	 * Build a Shift object
	 * @param driverPreceptor - Boolean: Driver Preceptor or not
	 * @param ccPreceptor - Boolean: Crew Chief Preceptor or not
	 * @param trainee - Boolean: Trainee or not
	 */
	Shift(boolean driverPreceptor, boolean ccPreceptor, Trainee trainee){
		drPrec = driverPreceptor;
		ccPrec = ccPreceptor;
		this.trainee = trainee;
	}
	
	/**
	 * Build a Shift object, without a Trainee
	 * @param driverPreceptor - Boolean: Driver Preceptor or not
	 * @param ccPreceptor - Boolean: Crew Chief Preceptor or not
	 */
	Shift(boolean driverPreceptor, boolean ccPreceptor){
		drPrec = driverPreceptor;
		ccPrec = ccPreceptor;
		trainee = null;
	}
	
	/**
	 * Sets whether  or not there is a CC Preceptor
	 * @param b - boolean - CC preceptor or not
	 */
	public void setCCPreceptor(boolean b){
		ccPrec = b;
	}
	
	/**
	 * Sets whether or not there is a Driver Preceptor
	 * @param b - boolean: Driver Preceptor or not
	 */
	public void setDrPreceptor(boolean b){
		drPrec = b;
	}
	
	/**
	 * Determines if there is a driver preceptor on shift
	 * @return Boolean, whether or not a driver preceptor is on shift
	 */
	public boolean hasDrPrec(){
		return drPrec;
	}
	
	/**
	 * Determines if there is a crew chief preceptor on shift
	 * @return Boolean, whether or not a crew chief preceptor is on shift
	 */
	public boolean hasCCPrec(){
		return ccPrec;
	}
	
	/**
	 * Determines if a trainee is on shift or not
	 * @return boolean, whether or not a trainee is on shift
	 */
	public boolean hasTrainee(){
		if (trainee == null) return false;
		return true;
	}
	
	/**
	 * Places (or removes) trainee a trainee on (or from) the shift
	 * @param t - Trainee object (null to remove)
	 */
	public void setTrainee(Trainee t){
		trainee = t;
	}
	/**
	 * Gets the Trainee currently on shift
	 * @return Trainee: the current trainee on shift (null if none)
	 */
	public Trainee getTrainee(){
		return trainee;
	}
	
	/**
	 * GUI window to set what kind of preceptors are on which shifts
	 * @param schedule - Matrix of [7][Trainee.SHIFTS_PER_DAY] to store the values ins
	 */
	public static void buildPreceptorSchedule(Shift[][] schedule){
		
		JFrame frame = new JFrame("Which shifts  have CC Preceptors");
		JButton submit = new JButton("Next");
		JButton clearAll = new JButton("Clear All");
		JButton checkAll = new JButton("Check All");
		Container window = frame.getContentPane();
		GridBagConstraints c = new GridBagConstraints();
		JCheckBox box[][] = new JCheckBox[7][Trainee.SHIFTS_PER_DAY];
		JTextField days[] = new JTextField[7];
		
		days[0] = new JTextField("Sunday");
		days[1] = new JTextField("Monday");
		days[2] = new JTextField("Tuesday");
		days[3] = new JTextField("Wednesday");
		days[4] = new JTextField("Thursday");
		days[5] = new JTextField("Friday");
		days[6] = new JTextField("Saturday");
		
		frame.setSize(500,350);
		frame.setVisible(true);
		//frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		window.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridheight = 1;
		c.gridwidth = 1;
		
		c.gridy=0;
		// add day labels
		for (int i = 0; i < 7; i++){
			c.gridx = i;
			days[i].setEditable(false);
			window.add(days[i], c);
		}
		// add submit button
		c.gridx = 7;
		
		submit.setActionCommand("Next");
		window.add(submit, c);
		
		clearAll.setActionCommand("clearAll");
		c.gridy = 1;
		window.add(clearAll, c);
		
		checkAll.setActionCommand("checkAll");
		c.gridy = 2;
		window.add(checkAll, c);
		// add checkboxes
		for (int d = 0; d < 7; d++){
			for (int t = 0; t < Trainee.SHIFTS_PER_DAY; t++){
				switch (t){
					case 0:
						box[d][t] = new JCheckBox("0000");
						break;
					case 1:
						box[d][t] = new JCheckBox("0600");
						break;
					case 2:
						box[d][t] = new JCheckBox("0900");
						break;
					case 3:
						box[d][t] = new JCheckBox("1200");
						break;
					case 4:
						box[d][t] = new JCheckBox("1500");
						break;
					case 5:
						box[d][t] = new JCheckBox("1800");
						break;
					case 6:
						box[d][t] = new JCheckBox("2100");
						break;
					default:
						box[d][t] = new JCheckBox("####");
						break;
					
				}
				c.gridx = d;
				c.gridy = t+1;
				window.add(box[d][t], c);
			}
		}
		
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (e.getActionCommand().equals("Next")){
					for (int d = 0; d < 7; d++){
						for (int t = 0; t < Trainee.SHIFTS_PER_DAY; t++){
							schedule[d][t] = new Shift();
							schedule[d][t].setCCPreceptor(box[d][t].isSelected());
						}
					}
					submit.setActionCommand("Submit");
					submit.setText("Submit");
					frame.setTitle("Which shifts have Driver Preceptors?");
				}
				else if (e.getActionCommand().equals("Submit")){
					for (int d = 0; d < 7; d++){
						for (int t = 0; t < Trainee.SHIFTS_PER_DAY; t++){
							schedule[d][t].setDrPreceptor(box[d][t].isSelected());
						}
					}
					submit.setActionCommand("Next");
					submit.setText("Next");
					Shift.showPreceptorSchedule(schedule);
					frame.dispose();
				}
				
			}
		});
		
		clearAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (e.getActionCommand().equals("clearAll")){
					for (int i = 0; i < box.length; i++){
						for (int j =0; j < box[0].length; j++){
							box[i][j].setSelected(false);
						}
					}
				}
			}
		});
		checkAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (e.getActionCommand().equals("checkAll")){
					for (int i = 0; i < box.length; i++){
						for (int j =0; j < box[0].length; j++){
							box[i][j].setSelected(true);
						}
					}
				}
			}
		});
		
		
	}
	
	/**
	 * Displays the weekly schedule with Trainee names in the appropriate spots
	 * @param schedule - The matrix of Shift objects to read the names from
	 */
	public static void showSchedule(Shift[][] schedule){
		JFrame frame = new JFrame("Schedule");
		Container window = frame.getContentPane();
		JTextField blocks[][] = new JTextField[schedule.length][schedule[0].length];
		JTextField days[] = new JTextField[7];
		JTextField times[] = new JTextField[Trainee.SHIFTS_PER_DAY];
		GridBagConstraints c;
		
		frame.setVisible(true);
		frame.setSize(700,700);
	
		
		window.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridheight = 1;
		c.gridwidth = 1;
		
		days[0] = new JTextField("Sunday");
		days[1] = new JTextField("Monday");
		days[2] = new JTextField("Tuesday");
		days[3] = new JTextField("Wednesday");
		days[4] = new JTextField("Thursday");
		days[5] = new JTextField("Friday");
		days[6] = new JTextField("Saturday");
		
		times[0] = new JTextField("0000");
		times[1] = new JTextField("0600");
		times[2] = new JTextField("0900");
		times[3] = new JTextField("1200");
		times[4] = new JTextField("1500");
		times[5] = new JTextField("1800");
		times[6] = new JTextField("2100");
		
		c.gridy = 0;
		for (int i = 0; i<7; i++){
			c.gridx = i+1;
			days[i].setEditable(false);
			window.add(days[i], c);
		}
		
		c.gridx = 0;
		for (int i = 0; i<Trainee.SHIFTS_PER_DAY; i++){
			c.gridy = i+1;
			times[i].setEditable(false);
			window.add(times[i], c);
		}
		
		
		
		for (int d = 0; d < blocks.length; d++){
			for (int t = 0; t < blocks[0].length; t++){
				if (schedule[d][t].hasTrainee())
					blocks[d][t] = new JTextField(schedule[d][t].getTrainee().getName());
				else
					blocks[d][t] = new JTextField();
				blocks[d][t].setEditable(false);
				c.gridx = d+1;
				c.gridy = t+1;
				window.add(blocks[d][t], c);
				System.out.println("Adding ("+d+", "+t+")");
			}
		}
	}
	/**
	 * Displays a window with a weekly schedule of when what preceptors will be on shifts
	 * @param schedule - Matrix of Shifts to read the values from
	 */
	public static void showPreceptorSchedule(Shift[][] schedule){
		JFrame frame = new JFrame("Schedule");
		Container window = frame.getContentPane();
		JTextField blocks[][] = new JTextField[schedule.length][schedule[0].length];
		JTextField days[] = new JTextField[7];
		JTextField times[] = new JTextField[Trainee.SHIFTS_PER_DAY];
		GridBagConstraints c;
		
		frame.setVisible(true);
		frame.setSize(500,500);
		
		
		window.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = 1;
		
		days[0] = new JTextField("Sunday");
		days[1] = new JTextField("Monday");
		days[2] = new JTextField("Tuesday");
		days[3] = new JTextField("Wednesday");
		days[4] = new JTextField("Thursday");
		days[5] = new JTextField("Friday");
		days[6] = new JTextField("Saturday");
		
		times[0] = new JTextField("0000");
		times[1] = new JTextField("0600");
		times[2] = new JTextField("0900");
		times[3] = new JTextField("1200");
		times[4] = new JTextField("1500");
		times[5] = new JTextField("1800");
		times[6] = new JTextField("2100");
		
		c.gridy = 0;
		for (int i = 0; i<7; i++){
			c.gridx = i+1;
			days[i].setEditable(false);
			window.add(days[i], c);
		}
		
		c.gridx = 0;
		for (int i = 0; i<Trainee.SHIFTS_PER_DAY; i++){
			c.gridy = i+1;
			times[i].setEditable(false);
			window.add(times[i], c);
		}
		
		
		for (int d = 0; d < blocks.length; d++){
			for (int t = 0; t < blocks[0].length; t++){
				String s = "";
				if (schedule[d][t].hasCCPrec()) s+= "[CC] ";
				else s+="";
				if (schedule[d][t].hasDrPrec()) s+= "[Dr]";
				else s+="";
				
				blocks[d][t] = new JTextField(s);
				blocks[d][t].setEditable(false);
				blocks[d][t].setHorizontalAlignment(JTextField.CENTER);
				
				//blocks[d][t].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
				c.gridx = d+1;
				c.gridy = t+1;
				window.add(blocks[d][t], c);
			}
		}
	}


	
	
	
}
