package com.javarush.task.task33.task3310.strategy;

import java.io.IOException;

public class FileStorageStrategy implements StorageStrategy {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000L;
    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize;
    int size;

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    public int hash(Long k){
        int h=Integer.parseInt(k.toString());
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    public int indexFor(int hash, int length){
        return hash & (length - 1);
    }

    public Entry getEntry(Long key){
        int hash = (key == null) ? 0 : hash(key);
        for (Entry e = table[indexFor(hash, table.length)].getEntry();
             e != null;
             e = e.next) {
            Long k;
            if (e.hash == hash && ((k = e.key).equals(key) || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }

    public FileStorageStrategy() throws IOException {
        table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
        for(int i = 0; i < table.length; ++i)
            table[i] = new FileBucket();

    }

    public void resize(int newCapacity) throws IOException {
        FileBucket[] newTable = new FileBucket[newCapacity];
        for (FileBucket fileBucket : newTable)
            fileBucket = new FileBucket();
        transfer(newTable);
        table = newTable;
    }

    public void transfer(FileBucket[] newTable) throws IOException {
        int newCapacity = newTable.length;

        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            Entry entry = table[i].getEntry();

            while (entry != null) {
                Entry next = entry.next;
                int indexFor = indexFor(entry.hash, newCapacity);
                entry.next = newTable[indexFor].getEntry();
                newTable[indexFor].putEntry(entry);
                entry = next;
            }
            table[i].remove();
            table[i] = null;
        }
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) throws IOException {
        Entry entry = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, entry));
        size++;
        if(table[bucketIndex].getFileSize() > bucketSizeLimit) {
            resize(2 * table.length); }
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) throws IOException {
        table[bucketIndex] = new FileBucket();
        table[bucketIndex].putEntry(new Entry(hash, key, value, null));
        size++;
    }



    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        if (value == null){return false;}
        for (int i = 0; i < table.length ; i++)
            if (table[i] != null) {
                for (Entry e = table[i].getEntry(); e != null ; e = e.next)
                    if (value.equals(e.value))
                        return true;}
        return false;

    }

    @Override
    public void put(Long key, String value) throws IOException {
        int hash = hash(key);
        int i = indexFor(hash, table.length);

        if (table[i] == null)
            createEntry(hash, key, value, i);
        else {
            for (Entry entry = table[i].getEntry(); entry != null; entry = entry.next) {
                Long k;
                if (entry.hash == hash && ((k = entry.key) == key || key.equals(k)))
                    entry.value = value;
            }
            addEntry(hash, key, value, i);
        }
    }

    @Override
    public Long getKey(String value) {
        for (int i = 0; i < table.length ; i++) {
            if (table[i] == null) continue;
            for (Entry e = table[i].getEntry(); e != null; e = e.next)
                if (value.equals(e.value))
                    return e.key;
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        return getEntry(key).getValue();
    }

}
