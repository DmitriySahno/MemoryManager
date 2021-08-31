import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        MemoryManager mm = new MemoryManager(32);
        mm.malloc(3); //0 1 2
        mm.malloc(5); //3 4  5  6  7
        mm.malloc(5); //8 9 10 11 12
        mm.free(3);
        mm.free(8);
        mm.free(0);
    }

}
