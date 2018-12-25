import java.util.Queue;
import java.util.ArrayDeque;
public class BinarySearchTree<K extends Comparable<K>> 
            implements BinarySearchTreeInterface<K> {

    private class Node {
        private Node parent;
        private Node left;
        private Node right;

        private K key;

        private void clear() {
            parent = null;
            left = null;
            right = null;
            key = null;
        }

        public String toString() {
            return key.toString();
        }
    }

    private Node root;

    private Node getNode(K key) {
        return getNodeHelper(key, root);
    }

    private Node getNodeHelper(K key, Node curNode) {
        if (curNode == null) return null;

        if (key.compareTo(curNode.key) < 0)
            return getNodeHelper(key, curNode.left);
        else if (key.compareTo(curNode.key) > 0)
            return getNodeHelper(key, curNode.right);
        else
            return curNode;
    }

    private Node nodeBefore(Node n) {
        if (n.left != null) {
            Node res = n.left;
            while (res.right != null) {
                res = res.right;
            }
            return res;
        } else {
            Node res = n;
            while (res.parent != null) {
                Node parent = res.parent;
                if (res.key.compareTo(parent.key) > 0)
                    return parent;
                else
                    res = parent;
            }
            return null;
        }
    }

    private Node nodeAfter(Node n) {
        if (n.right != null) {
            Node res = n.right;
            while (res.left != null) {
                res = res.left;
            }
            return res;
        } else {
            Node res = n;
            while (res.parent != null) {
                Node parent = res.parent;
                if (res.key.compareTo(parent.key) < 0)
                    return parent;
                else
                    res = parent;
            }
            return null;
        }
    }



    public boolean insert(K key) {
        if (key == null) return false;
        if (root == null) {
            Node n = new Node();
            n.key = key;
            root = n;
            return true;
        }
        return insertHelper(key, root);
    }

    private boolean insertHelper(K key, Node curNode) {
        if (key.compareTo(curNode.key) == 0)
            return false;
        if (key.compareTo(curNode.key) < 0) {
            if (curNode.left == null) {
                Node n = new Node();
                n.key = key;
                n.parent = curNode;
                curNode.left = n;
                return true;
            }
            return insertHelper(key, curNode.left);
        } else {
            if (curNode.right == null) {
                Node n = new Node();
                n.key = key;
                n.parent = curNode;
                curNode.right = n;
                return true;
            }
            return insertHelper(key, curNode.right);
        }
    }

    public boolean delete(K key) {
        return remove(key);
    }

    public boolean remove(K key) {
        if (key == null) return false;
        Node n = getNode(key);
        if (n == null) return false;
        if (n.left == null && n.right == null) {
            cutSubtree(n.parent, n);
        } else if (n.right != null) {
            Node successor = nodeAfter(n);
            if (successor != n.right) {
                successor.parent.left = successor.right;
                successor.right = n.right;
            }
            successor.left = n.left;
            replaceSubtree(n.parent, n, successor);
        } else {
            Node precursor = nodeBefore(n);
            if (precursor != n.left) {
                precursor.parent.right = precursor.left;
                precursor.left = n.left;
            }
            precursor.right = n.right;
            replaceSubtree(n.parent, n, precursor);
        }
        n.clear();
        return true;
    }

    private void cutSubtree(Node parent, Node child) {
        replaceSubtree(parent, child, null);
    }

    private void replaceSubtree(Node parent, Node child, Node replacement) {
        if (replacement != null)
            replacement.parent = parent;
        if (parent == null)
            root = replacement;
        else if (child.key.compareTo(parent.key) < 0)
            parent.left = replacement;
        else
            parent.right = replacement;
    }


    public boolean contains(K key) {
        if (key == null) return false;
        return getNode(key) != null;
    }
    public K keyBefore(K key) {
        if (key == null) return null;
        Node n = getNode(key);
        Node resNode = nodeBefore(n);
        return resNode == null? null : resNode.key;
    }
    public K keyAfter(K key) {
        if (key == null) return null;
        Node n = getNode(key);
        Node resNode = nodeAfter(n);
        return resNode == null? null : resNode.key;
    }
    public void visualize() {
        if (root == null) return;
        visualizeByBFS();
    }

    private void visualizeByBFS() {
        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);
        while(!q.isEmpty()) {
            Node curNode = q.poll();
            if (curNode.left != null)
                q.offer(curNode.left);
            if (curNode.right != null)
                q.offer(curNode.right);
            System.out.println(curNode + "["
                + curNode.left + ", " + curNode.right + "]");
        }
    }
}