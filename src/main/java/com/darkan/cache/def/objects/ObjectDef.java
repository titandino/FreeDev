package com.darkan.cache.def.objects;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.darkan.cache.Cache;
import com.darkan.cache.def.params.Params;
import com.darkan.cache.util.Utils;
import com.darkan.kraken.world.ObjectType;

public class ObjectDef {
	
	private static ObjectDefParser PARSER = new ObjectDefParser();
	
	public static ObjectDef get(int id) {
		return PARSER.get(Cache.get(), id);
	}

	public ObjectType[] types;
	public int[][] modelIds;
	public String name;
	public int sizeX;
	public int sizeY;
	public int clipType;
	public boolean blocks;
	public int interactable;
	public byte groundContoured;
	public boolean delayShading;
	public int occludes;
	public int[] animations;
	public int decorDisplacement;
	public byte ambient;
	public byte contrast;
	public String[] options = new String[5];
	public short[] originalColors;
	public short[] modifiedColors;
	public short[] originalTextures;
	public short[] modifiedTextures;
	public byte[] aByteArray5641;
	public boolean inverted;
	public boolean castsShadow;
	public int scaleX;
	public int scaleY;
	public int scaleZ;
	public int accessBlockFlag;
	public int offsetX;
	public int offsetY;
	public int offsetZ;
	public boolean obstructsGround;
	public int supportsItems;
	public boolean ignoreAltClip;
	public int varpBit;
	public int varp;
	public int[] transformTo;
	public int ambientSoundId;
	public int ambientSoundHearDistance;
	public int anInt5667;
	public int anInt5698;
	public int[] audioTracks;
	public int anInt5654;
	public boolean hidden;
	public boolean aBool5703 = true;
	public boolean aBool5702 = true;
	public boolean members;
	public boolean adjustMapSceneRotation;
	public boolean hasAnimation = false;
	public int anInt5705;
	public int anInt5665;
	public int anInt5670;
	public int anInt5666;
	public int mapSpriteRotation;
	public int mapSpriteId;
	public int ambientSoundVolume;
	public boolean flipMapSprite;
	public int[] animProbs;
	public int mapIcon;
	public int[] anIntArray5707;
	public byte aByte5644;
	public byte aByte5642;
	public byte aByte5646;
	public byte aByte5634;
	public short anInt5682;
	public short anInt5683;
	public short anInt5710;
	public int anInt5704;
	public boolean aBool5696;
	public boolean aBool5700;
	public int anInt5684;
	public int anInt5658;
	public int anInt5708;
	public int anInt5709;
	public boolean aBool5699;
	public int anInt5694;
	public boolean aBool5711;
	public Params params = new Params();
	
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
