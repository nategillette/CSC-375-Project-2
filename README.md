# CSC-375-Project-2

## Project 2 for Doug Lea's Fall 2020 Parallel Computing Class

### Project Background:

This is mainly an exercise in performance measurement. Each of the following steps has many possible variations; you are free to choose any of them.


1. Think of some kind of application in which a set of threads all rely on a shared collection of possibly-prioritized data; sometimes read-only, sometimes modifying the data. For example, a game-server with game-state as the collection, or a campus course scheduling system. Write a stripped-down version of this in which all the threads just emulate clients, and further strips out nearly everything except the reading and writing (while still somehow using results).

2. Write one solution using a data structure and/or locking scheme of your own devising (most likely a variant of some known technique). Write another to primarily use standard JDK components.

3. Compare the throughput of your program across at least two different loads on each of at least two different platforms. Use JMH unless you have an approved reason not to.

4. Plot your results as a set of graphs and place on a web page. 

### [Plotted Results](http://cs.oswego.edu/~ngillet2/CSC375/csc375.html)
