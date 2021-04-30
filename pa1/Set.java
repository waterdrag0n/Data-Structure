/*
 * Name: Su Yong Chang
 * Student ID #: 2016125016
 */

/*
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class Set implements ISet {
    /*
     * Add some variables you will use.
     */
    Array setArr;

    public Set() {
        /*
         * Constructor
         * This function is an initializer for this class.
         */
        setArr = new Array();
    }

    @Override
    public void insert(int value) {
        /*
         * Function input:
         *  + value: An integer to be inserted.
         *
         * Job:
         *  Insert the integer if it does not exist.
         */
        if(setArr.arraySize >= setArr.totalSize) {
            setArr.totalSize*=2;
            int tmp[] = new int[setArr.totalSize];
            for(int i=0;i<setArr.arraySize;i++) {
                tmp[i] = setArr.arr[i];
            }
            setArr.arr = tmp;
        }
        int idx = -1;
        for(int i=0;i<setArr.arraySize;i++){
            if(value == setArr.arr[i]){
                idx = i;
                break;
            }
        }
        if(idx == -1) {
            setArr.arr[setArr.arraySize++] =value;
        }
    }

    @Override
    public void delete(int value) throws IllegalStateException {
        /**
         * Function input:
         *  + value: An integer to be deleted
         *
         * Job:
         *  Delete the integer from the set.
         *  Raise an exception if it does not exist.
         */
        setArr.delete(value);
    }

    @Override
    public void union(ISet set) {
        /**
         * Function input:
         *  + set: A set to be unioned
         *
         * Job:
         *  Union the other set to this set
         */
        Set tmpSet = (Set) set;
        for(int i=0;i<tmpSet.setArr.arraySize;i++) {
            insert(tmpSet.setArr.arr[i]);
        }
    }

    @Override
    public void intersection(ISet set) {
        /**
         * Function input:
         *  + set: A set to be intersectioned
         *
         * Job:
         *  Intersect the other set to this set
         */
        Set tmpSet = (Set) set;
        int copyArr[] = new int[setArr.arraySize];
        for(int i=0;i<setArr.arraySize;i++)
            copyArr[i] = setArr.arr[i];

        for(int i=0;i<copyArr.length;i++) {
            int idx = -1;
            int value = copyArr[i];
            for(int j=0;j<tmpSet.setArr.arraySize;j++){
                if(value == tmpSet.setArr.arr[j]){
                    idx = j;
                    break;
                }
            }
            if(idx==-1) {
                delete(value);
            }
        }
    }

    @Override
    public void subtraction(ISet set) {
        /**
         * Function input:
         *  + set: A set to be subtracted
         *
         * Job:
         *  Subtract the other set from this set
         */
        Set tmpSet = (Set) set;
        int copyArr[] = new int[setArr.arraySize];
        for(int i=0;i<setArr.arraySize;i++)
            copyArr[i] = setArr.arr[i];

        for(int i=0;i<copyArr.length;i++) {
            int idx = -1;
            int value = copyArr[i];
            for(int j=0;j<tmpSet.setArr.arraySize;j++){
                if(value == tmpSet.setArr.arr[j]){
                    idx = j;
                    break;
                }
            }
            if(idx!=-1) {
                delete(value);
            }
        }
    }

    @Override
    public int[] show() {
        /**
         * Function input: Nothing
         *
         * Job:
         *  Return the elements of the set as an array.
         */
        int[] tmp = new int[setArr.arraySize];
        for(int i=0;i<setArr.arraySize;i++)
            tmp[i] = setArr.arr[i];
        return tmp;
    }
}
