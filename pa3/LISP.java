/*
 * Name: Su Yong Chang
 * Student ID #: 2016125016
 */

/*
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class LISP implements ILISP {
    public IStack<Character> stack = new Stack<Character>();
    /*
    * Use some variables for your implementation.
    */

    public LISP() {
        /*
        * Constructor
        * This function is an initializer for this class.
        */
    }

    @Override
    public boolean checkBracketBalance(String expression) {
        /*
        * Function input:
        *  + expression: A expression containing brackets.
        *
        * Job:
        *  Return if the four conditions meet:
        *   1. A left bracket and a right bracket of the same type consist a pair.
        *   2. There are no two pairs containing the same bracket.
        *   3. The left bracket must precede the corresponding right bracket.
        *   4. Pairs of different types of brackets must not intersect each other.
        */
        while(!stack.isEmpty())
            stack.pop();
        int len = expression.length();
        for(int i=0;i<len;i++)
        {
            char ch = expression.charAt(i);
            if(ch == '(' || ch == '{' || ch == '[')
                stack.push(ch);
            else if(ch == ')')
            {
                if(stack.isEmpty())
                    return false;
                else
                {
                    char tmpChar = stack.pop();
                    if(tmpChar == '{' || tmpChar == '[')
                        return false;
                }
            }
            else if(ch == '}')
            {
                if(stack.isEmpty())
                    return false;
                else
                {
                    char tmpChar = stack.pop();
                    if(tmpChar == '(' || tmpChar == '[')
                        return false;
                }
            }
            else if(ch == ']')
            {
                if(stack.isEmpty())
                    return false;
                else
                {
                    char tmpChar = stack.pop();
                    if(tmpChar == '(' || tmpChar == '{')
                        return false;
                }
            }
        }
        if(stack.isEmpty())
            return true;
        else
            return false;
    }
}
