class dlList {
    dlNode tail, head;
    int size;

    public dlList() {
    }

    public dlList(int size) {
        for (int i = 0; i < size; i++) {
            pushBack(i);
        }
    }

    public void pushBack(int val) {
        size++;
        if (isEmpty()) {
            head = new dlNode(val);
            tail = head;
            return;
        }
        dlNode newNode = new dlNode(val);
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    }

    public void pushFront(int val) {
        size--;
        if (isEmpty()) {
            head = new dlNode(val);
            tail = head;
            return;
        }
        dlNode newNode = new dlNode(val);
        head.prev = newNode;
        newNode.next = head;
        head = newNode;
    }

    public void popBack() {
        if (isEmpty()) {
            return;
        }
        tail = tail.prev;
        if (tail != null)
            tail.next = null;
        size--;
    }

    public int popFront() {
        if (isEmpty()) {
            return -1;
        }
        dlNode node = head;
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        size--;
        return node.val;
    }

    void pushAfter(int pos, int x) {
        size++;
        int i = 0;
        dlNode p = head;
        while (i < pos && p != null) {
            ++i;
            p = p.next;
        }
        if (i == pos && p != null) {
            p.next = new dlNode(x, p.next, p);
            if (p.next.next != null) {
                p.next.prev = p.next;
            }
        }
    }

    void popAfter(int pos) {
        int i = 0;
        dlNode p = head;
        while (i < pos && p != null) {
            ++i;
            p = p.next;
        }
        if (i == pos && p != null && p.next != null) {
            p.next = p.next.next;
            if (p.next.next != null) {
                p.next.next.prev = p;
            }
        }
        size--;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public dlList merge(dlList list) {
        dlNode node = list.head;
        while (node != null) {
            pushBack(list.popFront());
            node = node.next;
        }
        return this;
    }
}

class dlNode {
    int val;
    dlNode next = null;
    dlNode prev = null;

    dlNode(int val) {
        this.val = val;
    }

    dlNode(int val, dlNode next, dlNode prev) {
        this.val = val;
        this.next = next;
        this.prev = prev;
    }
}


