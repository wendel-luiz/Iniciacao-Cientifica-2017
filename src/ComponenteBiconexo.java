package iniciação.científica;

import java.util.Iterator;
import java.util.LinkedList;

public class ComponenteBiconexo{
    private String diretorio;
    private Arquivo arq = new Arquivo();
	private int V,E;
	private Grafo grafoMatriz;
	private LinkedList<Integer> adj[];
    private LinkedList<Integer> clAdd;
    static int count = 0, time = 0;
        
    public ComponenteBiconexo(Grafo g, String dir){
            diretorio = dir;
            grafoMatriz = g;
            clAdd = new LinkedList();
            criaAdj();
            BCC();
        }
	
	class Edge{
		int u;
		int v;
		Edge(int u, int v){
			this.u = u;
			this.v = v;
		}
	};

	private void criaAdj(){
		V = grafoMatriz.getnClau();
        E = 0;
       	adj = new LinkedList[V];
         	
		for (int i=0; i<V; ++i)
           		adj[i] = new LinkedList();
		
		for(int i = 0; i < V; i++){
		    for(int j = 0; j < V; j++){
                if(grafoMatriz.getGrafo()[i][j] == 1){
		    		adj[i].add(j);
                }
			}
		}
	}
        
        public void imprimeGrafoB(){
            for(int i = 0; i < V; i++){
                for(int j = 0; j < adj[i].size(); j++){
                    System.out.print(adj[i].get(j) + " -> ");
                }
                System.out.println();
            }
        }
        
        private void adicionarClausula(int val){
            boolean flag = false;
            
            for(int i = 0; i < clAdd.size() && !flag; i++){
                if(clAdd.get(i) == val)
                    flag = true;
            }
            
            if(!flag){
                clAdd.add(val);
            }
        }
        
        private void salvarClausula(){
            for(int i = 0; i < clAdd.size(); i++){
                arq.escreverArquivo(diretorio, Integer.toString(clAdd.get(i)) + " ");
                
            }
            arq.escreverArquivo(diretorio, ", ");
            clAdd.clear();
        }

	private void BCCUtil(int u, int disc[], int low[], LinkedList<Edge>st,int parent[]){
 
        // Initialize discovery time and low value
        disc[u] = low[u] = ++time;
        int children = 0;
 
        // Go through all vertices adjacent to this
        Iterator<Integer> it = adj[u].iterator();
        while (it.hasNext())
        {
            int v = it.next();  // v is current adjacent of 'u'
 
            // If v is not visited yet, then recur for it
            if (disc[v] == -1)
            {
                children++;
                parent[v] = u;
 
                // store the edge in stack
                st.add(new Edge(u,v));
                BCCUtil(v, disc, low, st, parent);
 
                // Check if the subtree rooted with 'v' has a
                // connection to one of the ancestors of 'u'
                // Case 1 -- per Strongly Connected Components Article
                if (low[u] > low[v])
                    low[u] = low[v];
 
                // If u is an articulation point,
                // pop all edges from stack till u -- v
                if ( (disc[u] == 1 && children > 1) ||
                        (disc[u] > 1 && low[v] >= disc[u]) )
                {
                    while (st.getLast().u != u || st.getLast().v != v)
                    {
                        adicionarClausula(st.getLast().u);
                        adicionarClausula(st.getLast().v);  
                        
                        st.removeLast();                                      

                    }
                    
                    adicionarClausula(st.getLast().u);
                    adicionarClausula(st.getLast().v); 
                    salvarClausula();

                    st.removeLast();
 
                    count++;
                }
            }
 
            // Update low value of 'u' only of 'v' is still in stack
            // (i.e. it's a back edge, not cross edge).
            // Case 2 -- per Strongly Connected Components Article
            else if (v != parent[u] && disc[v] < low[u])
            {
                if (low[u]>disc[v])
                    low[u]=disc[v];
                st.add(new Edge(u,v));
            }
        }
    }
 
    // The function to do DFS traversal. It uses BCCUtil()
    private void BCC()
    {
        int disc[] = new int[V];
        int low[] = new int[V];
        int parent[] = new int[V];
        LinkedList<Edge> st = new LinkedList<Edge>();
 
        // Initialize disc and low, and parent arrays
        for (int i = 0; i < V; i++)
        {
            disc[i] = -1;
            low[i] = -1;
            parent[i] = -1;
        }
 
        for (int i = 0; i < V; i++)
        {
            if (disc[i] == -1)
                BCCUtil(i, disc, low, st, parent);
 
            int j = 0;
 
            // If stack is not empty, pop all edges from stack
            while (st.size() > 0)
            {
                
                j = 1;
                adicionarClausula(st.getLast().u);
                adicionarClausula(st.getLast().v); 

                st.removeLast();

            }
            if (j == 1)
            {
                salvarClausula();
                count++;
            }
        }
    }
}

