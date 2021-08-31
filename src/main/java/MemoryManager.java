import java.util.ArrayList;
import java.util.Stack;

public class MemoryManager {
    ArrayList<dlList> segments; //Двусвязный список — для хранения отрезков. Изначально один большой отрезок,
    // с 0 по N-1 ячейку, по мере выделения памяти, в списке хранятся свободные и занятые отрезки.
    Stack<dlList> freeSegments; //LRU-кэш (на стеке) — для хранения свободных отрезков.
    dlList[] arr; //Массив длиной N — для связи «ячейка памяти - отрезок». Храним в каждой ячейке ссылку на отрезок, начинающийся в этой ячейке.

    public MemoryManager(int n) {
        dlList firstSegment = new dlList(n);
        segments = new ArrayList<>();
        segments.add(firstSegment);
        freeSegments = new Stack<>();
        freeSegments.push(firstSegment);
        arr = new dlList[n];
        arr[0] = firstSegment;
    }

    /* malloc(n), где  — запрашиваемое количество ячеек памяти. Менеджер должен вернуть номер ячейки, начиная с которой
     * он выделяет отрезок длиной . После этого последующие запросы к malloc() не могут выделять ячейки из этого отрезка,
     * пока он не будет освобождён при помощи free().*/
    public int malloc(int n) {
        for (dlList list : freeSegments) {
            if (list.size >= n) {
                dlList listBusy = new dlList();
                segments.add(listBusy);
                arr[list.head.val] = listBusy;
                for (int i = 0; i < n; i++) {
                    listBusy.pushBack(list.popFront());
                }
                if (!list.isEmpty())
                    arr[list.head.val] = list;
                return listBusy.head.val;
            }
        }
        return -1;
    }


    /*free(i), где  — это индекс ячейки, которую когда-то вернул malloc(). Функция должна вернуть , если в ячейке  
    не начинается никакой выделенный отрезок, и  в противном случае.*/
    public int free(int n) {
        if (arr[n] != null) {
            dlList list = arr[n];
            for (int i = n - 1; i >= 0; i--) { //проверяем возможность объединения с ближайшим пустым отрезком в левую сторону...
                if (arr[i] != null) {
                    if (freeSegments.contains(arr[i]) && arr[i].tail.val + 1 == list.head.val) {
                        segments.remove(list);
                        freeSegments.remove(list);
                        list = arr[i].merge(list);
                        arr[n] = null;
                    }
                    break; //...только до ближайшего отрезка
                }
            }

            for (int i = n + 1; i < arr.length; i++) { //проверяем возможность объединения с ближайшим пустым отрезком в правую сторону...
                if (arr[i] != null) {
                    if (freeSegments.contains(arr[i]) && list.tail.val + 1 == arr[i].head.val) {
                        segments.remove(arr[i]);
                        freeSegments.remove(arr[i]);
                        list = list.merge(arr[i]);
                        arr[i] = null;
                    }
                    break; //...только до ближайшего отрезка
                }
            }

            if (!freeSegments.contains(list))
                freeSegments.add(list);
            return list.head.val;
        }
        return -1;
    }
}



/*
Подсказка 1
Для хранения свободных участков удобно использовать stack. Если класть сверху участки, с которым взаимодействовал менеджер, тогда сверху будет лежать самый актуальный отрезок.

Подсказка 2
Воспользуйтесь тремя структурами:

Двусвязный список — для хранения отрезков. Изначально один большой отрезок, с 0 по N-1 ячейку, по мере выделения памяти, в списке хранятся свободные и занятые отрезки.
LRU-кэш (на стеке) — для хранения свободных отрезков.
Массив длиной  — для связи «ячейка памяти  отрезок». Храним в каждой ячейке ссылку на отрезок, начинающийся в этой ячейке.
*/