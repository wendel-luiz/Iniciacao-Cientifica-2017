package iniciação.científica;

public class ProgramaLinear {
    private String origem;
    private String destino;
    
    private int nVar;
    private int nClau;
    private int nVarpClau;
    
    private int[][] clausula;
    private double[] probabilidade;
    
    public ProgramaLinear(String origem, String destino){
        this.origem = origem;
        this.destino = destino;
        
        definirParametros();
        definirClausula();
        definirProbabilidade();

        processar();
    }
    
    private void processar(){
        Arquivo arq = new Arquivo();
        
        arq.gerarRestricoesBasicas(destino, nVar);
        
        for(int i = 0; i < nClau; i++){
            arq.gerarProgramaLinear(destino, clausula[i], probabilidade[i], nVar);
        }
    }

    private void definirParametros(){
        Arquivo arq = new Arquivo();
        int[] aux;
        
        aux = arq.retornaParametrosPsat(origem);
        
        nVar = aux[0];
        nClau = aux[1];
        nVarpClau = aux[2];
    }
    
    private void definirClausula(){
        Arquivo arq = new Arquivo();
        clausula = arq.retornaClausulas(origem, nClau, nVarpClau);
    }
    
    private void definirProbabilidade(){
        Arquivo arq = new Arquivo();
        probabilidade = arq.retornaProbabilidades(origem, nClau);
    }  
}

