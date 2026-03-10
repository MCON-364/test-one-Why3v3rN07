package edu.touro.las.mcon364.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class StudyTrackerTests {
    private StudyTracker tracker;

    @BeforeEach
    void setUp() {
        tracker = new StudyTracker();
    }

    @Test
    void addLearnerAddsLearnerWithEmptyList() {
        assertTrue(tracker.learnerNames().isEmpty());
        tracker.addLearner("Bob");
        assertTrue(tracker.learnerNames().contains("Bob"));
        assertTrue(tracker.scoresFor("Bob").isPresent());
        assertEquals(List.of(), tracker.scoresFor("Bob").get());

        assertFalse(tracker.learnerNames().contains("Meh"));
        tracker.addLearner("Meh");
        assertTrue(tracker.learnerNames().contains("Meh"));
        assertTrue(tracker.learnerNames().contains("Bob"));
    }

    @Test
    void addLearnerRejectsNameless() {
        assertThrows(IllegalArgumentException.class, () -> tracker.addLearner(""));
        assertThrows(IllegalArgumentException.class, () -> tracker.addLearner(null));
    }

    @Test
    void addLearnerRejectsDuplicates() {
        tracker.addLearner("Bob");
        assertTrue(tracker.learnerNames().contains("Bob"));
        assertFalse(tracker.addLearner("Bob"));    }

    @Test
    void addScoreAddsScore() {
        tracker.addLearner("Bob");
        tracker.addScore("Bob", 100);
        assertTrue(tracker.scoresFor("Bob").get().contains(100));
        tracker.addScore("Bob", 73);
        assertTrue(tracker.scoresFor("Bob").get().contains(73));
    }

    @Test
    void addScoreRejectsInvalidScores() {
        tracker.addLearner("Bob");
        assertThrows(IllegalArgumentException.class, () -> tracker.addScore("Bob", 101));
        assertThrows(IllegalArgumentException.class, () -> tracker.addScore("Bob", -1));
    }

    @Test
    void addScoreRejectsInvalidLearners() {
        assertFalse(tracker.addScore("Bob", 95));
        tracker.addLearner("Bob");
        assertTrue(tracker.addScore("Bob", 95));
        assertFalse(tracker.addScore("Moo", 22));
    }

    @Test
    void testAverageForReturnsCorrectAverage() {
        tracker.addLearner("Bob");
        tracker.addScore("Bob", 100);
        assertTrue(tracker.averageFor("Bob").isPresent());
        assertEquals(100, tracker.averageFor("Bob").get());
        tracker.addScore("Bob", 0);
        assertEquals(50, tracker.averageFor("Bob").get());
    }

    @Test
    void testAverageForReturnsEmptyForInvalidLearner() {
        assertEquals(Optional.empty(), tracker.averageFor("Bob"));
    }

    @Test
    void testAverageForReturnsEmptyForEmptyScoreList() {
        tracker.addLearner("Bob");
        assertEquals(Optional.empty(), tracker.averageFor("Bob"));
    }

    @Test
    void testLetterBandReturnsCorrectLetter() {}

    @Test
    void testLetterBandReturnsEmptyWhenNoAverage() {}

    @Test
    void testUndoLastChangeUndoesLastChange() {}

    @Test
    void testUndoLastChangeReturnsFalseWhenNoChanges() {}
}
