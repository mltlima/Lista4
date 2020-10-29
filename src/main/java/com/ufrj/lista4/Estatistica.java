/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufrj.lista4;

import com.ufrj.lista4.File.Autor;
import com.ufrj.lista4.File.Books;
import com.ufrj.lista4.File.Emprestimo;
import com.ufrj.lista4.File.Estudante;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TreeMap;


/**
 *  Classe para realizar todas as estatisticas dos dados dos TreeMaps
 * @author Miguel
 */
public class Estatistica {
    
    private TreeMap<Integer, Emprestimo> emprestimosTreeMap;
    private TreeMap<Integer,Books> booksTreeMap;
    private TreeMap<Integer,Estudante> studentsTreeMap;
    private TreeMap<Integer,Autor> authorsTreeMap;
    private TreeMap<Integer,String> estudantesMaisEmprestimos = new TreeMap<>();
    private TreeMap<Integer,String> booksMaisEmprestimos = new TreeMap<>();
    private TreeMap<Integer,String> authorsMaisEmprestimos = new TreeMap<>();
    
    public Estatistica(TreeMap<Integer, Emprestimo> emprestimosTreeMap,TreeMap<Integer,Books> booksTreeMap,TreeMap<Integer,Estudante> studentsTreeMap,TreeMap<Integer,Autor> authorsTreeMap){
        this.emprestimosTreeMap = emprestimosTreeMap;
        this.booksTreeMap = booksTreeMap;
        this.studentsTreeMap = studentsTreeMap;
        this.authorsTreeMap = authorsTreeMap;
    }
    /**
     * N ultimos livros emprestados
     * @param n n ultimo emprestimos
     */
    public void nUltimoEmprestimos(int n) {
        
        int count = 0;
        
        for (int key : this.emprestimosTreeMap.descendingKeySet()) {
            if (count == n) {
                break;
            }
            int studentId = this.emprestimosTreeMap.get(key).getStudentId();
            String nome = this.studentsTreeMap.get(studentId).getName() + " " + this.studentsTreeMap.get(studentId).getSurname();
            int bookId = this.emprestimosTreeMap.get(key).getBookId();
            String nomeBook = this.booksTreeMap.get(bookId).getName();
            String takenDate = this.emprestimosTreeMap.get(key).getTakenDate();
            String broughtDate = this.emprestimosTreeMap.get(key).getBroughtDate();
            System.out.println(key + "," + nome + "," + nomeBook + "," + takenDate + "," + broughtDate);
            count ++;
        }
    }
    /**
     * Verifica ultimos livros emprestados em aberto ou fechado
     * @param estado estado do emprestimo true se estiver aberto false se estiver fechado
     * @param n 
     */
    public void consultaEmprestimoEstado(boolean estado, int n) {
        
        for (int key : this.emprestimosTreeMap.descendingKeySet()) {
            
            String takenDate = this.emprestimosTreeMap.get(key).getTakenDate();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime comparar = LocalDateTime.parse(takenDate.replace("\"",""), formato);
            LocalDateTime atual = LocalDateTime.now();
            LocalDateTime temp = LocalDateTime.from(comparar);
            long compararDias = temp.until( atual, ChronoUnit.DAYS );
            
            if (compararDias > n) {
                //True aberto
                if (estado) {
                    if (this.emprestimosTreeMap.get(key).getBroughtDate() == null) {
                        printEmprestimo(key);
                    }
                } else {
                    if (this.emprestimosTreeMap.get(key).getBroughtDate() != null) {
                        printEmprestimo(key);
                    }
                }
            }
        }
    }
    /**
     * Realiza impressao dos dados a partir da chave
     * @param key chave para acessar e imprimir dados dos TreeMaps
     */
    public void printEmprestimo(int key) {
        int studentId = this.emprestimosTreeMap.get(key).getStudentId();
        String nome = this.studentsTreeMap.get(studentId).getName() + " " + this.studentsTreeMap.get(studentId).getSurname();
        int bookId = this.emprestimosTreeMap.get(key).getBookId();
        String nomeBook = this.booksTreeMap.get(bookId).getName();
        String takenDate = this.emprestimosTreeMap.get(key).getTakenDate();
        String broughtDate = this.emprestimosTreeMap.get(key).getBroughtDate();
        System.out.println(key + "," + nome + "," + nomeBook + "," + takenDate + "," + broughtDate);
    }
    /**
     * N estudantes com maiores quantidades de emprestimos
     * @param n quantidade de alunos com mais emprestimos
     */
    public void estudenteMaisEmprestimos(int n) {
        
        for (int key : this.studentsTreeMap.keySet()) {
            int total = this.studentsTreeMap.get(key).getTotalEmprestimo();
            String nome = this.studentsTreeMap.get(key).getName() + " " + this.studentsTreeMap.get(key).getSurname();
            this.estudantesMaisEmprestimos.put(total,nome);
        }
        
        int count = 0;
        
        for (int key : this.estudantesMaisEmprestimos.descendingKeySet()) {
            
            if (count < n) {
                System.out.println(this.estudantesMaisEmprestimos.get(key) + ": " + key);
            }
            count ++;
        }
    }
    /**
     * Livros mais emprestados
     * @param n numero de livros com mais emprestimos
     */
    public void livrosMaisEmprestimos(int n) {
        
        for (int key : this.booksTreeMap.keySet()) {
            int total = this.booksTreeMap.get(key).getTotalEmprestimo();
            String nome = this.booksTreeMap.get(key).getName();
            this.booksMaisEmprestimos.put(total,nome);
        }
        
        int count = 0;
        
        for (int key : this.booksMaisEmprestimos.descendingKeySet()) {
            
            if (count < n) {
                System.out.println(this.booksMaisEmprestimos.get(key) + ": " + key);
            }
            count ++;
        }
    }
    /**
     * Autores com mais emprestimos
     * @param n numero de autores
     */
    public void autoresMaisEmprestimos(int n) {
        
        for (int key : this.authorsTreeMap.keySet()) {
            int total = this.authorsTreeMap.get(key).getTotalEmprestimo();
            String nome = this.authorsTreeMap.get(key).getName();
            this.authorsMaisEmprestimos.put(total,nome);
        }
        
        int count = 0;
        
        for (int key : this.authorsMaisEmprestimos.descendingKeySet()) {
            
            if (count < n) {
                System.out.println(this.authorsMaisEmprestimos.get(key) + ": " + key);
            }
            count ++;
        }
        
    }
    
}
