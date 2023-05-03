package abalone;
import javax.print.attribute.standard.Destination;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AbaloneGraph

//Bubble sort from https://www.geeksforgeeks.org/bubble-sort/

//This class creates a graph data structure from linked nodes and represents a game of Abalone
{
    public final int GRAPH_SIZE = 91;
    private Node[] graph = new Node[GRAPH_SIZE];
    private int player1Score;
    private int player2Score;

    //Traditional starting positions
    public final int[] P1_CLASSIC = {64, 65, 66, 71, 72, 73, 74, 75, 76, 79, 80, 81, 82, 83};
    public final int[] P2_CLASSIC = {7, 8, 9, 10, 11, 14, 15, 16, 17, 18, 19, 24, 25, 26};


    //Default constructor creates the classic board
    public AbaloneGraph()
    {
        this.createGraph(P1_CLASSIC, P2_CLASSIC);
        this.setSiblings();
        this.setLevels();
    }

    //Constructor creates a board with pieces for each player given the passed directions
    public AbaloneGraph(int[] p1Spaces, int[] p2Spaces)
    {
        this.createGraph(p1Spaces, p2Spaces);
        this.setSiblings();
        this.setLevels();
    }

    //Creates all nodes in the abalone board graph
    private void createGraph(int[] p1Spaces, int[] p2Spaces)
    {
        Arrays.sort(p1Spaces);
        Arrays.sort(p2Spaces);
        //TODO prevent duplicate positions in both arrays

        boolean incrementing = true;
        int rowSize = 6;
        int numRows = 11;
        int currPosition = 0;
        int currP1Position = 0;
        int currP2Position = 0;

        for (int i = 0; i < numRows; ++i)
        {
            for (int j = 0; j < rowSize; ++j)
            {
                if (rowSize == 11)
                    incrementing = false;

                if (incrementing)
                {
                    //Starting edge of board or an edge along the side
                    if (rowSize==6 || j==0 || j==rowSize-1)
                        graph[currPosition] = new Node(0, currPosition, true);
                    //Any spaces where the passed p1Space position equals the currPosition should have a p1 piece
                    else if (p1Spaces[currP1Position] == currPosition)
                    {
                        graph[currPosition] = new Node(1, currPosition, false);
                        if (currP1Position < p1Spaces.length-1)
                            ++currP1Position;
                    }
                    //Any spaces where the passed p2Space position equals the currPosition should have a p2 piece
                    else if (p2Spaces[currP2Position] == currPosition)
                    {
                        graph[currPosition] = new Node(2, currPosition, false);
                        if (currP2Position < p2Spaces.length-1)
                            ++currP2Position;
                    }
                    //Any other empty board space that is not an edge
                    else
                        graph[currPosition] = new Node(0, currPosition, false);
                }
                else //I.e. Player1/Bottom side of the board
                {
                    //Starting edge of board or an edge along the side
                    if (rowSize==6 || j==0 || j==rowSize-1)
                        graph[currPosition] = new Node(0, currPosition, true);
                    //Any spaces where the passed p1Space position equals the currPosition should have a p1 piece
                    else if (p1Spaces[currP1Position] == currPosition)
                    {
                        graph[currPosition] = new Node(1, currPosition, false);
                        if (currP1Position < p1Spaces.length-1)
                            ++currP1Position;
                    }
                    //Any spaces where the passed p2Space position equals the currPosition should have a p2 piece
                    else if (p2Spaces[currP2Position] == currPosition)
                    {
                        graph[currPosition] = new Node(2, currPosition, false);
                        if (currP2Position < p2Spaces.length-1)
                            ++currP2Position;
                    }
                    //Any other empty board space that is not an edge
                    else
                        graph[currPosition] = new Node(0, currPosition, false);
                }
                ++currPosition;
            }
            if (incrementing)
                ++rowSize;
            else
                --rowSize;
        }
    }

    //Sets all siblings for each node
    private void setSiblings()
    {
        boolean incrementing = true;
        int rowSize = 6;
        int numRows = 11;
        int currPosition = 0;

        for (int i = 0; i < numRows; ++i)
        {
            for (int j = 0; j < rowSize; ++j)
            {
                if (rowSize == 11)
                    incrementing = false;

                if (incrementing)
                {
                    //Visually lower sibling nodes are always set when rowSize is increasing
                    graph[currPosition].setSibling(graph[currPosition+rowSize], 7);
                    graph[currPosition].setSibling(graph[currPosition+rowSize+1], 5);

                    //The left-most node of the starting row only has its sibling on the right
                    // side in addition to sibling 7 and 5
                    if (rowSize==6 && j==0)
                    {
                        graph[currPosition].setSibling(graph[currPosition+1], 3);
                    }
                    //The right-most node of the starting row only has its sibling on the left
                    // side in addition to sibling 7 and 5
                    else if (rowSize==6 && j==rowSize-1)
                    {
                        graph[currPosition].setSibling(graph[currPosition+1], 9);
                    }
                    //Siblings 9 and 11 are null as the node is on the left edge of the graph
                    else if (j==0)
                    {
                        graph[currPosition].setSibling(graph[currPosition-rowSize+1], 1);
                        graph[currPosition].setSibling(graph[currPosition+1], 3);
                    }
                    //Siblings 1 and 3 are null as the node is on the right edge of the graph
                    else if (j==rowSize-1)
                    {
                        graph[currPosition].setSibling(graph[currPosition-1], 9);
                        graph[currPosition].setSibling(graph[currPosition+1], 11);
                    }
                    else if (rowSize!=6)
                    {
                        graph[currPosition].setSibling(graph[currPosition-rowSize+1], 1);
                        graph[currPosition].setSibling(graph[currPosition+1], 3);
                        graph[currPosition].setSibling(graph[currPosition-1], 9);
                        graph[currPosition].setSibling(graph[currPosition-rowSize], 11);
                    }
                }
                else if (rowSize==11)
                {
                    //Node on left edge of board
                    if (j==0)
                    {
                        graph[currPosition].setSibling(graph[currPosition-rowSize+1], 1);
                        graph[currPosition].setSibling(graph[currPosition+1], 3);
                        graph[currPosition].setSibling(graph[currPosition+rowSize+1], 5);
                    }
                    //Node on right edge of board
                    else if (j==rowSize-1)
                    {
                        graph[currPosition].setSibling(graph[currPosition+rowSize], 7);
                        graph[currPosition].setSibling(graph[currPosition-1], 9);
                        graph[currPosition].setSibling(graph[currPosition-rowSize], 11);
                    }
                    //Non-edge node
                    else
                    {
                        graph[currPosition].setSibling(graph[currPosition-rowSize+1], 1);
                        graph[currPosition].setSibling(graph[currPosition+1], 3);
                        graph[currPosition].setSibling(graph[currPosition+rowSize], 5);
                        graph[currPosition].setSibling(graph[currPosition+rowSize-1], 7);
                        graph[currPosition].setSibling(graph[currPosition-1], 9);
                        graph[currPosition].setSibling(graph[currPosition-rowSize], 11);
                    }
                }
                else //I.e. Player1/Bottom side of the board
                {
                    //Sibling nodes visually above are always set when rowSize is decreasing
                    graph[currPosition].setSibling(graph[currPosition-rowSize], 1);
                    graph[currPosition].setSibling(graph[currPosition-rowSize-1], 11);

                    //The left-most node of the starting row only has its sibling on the right
                    // side in addition to siblings 1 and 11
                    if (rowSize==6 && j==0)
                    {
                        graph[currPosition].setSibling(graph[currPosition+1], 3);
                    }
                    //The right-most node of the starting row only has its sibling on the left
                    // side in addition to siblings 1 and 11
                    else if (rowSize==6 && j==rowSize-1)
                    {
                        graph[currPosition].setSibling(graph[currPosition-1], 9);
                    }
                    //Siblings 5 and 7 (i.e. the visually lower nodes) are null
                    else if (rowSize==6)
                    {
                        graph[currPosition].setSibling(graph[currPosition+1], 3);
                        graph[currPosition].setSibling(graph[currPosition-1], 9);
                    }
                    //Siblings 7 and 9 are null as the node is on the left edge of the graph
                    else if (j==0)
                    {
                        graph[currPosition].setSibling(graph[currPosition+1], 3);
                        graph[currPosition].setSibling(graph[currPosition+rowSize], 5);
                    }
                    //Siblings 3 and 5 are null as the node is on the right edge of the graph
                    else if (j==rowSize-1)
                    {
                        graph[currPosition].setSibling(graph[currPosition+rowSize-1], 7);
                        graph[currPosition].setSibling(graph[currPosition-1], 9);
                    }
                    //All siblings are set as the node is not along an edge
                    else if (rowSize!=6)
                    {
                        graph[currPosition].setSibling(graph[currPosition+1], 3);
                        graph[currPosition].setSibling(graph[currPosition+rowSize], 5);
                        graph[currPosition].setSibling(graph[currPosition+rowSize-1], 7);
                        graph[currPosition].setSibling(graph[currPosition-1], 9);
                    }
                }
                ++currPosition;
            }
            if (incrementing)
                ++rowSize;
            else
                --rowSize;
        }

    }

    //Returns the node from a given position in the graph array
    public Node getNode(int position)
    {
        return graph[position];
    }

    public Ellipse2D getPiece(int position)
    {
        return graph[position].getPiece();
    }

    public void setPiece(int position, Ellipse2D.Double piece)
    {
        graph[position].setPiece(piece);
    }

    //Traverses a one piece wide path updating nodes until all nodes in the path are updated
    //Assumes the path between the first and last node is valid
    public void makeInlineMove(Node first, Node last, int direction)
    {
        boolean updated = false;
        Node currNode = last;
        int prevDirection = (direction+6) % 12; //used to reference previous nodes
        while (!updated)
        {
            //Set currNode to the previous node's color if it is not the first node
            if (currNode != first) 
                currNode.setColor(currNode.getSibling(prevDirection).getColor());
            //Set the current node's color to the board's color if it is the first node in the path
            else
                first.setColor(0);
                
            if (currNode == first)
                updated = true;
            //if (currNode.getSibling(prevDirection) != null)
                currNode = currNode.getSibling(prevDirection);
        }
        //Assign point if scored and reset edge color
        if (last.isEdge())
        {
            if (last.getColor() == 1)
                ++player2Score;
            else
                ++player1Score;
            last.setColor(0);
        }
    }

    //Takes an array of nodes to be moved in a given direction
    //Array must be size 3
    //Pad non used indexes with null values
    //This method assumes move is valid
    public void makeBroadsideMove(Node[] nodes, int direction)
    {
        int color = nodes[0].getColor();
        for(int i=0; i<nodes.length; i++)
        {
            nodes[i].getSibling(direction).setColor(color);
            nodes[i].setColor(0);
        }

        for(int i=0; i<nodes.length; i++)
        {
            if(nodes[i].getSibling(direction).isEdge())
            {
                //Not sure why this seems to be backwards but works?
                if(nodes[i].getColor()==1)
                    ++player1Score;
                else 
                    ++player2Score;
                nodes[i].getSibling(direction).setColor(0);
            }
        }
    }

    //Determines if a broadside move can be made for a given array of nodes
    //The node array should be three nodes or less
    public boolean canMoveBroadside(Node[] nodes, int direction, Node clickedDirection)
    {
        boolean canMove = true;
        int color = nodes[0].getColor();
        //Make sure not more than 3 pieces being moved
        if(nodes.length>3)
            canMove=false;
        //Check if array has consistent colors
        for(int i=0; i<nodes.length; i++)
        {
            if(nodes[i].getColor()==0 || nodes[i].getColor() != color)
                canMove = false;
        }
        //Check if neighbors occupied or is edge
        for(int i=0; i<nodes.length; i++)
        {
            if(nodes[i].getSibling(direction).getColor()!=0 || nodes[i].getSibling(direction).isEdge())
            {
                canMove=false;
            }
        }
        //Check if destination is not in the middle
        if (clickedDirection != null)
        {
            int destNeighbors = 0;
            for (int i = 0; i < nodes.length; i++) {
                if (nodes[i].hasNeighbor(clickedDirection))
                    destNeighbors++;
            }
            if (destNeighbors > 1)
                canMove = false;
        }
        //Check to see if nodes are in line 
        if(nodes.length>2)
        {
            int numNeighbors=0;
            for(int i=0; i<nodes.length-1; i++)
            {
                if(nodes[i].hasNeighbor(nodes[i+1]))
                    numNeighbors++;
                if(nodes[i].hasNeighbor(nodes[nodes.length-1]))
                    numNeighbors++;
            }

            if(numNeighbors<2)
                canMove=false;
        }
        else
        {
            int numNeighbors=0;
            for(int i=0; i<nodes.length-1; i++)
            {
                if(nodes[i].hasNeighbor(nodes[i+1]))
                    numNeighbors++;
            }

            if(numNeighbors<1)
                canMove=false;
        }   
        
        return canMove;
    }


    public void printNodes()
    {
        for (Node n : graph)
        {
            System.out.println(n);
        }
    }

    public void printSiblings(int nodePosition)
    {
        System.out.println("Node: " + nodePosition);
        for (int i = 1; i <= 11; i+=2)
        {
            System.out.println("Sibling" + i + " " + graph[nodePosition].getSibling(i));
        }
        System.out.println();
    }

    //Returns the direction to traverse between two sibling nodes
    //Returns -1 if the nodes are not siblings
    public int getDirection(Node n1, Node n2)
    {
        int sibNum = -1;
        for(int i=1; i<12; i+=2)
        {
            if(n1.getSibling(i)!= null && n1.getSibling(i).equals(n2))
            {
                sibNum = i;
            }
        } 
        return sibNum;
    }

    public int getBroadsideDirection(Node[] nodes, Node directionNode)
    {
        int direction = -1;
        for (int i = 0; i < nodes.length; ++i)
        {
            if (getDirection(nodes[i], directionNode) != -1)
            {
                return getDirection(nodes[i], directionNode);
            }
        }
        return direction;
    }

    //Returns the destination of the last node in an in line move if it is valid 
    // Returns null if the move is not valid 
    public Node destination(Node first, Node second, int direction)
    {
        //System.out.println("destination:" + first.getID() + ", " + second.getID() + "   direction:" + direction);
        Node next = second;
        int playerColor = -1;
        if (first.getColor() != 0)
            playerColor = first.getColor();

        int opponentColor = -1;
        if (playerColor == 1)
            opponentColor = 2;
        else if (playerColor == 2)
            opponentColor = 1;

        int numPlayers = 1;
        int numOpponents = 0;

        //Returns null if the two nodes aren't siblings or not a player's piece
        if(!first.hasNeighbor(second) || playerColor == -1)
            return null;
        //Iterates through spaces held by players color until an opposite color, empty space, or edge is found
        //Counts number of pieces in a row of the color whose turn it is
        while(next!=null && next.getColor()==playerColor)
        {
            //System.out.print("next Color: " + next.getColor() + " playerColor: " + playerColor);
            next = next.getSibling(direction);
            numPlayers++;
            //System.out.println(" numPlayers:" + numPlayers);
        }
        //If player's pieces exceed 3 return null or pushing self off edge
        if(numPlayers > 3 || next.isEdge())
            return null;

        //If empty space is reached, return empty space node
        //Number of pieces in a row must be less than 4
        if(next.getColor()==0 && numPlayers<=3)
        {
            return next;
        }
        //Counts number of opponents pieces
        while(next.getColor()==opponentColor)
        {
            next = next.getSibling(direction);
            numOpponents++;
            //System.out.println("numOpponents:" + numPlayers);
        }
        //If number of opponents pieces is less than players pieces return 
        // the empty space after last opponents node in the row
        if(numPlayers>numOpponents && numPlayers<=3 && next.getColor()==0)
        {
            next = first.getSibling(direction);
            int count=0;
            while(count < numPlayers+numOpponents-1)
            {
                next = next.getSibling(direction);
                count++;
            }
            return next;
        }
        else 
            return null;
    }

    //Returns the node's postion that can push the passed node off the edge by its opponent
    // and the direction the pushing node would push in via an int array
    // Ex: int[] values = {pushingNodePosition, direction};
    // The passed node must be an edge piece
    // Will return an array with -1s if not possible
    public int[] inDangerFrom(Node node)
    {
        int[] values = {-1, -1};
        Node pushingNode = null;
        Node next;
        int numPlayers = 1;
        int numOpponents = 0;
        //Find player
        int playerColor = node.getColor();
        int opponentColor = 1;
        if (playerColor == 1)
            opponentColor = 2;
        
        //Check each of the node's siblings opposite the edge sibling's direction to see if it can be pushed of that edge
        int[] edgeDirections = {-1, -1, -1};
        //find edge directions
        int currPostion = 0;
        for (int i = 1; i < 12; i += 2)
        {
            if (node.getSibling(i).isEdge())
            {
                edgeDirections[currPostion] = i;
                ++currPostion;
            }
        }

        //Check the opposite direction of each edge direction to see if there are a greater number of opponent's pieces
        for (int i = 0; i<edgeDirections.length && edgeDirections[i]!=-1; ++i)
        {
            //Reset for new direction
            numPlayers = 1;
            numOpponents = 0;
            pushingNode = null;

            int direction = (edgeDirections[i] + 6) % 12;
            next = node.getSibling(direction);

            //Iterates through spaces held by players color until a opposite color, empty space, or edge is found
            //Counts number of pieces in a row of the color whose turn it is
            while(next!=null && next.getColor()==playerColor)
            {
                next = next.getSibling(direction);
                numPlayers++;
                //System.out.println("numPlayers:" + numPlayers);
            }
            //If player's pieces exceed 3 for all directions return empty values
            if(numPlayers > 3 && i == 3 || edgeDirections[i] == -1)
            {
                //System.out.println("Returned at exceed 3");
                return values;
            }

            //If empty space is reached for all directions, return empty values
            if(next.getColor()==0  && i == 3 || edgeDirections[i] == -1)
            {
                //System.out.println("Returned at exceed empty ends");
                return values;
            }
            //Counts number of opponents pieces
            while(next.getColor()==opponentColor)
            {
                numOpponents++;
                // Assigns the pushing node that could push the greatest amount
                if (numOpponents == 2 || numOpponents == 3)
                    pushingNode = next;

                next = next.getSibling(direction);
            }
            //If number of opponents pieces is greater than players pieces return the opponent's piece that could push in the direction
            if(numPlayers<numOpponents && pushingNode != null && numPlayers < 3)
            {
                //System.out.println("Returned at push");
                values[0] = pushingNode.getID();
                values[1] = edgeDirections[i];
                return values;
            }
        }
        //Catch all for unexpected cases
        return values;
    }

    //Returns true if a node can push an opponent in the passed direction
    public boolean canPush(Node node, int direction)
    { 
        Node next = node.getSibling(direction);
        int playerColor = node.getColor();
        int opponentColor = 1;
        if (playerColor == 1)
            opponentColor = 2;
        int numPlayers = 1;
        int numOpponents = 0;

        //Iterates through spaces held by players color until a opposite color, empty space, or edge is found
        //Counts number of pieces in a row of the color whose turn it is
        while(next!=null && next.getColor()==playerColor)
        {
            next = next.getSibling(direction);
            numPlayers++;
            //System.out.println("numPlayers:" + numPlayers);
        }
        //If player's pieces exceed 3 return false because it cannot push
        if(numPlayers > 3 || numPlayers < 2)
            return false;

        //If empty space is reached, return false because it is not pushing an opponent
        if(next.getColor()==0)
            return false;
            
        //Counts number of opponents pieces
        while(next.getColor()==opponentColor)
        {
            numOpponents++;
            next = next.getSibling(direction);
        }
        //If number of opponents pieces is less than players pieces return true
        if(numPlayers>numOpponents && numPlayers<=3 && next.getColor()==0)
        {
            return true;
        }
        else 
            return false;
    }

    private void setLevels()
    {
        for (int i = 0; i < graph.length/2; ++i)
        {
            if (graph[i].isEdge())
                graph[i].setLevel(0);
            else if(graph[i].bordersEdge())
                graph[i].setLevel(1);
            else
                graph[i].setLevel(graph[i].minSiblingLevel() + 1);
        }
        //Call the reverse because the first for loop messes up around the middle of the board
        for (int i = graph.length-1; i >= graph.length/2; --i)
        {
            if (graph[i].isEdge())
                graph[i].setLevel(0);
            else if(graph[i].bordersEdge())
                graph[i].setLevel(1);
            else
                graph[i].setLevel(graph[i].minSiblingLevel() + 1);
        }
    }

    public AbaloneGraph clone()
    {
        ArrayList<Integer> p1Spaces = new ArrayList<>();
        ArrayList<Integer> p2Spaces = new ArrayList<>();
        for (int i = 0; i < GRAPH_SIZE; ++i)
        {
            if(graph[i].getColor() == 1)
                p1Spaces.add(i);
            else if(graph[i].getColor() == 2)
                p2Spaces.add(i);
        }
        p1Spaces.trimToSize();
        p2Spaces.trimToSize();
        int[] p1Ints = new int[p1Spaces.size()];
        for (int i = 0; i < p1Ints.length; ++i)
        {
            p1Ints[i] = p1Spaces.get(i);
        }
        int[] p2Ints = new int[p2Spaces.size()];
        for (int i = 0; i < p2Ints.length; ++i)
        {
            p2Ints[i] = p2Spaces.get(i);
        }
        return new AbaloneGraph(p1Ints, p2Ints);
    }

    //Returns a 2D array of ints representing the players' positions on the board, -1 means the player doesn't have a piece
    //positions[0][n] return the positions of player 1
    //positions[1][n] return the positions of player 2
    public int[][] getPlayerPositions()
    {
        ArrayList<Integer> p1Spaces = new ArrayList<>();
        ArrayList<Integer> p2Spaces = new ArrayList<>();
        for (int i = 0; i < GRAPH_SIZE; ++i)
        {
            if(graph[i].getColor() == 1)
                p1Spaces.add(i);
            else if(graph[i].getColor() == 2)
                p2Spaces.add(i);
        }
        p1Spaces.trimToSize();
        p2Spaces.trimToSize();
        int[] p1Ints = new int[p1Spaces.size()];
        for (int i = 0; i < p1Ints.length; ++i)
        {
            p1Ints[i] = p1Spaces.get(i);
        }
        int[] p2Ints = new int[p2Spaces.size()];
        for (int i = 0; i < p2Ints.length; ++i)
        {
            p2Ints[i] = p2Spaces.get(i);
        }
        int size = 14;
        if (p1Ints.length < size)
            size = p1Ints.length;
        if (p2Ints.length > size)
            size = p2Ints.length;
        int[][] positions = new int[2][size];
        for (int i = 0; i < size; ++i)
        {
            if (i >= p1Ints.length)
                positions[0][i] = -1;
            else
                positions[0][i] = p1Ints[i];
            if (i >= p2Ints.length)
                positions[1][i] = -1;
            else
                positions[1][i] = p2Ints[i];
        }
        return positions;
    }

    //Returns an array of the possible destinations that could be clicked to make a move given the passed node(s)
    //TODO
    public Node[] findPossibleClicks(Node[] selected)
    {
        ArrayList<Node> possibles = new ArrayList<>();

        //If one node is selected show options for an inline move
        if (selected.length == 1)
        {
            Node start = selected[0];
            Node testNode = null;
            //Iterate through the selected, adding a sibling if the move is possible
            for (int direction = 1; direction < 12; direction += 2)
            {
                testNode = start.getSibling(direction);
                if (destination(start, testNode, direction) != null)
                    possibles.add(testNode);
            }
        }
        //A broadside move is attempting to be made
        else
        {
            for (int direction = 1; direction < 12; direction +=2)
            {
                if (canMoveBroadside(selected, direction, null))
                {
                    int[] numSiblings = new int[selected.length];
                    //Find the nodes that are on the end of the line
                    // if the node is only a neighbor with one of the other nodes in the array, it is on the end
                    for (int i = 0; i < selected.length; ++i)
                    {
                        int numSibs = 0;
                        for (int j = 0; j < selected.length; ++j)
                        {
                            if (selected[i].hasNeighbor(selected[j]))
                                ++numSibs;
                        }
                        numSiblings[i] = numSibs;
                    }
                    for (int i = 0; i < selected.length; ++i)
                    {
                        //Then get the node adjacent to the end pieces in the current direction
                        if (numSiblings[i] == 1)
                        {
                            possibles.add(selected[i].getSibling(direction));
                        }
                    }
                }
            }
            //Now remove any possible locations that border more than one piece
            ArrayList<Node> removes = new ArrayList<>();
            for (int i = 0; i < possibles.size(); ++i)
            {
                Node currNode = possibles.get(i);
                int selectedNeighbors = 0;
                for (int j = 0; j < selected.length; j++) {
                    if (currNode.hasNeighbor(selected[j]))
                        selectedNeighbors++;
                }
                if (selectedNeighbors > 1)
                    removes.add(currNode);
            }
            for (int i = 0; i < removes.size(); ++i)
            {
                possibles.remove(removes.get(i));
            }
        }

        possibles.trimToSize();
        Node[] nodes = possibles.toArray(new Node[0]);
        return nodes;
    }

    public void resetGraph()
    {
        this.graph = new Node[GRAPH_SIZE];
        this.createGraph(P1_CLASSIC, P2_CLASSIC);
        player1Score = 0;
        player2Score = 0;
    }

    public int getPlayer1Score()
    {
        return player1Score;
    }

    public int getPlayer2Score()
    {
        return player2Score;
    }
}
