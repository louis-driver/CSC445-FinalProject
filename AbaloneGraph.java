public class AbaloneGraph
{
    private Node[] graph = new Node[91];
    //Create array or arraylist of 91 nodes for the entire board
    // 61 of the nodes will be playable nodes, the other 30 are border nodes

    //iterate through list of nodes, then assign the sibling nodes to each one

    public static void main(String[] args)
    {
        AbaloneGraph graph = new AbaloneGraph();
    }

    public AbaloneGraph()
    {
        createGraph();
        //this.setSiblings();
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

                System.out.println(graph[currPosition]);
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

        //TODO test sibling nodes to see if they start null so I can only set those with actual siblings
        for (int i = 0; i < numRows; ++i)
        {
            for (int j = 0; j < rowSize; ++j)
            {
                if (rowSize == 11)
                    incrementing = false;

                if (incrementing)
                {
                    graph[currPosition].setSibling(graph[currPosition+rowSize], 7);
                    graph[currPosition].setSibling(graph[currPosition+rowSize+1], 5);

                    if (rowSize==6)
                    {
                        graph[currPosition].setSibling(null, 1);
                        graph[currPosition].setSibling(null, 11);
                    }
                    else if (j==0)
                    {
                        graph[currPosition].setSibling(graph[currPosition-rowSize+1], 1);
                        graph[currPosition].setSibling(graph[currPosition+1], 3);
                        graph[currPosition].setSibling(graph[currPosition+rowSize+1], 5);
                        graph[currPosition].setSibling(graph[currPosition+rowSize], 7);
                        graph[currPosition].setSibling(null, 9);
                        graph[currPosition].setSibling(null, 11);
                    }
                    else if (j==rowSize-1)
                    {
                        graph[currPosition].setSibling(null, 1);
                        graph[currPosition].setSibling(null, 3);
                        graph[currPosition].setSibling(graph[currPosition+rowSize+1], 5);
                        graph[currPosition].setSibling(graph[currPosition+rowSize], 7);
                        graph[currPosition].setSibling(graph[currPosition-1], 9);
                        graph[currPosition].setSibling(graph[currPosition+1], 11);
                    }
                    else if (rowSize!=6)
                    {
                        graph[currPosition].setSibling(graph[currPosition-rowSize+1], 1);
                        graph[currPosition].setSibling(graph[currPosition+1], 3);
                        graph[currPosition].setSibling(graph[currPosition+rowSize+1], 5);
                        graph[currPosition].setSibling(graph[currPosition+rowSize], 7);
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

}
