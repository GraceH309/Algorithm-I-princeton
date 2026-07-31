# Princeton Algorithms, Part I — notes

Note: The large volume of early commits stems from migrating offline notes to this repository. I will keep making incremental improvements over time.

Finished Robert Sedgewick's Coursera course in 2025. Autograder passed everything.
I'm keeping the code here mostly so future-me can find it again.

## Assignments

- **Percolation** — the backwash bug ate a lot of time. Fix was two UFs (one with
  the virtual bottom, one without); I only really got it after reading the discussion
  board. Details scribbled in `notes/percolation-backwash.md`.
- **Deques & Randomized Queues** — fine, passed first try, nothing to say here.
- **Collinear Points** — brute force first (slow but obviously right), then the fast
  version. The sort + dedup step took a whole evening.
- **8-Puzzle** — Manhattan distance beats Hamming easily; the twin-board trick for
  the unsolvable case is the one thing I'm glad I didn't have to invent myself.
- **KdTree** — `range()` and `nearest()` work, but I kept flipping `>=` / `>` on the
  rectangle pruning and failing the hidden tests a couple of times.

(the `2-1-1.java`, `10-2.java` style files are just weekly exercises, not graded)

## Build

Needs Princeton's `algs4.jar` on the classpath:

    javac -cp ".:algs4.jar" Percolation.java
    java  -cp ".:algs4.jar" Percolation

On Windows the separator is `;` not `:`, so `-cp ".;algs4.jar"`. Cost me half an
hour of "class not found" errors before I noticed.

## TODO / didn't finish

- KdTree: want to extend `nearest()` into k-NN, but haven't figured out the pruning
  yet. Left as is for now.
- The course had a few optional exercises I started and dropped — never wrote a fast
  Boggle solver, the brute-force one is all I've got.
- 8-Puzzle A* works, but I skipped the "game-tree" bonus they mentioned.

## Note

My own solutions, posted after I finished. Reference only — don't copy, and don't
submit as yours (honor code).
