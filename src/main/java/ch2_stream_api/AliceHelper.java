package ch2_stream_api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class AliceHelper {
    public static List<String> getAliceWords() throws URISyntaxException, IOException {
        URI file = AliceHelper.class.getResource("alice.txt").toURI();
        Path alicePath = Paths.get(file);
        String contents = new String(Files.readAllBytes(alicePath), StandardCharsets.UTF_8); // Read ﬁle into string
        return Arrays.asList(contents.split("[\\P{L}]+"));
    }
}
