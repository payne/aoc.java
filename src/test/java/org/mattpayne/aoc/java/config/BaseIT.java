package org.mattpayne.aoc.java.config;

import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.mattpayne.aoc.java.JavaApplication;
import org.mattpayne.aoc.java.puzzle.PuzzleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;


/**
 * Abstract base class to be extended by every IT test. Starts the Spring Boot context, with all data
 * wiped out before each test.
 */
@SpringBootTest(
        classes = JavaApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("it")
@Sql("/data/clearAll.sql")
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIT {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public PuzzleRepository puzzleRepository;

    @SneakyThrows
    public String readResource(final String resourceName) {
        return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), StandardCharsets.UTF_8);
    }

}
