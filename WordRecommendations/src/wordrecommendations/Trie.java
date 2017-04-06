/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordrecommendations;

import java.util.ArrayList;

/**
 *
 * @author Anwar
 */
public class Trie {
    private final Node root;
    public Trie(){
        root=new Node('$');
    }
    /**
    * Method to add a word to a Trie
    * @param word: word to be added
    * @return true if the word is added, else false
    */
    public boolean addWord(String word){
        Node n=root;
        if(validateWord(word)){
            for(int i=0;i<word.length();++i){
                    n=n.addChild(word.charAt(i));
            }
            if(n.hasChild('*')) return false;
            else n.addChild('*');
        }
        else return false;
        return true;
    }
    private boolean validateWord(String word){
        for(int i=0;i<word.length();++i)
            if(!Character.isAlphabetic(word.charAt(i)))
                return false;
        return true;
    }
    public void printTrie(){
        ArrayList<Node> l=new ArrayList<>();
        l.add(root);
        while(!l.isEmpty()){
            Node n=l.get(0);
            System.out.println(n.getChar());
            ArrayList<Node> arr=n.getAllChildNodes();
            for(Node nn: arr){
                if(nn!=null)
                    l.add(nn);
            }
            l.remove(0);
        }
    }
    private void printEachWord(Node n, String word_so_far){
        if(n==null){
            return;
        }
        if(n.getChar()=='*'){
            System.out.println(word_so_far);
            return;
        }
        ArrayList<Node> arr=n.getAllChildNodes();
        for(Node nn: arr){
            if(nn!=null && nn.getChar()!='*')
                printEachWord(nn,word_so_far+nn.getChar());
            else printEachWord(nn,word_so_far);
        }
    }
    public void printEachWord(String word_so_far){
        this.printEachWord(root, word_so_far);
    }
    public void getSuggestion(String word){
        if(word==null || word.isEmpty()) return;
        Node n=root;
        for(int i=0;i<word.length();++i){
            char ch=word.charAt(i);
            if(n.hasChild(ch)){
                n=n.getNode(ch);
            }
            else{
                System.out.println("Spelled Wrong!");
                return;
            }
        }
        if(!n.hasChild('*')){
            System.out.println("Do you mean? ");
            for(Node nn: n.getAllChildNodes()){
                    printEachWord(nn, word+nn.getChar());
            }
        }else{
            System.out.println("Is A complete word!!");
        }
    }
}