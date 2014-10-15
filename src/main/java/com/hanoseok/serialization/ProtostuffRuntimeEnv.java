/**
 *
 */
package com.hanoseok.serialization;

/**
 *
 */
public final class ProtostuffRuntimeEnv {
	public static final ProtostuffRuntimeEnv instance = new ProtostuffRuntimeEnv();
	static {
		System.setProperty("protostuff.runtime.collection_schema_on_repeated_fields", "true");
	}

	private ProtostuffRuntimeEnv() {
	}

}
