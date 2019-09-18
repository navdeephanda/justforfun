import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class NetworkAnalysis
{
    public static void main(String [] args)
    {
        EdgeWeightedGraph G = new EdgeWeightedGraph(args[0]);

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the NetworkAnalyzer application!");
        promptUser();
        int userChoice = sc.nextInt();
        sc.nextLine();

        while(userChoice != 0)
        {
            switch(userChoice)
            {
                case 0: //failsafe. this code should never be reached
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
                case 1:
                    lowestLatencyPath(G, sc);
                    break;
                case 2:
                    copperConnected(G);
                    break;
                case 3:
                    minimumAverageLatencySpanningTree(G);
                    break;
                case 4:
                    canSurviveTwoFailures(G);
                    break;
                default:
                    System.out.println("Invalid input! Try again.");
                    break;
            }
            promptUser();
            userChoice = sc.nextInt();
            sc.nextLine();
        }
        System.out.println("Bye!");
        sc.close();
    }

    private static void promptUser()
    {
        System.out.println
        ("Type the corresponding number to select an action.\n"+
         "1. Find the lowest latency path between any two network nodes and the bandwith along that path.\n"+
         "2. Determine whether the network is copper-only connected.\n"+
         "3. Find the minimum average latency spanning tree.\n"+
         "4. Determine whether the network would still be connected if any two nodes fail.\n"+
         "Type 0 to quit.\n"
        );
    }

    private static void lowestLatencyPath(EdgeWeightedGraph G, Scanner userInputSrc)
    {
        System.out.println("Enter the starting network node: ");
        int startV = Integer.parseInt(userInputSrc.nextLine());
        while(startV < 0 || startV >= G.V())
        {
            System.out.println("Yeah, that's not a valid node in the network. Try again. You can do anything from 0 to "+(G.V()-1)+"!");
            startV = Integer.parseInt(userInputSrc.nextLine());    
        }
        // compute shortest paths
        DijkstraSP sp = new DijkstraSP(G, startV);

        System.out.println("Enter the ending network node: ");
        int endV = Integer.parseInt(userInputSrc.nextLine());
        while(endV < 0 || endV >= G.V())
        {
            System.out.println("That node's out of the network range. Let's try this ONE more time. You can do anything from 0 to "+(G.V()-1)+"!");
            endV = Integer.parseInt(userInputSrc.nextLine());    
        }

        // print shortest path
        System.out.println(sp.pathString(startV, endV));
        System.out.println("\n");
    }

    private static void copperConnected(EdgeWeightedGraph G)
    {
        PrimMSTCopper mst = new PrimMSTCopper(G);
        if (mst.isNotSpanningTree())
        {
            System.out.println("This network is not copper connected.\n");
        }
        else
        {
            System.out.println("This network is copper connected.\n");
        }
    }

    private static void minimumAverageLatencySpanningTree(EdgeWeightedGraph G)
    {
        PrimMST mst = new PrimMST(G);
        if (mst.isNotSpanningTree(G))
        {
            System.out.println("No minimum spanning tree.\n");
        }
        else
        {
            for (Edge e : mst.edges()) 
            {
                System.out.println(e);
            }
            System.out.printf("Total latency along this MST: %.4f ms\n\n", mst.weight()*1000000);    
        }
    }

    private static void canSurviveTwoFailures(EdgeWeightedGraph G)
    {
        ArrayList<Edge>[] edges = new ArrayList[G.V()];

        for(int i = 0; i < edges.length; i++)
        {
            edges[i] = new ArrayList<Edge>();
            Iterator<Edge> adj = G.adj(i).iterator();
            while(adj.hasNext())
            {
                edges[i].add(adj.next());
            }
        }
        for(int i = 0; i < G.V(); i++)
        {
            for(int j = i+1; j < G.V(); j++)
            {
                EdgeWeightedGraph gWithoutIandJ = new EdgeWeightedGraph(G.V());
                for(int a = 0; a < edges.length; a++)
                {
                    for(int b = 0; b < edges[a].size(); b++)
                    {
                        if(edges[a].get(b).either() == i) continue;
                        if(edges[a].get(b).other(edges[a].get(b).either()) == j) continue;
                        if(edges[a].get(b).either() == j) continue;
                        if(edges[a].get(b).other(edges[a].get(b).either()) == i) continue;
                        gWithoutIandJ.addEdge(edges[a].get(b));
                    }
                }
                CC connectedComponent = new CC(gWithoutIandJ);
                for(int currV = 0; currV < G.V(); currV++)
                {
                    for(int combV = 0; combV < G.V(); combV++)
                    {
                        if(currV != i && currV != j && combV != i && combV != j && currV != combV)
                        {
                            if(!connectedComponent.connected(currV, combV))
                            {
                                System.out.println("Network can't survive two node failures");
                                return;
                            }
                        }
                    }

                }
            }
        }
        System.out.println("Network can survive two failed nodes");    
    }
}