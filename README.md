# Banco de Dados Chave-Valor

Trabalho da disciplina de Banco de Dados: programa que cadastra, consulta e
exclui informações em um banco de dados chave-valor.

## Solução

Implementado em Java, utilizando a API `java.util.prefs.Preferences`, que é o
mecanismo de persistência chave-valor nativo do próprio JDK (grava em
`~/.java/.userPrefs` no Linux/Mac ou no Registro do Windows). A aplicação
apenas delega as operações de persistência a essa API (`put`, `get`,
`remove`, `keys`), sem implementar armazenamento manual em memória ou em
arquivo próprio.

## Operações

- **Cadastrar (PUT)** — grava ou atualiza um par chave-valor
- **Consultar (GET)** — recupera o valor associado a uma chave
- **Excluir (DELETE)** — remove um par chave-valor
- **Listar** — exibe todos os registros cadastrados

## Como executar

```bash
javac BancoChaveValor.java
java BancoChaveValor
```
