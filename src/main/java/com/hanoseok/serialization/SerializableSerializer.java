package com.hanoseok.serialization;

import com.google.common.base.Throwables;
import com.google.common.primitives.Chars;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Shorts;

import java.io.*;
import java.nio.charset.Charset;

import static com.google.common.primitives.Primitives.isWrapperType;

/**
 * JDK의 Serializable 인터페이스를 구현한 경우 사용.
 *
 * @param <T>
 *
 */
public class SerializableSerializer<T extends Serializable> implements Serializer<T> {
	private InternalSerializer internalSerializer;



	public static <T extends Serializable> SerializableSerializer<T> make(Class<T> type) {
		return new SerializableSerializer<T>(type);
	}

	public SerializableSerializer(Class<T> type) {
		if (isWrapperType(type)) {
			if (type.equals(Long.class)) {
				this.internalSerializer = InternalSerializer.Long;
			} else if (type.equals(Integer.class)) {
				this.internalSerializer = InternalSerializer.Integer;
			} else if (type.equals(Byte.class)) {
				this.internalSerializer = InternalSerializer.Byte;
			} else if (type.equals(Boolean.class)) {
				this.internalSerializer = InternalSerializer.Boolean;
			} else if (type.equals(Character.class)) {
				this.internalSerializer = InternalSerializer.Character;
			} else if (type.equals(Double.class)) {
				this.internalSerializer = InternalSerializer.Double;
			} else if (type.equals(Float.class)) {
				this.internalSerializer = InternalSerializer.Float;
			} else if (type.equals(Short.class)) {
				this.internalSerializer = InternalSerializer.Short;
			}
		} else if (type.equals(String.class)) {
			this.internalSerializer = InternalSerializer.String;
		} else {
			this.internalSerializer = InternalSerializer.Serializable;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] serialize(T value) {
		return internalSerializer.serialize(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(byte[] serialized) throws Exception {
		return (T) internalSerializer.deserialize(serialized);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getType() {
		return internalSerializer.getType();
	}

	@SuppressWarnings("rawtypes")
	static enum InternalSerializer implements Serializer {
		String {
			Charset charset = Charset.forName("UTF-8");

			@Override
			public byte[] serialize(Object value) {
				return ((String) value).getBytes(charset);
			}

			@Override
			public String deserialize(byte[] serialized) {
				return new String(serialized, charset);
			}

			@Override
			public Class<String> getType() {
				return String.class;
			}
		},
		Long {
			@Override
			public byte[] serialize(Object value) {
				return Longs.toByteArray((Long) value);
			}

			@Override
			public Long deserialize(byte[] serialized) {
				return Longs.fromByteArray(serialized);
			}

			@Override
			public Class getType() {
				return Long.class;
			}

		},
		Integer {
			@Override
			public byte[] serialize(Object value) {
				return Ints.toByteArray((Integer) value);
			}

			@Override
			public Integer deserialize(byte[] serialized) {
				return Ints.fromByteArray(serialized);
			}

			@Override
			public Class<Integer> getType() {
				return Integer.class;
			}
		},
		Byte {
			@Override
			public byte[] serialize(Object value) {
				return new byte[]{((Byte) value)};
			}

			@Override
			public Byte deserialize(byte[] serialized) {
				return serialized[0];
			}

			@Override
			public Class<Byte> getType() {
				return Byte.class;
			}

		},
		Boolean {
			@Override
			public byte[] serialize(Object value) {
				return new byte[]{(byte) ((Boolean) value ? 0x01 : 0x00)};
			}

			@Override
			public Boolean deserialize(byte[] serialized) {
				return serialized[0] == 0x01;
			}

			@Override
			public Class<Boolean> getType() {
				return Boolean.class;
			}

		},
		Character {
			@Override
			public byte[] serialize(Object value) {
				return Chars.toByteArray((Character) value);
			}

			@Override
			public Character deserialize(byte[] serialized) {
				return Chars.fromByteArray(serialized);
			}

			@Override
			public Class<Character> getType() {
				return Character.class;
			}

		},
		Double {
			@Override
			public byte[] serialize(Object value) {
				return Longs.toByteArray(java.lang.Double.doubleToLongBits((Double) value));
			}

			@Override
			public Double deserialize(byte[] serialized) {
				return java.lang.Double.longBitsToDouble(Longs.fromByteArray(serialized));
			}

			@Override
			public Class<Double> getType() {
				return Double.class;
			}

		},
		Float {
			@Override
			public byte[] serialize(Object value) {
				return Ints.toByteArray(java.lang.Float.floatToRawIntBits((Float) value));
			}

			@Override
			public Float deserialize(byte[] serialized) {
				return java.lang.Float.intBitsToFloat(Ints.fromByteArray(serialized));
			}

			@Override
			public Class<Float> getType() {
				return Float.class;
			}

		},
		Short {
			@Override
			public byte[] serialize(Object value) {
				return Shorts.toByteArray((Short) value);
			}

			@Override
			public Short deserialize(byte[] serialized) {
				return Shorts.fromByteArray(serialized);
			}

			@Override
			public Class<Short> getType() {
				return Short.class;
			}
		},
		Serializable {
			@Override
			public byte[] serialize(Object value) {
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				ObjectOutputStream oout;
				try {
					oout = new ObjectOutputStream(bout);
					oout.writeObject(value);
					oout.reset();
					oout.close();
					bout.close();

					return bout.toByteArray();
				} catch (IOException e) {
					throw Throwables.propagate(e);
				}
			}

			@Override
			public Object deserialize(byte[] serialized) {
				try {
					ByteArrayInputStream bin = new ByteArrayInputStream(serialized);
					ObjectInputStream oin = new ObjectInputStream(bin);
					Object ret = oin.readObject();
					bin.close();
					oin.close();
					return ret;
				} catch (IOException e) {
					if (e instanceof StreamCorruptedException) {
						return null;
					} else {
						throw Throwables.propagate(e);
					}
				} catch (ClassNotFoundException e) {
					throw Throwables.propagate(e);
				}
			}

			@Override
			public Class getType() {
				return Serializable.class;
			}
		}
	}

}
