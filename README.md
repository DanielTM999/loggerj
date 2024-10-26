# LoggerJ - Java Logging Framework

LoggerJ é um framework de logging desenvolvido em Java, que permite a gravação de logs no console e em arquivos com base nos diferentes níveis de severidade. A biblioteca é configurável, permitindo que você ajuste o nível de severidade mínima para registros e direcione o log para o console ou para um arquivo especificado.

## Classes e Estrutura

### 1. `LoggerJ` (Interface)
Define métodos para escrita de logs em diferentes níveis de severidade, aceitando mensagens de log com suporte a exceções e especificação de arquivo de saída.

#### Métodos:
- `write(String msg, LogType logType)`
- `write(String msg, LogType logType, String filePath)`
- `write(String msg, LogType logType, Throwable throwable)`
- `write(String msg, LogType logType, Throwable throwable, String filePath)`

### 2. `LogType` (Enum)
Define três tipos de log:
- `INFO` - Nível informativo, valor: 0
- `WARNING` - Avisos, valor: 1
- `ERROR` - Erros, valor: 2

### 3. `ConsoleLog` (Implementação de `LoggerJ`)
Escreve os logs diretamente no console e suporta configuração do nível de severidade mínima. Logs abaixo do nível especificado são ignorados.

### 4. `FileLog` (Implementação de `LoggerJ`)
Grava os logs em arquivos no sistema de arquivos. É possível especificar o diretório de saída e o nível de severidade mínima.

## Configuração

1. **ConsoleLog**: Configuração do nível de severidade mínima via `ConsoleLog(int nv)` ou `ConsoleLog(LogType nv)`.
2. **FileLog**: 
   - Configuração do nível de severidade e diretório de saída.
   - Especificação de diretório personalizado pelo método `setArea`.

## Exemplo de Uso

```java
    // Logger de Console
    ConsoleLog consoleLogger = new ConsoleLog(LogType.INFO); // <- indica o filtro qe irá ser mostrado
    consoleLogger.write("Teste de log informativo", LogType.INFO);

    // Logger de Arquivo
    FileLog fileLogger = new FileLog("logs");
    fileLogger.write("Log de teste em arquivo", LogType.WARNING);
```

## Funcionalidades

- **Multithreading**: Gravação de logs é feita em uma nova thread, evitando bloqueios.
- **Personalização de Diretórios**: Diretório de gravação dos logs pode ser especificado em `FileLog`.
- **Gravação de Exceções**: Suporte para log de exceções com mensagens detalhadas.

## Estrutura de Diretórios

```plaintext
    .
    ├── dtm
    │   └── loggerj
    │       ├── core
    │       │   ├── LoggerJ.java
    │       │   └── LogType.java
    │       └── log
    │           ├── ConsoleLog.java
    │           └── FileLog.java

```