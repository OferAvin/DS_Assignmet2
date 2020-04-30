public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int size;//the index for the next available cell

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        size = 0; 
    }

    @Override
    public Integer get(int index){
    	return arr[index];
    }

    @Override
    public Integer search(int x) {
        int ans=-1;
        for(int i=0; i<size; i=i+1) {
        	if(arr[i]==x) {
        		ans=i;
        		break;      		
        	}
        }
        return ans;
    }

    @Override
    //this method adds x at the end of the array
    public void insert(Integer x) {
    	if(size == arr.length) {}	
    	arr[size] = x;
    	size = size + 1;
    	stack.push(true);        		//true represents insert
    }

    @Override
    public void delete(Integer value) {
    	int index = search(value);
    	if(index != -1) {
    		arr[index] = arr[size-1];	//put the last value in the deleted value index
    		size = size-1;				
	    	stack.push(value);
	    	stack.push(index);
	    	stack.push(false);			//false represent delete
    	}
    }

    @Override
    public Integer minimum() {
        int min=0;
        for(int i=1;i<size;i=i+1) {
        	if(arr[min]>arr[i]) {
        		min=i;
        	}
        }
        return min;
    }

    @Override
    public Integer maximum() {
        int max=0;
        for(int i=1;i<size;i=i+1) {
        	if(arr[max]<arr[i]) {
        		max=i;
        	}
        }
        return max;
    }

    @Override
    public Integer successor(Integer index) {
        boolean cond = true;
    	int succesor = -1;
        int max;
    	for(int i=0;i<size;i=i+1) {
        	if(arr[i]>arr[index]) {
        		max = arr[i];
        		if(cond || max<arr[succesor]) {
        			succesor = i;
        			cond = false;
        		}
        	}
    	}
    	return succesor;
    }

    @Override
    public Integer predecessor(Integer index) {
        boolean cond = true;
        int predecessor = -1;
        int min;
        for(int i=0;i<size;i=i+1) {
        	if(arr[i]<arr[index]) {
        		min = arr[i];
        		if(cond || min>arr[predecessor]) {
        			predecessor = i;
        			cond = false;
        		}
        	}
        }
        return predecessor;
    }

    @Override
    public void backtrack() {
        if(!stack.isEmpty()) {
        	if((boolean)stack.pop()) { 			//insertion backtrack   		
        		size = size-1;
        	}
        	else {								//deletion backtrack
        		int index = (int)stack.pop();
        		int value = (int)stack.pop();
        		arr[size] = arr[index];
        		arr[index] = value;
        		size = size+1;
        	}
        }        	
    }

    @Override
    public void retrack() {
    	// Do not implement anything here!!
    }

    @Override
    public void print() {
       for(int i=0;i<size-1;i=i+1)
    	   System.out.print(arr[i]+" ");
       System.out.print(arr[size-1]);
    }
}

