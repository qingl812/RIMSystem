package com.example.rimsystem.tool;

import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @auther luyu
 */

public class BloomFilterHelper<T> {
//    k个哈希函数
    private int numHashFunctions;
//    比特数组的位数
    private int bitSize;
//    将对象转化为byte的通道，通过此来形成位数组大小
    private Funnel<T> funnel;

    public BloomFilterHelper(int expectedInsertions) {
        this.funnel = (Funnel<T>) Funnels.stringFunnel(Charset.defaultCharset());
        bitSize = optimalNumOfBits(expectedInsertions, 0.03);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    public BloomFilterHelper(Funnel<T> funnel, int expectedInsertions, double fpp) {
        this.funnel = funnel;
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    public int[] murmurHashOffset(T value) {
        int[] offset = new int[numHashFunctions];

        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= numHashFunctions; i++) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }

        return offset;
    }

    /**
     * 计算bit数组长度
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    /**
     * 计算hash方法执行次数
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }
}

