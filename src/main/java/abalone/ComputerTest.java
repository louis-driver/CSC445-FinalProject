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

    //Display
    JFrame frame = new JFrame();
    AbalonePanel panel;

    //Test Main class
    public static void main(String[] args) {
        AbaloneGraph testGraph = new AbaloneGraph();
        //ComputerTest computerTest = new ComputerTest(testGraph);
        //computerTest.createGameFrame(testGraph);
        /*
        int[] testMove = computerTest.getComputerPlayer(1).getMove();
        if (testMove[0] != -1)
            testGraph.makeInlineMove(testGraph.getNode(testMove[0]), testGraph.getNode(testMove[1]), testMove[2]);
        else
            System.out.println("unable to make move"); */
        /*
        int[][] currBoardState = testGraph.getPlayerPositions();
        int[][] duplicatBoard = currBoardState.clone();
        System.out.println(Arrays.toString(currBoardState));
        System.out.println(Arrays.toString(duplicatBoard));
        System.out.println("DeepEquals: " + Arrays.deepEquals(currBoardState, duplicatBoard)); */

        int ai1Wins = 0;
        int ai2Wins = 0;
        int numGames = 20;
        for (int i = 0 ; i < numGames; ++i) {
            ComputerTest computerTest = new ComputerTest(testGraph);
            computerTest.createGameFrame(testGraph);
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
        }
        System.out.println("Ai1Wins: " + ai1Wins + " Ai2Wins: " + ai2Wins);
        //System.out.println("GameOver");
        //computerTest.updateGameFrame(testGraph);
    }

    public ComputerTest(AbaloneGraph g) {
        this.graph = g;
        this.ai1 = new ComputerPlayer(this.graph, 1);
        this.ai2 = new ComputerPlayerV2(this.graph, 2);
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
            if (currDuplicates >= 2)
                inLoop = true;
        }
        return inLoop;
    }
}

