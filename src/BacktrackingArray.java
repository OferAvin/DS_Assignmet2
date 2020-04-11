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
    		arr[index] = arr[size];
	    	stack.push(value);
	    	stack.push(index);
	    	stack.push(false);
    	}
    }

    @Override
    public Integer minimum() {
        // TODO: implement your code here
    }

    @Override
    public Integer maximum() {
        // TODO: implement your code here
    }

    @Override
    public Integer successor(Integer index) {
        // TODO: implement your code here
    }

    @Override
    public Integer predecessor(Integer index) {
        // TODO: implement your code here
    }

    @Override
    public void backtrack() {
        // TODO: implement your code here
    }

    @Override
    public void retrack() {
        // Do not implement anything here!!
    }

    @Override
    public void print() {
        // TODO: implement your code here
    }
    
}
