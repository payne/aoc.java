package org.mattpayne.aoc.java.puzzle;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PuzzleDTO {

    private Long id;

    @NotNull
    private Integer year;

    @NotNull
    private Integer day;

    @NotNull
    private Integer problem;

}
