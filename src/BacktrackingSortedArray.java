public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int size;//the index for the next available cell

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        size = 0;
    }
    @Override
    public Integer get(int index){
        return arr[index];
    }
    
    @Override
    public Integer search(int x) {//binary search
        int ans = -1;
        int low=0;
        int high=size-1;
        while(high>=low) {
        	int mid = (high+low)/2;
        	if(arr[mid]==x) {
        		ans = mid;
        		break;
        	}
        	else if (arr[mid]>x) {
				high=mid-1;
			}
        	else {
				low = mid+1;
			}
        }
        return ans;
    }

    @Override
    public void insert(Integer x) {
    	int index_to_insert = toInsert(x);//searching for the correct index to insert
    	add(x, index_to_insert);//add x to the correct index 
        stack.push(index_to_insert);//saving the index for backtrack
        stack.push(true);//true represents insert
        }
    private void add(Integer x,int index_to_insert ) {//this method adds x at the index_to_insert to array 
    	for(int j=size-1;j>=index_to_insert;j=j-1) {//pushing all cell from index_to_insert on cell forward
    		arr[j+1] = arr[j];
        }
        arr[index_to_insert] = x;
        size = size+1;
	}
    //this function finding the correct index for insert by binary search
    private int toInsert(Integer x) {
    	int index_to_insert=0;
    	int low = 0;
    	int high = size-1;
    	while(high>=low) {
    		int mid = (high+low)/2;
    		if(high==low) {
    			if(arr[mid]<x)
    				index_to_insert = high+1;
    			else {
					index_to_insert = low;
				}
    			break;
    		}
    		else if (arr[mid]>x) {
				high = mid-1;
			}
    		else if (arr[mid]<x) {
				low = mid+1;
			}
    		else {
				index_to_insert = mid;
				break;
			}
    	}
    	return index_to_insert;
	}


    @Override
    public void delete(Integer index) {
        int value = arr[index];
        remove(index);
        stack.push(index);//saving the index for backtrack
        stack.push(value);//saving the value for backtrack
        stack.push(false);//false represent delete
    }
    private void remove(Integer index) {//this method delete the element in the specific index and organize the array
        int i=size-1;
        int prev = arr[i];
        while(i>index) {//moving each cell one cell back till the index to remove
        	int next = arr[i-1];
        	arr[i-1] = prev;
        	prev = next;
        	i = i-1;
        }
        size = size-1;
	}

    @Override
    public Integer minimum() {
        return 0;
    }

    @Override
    public Integer maximum() {
        return size-1;
    }

    @Override
    public Integer successor(Integer index) {
    	if(index!=size-1)
        	return index+1;
    	else {
			return -1;
		}
    }

    @Override
    public Integer predecessor(Integer index) {
        if(index!=0)
        	return index-1;
        else {
        	return -1;
		}
    }

    @Override
    public void backtrack() {
        if(!stack.isEmpty()) {
        	if((boolean)stack.pop()==true) {//insertion backtrack
        		remove((int)stack.pop());   //use remove with the correct index
        	}
        	else {//deletion backtrack
				add((int)stack.pop(), (int) stack.pop());//use add with the correct index and value
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

