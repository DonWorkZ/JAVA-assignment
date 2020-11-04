//package running;

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
		Run temporary=new Run(run);
		if(this.next==null && this.prev ==null){
			this.next=temporary;
			this.next.prev=this;
			this.next.next=null;
			temporary=null;
			
		}else{
			Run temp=this;
			while(temp.next!=null){
				temp=temp.next;
			}
			temp.next=temporary;
			temporary.prev=temp;
			temporary.next=null;
			temporary=null;
			temp=null;
		} 
	}

	/**
	 * add an INSTANCE COPY of the passed object (using the copy constructor)
	 * at the front of the list in which the calling object exists
	 * @param run
	 */
	public void addToFront(Run run) {
		Run temporary=new Run(run);
		if(this.next==null&& this.prev==null){
			this.prev=temporary;
			temporary.next=this;
			temporary.prev=null;
			temporary=null;
		}else{
			Run temp=this;
			while(temp.prev!=null){
				temp=temp.prev;
			}
			temp.prev=temporary;
			temporary.prev=null;
			temporary.next=temp;
			temporary=null;
			temp=null;
		}
	}

	/**
	 * 
	 * @return number of objects in the list in which the calling object exists
	 */
	public int size() {
		if(this==null){return 0;}
		if(this.prev==null&&this.next==null){return 1;}
		else{
			int count=0;
			Run temp=this;
			while(temp.prev!=null){
				temp=temp.prev;
			}
			if(temp.prev==null){
				while(temp!=null){
					count++;
					temp=temp.next;
				}
			}
			return count;          
		}
	}

	/**
	 * 
	 * @return the index of the calling object in the list.
	 * the index of the first object in the list is 0.
	 */
	public int getIndex() {
		if(this==null){return -1;}
            if(this.prev==null&&this.next==null){
		return 0;
            }else{
                int count=0;
		Run temp=this;
                while(temp.prev!=null){
                    temp=temp.prev;
                }
                if(temp.prev==null){
                    while(temp!=this){
                        count++;
                        temp=temp.next;
                    }
                }
                return count;          
            }
	}

	/**
	 * 
	 * @param idx
	 * @return the object that exists in the list at the passed index.
	 * return null if there is no object at that index
	 */
	public Run get(int idx) {
		if(idx<0&&idx>this.size()){return null;}
		if(this==null){return null;}
        if(idx==0&&this.next==null&& this.prev==null){return this;}
        else{
			Run temp=this;
            while(temp.prev!=null){temp=temp.prev;}
            for(int i=0;i<idx;++i){temp=temp.next;}
            return temp;
        }
	}
	
	/**
	 * return a text version of the list in which the calling object exists.
	 * use "->" as the separator.
	 */
	public String toString() {
		if(this==null){return null;}
		if(this.next==null&& this.prev==null){ return "Distance :"+this.distance+" Time :"+this.time;}
		else{
			String str="";
			Run temp=this;
			while(temp.prev!=null){temp=temp.prev;}
			while(temp.next!=null){
				str=str+temp.distance+" in "+temp.time+"  -->  ";
				temp=temp.next;
			}
			str=str+temp.distance+" in "+temp.time;
			return str;
		}
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
		Run temporary=new Run(run);
		if(idx<0&&idx>this.size()){return false;}
		if(run==null){return false;}
		if(idx==0&&this.next==null&& this.prev==null){this.addToFront(temporary); return true;}
		else{
			Run temp=this;
			while(temp.prev!=null){temp=temp.prev;}
			for(int i=0;i<idx;++i){temp=temp.next;}
			temporary.prev=temp.prev;
			temporary.next=temp;
			temporary.prev.next=temporary;
			temp.prev=temporary;
			temporary=null;
			temp=null;
			return true;
		}
	}

	/**
	 * 
	 * @param thresholdSpeed
	 * @return the highest number of consecutive items in the list
	 * (to which the calling object belongs) that have a speed more than
	 * the thresholdSpeed.
	 */
	public int longestSequenceOver(double thresholdSpeed) {
		if(this==null){return -1;}
		if(this.next==null && this.prev==null&& this.speed()>thresholdSpeed){return 1;}
		if(this.next==null && this.prev==null&& this.speed()<thresholdSpeed){return 0;}
		else{
			int count=0;
			int maxcount=-1;
			Run temp=this;
			while(temp.prev!=null){temp=temp.prev;}
			while(temp!=null){
				if(temp.speed()>thresholdSpeed){
					count++;
					if(count>maxcount){maxcount=count;}
				}
				else{count=0;}
				temp=temp.next;
			}
			return maxcount;
		}
	}

	/**
	 * 
	 * @param thresholdSpeed
	 * @return an array containing representations of the runs (in order of sequence)
	 * in the list to which the calling object belongs, that have a speed more than
	 * the thresholdSpeed. return null if no such item exists in the list.
	 */
	public String[] getRunsOver(double thresholdSpeed) {
		String arr[] = new String[this.size()];
		if(this==null){return null;}
		if(this.next==null && this.prev==null&& this.speed()>thresholdSpeed){arr[0]=this.toStringIndividual(); return arr;}
		if(this.next==null && this.prev==null&& this.speed()<thresholdSpeed){return null;}
		else{
			int count=0;
			int maxcount=-1;
			Run temp=this;
			String temArr[]=new String[this.size()];
			int tempIndex=0;
			while(temp.prev!=null){temp=temp.prev;}
			while(temp!=null){
				if(temp.speed()>thresholdSpeed){
					count++;
					temArr[tempIndex]=temp.toStringIndividual()+"    ";
					tempIndex++;
					if(count>maxcount){
						maxcount=count;
						arr=temArr;
					}
				}
				else{
					tempIndex=0;
					count=0;
				}
				temp=temp.next;
			}
			String finalArr[]=new String[maxcount];
			for(int i=0;i<arr.length;i++){
				if(arr[i]!=null){
					finalArr[i]=arr[i];
				}
			}
			return finalArr;
		}
	}
}
