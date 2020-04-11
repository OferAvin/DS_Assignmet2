public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    // TODO: implement your code here

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
    }

    @Override
    public Integer get(int index){
    	return arr[index];
    }

    @Override
    public Integer search(int x) {
        int ans=-1;
        for(int i=0;i<arr.length & ans==-1;i=i+1) {
        	if(arr[i]==x) {
        		ans=i;
        	}
        }
        return ans;
    }

    @Override
    public void insert(Integer x) {
    
    }

    @Override
    public void delete(Integer index) {
        // TODO: implement your code here
    }

    @Override
    public Integer minimum() {
        int min=arr[0];
        for(int i=1;i<=arr.length-1;i=i+1) {
        	if(min>arr[i]) {
        		min=arr[i];
        	}
        }
        return min;
    }

    @Override
    public Integer maximum() {
        int max=arr[0];
        for(int i=1;i<=arr.length-1;i=i+1) {
        	if(max<arr[i]) {
        		max=arr[i];
        	}
        }
        return max;
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
       for(int i=0;i<arr.length-1;i=i+1)
    	   System.out.print(arr[i]+" ");
       System.out.print(arr[arr.length-1]);
    }
    public static void main(String[] args) {
    	BacktrackingArray tamirBacktrackingArray=new BacktrackingArray(null, 5);
    	tamirBacktrackingArray.print();
    	System.out.println();
    	System.out.println(tamirBacktrackingArray.maximum());
		
	}
}

