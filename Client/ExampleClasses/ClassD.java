package Client.ExampleClasses;

public class ClassD
{
    public ClassD() {}
    public ClassD(int i) { val3=i; }

    public String toString() { return "ClassD"; }
    
    public int getVal3() { return val3; }

    private ClassA val = new ClassA(17);
    private static ClassA val2;
    private int val3=34;
    private ClassA[] vallarray = new ClassA[10];
    
    private ClassA[] custarray = {new ClassA(20),val};
    private int[] intarray = {1,2,3,4};
    //private char[] chararry = {'a','b','c'};
    private boolean[] boolarray = {false,true,false};
}
