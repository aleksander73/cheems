package aleksander73.cheems.utility;

import java.util.ArrayList;
import java.util.List;

import aleksander73.cheems.utility.functional_interface.Condition;

public class ListUtility {
    public static <E> List<E> filter(List<E> list, Condition<E> condition) {
        List<E> result = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            E element = list.get(i);
            if(condition.test(element)) {
                result.add(element);
            }
        }

        return result;
    }

    public static <E> E first(List<E> list, Condition<E> condition) {
        E result = null;
        for(int i = 0; i < list.size(); i++) {
            E element = list.get(i);
            if(condition.test(element)) {
                result = element;
                break;
            }
        }

        return result;
    }
}
