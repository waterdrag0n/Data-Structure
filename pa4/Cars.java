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

public final class Cars implements ICars {
    public Heap heap;
    public int mx;
    /*
    * Use some variables for your implementation.
    */

    public Cars(int k) {
        /*
        * Constructor
        * This function is an initializer for this class.
        * Input:
        *  + k: The number of the cars you have to consider.
        */
        heap = new Heap(new Integer[] {});
        mx = k;
    }

    @Override
    public void carDistance(int d) {
        /*
        * Function input:
        *  d: The travel distance of a car. (always positive)
        *
        * Job:
        *  Determine whether or not to keep the travel distance d.
        *  Consider the total time complexity of the program
        */
        if(mx==0) return;
        int dt = -d;
        if(heap.size() < mx){
            heap.insert(dt);
        }else{
            heap.removeMin(dt);
        }
    }

    @Override
    public int[] getCandidates() {
        /*
        * Function input: Nothing.
        *
        * Job:
        *  Return the k longest travel distances of the travel distances inputed until now.
        *  (You do not have to return the travel distances sorted)
        */
        if(heap.isEmpty())
            return new int[] {};
        Heap tmpHeap = new Heap(new Integer[] {});
        int[] arr = new int[heap.size()];
        int heapSize = heap.size();
        for(int i=0;i<heapSize;i++){
            int dt = heap.removeMax();
            arr[i] = (-1)*dt;
            if(tmpHeap.size() < mx){
                tmpHeap.insert(dt);
            }else{
                tmpHeap.removeMin(dt);
            }
        }
        heap = tmpHeap;
        return arr;
    }
}
