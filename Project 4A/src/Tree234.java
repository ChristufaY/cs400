import java.util.LinkedList;

/**
 * This class implements a 2-3-4 BTree, with the operations to insert, retrieve,
 * and retrieve the predecessor and successor values from within the tree.  Each
 * of these operations are implemented with time complexity: O(log n), where n
 * is the size or total number of elements within the tree.
 */
public class Tree234 <Key extends Comparable<Key>, Value> {

    private Node root = new Node();
    private int size = 0;

    /**
     * This method inserts a new key:value pair into this 2-3-4 tree.
     * @param key is used for ording (via compareTo) data within tree
     * @param value is stored along with the key in a tree node
     */
    public void insert(Key key, Value value) {
        root.insert(key,value);
        size++;
    }

    /**
     * This method retrieves a list of all values associated with a specific key.
     * @param key the search parameter for the values retrieved
     * @return the list of values associated with key, which may be empty
     */
    public LinkedList<Value> get(Key key) {
        return root.get(key);
    }

    /**
     * This method retrieves the value associated with a key that is the 
     * in-order predecessor of the specified key.
     * @param key is larger/later than the key associated with return value
     * @return value associated with smaller key, or null if no key is found
     */
    public Value getPred(Key key) {
        return root.getPred(key);
    }

    /**
     * This method retrieves the value associated with a key that is the 
     * in-order successor of the specified key.
     * @param key is smaller/earlier than the key associated with return value
     * @return value associated with larger key, or null if no key is found
     */
    public Value getSucc(Key key) {
        return root.getSucc(key);
    }

    /**
     * This method returns the number of key:value pairs stored in this tree.
     * @return size of this 2-3-4 tree
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the contents of this tree in string form.  These contents are
     * presented as a comma separated list, that is presented in level order.
     * The key:value pairs within each node are also comma separated, and
     * grouped within curly braces within this string: {}
     * @return string representation of this 2-3-4 tree's contents
     */
    @SuppressWarnings("unchecked")
    public String toString() {
        String s="[";
        LinkedList<Node> q = new LinkedList<>();
        q.push(root); // queue of nodes to visit in level-order

        while(!q.isEmpty()) {
            Node next = q.pop(); // add children from node visited to queue
            if(next instanceof Tree234.InteriorNode) {
                InteriorNode interiorNode = ((Tree234<Key,Value>.InteriorNode)next);
                q.addAll( interiorNode.getChildren() );
            }

            s += next.toString(); // add string from node visited to s
            if(!q.isEmpty()) s+= ", ";
        }
        return s+"]";
    }

    /**
     * A single instance of this base class is used to represent an empty tree. 
     * LeafNode and InteriorNode subclasses are defined below.  Notice that
     * these public methods mirror those defined for the containing class, and
     * so method headers for them are not repeated in the inner classes below. 
     */
    private class Node {
        // Note that this helper method returns a node, which represents
        // this tree or subtree after the key:value pair has been inserted.
        public Node insert(Key key, Value value) { 
            // inserting into an empty tree results in a single value leaf node
            return new LeafNode(key,value);
        }
        // cannot retrieve any values from an empty tree
        public LinkedList<Value> get(Key key) { return new LinkedList<>(); }
        public Value getPred(Key key) { return null; }
        public Value getSucc(Key key) { return null; }
        public String toString() { return "{}"; }
    }

    /**
     * This class represents leaf nodes that contain one, two, or three
     * key:value pairs.  This class is also used as a base class for the
     * InteriorNode class below, which also contains 2-4 child references.
     */
    private class LeafNode extends Node {
        protected Key[] keys;
        protected Value[] values;
        protected int size; // number of key:value pairs
        @SuppressWarnings("unchecked")
        public LeafNode(Key key, Value value) {
            keys = (Key[])new Comparable[3]; 
            values = (Value[])new Object[3];
            setKeyValuePairs(key,value);
        }

