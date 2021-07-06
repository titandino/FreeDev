package com.darkan.cache.def.params;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.Map;

import com.darkan.cache.util.Utils;

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;

public class Params {
	
	private Map<Integer, Object> map = new Int2ObjectAVLTreeMap<>();
	
	public void parse(ByteBuffer buffer) {
		int size = buffer.get() & 0xFF;
		for (int index = 0; index < size; index++) {
			boolean bool = (buffer.get() & 0xFF) == 1;
			int key = Utils.getTriByte(buffer);
			Object value = bool ? Utils.getString(buffer) : buffer.getInt();
			map.put(key, value);
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(this.getClass().getName());
		result.append(" {");
		result.append(newLine);

		// determine fields declared in this class only (no fields of
		// superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		// print field names paired with their values
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()))
				continue;
			result.append("  ");
			try {
				result.append(field.getType().getCanonicalName() + " " + field.getName() + ": ");
				result.append(Utils.getFieldValue(this, field));
			} catch (Throwable ex) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}
}
