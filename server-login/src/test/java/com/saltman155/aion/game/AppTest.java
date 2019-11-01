package com.saltman155.aion.game;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        Random random = new Random();
        System.out.println(random.nextInt(5000));
    }
}
