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
    	redoStack = new Stack();
    }

    public void delete(Node x) {
		Node removed = this.removeNode(x, root);
    	stack.push(removed);
    	stack.push(false);
    	redoStack = new Stack();
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
    	if(!stack.isEmpty()) {
	        boolean action = (boolean)stack.pop();
	        Node node = (Node)stack.pop();
	        if(action) 
	        	leafRemove(node);//insert backtrack
	        else
	        	deleteBacktrack(node);
	        redoStack.push(node);
	        redoStack.push(action);
    	}
    }
    private void deleteBacktrack(Node node) {
    	insertAsChild(node);
		if(node.right != null && node.left != null) {  //backtrack a one child node deletion
			Node toReInsert = (Node)stack.pop(); //this is the node that replaced the deleted node
			replaceNodeWithLeaf(node, toReInsert);
			redoStack.push(toReInsert); // for retracting
			insertAsChild(toReInsert);
		}
		fixChildren(node);
	}
//  	fix the parent field of the child  
    private void fixChildren(Node node) {
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
    	if(!redoStack.isEmpty()) {
	        boolean action = (boolean)redoStack.pop();
	        Node node = (Node)redoStack.pop();
	        if(action)
	        	insertAsChild(node);//reinsert a leaf
	        else
	        	deleteRetrack(node);
	        stack.push(node);
	        stack.push(action);
    	}
    }
<<<<<<< HEAD
=======
    private void deleteRetrack(Node toRemove) {
		if(amILeaf(toRemove))
			leafRemove(toRemove);
		else if(toRemove.left == null || toRemove.right == null )
			oneChildNodeRM(toRemove);
		else {
			Node replacement = (Node)redoStack.pop();
			replaceNodeWithLeaf(toRemove, replacement);
			stack.push(replacement);//push replacement to stack for backtracking
			leafRemove(replacement);
		}
	}

>>>>>>> 083ba1a766fbd0f1f0f13d076beab2d84d1b6c53
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
<<<<<<< HEAD
    	return new Node(z);
    }
=======
    	return z;
    }
    
>>>>>>> 083ba1a766fbd0f1f0f13d076beab2d84d1b6c53
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
    	replaceNodeWithLeaf(toRemove, rightSubTreeMin);
    	leafRemove(rightSubTreeMin);
    	stack.push(rightSubTreeMin);//push replacement to stack for backtracking
    }
    private void replaceNodeWithLeaf(Node toReplace, Node leaf) {
    	int replacedKey = toReplace.getKey();
    	Object replaceVal = toReplace.getValue();
    	toReplace.key = leaf.getKey();
    	toReplace.value = leaf.getValue();
    	leaf.key = replacedKey;
    	leaf.value = replaceVal;
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
<<<<<<< HEAD
=======
    
    public static void main(String[] args) {
		BacktrackingBST tree = new BacktrackingBST(new Stack(), new Stack());
		tree.insert(new Node(4, 4));
//		tree.delete(new Node(4, 4));
		tree.insert(new Node(7, 7));
//		tree.print();
//		tree.backtrack();
//		tree.print();
		tree.insert(new Node(2, 2));
		tree.insert(new Node(3, 3));
		tree.insert(new Node(5, 5));
		tree.insert(new Node(1, 1));
		tree.insert(new Node(9, 9));
		tree.insert(new Node(8, 8));
		tree.insert(new Node(11, 11));
//		System.out.println(tree.maximum().key+" "+tree.minimum().key);
//		System.out.println(tree.predecessor(new Node(7,7)).key+" "+tree.predecessor(new Node(1,1)));
//		System.out.println(tree.successor(new Node(7,7)).key+" "+tree.successor(new Node(1,1)).key);
//		tree.print();
//		tree.delete(new Node(4, 4));
		tree.print();
		tree.delete(new Node(1, 1));
		tree.delete(new Node(2, 2));
		tree.delete(new Node(4, 4));
		tree.print();
		tree.backtrack();
		tree.print();
		tree.backtrack();
		tree.print();
		tree.backtrack();
		tree.print();
		tree.insert(new Node(222, 222));
		tree.retrack();
		tree.print();
		tree.retrack();
		tree.print();
		tree.retrack();
		tree.print();
//		tree.backtrack();
//		tree.print();
//		tree.backtrack();
//		tree.print();
//		tree.backtrack();
//		tree.print();
//		tree.backtrack();
//		tree.print();
//		tree.backtrack();
//		tree.print();
//		tree.retrack();
//		tree.print();
//		tree.retrack();
//		tree.print();
////		System.out.println(tree.search(2).parent.key);
	}
>>>>>>> 083ba1a766fbd0f1f0f13d076beab2d84d1b6c53
}
