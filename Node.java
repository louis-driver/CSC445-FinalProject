
import java.awt.geom.Ellipse2D;

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
    private Ellipse2D.Double piece;

    //Creates a node that has a value for:
    // color: a state to determine whose piece is on the node; 0=board, 1=white/player1, 2=black/player2
    // ID: the position of the node on the Abalone board
    // isEdge: if the node is valid place for a playing piece
    public Node(int color, int ID, boolean edge)
    {
        this.color = color;
        positionID = ID;
        isEdge = edge; 
    }

    //Sets the sibling node in a given direction based on 
    // a clock face. 
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

    //Defines which color the node should be in a
    // GUI. 0 for background, 1 for Player1's piece color,
    // and 2 for Player2's piece color.
    public void setColor(int color)
    {
        this.color = color;
    }

    //Defines the piece size and location within a graphics window
    public void setPiece(Ellipse2D.Double piece)
    {
        this.piece = piece;
    }

    //Returns the sibling node for a given clock
    // orientation
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
    else if(sibNum == 11)
        return sibling11;
    else
        return null;
    }

    //Returns an int representation of a node's color
    // 0 for background, 1 for Player1's piece color,
    // and 2 for Player2's piece color.
    public int getColor()
    {
        return color;
    }
    
    //Returns the positionID of the node
    public int getID()
    {
        return positionID;
    }

    //Returns the Ellipse2D representation of the
    // node for graphical display
    public Ellipse2D getPiece()
    {
        return this.piece;
    }

    //Specifies if the node is a playable location (false)
    // or one signifying a point may be scored (true) 
    // when a piece reaches this node on the Abalone board.
    public boolean isEdge()
    {
        return isEdge;
    }

    //Returns boolean representing if that neighbor exists
    public boolean hasNeighbor(Node n)
    {
        if(sibling1.equals(n))
            return true;
        else if(sibling3.equals(n))
            return true;
        else if(sibling5.equals(n))
            return true;
        else if(sibling7.equals(n))
            return true;
        else if(sibling9.equals(n))
            return true;
        else if(sibling11.equals(n))
            return true;
        else 
            return false;
        
    }

    public String toString()
    {
        String result = "Position: " + getID() + " Color: " + getColor() + " IsEdge: " + isEdge();
        return result;
    }
}