        public Node insert(Key key, Value value) { 
            switch(size) {
                case 1: // this node contains one key
                    if(key.compareTo(keys[0]) < 0) // add key:value before contents
                        setKeyValuePairs(key,value,keys[0],values[0]);
                    else // add key:value after contents
                        setKeyValuePairs(keys[0],values[0],key,value);
                    return this;
                case 2: // this node contains two keys
                    if(key.compareTo(keys[0]) < 0) // add key:value before contents
                        setKeyValuePairs(key,value,keys[0],values[0],keys[1],values[1]);
                    else if(key.compareTo(keys[1]) < 0) // add key:value between contents
                        setKeyValuePairs(keys[0],values[0],key,value,keys[1],values[1]);
                    else // add key:value after contents
                        setKeyValuePairs(keys[0],values[0],keys[1],values[1],key,value);
                    return this;
                case 3: // this node contains three keys
                    LeafNode left = new LeafNode(keys[0],values[0]);
                    LeafNode right = new LeafNode(keys[2],values[2]);
                    InteriorNode parent = new InteriorNode(keys[1],values[1],left,right);
                    parent.insert(key,value); // split before inserting
                    return parent;
            }
            throw new IllegalStateException("Size of leaf node is not 1, 2, or 3");
        }

        public LinkedList<Value> get(Key key) { 
        	  LinkedList<Value> returnValues = new LinkedList<>();
              for(int i=0;i<size;i++)
                  if(key.equals(keys[i])) {
                      returnValues.add(values[i]);
                      return returnValues;
                  }
              return returnValues;
          }
          public Value getPred(Key key) { 
              for(int i=size-1;i>=0;i--)
                  if(key.compareTo(keys[i]) > 0) return values[i];
              return null;

           }
          public Value getSucc(Key key) { 
              for(int i=0;i<size;i++)
                  if(key.compareTo(keys[i]) < 0) return values[i];
              return null;
           }

          /**
           * Returns the key:value pairs within this node in string form, as a
           * comma separated list within curly braces: {}.
           * @return string representation of this node's contents
           */
          public String toString() {
              String s="{";
              for(int i=0;i<size-1;i++) {
                      s += keys[i].toString()+":"+values[i].toString();
                  if(i < size-1) s += ", ";
              }
              return s+"}";
          }

          // helper methods below help update the key:value pairs in this node
          protected void setKeyValuePairs(Key k1, Value v1) {
              keys[0] = k1;
              values[0] = v1;
              size = 1;
          }
          protected void setKeyValuePairs(Key k1, Value v1, Key k2, Value v2) {
              setKeyValuePairs(k1,v1);
              keys[1] = k2;
              values[1] = v2;
              size = 2;
          }
          protected void setKeyValuePairs(Key k1, Value v1, Key k2, Value v2, Key k3, Value v3) {
              setKeyValuePairs(k1,v1,k2,v2);
              keys[2] = k3;
              values[2] = v3;
              size = 3;
          }
      }

      /**
       * This class represents interior node, which are like leaf nodes, but
       * with child references and some extra logic for managing those children.
       */
      private class InteriorNode extends LeafNode {
          protected Node[] children;
          @SuppressWarnings("unchecked")
          public InteriorNode(Key key, Value value, Node left, Node right) {
              super(key,value);
              children = new Tree234.LeafNode[4];
              children[0] = left;
              children[1] = right;
          }

          public Node insert(Key key, Value value) {
              switch(size) {
              case 1: // this node contains one key and two children
                  return insertHelperSize1(key,value);
              case 2: // this node contains two keys and three children
                  return insertHelperSize2(key,value);
              case 3: // this node contains three keys and four children
                  InteriorNode left = new InteriorNode(keys[0],values[0],children[0],children[1]);
                  InteriorNode right = new InteriorNode(keys[2],values[2],children[2],children[3]);
                  InteriorNode parent = new InteriorNode(keys[1],values[1],left,right);
                  return parent;
          }
          throw new IllegalStateException("Size of leaf node is not 1, 2, or 3");
      }

