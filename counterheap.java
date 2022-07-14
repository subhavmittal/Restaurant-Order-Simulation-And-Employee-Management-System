public class counterheap{
    private heapnode [] A;
    int size;
    public counterheap(int k){
        this.A = new heapnode[k+1];
        this.size = k;
        for (int i = 1; i < k+1; i++) {
            this.A[i] = new heapnode(i,0);
        }
    }
    public void percDown(int i, heapnode p){
        if (2*i>this.size){
            A[i] = p;
            p.i = i;
            return;
        }
        if (2*i == this.size){
            if(A[2*i].queue_length<p.queue_length){
                A[i] = A[2*i];
                A[i].i = i;
                A[2*i] = p;
                p.i = 2*i;
                return;
            }
            if(A[2*i].queue_length==p.queue_length){
                if(A[2*i].queue_num < p.queue_num){
                    A[i]=A[2*i];
                    A[i].i = i;
                    A[2*i] = p;
                    p.i = 2*i;
                    return;
                }

            }
            A[i]=p;
            p.i=i;
            return;
        }
        int j = 0;
        if(A[2*i].queue_length > A[2*i + 1].queue_length)
            j = 2*i + 1;
        if(A[2*i].queue_length < A[2*i + 1].queue_length)
            j = 2*i;
        if(A[2*i].queue_length == A[2*i + 1].queue_length){
            if(A[2*i].queue_num > A[2*i + 1].queue_num)
                j = 2*i + 1;
            else
                j = 2*i;
        }
        if (A[j].queue_length<p.queue_length) {
            A[i]=A[j];
            A[i].i = i;
            percDown(j,p);
            return;
        }
        if(A[j].queue_length==p.queue_length){
            if(A[j].queue_num<p.queue_num){
                A[i]=A[j];
                A[i].i = i;
                percDown(j,p);
                return;
            }
        }
        A[i]=p;
        p.i=i;
        return;
    }
    public heapnode addCust(int t){
        this.A[1].queue_length +=1;
        heapnode temp = this.A[1];
        if(t > this.A[1].time)
            this.A[1].time = t + this.A[1].queue_num;
        else
            this.A[1].time +=this.A[1].queue_num;
        percDown(1,A[1]);
        return temp;
    }
    public void removeCust(heapnode p){
        this.A[p.i].queue_length-=1;
        percUp(p.i,p);
    }
    public void percUp(int i,heapnode p){
        if (i==1){
            A[i]=p;
            p.i = i;
            return;
        }
        if (A[i/2].queue_length > p.queue_length){
            A[i]=A[i/2];
            A[i].i = i;
            percUp(i/2,p);
            return;
        }
        if(A[i/2].queue_length == p.queue_length){
            if(A[i/2].queue_num > p.queue_num){
                A[i]=A[i/2];
                A[i].i = i;
                percUp(i/2,p);
                return;
            }
        }
        A[i]=p;
        p.i = i;
        return;
    }

}
class heapnode{
    int queue_num;
    int queue_length;
    int i;
    int time;
    public heapnode(int q1,int q2){
        queue_num = q1;
        queue_length = q2;
        i = q1;
        time = 0;
    }
}