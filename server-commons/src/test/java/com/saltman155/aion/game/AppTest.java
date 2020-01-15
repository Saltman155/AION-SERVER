package com.saltman155.aion.game;

import static org.junit.Assert.assertTrue;

import com.aionstar.commons.properties.ConfigurableProcessor;
import com.aionstar.commons.properties.Property;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        assertTrue( true );
    }

    @Test
    public void TestProperties(){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(new File("C:\\Users\\杨文鼎\\Documents\\GitHub\\AION-SERVER\\server-commons\\src\\test\\resources\\test.properties")));
            List<Properties> tmp = new ArrayList<>();
            tmp.add(properties);
            TestClass item = new TestClass();
            ConfigurableProcessor.process(item,tmp);
            System.out.println(TestClass.attr1);
            System.out.println(item.attr3);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class TestClass{

        @Property(key = "attr2")
        private static Integer attr1;

        @Property(key = "attr2")
        private static Integer attr2;

        @Property(key = "attr3")
        private Integer attr3;
    }
}
