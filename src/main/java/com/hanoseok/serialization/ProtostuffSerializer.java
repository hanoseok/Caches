package com.hanoseok.serialization;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class ProtostuffSerializer<T> extends ProtostuffEnv implements Serializer<T> {
	private final Class<T> valueType;

	public static <T> ProtostuffSerializer<T> make(Class<T> type) {
		return new ProtostuffSerializer<T>(type);
	}

	private ProtostuffSerializer(Class<T> valueType) {
		super(valueType);
		this.valueType = valueType;
	}

	@Override
	public byte[] serialize(T value) {
		LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 400);
		try {
			return ProtostuffIOUtil.toByteArray(value, RuntimeSchema.getSchema(valueType), buffer);
		} finally {
			buffer.clear();
		}
	}

	@Override
	public T deserialize(byte[] serialized) throws Exception {
		try {
			T t = getType().newInstance();
			ProtostuffIOUtil.mergeFrom(serialized, t, RuntimeSchema.getSchema(getType()));
			return t;
		} catch (InstantiationException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		} catch (RuntimeException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Class<T> getType() {
		return valueType;
	}

}
