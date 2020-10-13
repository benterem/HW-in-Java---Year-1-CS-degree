package TaskManager;


public class TaskManager {

	Task[] heap;
	int[] availableData; // the reference array
	int size;			 // the current number of tasks awaiting completion

	/**
	 * Creates an empty data structure with the given capacity.
	 * The capacity dictates how many different tasks may be put into the data structure.
	 * Moreover, the capacity gives an upper bound to the serial number of tasks to be put in the data structure.
	 * 
	 */
	public TaskManager(int capacity){
		heap = new Task[capacity];
		availableData = new int[capacity];
		size = 0;
	}

	/**
	 * Returns the size of the heap.
	 *
	 * @return the size of the heap
	 */
	public int size(){
		return size;
	}

	/**
	 * Inserts a given Task into the heap.
	 *
	 * @param t - the Task to be inserted.
	 */
	public void insert(Task t){
		if(size == heap.length) {
		System.out.println("Data overflow");
		return;
		}
		percolateUp(size++, t);
	}

	public void percolateUp(int i, Task t) {
		int parentIndex = (i - 1) / 2;
		if (i == 0 || heap[parentIndex].compareTo(t) > 0) {
			heap[i] = t;
			availableData[t.serial - 1] = i;
		} else {
			heap[i] = heap[parentIndex];
			availableData[heap[parentIndex].serial - 1] = i;
			percolateUp(parentIndex, t);
		}
	}

	public void percolateDown(int i, Task t) {
		int firstChildIndex = 2 * i + 1;
		int secondChildIndex = 2 * i + 2;
		if(firstChildIndex > size - 1) {
			heap[i] = t;
			availableData[t.serial - 1] = i;
		}
		else if(firstChildIndex == size - 1) {
			if(heap[firstChildIndex].compareTo(t) > 0) {
				heap[i] = heap[firstChildIndex];
				availableData[heap[firstChildIndex].serial - 1] = i;
				heap[firstChildIndex] = t;
				availableData[t.serial - 1] = firstChildIndex;
			}else {
				heap[i] = t;
				availableData[t.serial - 1] = i;
			}
		}
		else {
			int j = heap[firstChildIndex].compareTo(heap[secondChildIndex]) > 0 ? firstChildIndex : secondChildIndex;
			if(heap[j].compareTo(t) > 0) {
				heap[i] = heap[j];
				availableData[heap[j].serial - 1] = i;
				percolateDown(j, t);
			}else {
				heap[i] = t;
				availableData[t.serial - 1] = i;
			}
		}
	}
	/**
	 * Returns the Task with the highest serial number in the heap.
	 * You may not use any loops (or recursion) in this function.
	 *
	 * @return the Task with the highest priority in the heap.
	 */
	public Task findMax() {
		return heap[0];
	}

	/**
	 * Removes and returns the Task with the highest serial number from the heap.
	 * Recall that you are not allowed to traverse all elements of the heap array.
	 *
	 * @return the Task with the highest priority in the heap.
	 */

	public Task extractMax(){
		if(size == 0) {
			System.out.println("Please insert a task");
			return null;
		}
		Task t = findMax();
		availableData[heap[0].serial - 1] = 0;
		heap[0] = heap[--size];
		availableData[heap[0].serial - 1] = heap[size].serial;
		heap[size] = null;
		if (size != 0)
			percolateDown(0, heap[0]);
		return t;
	}



	/**
	 * Updates the priority of a given task.
	 * Does nothing if the task is not already in the heap.
	 * Recall that you are not allowed to traverse all elements of the heap array.
	 * Think about what can go wrong in the heap as you change the priority of a given task. How will you fix it?
	 *
	 * @param t - the given task
	 * @param newPriority - the new priority of the given task.
	 */
	public void updatePriority(Task t, int newPriority){
		int index = availableData[t.serial - 1];
		if (t.serial != heap[index].serial)
			return;
		int oldP = t.priority;
		t.priority = newPriority;
		if(newPriority > oldP) {
			percolateUp(index, t);
		}else {
			percolateDown(index, t);
		}
	}


	/*
	 * Test code; output should be:
	 * task: abbreviate notes, priority: 10
	 * task: download new version, priority: 20
	 * task: bring food, priority: 11
	 * task: abbreviate notes, priority: 10
	 * task: clear histories, priority: 3
	 * task: download new version, priority: 0
	 */
	public static void main (String[] args){
		TaskManager demo = new TaskManager(10);
		Task a = new Task(1, 10, "abbreviate notes");
		Task b = new Task(2, 2, "bring food");
		Task c = new Task(3, 3, "clear histories");
		Task d = new Task(4, 20, "download new version");

		demo.insert(a);
		System.out.println(demo.findMax());

		demo.insert(b);
		demo.insert(c);
		demo.insert(d);
		System.out.println(demo.findMax());
		demo.updatePriority(b, 11);
		demo.updatePriority(d, 0);
		System.out.println(demo.extractMax());
		System.out.println(demo.extractMax());
		System.out.println(demo.extractMax());
		System.out.println(demo.extractMax());


	}
}
