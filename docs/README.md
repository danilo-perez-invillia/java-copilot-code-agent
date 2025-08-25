# Sistema de Gestão Escolar - Mergington High School

Sistema de gestão de atividades extracurriculares desenvolvido com Spring Boot e arquitetura limpa (Clean Architecture).

## 📋 Visão Geral

O **School Management System** é uma aplicação web que permite o gerenciamento de atividades extracurriculares da Mergington High School. O sistema possibilita que professores administrem atividades e que estudantes se inscrevam nelas através de uma interface web intuitiva.

## 🏗️ Arquitetura

### Princípios Arquiteturais

- **Clean Architecture**: Separação clara entre camadas de domínio, aplicação, infraestrutura e apresentação
- **Domain-Driven Design (DDD)**: Modelagem focada no domínio escolar
- **SOLID Principles**: Código bem estruturado e extensível
- **Hexagonal Architecture**: Isolamento das regras de negócio

### Estrutura de Camadas

```text
src/main/java/com/mergingtonhigh/schoolmanagement/
├── domain/                    # 🎯 Camada de Domínio
│   ├── entities/             # Entidades principais
│   │   ├── Activity.java     # Atividade extracurricular
│   │   └── Teacher.java      # Professor/Administrador
│   ├── repositories/         # Interfaces de repositório
│   │   ├── ActivityRepository.java
│   │   └── TeacherRepository.java
│   └── valueobjects/         # Objetos de valor
│       ├── ActivityType.java # Tipos de atividade (enum)
│       ├── Email.java        # Validação de email
│       └── ScheduleDetails.java # Detalhes de horário
├── application/              # 🔧 Camada de Aplicação
│   ├── dtos/                 # Data Transfer Objects
│   │   ├── ActivityDTO.java
│   │   ├── ActivityTypeDTO.java
│   │   ├── LoginRequestDTO.java
│   │   ├── StudentRegistrationDTO.java
│   │   └── TeacherDTO.java
│   └── usecases/             # Casos de uso
│       ├── ActivityUseCase.java
│       ├── AuthenticationUseCase.java
│       └── StudentRegistrationUseCase.java
├── infrastructure/           # 🏭 Camada de Infraestrutura
│   ├── config/               # Configurações
│   │   ├── SecurityConfig.java
│   │   └── WebConfig.java
│   ├── migrations/           # Migrações do banco
│   │   └── V001_InitialDatabaseSetup.java
│   └── persistence/          # Implementações de repositório
│       ├── ActivityRepositoryImpl.java
│       ├── MongoActivityRepository.java
│       ├── MongoTeacherRepository.java
│       └── TeacherRepositoryImpl.java
└── presentation/             # 🎨 Camada de Apresentação
    ├── controllers/          # Controllers REST
    │   ├── ActivityController.java
    │   ├── AuthController.java
    │   └── StaticController.java
    └── mappers/              # Mapeadores DTO ↔ Entity
        ├── ActivityMapper.java
        └── TeacherMapper.java
```

## 🚀 Tecnologias Utilizadas

### Backend

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.4** - Framework principal
- **Spring Data MongoDB** - Integração com MongoDB
- **Spring Security** - Autenticação e autorização
- **Spring Web** - APIs REST
- **Spring Validation** - Validação de dados
- **Mongock 5.5.1** - Migrações do banco de dados
- **BouncyCastle** - Criptografia para senhas

### Frontend

- **HTML5/CSS3/JavaScript** - Interface web
- **Vanilla JavaScript** - Interatividade do frontend

### Banco de Dados

- **MongoDB** - Banco de dados NoSQL

### Ferramentas de Desenvolvimento

- **Maven** - Gerenciamento de dependências
- **JUnit 5** - Testes unitários
- **Mockito** - Mocks para testes
- **Testcontainers** - Testes de integração
- **Jacoco** - Cobertura de testes

## 📦 Funcionalidades Principais

### 🎓 Gestão de Atividades

- **Listagem de atividades** com filtros por:
  - Dia da semana
  - Horário (manhã, tarde, fim de semana)
  - Categoria (esportes, artes, acadêmico, etc.)
- **Detalhes de atividades**:
  - Nome e descrição
  - Horários e dias da semana
  - Capacidade máxima
  - Lista de participantes

### 👨‍🏫 Sistema de Autenticação

- **Login de professores** com username/senha via endpoint REST
- **Controle de acesso** baseado em roles (TEACHER/ADMIN)
- **Verificação de sessão** por username
- **Autenticação requerida** para inscrições e operações administrativas
- **Criptografia segura** com Argon2 para senhas

### 📝 Gestão de Inscrições

- **Inscrição de estudantes** em atividades
- **Cancelamento de inscrições**
- **Validações**:
  - Capacidade máxima
  - Duplicatas
  - Autenticação do professor

### 🎨 Interface Web

