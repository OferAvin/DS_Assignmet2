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
    	Node inserted = this.addNode(z);
    	stack.push(inserted);
    	stack.push(true);
    }

    public void delete(Node x) {
		Node removed = this.removeNode(x, root);
    	stack.push(removed);
    	stack.push(false);
    }

    public Node minimum() {
    	return new Node(subTreeMin(root));
    }

    public Node maximum() {
    	return new Node(subTreeMax(root));
    }

    public Node successor(Node x) {
    	Node ans = search(x.key);;
    	if(ans.right == null){
    		while(ans != null && !amILeftChild(ans))
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
        Node ans = search(x.key);
        if(ans.left == null) {
        	while(ans != null && !amIRightChild(ans)) 
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
        boolean action = (boolean)stack.pop();
        Node node = (Node)stack.pop();
        if(action) 
        	leafRemove(node);
        else
        	deleteBacktrack(node);
    }
    private void deleteBacktrack(Node node) {
    	insertAsChild(node);
		if(node.right != null && node.left != null) {  //backtrack a one child node deletion
			Node toReInsert = (Node)stack.pop(); //this is the node that replaced the deleted node
			insertAsChild(toReInsert);
		}
		if(node.left != null)
			node.left.parent = node;
		if(node.right != null)
			node.right.parent = node;
	}
    private void insertAsChild(Node child) {
    	Node parent = child.parent;
    	if(parent == null) {
			root = child;
    	}
    	else {
    		if(amILeftChild(child))
    			parent.left = child;
    		else
    			parent.right = child;
    	}
    }
    @Override
    public void retrack() {
        // TODO: implement your code here
    }
    public void printPreOrder(){
    	if(this.isEmpty())
    		System.out.println("null");
    	else {
	    	root.printPreOrder(root);
	    	System.out.println();
    	}
    }
    @Override
    public void print() {
    	printPreOrder();
    }
    private boolean isEmpty() {
    	return this.root == null;
    }
    
    private Node addNode(BacktrackingBST.Node z) {
    	Node parent = null;
    	Node currNode = root;
    	while(currNode != null) {
    		parent = currNode;
    		if(currNode.getKey() > z.getKey())
    			currNode = currNode.left;
    		else
    			currNode = currNode.right;
    	}
    	z.parent = parent;
    	insertAsChild(z);
    	return new Node(z);
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
    			toReturn = new Node(toRemove);
    			if(amILeaf(toRemove))
    				leafRemove(toRemove);
    			else if(toRemove.left == null || toRemove.right == null )
    				oneChildNodeRM(toRemove);
    			else
    				twoChildrenNodeRM(toRemove);
    			break;
    		}
    	}
    	return toReturn;
    }
    private void leafRemove(Node toRemove) {
    	Node parent = toRemove.parent;
    	if(parent == null) 
    		root = null;
    	else {
	    	if(amILeftChild(toRemove))
	    		parent.left = null;
	    	else
	    		parent.right = null;
    	}
    }
    private void oneChildNodeRM(Node toRemove) {
    	Node parent = toRemove.parent;
    	Node child;
    	if(toRemove.left == null) 
    		child = toRemove.right;
    	else
    		child = toRemove.left;
    	child.parent = parent;
    	if (parent == null)
    		root = child;
    	else if(amILeftChild(toRemove))
    		parent.left = child;
    	else
    		parent.right = child;
    }
    private void twoChildrenNodeRM(Node toRemove) {
    	Node rightSubTreeMin = subTreeMin(toRemove.right);  //look for replacement 
    	stack.push(removeNode(rightSubTreeMin, toRemove.right ));//push replacement to stack for backtracking
    	toRemove.key = rightSubTreeMin.getKey();
    	toRemove.value = rightSubTreeMin.getValue();
    }
    private Node subTreeMin(Node subTreeRoot) {
        Node min = subTreeRoot;
        while(min.left != null) {
        	min = min.left;
        }
        return min;    	
    }
    private Node subTreeMax(Node subTreeRoot) {
        Node max = subTreeRoot;
        while(max.right != null) {
        	max = max.right;
        }
        return max;
    }
    private boolean amILeftChild(Node x) {
    	Node parent = x.parent;
    	boolean parentExists = parent != null;
    	return parentExists && parent.getKey() > x.getKey();
    }
    private boolean amIRightChild(Node x) {
    	Node parent = x.parent;
    	boolean parentExists = parent != null;
    	return parentExists && parent.getKey() < x.getKey();
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
        private void printPreOrder(Node x) {
			if(x != null) {
				System.out.print(x.key + " ");
				printPreOrder(x.left);
				printPreOrder(x.right);
			}
		}
    }
}
