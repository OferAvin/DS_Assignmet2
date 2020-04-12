public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int size;

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
    public Integer search(int x) {
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
    	int index_to_insert = toInsert(x);
    	add(x, index_to_insert);
        stack.push(index_to_insert);
        stack.push(x);
        stack.push(true);
        }
    private void add(Integer x,int index_to_insert ) {
    	for(int j=size-1;j>=index_to_insert;j=j-1) {
    		arr[j+1] = arr[j];
        }
        arr[index_to_insert] = x;
        size = size+1;
	}
    
    private int toInsert(Integer x) {
    	int index_to_insert=size;
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
        stack.push(value);
        stack.push(false);
    }
    private void remove(Integer index) {
        int i=size-1;
        int prev = arr[i];
        while(i>index) {
        	int next = arr[i-1];
        	arr[i-1] = prev;
        	prev = next;
        	i = i-1;
        }
        size = size-1;
	}

    @Override
    public Integer minimum() {
        return arr[0];
    }

    @Override
    public Integer maximum() {
        return arr[size-1];
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
        	if((boolean)stack.pop()==true) {
        		remove((int)stack.pop());
        	}
        	else {
				add((int)stack.pop(), (int) stack.pop());
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
        System.out.println();
    }
    public static void main(String[] args) {
    	BacktrackingSortedArray tamir = new BacktrackingSortedArray(new Stack(), 10);
    	tamir.insert(5);
    	tamir.insert(90);
    	tamir.insert(11);
    	tamir.insert(7);
    	tamir.print();
    	tamir.insert(70);
    	tamir.print();
    	tamir.delete(3);
    	tamir.print();
    	tamir.delete(3);
    	tamir.print();
    	tamir.insert(70);
    	tamir.print();
    	tamir.insert(90);
    	tamir.print();
	}
}
