package running;

public class Run {
	public double distance; //in kms
	public int time; //in seconds

	public Run prev;
	public Run next;

	//DO NOT MODIFY - Parameterized constructor
	public Run(double d, int t) {
		distance = Math.max(0, d);
		time = Math.max(1, t);
	}
	
	//DO NOT MODIFY - Copy Constructor to create an instance copy
	//NOTE: Only the data section should be copied over, not the links
	public Run(Run source) {
		this(source.distance, source.time);
	}

	//DO NOT MODIFY (return speed in kmph)
	public double speed() {
		return distance*3600/time;
	}

	/**
	 * add an INSTANCE COPY of the passed object (using the copy constructor)
	 * at the end of the list in which the calling object exists. 
	 * @param run
	 */
	public void addToEnd(Run run) {
		//to be completed
	}

	/**
	 * add an INSTANCE COPY of the passed object (using the copy constructor)
	 * at the front of the list in which the calling object exists
	 * @param run
	 */
	public void addToFront(Run run) {
		//to be completed
	}

	/**
	 * 
	 * @return number of objects in the list in which the calling object exists
	 */
	public int size() {
		return 0; //to be completed
	}

	/**
	 * 
	 * @return the index of the calling object in the list.
	 * the index of the first object in the list is 0.
	 */
	public int getIndex() {
		return 0; //to be completed
	}

	/**
	 * 
	 * @param idx
	 * @return the object that exists in the list at the passed index.
	 * return null if there is no object at that index
	 */
	public Run get(int idx) {
		return null; //to be completed
	}
	
	/**
	 * return a text version of the list in which the calling object exists.
	 * use "->" as the separator.
	 */
	public String toString() {
		return null; //to be completed
	}
	
	//DO NOT MODIFY
	public String toStringIndividual() {
		return distance+" in "+time;
	}
	
	/**
	 * insert an INSTANCE COPY of the second parameter (using the copy constructor)
	 * at index idx, thereby pushing all subsequent items one place higher 
	 * (in terms of index).
	 * @param idx
	 * @param run
	 * @return true if an INSTANCE COPY was successfully added at 
	 * the given index in the list, false otherwise.
	 */
	public boolean add(int idx, Run run) {
		return false; //to be completed
	}

	/**
	 * 
	 * @param thresholdSpeed
	 * @return the highest number of consecutive items in the list
	 * (to which the calling object belongs) that have a speed more than
	 * the thresholdSpeed.
	 */
	public int longestSequenceOver(double thresholdSpeed) {
		return 0; //to be completed
	}

	/**
	 * 
	 * @param thresholdSpeed
	 * @return an array containing representations of the runs (in order of sequence)
	 * in the list to which the calling object belongs, that have a speed more than
	 * the thresholdSpeed. return null if no such item exists in the list.
	 */
	public String[] getRunsOver(double thresholdSpeed) {
		return null; //to be completed
	}
}