- **Design responsivo** e intuitivo com CSS moderno
- **Filtros dinâmicos** para busca de atividades por categoria e horário
- **Sistema de login** com modal interativo
- **Feedback visual** para ações do usuário e estados de carregamento
- **Categorização automática** de atividades com cores e ícones
- **Gestão de capacidade** com indicadores visuais de vagas disponíveis

## 🔧 Configuração e Execução

### Pré-requisitos

- Java 21
- Maven 3.8+
- MongoDB 4.4+

### Configurações do Sistema

#### Segurança (SecurityConfig.java)
- **Criptografia de senhas**: Argon2PasswordEncoder configurado para compatibilidade com Spring Security
- **Autenticação permissiva**: Configuração temporária permite acesso a todos endpoints (`permitAll()`)
- **CSRF desabilitado**: Para facilitar desenvolvimento e testes com formulários

#### Web (WebConfig.java)
- **Recursos estáticos**: Configuração para servir arquivos CSS, JS e HTML da pasta `/static`
- **Mapeamento de rotas**: Configuração de handlers para recursos estáticos

### Variáveis de Ambiente

Crie um arquivo `.env` baseado no `.env.example`

### Executando o Projeto

1. **Iniciar MongoDB**:

   ```bash
   # Docker
   docker run -d -p 27017:27017 --name mongodb mongo:latest
   
   # Ou MongoDB local
   mongod
   ```

2. **Compilar e executar**:

   ```bash
   # Compilar o projeto
   mvn clean compile
   
   # Executar os testes
   mvn test
   
   # Iniciar a aplicação
   mvn spring-boot:run
   ```

3. **Acessar a aplicação**:
   - Frontend: <http://localhost:8080>
   - API REST: <http://localhost:8080/activities>

### Tasks Maven Disponíveis

- `mvn clean install` - Build completo
- `mvn test` - Executar testes
- `mvn spring-boot:run` - Iniciar aplicação
- `mvn package -DskipTests` - Gerar JAR

## 🌐 API REST

### Endpoints Principais

#### Autenticação

```http
POST /auth/login
Content-Type: application/x-www-form-urlencoded

username=teacher.rodriguez&password=senha123

GET /auth/check-session?username=teacher.rodriguez
```

#### Atividades

```http
GET /activities
GET /activities?day=Monday&start_time=15:00&end_time=17:00
GET /activities/days
```

#### Inscrições

```http
POST /activities/{activityName}/signup
Content-Type: application/x-www-form-urlencoded

email=student@mergington.edu&teacher_username=teacher1

POST /activities/{activityName}/unregister
Content-Type: application/x-www-form-urlencoded

email=student@mergington.edu&teacher_username=teacher1
```

#### Recursos Estáticos

```http
GET /                        # Página principal
GET /static/*                # Recursos estáticos (CSS, JS, imagens)
```

## 🧪 Testes

### Estrutura de Testes

```text
src/test/java/
├── application/usecases/     # Testes de casos de uso
├── domain/entities/          # Testes de entidades
└── integration/             # Testes de integração
```

### Executar Testes

```bash
# Todos os testes
mvn test

# Testes específicos
mvn test -Dtest=ActivityTest
mvn test -Dtest=StudentRegistrationUseCaseTest

# Com cobertura
mvn jacoco:report
```

## 📊 Dados Iniciais

O sistema utiliza **Mongock** para realizar migrações automáticas do banco de dados, incluindo:

### Professores Padrão

- **principal** - Administrador principal (Diretor Martinez)
- **mrodriguez** - Professor de artes (Sr. Rodriguez)
- **mchen** - Professor de xadrez (Sra. Chen)

### Atividades Exemplo

- **Art Club** - Terças e quintas, 15:30-17:00
- **Chess Club** - Segundas e quartas, 15:30-17:00
- **Drama Club** - Quartas e sextas, 16:00-18:00
- **Science Olympiad** - Sábados, 09:00-12:00
- **Community Service** - Fins de semana, flexível

### Configuração de Senhas

As senhas dos usuários padrão podem ser configuradas via variáveis de ambiente:
- `TEACHER_RODRIGUEZ_PASSWORD` (padrão: "art123")
- `TEACHER_CHEN_PASSWORD` (padrão: "chess123")  
- `PRINCIPAL_PASSWORD` (padrão: "admin123")

## 🔒 Segurança

- **Sistema de autenticação customizado** para professores via endpoints REST
- **Criptografia Argon2** para senhas (compatível com BouncyCastle)
- **Validação de dados** em todas as camadas do sistema
- **CORS** configurado para desenvolvimento (origins: "*")
- **Configuração de segurança flexível** (Spring Security com acesso permitido para desenvolvimento)
- **Autenticação baseada em sessão simples** via username/password

## 📈 Monitoramento

- **Spring Actuator** - Métricas da aplicação
- **Logs estruturados** - Nível DEBUG para desenvolvimento
- **Health checks** - Status da aplicação e banco

## 🚀 Deploy

### Perfis de Ambiente

- **dev** - Ambiente de desenvolvimento
