public class AbaloneGraph
{
    private Node[] graph = new Node[91];
    //Create array or arraylist of 91 nodes for the entire board
    // 61 of the nodes will be playable nodes, the other 30 are border nodes

    //iterate through list of nodes, then assign the sibling nodes to each one

    public static void main(String[] args)
    {
        AbaloneGraph graph = new AbaloneGraph();
        for (int i = 0; i < 30; ++i)
            graph.printSiblings(i);
    }

    public AbaloneGraph()
    {
        createGraph();
        this.setSiblings();
        this.printNodes();
    }

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

    private void setSiblings()
    {
        boolean incrementing = true;
        int rowSize = 6;
        int numRows = 11;
        int currPosition = 0;

        //TODO null siblings need not be set
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
                else //I.e. Player1/Bottom side of the board
                {

                }

                System.out.println(graph[currPosition]);
                ++currPosition;
            }
            if (incrementing)
                ++rowSize;
            else
                --rowSize;
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

}
