
public class Heap {
    public Event[] Heap;
    public int size;
    private int maxsize = 100000000;

    private static final int FRONT = 1;

    public Heap() {
        //public Heap(int maxsize) {
        //this.maxsize = maxsize;
        size = 0;
        Heap = new Event[this.maxsize + 1];
        Event e = new Event();
        e.t = Double.MIN_VALUE;
        Heap[0] = e;
    }

    private int parent(int pos) {
        return pos / 2;
    }

    private int leftChild(int pos) {
        return (2 * pos);
    }

    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    private boolean isLeaf(int pos) {
        if (pos >= (size / 2) && pos <= size) {
            return true;
        }
        return false;
    }

    private void swap(int fpos, int spos) {
        Event tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }

    private void minHeapify(int pos) {
        if (!isLeaf(pos)&& size!=1) {
            if (Heap[pos].t > Heap[leftChild(pos)].t || Heap[pos].t > Heap[rightChild(pos)].t) {
                if (Heap[leftChild(pos)].t < Heap[rightChild(pos)].t) {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                } else {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }



    public void insert(Event element) {

        if (size + 1 == maxsize){
            return;
        }

        Heap[++size] = element;
        int current = size;

        while (Heap[current].t < Heap[parent(current)].t) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public void print() {
        System.out.println("size : " + size);
        if(size!=0 && size != 1){

            if(size%2 == 0){

                for (int i = 1; i < size / 2; i++) {

                    System.out.print(" PARENT : " + Heap[i].t + " LEFT CHILD : " + Heap[2 * i].t
                            + " RIGHT CHILD :" + Heap[2 * i + 1].t);
                    System.out.println();
                }
                System.out.print(" PARENT : " + Heap[size / 2].t + " LEFT CHILD : " + Heap[2 * size / 2].t);

            } else{
                for (int i = 1; i <= size / 2; i++) {

                    System.out.print(" PARENT : " + Heap[i].t + " LEFT CHILD : " + Heap[2 * i].t
                            + " RIGHT CHILD :" + Heap[2 * i + 1].t);
                    System.out.println();
                }
            }
        } else if (size == 1) {
                    System.out.print(" ROOT : " + Heap[size].t);
        } else {
            System.out.println(" The heap is empty ");
        }

    }

    public void minHeap() {
        for (int pos = (size / 2); pos >= 1; pos--) {
            minHeapify(pos);
        }
    }

    public Event remove() {

        Event popped = Heap[FRONT];
        if(size!=1){
            Heap[FRONT] = Heap[size--];
            minHeapify(FRONT);
        }else{
            size--;
            //Event e = new Event();
            //e.t = Msq.STOP;
            //Heap[FRONT] = e;
        }
        return popped;
    }



    public Event root_event() {
        Event popped = Heap[FRONT];
        return popped;
    }



    public int isEmpty(){

        return size;
    }

}