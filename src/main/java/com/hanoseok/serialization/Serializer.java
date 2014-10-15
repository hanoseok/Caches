package com.hanoseok.serialization;

/**
 * 객체 직렬화 인터페이스
 *
 * @param <T>
 */
public interface Serializer<T> {
	byte[] serialize(T value);

	T deserialize(byte[] serialized) throws Exception;

	Class<T> getType();
}
