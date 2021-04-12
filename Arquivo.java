package iniciação.científica;

import java.io.*;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arquivo {
    
    public int[][] retornaClausulas(String diretorio, int nLinhas, int nColunas){
        String linha;
        String valor;
        int cont;
        int[][] matriz;
        File arq = new File(diretorio);
        
        matriz = new int[nLinhas][nColunas];
        
        try {
            FileReader fr = new FileReader(arq);
            BufferedReader br = new BufferedReader(fr);
            
            linha = br.readLine();
            
            for(int i = 0; i < nLinhas; i++){
                cont = 0;
                valor = "";
                
                linha = br.readLine();
                
                for(int j = 0; j < linha.length(); j++){ 
                    if(linha.charAt(j) == ' '){
                        matriz[i][cont++] = Integer.parseInt(valor);
                        valor = "";
                    }else{
                        if(linha.charAt(j) == '=')
                            break;
                        valor += linha.charAt(j);
                    }  
                }
            }
            
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
        
        return matriz;
    }
    
    public int[] retornaParametrosPsat(String diretorio){
        String linha;
        String valor;
        int[] dados = new int[3];
        int cont = 0;
        
        File arq = new File(diretorio);
        
        try {
            FileReader fr = new FileReader(arq);
            BufferedReader br = new BufferedReader(fr);
            
            linha = br.readLine();
            
            valor = "";
            for(int i = 0; i < linha.length(); i++){
                if(linha.charAt(i) == ' '){
                    dados[cont++] = Integer.parseInt(valor);
                    valor = "";
                }else{
                    valor += linha.charAt(i);
                }
            }
            dados[cont] = Integer.parseInt(valor);
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
        
        return dados;
    }
    
    public double[] retornaProbabilidades(String diretorio, int nClau){
        double[] aux = new double[nClau];
        String linha;
        String valor;
        
        File arq = new File(diretorio);
        
        try {
            FileReader fr = new FileReader(arq);
            BufferedReader br = new BufferedReader(fr);
            
            linha = br.readLine();
            
            for(int i = 0; i < nClau; i++){
                linha = br.readLine();
                valor = "";
                for(int j = 0; j < linha.length(); j++){
                    if(linha.charAt(j) == '='){
                        for(int k = j+1; k < linha.length(); k++){
                            valor += linha.charAt(k);
                        }
                        aux[i] = Double.parseDouble(valor);
                        break;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return aux;
    }
    
    public void gerarProgramaLinear(String diretorio, int[] clausula, double probabilidade, int nVar){
        int i = 0;
        int ultimo = (int)Math.pow(2, nVar)-1;
        boolean naoAchou = true;
        
        TabelaVerdade bin = new TabelaVerdade(nVar);
        DecimalFormat df = new DecimalFormat("#.######");

        while(naoAchou){
            bin.zerarBinario();
            bin.setBin(ultimo);
            
            if(bin.compara(clausula))
                naoAchou = false;
            else
                ultimo--;
        }
        
        File arq = new File(diretorio);
        
        
        try {
            FileWriter fr = new FileWriter(arq, true);
            BufferedWriter br = new BufferedWriter(fr);
            
            bin.zerarBinario();
            while(i < ultimo){
                bin.setBin(i);
                if(bin.compara(clausula)){
                    br.write("x" + i + " + ");
                }  
                i++;
            }
            
            br.write("x" + ultimo + " = " + df.format(probabilidade).replace(',', '.') + ";" + "\n");
            br.close();
 
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
    
    public void gerarRestricoesBasicas(String diretorio, int nVar){
        File arq = new File(diretorio);
        try {
            FileWriter fr = new FileWriter(arq, true);
            BufferedWriter br = new BufferedWriter(fr);
            
            br.write("min: 0;\n");
            
            for(int i = 0; i < (int)Math.pow(2, nVar)-1; i++){
                br.write("x" + i + " + ");
            }
            
            br.write("x" + ((int)Math.pow(2, nVar)-1) + " = 1;\n");
            
            for(int i = 0; i < (int)Math.pow(2, nVar); i++){
                br.write("x" + i + " >= 0;\n");
            }
            
            br.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void criarArquivo(String diretorio){
        File arq = new File(diretorio);
        
        try {
            arq.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void escreverArquivo(String diretorio, String dados){
        File arq = new File(diretorio);
        
        try{
            FileWriter fr = new FileWriter(arq, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write(dados);
            br.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void deletarArquivo(String dir){
        File arq = new File(dir);
        arq.delete();
    }
        
    public String retornaCompBiconexo(String nome, String pathBicon){
        String linha = "";
        
        File arq = new File(pathBicon);
        FileReader fr;
                
        try {
            fr = new FileReader(arq);
            BufferedReader br = new BufferedReader(fr);
            
            while((linha = br.readLine()) != null){
                String aux[];
                aux = linha.split(" ");
                
                if(nome.equals(aux[0])){
                    //Isso permite que o retorno da linha sejam apenas os componentes, sem o nome na frente.
                    linha = linha.substring(aux[0].length() + 1, linha.length());
                    break;
                }
            }
            return linha;
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         return linha;
    }
    
    public void criarNovoPsat(Integer[] linha, int maiorVar, int varpClau, String dirVelhoPsat, String dirNovoPsat){
        LinkedList<String> clausula = new LinkedList<String>();
        
        criarArquivo(dirNovoPsat);
        
        File arq = new File(dirVelhoPsat);
        File arq2 = new File(dirNovoPsat);
        
        try {
            
            
            int cont = 0;
            while(cont < linha.length){
                FileReader fr = new FileReader(arq);
                BufferedReader br = new BufferedReader(fr);
                
                br = new BufferedReader(fr);
                
                for(int i = 0; i <= linha[cont]; i++)
                    br.readLine();
                
                clausula.add(br.readLine());
                cont++;
                br.close();
            }
            
            FileWriter fw = new FileWriter(arq2);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(maiorVar + " " + linha.length + " " + varpClau + "\n");
            
            for(int i = 0; i < clausula.size(); i++){
                bw.write(clausula.get(i) + "\n");
            }
            
            bw.close();
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } 
}
