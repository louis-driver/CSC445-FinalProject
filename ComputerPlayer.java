//A class to play against a human 
//It will probably play like the biggest idiot known to man kind, so be nice. 


//Player takes a AbaloneGraph Object for construction.
//For each turn, the updated graph should be passed to the Player to keep track of its nodes and positions
//Calling the get move method provides the nodes for the next computer move
import java.util.ArrayList;

public class ComputerPlayer {
    private ArrayList<Node> computerNodes;
    private ArrayList<Node> edgePieces;
    private AbaloneGraph graph;
    private ArrayList<Node> nonEdgePieces;
    private ArrayList<Node> opponentNodes;

    public ComputerPlayer(AbaloneGraph g)
    {
        computerNodes = new ArrayList<Node>(14);
        edgePieces = new ArrayList<Node>(computerNodes.size());
        nonEdgePieces = new ArrayList<>(computerNodes.size());
        opponentNodes = new ArrayList<>(computerNodes.size());
        graph = g;
        for(int i=0; i<91; i++)
        {
            if(g.getNode(i).getColor()==2)
            {
                computerNodes.add(g.getNode(i));
                if(g.getNode(i).bordersEdge() && !g.getNode(i).isEdge())
                    edgePieces.add(g.getNode(i));
                
                else if(!g.getNode(i).isEdge())
                    nonEdgePieces.add(g.getNode(i));
            }  
            else if(g.getNode(i).getColor()==1)
                opponentNodes.add(g.getNode(i));    
        }
        edgePieces.trimToSize();
        nonEdgePieces.trimToSize();
        computerNodes.trimToSize();
        // System.out.println("edge piece size 1: " + edgePieces.size());
    }

    //Takes the current state of graph and iterates throguh adding its players to an ArrayList and its edgePieces to and Array List
    public void updatePlayers(AbaloneGraph g)
    {
        computerNodes.clear();
        edgePieces.clear();
        for(int i=0; i<91; i++)
        {
            if(g.getNode(i).getColor()==2)
            {
                computerNodes.add(g.getNode(i));
                if(g.getNode(i).bordersEdge() && !g.getNode(i).isEdge())
                    edgePieces.add(g.getNode(i));
                else if(!g.getNode(i).isEdge())
                    nonEdgePieces.add(g.getNode(i));
            }   
        }
        computerNodes.trimToSize();
        edgePieces.trimToSize();
        nonEdgePieces.trimToSize();
        // System.out.println("comp piece size 2: " + computerNodes.size());
        // System.out.println("edge piece size 2: " + edgePieces.size());
    }

    //Returns the nodes and direction for the next move
    //The system to determine moves is to prioritise edge pieces and move them from the edge
    //The moves are ranked as follows:
    // 1. If its an edge piece, borders a white piece, and can push the white piece
    // 2. If its an edge piece, borders a white piece, and can move away from the white piece
    // 3. If its an edge piece and can move 
    // 4. Any other single piece that can move 
    //The int[] returned is ordered as follows: [first node ID from graph, destination piece ID from graph, direction]
    public int[] getMove()
    {
        int[] move = dangerEscapeBoth();
        if(move[0]!=-1)
            return move;
        move = dangerEscapeDanger();
        if(move[0]!=-1)
            return move;
        move = pushWhite();
        if(move[0]!=-1)
            return move;
        move = edgePush();
        if(move[0]!=-1)
            return move;
        move = edgeEscape();
        if(move[0]!=-1)
            return move;
        move = otherMove();
        if(move[0]!=-1)
            return move;
        int[] error = {-1, -1, -1};
        return error;


    }

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
                if(destination!= null && !destination.bordersEdge() && !destination.isEdge())
                {
                    toMove = piece1;
                    direction = j; 
                    destination = dest;
                }
            }
        }
        if(toMove!=null && direction!=-1 && destination!=null)
        {
            int[] move = {toMove.getID(), destination.getColor(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        }

    }
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
                if(destination!=null && !destination.isEdge())
                {
                    toMove = piece2;
                    direction =j;
                    destination = dest;
                }
            }
        } 
        if(toMove!=null && direction!=-1 && destination!=null)
        {
            int[] move = {toMove.getID(), destination.getColor(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        }  
    }
    private int[] captureOpponent()
    {
        int toMove = -1;
        int direction = -1;
        for(int i=0; i<opponentNodes.size(); i++)
        {
            int[] danger = graph.inDangerFrom(opponentNodes.get(i));
            if(danger[0]!=-1)
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
    private int[] edgeEscape()
    {
        Node toMove = null;
        int direction = -1;
        Node destination = null;
        for(int i=0; i<edgePieces.size(); i++)
        {
            for(int j=2; j<12; j+=2)
            {
                Node piece1 = edgePieces.get(i);
                Node piece2 = piece1.getSibling(j);
                Node dest= graph.destination(piece1, piece2, j);
                if(destination!= null && !destination.bordersEdge())
                {
                    toMove = piece1;
                    direction = j;
                    destination = dest;
                }
            }

        }
        if(toMove!=null && direction!=-1 && destination!=null)
        {
            int[] move = {toMove.getID(), destination.getColor(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        } 
    }
    private int[] pushWhite()
    {
        Node toMove = null;
        int direction = -1;
        for(int i=0; i<nonEdgePieces.size(); i++)
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
    private int[] otherMove()
    {
        for(int i=0; i<nonEdgePieces.size(); i++)
        {
            for(int j=1; j<12; j+=2)
            {
                Node piece1 = nonEdgePieces.get(i);
                Node peice2 = piece1.getSibling(j);
                Node dest = graph.destination(piece1, peice2, j);
                if(dest!=null && !dest.isEdge())
                {
                    int[] move = {piece1.getID(), dest.getID(), j};
                    return move;

                }

            }
        }

        int[] move = {-1, -1, -1};
        return move;

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
