

public class gb_sem_4 {
   
    
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        Integer[] arr = {9,4,6,2,7,5,10,11,12,13,14};
        for (Integer integer : arr) {
            tree.add(integer);
        }

        tree.printTree();

    }
}