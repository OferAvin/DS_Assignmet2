public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int fd, int bk, Stack myStack) {
        int ans=-1;
        int num_of_steps=0;
        boolean found=false;
        for(int i=0;i<arr.length & !found;i=i+1) {
        	if(arr[i]==x) {
        		found=true;
        		ans=i;
        	}
        	else {
				num_of_steps=num_of_steps+1;
				myStack.push(arr[i]);
				if(num_of_steps==fd) {
					i=i-bk;
					num_of_steps=0;
					for(int j=1;j<=bk;j=j+1) {
						myStack.pop();
					}
				}
			}
        }
        return ans;
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
    	int ans=-1;
        int low=0;
        int high=arr.length-1;
        while(high>=low) {
        	int changes=isConsistent(arr);
        	for(int i = 0; i < changes; i++) {
        		high = (int)myStack.pop();
        		low = (int)myStack.pop();
        	}
        	int mid=(low+high)/2;
        	myStack.push(low);
        	myStack.push(high);
        	if(arr[mid]==x) {
        		ans = mid;
    			break;
        	}
    		else if (arr[mid]>x)
				high=mid-1;
    		else 
				low=mid+1;
        }
        return ans;
    }

    private static int isConsistent(int[] arr) {
        double res = Math.random() * 100 - 75;

        if (res > 0) {
            return (int)Math.round(res / 10);
        } else {
            return 0;
        }
    }
}

	
