# Algorithm-I-princeton

My personal coursework from **Princeton University's "Algorithms, Part I"** (Robert Sedgewick & Kevin Wayne, Coursera). All programming assignments were completed and verified by the course's automated grader. This repository is a study/portfolio record of that work — not official course material.

## What this demonstrates (CS competencies)

| Assignment | File(s) | CS competency demonstrated |
|---|---|---|
| Percolation | `Percolation.java`, `PercolationStats.java` | Union–Find (weighted quick-union + path compression); backwash handling with dual UF structures; Monte-Carlo simulation |
| Deques & Randomized Queues | `Deque.java`, `RandomizedQueue.java`, `Permutation.java` | Generic data types; doubly-linked list; custom `Iterator` implementation; array shuffling |
| Collinear Points | `Point.java`, `BruteCollinearPoints.java`, `FastCollinearPoints.java` | Sorting, comparators, algorithm analysis (brute-force vs. sorting-based fast method) |
| 8-Puzzle | `Board.java`, `Solver.java` | Priority queues (A* search), Manhattan-distance heuristic, immutable ADT design, unsolvability detection via twin boards |
| KdTree | `PointSET.java`, `KdTree.java` | 2D-tree / geometric search, range search with rectangle pruning, nearest-neighbor search |

The numbered files (`2-1-1.java`, `10-2.java`, etc.) are the course's **weekly exercises** (not graded assignments) and are included as additional practice.

## Build & run

These programs depend on Princeton's `algs4.jar` standard library.

1. Download `algs4.jar` from: https://algs4.cs.princeton.edu/code/
2. Compile (example):
   ```bash
   javac -cp ".:algs4.jar" Percolation.java
   java  -cp ".:algs4.jar" Percolation
   ```
3. Replace `.` with `;` on Windows: `javac -cp ".;algs4.jar" Percolation.java`

## Note on academic integrity

This repository contains my own solutions to publicly available course assignments, kept here as a personal learning record after course completion. It is shared for portfolio purposes only.


