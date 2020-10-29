package com.ufrj.lista4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
/**
 * Interface para a utilizacao das classes
 * @author Miguel
 */
final class UserInterface {
 
    private Scanner scan;

    public UserInterface(Scanner scan) {
        this.scan = scan;   
    }
    /**
     * <p> Metodo para o inicio pela qual todos os outros metodos 
     * serao chamados 
     * </p>
     */
    public void start() throws FileNotFoundException, IOException  {
        
        
        //Verifica se utiliza objeto gravado ou criar um novo objeto
        File file = null;
        while (true) {
        String programa = askUserInput("Deseja -continuar- ou começar uma -nova- Biblioteca?");
            
            if (programa.contains("continuar")) {
                file = objetoSalvo();
                break;
            } else if (programa.contains("nova")) {
                
                String confirm = askUserInput("Deseja começar um nova biblioteca(todos os dados serão apagados) digite -SIM- para apagar\n\n");
                if (confirm.equals("sim")) {
                    
                }else {
                    continue;
                }
                file = new File();    
                break;
            }
        }
        
        
        
        while (true) {
            
            System.out.println("\nComandos: 1-cadastrar estudante, 2-cadastrar livro, 3-emprestimo, 4-devolver,5- n ultimos emprestimos,6- emprestimos n dias \n"
                    + " 7-estudante com mais emprestimos, 8-livros mais emprestados,9- autores mais populares,10- estilos literarios mais populares e -SAIR-");
           
            String command = askUserInput("\n\nDigite um comando").toLowerCase();
 
            if (command.equals("teste")) {
                //"borrowId","studentId","bookId","takenDate","broughtDate"
                file.getMapAuthors().entrySet().forEach(entry->{
                System.out.println(entry.getKey() + " " + entry.getValue().getName());  
            });
            } else if (command.equals("1")) {
                cadastrarEstudante(file);
            } else if (command.equals("2")) {
                cadastrarLivro(file);
            } else if (command.equals("3")) {
                emprestarLivro(file);
            } else if (command.equals("4")) {
                devolverLivro(file);
            } else if (command.equals("5")) {
                nUltimosEmprestimos(file);
            } else if (command.equals("6")) {
                emprestimosNDias(file);
            } else if (command.equals("7")) {
                estudantesMaisEmprestimos(file);
            } else if (command.equals("8")) {
                livrosMaisEmprestimos(file);
            } else if (command.equals("9")) {
                autoresMaisEmprestimos(file);
            } else if (command.equals("10")) {
                
            } else if (command.equals("sair")) {
                salvarEstado(file);
                break;
            } else {
                System.out.println("Comando desconhecido!");
            }
        }
    }
    /**
     * <p> Metodo para Imprimir perguntas e receper as respostas
     * do usuario
     * </p>
     * @param prompt pergunta a ser impressa na tela
     * @return input com a resposta do usuario
     */
    public String askUserInput(String prompt) {
        System.out.print(prompt + " ");
        String input = scan.nextLine();
        return input.toLowerCase();
    }
    /**
     * <p> Metodo para receber e validar os numeros do usuario </p>
     * @param prompt pergunta a ser feita para o usuario
     * @return o numero digitado pelo usuario validado
     */
    public int askUserNum(String prompt) {
        
        int num = -1;

        while (num <=  0) {
            System.out.print(prompt + ":\t");
            String numInput = scan.nextLine();

            try {
                num = Integer.valueOf(numInput);
            } catch (NumberFormatException e) {
                num = -1;
            }
        }
        return num;
    }
    /**
     * Cadastra os livros
     * @param file objeto unico final a ser recebido para ser final ao sair do programa
     */
    public void cadastrarLivro(File file){
        String nome = askUserInput("Digite o nome do livro");
        int pageCount = askUserNum("Digite o numero de paginas");
        //int ponto = askUserNum("Digite a pontuação");
        int authorId = askUserNum("Digite o id do autor");
        //file.
        int typeId = askUserNum("Digite o id do estilo literario");
        file.addBook(nome,pageCount,authorId,typeId);
    }
    /**
     * Cadastra estudantes
     * @param file objeto unico final a ser recebido para ser final ao sair do programa
     */
    public void cadastrarEstudante(File file) {
        //addStudent(String nome,String surname,String birthdate,String gender,String classe,int point)
        String nome = askUserInput("Digite o nome do estudante");
        String surname = askUserInput("Digite o sobrenome do estudante");
        int dia = 0;
        while(dia < 1 || dia > 31){
            dia = askUserNum("Digite o dia");
        }
        int mes = 0;
        while(mes < 1 || mes > 12){
            mes = askUserNum("Digite o mes");
        }
        int ano = 0;
        while(ano < 0) {
            ano = askUserNum("Digite o ano");
        }
        String birthdate = "\"" + ano + "-" + mes + "-" + dia + "\"";
        String gender = askUserInput("Digite o genero F ou M");
        while(gender.length() > 1 ) {
            gender = askUserInput("Digite o genero F ou M");
        }
        String classe = askUserInput("Digite a classe");
        //int point = askUserNum("Digite a pontuacao");
        file.addStudent(nome,surname,birthdate,gender.toUpperCase(),classe);
    }
    /**
     * Empresta um livro de acordo com o id do estudante e verifica se o emprestimo pode ser efetuado 
     * @param file objeto unico final a ser recebido para ser salvo ao sair do programa
     */
    public void emprestarLivro(File file) {
        
        //Valida o id de estudante
        int studentId = askUserNum("Digite o id do estudante");
        while(file.getMapStudents().get(studentId) == null){
            studentId = askUserNum("Digite o id do estudante");
        }
        
        if (file.getMapStudents().get(studentId).getEmprestimos() < 2 ) {
            //Valida o id do livro
            int bookId = askUserNum("Digite o id do livro");
            while(file.getMapBooks().get(bookId) == null) {
                bookId = askUserNum("Digite o id do livro");
            }
            if (file.getMapBooks().get(bookId).getEmprestimos() == 2) {
                System.out.println("Livros ja emprestados, estudante entrara na lista de espera");
                file.getMapBooks().get(bookId).adicionarListaEspera(studentId);
                
                return;
            }
            if (!file.getMapStudents().get(studentId).pegarEmprestado(bookId)) {
                System.out.println("Estudante ja possui livro");
                return;
            }
            file.emprestarLivro(studentId, bookId);
            file.getMapStudents().get(studentId).pegarEmprestado(bookId);
            file.getMapBooks().get(bookId).pegarEmprestado();
        }
        
    }
    /**
     * Devolve o livro e verifica se ha alunos na fila para o emprestimo
     * @param file objeto unico final a ser recebido para ser salvo ao sair do programa
     */
    public void devolverLivro(File file) {
        
        //Validacao id do emprestimo
        int borrowId = askUserNum("Digite o id do emprestimo");
        while(file.getMapEmprestimos().get(borrowId) == null) {
            borrowId = askUserNum("Digite o id do emprestimo");
        }
        
        file.devolverLivro(borrowId);
        int studentId = file.getMapEmprestimos().get(borrowId).getStudentId();
        int bookId = file.getMapEmprestimos().get(borrowId).getBookId();
        file.getMapStudents().get(studentId).devolverEmprestado(bookId);
        file.getMapBooks().get(bookId).devolver();
        
        //Verifica se ha alguem na lista de espera e empresta o livro
        if (file.getMapBooks().get(bookId).esperaLista()) {
            
            int studentIdFila = file.getMapBooks().get(bookId).getPrimeiroFila();
            file.getMapBooks().get(bookId).removerDaLista();
            emprestarFila(file,studentIdFila,bookId);
            file.getMapBooks().get(bookId).pegarEmprestado();
            file.getMapBooks().get(bookId).devolver();
            //file.getMapBooks().get(bookId).removerDaLista();
        }
        
        //file.getMapBooks().get(bookId).devolverEmprestado();
    }
    /**
     * Empresta livro especificamente para alunos na lista de espera
     * @param file objeto unico final a ser recebido para ser salvo ao sair do programa
     * @param studentId id unico do estudante
     * @param bookId  id unico do livro
     */
    public void emprestarFila(File file, int studentId, int bookId) {
        
        if (file.getMapStudents().get(studentId).getEmprestimos() < 2 ) {
            
            if (!file.getMapStudents().get(studentId).pegarEmprestado(bookId)) {
                //Estudante ja possui livro
                return;
            }
            file.emprestarLivro(studentId, bookId);
            file.getMapStudents().get(studentId).pegarEmprestado(bookId);
            file.getMapBooks().get(bookId).pegarEmprestado();
            //file.getMapBooks().get(bookId).devolver();
        }
    }
    /**
     * retorna programa ao estado em que foi utilizado da ultima vez
     * @return objeto file salvo
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public File objetoSalvo() throws FileNotFoundException, IOException {
        
        File file = null;	
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ObjetoSalvo.ser"))) {
            file = (File)ois.readObject(); 
            return file;
        }
        catch (ClassNotFoundException e) {
            System.err.println("Problema ao abrir ou fechar o arquivo.");
        }
        return file;
    }
    /**
     * Salva o estado do programa
     * @param file objeto unico final a ser recebido para ser salvo ao sair do programa
     */
    public void salvarEstado(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ObjetoSalvo.ser"))){
           oos.writeObject(file);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    /**
     * Ultimos n emprestimos realizados
     * @param file objeto unico final a ser recebido para ser final ao sair do programa
     */
    public void nUltimosEmprestimos(File file) {
        
        int n = askUserNum("Digite quantos emprestimos deseja ver");
        Estatistica estatistica = new Estatistica(file.getMapEmprestimos(),file.getMapBooks(),file.getMapStudents(),file.getMapAuthors());
        
        estatistica.nUltimoEmprestimos(n);
    }
    /**
     * emprestimos nos n ultimos dias
     * @param file objeto unico final a ser recebido para ser final ao sair do programa
     */
    public void emprestimosNDias(File file) {
        Estatistica estatistica = new Estatistica(file.getMapEmprestimos(),file.getMapBooks(),file.getMapStudents(),file.getMapAuthors());
        estatistica.consultaEmprestimoEstado(true, 0);
    }
    /**
     * Estudantes com mais emprestimos
     * @param file objeto unico final a ser recebido para ser final ao sair do programa
     */
    public void estudantesMaisEmprestimos(File file) {
        
        int n = askUserNum("Digite o numero de estudantes com mais emprestimos que deseja ver");
        Estatistica estatistica = new Estatistica(file.getMapEmprestimos(),file.getMapBooks(),file.getMapStudents(),file.getMapAuthors());
        estatistica.estudenteMaisEmprestimos(n);
    }
    /**
     * Livros com mais emprestimos
     * @param file objeto unico final a ser recebido para ser final ao sair do programa
     */
    public void livrosMaisEmprestimos(File file) {
        
        int n = askUserNum("Digite o numero de livros com mais emprestimos que deseja ver");
        Estatistica estatistica = new Estatistica(file.getMapEmprestimos(),file.getMapBooks(),file.getMapStudents(),file.getMapAuthors());
        estatistica.livrosMaisEmprestimos(n);
    }
    /**
     * Autores com mais emprestimos
     * @param file objeto unico final a ser recebido para ser final ao sair do programa
     */
    public void autoresMaisEmprestimos(File file) {
        int n = askUserNum("Digite o numero de autores com mais emprestimos que deseja ver");
        Estatistica estatistica = new Estatistica(file.getMapEmprestimos(),file.getMapBooks(),file.getMapStudents(),file.getMapAuthors());
        estatistica.autoresMaisEmprestimos(n);
    }
    
 
}
    

