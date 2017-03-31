/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordrecommendations;

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
        trie.addWord("sudharma");
        trie.addWord("sunil");
        trie.addWord("sunill");
        trie.printTrie();
        trie.printEachWord();
        trie.getSuggestion("suni");
    }
}
