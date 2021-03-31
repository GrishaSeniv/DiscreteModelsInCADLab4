import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FordFulkerson
{
    private int[] parent;
    private Queue<Integer> queue;
    private int numberOfVertices;
    private boolean[] visited;

    public FordFulkerson(int numberOfVertices)
    {
        this.numberOfVertices = numberOfVertices;
        this.queue = new LinkedList<Integer>();
        parent = new int[numberOfVertices + 1];
        visited = new boolean[numberOfVertices + 1];
    }

    public boolean bfs(int source, int goal, int graph[][])
    {
        boolean pathFound = false;
        int destination, element;

        for(int vertex = 1; vertex <= numberOfVertices; vertex++)
        {
            parent[vertex] = -1;
            visited[vertex] = false;
        }

        queue.add(source);
        parent[source] = -1;
        visited[source] = true;

        while (!queue.isEmpty())
        {
            element = queue.remove();
            destination = 1;

            while (destination <= numberOfVertices)
            {
                if (graph[element][destination] > 0 &&  !visited[destination])
                {
                    parent[destination] = element;
                    queue.add(destination);
                    visited[destination] = true;
                }
                destination++;
            }
        }
        if(visited[goal])
        {
            pathFound = true;
        }
        return pathFound;
    }

    public int fordFulkerson(int graph[][], int source, int destination)
    {
        int u, v;
        int maxFlow = 0;
        int pathFlow;

        int[][] residualGraph = new int[numberOfVertices + 1][numberOfVertices + 1];
        for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++)
        {
            for (int destinationVertex = 1; destinationVertex <= numberOfVertices; destinationVertex++)
            {
                residualGraph[sourceVertex][destinationVertex] = graph[sourceVertex][destinationVertex];
            }
        }

        System.out.println("Path with MinFlow");
        while (bfs(source ,destination, residualGraph))
        {
            pathFlow = Integer.MAX_VALUE;
            for (v = destination; v != source; v = parent[v])
            {
                u = parent[v];
                //System.out.println(parent[v]);
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            for (v = destination; v != source; v = parent[v])
            {
                u = parent[v];
                //System.out.println(residualGraph[u][v]);
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }
            System.out.print(" > " + pathFlow);
            maxFlow += pathFlow;
        }
        System.out.println();

        return maxFlow;
    }

    public static void main(String...arg)
    {
        int[][] graph;
        int numberOfNodes;
        int source;
        int sink;
        int maxFlow;

        String fileIn = "C:\\Users\\Grisha\\Desktop\\Ну ЛП\\4 курс\\2 семестр\\Дискретна математика\\Lab4\\Ford-fulkerson algorithm\\Data\\l4-2.txt";
        String fileOut = "C:\\Users\\Grisha\\Desktop\\Ну ЛП\\4 курс\\2 семестр\\Дискретна математика\\Lab4\\Ford-fulkerson algorithm\\Data\\Out.txt";
        FileReaderMatrixSize fileReaderMatrixSize = new FileReaderMatrixSize(fileIn, fileOut);
        

        Scanner scanner = new Scanner(System.in);
        System.out.println("The number of nodes: " + fileReaderMatrixSize.getM());
        numberOfNodes = fileReaderMatrixSize.getM();
        graph = new int[numberOfNodes + 1][numberOfNodes + 1];

        System.out.println("The graph matrix");
        for (int sourceVertex = 1; sourceVertex <= numberOfNodes; sourceVertex++)
        {
            for (int destinationVertex = 1; destinationVertex <= numberOfNodes; destinationVertex++)
            {
                System.out.print(fileReaderMatrixSize.getArray(sourceVertex, destinationVertex) + " ");
                graph[sourceVertex][destinationVertex] = fileReaderMatrixSize.getArray(sourceVertex, destinationVertex);
            }
            System.out.println();
        }

        System.out.println("Enter the source of the graph");
        source= scanner.nextInt();

        System.out.println("Enter the sink of the graph");
        sink = scanner.nextInt();

        FordFulkerson fordFulkerson = new FordFulkerson(numberOfNodes);
        maxFlow = fordFulkerson.fordFulkerson(graph, source, sink);
        System.out.println("The Max Flow is " + maxFlow);
        scanner.close();
    }
}