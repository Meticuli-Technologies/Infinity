package com.meti;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/1/2019
 */
public class ModuleOrganizer {
    public static void main(String[] args) {
        try {
            Path out = Paths.get(".\\out");
            checkDirectory(out);

            Path artifacts = out.resolve("artifacts");
            checkDirectory(artifacts);

            Set<Path> jars = Files.list(artifacts)
                    .filter(path -> Files.isDirectory(path))
                    .map((Function<Path, Optional<Stream<Path>>>) path -> {
                        try {
                            return Optional.of(Files.list(path));
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Optional.empty();
                        }
                    }).flatMap(Optional::stream)
                    .flatMap((Function<Stream<Path>, Stream<Path>>) pathStream -> pathStream)
                    .filter(path -> path.toString().endsWith("jar"))
                    .collect(Collectors.toSet());

            Path moduleDirectory = Paths.get(".\\modules");
            checkDirectory(moduleDirectory);

            for (Path path : jars) {
                String name = path.getFileName().toString().replace(".jar", "");
                Path resolve = moduleDirectory.resolve(name);
                ensure(resolve);

                Path bin = resolve.resolve("bin");
                ensure(bin);

                Files.move(path, bin.resolve(path.getFileName()), REPLACE_EXISTING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ensure(Path resolve) throws IOException {
        if (!Files.exists(resolve)) {
            Files.createFile(resolve);
        }
    }

    public static void checkDirectory(Path artifacts) {
        if (!Files.exists(artifacts) || !Files.isDirectory(artifacts)) {
            throw new IllegalStateException(artifacts + " is not valid!");
        }
    }
}
