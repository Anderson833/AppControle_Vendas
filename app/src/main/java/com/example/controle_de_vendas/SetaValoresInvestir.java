package com.example.controle_de_vendas;

public class SetaValoresInvestir {

    private static int idInvest;
    private  static String nomeInvest;
    private static String dataInvest;
    private  static int quantidadeInvest;
    private static double valorRevendaInvest;
    private static double precoPagorInvest;
    private static double todoTotalInvest;
    private static String opcao;

    public static String getOpcao() {
        return opcao;
    }

    public static void setOpcao(String opcao) {
        SetaValoresInvestir.opcao = opcao;
    }

    public static int getIdInvest() {
        return idInvest;
    }

    public static void setIdInvest(int idInvest) {
        SetaValoresInvestir.idInvest = idInvest;
    }

    public static String getNomeInvest() {
        return nomeInvest;
    }

    public static void setNomeInvest(String nomeInvest) {
        SetaValoresInvestir.nomeInvest = nomeInvest;
    }

    public static String getDataInvest() {
        return dataInvest;
    }

    public static void setDataInvest(String dataInvest) {
        SetaValoresInvestir.dataInvest = dataInvest;
    }

    public static int getQuantidadeInvest() {
        return quantidadeInvest;
    }

    public static void setQuantidadeInvest(int quantidadeInvest) {
        SetaValoresInvestir.quantidadeInvest = quantidadeInvest;
    }

    public static double getValorRevendaInvest() {
        return valorRevendaInvest;
    }

    public static void setValorRevendaInvest(double valorRevendaInvest) {
        SetaValoresInvestir.valorRevendaInvest = valorRevendaInvest;
    }

    public static double getPrecoPagorInvest() {
        return precoPagorInvest;
    }

    public static void setPrecoPagorInvest(double precoPagorInvest) {
        SetaValoresInvestir.precoPagorInvest = precoPagorInvest;
    }

    public static double getTodoTotalInvest() {
        return todoTotalInvest;
    }

    public static void setTodoTotalInvest(double todoTotalInvest) {
        SetaValoresInvestir.todoTotalInvest = todoTotalInvest;
    }
}
