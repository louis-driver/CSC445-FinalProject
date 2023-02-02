public class Node
{

    private int color;
    private int positionID;
    private Node sibling1;
    private Node sibling3;
    private Node sibling5;
    private Node sibling7;
    private Node sibling9;
    private Node sibling11;
    private boolean isEdge;

    public Node(int color, int ID, boolean edge)
    {
        this.color = color;
        positionID = ID;
        isEdge = edge; 
    }

    public void setSibling(Node Node, int sibNum)
    {
        if(sibNum == 1)
            sibling1 = Node;
        else if(sibNum == 3)
            sibling3 = Node;
        else if(sibNum == 5)
            sibling5 = Node;
        else if(sibNum == 7)
            sibling7 = Node;
        else if(sibNum == 9)
            sibling9 = Node;
        else if(sibNum == 11)
            sibling11 = Node;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public Node getSibling(int sibNum)
    {
    if(sibNum == 1)
        return sibling1;
    else if(sibNum == 3)
        return sibling3;
    else if(sibNum == 5)
        return sibling5;
    else if(sibNum == 7)
        return sibling7;
    else if(sibNum == 9)
        return sibling9;
    else
        return sibling11;
    }

    public int getColor()
    {
        return color;
    }

    public int getID()
    {
        return positionID;
    }

    public boolean isEdge()
    {
        return isEdge;
    }

}


