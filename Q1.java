import java.util.LinkedList;
import java.util.Scanner;

class Q1 {
    public static void main(String[] args) {
        Q1 ma = new Q1();
        try
        {
            int input = Integer.parseInt(args[0]);
            System.out.println("Answer for input X = " + input + " is: " + ma.q1(input));
        } 
        catch (NumberFormatException e)
        {
            System.out.println("Your input X = " + args[0] + " is invalid");
        }
    }
    
    private int q1(int x)
    {
        if(x < 1)
        {
            return 0; 
        }
        
        LinkedList<Integer> qu = new LinkedList<>();
        for(int i = 0; i < x; i++)
        {
            qu.offerLast(i);
        }
        qu = playOnce(qu);
        int out = 1;
        while(!check(qu))
        {
            qu = playOnce(qu);
            out++;
        }
        return out;
    }
    
    private LinkedList<Integer> playOnce(LinkedList<Integer> qu)
    {
        LinkedList<Integer> temp = new LinkedList<>();
        while(!qu.isEmpty())
        {
            temp.offerFirst(qu.pop());
            if(!qu.isEmpty())
            {
                qu.offerLast(qu.pop());
            }
        }
        return temp;
    }
    
    private boolean check(LinkedList<Integer> qu)
    {
        int i = 0;
        for(Integer ele : qu)
        {
            if(ele != i)
            {
                return false;
            }
            i++;
        }
        return true;
    }
}