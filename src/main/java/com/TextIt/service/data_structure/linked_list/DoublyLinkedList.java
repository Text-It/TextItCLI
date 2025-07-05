package com.TextIt.service.data_structure.linked_list;


public class DoublyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;

    // Insert At the First of the DoublyLinkedList
    public void insertFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null values");
        }
        try {
            Node<T> n = new Node<>(data);
            if (head == null) {
                head = n;
                tail = head;// tail  points to where head is

            } else {
                n.next = head;
                head.prev = n;
                head = n;
            }
        } catch (Exception e) {
            System.err.println("Error while inserting: " + e.getMessage());
        }
    }

    // Insert At the Last of the LinkedList
    public void insertLast(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null values");
        }

        Node<T> n = new Node<>(data);

        try {
            // Insert element At Last When linkedList is not empty
            if (head != null) {
                tail.next = n;
                n.prev = tail;
                tail = n;
            } else {
                head = n;
                tail = head;
            }
        } catch (Exception e) {
            System.err.println("Error wile insertingATLast: " + e.getMessage());
        }
    }

    public T deleteFirst() {
        if (head == null) {
            System.out.println("LinkedList is Empty");
            return null;
        }
        try {
            T val;
            if (head.next == null) {
                val = head.data;
                head = null;
                tail = null;
            } else {
                val = head.data;
                head = head.next;
                head.prev.next = null;
                head.prev = null;
            }
            return val;
        } catch (Exception e) {
            System.err.println("Error while deleting: " + e.getMessage());
        }
        return null;
    }

    public T deleteLast() {
        if (tail == null) {
            System.out.println("LinkedList is Empty");
            return null;
        }
        try {
            T val;
            if (tail.prev == null) {
                val = tail.data;
                tail = null;
                head = null;
                return val;
            } else {
                val = tail.data;
                tail = tail.prev;
                tail.next = null;
            }
            return val;
        } catch (Exception e) {
            System.err.println("Error while deleting: " + e.getMessage());
        }
        return null;
    }
    public void display(){
        if(head==null){
            System.out.println("The LinkedList is Empty!!");
            return;
        }
        Node<T> temp = head;
        while (temp!=null){
            System.out.println(temp.data+"->");
            temp=temp.next;
        }
    }

    static class Node<T> {
        private final T data;
        private Node<T> next;
        private Node<T> prev;

        private Node(T data) {
            this.data = data;
        }
    }


}

