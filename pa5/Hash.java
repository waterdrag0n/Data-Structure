/*
* Name: Su Yong Chang
* Student ID#: 2016125016
*/

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
/*
* Do NOT use any external packages/classes.
* If you (un)intentionally use them we did not provide,
* you will get 0.
* Also do NOT use auto-import function on IDEs.
* If the import statements change, you will also get 0.
*/

public final class Hash<K> implements IHash<K> {
    /*
    * Use some variables for your implementation.
    */
    int sz;
    int tablesize;
    IHashFunction<K> h;
    IResizeFunction ex;
    K[] hTable;
    boolean[] flag;

    public Hash(IHashFunction<K> h, IResizeFunction ex, int tablesize) {
        /*
         * Constructor
         * This function is an initializer for this class.
         * Input:
         *  + IHashFunction<K> h:
         *      int h.hash(K key, int tablesize): returns an integral hash value of key.
         *  + IResizeFunction ex:
         *      boolean ex.checkResize(int tablesize, int numItems): returns 'true' if the table must be extended for containing 'numItems' items. Otherwise, returns 'false'.
         *      int ex.extendTable(int tablesize): returns new tablesize for extended table.
         *  + tablesize: the initial table size of the hash table.
        */
        sz = 0;
        this.tablesize = tablesize;
        this.h = h;
        this.ex = ex;
        hTable = (K[]) new Object[tablesize];
        flag = new boolean[tablesize];
    }

    @Override
    public void put(K key) {
        /**
         * Input:
         * + key: A key to be added 
         * 
         * Job:
         *  Add the key into the hashtable.
         *  If the table must be extended, extend the table and retry adding the key.
         *  If the key is already in the hashtable, ignore the request.
         *  To decide whether two keys are equal,
         *  you must use _key.equals_ method.
         */
        if(exists(key))
            return;

        int hIdx = Math.abs(h.hash(key, tablesize));
        while(flag[hIdx]==true){
            hIdx++;
            if(hIdx==tablesize)
                hIdx %=tablesize;
        }
        hTable[hIdx] = key;
        flag[hIdx]=true;
        sz++;

        if(ex.checkResize(tablesize, sz))
            resize();
    }

    public void resize(){
        int newTableSize = ex.extendTable(tablesize);
        K[] newTable = (K[]) new Object[newTableSize];
        boolean[] newFlag = new boolean[newTableSize];
        for(int i=0;i<tablesize;i++){
            if(flag[i]==true){
                int hIdx = Math.abs(h.hash(hTable[i], newTableSize));
                while(newFlag[hIdx]==true){
                    hIdx++;
                    if(hIdx==tablesize)
                        hIdx %=tablesize;
                }
                newTable[hIdx] = hTable[i];
                newFlag[hIdx]=true;
            }
        }
        hTable = newTable;
        flag = newFlag;
        tablesize = newTableSize;
    }

    @Override
    public void remove(K key) throws IllegalStateException {
        /*
        * Input:
        *  + key: A key to be removed
        *
        * Job:
        *  Delete the key from the hash table.
        *  To decide whether two keys are equal,
        *  you must use _key.equals_ method.
        *  If the key does not exist in the table, raise an exception.
        */
        
        for(int i = 0;i<tablesize;i++){
            if(flag[i]){
                if(hTable[i].equals(key)){
                    flag[i] = false;
                    sz--;
                    return;
                }
            }
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean exists(K key) {
        /*
        * Input:
        *  + key: A key to be checked
        *
        * Job:
        *  Return true if the key is in the table; false otherwise.
        *  To decide whether two keys are equal,
        *  you must use _key.equals_ method.
        */
        
        for(int i = 0;i<tablesize;i++){
            if(flag[i]){
                if(hTable[i].equals(key))
                    return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        /*
        * Job:
        *  Return the number of items in the hashtable.
        */
        return sz;
    }

    @Override
    public int tablesize() {
        /*
        * Job:
        *  Return the size of current hashtable.
        */
        return tablesize;
    }

    @Override
    public List<K> show() {
        /*
        * Job:
        *  Return the items in the hashtable.
        *  The list index must be the bucket index of the item.
        *  If a bucket has no item, assign null.
        *  Note that you can use ArrayList.
        */
        ArrayList<K> list = new ArrayList<>(tablesize);
        for(int i=0;i<tablesize;i++){
            if(flag[i]==true)
                list.add(hTable[i]);
            else
                list.add(null);
        }
        return list;
    }
}
