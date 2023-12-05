package com.example.dictionary.base;

import java.sql.SQLException;
import java.util.*;
public class Trie {
    public static class TrieNode {
        Map<Character, TrieNode> children = new TreeMap<>();
        boolean isEndOfWord = false;

        TrieNode() {
        };
    }

    private static final ArrayList<String> currWord = new ArrayList<>();
    private static final TrieNode root = new TrieNode();

    private static void getAllNode(TrieNode trie, String word) {
        if (trie.isEndOfWord) {
            currWord.add(word);
        }
        for (char c : trie.children.keySet()) {
            if (trie.children.get(c) != null)
                getAllNode(trie.children.get(c), word + c);
        }
    }

    public static void addNode(String word) {
        TrieNode currentNode = root;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            // Kiểm tra xem node hiện tại có null hay không
            if (currentNode.children == null) {
                currentNode.children = new HashMap<>();
            }

            // Kiểm tra xem node con tương ứng với ký tự c có tồn tại hay không
            TrieNode childNode = currentNode.children.get(c);
            if (childNode == null) {
                childNode = new TrieNode();
                currentNode.children.put(c, childNode);
            }

            // Di chuyển đến node con tiếp theo
            currentNode = childNode;
        }

        // Đánh dấu node hiện tại là node kết thúc của từ
        currentNode.isEndOfWord = true;
    }
    private static void dfsGetWordsSubtree(TrieNode pCrawl, String target) {
        if (pCrawl.isEndOfWord) {
            currWord.add(target);
        }
        for (char index : pCrawl.children.keySet()) {
            if (pCrawl.children.get(index) != null) {
                dfsGetWordsSubtree(pCrawl.children.get(index), target + index);
            }
        }
    }
    public static ArrayList<String> searchList(String word) {
        word = word.toLowerCase();
        if (word.isEmpty()) {
            return new ArrayList<>();
        }
        currWord.clear();
        int length = word.length();
        TrieNode pCrawl = root;

        for (int level = 0; level < length; level++) {
            char index = word.charAt(level);

            if (pCrawl.children.get(index) == null) {
                return new ArrayList<>();
            }

            pCrawl = pCrawl.children.get(index);
        }
        dfsGetWordsSubtree(pCrawl, word);
        return getCurrWord();
    }

    public static ArrayList<String> getCurrWord() {
        return currWord;
    }

    public static void delete(String word) {
        TrieNode chiNode = root;

        for (char c : word.toCharArray()) {
            if (chiNode.children.get(c) == null) {
                System.out.println("No such word exists");
                return;
            }
            chiNode = chiNode.children.get(c);
        }

        if (!chiNode.isEndOfWord) {
            System.out.println("No such word exists 2");
        }

        chiNode.isEndOfWord = false;
    }

}

