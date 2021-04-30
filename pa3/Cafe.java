/*
 * Name: Su Yong Chang
 * Student ID #: 2016125016
 */

/*
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class Cafe implements ICafe {
    /*
    * Use some variables for your implementation.
    */
    LList list;
    int cur;
    int st;
    LList process;

    public Cafe() {
        /*
        * Constructor
        * This function is an initializer for this class.
        */
        list = new LList();
        cur = 0;
        st=0;
        process = new LList();
    }

    @Override
    public void arrive(int Id, int arrivaltime, int coffee) {
        /*
        * Function input:
        *  + Id: Students Id
        *  + arrivaltime: Students arrival time
        *  + coffee: Time needed to prepare the coffee
        *
        * Job:
        *  Save the information to use later.
        */
        if(process.listSize <2)
            process.insertBack(Id, arrivaltime, coffee, arrivaltime>cur?arrivaltime:cur);
        else
            list.insertBack(Id, arrivaltime, coffee, 0);
    }
    @Override
    public int serve() {
        /**
         * Function input: Nothing
         *
         * Job:
         *  Serve the coffee to a strudent when ready. Return the student's Id.
         */
        int front = process.getHead().getNext().getCoffee()+process.getHead().getNext().getInserttime();
        int back = process.getTail().getPrev().getCoffee()+process.getTail().getPrev().getInserttime();
        int id;
        if(front<=back){
            id = process.getHead().getNext().getId();
            cur = front;
            st += (cur - process.getHead().getNext().getArrvalTime());
            process.deleteFront();
            if(!list.isEmpty()) {
                NNode tmp = list.deleteFront();
                tmp.setInserttime(tmp.getArrvalTime()>cur?tmp.getArrvalTime():cur);
                process.insertBack(tmp);
            }
        }else{
            id = process.getTail().getPrev().getId();
            cur = back;
            st += (cur-process.getTail().getPrev().getArrvalTime());
            process.deleteBack();
            if(!list.isEmpty()) {
                NNode tmp = list.deleteFront();
                tmp.setInserttime(tmp.getArrvalTime()>cur?tmp.getArrvalTime():cur);
                process.insertBack(tmp);
            }
        }
        return id;
    }
    @Override
    public int stat() {
        /**
         * Function input: Nothing
         *
         * Job:
         *  Calculate the sum of the waiting time of the served students.
         */
        return st;
    }
}

class NNode {
    public NNode next;
    public NNode prev;

    public int Id;
    public int arrvalTime;
    public int coffee;
    public int inserttime;

    public NNode() {
        next = null;
        prev = null;
        Id = 0;
        arrvalTime=0;
        coffee=0;
        inserttime=0;
    }

    public NNode(int Id, int arrvalTime, int coffee, int inserttime){
        next = null;
        prev = null;
        this.Id = Id;
        this.arrvalTime = arrvalTime;
        this.coffee = coffee;
        this.inserttime = inserttime;
    }

    NNode getNext() {
        return next;
    }

    NNode getPrev() {
        return prev;
    }

    void setNext(NNode next) {
        this.next = next;
    }

    void setPrev(NNode prev) {
        this.prev = prev;
    }

    int getId() {
        return Id;
    }

    void setId(int Id) {
        this.Id = Id;
    }

    int getArrvalTime() {
        return arrvalTime;
    }

    void setArrvalTime(int arrvalTime) {
        this.arrvalTime = arrvalTime;
    }

    int getCoffee() {
        return coffee;
    }

    void setCoffee(int coffee) {
        this.coffee = coffee;
    }

    int getInserttime() {
        return inserttime;
    }

    void setInserttime(int inserttime) {
        this.inserttime = inserttime;
    }
}

class LList{
    public NNode head;
    public NNode tail;
    public int listSize;

    public LList(){
        head = new NNode();
        tail = new NNode();
        listSize=0;
    }

    public NNode getHead(){
        return head;
    }

    public NNode getTail()
    {
        return tail;
    }


    public void insertBack(int Id, int arrvalTime, int coffee, int inserttime){
        NNode newNode = new NNode(Id, arrvalTime, coffee, inserttime);
        if(isEmpty()){
            head.setNext(newNode);
            tail.setPrev(newNode);
        }else{
            newNode.setPrev(tail.getPrev());
            tail.getPrev().setNext(newNode);
            tail.setPrev(newNode);
            newNode.setNext(tail);
        }
        listSize++;
    }

    public void insertBack(NNode nd){
        NNode newNode = nd;
        if(isEmpty()){
            head.setNext(newNode);
            tail.setPrev(newNode);
        }else{
            newNode.setPrev(tail.getPrev());
            tail.getPrev().setNext(newNode);
            tail.setPrev(newNode);
            newNode.setNext(tail);
        }
        listSize++;
    }
    public void insertFront(int Id, int arrvalTime, int coffee, int inserttime){
        NNode newNode = new NNode(Id, arrvalTime, coffee, inserttime);
        if(isEmpty()){
            head.setNext(newNode);
            tail.setPrev(newNode);
        }else{
            head.getNext().setPrev(newNode);
            newNode.setNext(head.getNext());
            newNode.setPrev(head);
            head.setNext(newNode);
        }
        listSize++;
    }
    public void insertFront(NNode nd){
        NNode newNode = nd;
        if(isEmpty()){
            head.setNext(newNode);
            tail.setPrev(newNode);
        }else{
            head.getNext().setPrev(newNode);
            newNode.setNext(head.getNext());
            newNode.setPrev(head);
            head.setNext(newNode);
        }
        listSize++;
    }
    public NNode deleteFront(){
        NNode tmp = head.getNext();
        if(listSize==1) {
            head.setNext(null);
            tail.setPrev(null);
            listSize--;
            return tmp;
        }
        head.getNext().getNext().setPrev(head);
        head.setNext(head.getNext().getNext());
        listSize--;
        return tmp;
    }

    public NNode deleteBack(){
        NNode tmp = tail.getPrev();
        if(listSize==1) {
            head.setNext(null);
            tail.setPrev(null);
            listSize--;
            return tmp;
        }
        tail.getPrev().getPrev().setNext(tail);
        tail.setPrev(tail.getPrev().getPrev());
        listSize--;
        return tmp;
    }

    public int size() {
        /*
         * Function input: Nothing
         *
         * Job:
         *  Return the size of the list
         */
        return listSize;
    }

    public boolean isEmpty() {
        /* You do not have to edit this function. */
        return size() == 0;
    }
}
