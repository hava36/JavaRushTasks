package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class FunctionalTest {

    public void testStorage(Shortener shortener) throws IOException {

        String one = "TestThis";
        String two = "NotThis";
        String three = "TestThis";
        Long idOne = shortener.getId(one);
        Long idTwo = shortener.getId(two);
        Long idThree = shortener.getId(three);
        Assert.assertNotEquals("idTwo equalsh 1",idTwo,idOne);
        Assert.assertNotEquals("idTwo equalsh 3",idTwo,idThree);
        Assert.assertEquals("id1 not equals id3 ",idOne,idThree);
        String oneTest =shortener.getString(idOne);
        String twoTest = shortener.getString(idTwo);
        String threeTest= shortener.getString(idThree);
        Assert.assertEquals("String one fail",one,oneTest);
        Assert.assertEquals("String two fail",two,twoTest);
        Assert.assertEquals("String three fail",three,threeTest);

    }

    @Test
    public void testHashMapStorageStrategy() throws IOException {
        Shortener shortener = new Shortener(new HashMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testOurHashMapStorageStrategy() throws IOException {
        Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testFileStorageStrategy() throws IOException {
        Shortener shortener = new Shortener(new FileStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testHashBiMapStorageStrategy() throws IOException {
        Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testDualHashBidiMapStorageStrategy() throws IOException {
        Shortener shortener = new Shortener(new DualHashBidiMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testOurHashBiMapStorageStrategy() throws IOException {
        Shortener shortener = new Shortener(new OurHashBiMapStorageStrategy());
        testStorage(shortener);
    }

}
