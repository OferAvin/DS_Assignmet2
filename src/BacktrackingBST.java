public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    BacktrackingBST.Node root = null;

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }

    public Node getRoot() {
        return root;
    }
	
    public Node search(int x) {
    	BacktrackingBST.Node currNode = root;
    	while(currNode != null) {
    		if(currNode.getKey() == x)
    			break;
    		if(currNode.getKey() > x)
    			currNode = currNode.left;
    		else
    			currNode = currNode.right;
    	}
    	return new Node(currNode);
    }

    public void insert(BacktrackingBST.Node z) {
    	this.addNode(z);
    	
    }

    public void delete(Node x) {
		this.removeNode(x, root);
    }

    public Node minimum() {
    	return new Node(subTreeMin(root));
    }

    public Node maximum() {
    	return new Node(subTreeMax(root));
    }

    public Node successor(Node x) {
    	Node ans = x;
    	if(ans.right == null){
    		while(ans != null && !amILeftChilde(ans))
    			ans = ans.parent;
    		if(ans == null)
        		return null;
    		else
    			ans = ans.parent;
    	}
    	else
    		ans = subTreeMin(ans.right);
    	return new Node(ans);
    }

    public Node predecessor(Node x) {
        Node ans = x;
        if(ans.left == null) {
        	while(ans != null && !amIRightChilde(ans)) 
        		ans = ans.parent;
        	if(ans == null)
        		return null;
        	else
        		ans = ans.parent;
        }
        else
        	ans = subTreeMax(ans.left);
    	
    	return new Node(ans);
    }

    @Override
    public void backtrack() {
        // TODO: implement your code here
    }

    @Override
    public void retrack() {
        // TODO: implement your code here
    }

    public void printPreOrder(){
        // TODO: implement your code here
    }

    @Override
    public void print() {
        // TODO: implement your code here
    }
    private boolean isEmpty(BacktrackingBST tree) {
    	return tree.root == null;
    }
    
    private void addNode(BacktrackingBST.Node z) {
    	BacktrackingBST.Node parent = null;
    	BacktrackingBST.Node node = root;
    	while(node != null) {
    		parent = node;
    		if(node.getKey() > z.getKey())
    			node = node.left;
    		else
    			node = node.right;
    	}
    	z.parent = parent;
    	if(parent == null)
    		root = z;
    	else if(z.parent.getKey() > z.getKey())
    		parent.left = z;
    	else
    		parent.right = z;
    }
    
    private Node removeNode(Node x, Node from){
    	Node toRemove = from;
    	Node toReturn = null;
    	while(toRemove != null) {
    		if(toRemove.getKey() > x.getKey())
    			toRemove = toRemove.left;
    		else if(toRemove.getKey() < x.getKey())
    			toRemove = toRemove.right;
    		else {
    			toReturn = toRemove;
    			if(amILeaf(toRemove))
    				leafRemove(toRemove);
    			else if(toRemove.left == null || toRemove.right == null )
    				oneChildeNodeRM(toRemove);
    			else
    				twoChildrenNodeRM(toRemove);
    			break;
    		}
    	}
    	return toReturn;
    }
    private void leafRemove(Node toRemove) {
    	Node parent = toRemove.parent;
    	if(amILeftChilde(toRemove))
    		parent.left = null;
    	else
    		parent.right = null;
    }
    private void oneChildeNodeRM(Node toRemove) {
    	Node parent = toRemove.parent;
    	Node childe;
    	if(toRemove.left == null) 
    		childe = toRemove.right;
    	else
    		childe = toRemove.left;
		parent.right = childe;
		childe.parent = parent;
    }
    private void twoChildrenNodeRM(Node toRemove) {
    	Node rightSubTreeMin = subTreeMin(toRemove.right);
    	removeNode(rightSubTreeMin, toRemove.right );
    	toRemove.key = rightSubTreeMin.getKey();
    	toRemove.value = rightSubTreeMin.getValue();
    }
    private Node subTreeMin(Node x) {
        Node min = x;
        while(min.left != null) {
        	min = min.left;
        }
        return min;    	
    }
    private Node subTreeMax(Node x) {
        Node max = x;
        while(max.right != null) {
        	max = max.right;
        }
        return max;
    }
    private boolean amILeftChilde(Node x) {
    	Node parent = x.parent;
    	boolean parentExists = parent != null;
    	return parentExists && parent.left != null && parent.left.getKey() == x.getKey();
    }
    private boolean amIRightChilde(Node x) {
    	Node parent = x.parent;
    	boolean parentExists = parent != null;
    	return parentExists && parent.right != null && parent.right.getKey() == x.getKey();
    }
    private boolean amILeaf(Node x) {
    	return x.left == null && x.right == null;
    }
    public static class Node{
    	//These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;
        
        private BacktrackingBST.Node parent;
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }
        //copy constructor
        public Node(Node toCopy) {
        	this.key = toCopy.getKey();
        	this.value = toCopy.getValue();
        	this.left = toCopy.left;
        	this.right = toCopy.right;
        	this.parent = toCopy.parent;
        }

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
    	BacktrackingBST Tree = new BacktrackingBST(new Stack(), new Stack());
    	Node ofer = new Node(3, 3);
		Node o = new Node(1, 1);
		Node f = new Node(7, 7);
		Node c = new Node(2 , 2);
		Node d = new Node(4, 4);
		Tree.insert(ofer);
		Tree.insert(o);
		Tree.insert(f);
		Tree.insert(c);
		Tree.insert(d);
//		System.out.println(Tree.search(1).getValue() + " " + Tree.search(7).getValue() + " " + Tree.search(4).getValue());
//		System.out.println(Tree.minimum().getValue() + " " + Tree.maximum().getKey());
		Tree.insert(new Node(0, 1));
		Tree.insert(new Node(9, 9));
		Tree.insert(new Node(10, 10));
//		System.out.println(Tree.minimum().getKey() + " " + Tree.maximum().getKey());
		System.out.println(Tree.predecessor(Tree.search(2)).key);
//		System.out.println(Tree.search(10).parent.getValue()+ " parent");
//		Tree.delete(new Node(9, 9));
//		Tree.delete(new Node(0, 0));
//		System.out.println(Tree.search(10).parent.getValue()+ " parent");
//		System.out.println(Tree.minimum().getKey() + " " + Tree.maximum().getKey());
//		System.out.println(Tree.root.getKey());
//		Tree.delete(new Node(3, 3));
//		System.out.println(Tree.root.getKey());
//		System.out.println(Tree.search(1).parent.getValue()+ " parent");
	}

}
