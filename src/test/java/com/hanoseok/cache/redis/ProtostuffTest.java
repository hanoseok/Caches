package com.hanoseok.cache.redis;

import com.hanoseok.cache.test.TestBase;
import com.hanoseok.serialization.ProtostuffSerializer;
import com.hanoseok.utils.reflect.ReflectionUtil;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;

/**
 * Created by hanoseok on 2014. 4. 15..
 */
public class ProtostuffTest extends TestBase {

    String s = "TEST";


    @Test
    public void test() throws Exception {
        System.setProperty("protostuff.runtime.collection_schema_on_repeated_fields", "true");
        ProtostuffSerializer<ProtostuffTest> protostuffSerializer = ProtostuffSerializer.make(ProtostuffTest.class);

        ProtostuffTest pt = new ProtostuffTest();
        byte[] bytes = protostuffSerializer.serialize(pt);

        System.out.println(new String(bytes));
        System.out.println(bytes.length);
        System.out.println(ReflectionUtil.toStringRecursive(pt));

        ProtostuffTest desPt = protostuffSerializer.deserialize(bytes);

        assertTrue(pt != desPt);
        assertNotSame(pt, desPt);
        assertEquals(ReflectionUtil.toStringRecursive(pt), ReflectionUtil.toStringRecursive(desPt));
    }


}
