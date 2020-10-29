package com.ufrj.lista4;

import java.io.Serializable;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *  Classe para ser o estado ser salvo como objeto de todos os dasos dos arquivos e adicionados pelos usuarios
 * @author Miguel
 */
final class File implements Serializable{
    
    //private String file;
    private TreeMap<Integer, Emprestimo> emprestimosTreeMap = new TreeMap<>();
    private TreeMap<Integer,Books> booksTreeMap = new TreeMap<>();
    private TreeMap<Integer,Estudante> studentsTreeMap = new TreeMap<>();
    private HashMap<Integer,String> typesHashMap = new HashMap<>();
    private TreeMap<Integer,Autor> authorsTreeMap = new TreeMap<>();
    
    
    public File () {
        //this.file = file;
        //this.emprestimosMap = new LinkedHashMap<>();  
        //this.booksHashMap = new HashMap<>();
        geraMapAuthors();
        geraMapStudents();
        geraMapBooks();
        geraMapBorrows();
        geraMapTypes();
        
    }
                
    
    
    /**
     * Gera TreeMap dos dados dos autores
     */
    public void geraMapAuthors(){
        try  {
            Scanner fileReader = new Scanner(Paths.get("authorsFull.csv"));
            String header = fileReader.nextLine();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();                        
                String[] parts = line.split(",");
                
                if (parts[0] == null) {
                    System.out.println("Invalid number");
                }else {
                    int authorId = Integer.valueOf(parts[0].replace("\"", ""));

                    //System.out.println(authorId + " " + parts[1].replace("\"", "").trim() +" " +parts[2].replace("\"", "").trim() );
                    Autor autor = new Autor(parts[1].replace("\"", "").trim(),parts[2].replace("\"", "").trim());
                    this.authorsTreeMap.put(authorId,autor);
                }
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }   
    }
    /**
     * Gera TreeMap dos  dados dos tipos literarios
     */
    public void geraMapTypes(){
        try  {
            Scanner fileReader = new Scanner(Paths.get("types.csv"));
            String header = fileReader.nextLine();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();                        
                String[] parts = line.split(",");
                
                if (parts[0] == null) {
                    System.out.println("Invalid number");
                }else {
                    int typeId = Integer.valueOf(parts[0].replace("\"", ""));
                    this.typesHashMap.put(typeId,parts[1]);
                }
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }   
    }
    /**
     * Gera TreeMap dos dados dos emprestimos
     */
    public void geraMapBorrows(){
       try  {
            Scanner fileReader = new Scanner(Paths.get("borrows.csv"));
            String header = fileReader.nextLine();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();                        
                String[] parts = line.split(",");
                String takenDate = parts[3];
                String broughtDate = parts[4];
                
                
                if (parts[0] == null || parts[1] == null || parts[1] == null || parts[2] == null) {
                    System.out.println("Invalid number");
                }else {
                    int borrowId = Integer.valueOf(parts[0].replace("\"", ""));
                    int studentId = Integer.valueOf(parts[1].replace("\"", ""));
                    int bookId = Integer.valueOf(parts[2].replace("\"", ""));
                    
                    //Adiciona livro emprestado ao estudante e livros
                    this.studentsTreeMap.get(studentId).totalEmprestimo();
                    this.booksTreeMap.get(bookId).totalEmprestimo();
                    int authorId = this.booksTreeMap.get(bookId).getAuthorId();
                    this.authorsTreeMap.get(authorId).totalEmprestimo();
                    
                    Emprestimo emprestimo = new Emprestimo(studentId,bookId,takenDate,broughtDate);
                    this.emprestimosTreeMap.put(borrowId,emprestimo);
                }
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }   
    }
    /**
     * Gera TreeMap dos dados dos livros
     */
    public void geraMapBooks() {
        try  {
            Scanner fileReader = new Scanner(Paths.get("books.csv"));
            String header = fileReader.nextLine();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                String[] parts2 = line.split("\"");
                String nome = parts2[3];
                
                
                if (parts[0] == null || parts2[5] == null || parts2[7] == null || parts2[9] == null || parts2[11] == null) {
                    System.out.println("Invalid number");
                }else {
                    int bookId = Integer.valueOf(parts[0].replace("\"", ""));
                    int pageCount = Integer.valueOf(parts2[5].replace("\"", ""));
                    int point = Integer.valueOf(parts2[7].replace("\"", ""));
                    int authorId = Integer.valueOf(parts2[9].replace("\"", ""));
                    int typeId = Integer.valueOf(parts2[11].replace("\"", ""));
                    Books books = new Books("\"" + nome + "\"",pageCount,authorId,typeId);
                    booksTreeMap.put(bookId, books);
                }
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    /**
     * Gera TreeMap dos dados dos estudantes
     */
    public void geraMapStudents() {
        try  {
            Scanner fileReader = new Scanner(Paths.get("students.csv"));
            String header = fileReader.nextLine();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                
                if (parts[0] == null || parts[6] == null) {
                    System.out.println("Invalid number");
                }else {
                    int studentId = Integer.valueOf(parts[0].replace("\"", ""));
                    int point = Integer.valueOf(parts[6].replace("\"", ""));
                    Estudante estudante = new Estudante(parts[1],parts[2],parts[3],parts[5],parts[4].toUpperCase());
                    studentsTreeMap.put(studentId,estudante);
                }
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }   
    }
    
    
    
    
    
    /**
     * 
     * @return HashMap dos tipos literarios
     */
    public HashMap<Integer,String> getMapTypes() {
        return this.typesHashMap;
    }
    /**
     * 
     * @return TreeMap dos dados dos estudantes
     */
    public TreeMap<Integer,Estudante> getMapStudents() {
        return this.studentsTreeMap;
    }
    /**
     * 
     * @return TreeMap dos dados dos livros
     */
    public TreeMap<Integer,Books> getMapBooks() {
        return this.booksTreeMap;
    }
    /**
     * 
     * @return TreeMap dos dados dos emprestimos
     */
    public TreeMap<Integer,Emprestimo> getMapEmprestimos() {
        return this.emprestimosTreeMap;
    }
    /**
     * 
     * @return TreeMap dos dados dos autores
     */
    public TreeMap<Integer,Autor> getMapAuthors() {
        return this.authorsTreeMap;
    }
    
    
    
    
    
    
    
    /**
     * Adiciona estudante ao TreeMap
     * @param nome nome do estudante
     * @param surname sobrenome do estudante
     * @param birthdate aniversario do estudante
     * @param gender sexo do estudante
     * @param classe classe do estudante
     */
    public void addStudent(String nome,String surname,String birthdate,String gender,String classe) {
        int key = this.studentsTreeMap.lastKey() + 1;
        String tempName = "\"" + nome.substring(0, 1).toUpperCase() + nome.substring(1) + "\"";
        String tempSurname = "\"" + surname.substring(0, 1).toUpperCase() + surname.substring(1) + "\"";
        Estudante estudante = new Estudante(tempName,tempSurname,birthdate,classe,gender);
        this.studentsTreeMap.put(key, estudante); 
        System.out.println("Estudante adicionado");
    }
    /**
     * Adiciona livro ao TreeMap
     * @param nome nome do livro
     * @param pageCount paginas do livro
     * @param authorId id do autor
     * @param typeId id do tipo literario
     */
    public void addBook(String nome,int pageCount, int authorId, int typeId) {
        
        int key = this.booksTreeMap.lastKey() + 1;
        int count = 0;
        String parts[] = nome.split(" ");
        StringBuilder temp = new StringBuilder();
        
        for (String str : parts) {
            if (count == parts.length - 1) {
               temp.append(str.substring(0,1).toUpperCase() + str.substring(1));
               break;
            }
            temp.append(str.substring(0,1).toUpperCase() + str.substring(1) + " ");
            count ++;
        }
        Books book = new Books("\"" + temp.toString() + "\"",pageCount,authorId,typeId);
        this.booksTreeMap.put(key,book);
        System.out.println("Livro adicionado");
    }
    /**
     * Empresta livro se disponivel ao aluno
     * @param studentId id do estudante
     * @param bookId id do livro
     */
    public void emprestarLivro(int studentId,int bookId){
        int borrowId = this.emprestimosTreeMap.lastKey() + 1;
        //Pega data na hora do emprestimo
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime atual = LocalDateTime.now();
        String takenDate = formato.format(atual);
        Emprestimo emprestimo = new Emprestimo(studentId,bookId,"\"" + takenDate + "\"");
        this.emprestimosTreeMap.put(borrowId,emprestimo);
        this.studentsTreeMap.get(studentId).totalEmprestimo();
        this.booksTreeMap.get(bookId).totalEmprestimo();
        int authorId = this.booksTreeMap.get(bookId).getAuthorId();
        this.authorsTreeMap.get(authorId).totalEmprestimo();
        System.out.println("Livro emprestado com sucesso, numero do emprestimo: " + borrowId);
    }
    /**
     * Devolve o livro a biblioteca
     * @param borrowId id do emprestimo
     */
    public void devolverLivro(int borrowId) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime atual = LocalDateTime.now();
        String takenDate = formato.format(atual);
        this.emprestimosTreeMap.get(borrowId).broughtDate = "\"" + takenDate + "\"";
        System.out.println("Livro devolvido");
    }
    
    
    
    
    
    /**
     * Inner class para ser salvo junto o objeto File
     */
    public static class Estudante implements Serializable{
    private String name;
    private String surname;
    private String birthdate;
    private String gender;
    private String classe;
    private int point;
    private int emprestimos;
    private int totalEmprestimos = 0;
    private ArrayList<Integer> bookIds = new ArrayList<>();
    
    public Estudante(String nome, String surname, String birthdate, String classe, String gender) {
        this.name = nome;
        this.surname = surname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.classe = classe;
        this.point = point;
        this.emprestimos = 0;
    }
    
    //Getters
    public String getName(){
        return this.name;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getBirthdate(){
        return this.birthdate;
    }
    public String getClasse(){
        return this.classe;
    }
    public String getGender(){
        return this.gender;
    }
    public int getPoint(){
        return this.point;
    }
    public int getEmprestimos(){
        return this.emprestimos;
    }
    public int getTotalEmprestimo(){
        return this.totalEmprestimos;
    }
    /**
     * Pega livro emprestado
     * @param bookId id do livro
     * @return true se bem sucedido ou false caso o contrario 
     */
    public boolean pegarEmprestado(int bookId){
        if (!this.bookIds.contains(bookId)) {
            this.emprestimos ++;
            this.bookIds.add(bookId);
            return true;
        }
        return false;
    }
    /**
     * Devolve livro emprestado
     * @param bookId id do livro
     */
    public void devolverEmprestado(int bookId) {
        this.emprestimos --;
        if (this.bookIds.get(0) == bookId) {
            this.bookIds.remove(0);
            return;
        }
        this.bookIds.remove(1);
    }
    /**
     * Numero total que o livro foi emprestado
     */
    public void totalEmprestimo () {
        this.totalEmprestimos ++;
    }
}
    
    
    
    
    
    /**
     * Inner class para ser salvo junto o objeto File
     */
    public static class Books implements Serializable{
        private String name;
        private int pageCount;
        private int point;
        private int authorId;
        private int typeId;
        private int emprestimos = 0;
        private int totalEmprestimos = 0;
        private ArrayList<Integer> listaEspera = new ArrayList<>();
        
        public Books(String name, int pageCount, int authorId, int typeId) {
            this.name = name;
            this.pageCount = pageCount;
            this.point = point;
            this.authorId = authorId;
            this.typeId = typeId;
        }
        
        //Getters
        public String getName(){
            return this.name;
        }
        public int getPageCount(){
            return this.pageCount;
        }
        public int getPoint(){
            return this.point;
        }
        public int getAuthorId(){
            return this.authorId;
        }
        public int getTypeId(){
            return this.typeId;
        }
        public int getEmprestimos(){
            return this.emprestimos;
        }
        public int getPrimeiroFila() {
            return this.listaEspera.get(0);
        }
        public int getTotalEmprestimo() {
            return this.totalEmprestimos;
        }
        /**
         * Marca livro como emprestado
         */
        public void pegarEmprestado() {
            this.emprestimos ++;
        }
        /**
         * Total de vezes que o livro foi emprestado
         */
        public void totalEmprestimo() {
            this.totalEmprestimos++;
        }
        /**
         * Verifica se alguem na lista de espera
         * @return true se ha false caso o contrario
         */
        public boolean esperaLista() {
            if (this.listaEspera.isEmpty()) {
                return false;
            }
            return true;
        }
        /**
         * Devolve um dos livros
         */
        public void devolver() {
            this.emprestimos --;
        }
        /**
         * Adiciona aluno a lista de espera
         * @param studentId id do estudante
         */
        public void adicionarListaEspera(int studentId) {
            this.listaEspera.add(studentId);
        }
        /**
         * Remove estudante da lista
         */
        public void removerDaLista() {
            if (this.listaEspera.size() == 1) {
                this.listaEspera.clear();
                return;
            }
            this.listaEspera.remove(0);
        }
    }
    /**
     * Inner class para ser salvo junto o objeto File
     */
    public static class Emprestimo implements Serializable{
        private int studentId;
        private int bookId;
        private String takenDate;
        private String broughtDate;
      
        public Emprestimo(int studentId, int bookId, String takenDate,String broughtDate){
            this.studentId = studentId;
            this.bookId = bookId;
            this.takenDate = takenDate;
            this.broughtDate = broughtDate;
        }
        public Emprestimo(int studentId, int bookId, String takenDate){
            this.studentId = studentId;
            this.bookId = bookId;
            this.takenDate = takenDate;
        }
        
        //Getters
        public int getStudentId(){
            return this.studentId;
        }
        public int getBookId(){
            return this.bookId;
        }
        public String getTakenDate(){
            return this.takenDate;
        }
        public String getBroughtDate(){
            return this.broughtDate;
        }

    }
    /**
     * Inner class para ser salvo junto o objeto File
     */
    public static class Autor implements Serializable{
        
        private String name;
        private String surname;
        private int totalEmprestimos = 0;
        
        public Autor(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }
        
        public String getName(){
            return this.name + " " + this.surname;
        }
        public int getTotalEmprestimo() {
            return this.totalEmprestimos;
        }
        
        public void totalEmprestimo() {
            this.totalEmprestimos++;
        }
        
    }
    
    
}

