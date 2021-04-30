/*
* Name: Su Yong Chang
* Student ID #: 2016125016
*/

/*
* Do NOT import any additional packages/classes.
* If you (un)intentionally use some additional packages/classes we did not
* provide, you may receive a 0 for the homework.
*/

public class PostfixCalc implements ICalc {
    public IStack<Integer> s;
    /* Use some variables for your implementation. */

    public PostfixCalc() {
        this.s = new Stack<>();
        /*
        * Constructor
        */
    }

    @Override
    public int evaluate(String expression) {
        /*
        * Function input:
        *  + expression: A postfix expression.
        *
        * Job:
        *  Return the value of the given expression.
        *
        * The postfix expression is a valid expression with
        * length of at least 1.
        * There are +(addition), -(subtraction) and *(multiplication)
        * operators and only non-negative integers, seperated with
        * a single space symbol.
        */

        String arr[] = expression.split(" ");
        for(int i=0;i<arr.length;i++)
         {
             if(arr[i].equals("+"))
             {
                 int a = s.pop();
                 int b = s.pop();
                 s.push(a+b);
             }
             else if(arr[i].equals("-"))
             {
                 int a = s.pop();
                 int b = s.pop();
                 s.push(b-a);
             }
             else if(arr[i].equals("*"))
             {
                 int a = s.pop();
                 int b = s.pop();
                 s.push(a*b);
             }
             else
             {
                 s.push(Integer.parseInt(arr[i]));
             }
         }
         return s.pop();
    }
}
