
public class Timer {

	public static int getTimeSec(int base) {
		int curr = 0, sec = 0;
		
		curr = (int) System.currentTimeMillis();
		sec = (curr - base) / 1000;
		
		return sec;
	}
	
}
