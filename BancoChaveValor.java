import java.util.Scanner;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class BancoChaveValor {


    private static final String NO_RAIZ = "BancoChaveValor";
    private Preferences dados;

    public BancoChaveValor() {
        dados = Preferences.userRoot().node(NO_RAIZ);
    }

    // PUT - cadastra ou atualiza um par chave-valor
    public void cadastrar(String chave, String valor) {
        dados.put(chave, valor);
        sincronizar();
    }

    // GET - consulta o valor associado a uma chave
    public String consultar(String chave) {
        return dados.get(chave, null);
    }

    // DELETE - exclui um par chave-valor
    public boolean excluir(String chave) {
        boolean existia = consultar(chave) != null;
        if (existia) {
            dados.remove(chave);
            sincronizar();
        }
        return existia;
    }

    public void listarTodos() {
        try {
            String[] chaves = dados.keys();
            if (chaves.length == 0) {
                System.out.println("Banco vazio.");
                return;
            }
            for (String chave : chaves) {
                System.out.println(chave + " = " + dados.get(chave, null));
            }
        } catch (BackingStoreException e) {
            System.out.println("Erro ao listar o banco: " + e.getMessage());
        }
    }

    // Garante que as alteracoes sejam persistidas imediatamente
    private void sincronizar() {
        try {
            dados.flush();
        } catch (BackingStoreException e) {
            System.out.println("Erro ao persistir o banco: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BancoChaveValor banco = new BancoChaveValor();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println();
            System.out.println("=== Banco de Dados Chave-Valor ===");
            System.out.println("1 - Cadastrar (PUT)");
            System.out.println("2 - Consultar (GET)");
            System.out.println("3 - Excluir (DELETE)");
            System.out.println("4 - Listar todos");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1:
                    System.out.print("Chave: ");
                    String chaveCadastro = scanner.nextLine();
                    System.out.print("Valor: ");
                    String valorCadastro = scanner.nextLine();
                    banco.cadastrar(chaveCadastro, valorCadastro);
                    System.out.println("Registro cadastrado com sucesso.");
                    break;

                case 2:
                    System.out.print("Chave: ");
                    String chaveConsulta = scanner.nextLine();
                    String resultado = banco.consultar(chaveConsulta);
                    if (resultado != null) {
                        System.out.println("Valor: " + resultado);
                    } else {
                        System.out.println("Chave nao encontrada.");
                    }
                    break;

                case 3:
                    System.out.print("Chave: ");
                    String chaveExclusao = scanner.nextLine();
                    if (banco.excluir(chaveExclusao)) {
                        System.out.println("Registro excluido com sucesso.");
                    } else {
                        System.out.println("Chave nao encontrada.");
                    }
                    break;

                case 4:
                    banco.listarTodos();
                    break;

                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static int lerInteiro(Scanner scanner) {
        try {
            int valor = Integer.parseInt(scanner.nextLine().trim());
            return valor;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
