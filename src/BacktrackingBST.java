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
    	return new Node(currNode); //return a copy of the node
    }

    public void insert(BacktrackingBST.Node z) {
    	Node inserted = this.addNode(z);
    	stack.push(inserted); 		//inserted node pushed to stack for backtracking
    	stack.push(true);    		//true represents insert
    	redoStack = new Stack();	//clear redo stack  
    }

    public void delete(Node x) {
		Node removed = this.removeNode(x, root);
    	stack.push(removed); 		//deleted node pushed to stack for backtracking
    	stack.push(false);	 		//false represents delete
    	redoStack = new Stack();	//clear redo stack
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
    	//in case no right subtree look for the first ancestor x in its left subtree
    		while(ans != null && !amILeftChild(ans))
    			ans = ans.parent;
    		if(ans == null)
        		return null;
    		else
    			ans = ans.parent;
    	}
    	else
		//else look for min in x right subtree
    		ans = subTreeMin(ans.right);
    	return new Node(ans);
    }

    public Node predecessor(Node x) {
        Node ans = search(x.key);
        if(ans.left == null) {
        	//in case no left subtree look for the first ancestor x in its right subtree
        	while(ans != null && !amIRightChild(ans)) 
        		ans = ans.parent;
        	if(ans == null)
        		return null;
        	else
        		ans = ans.parent;
        }
        else
        	//else look for min in x left subtree
        	ans = subTreeMax(ans.left);
    	return new Node(ans);
    }

    @Override
    // this function backtracks insertion and deletion actions
    public void backtrack() {
    	if(!stack.isEmpty()) {
	        boolean action = (boolean)stack.pop(); //boolean value indicates insert(true) delete(false)
	        Node node = (Node)stack.pop();
	        if(action)
        	//backtracking insertion (always leaf removal)
	        	leafRemove(node);
	        else
    		//backtracking deletion 
	        	deleteBacktrack(node);
	        redoStack.push(node);	//insert node to redo stack for retracking
	        redoStack.push(action); //insert action to redo stack for retracking
    	}
    }
    //this function backtracks deleted node
    private void deleteBacktrack(Node node) {
    	insertAsChild(node);
		if(node.right != null && node.left != null) {	//backtrack a two child node deletion
			Node toReInsert = (Node)stack.pop(); 		//this is the node which replaced the deleted node
			replaceNodeWithLeaf(node, toReInsert);
			redoStack.push(toReInsert); 				// for retracting
			insertAsChild(toReInsert);
		}
		fixChildren(node); 								//fix children's parent field
	}
    //this function fixes the parent field of the child  
    private void fixChildren(Node node) {
		if(node.left != null)
			node.left.parent = node;
		if(node.right != null)
			node.right.parent = node;
	}
    //this function set parent's child fields to point at child
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
    //this function retracks insert and delete actions
    public void retrack() {
    	if(!redoStack.isEmpty()) {
	        boolean action = (boolean)redoStack.pop();
	        Node node = (Node)redoStack.pop();
	        if(action)						//reinsert case
	        	insertAsChild(node);		//reinsert as leaf
	        else							//delete case (delete again)
	        	deleteRetrack(node);
	        stack.push(node);
	        stack.push(action);
    	}
    }
    //this function retracting deleted node
    private void deleteRetrack(Node toRemove) {
		if(amILeaf(toRemove))
			leafRemove(toRemove);
		else if(toRemove.left == null || toRemove.right == null )  	//one child case
			oneChildNodeRM(toRemove);
		else {														//two child case
			Node replacement = (Node)redoStack.pop();
			replaceNodeWithLeaf(toRemove, replacement);
			stack.push(replacement);								//push replacement to stack for backtracking
			leafRemove(replacement);
		}
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
    //this function add a node to the tree as a leaf
    private Node addNode(BacktrackingBST.Node z) {
    	Node parent = null;
    	Node currNode = root;
    	while(currNode != null) {  				//look for right place to insert
    		parent = currNode;
    		if(currNode.getKey() > z.getKey())	
    			currNode = currNode.left;
    		else
    			currNode = currNode.right;
    	}
    	z.parent = parent;
    	insertAsChild(z);
    	return z;
    }
    //this function removes node x
    private Node removeNode(Node x, Node from){
    	Node toRemove = from;
    	Node toReturn = null;
    	while(toRemove != null) { // look for the node to remove
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
  //this function deletes a leaf node
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
    //this function deletes a node with one child
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
    //this function deletes a node with two children
    private void twoChildrenNodeRM(Node toRemove) {
    	Node rightSubTreeMin = subTreeMin(toRemove.right);  //look for replacement 
    	replaceNodeWithLeaf(toRemove, rightSubTreeMin);
    	leafRemove(rightSubTreeMin);
    	stack.push(rightSubTreeMin);//push replacement to stack for backtracking
    }
    //this function finds a replacement for a two child node which have to be deleted
    private void replaceNodeWithLeaf(Node toReplace, Node leaf) {
    	int replacedKey = toReplace.getKey();
    	Object replaceVal = toReplace.getValue();
    	toReplace.key = leaf.getKey();
    	toReplace.value = leaf.getValue();
    	leaf.key = replacedKey;
    	leaf.value = replaceVal;
    }
    //this function looks for a min at a specific subtree
    private Node subTreeMin(Node subTreeRoot) {
        Node min = subTreeRoot;
        while(min.left != null) {
        	min = min.left;
        }
        return min;    	
    }
    //this function looks for a min at a specific subtree
    private Node subTreeMax(Node subTreeRoot) {
        Node max = subTreeRoot;
        while(max.right != null) {
        	max = max.right;
        }
        return max;
    }
    //this function checks if x is a left child
    private boolean amILeftChild(Node x) {
    	Node parent = x.parent;
    	boolean parentExists = parent != null;
    	return parentExists && parent.getKey() > x.getKey();
    }
    //this function checks if x is a right child
    private boolean amIRightChild(Node x) {
    	Node parent = x.parent;
    	boolean parentExists = parent != null;
    	return parentExists && parent.getKey() < x.getKey();
    }
    //this function checks if x is a leaf
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
