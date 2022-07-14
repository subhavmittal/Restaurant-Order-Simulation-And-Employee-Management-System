public class AvlTree {
    public int size;
    public AvlTree_Node root;

    public AvlTree() {
        size = 0;
        root = null;
    }
    public void insertRoot(int id, Node ref) {
        AvlTree_Node p = new AvlTree_Node();
        root = p;
        root.key = id;
        root.parent = null;
        root.maintree_ref = ref;
        root.height = 0;
        size = 1;
    }
    public Node getReference(int id, AvlTree_Node root) throws IllegalIDException {
        if (root==null) {
            throw new IllegalIDException("Illegal_ID");
        }
        if (root.key == id) {
            return root.maintree_ref;
        }
        if (root.key > id) {
            return getReference(id, root.left);
        }
        return getReference(id, root.right);
    }

    public void insertNode(int id,AvlTree_Node root, Node reference) throws IllegalIDException {
        if (root.key == id) {
            throw new IllegalIDException("Illegal_ID");
        }
        if (root.key > id) {
            if (root.left==null){
                size+=1;
                AvlTree_Node p = new AvlTree_Node();
                p.parent = root;
                p.height = 0;
                p.key =id;
                p.maintree_ref = reference;
                root.left = p;
                root.height = max(getHeight(root.left),getHeight(root.right)) + 1;
                root=root.parent;
                checkHeights1(root);
                return;
            }
            insertNode(id, root.left, reference);
            return;
        }
        if (root.right==null){
            size+=1;
            AvlTree_Node p = new AvlTree_Node();
            p.parent = root;
            p.height = 0;
            p.key =id;
            p.maintree_ref = reference;
            root.right = p;
            root.height = max(getHeight(root.left),getHeight(root.right)) + 1;
            root=root.parent;
            checkHeights1(root);
            return;
        }
        insertNode(id, root.right, reference);
        return;
    }
    public void checkHeights1(AvlTree_Node root){
        AvlTree_Node x;
        AvlTree_Node y;
        AvlTree_Node z;
        while(root!=null){
            if (abs(getHeight(root.left),getHeight(root.right)) > 1){
                z=root;
                if(getHeight(z.left) > getHeight(z.right)){
                    y=z.left;
                    if(getHeight(y.left) > getHeight(y.right)) {
                        x = y.left;
                        rotate_clockwise(y,z);
                    }
                    else{
                        x=y.right;
                        rotate_anticlockwise(x,y);
                        rotate_clockwise(x,z);
                    }
                }
                else{
                    y=z.right;
                    if(getHeight(y.left) > getHeight(y.right)) {
                        x = y.left;
                        rotate_clockwise(x,y);
                        rotate_anticlockwise(x,z);
                    }
                    else{
                        x=y.right;
                        rotate_anticlockwise(y,z);
                    }
                }
                break;
            }
            root.height = max(getHeight(root.left),getHeight(root.right)) + 1;
            root=root.parent;
        }
        return;
    }
    public void checkHeights2(AvlTree_Node root){
        AvlTree_Node x;
        AvlTree_Node y;
        AvlTree_Node z;
        while(root!=null){
            if (abs(getHeight(root.left),getHeight(root.right)) > 1){
                z=root;
                if(getHeight(z.left) > getHeight(z.right)){
                    y=z.left;
                    if(getHeight(y.left) > getHeight(y.right)) {
                        x = y.left;
                        rotate_clockwise(y,z);
                    }
                    else{
                        x=y.right;
                        rotate_anticlockwise(x,y);
                        rotate_clockwise(x,z);
                    }
                }
                else{
                    y=z.right;
                    if(getHeight(y.left) > getHeight(y.right)) {
                        x = y.left;
                        rotate_clockwise(x,y);
                        rotate_anticlockwise(x,z);
                    }
                    else{
                        x=y.right;
                        rotate_anticlockwise(y,z);
                    }
                }
            }
            root.height = max(getHeight(root.left),getHeight(root.right)) + 1;
            root=root.parent;
        }
        return;
    }
    public void rotate_clockwise(AvlTree_Node x,AvlTree_Node y){
        y.left = x.right;
        if(x.right!=null)
            x.right.parent = y;
        x.right = y;
        x.parent = y.parent;
        if(y==this.root)
            this.root = x;
        else {
            if (y.key < y.parent.key)
                y.parent.left = x;
            else
                y.parent.right = x;
        }
        y.parent =x;
        y.height = max(getHeight(y.left),getHeight(y.right)) + 1;
        x.height = max(getHeight(x.left),getHeight(x.right))+ 1;
        return;
    }
    public void rotate_anticlockwise(AvlTree_Node x,AvlTree_Node y){
        y.right = x.left;
        if(x.left!=null)
            x.left.parent = y;
        x.left = y;
        x.parent = y.parent;
        if(y==this.root)
            this.root = x;
        else{
            if(y.key<y.parent.key)
                y.parent.left = x;
            else
                y.parent.right = x;
        }
        y.parent =x;
        y.height = max(getHeight(y.left),getHeight(y.right)) + 1;
        x.height = max(getHeight(x.left),getHeight(x.right)) + 1;
        return;
    }
    public void deleteNode(int id,AvlTree_Node root) throws IllegalIDException {
        if(root==null){
            throw new IllegalIDException("IllegalId");
        }
        if (root.key == id) {
            size-=1;
            AvlTree_Node p = root.parent;
            if(p==null)
                throw new IllegalIDException("cant fire boss");
            if(root.left==null && root.right==null){
                root.maintree_ref=null;
                if(p.key>id)
                    p.left = null;
                else
                    p.right = null;
                p.height = max(getHeight(p.left),getHeight(p.right))+1;
            }
            if (root.left==null &&  root.right!=null){
                if (p.key > id)
                    p.left = root.right;
                else
                    p.right = root.right;
                p.height = max(getHeight(p.left),getHeight(p.right)) + 1;
                root.right.parent=p;
                root.right=null;
                root.parent = null;
                root.maintree_ref=null;
            }
            if (root.left!=null &&  root.right==null){
                if (p.key > id)
                    p.left = root.left;
                else
                    p.right = root.left;
                p.height = max(getHeight(p.left),getHeight(root.right)) + 1;
                root.left.parent=p;
                root.left=null;
                root.parent = null;
                root.maintree_ref=null;
            }
            if (root.left!=null &&  root.right!=null){
                AvlTree_Node y;
                y=root.right;
                while(y.left!=null){
                    y=y.left;
                }
                root.key = y.key;
                root.maintree_ref = y.maintree_ref;
                root=y;
                p = root.parent;
                if(root.left==null && root.right==null){
                    root.maintree_ref=null;
                    if(p.key>id)
                        p.left = null;
                    else
                        p.right = null;
                    p.height = max(getHeight(p.left),getHeight(p.right))+1;
                }
                if (root.left==null &&  root.right!=null){
                    if (p.key > id)
                        p.left = root.right;
                    else
                        p.right = root.right;
                    p.height = max(getHeight(p.left),getHeight(p.right)) + 1;
                    root.right.parent=p;
                    root.right=null;
                    root.parent = null;
                    root.maintree_ref=null;
                }
                if (root.left!=null &&  root.right==null){
                    if (p.key > id)
                        p.left = root.left;
                    else
                        p.right = root.left;
                    p.height = max(getHeight(p.left),getHeight(root.right)) + 1;
                    root.left.parent=p;
                    root.left=null;
                    root.parent = null;
                    root.maintree_ref=null;
                }
            }
            checkHeights2(p);
            return;
        }
        if (root.key > id) {
            deleteNode(id, root.left);
            return;
        }
        deleteNode(id, root.right);
        return;
    }
    public static int max(int a1,int a2){
        if(a1>=a2){
            return a1;
        }
        return a2;
    }
    public static int abs(int a1,int a2){
        if(a1>=a2)
            return a1-a2;
        return a2-a1;
    }
    public int getHeight(AvlTree_Node p){
            if (p==null)
                return -1;
            return p.height;
        }
}
class AvlTree_Node{
    public int key;
    public AvlTree_Node left;
    public AvlTree_Node right;
    public AvlTree_Node parent;
    public Node maintree_ref;
    public int height;
    public AvlTree_Node(){
        left = null;
        right = null;
        parent = null;
        maintree_ref=null;
    }
}
