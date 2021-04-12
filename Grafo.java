package iniciação.científica;

public class Grafo {
    private String diretorio;
    private int nVar;
    private int nClau;
    private int nVarpClau;
    private int[][] clausula;
    private int[][] grafo;
    private double[] probabilidade;
    
    public Grafo(String dir){
        diretorio = dir;
        lerCabecalho();
        lerClausulas();
        lerProbabilidades();
        gerarGrafo();
    }
    private void lerCabecalho(){
        Arquivo arquivo = new Arquivo();
        int[] dados;
        
        dados = arquivo.retornaParametrosPsat(diretorio);
        
        nVar = dados[0];
        nClau = dados[1];
        nVarpClau = dados[2];
    }
    
    private void lerClausulas(){
        Arquivo arquivo = new Arquivo();
        
        clausula = arquivo.retornaClausulas(diretorio, nClau, nVar);
    }
    
    private void lerProbabilidades(){
        Arquivo arquivo = new Arquivo();
        
        probabilidade = arquivo.retornaProbabilidades(diretorio, nClau);
    }
    
    private void gerarGrafo(){
        int cont = 0;
        grafo = new int[nClau][nClau];
        //boolean flag;
        
        for(int i = 0; i < nClau; i++){
            for(int j = 0; j < nClau; j++){
                grafo[i][j] = 0;
            }
        }
        
        for(int i = 0; i < nClau; i++){
            for(int j = 0; j < nClau; j++){
                if(i != j){
                    cont = 0;
                    //flag = false;
                    for(int k = 0; k < nVarpClau; k++){                        
                        for(int l = 0; l < nVarpClau; l++){
                            //if(Math.abs(clausula[i][k]) == Math.abs(clausula[j][l])){ ORIGINAL
                            if(clausula[i][k] == clausula[j][l]*(-1)){
                                cont++;
                               // if(clausula[i][k] == (clausula[j][l]*(-1)))
                                 //   flag = true;
                                //if(cont >= 2 && flag) ORIGINAL
                                if(cont >= 2)
                                    grafo[i][j] = 1;            
                            }
                        } 
                    }
                }
            }
        }
    }
    
    public double[] getProbabilidade() {
        return probabilidade;
    }
    
    public String getDiretorio() {
        return diretorio;
    }

    public int getnVar() {
        return nVar;
    }

    public int getnClau() {
        return nClau;
    }

    public int getnVarpClau() {
        return nVarpClau;
    }

    public int[][] getClausula() {
        return clausula;
    }

    public int[][] getGrafo() {
        return grafo;
    }
    
    public void imprimirClausulas(){
        System.out.println();
        for(int i = 0; i < nVarpClau; i++){
            for(int j = 0; j < nVarpClau; j++){
                System.out.print(clausula[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public void imprimir(){
        for(int i = 0; i < nClau; i++){
            for(int j = 0; j < nClau; j++){
                System.out.print(grafo[i][j] + " ");
            }
            System.out.println();
        }
    }
}