      // These helper methods recursively calls insert on the appropriate child.
      // If that child splits, then the contents of the new interior node (returned
      // by the recursive insert call) are then merged into the correct posistions
      // within the current node.
      @SuppressWarnings("unchecked")
      private Node insertHelperSize1(Key key, Value value) {
          Node tmp;
          if(key.compareTo(keys[0]) < 0) { // add key:value before contents
              tmp = children[0].insert(key,value);
              if(tmp != children[0]) { // merge interior node that bubbled up
                  InteriorNode merge = (InteriorNode)tmp;
                  setKeyValuePairs(merge.keys[0],merge.values[0],keys[0],values[0]);
                  setChildren(merge.children[0],merge.children[1],children[1]);
              }
          } else { // add key:value after contents
              tmp = children[1].insert(key,value);
              if(tmp != children[1]) { // merge interior node that bubbled up
                  InteriorNode merge = (InteriorNode)tmp;
                  setKeyValuePairs(keys[0],values[0],merge.keys[0],merge.values[0]);
                  setChildren(children[0],merge.children[0],merge.children[1]);
              }
          }
          return this;
      }
      @SuppressWarnings("unchecked")
      private Node insertHelperSize2(Key key, Value value) {
          Node tmp;
          if(key.compareTo(keys[0]) < 0) { // add key:value before contents
              tmp = children[0].insert(key,value);
              if(tmp != children[0]) { // merge interior node that bubbled up
                  InteriorNode merge = (InteriorNode)tmp;
                  setKeyValuePairs(merge.keys[0],merge.values[0],keys[0],values[0],keys[1],values[1]);
                  setChildren(merge.children[0],merge.children[1],children[1],children[2]);
              }
          } else if(key.compareTo(keys[1]) < 0) { // add key:value between contents
              tmp = children[1].insert(key,value);
              if(tmp != children[1]) { // merge interior node that bubbled up
                  InteriorNode merge = (InteriorNode)tmp;
                  setKeyValuePairs(keys[0],values[0],merge.keys[0],merge.values[0],keys[1],values[1]);
                  setChildren(children[0],merge.children[0],merge.children[1],children[2]);
              }
          } else { // add key:value after contents
              tmp = children[2].insert(key,value);
              if(tmp != children[2]) { // merge interior node that bubbled up
                  InteriorNode merge = (InteriorNode)tmp;
                  setKeyValuePairs(keys[0],values[0],keys[1],values[1],merge.keys[0],merge.values[0]);
                  setChildren(children[0],children[1],merge.children[0],merge.children[1]);
              }
          }
          return this;
      }

      public LinkedList<Value> get(Key key) {
          for(int i=0;i<size;i++)
              if(key.compareTo(keys[i]) < 0) {
                  LinkedList<Value> returnValues = children[i].get(key);
                  if(i-1>0 && key.equals(keys[i-1]))
                      returnValues.add(values[i-1]);
                  return returnValues;
              }
          LinkedList<Value> returnValues = children[size].get(key);
          if(key.equals(keys[size-1]))
              returnValues.add(values[size-1]);
          return returnValues;
  }
  public Value getPred(Key key) { 
      Value tmp;
      for(int i=size-1;i>=0;i--)
          if(key.compareTo(keys[i]) > 0) {
              tmp = children[i+1].getPred(key);
              return tmp!=null?tmp:values[i];
          } 
      return children[0].getPred(key);
   }
  public Value getSucc(Key key) { 
      Value tmp;
      for(int i=0;i<size;i++)
          if(key.compareTo(keys[i]) < 0) {
              tmp = children[i].getSucc(key);
              return tmp!=null?tmp:values[i];
          } 
      return children[size].getSucc(key);
   }

  // a simple accessor accessor method
  public LinkedList<Node> getChildren() { 
      LinkedList<Node> nodes = new LinkedList<>();
      for(int i=0;i<=size;i++)
          nodes.add(children[i]);
      return nodes;
  }

  // helper methods below help update the child references within this node
  protected void setChildren(Node n1, Node n2) {
      children[0] = n1;
      children[1] = n2;
  }
  protected void setChildren(Node n1, Node n2, Node n3) {
      setChildren(n1,n2);
      children[2] = n3;
      assert(size > 1);
  }
  protected void setChildren(Node n1, Node n2, Node n3, Node n4) {
      setChildren(n1,n2,n3);
      children[3] = n4;
      assert(size > 2);
  }
}
}

