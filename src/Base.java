/*
 * File: Base.java
 * Last Modified: 08/02/2016
 * Written by: Jacob Schwell
 * Description: This file is the hold the main GUI. When the program starts up,
 * 				it creates an instance of this class so the user can utilize 
 * 				this program and do stuff. Each button is labeled with a 
 * 				description of what it does and has an action listener to run
 * 				it's corresponding function. The GUI is built in the constructor
 * 				and the buttons are all run through actionPerformed().
 */


import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;

public class Base implements ActionListener, KeyListener{
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
	public Base() {
		JFrame f = new JFrame("Main Jawn");
		Container window = f.getContentPane();
		JScrollPane pane;
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
		addTrainee = new JButton("Add/Modify Trainee");
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
		f.setSize(475,400);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		pane = new JScrollPane(log);
		pane.setVisible(true);
		window.add(pane);
		f.addKeyListener(this);
		log.addKeyListener(this);
		
		// add all the button and log with appropriate constraints
		window.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
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
		c.weightx = .5;
		c.weighty = 1;
		c.gridwidth = 3;
		c.gridheight = 2;
		window.add(pane,c);
		
		
		// ensures everything in the JFrame shows up
		f.setVisible(true);

	}
	
	public static void updatePriorityWeights(ArrayList<Trainee> tl){
		// frame stuff (implemented later on)
		JFrame f = new JFrame("Modify Priority Weights");
		Container window = f.getContentPane();
		GridBagConstraints c = new GridBagConstraints();
		
		// Assume all weights are the same across Trainees, take
		// the baseline from the first in the arraylist
		int CCP_Weight = tl.get(0).getCCPWeight();
		int DrP_Weight = tl.get(0).getDRPWeight();
		int Sem_Weight = tl.get(0).getSemWeight();
		int Pref_Weight = tl.get(0).getPrefWeight();
		
		// set min and max values for all weights
		int min = 0;
		int max = 10;
		
		// make a JSlider for each weight
		JSlider CC = new JSlider(JSlider.HORIZONTAL, min, max, CCP_Weight);
		CC.setPaintTicks(true);
		CC.setPaintLabels(true);
		CC.setPaintTrack(true);
		CC.setMajorTickSpacing(1);
		JLabel CC_Label = new JLabel("CC Precepting Weight: ");
		
		JSlider Dr = new JSlider(JSlider.HORIZONTAL, min, max, DrP_Weight);
		Dr.setPaintTicks(true);
		Dr.setPaintLabels(true);
		Dr.setPaintTrack(true);
		Dr.setMajorTickSpacing(1);
		JLabel Dr_Label = new JLabel("Driver Precepting Weight: ");
		
		JSlider Sem = new JSlider(JSlider.HORIZONTAL, min, max, Sem_Weight);
		Sem.setPaintTicks(true);
		Sem.setPaintLabels(true);
		Sem.setPaintTrack(true);
		Sem.setMajorTickSpacing(1);
		JLabel Sem_Label = new JLabel("Semester Weight: ");
		
		JSlider Pref = new JSlider(JSlider.HORIZONTAL, min, max, Pref_Weight);
		Pref.setPaintTicks(true);
		Pref.setPaintLabels(true);
		Pref.setPaintTrack(true);
		Pref.setMajorTickSpacing(1);
		JLabel Pref_Label = new JLabel("Preference Weight: ");
		
		// create save/cancel buttons
		JButton apply = new JButton("Apply");
		JButton cancel = new JButton("Cancel");
		JButton default_button = new JButton("Default");
		
		apply.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				for (Trainee t: tl){
					t.setCCPWeight(CC.getValue());
					t.setDRPWeight(Dr.getValue());
					t.setSemWeight(Sem.getValue());
					t.setPrefWeight(Pref.getValue());
				}
				f.dispose();
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				f.dispose();
			}
		});
		default_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				for (Trainee t: tl){
					t.setCCPWeight(Trainee.DEFAULT_CC_WEIGHT);
					t.setDRPWeight(Trainee.DEFAULT_DR_WEIGHT);
					t.setSemWeight(Trainee.DEFAULT_SEM_WEIGHT);
					t.setPrefWeight(Trainee.DEFAULT_PREF_WEIGHT);
				}
				f.dispose();
			}
			
		});
		
		// Create frame and container and add everything to it
		f.setSize(500,400);
		f.setVisible(true);
		
		window.setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2;
		c.weighty = 1;
		
		c.gridx = 0;
		c.gridy = 0;
		window.add(CC_Label, c);
		
		c.gridx = 1;
		c.gridy = 0;
		window.add(CC, c);
		
		c.gridx = 0;
		c.gridy = 1;
		window.add(Dr_Label, c);

		c.gridx = 1;
		c.gridy = 1;
		window.add(Dr, c);
		
		c.gridx = 0;
		c.gridy = 2;
		window.add(Sem_Label, c);

		c.gridx = 1;
		c.gridy = 2;
		window.add(Sem, c);
		
		c.gridx = 0;
		c.gridy = 3;
		window.add(Pref_Label, c);
		
		c.gridx = 1;
		c.gridy = 3;
		window.add(Pref, c);
		
		c.gridx = 0;
		c.gridy = 4;
		window.add(apply, c);
		
		c.gridx = 1;
		c.gridy = 4;
		window.add(default_button, c);
		
		c.gridx = 2;
		c.gridy = 4;
		window.add(cancel, c);
		
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
			String[] options = {"Add New", "Modify Existing" };
			int response =JOptionPane.showOptionDialog(null, "What would you like to do?", "Add new or Modify Existing Trainee", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]); 
			if ( response == JOptionPane.YES_OPTION){
				traineeList.add(new Trainee());
				traineeList.get(traineeList.size()-1).GUIBuild();
			}
			else if (response == JOptionPane.NO_OPTION){
				int num = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the number next to the trainee you wish to modify", "Modify Existing Trainee", JOptionPane.OK_CANCEL_OPTION));
				traineeList.get(num).GUIBuild();
			}
			
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
					traineeList.addAll(Trainee.openTraineeList(s));
					
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
					schedule = Shift.openSchedule(s);
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
			fc.showSaveDialog(null);
			// update filetype
			if (fc.getSelectedFile().getPath().endsWith(Trainee.FILE_EXT))
				Trainee.saveTraineeList(traineeList, fc.getSelectedFile());
			else{
				String path = fc.getSelectedFile().getPath();
				if (path.indexOf(".") == -1) path += ".";
				path = path.substring(0, path.indexOf("."))+Trainee.FILE_EXT;

				Trainee.saveTraineeList(traineeList, new File(path));
			}
		}
		else if (e.getSource() == saveSchedule){
			JFileChooser fc = new JFileChooser("Save Schedule");
			fc.setDialogTitle("Save Preceptor Schedule");			
			fc.showSaveDialog(null);
			try {
				if (fc.getSelectedFile().getPath().endsWith(Shift.FILE_EXT)){
					Shift.saveSchedule(schedule, fc.getSelectedFile());
					scheduleFile=fc.getSelectedFile().getName();
				}
				else {
					String path = fc.getSelectedFile().getPath();
					if (path.indexOf(".") == -1) path += ".";
					path = path.substring(0,path.indexOf(".")) + Shift.FILE_EXT;
					Shift.saveSchedule(schedule, new File(path));
					scheduleFile=path;
				}
			} catch (Exception e1) {
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
			log.append("("+i+") " + traineeList.get(i).print() + "\n\n");
		}
		
	}

	public void keyPressed(KeyEvent e) {
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
			case KeyEvent.VK_A:
				String[] options = {"Add New", "Modify Existing" };
				int response =JOptionPane.showOptionDialog(null, "What would you like to do?", "Add new or Modify Existing Trainee", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]); 
				if ( response == JOptionPane.YES_OPTION){
					traineeList.add(new Trainee());
					traineeList.get(traineeList.size()-1).GUIBuild();
				}
				else if (response == JOptionPane.NO_OPTION){
					int num = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the number next to the trainee you wish to modify", "Modify Existing Trainee", JOptionPane.OK_CANCEL_OPTION));
					traineeList.get(num).GUIBuild();
				}
				break;
			case KeyEvent.VK_B:
				Shift.buildPreceptorSchedule(schedule);
				break;
			case KeyEvent.VK_R:
				break;
			case KeyEvent.VK_Q:
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?") == JOptionPane.YES_OPTION){
					System.exit(1);
				}
				break;
			case KeyEvent.VK_G:
				try {
					Main.run(traineeList, schedule);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Uh oh! Something bad happened...");
				}
				break;
			case KeyEvent.VK_H:
				JTextArea msg = new JTextArea(
						  "A -> Add or modify trainee\n"
						+ "B -> Build or edit preceptor schedule\n"
						+ "R -> Refresh info log\n"
						+ "Q -> Quit program\n"
						+ "G -> Run scheduling program with current trainees\n");
				msg.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
				msg.setLineWrap(false);
				JOptionPane.showMessageDialog(null, msg);
				break;
			case KeyEvent.VK_P:
				updatePriorityWeights(traineeList);
				
		}
		
		// UPDATE LOG
		log.setText("Schedule File: " + scheduleFile + "\n\nTrainees:\n");
		for (int i = 0; i < traineeList.size(); i++){
			log.append("("+i+") " + traineeList.get(i).print() + "\n\n");
		}
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
}
