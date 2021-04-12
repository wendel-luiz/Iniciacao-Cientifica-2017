package iniciação.científica;


public class TabelaVerdade {
    private int[] bin;
    private int tam;
    private int aux;
    
    public TabelaVerdade(int tam){
        this.tam = tam;
        aux = tam;
        bin = new int[tam];
    }
   
    public void setBin(int n){
        if(n > 0){
            aux--;
            setBin(n/2);
            bin[aux++] = n % 2;
        }     
    } 
    
    public boolean compara(int[] clausula){
        int i = 0;
   
        while(i < clausula.length){
            if(clausula[i] > 0){
                if(bin[clausula[i]-1] == 1)
                    return true;
            }else{
                if(bin[(clausula[i]*-1)-1] == 0)
                    return true;
            }
            i++;
        }
        return false;
    }
    
    public void zerarBinario(){
        bin = new int[tam];
    }
    
    public void imprime(){
        for(int i = 0; i < tam; i++)
            System.out.print(bin[i]);
        System.out.println();
    }
}
