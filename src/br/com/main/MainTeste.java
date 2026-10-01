package main;

import exception.CapacidadeExcedidaException;
import java.util.HashSet;
import java.util.Set;
import model.Carga;
import model.ModuloCarga;

public class MainTeste {

    public static void main(String[] args) {

        // =========================================================================
        // PROTOCOLO DE TESTES DE ESTRESSE (CHAOS MONKEY - 4 ATAQUES)
        // =========================================================================

        // ATAQUE 1: Injeção de Carga com Dados Espúrios
        try {

            Carga fantasma = new Carga(
                    "    ",
                    "CRIOGENICA",
                    10.0,
                    500.0
            );

            System.out.println("❌ FALHA: Aceitou codigo vazio!");

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "✅ SUCESSO ATAQUE 1 (Fail-Fast ativado): "
                    + e.getMessage()
            );
        }

        // ATAQUE 2: Teste de Integridade Hash
        Set<Carga> esteiraTriagem = new HashSet<>();

        Carga c1 = new Carga(
                "ORB-999",
                "ALIMENTOS",
                10.0,
                100.0
        );

        Carga c1Clone = new Carga(
                "ORB-999",
                "DADOS_ALTERADOS",
                999.0,
                8000.0
        );

        esteiraTriagem.add(c1);
        esteiraTriagem.add(c1Clone);

        if (esteiraTriagem.size() == 1) {

            System.out.println(
                    "✅ SUCESSO ATAQUE 2 (Hash imune a clones): "
                    + "Tamanho do Set = 1"
            );

        } else {

            System.out.println(
                    "❌ FALHA: O clone furou a dispersão e entrou no Set! "
                    + "Tamanho = " + esteiraTriagem.size()
            );
        }

        // ATAQUE 3: Teste de Ruptura de Limite
        ModuloCarga moduloTeste = new ModuloCarga(
                "MOD-TESTE",
                2
        );

        try {

            moduloTeste.carregarCarga(
                    new Carga("C-01", "GERAL", 5.0, 50.0)
            );

            moduloTeste.carregarCarga(
                    new Carga("C-02", "GERAL", 5.0, 50.0)
            );

            moduloTeste.carregarCarga(
                    new Carga("C-03", "GERAL", 5.0, 50.0)
            );

            System.out.println(
                    "❌ FALHA: Deixou ultrapassar a capacidade fisica!"
            );

        } catch (CapacidadeExcedidaException e) {

            System.out.println(
                    "✅ SUCESSO ATAQUE 3 (Trava de Limite operante): "
                    + e.getMessage()
            );
        }

        // ATAQUE 4: Processamento Analítico Funcional
        double seguroCalculado = moduloTeste.calcularSeguroTotal();

        System.out.println(
                "✅ SUCESSO ATAQUE 4 (Streams Calculado): R$ "
                + seguroCalculado
        );
    }
}