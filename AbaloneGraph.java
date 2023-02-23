public class AbaloneGraph
{
    private Node[] graph = new Node[91];

    public static void main(String[] args)
    {
        AbaloneGraph graph = new AbaloneGraph();
        for (int i = 0; i < 91; ++i)
            graph.printSiblings(i);
        System.out.println(graph.getDirection(graph.getNode(0), graph.getNode(1)));
    }

    public AbaloneGraph()
    {
        createGraph();
        this.setSiblings();
    }

    //Creates all nodes in the abalone board graph
    private void createGraph()
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
                    //Starting edge of board or an edge along the side
                    if (rowSize==6 || j==0 || j==rowSize-1)
                        graph[currPosition] = new Node(0, currPosition, true);
                    //Starting row for Player2's pieces
                    else if (rowSize==7 || rowSize==8)
                        graph[currPosition] = new Node(2, currPosition, false);
                    //Extra three pieces in middle of the third row out
                    else if (rowSize==9 && (j==3 || j==4 || j==5))
                        graph[currPosition] = new Node(2, currPosition, false);
                    //Any other empty board space that is not an edge
                    else
                        graph[currPosition] = new Node(0, currPosition, false);
                }
                else //I.e. Player1/Bottom side of the board
                {
                    //Starting edge of board or an edge along the side
                    if (rowSize==6 || j==0 || j==rowSize-1)
                        graph[currPosition] = new Node(0, currPosition, true);
                    //Starting row for Player1's pieces
                    else if (rowSize==7 || rowSize==8)
                        graph[currPosition] = new Node(1, currPosition, false);
                    //Extra three pieces in middle of the third row out
                    else if (rowSize==9 && (j==3 || j==4 || j==5))
                        graph[currPosition] = new Node(1, currPosition, false);
                    //Any other empty board space that is not an edge
                    else
                        graph[currPosition] = new Node(0, currPosition, false);
                }

                //Uncomment to view nodes being created
                //System.out.println(graph[currPosition]);
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
                        graph[currPosition].setSibling(graph[currPosition+1], 11);
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
                        graph[currPosition].setSibling(graph[currPosition+rowSize+1], 5);
                        graph[currPosition].setSibling(graph[currPosition+rowSize], 7);
                        graph[currPosition].setSibling(graph[currPosition-1], 9);
                        graph[currPosition].setSibling(graph[currPosition-rowSize], 11);
                    }
                }
                else //I.e. Player1/Bottom side of the board
                {
                    //Sibling nodes visually above are always set when rowSize is decreasing
                    graph[currPosition].setSibling(graph[currPosition-rowSize], 11);
                    graph[currPosition].setSibling(graph[currPosition-rowSize-1], 1);

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

    //TODO update each node along the path of the moving pieces
    public void makeMove(Node first, Node last, int direction)
    {
        //traverse a path updating nodes until the last node is updated
        boolean updated = false;
        Node currNode = first;
        int prevDirection = (direction+6) % 6; //used to reference previous nodes
        while (!updated)
        {
            //Set currNode to the previous node's color if it is not the first node
            if (currNode != first) 
                currNode.setColor(currNode.getSibling(prevDirection).getColor());
            //Set the current node's color to the board's color if it is the first node in the path
            else
                currNode.setColor(0);
            
            if (currNode == last)
                updated = true;
            if (currNode.getSibling(direction) != null)
                currNode = currNode.getSibling(direction);
        }
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
}
