 import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Trainee {
	public static final int NUM_PREFERENCES = 6;
	public static final int SHIFTS_PER_DAY = 7;
	int prefDay[];
	int prefTime[];
	int iterator;
	int semesters;
	boolean ccPrec;
	boolean drPrec;
	String name;
	
	
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
			s+= prefDay[i] + "\t" + prefTime[i]+'\t';
		}
		if (ccPrec) s+= "y\t";
		else s+="n\t";
		
		if (drPrec) s+= "y\t";
		else s+="n\t";
		
		s+=semesters+"\n";
		
		return s;
	}
	
}
