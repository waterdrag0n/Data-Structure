/*
* Name: Su Yong Chang
* Student ID#: 2016125016
*/

/*
* Do NOT use any external packages/classes.
* If you (un)intentionally use them we did not provide,
* you will get 0.
* Also do NOT use auto-import function on IDEs.
* If the import statements change, you will also get 0.
*/

public final class Heap implements IHeap {
    public Node root;
    public int sz;
    /*
    * Use some variables for your implementation.
    */

    public Heap(Integer[] array) {
        /*
        * Constructor
        * This function is an initializer for this class.
        * Input:
        *  + array: The initial unsorted array of values.
        *           You need to construct the heap bottom-up.
        */
        sz = array.length;
        root = null;
        //making tree not in order
        if(sz==0){
            root = null;
        }else{
            for(int i=0;i<sz;i++){
                if(isEmpty()){
                    root = new Node(array[i]);
                }else{
                    Que q = new Que();
                    q.push(root);
                    while(true){
                        Node tmp = q.popFront();
                        if(tmp.getLChild()==null){
                            Node newNode = new Node(array[i]);
                            tmp.setLChild(newNode);
                            newNode.setParent(tmp);
                            break;
                        }else if(tmp.getRChild()==null){
                            Node newNode = new Node(array[i]);
                            tmp.setRChild(newNode);
                            newNode.setParent(tmp);
                            break;
                        }else{
                            q.push(tmp.getLChild());
                            q.push(tmp.getRChild());
                        }
                    }
                }
            }

            //putting all the elements in stack
            Que tmpQue = new Que();
            Que save = new Que();
            getAllElem(tmpQue, save);
            heapify(save);
        }
    }

    public void getAllElem(Que tmpQue, Que save){
        tmpQue.push(root);
        save.push(root);
        while(true){
            if(tmpQue.isEmpty()) break;
            Node tmp = tmpQue.popFront();
            if(tmp.getLChild()!=null){
                tmpQue.push(tmp.getLChild());
                save.push(tmp.getLChild());
                if(tmp.getRChild()!=null){
                    tmpQue.push(tmp.getRChild());
                    save.push(tmp.getRChild());
                }
            }
        }
    }

    public void heapify(Que save){
        while(true){
            if(save.isEmpty()) break;
            Node tmp = save.popBack();
            while(true){
                if(tmp.getLChild()==null && tmp.getRChild()==null)
                    break;
                else if(tmp.getLChild()!=null && tmp.getRChild()!=null){
                    if(tmp.getLChild().getData()>=tmp.getRChild().getData()){
                        if(tmp.getLChild().getData()>tmp.getData()){
                            swap(tmp, tmp.getLChild());
                            tmp = tmp.getLChild();
                        }else
                            break;
                    }else{
                        if(tmp.getRChild().getData()>tmp.getData()){
                            swap(tmp, tmp.getRChild());
                            tmp = tmp.getRChild();
                        }
                        else
                            break;
                    }
                }else if(tmp.getLChild()!=null){
                    if(tmp.getLChild().getData()>tmp.getData()){
                            swap(tmp, tmp.getLChild());
                            tmp = tmp.getLChild();
                    }else
                        break;
                }
            }
        }
    }

    @Override
    public void insert(Integer value) {
        /*
        * Function input:
        *  value: A value to be inserted.
        *
        * Job:
        *  Insert the item value at the right position of the heap.
        */
        /*
        * Function input:
        *  value: A value to be inserted.
        *
        * Job:
        *  Insert the item value at the right position of the heap.
        */
        if(isEmpty()){
            root = new Node(value);
            sz++;
            return;
        }
        Que q = new Que();
        Node target = null;
        q.push(root);
        while(true){
            Node tmp = q.popFront();
            if(tmp.getLChild()!=null && tmp.getRChild()!=null){
                q.push(tmp.getLChild());
                q.push(tmp.getRChild());
            }else{
                target = tmp;
                break;
            }
        }
        Node newNode = new Node(value);
        if(target.getLChild()== null){
            target.setLChild(newNode);
            newNode.setParent(target);
        }else{
            target.setRChild(newNode);
            newNode.setParent(target);
        }
        sz++;

        while(true){
            if(newNode.getParent()==null) break;
            if(newNode.getData() > newNode.getParent().getData()){
                swap(newNode, newNode.getParent());
                newNode = newNode.getParent();
            }else{
                break;
            }
        }
    }

