/*
 * Name: Su Yong Chang
 * Student ID #: 2016125016
 */

/*
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class CDLList implements ICDLList {
    private Node head;
    /*
     * Add some variables you will use.
     */
    int listSize;

    public CDLList() {
        /*
         * Constructor
         * This function is an initializer for this class.
         */
        head = new Node();
        listSize = 0;
    }

    @Override
    public void insert(int value) {
        /*
         * Function input:
         *  + value: An integer to be inserted.
         *
         * Job:
         *  Insert the given integer to the list.
         */
        if(isEmpty()){
            head.setValue(value);
            head.setNext(head);
            head.setPrev(head);
        }else{
            Node newNode = new Node(value);
            newNode.setNext(head);
            newNode.setPrev(head.getPrev());
            head.getPrev().setNext(newNode);
            head.setPrev(newNode);
        }
        listSize++;
    }

    @Override
    public void delete() throws IllegalStateException {
        /*
         * Function input: Nothing
         *
         * Job:
         *  Delete the previous node of the head.
         */
        if(isEmpty())
            throw new IllegalStateException();
        else{
            head.getPrev().getPrev().setNext(head);
            head.setPrev(head.getPrev().getPrev());
            listSize--;
        }
    }

    @Override
    public Node getHead() throws IllegalStateException {
        /*
         * Function input: Nothing
         *
         * Job:
         *  return the reference of the head. If none, raise an exception.
         */
        if(isEmpty())
            throw new IllegalStateException();
        else
            return this.head;
    }

    @Override
    public void rotateForward() throws IllegalStateException {
        /*
         * Function input: Nothing
         *
         * Job:
         *  Rotate the list forward. If none, raise an exception.
         */
        if(isEmpty())
            throw new IllegalStateException();
        else
            head = head.getNext();
    }

    @Override
    public void rotateBackward() throws IllegalStateException {
        /*
         * Function input: Nothing
         *
         * Job:
         *  Rotate the list backward. If none, raise an exception.
         */
        if(isEmpty())
            throw new IllegalStateException();
        else
            head = head.getPrev();
    }

    @Override
    public int size() {
        /*
         * Function input: Nothing
         *
         * Job:
         *  Return the size of the list
         */
        return listSize;
    }

    @Override
    public boolean isEmpty() {
        /* You do not have to edit this function. */
        return size() == 0;
    }
}
