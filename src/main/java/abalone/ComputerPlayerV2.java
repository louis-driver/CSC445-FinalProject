package abalone;
//A class to play against a human 

//Player takes a AbaloneGraph Object for construction.
//For each turn, the updated graph should be passed to the Player to keep track of its nodes and positions
//Calling the get move method provides the nodes for the next computer move

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class ComputerPlayerV2  extends ComputerPlayer
{
    public ComputerPlayerV2(AbaloneGraph g, int color)
    {
        super(g, color);
    }

    //Returns the nodes and direction for the next move
    //The system to determine moves is to prioritise edge pieces and move them from the edge
    //The moves are ranked as follows:
    // 1. If its on an edge in danger and can escape danger and the edge 
    // 2. If its on an edge in danger and can escape danger
    // 3. If it can capture a white piece
    // 4. If it is an edge and can push a white node 
    // 5. If it is on an edge and can escape edge 
    // 6. If it can push a white
    // 7. Some other possible move 
    //The int[] returned is ordered as follows: [first node ID from graph, destination piece ID from graph, direction]
    @Override
    public int[] getMove()
    {
        updatePlayers(graph);
        int[] move = dangerEscapeBoth();
        if(move[0]!=-1){
            System.out.println("DangerEscapeBoth Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = dangerEscapeDanger();
        if(move[0]!=-1){
            System.out.println("DangerEscapeDanger Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = captureOpponent();
        if(move[0]!=-1){
            System.out.println("CaptureOpponent Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = edgePush();
        if(move[0]!=-1){
            System.out.println("EdgePush Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = moveToCenter();
        if(move[0]!=-1){
            System.out.println("ToCenter Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = edgeEscape();
        if(move[0]!=-1){
            System.out.println("EscapeEdge Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = pushWhite();
        if(move[0]!=-1){
            System.out.println("PushWhite Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = uniteLonelyFriends();
        if(move[0]!=-1){
            System.out.println("UniteLonely Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = uniteFriends();
        if(move[0]!=-1){
            System.out.println("UniteFriends Move:" + Arrays.toString(move));
            return move;
        }
        move = otherMove();
        if(move[0]!=-1){
            System.out.println("Other Move:" + Arrays.toString(move));
            return move;
        }
        int[] error = {-1, -1, -1};
        return error;
    }

    //Returns a move than will maximize the sum of the computer's nodes' levels
    // Essentially moving as many pieces as possible to the center of the board
    protected int[] moveToCenter()
    {
        int initialSum = ComputerPlayerV2.getLevelSum(graph, computerColor);
        //System.out.println("ComputerSumInitial: " + initialSum);
        int bestSum = initialSum;
        int[] bestMove = {-1, -1, -1};
        AbaloneGraph testGraph = graph.clone();

        for(int i=0; i<computerNodes.size(); i++)
        {
            int currPieceID = computerNodes.get(i).getID();
            //System.out.println("\n Testing node: " + currPieceID);
            //System.out.println("Best Sum so far: " + bestSum);
            //System.out.println("Best Move so far: " + Arrays.toString(bestMove));
            Node currPiece = testGraph.getNode(currPieceID);
            Node piece2 = null;

            for(int j=1; j<12; j+=2)
            {
                piece2 = currPiece.getSibling(j);
                if (!piece2.isEdge() && !piece2.bordersEdge())
                {
                    Node dest = testGraph.destination(currPiece, piece2, j);
                    //System.out.println("TestDestination " + j + ": " + dest);
                    if (dest != null && !dest.bordersEdge())
                    {
                        int[] testMove = {currPieceID, dest.getID(), j};
                        //System.out.println("Testing Move: " + Arrays.toString(testMove));
                        testGraph.makeInlineMove(currPiece, dest, j);
                        int testSum = ComputerPlayerV2.getLevelSum(testGraph, computerColor);
                        //System.out.println("Test Sum: " + testSum);
                        //Reset graph and piece after testing move
                        testGraph = graph.clone();
                        currPiece = testGraph.getNode(currPieceID);
                        if (testSum > bestSum)
                        {
                            bestSum = testSum;
                            bestMove = testMove;
                            //System.out.println("Best Move so far: " + Arrays.toString(bestMove));
                            //System.out.println("Best Sum so far: " + bestSum);
                        }
                    }
                }
            }
        }

        //System.out.println("ComputerSumBest: " + bestSum);
        if (bestSum > initialSum)
        {
            int[] move = bestMove;
            //System.out.println("moveToCenter: " + Arrays.toString(move));
            return move;
        }
        else
        {
            int[] move = {-1, -1, -1};
            return move;
        }

    }

    //Returns a move that will push pieces to join other pieces of its own color
    // Prioritizes pieces that neighbor the least amount of friendly pieces
    protected int[] uniteLonelyFriends()
    {
        //System.out.println(" Unite Lonely friends");
        PriorityQueue<int[]> sortedLonelies = new PriorityQueue<>(new DescIntArrayComparator());
        //Find add the number of friends a piece has and its position to a minHeap sorted on least number of friends
        for (int i = 0; i < computerNodes.size(); ++i)
        {
            int[] numFriends =  {computerNodes.get(i).getNumFriends(), computerNodes.get(i).getID()};
            //System.out.println("i" + i + ": " + Arrays.toString(numFriends));
            sortedLonelies.add(numFriends);
        }

        /*System.out.print("Sorted By Loneliness:");
        while (sortedLonelies.size() != 0)
        {
            System.out.print(" " + Arrays.toString(sortedLonelies.poll()));
        }
        System.out.println(); */


        boolean friendsFound = false;
        Node toMove = null;
        int direction = -1;
        Node destination = null;
        while (sortedLonelies.size() !=0 && !friendsFound)
        {
            //System.out.println("Size: " + sortedLonelies.size());
            int[] polled = sortedLonelies.poll();
            int currID = polled[1];
            //System.out.println("CurrID:" + currID);
            Node piece1 = graph.getNode(currID);
            for(int j=1; j<12; j+=2)
            {
                //Sets the piece to search in order of fewest neighbors
                Node piece2 = piece1.getSibling(j);
                //System.out.println("Checking Node: " + piece1.getID() + " & " + piece2.getID());
                Node dest = graph.destination(piece1, piece2, j);
                //System.out.println("Testing Destination: " + dest);
                if (dest != null && !dest.isEdge() && !dest.bordersEdge())
                {
                    for (int k = 1; k < 12; k += 2)
                    {
                        if ((k == (j + 2) % 12 || k == (j - 2) % 12 || k == j) && dest.getSibling(k).getColor() == piece1.getColor())
                        {
                            //System.out.println("k: " + k);
                            toMove = piece1;
                            direction = j;
                            destination = dest;
                            friendsFound = true;
                            //System.out.println("toMove: " + toMove + " direction: " + direction + " destination: " + destination);
                        }
                    }
                }
            }
        }
        if(toMove!=null && direction!=-1 && destination!=null)
        {
            int[] move = {toMove.getID(), destination.getID(), direction};
            //System.out.println(" Unite Lonely friends: " + Arrays.toString(move));
            return move;
        }
        else
        {
            int[] move = {-1, -1, -1};
            return move;
        }
    }

    //Sums all of a player's pieces distance from the edge of the board
    protected static int getLevelSum(AbaloneGraph g, int player)
    {
        int levelSum = 0;
        for (int i = 0; i < g.GRAPH_SIZE; ++i)
        {
            if (g.getNode(i).getColor() == player)
                levelSum += g.getNode(i).getLevel();
        }
        return levelSum;
    }

    //Used to sort list of int[] by comparing their values at 0
    protected static class DescIntArrayComparator implements Comparator<int[]>
    {
        @Override
        public int compare(int[] arr1, int[] arr2)
        {
            return arr1[0] > arr2[0] ? 1 : -1;
        }
    }
}
