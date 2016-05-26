
public class Shift {
	boolean drPrec;
	boolean ccPrec;
	Trainee trainee;
	
	Shift(){
		drPrec = false;
		ccPrec = false;
		trainee = null;
	}
	
	Shift(boolean driverPreceptor, boolean ccPreceptor, Trainee trainee){
		drPrec = driverPreceptor;
		ccPrec = ccPreceptor;
		this.trainee = trainee;
	}
	Shift(boolean driverPreceptor, boolean ccPreceptor){
		drPrec = driverPreceptor;
		ccPrec = ccPreceptor;
		trainee = null;
	}
	public boolean hasDrPrec(){
		return drPrec;
	}
	
	public boolean hasCCPrec(){
		return ccPrec;
	}
	public boolean hasTrainee(){
		if (trainee == null) return false;
		return true;
	}
	
	public void setTrainee(Trainee t){
		trainee = t;
	}
	public Trainee getTrainee(){
		return trainee;
	}
	
}
