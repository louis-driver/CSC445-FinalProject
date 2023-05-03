package abalone;

import javax.swing.*;
import java.util.Arrays;

public class ComputerPlayerV3 extends ComputerPlayerV2
{
    protected static int[] startLinesHorizontal = {7, 14, 22, 31, 41, 52, 62, 71, 79};
    protected static int[] startLinesDiagonalDown = {41, 31, 22, 14, 7, 8, 9, 10, 11};
    protected static int[] startLinesDiagonalUp = {41, 52, 62, 71, 79, 80, 81, 82, 83};
    protected static int[][] startTraversals = {startLinesHorizontal, startLinesDiagonalDown, startLinesDiagonalUp};
    protected static int[] traversalDirections = {3, 5, 1};
    protected double[] weights;
    protected final int MAX_LEVEL = 50;
    protected final int MAX_LINE_SCORE = 20;
    protected int[] nonEdgePositions = new int[61];

    public static void main(String[] args)
    {
        int frameWidth = 800;
        int frameHeight = 500;
        AbaloneGraph g = new AbaloneGraph();
        double[] weights = {1.0, 0.0};
        ComputerPlayer cp = new ComputerPlayerV3(g, 2, weights);
        AbalonePanel panel = new AbalonePanel(g, cp);

        JFrame frame = new JFrame();
        frame.setSize(frameWidth, frameHeight);
        frame.setTitle("ComputerGraph");
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public ComputerPlayerV3 (AbaloneGraph g, int color, double[] weights)
    {
        super(g, color);
        if (weights.length == 2)
            this.weights = weights;
        initNonEdges();
    }


    @Override
    public int[] getMove()
    {
        //System.out.println("LineScore: " + getLineScore(graph, computerColor));
        //System.out.println("Level: " + getLevelSum(graph, computerColor));
        updatePlayers(graph);
        int[] move = dangerEscapeBoth();
        if(move[0]!=-1){
            //System.out.println("DangerEscapeBoth Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = dangerEscapeDanger();
        if(move[0]!=-1){
            //System.out.println("DangerEscapeDanger Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = captureOpponent();
        if(move[0]!=-1){
            //System.out.println("CaptureOpponent Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = edgePush();
        if(move[0]!=-1){
            //System.out.println("EdgePush Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = cohesionCenter();
        if(move[0]!=-1){
            //System.out.println("CohesionCenter Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = moveToCenter();
        if(move[0]!=-1){
            //System.out.println("ToCenter Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = edgeEscape();
        if(move[0]!=-1){
            //System.out.println("EscapeEdge Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = pushWhite();
        if(move[0]!=-1){
            //System.out.println("PushWhite Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = uniteLonelyFriends();
        if(move[0]!=-1){
            //System.out.println("UniteLonely Move:" + Arrays.toString(move));
            return move;
        }
        if (!inLoop)
            move = uniteFriends();
        if(move[0]!=-1){
            //System.out.println("UniteFriends Move:" + Arrays.toString(move));
            return move;
        }
        move = otherMove();
        if(move[0]!=-1){
            //System.out.println("Other Move:" + Arrays.toString(move));
            return move;
        }
        int[] error = {-1, -1, -1};
        return error;
    }

    protected int[] weightedLineLevel()
    {
        //TODO Weight the getLevelScore and getLineScore with test values to find best ratio
        int[] move = new int[3];
        double initialSum = weights[0] * ((double) ComputerPlayerV3.getLevelSum(graph, computerColor) / MAX_LEVEL) + weights[1] * ((double) getLineScore(graph, computerColor) / MAX_LINE_SCORE);;
        //System.out.println("ComputerSumInitial: " + initialSum);
        double bestSum = initialSum;
        int[] bestMove = {-1, -1, -1};
        double testSum = 0;
        AbaloneGraph testGraph = graph.clone();

        for(int i=0; i<computerNodes.size(); i++)
        {
            int currPieceID = computerNodes.get(i).getID();
            //System.out.println("\n Testing node: " + currPieceID);
            //System.out.println("Best Sum so far: " + bestSum);
            //System.out.println("Best Move so far: " + Arrays.toString(bestMove));
            Node currPiece = testGraph.getNode(currPieceID);
            Node piece2 = null;
            Node dest = null;
            testSum = 0;

            for(int j=1; j<12; j+=2)
            {
                piece2 = currPiece.getSibling(j);
                if (!piece2.isEdge() && !piece2.bordersEdge())
                {
                    dest = testGraph.destination(currPiece, piece2, j);
                    //System.out.println("TestDestination " + j + ": " + dest);
                    if (dest != null && !dest.bordersEdge())
                    {
                        int[] testMove = {currPieceID, dest.getID(), j};
                        //System.out.println("Testing Move: " + Arrays.toString(testMove));
                        testGraph.makeInlineMove(currPiece, dest, j);
                        testSum = weights[0] * ((double) ComputerPlayerV3.getLevelSum(testGraph, computerColor) / MAX_LEVEL) + weights[1] * ((double) getLineScore(testGraph, computerColor) / MAX_LINE_SCORE);
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
            move = bestMove;
            //System.out.println("moveToCenter: " + Arrays.toString(move));
            return move;
        }
        else
        {
            move[0] = -1;
            move[1] = -1;
            move[2] = -1;
            return move;
        }
    }

    protected int[] cohesionCenter()
    {
        //TODO Weight the getLevelScore and getLineScore with test values to find best ratio
        int[] move = new int[3];
        int initialLevel = ComputerPlayerV3.getLevelSum(graph, computerColor);
        int initialCohesion = ComputerPlayerV3.getCohesion(graph, computerColor);
        double initialSum = weights[0] * initialCohesion + weights[1] * initialLevel;
        //System.out.println("ComputerSumInitial: " + initialSum);
        int bestLevel = initialLevel;
        int bestCohesion = initialCohesion;
        double bestSum = initialSum;
        int[] bestMove = {-1, -1, -1};
        int testLevel = 0;
        int testCohesion = 0;
        double testSum = 0;
        AbaloneGraph testGraph = graph.clone();

        for(int i=0; i<computerNodes.size(); i++)
        {
            int currPieceID = computerNodes.get(i).getID();
            //System.out.println("\n Testing node: " + currPieceID);
            //System.out.println("Best Sum so far: " + bestSum);
            //System.out.println("Best Move so far: " + Arrays.toString(bestMove));
            Node currPiece = testGraph.getNode(currPieceID);
            Node piece2 = null;
            Node dest = null;
            testLevel = 0;
            testCohesion = 0;

            for(int j=1; j<12; j+=2)
            {
                piece2 = currPiece.getSibling(j);
                if (!piece2.isEdge() && !piece2.bordersEdge())
                {
                    dest = testGraph.destination(currPiece, piece2, j);
                    //System.out.println("TestDestination " + j + ": " + dest);
                    if (dest != null && !dest.bordersEdge())
                    {
                        int[] testMove = {currPieceID, dest.getID(), j};
                        //System.out.println("Testing Move: " + Arrays.toString(testMove));
                        testGraph.makeInlineMove(currPiece, dest, j);
                        testLevel = ComputerPlayerV3.getLevelSum(testGraph, computerColor);
                        testCohesion = ComputerPlayerV3.getCohesion(testGraph, computerColor);
                        testSum = weights[0] * testCohesion + weights[1] * testLevel;
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
        if (initialSum < bestSum)
        {
            move = bestMove;
            //System.out.println("moveToCenter: " + Arrays.toString(move));
            return move;
        }
        else
        {
            move[0] = -1;
            move[1] = -1;
            move[2] = -1;
            return move;
        }
    }

    //Returns a value that represents the sum of all lines of the computer's color minus the sum of its opponent's lines

    protected static int getLineScore(AbaloneGraph g, int playerColor) throws RuntimeException
    {
        int opponentColor = -1;
        //Get player and opponent colors
        if (playerColor == 1)
            opponentColor = 2;
        else if (playerColor == 2)
            opponentColor = 1;
        else
            throw new RuntimeException("Not a valid player color");

        int lineScore = 0;
        int numComputer = 0;
        int numOpponent = 0;
        Node currNode = null;
        int currColor = 0;
        boolean length2Com = false;
        boolean length2Opp = false;

        for (int j = 0; j< startTraversals.length; ++j)
        {
            for (int i = 0; i < startTraversals[j].length; ++i)
            {
                currNode = g.getNode(startTraversals[j][i]);
                //Traverse across each horizontal row adding 1 for lines of 2 and 2 points for lines of three
                while (currNode != null) {
                    currColor = currNode.getColor();
                    //Increments numComputer if the currNode is its color
                    if (currColor == playerColor) {
                        ++numComputer;
                    }
                    else if (currColor == opponentColor) {
                        ++numOpponent;
                    }
                    //Reset values if an empty space or opponent's piece is reached because the line has ended
                    if (currColor == 0 || currColor == opponentColor) {
                        numComputer = 0;
                        length2Com = false;
                    }
                    if (currColor == 0 || currColor == playerColor){
                        numOpponent = 0;
                        length2Opp = false;
                    }
                    //Add a point to the computer's score if a line of two is made
                    if (numComputer == 2) {
                        ++lineScore;
                        length2Com = true;
                    }
                    else if (numOpponent == 2) {
                        --lineScore;
                        length2Opp = true;
                    }
                    //Add an additional point if the line of two becomes a line of three
                    if (length2Com && numComputer == 3) {
                        ++lineScore;
                        length2Com = false;
                        numComputer = 0;
                    }
                    else if (length2Opp && numOpponent == 3) {
                        --lineScore;
                        length2Opp = false;
                        numOpponent = 0;
                    }
                    //Set currNode to its right sibling to traverse across the row horizontally
                    currNode = currNode.getSibling(traversalDirections[j]);
                }
            }
        }
        return lineScore;
    }

    protected static int getCohesion(AbaloneGraph g, int color)
    {
        int opponentColor = -1;
        if (color == 1)
            opponentColor = 2;
        else if (color == 2)
            opponentColor = 1;
        else
            throw new RuntimeException("Invalid player");

        int cohesion = 0;
        Node currNode = null;
        for (int i = 0; i < g.GRAPH_SIZE; ++i)
        {
            currNode = g.getNode(i);
            if (currNode.getColor() == color)
            {
                cohesion +=  currNode.getNumFriends();
            }
        }
        return cohesion;
    }

    public double[] getWeights()
    {
        return weights;
    }

    private void initNonEdges()
    {
        int currPosition = 0;
        for (int i = 0; i < graph.GRAPH_SIZE; ++i)
        {
            if (!graph.getNode(i).isEdge())
            {
                nonEdgePositions[currPosition] = i;
                ++currPosition;
            }
        }
    }
}
