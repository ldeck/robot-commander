package deck.lachlan.robotics.domain.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class TrimmedIOReaderTest {

    TrimmedIOReader subject;

    @BeforeEach
    void setUp() {
        subject = new TrimmedIOReader();
    }

    @Test
    @DisplayName("should return empty with no input")
    void shouldReturnEmptyWithNoInput() {
        InputStream emptyString = new ByteArrayInputStream(new byte[0]);
        assertThat(subject.linesFor(emptyString)).isEmpty();
    }

    @Test
    @DisplayName("should return single item with one line of text")
    void shouldReturnSingleItemWithOneLineOfText() {
        InputStream emptyString = new ByteArrayInputStream("hello world".getBytes());
        assertThat(subject.linesFor(emptyString)).containsExactly("hello world");
    }

    @Test
    @DisplayName("should split lines and return the stream")
    void shouldSplitLinesAndReturnTheStream() {
        InputStream emptyString = new ByteArrayInputStream("hello\nworld".getBytes());
        assertThat(subject.linesFor(emptyString)).containsExactly("hello", "world");
    }

    @Test
    @DisplayName("should lower case input stream")
    void shouldLowerCaseInputStream() {
        InputStream emptyString = new ByteArrayInputStream("Hello\nWorld".getBytes());
        assertThat(subject.linesFor(emptyString)).containsExactly("hello", "world");
    }

    @Test
    @DisplayName("should trim each line of input")
    void shouldTrimEachLineOfInput() {
        InputStream emptyString = new ByteArrayInputStream(" Hello \n World ".getBytes());
        assertThat(subject.linesFor(emptyString)).containsExactly("hello", "world");
    }
}