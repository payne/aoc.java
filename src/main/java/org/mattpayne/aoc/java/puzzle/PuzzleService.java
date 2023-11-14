package org.mattpayne.aoc.java.puzzle;

import java.util.List;
import org.mattpayne.aoc.java.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PuzzleService {

    private final PuzzleRepository puzzleRepository;

    public PuzzleService(final PuzzleRepository puzzleRepository) {
        this.puzzleRepository = puzzleRepository;
    }

    public List<PuzzleDTO> findAll() {
        final List<Puzzle> puzzles = puzzleRepository.findAll(Sort.by("id"));
        return puzzles.stream()
                .map(puzzle -> mapToDTO(puzzle, new PuzzleDTO()))
                .toList();
    }

    public PuzzleDTO get(final Long id) {
        return puzzleRepository.findById(id)
                .map(puzzle -> mapToDTO(puzzle, new PuzzleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PuzzleDTO puzzleDTO) {
        final Puzzle puzzle = new Puzzle();
        mapToEntity(puzzleDTO, puzzle);
        return puzzleRepository.save(puzzle).getId();
    }

    public void update(final Long id, final PuzzleDTO puzzleDTO) {
        final Puzzle puzzle = puzzleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(puzzleDTO, puzzle);
        puzzleRepository.save(puzzle);
    }

    public void delete(final Long id) {
        puzzleRepository.deleteById(id);
    }

    private PuzzleDTO mapToDTO(final Puzzle puzzle, final PuzzleDTO puzzleDTO) {
        puzzleDTO.setId(puzzle.getId());
        puzzleDTO.setYear(puzzle.getYear());
        puzzleDTO.setDay(puzzle.getDay());
        puzzleDTO.setProblem(puzzle.getProblem());
        return puzzleDTO;
    }

    private Puzzle mapToEntity(final PuzzleDTO puzzleDTO, final Puzzle puzzle) {
        puzzle.setYear(puzzleDTO.getYear());
        puzzle.setDay(puzzleDTO.getDay());
        puzzle.setProblem(puzzleDTO.getProblem());
        return puzzle;
    }

}
