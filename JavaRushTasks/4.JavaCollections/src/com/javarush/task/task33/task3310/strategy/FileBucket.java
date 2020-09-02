package com.javarush.task.task33.task3310.strategy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {

    Path path;

    public FileBucket() throws IOException {

        path = Files.createTempFile(null, null);
        Files.deleteIfExists(path);
        Files.createFile(path);
        path.toFile().deleteOnExit();

    }

    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            return 0L;
        }
    }

    public void putEntry(Entry entry) throws IOException {

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path));
        objectOutputStream.writeObject(entry);
        objectOutputStream.flush();
        objectOutputStream.close();

    }

    public Entry getEntry()  {

        if (getFileSize() == 0L) {
            return null;
        }

        try {
            ObjectInputStream objectReader = new ObjectInputStream(Files.newInputStream(path));
            Entry entry = (Entry) objectReader.readObject();
            objectReader.close();
            return entry;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
