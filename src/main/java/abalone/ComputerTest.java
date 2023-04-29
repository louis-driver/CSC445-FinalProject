package abalone;

//Louis Driver test commit

//This is a JPanel that represents an Abalone board during play

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ComputerTest
{
    private AbaloneGraph graph;
    private int graphSize = 91;
    private ComputerPlayer ai1;
    private ComputerPlayer ai2;

    //Functionality
    private ArrayList<int[][]> previousBoards = new ArrayList<>(12);
    private int player1Score;
    private int player2Score;
    private boolean player1Turn = true;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private int numMoves;
    private static double[][] testWeights = {{0.1, 0.9}, {0.2, 0.8}, {0.3, 0.7}, {0.0, 1.0}};

    //Display
    JFrame frame = new JFrame();
    AbalonePanel panel;

    //Test Main class
    public static void main(String[] args) {
        AbaloneGraph testGraph = new AbaloneGraph();
        ComputerTest.randomizeWeights(testWeights);

        AbaloneGraph graph = new AbaloneGraph();
        ArrayList<ComputerPlayerV3> coms = new ArrayList<>(testWeights.length);
        for (int numWeights = 0; numWeights < testWeights.length; ++numWeights)
        {
            /*
            if (numWeights%2 == 0)
                coms.add(new ComputerPlayerV3(graph, 1, testWeights[numWeights]));
            if (numWeights%2 == 1)
                coms.add(new ComputerPlayerV3(graph, 2, testWeights[numWeights]));

             */
            coms.add(new ComputerPlayerV3(graph, 2, testWeights[numWeights]));
        }

        ComputerPlayer cmV2 = new ComputerPlayer(graph, 1);

        int comNum = 0;
        ComputerTest computerTest = null;
        while (coms.size() != 0) {
            ComputerPlayerV3 currCom1 = null;
            ComputerPlayer currCom2 = cmV2;
            //ComputerPlayerV3 currCom2 = null;
            if (coms.size() >= 1) {
                currCom1 = coms.get(coms.size() - 1);
                coms.remove(currCom1);
                //System.out.println(currCom1.getComputerColor());
            }

            /*
            if (coms.size() >= 1) {
                currCom2 = coms.get(coms.size() - 1);
                coms.remove(currCom2);
                //System.out.println(currCom2.getComputerColor());
            } */

            if (currCom2 != null && currCom1 != null) {
                int ai1Wins = 0;
                int ai2Wins = 0;
                int numGames = 50;

                testGraph.resetGraph();
                //System.out.println("graph reset");
                computerTest = new ComputerTest(testGraph, currCom1, currCom2);
                //System.out.println("New computer test made");
                //System.out.println("Ai1Weights: " + Arrays.toString(currCom1.getWeights()) + " Ai2Weights: " + Arrays.toString(currCom2.getWeights()));
                computerTest.createGameFrame(testGraph);
                for (int i = 0; i < numGames; ++i) {
                    testGraph.resetGraph();
                    //System.out.println("graph reset");
                    //computerTest.updateGameFrame(testGraph);
                    int move = 1;
                    int currC1Score = 0;
                    int currC2Score = 0;
                    boolean gameOver = false;
                    while (computerTest.getPlayerScore(1) < 6 && computerTest.getPlayerScore(2) < 6 && !gameOver) {
                        if (computerTest.getPlayerScore(1) >= 6 && computerTest.getPlayerScore(2) >= 6)
                            gameOver = true;
                        if (currC2Score != computerTest.getPlayerScore(2) || currC1Score != computerTest.getPlayerScore(1)) {
                            //System.out.println("Move : " + computerTest.numMoves);
                            currC1Score = computerTest.getPlayerScore(1);
                            currC2Score = computerTest.getPlayerScore(2);
                        }
                        computerTest.delayComputerMoves();
                        computerTest.refreshFrame();
                        ++move;
                    }
                    if (computerTest.getPlayerScore(1) == 6)
                        ++ai1Wins;
                    if (computerTest.getPlayerScore(2) == 6)
                        ++ai2Wins;
                    //System.out.println("Game" + i +" Over   Ai1Wins: " + ai1Wins + " Ai2Wins: " + ai2Wins);
                }
                //System.out.println("Ai1Weights: " + Arrays.toString(currCom1.getWeights()) + " Ai2Weights: " + Arrays.toString(currCom2.getWeights()));
                System.out.println("Ai1Weights: " + Arrays.toString(currCom1.getWeights()));
                System.out.println("Ai1Wins: " + ai1Wins + " AiV2Wins: " + ai2Wins);
            }
        }
        //System.out.println("GameOver");
        //computerTest.updateGameFrame(testGraph);
    }

    public ComputerTest(AbaloneGraph g) {
        this.graph = g;
        this.ai1 = new ComputerPlayer(this.graph, 1);
        this.ai2 = new ComputerPlayerV2(this.graph, 2);
    }

    public ComputerTest(AbaloneGraph g, ComputerPlayer c1, ComputerPlayer c2) {
        this.graph = g;
        this.ai1 = c1;
        this.ai2 = c2;
    }

    //This method creates a frame to display a passed graph
    public void createGameFrame(AbaloneGraph g) {
        int frameWidth = 500;
        int frameHeight = 400;
        this.graph = g.clone();
        panel = new AbalonePanel(graph, false, false);

        frame.setSize(frameWidth, frameHeight);
        frame.setTitle("ComputerGraph");
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    //Updates the frame to display a passed AbaloneGraph
    public void updateGameFrame(AbaloneGraph g) {
        frame.remove(panel);
        panel = new AbalonePanel(g, false, false);
        frame.add(panel);
        frame.repaint();
    }

    public void refreshFrame() {
        frame.repaint();
    }

    //Returns null if an incorrect player number is passed
    public ComputerPlayer getComputerPlayer(int player) {
        if (player == 1)
            return ai1;
        else if (player == 2)
            return ai2;
        else
            return null;
    }


    public void delayComputerMoves() {
        scheduler.schedule(new ComputerMoves(), 0l, TimeUnit.MILLISECONDS);
    }

    public int getPlayerScore(int player)
    {
        if (player == 1)
            return player1Score;
        else if (player == 2)
            return player2Score;
        else
            return -1;
    }
    private class ComputerMoves implements Runnable
    {
        public void run()
        {
            if (inLoop())
            {
                ai1.setInLoop(true);
                ai2.setInLoop(true);
                //System.out.println("Was in loop");
            }
            else
            {
                ai1.setInLoop(false);
                ai2.setInLoop(false);
            }
            if (player1Score != graph.getPlayer1Score() || player2Score != graph.getPlayer2Score())
            {
                player1Score = graph.getPlayer1Score();
                player2Score = graph.getPlayer2Score();
                //System.out.println(" C1: " + player1Score + " C2: " + player2Score);
            }
                if (player1Score < 6 && player2Score < 6) {
                    ai1.updatePlayers(graph);
                    int[] moveInfo = ai1.getMove();
                    graph.makeInlineMove(graph.getNode(moveInfo[0]), graph.getNode(moveInfo[1]), moveInfo[2]);
                    //System.out.println("Computer1 move made");
                    player1Turn = !player1Turn;
                    ++numMoves;

                    //Add gameState to previous gameStates
                    if (previousBoards.size() == 12)
                    {
                        previousBoards.remove(0);
                        previousBoards.add(graph.getPlayerPositions());
                    }
                    else
                    {
                        previousBoards.add(graph.getPlayerPositions());
                    }
                }
                if (player1Score < 6 && player2Score < 6) {
                    ai2.updatePlayers(graph);
                    int[] moveInfo = ai2.getMove();
                    graph.makeInlineMove(graph.getNode(moveInfo[0]), graph.getNode(moveInfo[1]), moveInfo[2]);
                    //System.out.println("Computer2 move made");
                    player1Turn = !player1Turn;
                    ++numMoves;

                    //Add gameState to previous gameStates
                    if (previousBoards.size() == 12)
                    {
                        previousBoards.remove(0);
                        previousBoards.add(graph.getPlayerPositions());
                    }
                    else
                    {
                        previousBoards.add(graph.getPlayerPositions());
                    }
                }
                int[] scores = {player1Score, player2Score};
                panel.setPlayerScores(scores);
        }
    }

    //Used to see if the computer is in a loop and repeating moves
    public boolean inLoop()
    {
        boolean inLoop = false;
        //Compare each positions[][] to each other, if at least 3 of the arrays equal each other it is in a loop
        for (int i = 0; i < previousBoards.size() && !inLoop; ++i)
        {
            int currDuplicates = 0;
            int[][] currBoardState = previousBoards.get(i);
            for (int j = 0; j < previousBoards.size(); ++j)
            {
                if (j != i)
                {
                    if (Arrays.deepEquals(currBoardState, previousBoards.get(j)))
                    {
                        ++currDuplicates;
                    }
                }
            }
            if (currDuplicates >= 1)
                inLoop = true;
        }
        return inLoop;
    }

    protected static void randomizeWeights(double[][] weights)
    {
        for (int i = 0; i < weights.length; ++i)
        {
            int randomPosition = (int) (Math.random() * (weights.length));
            double[] temp = weights[i];
            weights[i] = weights[randomPosition];
            weights[randomPosition] = temp;
        }
    }
}

