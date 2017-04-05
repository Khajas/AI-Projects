/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordrecommendations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Anwar
 */
public class WordRecommendations {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Trie trie=new Trie();
        try{
            readData(trie, "test//words.txt");
        }catch(FileNotFoundException e){
            System.out.println("Missing words File!!");
            return;
        }
//        print(trie);
        String input_word=getUserInput();
        trie.getSuggestion(input_word);
    }
    private static String getUserInput(){
        Scanner sc=new Scanner(System.in);
        return sc.nextLine();
    }
    private static void readData(Trie trie, String fileName) throws FileNotFoundException{
        Scanner sc;
        sc=new Scanner(new File(fileName));
        String pattern="[A-Za-z\n\']+";
        while(sc.hasNext(pattern)){
            if(sc.hasNext("\n")) continue;
            String word=sc.next(pattern);
//            System.out.println(word);
            trie.addWord(word);
        }
    }
    private static void print(Trie trie){
        trie.printTrie();
        trie.printEachWord("$");
    }
}
