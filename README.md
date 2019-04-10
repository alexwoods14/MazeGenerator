# MazeGenerator
Java program for randomly generating a maze of a specified size

---
## There are 3 branches, read below to find out about each

# Master
The simplest of all of them. This simply creates a maze of size x\*y

#### Disclaimer:
It is by no means the most efficient as I have been using it to further my knowlegde on Generics and Java API interfaces rather than most efficient. In the future I may come back to make it more efficient.

---
# Visualizer
The reason why it is not the most efficient is so I could follow it up with this branch.

This visualizes the generation to make it clear how this generation works. It steps through one at a time and waits for a brief period between step.

---
# Solver
This is a simple solver. It orders sub tree from the root in order of size so when the depth first traversal happens, it always looks down the shortest route first.

This is also **not** as efficient as it could be. This is once again because I was playing about with comparables and ListIterators and also wanted to make the gui to it to show how this works.
There are plenty more efficient path finding algorithms.
