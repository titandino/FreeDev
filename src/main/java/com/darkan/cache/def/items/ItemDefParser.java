package com.darkan.cache.def.items;

public class ItemDefParser {
/*
 * int opcode = buffer.get() & 0xFF;
            if (opcode == 0) {
                break;
            } else if (opcode == 1) {
                groundModel = BufferUtil.getSmartInt(buffer);
            } else if (opcode == 2) {
                name = BufferUtil.readString(buffer);
            } else if (opcode == 4) {
                modelZoom = buffer.getShort() & 0xFFFF;
            } else if (opcode == 5) {
                modelAngleX = buffer.getShort() & 0xFFFF;
            } else if (opcode == 6) {
                modelAngleY = buffer.getShort() & 0xFFFF;
            } else if (opcode == 7) {
                modelOffsetX = buffer.getShort() & 0xFFFF;
                if (modelOffsetX > 32767) {
                    modelOffsetX -= 65536;
                }
            } else if (opcode == 8) {
                modelOffsetY = buffer.getShort() & 0xFFFF;
                if (modelOffsetY > 32767) {
                    modelOffsetY -= 65536;
                }
            } else if (opcode == 11) {
                stackable = 1;
            } else if (opcode == 12) {
                price = buffer.getInt();
            } else if (opcode == 13) {
                equipmentSlot = buffer.get();
            } else if (opcode == 14) {
                equipmentType = buffer.get();
            } else if (opcode == 15) {
                //TODO
            } else if (opcode == 16) {
                members = true;
            } else if (opcode == 18) {
                unused = buffer.getShort() & 0xFFFF;
            } else if (opcode == 23) {
                maleModel1 = BufferUtil.getSmartInt(buffer);
            } else if (opcode == 24) {
                maleModel2 = BufferUtil.getSmartInt(buffer);
            } else if (opcode == 25) {
                femaleModel1 = BufferUtil.getSmartInt(buffer);
            } else if (opcode == 26) {
                femaleModel2 = BufferUtil.getSmartInt(buffer);
            } else if (opcode == 27) {
                equipmentType2 = buffer.get();
            } else if (opcode >= 30 && opcode < 35) {
                groundActions[opcode - 30] = BufferUtil.readString(buffer);
            } else if (opcode >= 35 && opcode < 40) {
                inventoryActions[opcode - 35] = BufferUtil.readString(buffer);
            } else if (opcode == 40) {
                int count = buffer.get() & 0xFF;
                originalColors = new short[count];
                replacementColors = new short[count];
                for (int idx = 0; idx < count; idx++) {
                    originalColors[idx] = (short) (buffer.getShort() & 0xFFFF);
                    replacementColors[idx] = (short) (buffer.getShort() & 0xFFFF);
                }
            } else if (opcode == 41) {
                int count = buffer.get() & 0xFF;
                originalTextures = new short[count];
                replacementTextures = new short[count];
                for (int idx = 0; idx < count; idx++) {
                    originalTextures[idx] = (short) (buffer.getShort() & 0xFFFF);
                    replacementTextures[idx] = (short) (buffer.getShort() & 0xFFFF);
                }
            } else if (opcode == 42) {
                int count = buffer.get() & 0xFF;
                recolorPalette = new byte[count];
                for (int index = 0; index < count; index++) {
                    recolorPalette[index] = buffer.get();
                }
            } else if (opcode == 43) {
                buffer.getInt();
            } else if (opcode == 44) {
                buffer.getShort();
            } else if (opcode == 45) {
                buffer.getShort();
            } else if (opcode == 65) {
                stockMarket = true;
            } else if (opcode == 69) {
                buffer.getInt();
            } else if (opcode == 78) {
                maleModel3 = BufferUtil.getSmartInt(buffer);
            } else if (opcode == 79) {
                femaleModel3 = BufferUtil.getSmartInt(buffer);
            } else if (opcode == 90) {
                maleHeadModel1 = BufferUtil.getSmartInt(buffer);
            } else if (opcode == 91) {
                femaleHeadModel1 = BufferUtil.getSmartInt(buffer);
            } else if (opcode == 92) {
                maleHeadModel2 = BufferUtil.getSmartInt(buffer);
            } else if (opcode == 93) {
                femaleHeadModel2 = BufferUtil.getSmartInt(buffer);
            } else if (opcode == 94) {
                buffer.getShort();
            } else if (opcode == 95) {
                modelAngleZ = buffer.getShort() & 0xFFFF;
            } else if (opcode == 96) {
                searchable = buffer.get() & 0xFF;
            } else if (opcode == 97) {
                notedItemId = buffer.getShort() & 0xFFFF;
            } else if (opcode == 98) {
                notedTemplate = buffer.getShort() & 0xFFFF;
            } else if (opcode >= 100 && opcode < 110) {
                if (stackIds == null) {
                    stackAmounts = new int[10];
                    stackIds = new int[10];
                }
                stackIds[opcode - 100] = buffer.getShort() & 0xFFFF;
                stackAmounts[opcode - 100] = buffer.getShort() & 0xFFFF;
            } else if (opcode == 110) {
                resizeX = buffer.getShort() & 0xFFFF;
            } else if (opcode == 111) {
                resizeY = buffer.getShort() & 0xFFFF;
            } else if (opcode == 112) {
                resizeZ = buffer.getShort() & 0xFFFF;
            } else if (opcode == 113) {
                ambient = buffer.get();
            } else if (opcode == 114) {
                contrast = buffer.get() * 5;
            } else if (opcode == 115) {
                teamId = buffer.get() & 0xFF;
            } else if (opcode == 121) {
                lentItemId = buffer.getShort() & 0xFFFF;
            } else if (opcode == 122) {
                lendTemplate = buffer.getShort() & 0xFFFF;
            } else if (opcode == 125) {
                maleModelOffsetX = buffer.get() << 2;
                maleModelOffsetY = buffer.get() << 2;
                maleModelOffsetZ = buffer.get() << 2;
            } else if (opcode == 126) {
                femaleModelOffsetX = buffer.get() << 2;
                femaleModelOffsetY = buffer.get() << 2;
                femaleModelOffsetZ = buffer.get() << 2;
            } else if (opcode == 127) {
                if (groundCursors == null) {
                    groundCursors = new int[6];
                    Arrays.fill(groundCursors, -1);
                }
                int groundCursorIdx1 = buffer.get() & 0xFF;
                int groundCursorId1 = buffer.getShort() & 0xFFFF;
                groundCursors[groundCursorIdx1] = groundCursorId1;
            } else if (opcode == 128) {
                if (groundCursors == null) {
                    groundCursors = new int[6];
                    Arrays.fill(groundCursors, -1);
                }
                int groundCursorIdx2 = buffer.get() & 0xFF;
                int groundCursorId2 = buffer.getShort() & 0xFFFF;
                groundCursors[groundCursorIdx2] = groundCursorId2;
            } else if (opcode == 129) {
                if (inventoryCursors == null) {
                    inventoryCursors = new int[5];
                    Arrays.fill(inventoryCursors, -1);
                }
                int invCursorIdx1 = buffer.get() & 0xFF;
                int invCursorId1 = buffer.getShort() & 0xFFFF;
                inventoryCursors[invCursorIdx1] = invCursorId1;
            } else if (opcode == 130) {
                if (inventoryCursors == null) {
                    inventoryCursors = new int[5];
                    Arrays.fill(inventoryCursors, -1);
                }
                int invCursorIdx2 = buffer.get() & 0xFF;
                int invCursorId2 = buffer.getShort() & 0xFFFF;
                inventoryCursors[invCursorIdx2] = invCursorId2;
            } else if (opcode == 132) {
                int count = buffer.get() & 0xFF;
                questIds = new int[count];
                for (int index = 0; index < count; index++) {
                    questIds[index] = buffer.getShort() & 0xFFFF;
                }
            } else if (opcode == 134) {
                anInt1919 = buffer.get() & 0xFF;
            } else if (opcode == 139) {
                boughtItemId = buffer.getShort() & 0xFFFF;
            } else if (opcode == 140) {
                boughtTemplate = buffer.getShort() & 0xFFFF;
            } else if (opcode >= 142 && opcode < 147) {
                if (groundCursors == null) {
                    groundCursors = new int[6];
                    Arrays.fill(groundCursors, -1);
                }
                groundCursors[opcode - 142] = buffer.getShort() & 0xFFFF;
            } else if (opcode == 147) {
                buffer.getShort();
            } else if (opcode >= 150 && opcode < 155) {
                if (inventoryCursors == null) {
                    inventoryCursors = new int[5];
                    Arrays.fill(inventoryCursors, -1);
                }
                inventoryCursors[opcode - 150] = buffer.getShort() & 0xFFFF;
            } else if (opcode == 161) {
                shardItemId = buffer.getShort() & 0xFFFF;
            } else if (opcode == 162) {
                shardTemplateId = buffer.getShort() & 0xFFFF;
            } else if (opcode == 163) {
                buffer.getShort();//shard amount
            } else if (opcode == 164) {
                BufferUtil.readString(buffer);//cs shard name
            } else if (opcode == 165) {
                //TODO
            } else if (opcode == 242) {
                BufferUtil.getSmartInt(buffer);
                BufferUtil.getSmartInt(buffer);
            } else if (opcode >= 243 && opcode < 249) {
                BufferUtil.getSmartInt(buffer);
            } else if (opcode == 249) {
                int size = buffer.get() & 0xFF;
                if (params == null) {
                    params = new LinkedHashMap<>(size);
                }
                for (int index = 0; index < size; index++) {
                    boolean bool = (buffer.get() & 0xFF) == 1;
                    int key = BufferUtil.readTriByte(buffer);
                    Object value = bool ? BufferUtil.readString(buffer) : buffer.getInt();
                    params.put(key, value);
                }
            } else if (opcode == 250) {
                buffer.get();
            }
 */
}
