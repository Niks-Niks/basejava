package main.storage;

import main.model.Resume;

import java.io.*;

public interface Stream {
    void doWrite(Resume r, OutputStream os) throws IOException;

    Resume doRead(InputStream is) throws IOException, ClassNotFoundException;
}
