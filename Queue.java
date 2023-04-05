//Cole Blakeman 
//Linked Queue data strcture to manage clicked nodes for inline and broadside moves

public class Queue {
    private Node front;
    private Node back;
    private int count;

    public Queue()
    {
        front = null;
        back = null;
        count = 0;
    }
    //Adds new Node to back of Queue
    public void push(Node n)
    {
        if(count!=0)
            back.setNext(n);
        else
            front = n;
        back = n;
        count ++;

    }
    //Removes front node from the Queue and reduces count
    public void pop()
    {
        if(count == 0)
        {
            throw new RuntimeException("Cannot pop from an empty queue");
        }

        if(count == 1)
        {
            back = null;
        }
        
        front = front.getNext();
        count--;
    }
    //Gets the front Node of Queue
    public Node peek()
    {
        if(count==0)
        {
            throw new RuntimeException("You cant peek at an empty queue.");
        }
        return front;
    }
    //Gets count of the Queue
    public int getCount()
    {
        return count;
    }
    //Returns if the Queue is empty or not
    public boolean isEmpty()
    {
        return count==0;
    }

    public String toString()
    {
        String output = "[";
        Node current = front;
        while(current!= null)
        {
            output += current.getID() + ",";
            current = current.getNext();
        }

        return output + "]";
    }
}