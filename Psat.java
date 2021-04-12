package iniciação.científica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

public class Psat {
    
    private int nVar;
    private int nClau;
    private int nVarpClau;
    
    // Diretorio da pasta ja com o nome e tipo do arquivo
    private String diretorio;
    
    public Psat(int nvar, int nclau, int nvarpclau, String diretorio){
        this.nVar = nvar;
        this.nClau = nclau;
        this.nVarpClau = nvarpclau;
        this.diretorio = diretorio;
        
        gerarPsatRandomico();
    }
    
    
    private void gerarPsatRandomico(){
        Random ran  = new Random();
        int[] clausula = new int[nVarpClau];
        boolean igual;
        int numero;
        
        File arq = new File(diretorio);
        try{
            FileWriter fw = new FileWriter(arq);
            BufferedWriter bf = new BufferedWriter(fw);
            
            DecimalFormat df = new DecimalFormat("#.######");
            
            bf.write(Integer.toString(nVar) + " " + Integer.toString(nClau) + " " + Integer.toString(nVarpClau) + "\n");
            
            for(int k = 0; k < nClau; k++){
                for(int i = 0; i < nVarpClau; i++){
                    do{
                        igual = false;
                        numero = ran.nextInt(nVar) + 1;
                        if(ran.nextBoolean())        
                            numero *= -1;

                        for(int j = 0; j < i; j++){
                            if(Math.abs(numero) == Math.abs(clausula[j])){
                                igual = true;
                                break;
                            }    
                        }
                    }while(igual);
                    clausula[i] = numero;
                }
                
                for(int i = 0; i < nVarpClau; i++){
                    bf.write(Integer.toString(clausula[i]));
                    bf.write(" ");
                }
                bf.write("= ");
                bf.write(df.format(ran.nextDouble()).replace(',', '.'));
                bf.write("\n");
            }
            bf.close();
                        
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
