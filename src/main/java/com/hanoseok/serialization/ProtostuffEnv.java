package com.hanoseok.serialization;

public abstract class ProtostuffEnv {
		@SuppressWarnings("unused")
		private static final ProtostuffRuntimeEnv instance = ProtostuffRuntimeEnv.instance; // ��ㅼ�� 濡���⑹�� ������

		ProtostuffEnv(Class<?> valueType) {
		}
	}