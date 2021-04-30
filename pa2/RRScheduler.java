/*
 * Name: Su Yong Chang
 * Student ID #: 2016125016
 */

/*
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class RRScheduler implements IRRScheduler {
    /*
     * Add some variables you will use.
     */
    CDLList list;
    boolean dir;

    RRScheduler(){
        list = new CDLList();
        dir = true;
    }

    @Override
    public void insert(int id) {
        /*
        * Function input:
        *  + id: the job id to insert
        *
        * Job:
        *  Insert the job at the back of the scheduler.
        */
        list.insert(id);
    }

    @Override
    public void done() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  One time segment passes and the job processed is deleted
        */

        if(dir){
            list.rotateForward();
            list.delete();
        }else{
            list.rotateForward();
            list.delete();
            if(!list.isEmpty())
                list.rotateBackward();
        }
    }

    @Override
    public void timeflow(int n) {
        /*
        * Function input:
        *  + n: An integer.
        *
        * Job:
        *  Simulate n time segments.
        */
        for(int i=0;i<n;i++)
        {
            if(dir)
                list.rotateForward();
            else
                list.rotateBackward();
        }
    }

    @Override
    public void changeDirection() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Change the direction of the scheduler.
        */
        dir = !dir;
    }

    @Override
    public int currentJob() throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the current job.
        */
        if(list.isEmpty())
            throw new IllegalStateException();
        else
            return list.getHead().getValue();
    }
}
