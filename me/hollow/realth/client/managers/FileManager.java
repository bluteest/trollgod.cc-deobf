//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import java.util.function.*;
import java.nio.file.attribute.*;
import me.hollow.realth.client.modules.*;
import java.io.*;
import java.util.stream.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class FileManager
{
    private final Path base;
    private final Path config;
    
    private String[] expandPath(final String fullPath) {
        return fullPath.split(":?\\\\\\\\|\\/");
    }
    
    private Stream<String> expandPaths(final String... paths) {
        return Arrays.stream(paths).map((Function<? super String, ?>)this::expandPath).flatMap((Function<? super Object, ? extends Stream<? extends String>>)Arrays::stream);
    }
    
    private Path lookupPath(final Path root, final String... paths) {
        return Paths.get(root.toString(), paths);
    }
    
    private Path getRoot() {
        return Paths.get("", new String[0]);
    }
    
    private void createDirectory(final Path dir) {
        try {
            if (!Files.isDirectory(dir, new LinkOption[0])) {
                if (Files.exists(dir, new LinkOption[0])) {
                    Files.delete(dir);
                }
                Files.createDirectories(dir, (FileAttribute<?>[])new FileAttribute[0]);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Path getMkDirectory(final Path parent, final String... paths) {
        if (paths.length < 1) {
            return parent;
        }
        final Path dir = this.lookupPath(parent, paths);
        this.createDirectory(dir);
        return dir;
    }
    
    public FileManager() {
        this.base = this.getMkDirectory(this.getRoot(), "TrollGod");
        this.config = this.getMkDirectory(this.base, "config");
        this.getMkDirectory(this.base, "util");
        for (final Module.Category category : Module.Category.values()) {
            this.getMkDirectory(this.config, category.getLabel());
        }
    }
    
    public Path getBasePath() {
        return this.base;
    }
    
    public Path getBaseResolve(final String... paths) {
        final String[] names = this.expandPaths(paths).toArray(String[]::new);
        if (names.length < 1) {
            throw new IllegalArgumentException("missing path");
        }
        return this.lookupPath(this.getBasePath(), names);
    }
    
    public Path getMkBaseResolve(final String... paths) {
        final Path path = this.getBaseResolve(paths);
        this.createDirectory(path.getParent());
        return path;
    }
    
    public Path getConfig() {
        return this.getBasePath().resolve("config");
    }
    
    public Path getCache() {
        return this.getBasePath().resolve("cache");
    }
    
    public Path getMkBaseDirectory(final String... names) {
        return this.getMkDirectory(this.getBasePath(), this.expandPaths(names).collect(Collectors.joining(File.separator)));
    }
    
    public Path getMkConfigDirectory(final String... names) {
        return this.getMkDirectory(this.getConfig(), this.expandPaths(names).collect(Collectors.joining(File.separator)));
    }
    
    public static boolean appendTextFile(final String data, final String file) {
        try {
            final Path path = Paths.get(file, new String[0]);
            Files.write(path, Collections.singletonList(data), StandardCharsets.UTF_8, Files.exists(path, new LinkOption[0]) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
        }
        catch (IOException e) {
            System.out.println("WARNING: Unable to write file: " + file);
            return false;
        }
        return true;
    }
    
    public static List<String> readTextFileAllLines(final String file) {
        try {
            final Path path = Paths.get(file, new String[0]);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            System.out.println("WARNING: Unable to read file, creating new file: " + file);
            appendTextFile("", file);
            return Collections.emptyList();
        }
    }
}
