public class cust_info {
    int size;
    cust_node[] A;
    int num;
    public cust_info(){
        A = new cust_node[1];
        size = 1;
        num = 0;
    }
    public void insertInfo(int id,heapnode p,int t,int numb) throws IllegalNumberException {
        if(id<=size && id>0) {
            this.A[id - 1] = new cust_node(p,t,numb);
            this.num =id;
            return;
        }
        if(id>size+1 || id<=0)
            throw new IllegalNumberException("s");
        cust_node[] temp = new cust_node[size*2];
        for (int i = 0; i < size; i++) {
            temp[i] = this.A[i];
        }
        temp[size] = new cust_node(p,t,numb);
        this.A = temp;
        this.size = 2*size;
        this.num =id;
        return;
    }
    public int getcustomerstate(int id){
        return A[id-1].cust_state;
    }
}
class cust_node{
    heapnode p;
    int cust_state;
    int arrive_time;
    int leave_time;
    int numb;
    int completed_burgers = 0;
    public cust_node(heapnode p,int t,int numb){
        this.p = p;
        this.cust_state = p.queue_num;
        this.arrive_time =t;
        this.numb = numb;
    }
}
