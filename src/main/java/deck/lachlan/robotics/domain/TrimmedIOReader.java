package deck.lachlan.robotics.domain;

import lombok.EqualsAndHashCode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@EqualsAndHashCode
public class TrimmedIOReader {
    public Stream<String> linesFor(InputStream in) {
        return new BufferedReader(new InputStreamReader(in))
            .lines()
            .map(String::toLowerCase)
            .map(String::trim);
    }
}
