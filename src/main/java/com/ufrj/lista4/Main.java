/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufrj.lista4;

import com.ufrj.lista4.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Miguel
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        UserInterface test = new UserInterface(scan);
        test.start();
        
    }
    
}