    public void removeMin(int dt){
        if(root.getData() > dt){
                root.setData(dt);
                Node tmp = root;
                while(true){
                    if(tmp.getLChild()!=null && tmp.getRChild()!=null){
                        if(tmp.getLChild().getData()>=tmp.getRChild().getData()){
                            if(tmp.getLChild().getData()>dt){
                                swap(tmp.getLChild(),tmp);
                                tmp = tmp.getLChild();
                            }else{
                                break;
                            }
                        }else{
                            if(tmp.getRChild().getData()>dt){
                                swap(tmp.getRChild(),tmp);
                                tmp = tmp.getRChild();
                            }else{
                                break;
                            }
                        }
                    }else if(tmp.getLChild()!=null){
                        if(tmp.getLChild().getData()>dt){
                            swap(tmp.getLChild(),tmp);
                            tmp = tmp.getLChild();
                        }else{
                            break;
                        }
                    }else{
                        break;
                    }
                }
            }
    }

    @Override
    public Integer removeMax() throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Delete and return the maximum value in the heap.
        *  Throw an exception if the tree is empty.
        */
        if(isEmpty())
            throw new IllegalStateException();
        else if(sz==1) {
            int tmp = root.getData();
            root = null;
            sz--;
            return tmp;
        }
        int ans = root.getData();
        Que q = new Que();
        Node target = null;
        q.push(root);
        while(true){
            if(q.isEmpty()) break;
            Node tmp = q.popFront();
            if(tmp.getLChild()!=null && tmp.getRChild()!=null){
                q.push(tmp.getLChild());
                q.push(tmp.getRChild());
                target = tmp;
            }else if(tmp.getLChild()!=null){
                target = tmp;
                break;
            }else{
                break;
            }
        }
        if(target.getRChild()!=null) {
            swap(root, target.getRChild());
            target.getRChild().setParent(null);
            target.setRChild(null);
        }
        else {
            swap(root, target.getLChild());
            target.getLChild().setParent(null);
            target.setLChild(null);
        }

        Que tmpQue = new Que();
        Que save = new Que();
        getAllElem(tmpQue, save);
        heapify(save);
        sz--;
        return ans;
    }

    public void swap(Node a, Node b){
        int tmp = a.getData();
        a.setData(b.getData());
        b.setData(tmp);
    }

    @Override
    public Integer max() throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the maximum value in the heap.
        *  Throw an exception if the tree is empty.
        */
        if(isEmpty())
            throw new IllegalStateException();
        return root.getData();
    }

    @Override
    public int size() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the size of the heap
        */
        return sz;
    }

    @Override
    public Node getRoot() throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the root node of the heap.
        *  Throw an exception if the tree is empty.
        */
        if (!isEmpty()) {
            return root;
        }
        throw new IllegalStateException("There is no root");
    }

    @Override
    public boolean isEmpty() {
        /* You must not edit this function. */
        return root == null;
    }
}

class MyNode {
    public MyNode next;
    public MyNode prev;
    public Node value;

    public MyNode() {
        next = null;
        prev = null;
        value = null;
    }

    public MyNode(Node node){
        next = null;
        prev = null;
        value = node;
    }

    MyNode getNext() {
        return next;
    }

    MyNode getPrev() {
        return prev;
    }

    void setNext(MyNode next) {
        this.next = next;
    }

    void setPrev(MyNode prev) {
        this.prev = prev;
    }

    Node getValue() {
        return value;
    }

    void setValue(Node value) {
        this.value = value;
    }
}

class Que{
    public MyNode top;
    public MyNode tail;
    public int sz;

    public Que(){
        top = new MyNode();
        tail = new MyNode();
        sz=0;
    }

    public void push(Node node){
        MyNode newNode = new MyNode(node);
        if(isEmpty()){
            top.setNext(newNode);
            tail.setPrev(newNode);
            newNode.setPrev(top);
            newNode.setNext(tail);
        }else{
            tail.getPrev().setNext(newNode);
            newNode.setPrev(tail.getPrev());
            newNode.setNext(tail);
            tail.setPrev(newNode);
        }
        sz++;
    }
    public Node popFront(){
        Node tmp = top.getNext().getValue();
        if(size()==1){
            top.setNext(null);
            tail.setPrev(null);
        }else{
            top.getNext().getNext().setPrev(top);
            top.setNext(top.getNext().getNext());
        }
        sz--;
        return tmp;
    }

    public Node popBack(){
        Node tmp = tail.getPrev().getValue();
        if(size()==1){
            top.setNext(null);
            tail.setPrev(null);
        }else{
            tail.getPrev().getPrev().setNext(tail);
            tail.setPrev(tail.getPrev().getPrev());
        }
        sz--;
        return tmp;
    }

    public int size() {
        return sz;
    }

    public boolean isEmpty() {
        return size()==0;
    }
}
