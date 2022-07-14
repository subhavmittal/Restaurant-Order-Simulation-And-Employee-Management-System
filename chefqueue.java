// Java program for linked-list implementation of queue

// A linked list (LL) node to store a queue entry
class QNode {
    int numb;
    int id;
    QNode next;

    // constructor to create a new linked list node
    public QNode(int id,int numb)
    {
        this.numb = numb;
        this.id = id;
        this.next = null;
    }
}

// A class to represent a queue
// The queue, front stores the front node of LL and rear stores the
// last node of LL
public class chefqueue {
    QNode front, rear;
    int griddlestate;
    int griddlewait;
    int max;
    public chefqueue(int m)
    {
        this.front = this.rear = null;
        this.max = m;
    }

    // Method to add an key to the queue.
    void enqueue(int id, int numb)
    {

        // Create a new LL node
        QNode temp = new QNode(id,numb);
        // If queue is empty, then new node is front and rear both
        if (this.rear == null) {
            this.front = this.rear = temp;
            return;
        }

        // Add the new node at the end of queue and change rear
        this.rear.next = temp;
        this.rear = temp;
    }

    // Method to remove an key from queue.
    void dequeue()
    {
        // If queue is empty, return NULL.
        if (this.front == null)
            return;

        // Store previous front and move front one node ahead
        QNode temp = this.front;
        this.front = this.front.next;

        // If front becomes NULL, then change rear also as NULL
        if (this.front == null)
            this.rear = null;
    }
}