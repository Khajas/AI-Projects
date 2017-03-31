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
    private Node root;
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
    private class Node{
        char ch;
        ArrayList<Node> childNodes;
        Node(char ch){
            this.ch=ch;
            childNodes=new ArrayList<>();
            for(int i=0;i<27;++i){
                childNodes.add(null);
            }
        }
        public Node addChild(char ch){
            if(ch=='*'){
                childNodes.set(26, new Node(ch));
                return childNodes.get(26);
            }
            ch=Character.toLowerCase(ch);
            Node n=childNodes.get(ch-'a');
            if(n==null) childNodes.set(ch-'a', new Node(ch));
            return childNodes.get(ch-'a');
        }
        public ArrayList<Node> getAllChildNodes(){
            return this.childNodes;
        }
        public boolean hasChild(char ch){
            Node n;
            if(ch=='*')
                n=childNodes.get(26);
            else n=childNodes.get(ch-'a');
            if(n==null) return false;
            return true;
        }
        public char getChar(){
            return ch;
        }
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
    private void printEachWord(Node n){
        if(n==null){
            return;
        }
        if(n.getChar()=='*'){
            System.out.println();
            return;
        }
        System.out.print(n.getChar());
        ArrayList<Node> arr=n.getAllChildNodes();
        for(Node nn: arr){
            printEachWord(nn);
        }
    }
    public void printEachWord(){
        this.printEachWord(root);
    }
    public void getSuggestion(String word){
        if(word==null || word.isEmpty()) return;
        Node n=root;
        ArrayList<Node> arr=root.getAllChildNodes();
        for(int i=0;i<word.length();++i){
            char ch=word.charAt(i);
            if(n.hasChild(ch)){
                n=arr.get(ch-'a');
                arr=n.getAllChildNodes();
            }
            else{
                System.out.println("Spelled Wrong!");
                return;
            }
        }
        if(arr.get(26)==null){
            System.out.println("Recommendations for "+word+": ");
            for(Node nn: n.getAllChildNodes()){
                if(nn!=null){
                    System.out.print(word);
                    printEachWord(nn);
                }
            }
        }else{
            System.out.println("Is A complete word!!");
        }
    }
}
