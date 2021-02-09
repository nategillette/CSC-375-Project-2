package com.flights;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentHashTable {

    private Node[] nodes;
    private int size;
    private ReadWriteLock rwl = new ReentrantReadWriteLock();


    public ConcurrentHashTable(int size) {
        this.size = size * 2;
        this.nodes = new Node[this.size];
    }


    private synchronized Node nodeGetter(int hash) {
        return nodes[hash];
    }

    private synchronized void nodeWriter(int hash, Node newN) {
        nodes[hash] = newN;
    }


    public boolean put(String id, Flight value) throws InterruptedException {
        int hash = genHash(value.getID());
        Node temp = new Node(hash, value);

        boolean tf = false;

        try {
            rwl.writeLock().lock();
            Node n = nodeGetter(hash);
            if (n == null) {
                nodeWriter(hash, temp);
                tf = true;
            } else {
                if (n.hasNext()) {
                    for (; n.hasNext(); n = n.getNext()) {
                        if (n.getVal().getID().equals(value.getID())) {
                            tf = false;
                            break;
                        }
                    }
                } else {
                    if (n.getVal().getID().equals(value.getID())) {
                        tf = false;
                    } else {
                        n.setNext(temp);
                        tf = true;
                    }
                }
            }
        } finally {
            rwl.writeLock().unlock();
            if (tf == false) {
                return false;
            } else {
                return true;
            }
        }
    }


    public Flight get(String id) throws InterruptedException {
        int hash = genHash(id);
        rwl.readLock().lock();
        Node n = nodeGetter(hash);
        Flight retFlight = null;
        try {
            if (n != null) {
                if (n.hasNext()) {
                    for (; ; ) {
                        if (n.getVal().getID().equals(id)) {
                            retFlight = n.getVal();
                            break;
                        } else {
                            if (n.hasNext()) {
                                n = n.getNext();
                            } else {
                                break;
                            }
                        }
                    }
                } else {
                    retFlight = n.getVal();
                }
            }
        } finally {
            rwl.readLock().unlock();
            if (retFlight == null) {
                return null;
            } else {
                return retFlight;
            }
        }

    }


    private int genHash(String id) {
        int hash = 0;
        for (int x = 0; x < id.length(); x++) {
            hash = hash + (x * id.charAt(x));
        }
        hash = hash % this.size;
        return hash;
    }


   private class Node {
        private int key;
        private Flight val;
        private Node next = null;

        public Node(int k, Flight val) {
            this.key = k;
            this.val = val;
        }

        public synchronized void setNext(Node n) {
            this.next = n;
        }

        //Get methods
        public synchronized int getKey() {
            return this.key;
        }

        public synchronized Flight getVal() {
            return this.val;
        }

        public synchronized Node getNext() {
            return this.next;
        }

        public boolean hasNext() throws InterruptedException {
            rwl.readLock().lock();
            if (this.next == null) {
                rwl.readLock().unlock();
                return false;
            } else {
                rwl.readLock().unlock();
                return true;
            }
        }
    }
}
