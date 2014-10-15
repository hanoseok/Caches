package com.hanoseok.cache.redis;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.hanoseok.cache.redis.service.RedisCacheService;
import com.hanoseok.cache.redis.service.ShardedRedisCacheService;
import com.hanoseok.cache.test.TestBase;
import com.hanoseok.serialization.ProtostuffSerializer;
import com.hanoseok.serialization.SerializableSerializer;
import com.hanoseok.serialization.Serializer;
import org.junit.Test;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by hanoseok on 2014. 4. 5..
 */
public class Main extends TestBase implements Serializable {

//	RedisCacheService jedis = new ShardedRedisCacheService();
	String a = "jilfds";

	public String getA(){
		return a + "test" + a;
	}

	@Test
	public void test() throws Exception {
		System.setProperty("protostuff.runtime.collection_schema_on_repeated_fields", "true");
		System.out.println(getA());
		LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 400);
		Serializer<Main> protostuffSerializer = ProtostuffSerializer.make(Main.class);
		byte[] temp1 = protostuffSerializer.serialize(new Main());
		System.out.println(new String(temp1));
		System.out.println(temp1.length);

		Main m1 = protostuffSerializer.deserialize(temp1);
		System.out.println(m1.getA());

		Serializer<Main> serializer = SerializableSerializer.make(Main.class);
		byte [] temp2 = serializer.serialize(new Main());
		System.out.println(new String(temp2));
		System.out.println(temp2.length);

		Main m2 = serializer.deserialize(temp2);
		System.out.println(m2.getA());
	}


	@Test
	public void test2() throws Exception {
//		String [] array = {"?", "22", "12", "1?2", "??1", "?", "22", "12", "1?2", "??1", "1??1", "1???1", "1???2"};
//		for (String s : array) {
//			System.out.println(s + ":" + solve(s.toCharArray()));
//		}


		ReflectionCla cla = new ReflectionCla();
		Method [] methods = cla.getClass().getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method.getName());
		}
		Method m;


		m = cla.getClass().getDeclaredMethod("setA", String.class);
		m.invoke(cla, "reflection setA Test");
		System.out.println("reflection setA() : " + cla.getA());

		m = cla.getClass().getDeclaredMethod("privateSetA", String.class);
		m.setAccessible(true);
		m.invoke(cla, "reflection privateSetA Test");
		System.out.println("reflection privateSetA() : " + cla.getA());

		cla.setA("privateGetA Test");
		m = cla.getClass().getDeclaredMethod("privateGetA");
		m.setAccessible(true);
		System.out.println("reflection privateGet() : " + m.invoke(cla));

		System.out.println("final : " + cla.getA());

	}

	public String solve(char[] c) {
		boolean right = true;
		char lastChar = c[0];
		int questionCnt = (c[0] == '?')? 1 : 0;

		for (int i = 1; i < c.length; i++){
			if (c[i] == '?'){
				c[i] = getAlter(lastChar);
				questionCnt++;
			}

			if(c[i] == lastChar){
				right = false;
			}else{
				right = true;
			}
			lastChar = c[i];
		}

		if(right){
			if(questionCnt > 1)	return "unknown word";
			else return "right word";
		}
		return "wrong word";
	}

	private char getAlter(char c){
		if(c == '1') return '2';
		if(c == '2') return '1';
		else return '?';
	}



}



class ReflectionCla {
	private String a;

	private void privateSetA(String a){
		this.a = a;
	}
	private String privateGetA(){
		return a;
	}

	public String getA(){
		return a;
	}
	public void setA(String a){
		this.a = a;
	}

}