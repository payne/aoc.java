package org.mattpayne.aoc.java.puzzle;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PuzzleRepository extends JpaRepository<Puzzle, Long> {
}
