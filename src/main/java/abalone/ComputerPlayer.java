package abalone;
//A class to play against a human 

//Player takes a AbaloneGraph Object for construction.
//For each turn, the updated graph should be passed to the Player to keep track of its nodes and positions
//Calling the get move method provides the nodes for the next computer move
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ComputerPlayer {
    private ArrayList<Node> computerNodes;
    private ArrayList<Node> edgePieces;
    private AbaloneGraph graph;
    private ArrayList<Node> nonEdgePieces;
    private ArrayList<Node> opponentNodes;
    private int computerColor;
    private int opponentColor;
    private int[] playablePositions;

    public ComputerPlayer(AbaloneGraph g, int color)
    {
        computerNodes = new ArrayList<Node>(14);
        edgePieces = new ArrayList<Node>(computerNodes.size());
        nonEdgePieces = new ArrayList<>(computerNodes.size());
        opponentNodes = new ArrayList<>(computerNodes.size());
        graph = g;
        computerColor = color;
        opponentColor = 1;
        if (computerColor == 1)
            opponentColor = 2;
        
        playablePositions = getBoardSpaces();
        randomizeArray(playablePositions);
        for(int i=0; i<playablePositions.length; i++)
        {
            Node currNode = graph.getNode(playablePositions[i]);
            if(currNode.getColor()==computerColor)
            {
                computerNodes.add(currNode);
                if(currNode.bordersEdge() && !currNode.isEdge())
                    edgePieces.add(currNode);
                else if(!currNode.isEdge())
                    nonEdgePieces.add(currNode);
            }   
            if(currNode.getColor()==opponentColor)
            {
                opponentNodes.add(currNode);
            }   
        }
        edgePieces.trimToSize();
        nonEdgePieces.trimToSize();
        computerNodes.trimToSize();
    }

    //Takes the current state of graph and iterates through adding its players to an ArrayList and its edgePieces to and Array List
    public void updatePlayers(AbaloneGraph g)
    {
        graph = g;
        computerNodes.clear();
        edgePieces.clear();
        opponentNodes.clear();
        randomizeArray(playablePositions);
        for(int i=0; i<playablePositions.length; i++)
        {
            Node currNode = graph.getNode(playablePositions[i]);
            if(currNode.getColor()==computerColor)
            {
                computerNodes.add(currNode);
                if(currNode.bordersEdge() && !currNode.isEdge())
                    edgePieces.add(currNode);
                else if(!currNode.isEdge())
                    nonEdgePieces.add(currNode);
            }
            if(currNode.getColor()==opponentColor)
            {
                opponentNodes.add(currNode);
            }
        }
        computerNodes.trimToSize();
        edgePieces.trimToSize();
        nonEdgePieces.trimToSize();
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
    public int[] getMove()
    {
        updatePlayers(graph);
        int[] move = dangerEscapeBoth();
        if(move[0]!=-1)
            return move;
        move = dangerEscapeDanger();
        if(move[0]!=-1)
            return move;
        move = captureOpponent();
        if(move[0]!=-1)
            return move;
        move = edgePush();
        if(move[0]!=-1)
            return move;
        move = moveToCenter();
        if(move[0]!=-1)
            return move;
        System.out.println("Didn't move to center");
        move = edgeEscape();
        if(move[0]!=-1)
            return move;
        move = pushWhite();
        if(move[0]!=-1)
            return move;
        move = uniteFriends();
        if(move[0]!=-1)
            return move;
        move = otherMove();
        if(move[0]!=-1)
            return move;
        int[] error = {-1, -1, -1};
        return error;


    }

    //Returns a move if a node is found in danger that can escape the edge and danger. Otherwise returns {-1, -1, -1}
    private int[] dangerEscapeBoth()
    {
        Node toMove = null;
        int direction = -1;
        Node destination = null;
        for(int i=0; i<edgePieces.size(); i++)
        {
            Node piece1 = edgePieces.get(i);
            int[] inDanger = graph.inDangerFrom(piece1); 
            for(int j=1; j<12 && inDanger[0]!=-1; j+=2)
            {
                Node piece2 = piece1.getSibling(j);
                Node dest= graph.destination(piece1, piece2, j);
                if(dest!= null && !dest.bordersEdge() && !dest.isEdge())
                {
                        dest.setColor(0);
                        toMove = piece1;
                        direction = j; 
                        destination = dest;
                }
            }
        }
        if(toMove!=null && direction!=-1 && destination!=null)
        {
            int[] move = {toMove.getID(), destination.getID(), direction};
            //System.out.println("escapeDanger&Edge: " + Arrays.toString(move));
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        }

    }
    //Returns a move if a node is found in danger that can escape danger. Otherwise returns {-1, -1, -1}
    private int[] dangerEscapeDanger()
    {
        Node toMove = null;
        int direction = -1;
        Node destination = null;
        for(int i=0; i<edgePieces.size(); i++)
        {
            Node piece1 = edgePieces.get(i);
            int[] danger = graph.inDangerFrom(piece1);
            for(int j=1; j<12 && danger[0]!=-1; j+=2)
            {
                Node piece2 = piece1.getSibling(j);
                Node dest= graph.destination(piece1, piece2, j);
                if(dest!=null && !dest.isEdge())
                {
                    dest.setColor(2);
                    danger = graph.inDangerFrom(dest);
                    if(danger[0]==-1)
                    {
                        dest.setColor(0);
                        toMove = piece1;
                        direction =j;
                        destination = dest;
                    }
                    else 
                        dest.setColor(0);
                }
            }
        } 
        if(toMove!=null && direction!=-1 && destination!=null)
        {
            int[] move = {toMove.getID(), destination.getID(), direction};
            //System.out.println("dangerEscapeDanger: " + Arrays.toString(move));
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        }  
    }
    //Returns a move if an opponent piece can be captured. Otherwise returns {-1, -1, -1}
    private int[] captureOpponent()
    {
        int toMove = -1;
        int direction = -1;
        for(int i=0; i<opponentNodes.size(); i++)
        {
            int[] danger = graph.inDangerFrom(opponentNodes.get(i));
            if(danger[0]!=-1 && graph.getNode(danger[0]).getColor()==computerColor)
            {
                toMove = danger[0];
                direction = danger[1];
            }
        }
        if(toMove!=-1 && direction!=-1)
        {
            Node piece1 = graph.getNode(toMove);
            Node piece2 = piece1.getSibling(direction);
            Node destination = graph.destination(piece1, piece2, direction);
            int[] move = {piece1.getID(), destination.getID(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        }
    }
    //Returns a move if a node is found on an edge and can push an opponent. Otherwise returns {-1, -1, -1}
    private int[] edgePush()
    {
        Node toMove = null;
        int direction = -1;
        for(int i=0; i<edgePieces.size(); i++)
        {
            for(int j=1; j<12; j+=2)
            {
                Node piece = edgePieces.get(i);
                boolean canPush = graph.canPush(piece, j); 
                if(canPush)
                {
                    toMove = edgePieces.get(i);
                    direction = j;
                }
            }
        }
        if(toMove!=null && direction!=-1)
        {
            Node piece2 = toMove.getSibling(direction);
            Node destination = graph.destination(toMove, piece2, direction);
            int[] move = {toMove.getID(), destination.getID(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        }
    }
    //Returns a move if a node is found on an edge and can escape edge. Otherwise returns {-1, -1, -1}
    private int[] edgeEscape()
    {
        Node toMove = null;
        int direction = -1;
        Node destination = null;
        for(int i=0; i<edgePieces.size(); i++)
        {
            for(int j=1; j<12; j+=2)
            {
                Node piece1 = edgePieces.get(i);
                Node piece2 = piece1.getSibling(j);
                Node dest= graph.destination(piece1, piece2, j);
                if(dest!= null && !dest.isEdge() && !dest.bordersEdge())
                {
                    toMove = piece1;
                    direction = j;
                    destination = dest;
                }
            }

        }
        if(toMove!=null && direction!=-1 && destination!=null)
        {
            int[] move = {toMove.getID(), destination.getID(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        } 
    }
    //Returns a move if a node is found that can push an opponent. Otherwise returns {-1, -1, -1}
    private int[] pushWhite()
    {
        Node toMove = null;
        int direction = -1;
        for(int i=0; i<computerNodes.size(); i++)
        {
            for(int j=1; j<12; j+=2)
            {
                Node piece = computerNodes.get(i);
                boolean canPush = graph.canPush(piece, j);
                if(canPush)
                {
                    toMove = computerNodes.get(i);
                    direction = j;
                }
            }
        }
        if(toMove!=null && direction!=-1)
        {
            Node piece2 = toMove.getSibling(direction);
            Node destination = graph.destination(toMove, piece2, direction);
            int[] move = {toMove.getID(), destination.getID(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        }
    }

    //Returns a move than will maximize the sum of the computer's nodes' levels
    private int[] moveToCenter()
    {
        int initialSum = ComputerPlayer.getLevelSum(graph, computerColor);
        System.out.println("ComputerSumInitial: " + initialSum);
        int bestSum = initialSum;
        int[] bestMove = new int[3];
        AbaloneGraph testGraph = graph.clone();

        int frameWidth = 400;
        int frameHeight = 300;
        JFrame frame = new JFrame();
        AbaloneGraph graph = new AbaloneGraph();
        AbalonePanel panel = new AbalonePanel(testGraph, true, false);

        frame.setSize(frameWidth, frameHeight);
        frame.setTitle("Clone Graph");
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

        for(int i=0; i<computerNodes.size(); i++)
        {
            testGraph = graph.clone();
            int currPieceID = computerNodes.get(i).getID();
            System.out.println("\n Testing node: " + currPieceID);
            System.out.println("Best Sum so far: " + bestSum);
            System.out.println("Best Move so far: " + Arrays.toString(bestMove));
            Node currPiece = testGraph.getNode(currPieceID);
            System.out.println("Testing node: " + currPiece);
            for(int j=1; j<12; j+=2)
            {
                testGraph = graph.clone();
                Node piece2 = currPiece.getSibling(j);
                if (!piece2.isEdge())
                {
                    Node dest = testGraph.destination(currPiece, piece2, j);
                    System.out.println("TestDestination: " + dest);
                    if (dest != null && !dest.bordersEdge())
                    {
                        int[] testMove = {currPiece.getID(), dest.getID(), j};
                        System.out.println("Testing Move: " + Arrays.toString(testMove));
                        testGraph.makeInlineMove(currPiece, dest, j);
                        int testSum = getLevelSum(testGraph, computerColor);
                        System.out.println("Test Sum: " + testSum);
                        if (testSum > bestSum)
                        {
                            bestSum = testSum;
                            bestMove = testMove;
                            System.out.println("Best Sum so far: " + bestSum);
                            testGraph = graph.clone();
                        }
                    }
                }
            }
        }

        System.out.println("ComputerSumBest: " + bestSum);
        if (bestSum > initialSum)
        {
            int[] move = bestMove;
            System.out.println("moveToCenter: " + Arrays.toString(move));
            return move;
        }
        else
        {
            int[] move = {-1, -1, -1};
            return move;
        }

    }

    //Returns a move that will push pieces to join other pieces of its own color
    private int[] uniteFriends()
    {
        System.out.println(" Cole is attempting to fix this method for Louis.");
        boolean friendsFound = false;
        Node toMove = null;
        int direction = -1;
        Node destination = null;
        for(int i=0; i<computerNodes.size() && !friendsFound; i++)
        {
            for(int j=1; j<12; j+=2)
            {
                Node piece1 = computerNodes.get(i);
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
            //System.out.println(Arrays.toString(move));
            return move;
        }
        else
        {
            int[] move = {-1, -1, -1};
            return move;
        }
    }

    //Returns a move the next possible move. Otherwise returns {-1, -1, -1}
    private int[] otherMove()
    {
        //System.out.println("OtherMove");
        //Moves on that won't go to an edge
        for(int i=0; i<computerNodes.size(); i++)
        {
            for(int j=1; j<12; j+=2)
            {
                Node piece1 = computerNodes.get(i);
                Node piece2 = piece1.getSibling(j);
                Node dest = graph.destination(piece1, piece2, j);
                if(dest!=null && !dest.bordersEdge() && !dest.isEdge())
                {
                        int[] move = {piece1.getID(), dest.getID(), j};
                        return move;
                }

            }
        }
        //Then choose one that goes to an edge
        for(int i=0; i<computerNodes.size(); i++)
        {
            for(int j=1; j<12; j+=2)
            {
                Node piece1 = computerNodes.get(i);
                Node piece2 = piece1.getSibling(j);
                Node dest = graph.destination(piece1, piece2, j);
                if(dest!=null && !dest.isEdge())
                {
                    dest.setColor(2);
                    int[] danger = graph.inDangerFrom(dest);
                    if(danger[0]==-1)
                    {
                        dest.setColor(0);
                        int[] move = {piece1.getID(), dest.getID(), j};
                        return move;
                    }
                    else 
                        dest.setColor(0);

                }

            }
        }

        int[] move = {-1, -1, -1};
        return move;

    }

    private int[] getBoardSpaces()
    {
        int[] playablePositions = new int[61];
        int currPosition = 0;
        for (int i = 0; i < 91; ++i)
        {
            if (!graph.getNode(i).isEdge())
            {
                playablePositions[currPosition] = i;
                ++currPosition;
            }
        }
        return playablePositions;
    }

    private static int getLevelSum(AbaloneGraph g, int player)
    {
        int levelSum = 0;
        for (int i = 0; i < g.GRAPH_SIZE; ++i)
        {
            if (g.getNode(i).getColor() == player)
                levelSum += g.getNode(i).getLevel();
        }
        return levelSum;
    }

    private void randomizeArray(int[] ints)
    {
        for (int i = 0; i < ints.length; ++i)
        {
            int randomPosition = (int) (Math.random() * (ints.length));
            int temp = ints[i];
            ints[i] = ints[randomPosition];
            ints[randomPosition] = temp;
        }
    }

    public String toString()
    {
        String s = "";
        for(int i=0; i<computerNodes.size(); i++)
        {
            if(computerNodes.get(i)!=null)
                s += computerNodes.get(i).getID() + " ";
        }

        return s;
    }

}
