package iniciação.científica;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PsatCompBiconexo {
    
    private Integer[] comp;
    private LinkedList<Integer[]> listaComp;
    private String nomePsat;
    private dados[];    

    public PsatCompBiconexo(String nome){
        listaComp = new LinkedList<Integer[]>();
        this.nomePsat = nome;
        dados = lerCabecalho();
    }

    private int[] lerCabecalho(){
        Arquivo arq = new Arquivo();
        return arq.retornaParametrosPsat(Dir.pastaPsat + nomePsat);
    }
    
    private void lerComponentes(){
        Arquivo arq = new Arquivo();
        Integer[] vet;
        String componente[];
        String biconexo;
        String arrayBicon[];
        
        biconexo = arq.retornaCompBiconexo(nomePsat, Dir.Biconexo);
        arrayBicon = biconexo.split(" , ");
        
        for(int i = 0; i < arrayBicon.length; i++){
            componente = arrayBicon[i].split(" ");
            vet = new Integer[componente.length];
            
            for(int j = 0; j < componente.length; j++){
                vet[j] = Integer.parseInt(componente[j]);
            }
            
            adicionarLista(vet);
        }  
    }
    
    private void adicionarLista(Integer[] comp){
        Integer c1[];
        int i = 0;
        
        while(i < listaComp.size()){
            
            c1 = listaComp.get(i);
            
            if(c1MaiorQueC2(c1, comp)){
                break;
            }
            i++;
        }
        
        listaComp.add(i, comp); 
    }
    
    private boolean c1MaiorQueC2(Integer[] c1, Integer[] c2){
        Integer maiorC1;
        Integer maiorC2;
        
        if(c1.length != c2.length){
            return c1.length > c2.length;
        }else{
            maiorC1 = c1[0];
            maiorC2 = c2[0];
            
            for(int i = 0; i < c1.length; i++){
                if(c1[i] > maiorC1)
                    maiorC1 = c1[i];
                
                if(c2[i] > maiorC2)
                    maiorC2 = c2[i];
            }
            
            return maiorC1 > maiorC2;
        }
    }
    
    private void setarComp(){
        int i = 0;
        boolean flag = false;
        while(i < listaComp.size()){
            if(temEmComumOuVazio(comp, listaComp.get(i))){
                recriarComp(listaComp.get(i));
                listaComp.remove(i);
                flag = true;
                break;
            }
            i++;
        }
        
        if(!flag){
            comp = null;
            recriarComp(listaComp.get(0));
            listaComp.removeFirst();
        }
    }
    
    private boolean temEmComumOuVazio(Integer[] c1, Integer[] c2){
        if(c1 == null || c2 == null)
            return true;
        
        for(int i = 0; i < c1.length; i++){
            for(int j = 0; j < c2.length; j++){
                if(c1[i] == c2[j])
                    return true;
            }
        }
        return false;
    }
    
    private void recriarComp(Integer[] c){
        if(comp == null){
            comp = c;
        }else{          
            LinkedList<Integer> aux = new LinkedList<Integer>();
            
            for(int i = 0; i < comp.length; i++){
                aux.add(comp[i]);
            }
            
            boolean flag;
            
            for(int i = 0; i < c.length; i++){
                flag = true;
                for(int j = 0; j < comp.length; j++){
                    if(comp[j] == c[i]){
                        flag = false;
                    }
                }
                if(flag){
                    aux.add(c[i]);
                }
            }
            
            comp = new Integer[aux.size()];
            
            for(int i = 0; i < aux.size(); i++)
                comp[i] = aux.get(i);
            
        }
    }

    private void criarNovoPsatComVariaveisOriginais(){
        Arquivo arq = new Arquivo();
        arq.criarNovoPsat(comp, dados[0], dados[2], Dir.pastaPsat + nomePsat, Dir.psatTemp);
    }
    
    
    private void deletarArquivos(){
        Arquivo arq = new Arquivo();
        arq.deletarArquivo(Dir.psatTemp);
        arq.deletarArquivo(Dir.lP);
    }
    
    private void imprimirComp(){
        for(int i = 0; i < comp.length; i++){
            System.out.print(comp[i] + " ");
        }
        System.out.println();
    }
    
    private void imprimirLista(){
        for(int i = 0; i < listaComp.size(); i++){
            for(int j = 0; j < listaComp.get(i).length; j++){
                System.out.print(listaComp.get(i)[j] + " ");
            }
            System.out.println();
        }
    }
    
    public long processar(){
        ProgramaLinear gl;
        Process p = null;
        
        long tInicial;
        long tFinal;
        long tTotal = 0;
        boolean satisfativel = true;
        boolean flag = false;

        lerComponentes();

        do{
            try {
                
                deletarArquivos();
                
                setarComp();
                criarNovoPsatComVariaveisOriginais();
                
                gl = new ProgramaLinear(Dir.psatTemp, Dir.lP);
                
                tInicial = System.currentTimeMillis();
                
                p = Runtime.getRuntime().exec(Dir.lp_solve);
               
                p.waitFor();
                
                tFinal = System.currentTimeMillis();
                
                tTotal += (tFinal - tInicial);
                
                if(p.exitValue() == 2){
                    satisfativel = false;
                }
                p.destroy();
                
                if(listaComp.isEmpty()){
                    flag = true;
                }
                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }while(satisfativel && !flag);
        
        if(flag){
            System.out.println(nomePsat + " ERROR");
            return -1;
        }
        
        System.out.println(nomePsat + " " + tTotal);
        
        return tTotal;
    }
}
