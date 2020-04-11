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
        int[] arr2=new int[arr.length+1];
        for(int i=0;i<arr.length;i=i+1) {
        	arr2[i]=arr[i];
        }
        arr2[arr.length+1]=x;
        stack.push(arr);
        arr=arr2;
    }

    @Override
    public void delete(Integer index) {
        // TODO: implement your code here
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
