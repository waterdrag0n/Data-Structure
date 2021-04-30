/*
 * Name: Su Yong Chang
 * Student ID #: 2016125016
 */

/*
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class Array implements IArray {
    /*
     * Add some variables you will use.
     */
    boolean sorted;
    int arraySize;
    int totalSize;
    int[] arr;
    int mergeArr[];

    public Array() {
        /*
         * Constructor
         * This function is an initializer for this class.
         */
        sorted = false;
        arraySize = 0;
        arr = new int[10];
        totalSize = 10;
    }

    int binarySearch(int arr[], int target, int front, int back) {
        if(front>back) {
            return -1;
        }
        int middle = (front+back)/2;

        if(target == arr[middle]) {
            return middle;
        }
        else if(target<arr[middle]) {
            return binarySearch(arr, target, front, middle-1);
        }else {
            return binarySearch(arr, target, middle+1,back);
        }

    }

    @Override
    public void insert(int value) {
        /*
         * Function input:
         *  + value: An integer to be inserted.
         *
         * Job:
         *  Insert the given integer according to the state of the array.
         *  - unsorted: insert the integer as the last element of the array.
         *  - sorted: insert the integer at the position that makes the array sorted in ascending order.
         */
        if(arraySize >= totalSize) {
            totalSize*=2;
            int tmp[] = new int[totalSize];
            for(int i=0;i<arraySize;i++) {
                tmp[i] = arr[i];
            }
            arr = tmp;
        }

        arr[arraySize++] = value;
        if(sorted) {
            sort();
        }
    }

    @Override
    public void delete(int value) throws IllegalStateException {
        /*
         * Function input:
         *  + value: An integer to delete.
         *
         * Job:
         *   Delete the first element that has the given integer as its value.
         *   If there is no such element, raise an exception.
         */
        if(arraySize == 0)
            throw new IllegalStateException();
        int idx = -1;
        if(sorted){
            idx = binarySearch(arr,value,0,arraySize-1);
        }else{
            for(int i=0;i<arraySize;i++){
                if(value == arr[i]){
                    idx = i;
                    break;
                }
            }
        }
        if(idx ==-1)
            throw new IllegalStateException();
        else{
            for(int i=idx+1;i<arraySize;i++)
                arr[i-1] = arr[i];

            arr[arraySize-1] = 0;
            arraySize--;
        }
    }

    @Override
    public int search(int value) throws IllegalStateException {
        /*
         * Function input:
         *  + value: An integer to search.
         *
         * Job:
         *  Return the first index of the element with the given value.
         *  If there is no such element, raise an exception.
         */
        if(arraySize == 0)
            throw new IllegalStateException();

        int idx = -1;
        if(sorted){
            idx = binarySearch(arr,value,0,arraySize-1);
        }else{
            for(int i=0;i<arraySize;i++){
                if(value == arr[i]){
                    idx = i;
                    break;
                }
            }
        }
        if(idx ==-1)
            throw new IllegalStateException();
        else
            return idx;

    }

    @Override
    public void sort() {
        /**
         * Function input: Nothing
         *
         * Job:
         *  Change the state of the array to sorted.
         *  Sort the elements in the array in ascending order.
         */
        mergeArr = new int[arraySize];
        mergeSort(arr, 0, arraySize-1);
        sorted = true;
    }

    public void merge(int arr[], int front, int back) {
        int middle = (front+back)/2;
        int i = front;
        int j = middle+1;
        int idx = front;

        while(i<=middle && j<=back) {
            if(arr[i]<arr[j]) {
                mergeArr[idx++] = arr[i++];
            }else {
                mergeArr[idx++] = arr[j++];
            }
        }

        if(i<=middle) {
            while(i<=middle) {
                mergeArr[idx++] = arr[i++];
            }
        }else {
            while(j<=back) {
                mergeArr[idx++] = arr[j++];
            }
        }

        for(int q = front;q<=back;q++) {
            arr[q] = mergeArr[q];
        }
    }

    public void mergeSort(int arr[], int front, int back) {
        int middle = (front+back)/2;
        if(front<back) {
            mergeSort(arr, front, middle);
            mergeSort(arr, middle+1,back);
            merge(arr, front, back);
        }
    }

    @Override
    public void unsort() {
        /**
         * Function input: Nothing
         *
         * Job:
         *  Change the state of the array to unsorted.
         */
        sorted = false;
    }

    @Override
    public int atIndex(int index) throws IndexOutOfBoundsException {
        /**
         * Function input:
         *  + index: An integer to find the element at that position
         *
         * Job:
         *  Return the value of the element at the given index.
         */

        if(index >= arraySize)
            throw new IndexOutOfBoundsException();
        else
            return arr[index];
    }

    @Override
    public int size() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the number of elements in this array.
        */
        return arraySize;
    }

    @Override
    public boolean isSorted() {
        /**
         * Function input: Nothing
         *
         * Job:
         *  Return true if the array is sorted and false otherwise.
         */
        return sorted;
    }

    @Override
    public boolean isEmpty() {
        /* You do not have to edit this function. */
        return size() == 0;
    }
}
