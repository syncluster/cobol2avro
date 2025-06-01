package com.tech4box.partition;

public class PartitionUtils {
    public static long[] calculatePartition(long totalFileSize, int totalPartitions, int podIndex) {
        long partitionSize = totalFileSize / totalPartitions;
        long start = podIndex * partitionSize;

// Last pod takes any remaining bytes
        if (podIndex == totalPartitions - 1) {
            long end = totalFileSize;
            return new long[]{start, end - start};
        } else {
            return new long[]{start, partitionSize};
        }
    }
}
