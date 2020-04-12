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
    	BacktrackingBST.Node node = root;
    	while(node != null) {
    		if(node.getKey() == x)
    			break;
    		if(node.getKey() > x)
    			node = node.left;
    		else
    			node = node.right;
    	}
    	return node;
    }

    public void insert(BacktrackingBST.Node z) {
    	this.addNode(z);
    	
    }

    public void delete(Node x) {
        this.removeNode(x);
    }

    public Node minimum() {
    	return subTreeMin(root);
    }

    public Node maximum() {
    	return subTreeMax(root);
    }

    public Node successor(Node x) {
        // TODO: implement your code here
    }

    public Node predecessor(Node x) {
        // TODO: implement your code here
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
    
    private Node removeNode(Node x){
    	Node toRemove = root;
    	Node toReturn = null;
    	while(toRemove != null) {
    		if(toRemove.getKey() > x.getKey())
    			toRemove = toRemove.left;
    		else if(toRemove.getKey() < x.getKey())
    			toRemove = toRemove.right;
    		else {
    			toReturn = toRemove;
    			if(toRemove.left == null || toRemove.right == null )
    				leafOrOneChildeRM(toRemove);
    			else
    				twoChildeNodeRM(toRemove);
    			break;
    		}
    	}
    	return toReturn;
    }
    private void leafOrOneChildeRM(Node toRemove) {
    	Node parent = toRemove.parent;
    	Node childe;
    	if(toRemove.left == null)
    		childe = toRemove.right;
    	else
    		childe = toRemove.left;
    	if(parent.left.getKey() == toRemove.getKey())
    		parent.left = childe;
    	else
    		parent.right = childe;
    }
    private void twoChildeNodeRM(Node toRemove) {
    	Node rightSubTreeMin = subTreeMin(toRemove.right);
    	removeNode(rightSubTreeMin);
    	toRemove.key = rightSubTreeMin.getKey();
    	toRemove.value = rightSubTreeMin.getValue();
    }
    private Node subTreeMin(Node x) {
        Node min = x;
        while(min.left != null) {
        	min = min.left;
        }
        return new Node(min.key, min.value);    	
    }
    private Node subTreeMax(Node x) {
        Node max = x;
        while(max.right != null) {
        	max = max.right;
        }
        return new Node(max.key, max.value);
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

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }
    }
}
