public class eventheap {
    eventheapnode []A;
    private int size;
    int num_events;
    private cust_info c;
    private counterheap c_heap;
    chefqueue c_queue;
    public eventheap(cust_info c, counterheap c_heap,chefqueue c_queue){
        this.A = new eventheapnode[2];
        this.size = 2;
        this.num_events = 0;
        this.c = c;
        this.c_heap = c_heap;
        this.c_queue = c_queue;
    }
    public void insertEvent(int eventid, int cust_id, int time,int numb){
        if(this.size == this.num_events + 1){
            eventheapnode [] temp = new eventheapnode[2*size];
            for (int i = 1; i < size; i++) {
                temp[i] = this.A[i];
            }
            this.A = temp;
            this.size = 2*size;
        }
        A[this.num_events + 1] = new eventheapnode(eventid,cust_id,time,numb);
        percUp(this.num_events+1,A[this.num_events + 1]);
        this.num_events +=1;
        return;
    }
    public void deleteMinEvent(){
        this.A[1] = this.A[this.num_events];
        this.num_events -=1;
        percDown(1,this.A[1]);
        return;
    }
    public void runEvent(){
        if(A[1].event_id == 1){
            //change queue length
            //change customer state
            //insert into queue
            //call eventid 2 if needed
            c_heap.removeCust(c.A[A[1].cust_id-1].p);
            c.A[A[1].cust_id-1].cust_state = c_heap.size+1;
            if(c_queue.front!=null) {
                c_queue.enqueue(A[1].cust_id, c.A[A[1].cust_id - 1].numb);
                c_queue.griddlewait+=c.A[A[1].cust_id - 1].numb;
            }
            else{
                if(c_queue.griddlestate==0){
                    if(c.A[A[1].cust_id-1].numb > c_queue.max){
                        c_queue.enqueue(A[1].cust_id,c.A[A[1].cust_id-1].numb - c_queue.max);
                        c_queue.griddlestate = c_queue.max;
                        c_queue.griddlewait = c_queue.rear.numb;
                        insertEvent(2,A[1].cust_id,A[1].time + 10,c_queue.max);
                    }
                    else{
                        c_queue.griddlestate = c.A[A[1].cust_id - 1].numb;
                        insertEvent(2,A[1].cust_id,A[1].time + 10,c.A[A[1].cust_id-1].numb);
                    }
                }
                else{
                    if(c_queue.max - c_queue.griddlestate >= c.A[A[1].cust_id-1].numb){
                        insertEvent(2,A[1].cust_id,A[1].time + 10,c.A[A[1].cust_id-1].numb);
                        c_queue.griddlestate += c.A[A[1].cust_id - 1].numb;
                    }
                    else{
                        c_queue.enqueue(A[1].cust_id,c.A[A[1].cust_id-1].numb - (c_queue.max - c_queue.griddlestate));
                        insertEvent(2,A[1].cust_id,A[1].time + 10,c_queue.max - c_queue.griddlestate);
                        c_queue.griddlestate = c_queue.max;
                        c_queue.griddlewait+=c_queue.rear.numb;
                    }
                }

            }
        }
        if(A[1].event_id == 2){
            c.A[A[1].cust_id-1].completed_burgers += A[1].numb;
            if(c.A[A[1].cust_id-1].completed_burgers == c.A[A[1].cust_id-1].numb){
                insertEvent(3,A[1].cust_id,A[1].time + 1,0);
            }
            int i =0;
            c_queue.griddlestate -= A[1].numb;
            while(c_queue.front != null && i < A[1].numb){
                i += c_queue.front.numb;
                if(i>A[1].numb){
                    c_queue.front.numb = i - A[1].numb;
                    c_queue.griddlestate = c_queue.max;
                    c_queue.griddlewait -= c.A[c_queue.front.id-1].numb - c_queue.front.numb;
                    insertEvent(2, c_queue.front.id, A[1].time + 10,c.A[c_queue.front.id-1].numb - c_queue.front.numb);
                }
                else {
                    insertEvent(2, c_queue.front.id, A[1].time + 10,c_queue.front.numb);
                    c_queue.griddlestate += c_queue.front.numb;
                    c_queue.griddlewait -= c_queue.front.numb;
                    c_queue.dequeue();
                }
            }
        }
        if(A[1].event_id == 3){
            c.A[A[1].cust_id-1].cust_state = c_heap.size+2;
            c.A[A[1].cust_id-1].leave_time = A[1].time;
        }
        deleteMinEvent();
    }
    public void percDown(int i, eventheapnode p){
        if (2*i>this.num_events){
            A[i] = p;
            return;
        }
        if (2*i == this.size){
            if(eventComparison(A[2*i],p)){
                A[i] = A[2*i];
                A[2*i] = p;
                return;
            }
            A[i]=p;
            return;
        }
        int j = 0;
        if(eventComparison(A[2*i + 1], A[2*i]))
            j = 2*i + 1;
        else
            j = 2*i;
        if (eventComparison(A[j], p)) {
            A[i]=A[j];
            percDown(j,p);
            return;
        }
        A[i]=p;
        return;
    }
    public void percUp(int i,eventheapnode p){
        if (i==1){
            A[i]=p;
            return;
        }
        if (eventComparison(p,A[i/2])){
            A[i]=A[i/2];
            percUp(i/2,p);
            return;
        }
        A[i]=p;
        return;
    }
    public boolean eventComparison(eventheapnode p1 , eventheapnode p2){
        if(p1.time < p2.time)
            return true;
        if(p1.time == p2.time){
            if(p1.event_id < p2.event_id)
                return true;
            if(p1.event_id == p2.event_id){
                if(p1.event_id == 1){
                    if(this.c.A[p1.cust_id-1].p.queue_num > this.c.A[p2.cust_id-1].p.queue_num){
                        return true;
                    }
                    return false;
                }
                if(p1.event_id==2)
                {
                    if(p1.cust_id < p2.cust_id)
                        return true;
                    return false;
                }
                return true; //returned true for eventid 2 and 3 as order doesnt matter
            }
            return false;
        }
        return false;
    }
}
class eventheapnode {
    int event_id;
    int cust_id;
    int time;
    int numb = 0;
    public eventheapnode(int eventid, int cust_id, int time, int numb){
        this.event_id = eventid;
        this.cust_id = cust_id;
        this.time = time;
        if(event_id==2)
            this.numb = numb;
    }

}