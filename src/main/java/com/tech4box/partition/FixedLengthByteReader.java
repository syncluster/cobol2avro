package com.tech4box.partition;

import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Details.Line;
import com.tech4box.JRecord.IO.AbstractLineReader;


import java.io.*;

public class FixedLengthByteReader extends AbstractLineReader {

    private InputStream inputStream;
    private LayoutDetail layout;
    private final long startByte;
    private final long endByte;
    private long currentByte = 0;
    private int recordLength;

    public FixedLengthByteReader(long startByte, long endByte) {
        this.startByte = startByte;
        this.endByte = endByte;
        this.recordLength = -1; // Will be initialized in open

    }

    @Override
    public void open(InputStream inputStream, LayoutDetail layout) throws IOException {
        this.inputStream = inputStream;
        this.layout = layout;
        this.recordLength = layout.getMaximumRecordLength();


// Skip to start byte
        long skipped = inputStream.skip(startByte);
        if (skipped < startByte) {
            throw new IOException("Failed to skip to start byte: " + startByte);
        }
        currentByte = startByte;
    }

    @Override
    public AbstractLine readImplementation() throws IOException {
        if (currentByte >= endByte) {
            return null; // Reached assigned range end
        }

        byte[] buffer = new byte[recordLength];
        int bytesRead = inputStream.read(buffer);

        if (bytesRead == -1) {
            return null;
        }

        currentByte += bytesRead;

        if (bytesRead != recordLength) {
            throw new IOException("Incomplete record read, expected " + recordLength + " but got " + bytesRead);
        }

        AbstractLine line = new Line(layout);
        line.replace(buffer, 0, buffer.length);

        return line;
    }

    @Override
    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
    }
}
