public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int size;

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
    	stack.push(true);
    }

    @Override
    public void delete(Integer value) {
    	if(value >= size) {}
    	int index = search(value);
    	if(index != -1) {
    		arr[index] = arr[size-1];
    		size = size-1;
	    	stack.push(value);
	    	stack.push(index);
	    	stack.push(false);
    	}
    }

    @Override
    public Integer minimum() {
        int min=arr[0];
        for(int i=1;i<size;i=i+1) {
        	if(min>arr[i]) {
        		min=arr[i];
        	}
        }
        return min;
    }

    @Override
    public Integer maximum() {
        int max=arr[0];
        for(int i=1;i<size;i=i+1) {
        	if(max<arr[i]) {
        		max=arr[i];
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
        		if(cond | max<succesor) {
        			succesor = max;
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
        		if(cond | min>predecessor) {
        			predecessor = min;
        			cond = false;
        		}
        	}
        }
        return predecessor;
    }

    @Override
    public void backtrack() {
        if(!stack.isEmpty()) {
        	if((boolean)stack.pop()==true) {
        		delete(arr[size-1]);
        		
        	}
        	else {
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
        
    }

    @Override
    public void print() {
       for(int i=0;i<size-1;i=i+1)
    	   System.out.print(arr[i]+" ");
       System.out.print(arr[size-1]);
       System.out.println();
    }
}

