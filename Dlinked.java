public class Dlinked {
    public DNode head;
    public DNode tail;
    public int size;
    public Dlinked(){
        head = new DNode();
        tail = new DNode();
        size=0;
        head.setNext(tail);
        tail.setPrev(head);
    }
    public DNode insertAtEnd(Node in){
        DNode listin = new DNode(in,tail,tail.getPrev());
        tail.getPrev().setNext(listin);
        tail.setPrev(listin);
        size+=1;
        return listin;
    }
    public void remove(Node out){
        size-=1;
        out.DNode_ref.getPrev().setNext(out.DNode_ref.getNext());
        out.DNode_ref.getNext().setPrev(out.DNode_ref.getPrev());
        out.DNode_ref.setNext(null);
        out.DNode_ref.setPrev(null);
        out.DNode_ref.setElement(null);
        out.DNode_ref = null;
    }
}
class DNode{

        private Node element;
        private DNode next;
        private DNode prev;
        public DNode() {
            this(null, null, null);
        }
        public DNode(Node e, DNode n, DNode p) {
            element = e;
            next = n;
            prev = p;
        }
        public Node getElement() {
            return element;
        }
        public DNode getNext() {
            return next;
        }
        public DNode getPrev() {
            return prev;
        }

        public void setElement(Node newElem) {
            element = newElem;
        }
        public void setNext(DNode newNext) {
            next = newNext;
        }
        public void setPrev(DNode newPrev) {
            prev = newPrev;
        }
}
