import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Heap { //This is the heap class to create my own implementation
    private List<Integer> heap;
    private boolean isMaxHeap;
    
    
/*This one is the constructor to initialize the heap and define if it is a max-heap or min-heap. It calls the heap 
with the boolean, which holds the value of isMaxHeap. If it is 'true' it means that it is a max-heap and if it is
'false' then, it is a min-heap.*/
    public Heap(boolean isMaxHeap) { 
        this.heap = new ArrayList<>();
        this.isMaxHeap = isMaxHeap;
    }

    //This method gets the size of the heap
    public int size() {
        return heap.size();
    }

    //This method gets the root element (maximum element in max-heap or minimum element in min-heap)
    public int peek() {
        return heap.get(0);
    }

    //This method adds a new number to the heap
    public void add(int num) {
        heap.add(num);
        heapifyUp(heap.size() - 1);
    }

    //This method firstly finds the root and then removes it from the heap
    public int poll() {
        int root = heap.get(0);
        int last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return root;
    }

    //This method maintains heap property from bottom to top
    private void heapifyUp(int index) {
        int parent = (index - 1) / 2;
        while (index > 0 && compare(heap.get(index), heap.get(parent))) {
            Collections.swap(heap, index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    //This method maintains heap property from top to bottom
    private void heapifyDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int extremeIndex = index;

        if (leftChild < heap.size() && compare(heap.get(leftChild), heap.get(extremeIndex))) {
            extremeIndex = leftChild;
        }
        if (rightChild < heap.size() && compare(heap.get(rightChild), heap.get(extremeIndex))) {
            extremeIndex = rightChild;
        }
        if (extremeIndex != index) {
            Collections.swap(heap, index, extremeIndex);
            heapifyDown(extremeIndex);
        }
    }

/*This method compares two elements based on their heap type(max-heap or min-heap). It basically is being called with 
two numbers to compare them and returns a>b if the heap is a max-heap (if the boolean 'isMaxHeap' is true), 
and returns a<b when the heap is a min-heap (if the boolean 'isMaxHeap' is false).*/
    private boolean compare(int a, int b) {
        if (isMaxHeap) {
        	return a > b;
        }
        else {
        	return a < b;
        }
    }
    
    
}


/*This part is the part that the program will determine the median of the ages that we added to heaps.*/
public class MedianOfAges {
    private Heap maxHeap;
    private Heap minHeap;

/*This is the constructor to initialize max-heap and min-heap. This is the exact place that we assign the 'isMaxHeap'
variable of the 'Heap' class as true if the heap is a max-heap and false if the heap is min-heap.*/
    public MedianOfAges() {
        maxHeap = new Heap(true); 
        minHeap = new Heap(false); 
    }

    //This one inserts a new age and maintains the balance between the heaps
    public void insertNum(int num) {
        if (maxHeap.size() == 0 || num <= maxHeap.peek()) {
            maxHeap.add(num); //This will add 'num' to max-heap if it is smaller than or equal to the root of max-heap
        } else {
            minHeap.add(num); //This will add 'num' to min-heap if it is larger than the root of max-heap
        }

        //Here we balance the heaps if the size difference is more than 1
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.add(maxHeap.poll()); //This moves the root of max-heap to min-heap
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.add(minHeap.poll()); //And this moves the root of min-heap to max-heap
        }
    }

    //This method will find the median of the current ages
    public double findMedian() {
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0; //If heaps have equal sizes, median is the average of roots
        }
        return maxHeap.peek(); //If heaps are not of equal size, median is the root of the max-heap
    }

    public static void main(String[] args) {
        MedianOfAges MedianOfAges = new MedianOfAges();
        MedianOfAges.insertNum(22);
        MedianOfAges.insertNum(35);
        System.out.println("The recommended content will be for ages under: " + MedianOfAges.findMedian());
        MedianOfAges.insertNum(30);
        System.out.println("The recommended content will be for ages under: " + MedianOfAges.findMedian());
        MedianOfAges.insertNum(25);
        System.out.println("The recommended content will be for ages under: " + MedianOfAges.findMedian());
    }
}
