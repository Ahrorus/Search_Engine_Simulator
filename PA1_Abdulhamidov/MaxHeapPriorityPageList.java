// Ahror Abdulhamidov
// The purpose of this class is to manage an array list of pages, provide
// heapsort related functions, and max-heap priority queue related functions

package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MaxHeapPriorityPageList {

    private ArrayList<Page> A;  // ArrayList of Pages (heap list could be a part of the entire list)
    private int size;  // Size of the entire arrayList
    private int heapSize;  // Size of the heap inside the arrayList

    // PageList constructor from an arrayList
    // Ahror Abdulhamidov
    public MaxHeapPriorityPageList(ArrayList<String> A) {
        this.A = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            this.A.add(new Page(A.get(i)));
            size++;
        }
    }

    // Get i-th element's rank
    public int getTotalRank(int i) {
        return A.get(i).getTotalRank();
    }

    // Set i-th element's rank
    public void setRanks(int i, int r1, int r2, int r3, int r4) {
        A.get(i).setRanks(r1, r2, r3, r4);
    }

    // Swaps to elements in the ArrayList
    private void swap(int i, int j) {
        Collections.swap(A, i, j);
    }

    // ToString method for the entire list
    // Ahror Abdulhamidov
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Page Priority List:\n\n");
        for (int i = 0; i < A.size(); i++) {
            sb.append("#").append(i).append(" ").append(A.get(i).toString());
        }
        return sb.toString();
    }

    // ToString method for the heap list
    // Ahror Abdulhamidov
    public String heapToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Page Heap List:\n\n");
        for (int i = 0; i < heapSize; i++) {
            sb.append("#").append(i).append(" ").append(A.get(i).toString());
        }
        return sb.toString();
    }

    // --------------------
    // Heap Sort Functions:

    private int left(int i) { return 2 * i + 1; }  // Left child of i-th element in the heap

    private int right(int i) { return 2 * i + 2; }  // Right child of i-th element in the heap

    private int parent(int i) { return (i - 1) / 2; }  // Parent of i-th element in the heap

    // MaxHeapify i-th element in the heap
    // Ahror Abdulhamidov
    private void maxHeapify(int i) {
        int largest;
        int l = left(i);
        int r = right(i);
        if ((l < heapSize) && (getTotalRank(l) > getTotalRank(i)))
            largest = l;
        else largest = i;
        if  ((r < heapSize) && (getTotalRank(r) > getTotalRank(largest)))
            largest = r;
        if (largest != i) {
            swap(i, largest);
            maxHeapify(largest);
        }
    }

    // Build MaxHeap tree
    // Ahror Abdulhamidov
    private void buildMaxHeap() {
        heapSize = size;
        for (int i = size / 2 - 1; i >= 0; i--)
            maxHeapify(i);
    }

    // HeapSort the MaxHeap tree
    // Ahror Abdulhamidov
    public void heapSort() {
        buildMaxHeap();
        for (int i = size - 1; i >= 1; i--) {
            swap(0, i);
            heapSize--;
            maxHeapify(0);
        }
        // reverse (sort in descending order):
        for (int i = 0; i < size / 2; i++) {
            swap(i, size - 1 - i);
        }
    }

    // ----------------------------
    // MaxHeapPriorityList methods:

    // Make a heap of the specified size from the arrayList
    public void makePriorityQueueOfSize(int heapSize) {
        this.heapSize = heapSize;
    }

    // Peek at the most ranked element in the MaxHeap tree
    public Page heapMaximum() {
        return A.get(0);
    }

    // Extract the most ranked element in the MaxHeap tree
    // Ahror Abdulhamidov
    public Page heapExtractMaximum() throws Exception {
        if (heapSize < 1)
            throw new Exception("Heap Underflow.\n");
        Page max = A.get(0);
        A.set(0, A.get(heapSize - 1));
        heapSize--;
        maxHeapify(0);
        return max;
    }

    // Increase page rank of the i-th element in the heap
    // Ahror Abdulhamidov
    public void heapIncreaseKey(int i, int r1, int r2, int r3, int r4) throws Exception {
        int rank = r1 + r2 + r3 + r4;
        if (rank < getTotalRank(i))
            throw new Exception("New key is smaller than the current key.\n");
        setRanks(i, r1, r2, r3, r4);
        while ((i > 0) && (getTotalRank(parent(i)) < getTotalRank(i))) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    // MaxHeapInsert a new page with its link and rank
    // Ahror Abdulhamidov
    public void maxHeapInsert(String link, int r1, int r2, int r3, int r4) throws Exception {
        heapSize++;
        if (heapSize > size)
            size = heapSize;
        A.add(heapSize - 1, new Page(link));
        setRanks(heapSize - 1, -1, -1, -1, -1);
        heapIncreaseKey(heapSize - 1, r1, r2, r3, r4);
    }

    // --------------------
    // Inner Class for Page
    // Ahror Abdulhamidov
    private class Page {

        // Page's url link
        private String link;
        // Page's total rank and the 4 ranks:
        //  frequency of the keyword, website's age, linked from other websites, paid advertisement
        private int totalRank;  // Page's total rank
        private int r1, r2, r3, r4;
        private Random rand = new Random();  // Randomizer

        // Page constructor
        private Page(String link) {
            this.link = link;
            r1 = rand.nextInt(100) + 1;
            r2 = rand.nextInt(100) + 1;
            r3 = rand.nextInt(100) + 1;
            r4 = rand.nextInt(100) + 1;
            calculateTotalRank();
        }

        // Getters and Setters
        private int getTotalRank() { return totalRank; }

        private void setRanks(int r1, int r2, int r3, int r4) {
            this.r1 = r1;
            this.r2 = r2;
            this.r3 = r3;
            this.r4 = r4;
            calculateTotalRank();
        }

        // Calculate the total rank of the Page
        public void calculateTotalRank() {
            totalRank = r1 + r2 + r3 + r4;
        }

        // ToString method for the page
        // Ahror Abdulhamidov
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Page Link: \"")
                    .append(link).append("\", Page Ranks: ")
                    .append(r1).append(" ")
                    .append(r2).append(" ")
                    .append(r3).append(" ")
                    .append(r4).append(", Total rank: ")
                    .append(totalRank).append("\n");
            return sb.toString();
        }
    }

}
