package iniciação.científica;

public class Dir {
    public static String nomeSaidaBruta = "saidaProcessamentoBruto.txt";
    
    public static String pastaPsat = "/home/wendel/Documents/IC/Arquivos/";
    public static String pastaSaida = "/home/wendel/Documents/IC/Resultados/";
    public static String lP = "/home/wendel/Documents/IC/LpSolve/lp.lp";
    public static String lp_solve = "/home/wendel/Documents/IC/LpSolve/lp_solve -S0 /home/wendel/Documents/IC/LpSolve/lp.lp"
    public static String biconexo = "/home/wendel/Documents/IC/Resultados/Biconexo.txt";
    public static String psatTemp = "/home/wendel/Documents/IC/LpSolve/psatTemp.txt";
    
    public static String nomeBiconexo = "/bicon.txt";
    public static String pathArquivosPasta = "/home/wendel/Documents/IC/Arquivos";
    public static String nomeLpSolve = "/lp_solve";
    public static String pathLpSolvePasta = "/home/wendel/Documents/IC/LpSolve";
    public static String nomeLp = "/lp.lp";
    public static String pathPastaTemp = "/home/wendel/Documents/IC/temp";
    public static String nomeNovoPsat = "/novoPsat.txt";
    
    public static String nomePsat(int n1, int n2, int n3, int n4){
        return "PSAT_" + n1 + "_VAR_" + n2 + "_CLAU_" + n3 + "_VARpCLAU_" + n4 + ".txt";
    }
}
