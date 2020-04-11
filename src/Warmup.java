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
        boolean isBigger=false;
    	int ans=-1;
        int low=0;
        int high=arr.length-1;
        while(high>=low & ans==-1) {
        	int check=isConsistent(arr);
        	int mid=(low+high)/2;
        	if(check==0) {
        		myStack.push(mid);
        		if(arr[mid]==x) {
        			ans=mid;
        		}
        		else if (arr[mid]>x) {
					high=mid-1;
					isBigger=false;
				}
        		else {
					low=mid+1;
					isBigger=true;
				}
        	}
        	else {
        		for(int i=1;i<=check;i=i+1) {
        			mid=(int)myStack.pop();
        		}
				if(!isBigger) {
					high=2*mid-low;
				}else {
					low=2*mid-high;
				}
			}
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
