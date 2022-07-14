

public class MMBurgers implements MMBurgersInterface {
    int M;
    int K;
    int current_time=0;
    eventheap manage_burgers;
    cust_info manage_customers;
    counterheap manage_queues;
    chefqueue manage_orders;


    public MMBurgers(){
        this.manage_customers = new cust_info();
    }
    public boolean isEmpty(){
        //if event heap is empty return true
        if(this.manage_burgers.num_events == 0)
            return true;
        return false;
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    } 
    
    public void setK(int k) throws IllegalNumberException{
        if(k<=0)
            throw new IllegalNumberException("s");
        this.manage_queues = new counterheap(k);
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    }   
    
    public void setM(int m) throws IllegalNumberException{
        if(m<=0)
            throw new IllegalNumberException("s");
        this.manage_orders = new chefqueue(m);
        this.manage_burgers = new eventheap(this.manage_customers,this.manage_queues,this.manage_orders);
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public void advanceTime(int t) throws IllegalNumberException{
        //run event heap till time t
        current_time = t;
        if(t<0)
            throw new IllegalNumberException("s");
        while(manage_burgers.num_events!=0){
            if(manage_burgers.A[1].time>t)
                break;
            manage_burgers.runEvent();
        }
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException{
        //your implementation
        if(numb<=0)
            throw new IllegalNumberException("s");
        if(t<current_time)
            throw new IllegalNumberException("s");
        current_time = t;
        advanceTime(t);
        heapnode p = this.manage_queues.addCust(t);
        this.manage_customers.insertInfo(id,p,t,numb);
        manage_burgers.insertEvent(1,id,p.time,0);
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public int customerState(int id, int t) throws IllegalNumberException{
        //your implementation
        if(t<current_time)
            throw new IllegalNumberException("s");
        current_time = t;
        advanceTime(t);
        if(manage_customers.num<id)
            return 0;
        return this.manage_customers.getcustomerstate(id);
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    } 

    public int griddleState(int t) throws IllegalNumberException{
        //your implementation
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        if(t<current_time)
            throw new IllegalNumberException("s");
        current_time = t;
        advanceTime(t);
        return this.manage_orders.griddlestate;
    }

    public int griddleWait(int t) throws IllegalNumberException{
        //your implementation
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        if(t<current_time)
            throw new IllegalNumberException("s");
        current_time = t;
        advanceTime(t);
        return this.manage_orders.griddlewait;
    } 

    public int customerWaitTime(int id) throws IllegalNumberException{
        //your implementation
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        return this.manage_customers.A[id-1].leave_time - this.manage_customers.A[id-1].arrive_time;
    }

	public float avgWaitTime(){
        int sum=0;
        //your implementation
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        for (int i = 1; i <= this.manage_customers.num; i++) {
            try {
                sum += customerWaitTime(i);
            }catch (IllegalNumberException e) {
                e.printStackTrace();
            }
        }
        return (float)sum/this.manage_customers.num;
    } 

    
}
