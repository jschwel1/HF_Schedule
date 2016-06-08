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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Base implements ActionListener{
	JButton run;
	JButton addTrainee;
	JButton openTraineeList;
	JButton openSchedule;
	JButton buildSchedule;
	JButton saveTraineeList;
	JButton saveSchedule;
	JButton refreshButton;
	JButton removeTrainee;
	JTextArea log;
	ArrayList<Trainee> traineeList;
	Shift schedule[][];
	String scheduleFile;
	
	/**
	 * Constructs the base station (window opened when initially run) which has
	 * options to add trainees, build schedules, open and save files of trainee 
	 * lists or preceptor schedules and run the trainee sort.
	 */
	public Base(){
		JFrame f = new JFrame("Main Jawn");
		Container window = f.getContentPane();
		JScrollPane pane = new JScrollPane();
		GridBagConstraints c = new GridBagConstraints();
		scheduleFile = "";
		/* Initialize Trainee list and schedule variables */
		traineeList = new ArrayList<Trainee>();
		schedule = new Shift[7][Trainee.SHIFTS_PER_DAY];
		
		for (int i = 0; i < 7; i++){
			for (int j = 0; j < Trainee.SHIFTS_PER_DAY; j++){
				schedule[i][j] = new Shift();
			}
		}
		
		/* Startup the GUI */
		
		log = new JTextArea();
		run = new JButton("Run");
		addTrainee = new JButton("Add Trainee");
		openTraineeList = new JButton("Open Trainee List");
		openSchedule = new JButton("Open Schedule");
		buildSchedule = new JButton("Build Schedule");
		saveTraineeList = new JButton("Save Trainee List");
		saveSchedule = new JButton("Save Schedule");
		refreshButton = new JButton("Refresh");
		removeTrainee = new JButton("Remove Trainee");
		
		// set log settings
		log.setEditable(false);
		
		// set button settings
		run.addActionListener(this);
		addTrainee.addActionListener(this);
		openTraineeList.addActionListener(this);
		openSchedule.addActionListener(this);
		buildSchedule.addActionListener(this);
		saveTraineeList.addActionListener(this);
		saveSchedule.addActionListener(this);
		refreshButton.addActionListener(this);
		removeTrainee.addActionListener(this);
		// set JFrame stuff
		f.setSize(400,400);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
//		window.add(pane);
		
		// add all the button and log with appropriate constraints
		window.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 0;
		
		c.gridx = 0;
		c.gridy = 0;
		window.add(addTrainee, c);
		
		c.gridx = 1;
		c.gridy = 0;
		window.add(openTraineeList, c);
		
		c.gridx = 2;
		c.gridy = 0;
		window.add(saveTraineeList, c);
		
		c.gridx = 0;
		c.gridy = 1;
		window.add(buildSchedule, c);
		
		c.gridx = 1;
		c.gridy = 1;
		window.add(openSchedule, c);
		
		c.gridx = 2;
		c.gridy = 1;
		window.add(saveSchedule, c);
		
		c.gridx = 0;
		c.gridy = 3;
		window.add(refreshButton, c);
		
		c.gridx = 1;
		c.gridy = 3;
		window.add(removeTrainee, c);
		
		c.gridx = 2;
		c.gridy = 3;
		window.add(run, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 4;
		c.ipady = 200;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 3;
		c.gridheight = 2;
		window.add(log, c);

	}

/**
 * Checks the Action Events and performs the appropriate commands
 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == run){
			try {
				Main.run(traineeList, schedule);
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "Uh oh! Something bad happened...");
			}
		}
		else if (e.getSource() == addTrainee){
			log.append("New Trainee\n");
			traineeList.add(new Trainee());
			traineeList.get(traineeList.size()-1).GUIBuild();

			
		}
		else if (e.getSource() == openTraineeList){
			JFileChooser fc = new JFileChooser("Open trainee list file");
			fc.setDialogTitle("Open trainee list file");
			File file;
			Scanner s;
			
			if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				file = fc.getSelectedFile();
				try {
					s = new Scanner(file);
					while (s.hasNext()){
						traineeList.add(new Trainee(s));
					}
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Could not read file: " + file.getName());
				}
				
			}
		}
		else if (e.getSource() == openSchedule){
			JFileChooser fc = new JFileChooser("Open existing schedule file");
			fc.setDialogTitle("Open existing schedule file");
			File file;
			Scanner s;
			
			if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				file = fc.getSelectedFile();
				try {
					s = new Scanner(file);
					String str;
					for (int t = 0; t < Trainee.SHIFTS_PER_DAY; t++){
						for (int d = 0; d < 7; d++){	
							schedule[d][t] = new Shift();
							str = s.next();
							if (str.charAt(0) == 'y') schedule[d][t].setCCPreceptor(true);
							else schedule[d][t].setCCPreceptor(false);
							System.out.println("("+d+", "+t+") -> " + str);
						}
					}
					for (int t = 0; t < Trainee.SHIFTS_PER_DAY; t++){
						for (int d = 0; d < 7; d++){	
							str = s.next();
							if (str.charAt(0) == 'y') schedule[d][t].setDrPreceptor(true);
							else schedule[d][t].setDrPreceptor(false);
							System.out.println("("+d+", "+t+") -> \"" + str+"\"");
						}
					}
					Shift.showPreceptorSchedule(schedule);
					scheduleFile=fc.getSelectedFile().getName();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Could not read file: " + file.getName());
				}
				
			}
		}
		else if (e.getSource() == buildSchedule){
			Shift.buildPreceptorSchedule(schedule);
		}
		else if (e.getSource() == saveTraineeList){
			JFileChooser fc = new JFileChooser("Save trainee list");
			fc.setDialogTitle("Save Trainee List");
			PrintWriter writer;
			
			fc.showSaveDialog(null);
			try {
				writer = new PrintWriter(fc.getSelectedFile());
			
				for (int i = 0; i < traineeList.size(); i++){
					writer.println(traineeList.get(i).getName());
					writer.println(traineeList.get(i).getInfo());
				}
				writer.close();
				
			} catch (FileNotFoundException e1) {
				
				JOptionPane.showMessageDialog(null, "There was some sort of error when saving...");
			}
		}
		else if (e.getSource() == saveSchedule){
			JFileChooser fc = new JFileChooser("Save Schedule");
			fc.setDialogTitle("Save Preceptor Schedule");
			PrintWriter writer;
			
			fc.showSaveDialog(null);
			try {
				writer = new PrintWriter(fc.getSelectedFile());
				for (int d = 0; d < 7; d++){
					for (int t = 0; t < Trainee.SHIFTS_PER_DAY; t++){
						if(schedule[d][t].hasCCPrec()) writer.print("y ");
						else writer.print("n ");
					}
					writer.println();
				}
				writer.println();
				for (int d = 0; d < 7; d++){
					for (int t = 0; t < Trainee.SHIFTS_PER_DAY; t++){
						if(schedule[d][t].hasDrPrec()) writer.print("y ");
						else writer.print("n ");
					}
					writer.println();
				}
				scheduleFile=fc.getSelectedFile().getName();
				writer.close();
			} catch (FileNotFoundException e1) {
				
				JOptionPane.showMessageDialog(null, "There was some sort of error when saving...");
			}
		}
		else if (e.getSource() == removeTrainee){
			int index = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of the trainee you would like to remove (one at a time)"));
			traineeList.remove(index);
		}
		// UPDATE LOG
		log.setText("Schedule File: " + scheduleFile + "\n\nTrainees:\n");
		for (int i = 0; i < traineeList.size(); i++){
			log.append("("+i+") " + traineeList.get(i).toString() + "\n\n");
		}
		
	}
}
