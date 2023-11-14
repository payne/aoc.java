package org.mattpayne.aoc.java.puzzle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mattpayne.aoc.java.config.BaseIT;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;


public class PuzzleResourceTest extends BaseIT {

    @Test
    @Sql("/data/puzzleData.sql")
    void getAllPuzzles_success() throws Exception {
        mockMvc.perform(get("/api/puzzles")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(((long)1000)));
    }

    @Test
    @Sql("/data/puzzleData.sql")
    void getPuzzle_success() throws Exception {
        mockMvc.perform(get("/api/puzzles/1000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year").value(((long)93)));
    }

    @Test
    void getPuzzle_notFound() throws Exception {
        mockMvc.perform(get("/api/puzzles/1666")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("NotFoundException"));
    }

    @Test
    void createPuzzle_success() throws Exception {
        mockMvc.perform(post("/api/puzzles")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/puzzleDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertEquals(1, puzzleRepository.count());
    }

    @Test
    void createPuzzle_missingField() throws Exception {
        mockMvc.perform(post("/api/puzzles")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/puzzleDTORequest_missingField.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").value("MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("year"));
    }

    @Test
    @Sql("/data/puzzleData.sql")
    void updatePuzzle_success() throws Exception {
        mockMvc.perform(put("/api/puzzles/1000")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/puzzleDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(95, puzzleRepository.findById(((long)1000)).get().getYear());
        assertEquals(2, puzzleRepository.count());
    }

    @Test
    @Sql("/data/puzzleData.sql")
    void deletePuzzle_success() throws Exception {
        mockMvc.perform(delete("/api/puzzles/1000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertEquals(1, puzzleRepository.count());
    }

}
