# Princeton Algorithms, Part I — assignment notes

Took Robert Sedgewick's Coursera course, finished in 2025. The autograder passed
everything, but a couple of assignments took several rewrites. This repo is my
own study record after finishing, not the official solutions.

## Assignments

| Assignment | Difficulty | What bit me |
|------------|-----------|-------------|
| Percolation | ⭐⭐⭐ | backwash problem stuck me for two days; dual UF only clicked after reading the discussion board |
| Deques & Randomized Queues | ⭐⭐ | Iterator didn't handle fail-fast at first, tests failed |
| Collinear Points | ⭐⭐⭐⭐ | Fast version's sorting idea took a whole evening; wrote brute force first as a safety net |
| 8-Puzzle | ⭐⭐⭐⭐⭐ | Tried both Manhattan and Hamming; Manhattan much faster; unsolvable check via twin board is the standard trick |
| KdTree | ⭐⭐⭐⭐ | range search rectangle pruning easy to get the boundary condition wrong |

(numbered files like `2-1-1.java`, `10-2.java` are weekly exercises, not graded — kept as extra practice.)

## Build

Depends on Princeton's `algs4.jar`:

```bash
javac -cp ".:algs4.jar" Percolation.java
java  -cp ".:algs4.jar" Percolation
```

On Windows swap `.` for `;`: `-cp ".;algs4.jar"`.

## Gotchas I remember

- **Percolation backwash**: with a single UF (virtual top + bottom), once the system percolates, bottom-connected open sites get wrongly flagged as "full". Fix is two UFs (one with bottom, one without); details in `notes/percolation-backwash.md`. Took me two days to see it; got it from the discussion board.
- **8-Puzzle unsolvable**: twin-board trick for detecting unsolvable is way easier than guessing.
- **KdTree pruning**: in range/nearest, got the `>=` / `>` boundary wrong several times.

## Note

These are my **own** solutions, uploaded after I finished. For reference only — don't copy them (and don't submit them as your own, that breaks the honor code).
