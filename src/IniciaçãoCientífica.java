package iniciação.científica;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IniciaçãoCientífica {
    
    public static void main(String[] args) {
        
    }
    
    private void gerarNPsats(){
        int n1, n2, n3, n4, n5;
        
        Scanner input = new Scanner(System.in);
        Psat psat;
        
        System.out.println("Variáveis: ");
        n1 = input.nextInt();
        
        System.out.println("Cláusulas: ");
        n2 = input.nextInt();
        
        System.out.println("Variáveis por cláusula: ");
        n3 = input.nextInt();
        
        System.out.println("Quantidade de Psat's: ");
        n4 = input.nextInt();
        
        System.out.println("ID inicial dos Psat's: ");
        n5 = input.nextInt();
        
        for(int i = 0; i < n4; i++){
            psat = new Psat(n1, n2, n3, Dir.pastaPsat + Dir.nomePsat(i + n5, n1, n2, n3));
        }
    }
    
    private void processarTodosOsPsats(){
        Arquivo arq = new Arquivo();
        File arquivos[];
        File diretorio = new File(Dir.pastaPsat);
        long tempo;
        arquivos = diretorio.listFiles();
        
        arq.deletarArquivo(Dir.pastaSaida + Dir.nomeSaidaBruta);
        arq.criarArquivo(Dir.pastaSaida + Dir.nomeSaidaBruta);
        
        for(int i = 0; i < arquivos.length; i++){
            tempo = processarPsat(arquivos[i].getName());
            arq.escreverArquivo(Dir.pastaSaida + Dir.nomeSaidaBruta, arquivos[i].getName() + " " + tempo + "\n");
        }
    }
    
    private long processarPsat(String nome){
        Arquivo arq = new Arquivo();
        ProgramaLinear pl;
        Process p;
        
        long t1, t2;
        
        arq.deletarArquivo(Dir.lP);
        
        pl = new ProgramaLinear(Dir.pastaPsat + nome, Dir.lP);
        
        try {
            t1 = System.currentTimeMillis();
            p = Runtime.getRuntime().exec(Dir.lp_solve + " " + Dir.lP);
            p.waitFor();
            t2 = System.currentTimeMillis();
            
            return t2-t1;
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    private void gerarTodosOsComponentesBiconexos(){
        File arquivos[];
        File diretorio = new File(Dir.pastaPsat);
        Arquivo arq = new Arquivo();

        arquivos = diretorio.listFiles();
        arq.criarArquivo(Dir.Biconexo);        
        
        for(int i = 0; i < arquivos.length; i++{
            gerarComponenteBiconexo(arquivos[i].getName());   
        }
    }

    private void gerarComponenteBiconexo(String nome){
        Grafo g;
        ComponenteBiconexo bicon;        

        g = new Grafo(Dir.pastaPsat + nome);
        bicon = new ComponenteBiconexo(g, Dir.biconexo);             
    }
    
    private void reprocessarTodosPsat(){
        String nome;
        long temp;
        Arquivo arq = new Arquivo();
        File arquivos[];
        File diretorio = new File(Dir.pastaPsat);
        arquivos = diretorio.listFiles();

        arq.deletarArquivo(Dir.pastaSaida + "SaidaBicon.txt");
        arq.criarArquivo(Dir.pastaSaida + "SaidaBicon.txt");
        
        for(int i = 0; i < arquivos.lenght; i++){
            nome = arquivos[i].getNome();
            temp = reprocessarPsat(nome);
            arq.atualizarArquivo(Dir.pastaSaida + "SaidaBicon.txt", nome + " " + temp + "\n");
        }
    }
    
    private long reprocessarPsat(String nome){
        PsatCompBiconexo p = new PsatCompBiconexo(nome);
        return p.processar();
    } 
}
