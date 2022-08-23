package homeWork.hw2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {
    private static List<String> sqrtList = Collections.synchronizedList(new ArrayList<>(1000000));

    public static void main(String[] args) {
        { for (int i = 0; i < 1000000; i++) sqrtList.add(null);}
//sqrtList.(10, "111111111111111111");
        System.out.println(sqrtList);

    }
}
