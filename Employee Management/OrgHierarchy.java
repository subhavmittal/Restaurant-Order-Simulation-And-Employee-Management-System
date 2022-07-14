// Tree node
class Node {
  public Node parent;
  public DNode DNode_ref;
  int level;
  Dlinked children;
  int id;
  public Node(){
	  this(null, null);
  }
  public Node(Node parent, DNode DNode_ref){
	  this.parent = parent;
	  this.DNode_ref = DNode_ref;
	  Dlinked p = new Dlinked();
	  this.children = p;
  }
}

public class OrgHierarchy implements OrgHierarchyInterface{

//root node
Node root;
public AvlTree avl_tree;
public OrgHierarchy(){
	this.avl_tree = new AvlTree();
	root = null;
};
public boolean isEmpty(){
	//your implementation
	return avl_tree.size==0;
} 

public int size(){
	//your implementation
	return avl_tree.size;
}

public int level(int id) throws IllegalIDException{
	//your implementation
	 //search in avl tree get node
	Node p = avl_tree.getReference(id,avl_tree.root);
	return p.level;
} 

public void hireOwner(int id) throws NotEmptyException{
	//your implementation
	 //create node in avl tree and assign to root. create node here too and assign to root.
	if(!isEmpty())
		throw new NotEmptyException("not_empty");
	this.root = new Node();
	avl_tree.insertRoot(id,this.root);
	this.root.level = 1;
	this.root.id = id;
}

public void hireEmployee(int id, int bossid) throws IllegalIDException{
	//your implementation
	//search bossid in avl tree and get ref node.create new node and insert in children
	Node p = avl_tree.getReference(bossid,avl_tree.root);
	Node new_emp = new Node();
	DNode temp = p.children.insertAtEnd(new_emp);
	new_emp.parent = p;
	new_emp.DNode_ref = temp;
	new_emp.level = new_emp.parent.level + 1;
	new_emp.id = id;
	avl_tree.insertNode(id,avl_tree.root,new_emp);
} 

public void fireEmployee(int id) throws IllegalIDException{
	Node p = avl_tree.getReference(id,avl_tree.root);
	avl_tree.deleteNode(id,avl_tree.root);
	p.parent.children.remove(p);
	p.parent = null;
}
public void fireEmployee(int id, int manageid) throws IllegalIDException{
	Node p1 = avl_tree.getReference(id,avl_tree.root);
	Node p2 = avl_tree.getReference(manageid,avl_tree.root);
	DNode x=p1.children.head.getNext();
	DNode z=x.getNext();
	while(z!=null){
		Node temp_in = x.getElement();
		DNode temp = p2.children.insertAtEnd(x.getElement());
		p1.children.remove(x.getElement());
		temp_in.DNode_ref = temp;
		temp_in.parent = p2;
		x=z;
		z=z.getNext();
	}

} 

public int boss(int id) throws IllegalIDException{
	//your implementation
	Node p = avl_tree.getReference(id,avl_tree.root);
	if (p.parent==null)
		return -1;
	return p.parent.id;
}

public int lowestCommonBoss(int id1, int id2) throws IllegalIDException{
	Node p1 = avl_tree.getReference(id1,avl_tree.root);
	Node p2 = avl_tree.getReference(id2,avl_tree.root);
	if (p1==this.root || p2==this.root)
		return -1;
	if(p2.level < p1.level){
		while(p1.level!= p2.level){
			p1=p1.parent;
		}
	}
	else{
		while(p2.level!= p1.level){
			p2=p2.parent;
		}
	}
	while(p1!=p2){
		p1=p1.parent;
		p2=p2.parent;
	}
	return p1.id;
}

public String toString(int id) throws IllegalIDException{
	Node p = avl_tree.getReference(id,avl_tree.root);
	StringBuilder sb=new StringBuilder(Integer.toString(id));
	
	return sb.toString();

}

}
