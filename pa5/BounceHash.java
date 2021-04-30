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

public final class BounceHash<K> implements IHash<K> {
    /*
    * Use some variables for your implementation.
    */
    IBounceHashFunction<K> h;
    IResizeFunction ex;
    K[] hTable;
    boolean[] flag;
    int tablesize;
    int sz;
    ArrayList<K> elem;
    public BounceHash(IBounceHashFunction<K> h, IResizeFunction ex, int tablesize) {
        /*
         * Constructor
         * This function is an initializer for this class.
         * Input:
         *  + IBounceHashFunction<K> h:
         *      int h.hash1(K key, int tablesize): returns an integral hash value of key with h_1.
         *      int h.hash2(K key, int tablesize): returns an integral hash value of key with h_2.
         *      void h.changeHashFn(int tablesize): change the hash functions for rehashing.
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
        elem = new ArrayList<>();
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
         *  If the insertion results in a cycle, 
         *  If the key is already in the hashtable, ignore the request.
         *  To decide whether two keys are equal,
         *  you must use _key.equals_ method.
         */

        if(exists(key))
            return;

        elem.add(key);
        ArrayList<K> backUp = new ArrayList<>();
        for(int q=0;q<tablesize;q++){
            if(flag[q]){
                if(!backUp.contains(hTable[q]))
                    backUp.add(hTable[q]);
            }
                
        }
        backUp.add(key);
            


        int idx = Math.abs(h.hash1(key, tablesize));
        K val = key;
        
        if(!flag[idx]){
            hTable[idx]=key;
            flag[idx] = true;
        }else{
            int visited[] = new int[elem.size()];
            while(true){
                if(!flag[idx]){
                    hTable[idx] = val;
                    flag[idx] = true;
                    break;
                }
                
                K tmp = hTable[idx];
                hTable[idx] = val;
                val = tmp;
                visited[elem.indexOf(hTable[idx])]++;
                
                if(visited[elem.indexOf(hTable[idx])]>=5){
                    ArrayList<K> bu = new ArrayList<>();
                    for(int k = 0;k<backUp.size();k++){
                        if(!bu.contains(backUp.get(k))){
                            bu.add(backUp.get(k));
                        }
                    }

                    if(!bu.contains(key))
                        bu.add(key);
                    
                    if(!bu.contains(val))
                        bu.add(val);
                    
                    if(!bu.contains(hTable[idx]))
                        bu.add(hTable[idx]);
                    
                    
                    rehash(bu);
                    
                    break;
                }

                if(Math.abs(h.hash1(val, tablesize))==Math.abs(h.hash1(hTable[idx],tablesize))){
                    idx = Math.abs(h.hash2(val, tablesize));
                    
                }else{
                    idx = Math.abs(h.hash1(val, tablesize));
                    
                }
                
            }
        }
        sz++;
        show();
        if(ex.checkResize(tablesize, sz)){
            resize();
        }
    }

    public void resize(){
        int newTableSize = ex.extendTable(tablesize);
        K[] newTable = (K[]) new Object[newTableSize];
        boolean[] newFlag = new boolean[newTableSize];

        ArrayList<K> backUp = new ArrayList<>();
        for(int q=0;q<tablesize;q++){
            if(flag[q]){
                if(!backUp.contains(hTable[q]))
                    backUp.add(hTable[q]);
            }
                
        }

        
        for(int i=0;i<backUp.size();i++){

            int idx = Math.abs(h.hash1(backUp.get(i), newTableSize));
            K val = backUp.get(i);
            if(!newFlag[idx]){
                newTable[idx]=val;
                newFlag[idx] = true;
            }else{
                int visited[] = new int[tablesize];
                while(true){
                    if(!newFlag[idx]){
                        newTable[idx] = val;
                        newFlag[idx] = true;
                        break;
                    }
                    K tmp = newTable[idx];
                    newTable[idx] = val;
                    val = tmp;
                    visited[elem.indexOf(newTable[idx])]++;
                    if(visited[elem.indexOf(newTable[idx])]==5){
                        ArrayList<K> bu = new ArrayList<>();
                        for(int k = 0;k<backUp.size();k++)
                            if(!bu.contains(backUp.get(k)))
                                bu.add(backUp.get(k));
                        if(!bu.contains(val))
                            bu.add(val);
                        
                        hTable = newTable;
                        flag = newFlag;
                        tablesize = newTableSize;
                        rehash(bu);
                        return;
                    }
                    if(Math.abs(h.hash1(val, newTableSize))==Math.abs(h.hash1(newTable[idx],newTableSize))){
                        idx = Math.abs(h.hash2(val, newTableSize));
                    }else{
                        idx = Math.abs(h.hash1(val, newTableSize));
                    }
                    
                }
            }
        }
        hTable = newTable;
        flag = newFlag;
        tablesize = newTableSize;
    }

    public void rehash(ArrayList<K> backUp){
        
        h.changeHashFn(tablesize);
        K[] newTable = (K[]) new Object[tablesize];
        boolean[] newFlag = new boolean[tablesize];

        for(int i=0;i<backUp.size();i++){
            

            int idx = Math.abs(h.hash1(backUp.get(i), tablesize));
            K val = backUp.get(i);
            

            if(!newFlag[idx]){
                newTable[idx]=val;
                newFlag[idx] = true;
            }else{
                int visited[] = new int[backUp.size()];
                while(true){
                    if(!newFlag[idx]){
                        newTable[idx] = val;
                        newFlag[idx] = true;
                        
                        break;
                    }
                    K tmp = newTable[idx];
                    newTable[idx] = val;
                    val = tmp;
                    visited[backUp.indexOf(newTable[idx])]++;
                    
                    if(visited[backUp.indexOf(newTable[idx])]==5){
                        hTable = newTable;
                        flag = newFlag;
                        ArrayList<K> bu = new ArrayList<>();
                        
                        for(int q=0;q<tablesize;q++){
                            if(flag[q]){
                                if(!bu.contains(hTable[q]))
                                    bu.add(hTable[q]);
                            }
                                
                        }
                        for(int q=0;q<backUp.size();q++){
                            if(flag[q]){
                                if(!bu.contains(backUp.get(q)))
                                    bu.add(backUp.get(q));
                            }
                                
                        }
                        rehash(bu);
                        
                        return;
                    }
                    if(Math.abs(h.hash1(val, tablesize))==Math.abs(h.hash1(newTable[idx],tablesize))){
                        idx = Math.abs(h.hash2(val, tablesize));
                        
                    }else{
                        idx = Math.abs(h.hash1(val, tablesize));
                        
                    }
                    
                }
            }
        }
        
        hTable = newTable;
        flag = newFlag;
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
