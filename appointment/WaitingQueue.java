package appointment;

import patient.Patient;

public class WaitingQueue {

    private Patient[] queue;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    // Constructor
    public WaitingQueue(int capacity) {
        this.capacity = capacity;
        queue = new Patient[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    // Add patient to waiting list
    public boolean enqueue(Patient p) {
        if (isFull()) {
            System.out.println("Waiting list is full.");
            return false;
        }
        rear = (rear + 1) % capacity;
        queue[rear] = p;
        size++;
        return true;
    }

    // Remove patient from waiting list
    public Patient dequeue() {
        if (isEmpty()) return null;
        Patient p = queue[front];
        front = (front + 1) % capacity;
        size--;
        return p;
    }

    // Display all patients in waiting list
    public void display() {
        if (isEmpty()) {
            System.out.println("Waiting list is empty.");
            return;
        }
        System.out.println("\n--- Waiting List ---");
        for (int i = 0; i < size; i++) {
            int idx = (front + i) % capacity;
            System.out.println(queue[idx].getName());
        }
    }

    public boolean isEmpty() { return size == 0; }

    public boolean isFull() { return size == capacity; }

    public Patient getAtIndex(int index) {
        if (index < 0 || index >= size) return null;
        return queue[(front + index) % capacity];
    }

    public int getSize() {
        return size;
    }
}
