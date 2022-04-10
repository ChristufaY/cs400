public class Main {
    public static void main(String[] args) {
        // System.out.println("FIXME: Do something with the MyList class");                                       
        MyList<String> demo = new MyList<String>();
        demo.add("Hello. ");
        demo.add("My name is ");
        demo.add("Chris");
        System.out.println(demo.get(0));
        System.out.println(demo.get(1));
        System.out.println(demo.get(2));
    }
}