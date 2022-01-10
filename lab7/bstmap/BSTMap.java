package bstmap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author jiaxing zhou
 * @version 1.0
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {


    private int size;
    private Set<K> keySet = new HashSet<>();
    private Node root;


    public BSTMap() {
        this(null);
        size = 0;

    }

    public BSTMap(Node root) {
        this.root = root;
    }

    @Override
    public void clear() {
        this.root = null;
        size = 0;
        keySet.clear();
    }

    @Override
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    @Override
    public V get(K key) {
        if (!keySet.contains(key)) {
            return null;
        }
        V value = (V) getValue(key, this.root);
        return value;
    }

    private V getValue(K key, Node node) {
        if (node == null) {
            return null;
        }
        int comp = key.compareTo((K) node.key);
        if (comp == 0) {
            return (V) node.value;
        } else if (comp < 0) {
            return (V) getValue(key, node.left);
        } else {
            return (V) getValue(key, node.right);
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {

        Node node = new Node(key, value);
        this.root = addNode(node, this.root);
        size += 1;
        keySet.add(key);
    }

    private Node addNode(Node node, Node root) {

        if (root == null) {
            return node;
        }
        int com = ((K) (node.key)).compareTo((K) root.key);
        if (com < 0) {
            root.left = addNode(node, root.left);
        } else {
            root.right = addNode(node, root.right);
        }
        return root;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node node) {
        if (node == null) {
            return;
        }
        printInOrder(node.left);
        System.out.println(node.key.toString() + " -> " + node.value.toString());
        printInOrder(node.right);
    }

    @Override
    public Set keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        if (containsKey(key)) {
            V targetValue = get(key);
            root = remove(root, key);
            size -= 1;
            keySet.remove(key);
            return targetValue;
        }
        return null;
    }


    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo((K) node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            Node originalNode = node;
            node = getMinChild(node.right);
            node.left = originalNode.left;
            node.right = remove(originalNode.right, (K) node.key);
            //node.right = originalNode.left;
        }
        return node;
    }


    private Node getMinChild(Node node) {
        if (node.left == null) {
            return node;
        }
        return getMinChild(node.left);
    }


    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }


    private class Node<K, V> {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }


}
