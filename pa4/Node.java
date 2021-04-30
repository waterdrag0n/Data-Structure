public final class Node {
    private Integer data;
    private Node parent;
    private Node lchild;
    private Node rchild;

    public Node() {
        this.data = null;
        this.parent = null;
        this.lchild = null;
        this.rchild = null;
    }

    public Node(Integer data) {
        this.data = data;
        this.parent = null;
        this.lchild = null;
        this.rchild = null;
    }

    public Node(Integer data, Node parent) {
        this.data = data;
        this.parent = parent;
        this.lchild = null;
        this.rchild = null;
    }

    public Node(Node node) {
        this.data = node.getData();
        this.parent = node.getParent();
        this.lchild = node.getLChild();
        this.rchild = node.getRChild();
    }

    public void copyNode(Node node) {
        this.data = node.getData();
        this.parent = node.getParent();
        this.lchild = node.getLChild();
        this.rchild = node.getRChild();
    }

    public void copyNodePos(Node node) {
        this.parent = node.getParent();
        this.lchild = node.getLChild();
        this.rchild = node.getRChild();
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public void setParent(Node node) {
        this.parent = node;
    }

    public void setLChild(Node node) {
        this.lchild = node;
    }

    public void setRChild(Node node) {
        this.rchild = node;
    }

    public Integer getData() {
        return data;
    }

    public Node getParent() {
        return parent;
    }

    public Node getLChild() {
        return lchild;
    }

    public Node getRChild() {
        return rchild;
    }
}