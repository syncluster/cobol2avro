package com.tech4box.partition;


import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Details.Line;
import com.tech4box.JRecord.IO.AbstractLineReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class PartitionedLineReader extends AbstractLineReader {

    private final long startByte;
    private final long endByte;
    private RandomAccessFile raf;
    private LayoutDetail layout;
    private boolean finished = false;
    private final int recordLength;

    public PartitionedLineReader(long startByte, long endByte, int recordLength) {
        this.startByte = startByte;
        this.endByte = endByte;
        this.recordLength = recordLength;
    }

    @Override
    public void open(String fileName, LayoutDetail layout) throws IOException {
        this.layout = layout;
        this.raf = new RandomAccessFile(fileName, "r");
        raf.seek(startByte);
    }

    @Override
    public void open(InputStream inputStream, LayoutDetail layout) {
        throw new UnsupportedOperationException("InputStream is not supported for PartitionedLineReader.");
    }

    @Override
    public AbstractLine readImplementation() throws IOException {
        if (finished || raf.getFilePointer() >= endByte) {
            finished = true;
            return null;
        }

        byte[] buffer = new byte[recordLength];
        int bytesRead = raf.read(buffer);

        if (bytesRead < recordLength) {
            finished = true;
            return null;
        }

        AbstractLine line = new Line(layout);
        line.replace(buffer, 0, buffer.length);

        return line;
    }

    @Override
    public void close() throws IOException {
        if (raf != null) {
            raf.close();
        }
    }
}