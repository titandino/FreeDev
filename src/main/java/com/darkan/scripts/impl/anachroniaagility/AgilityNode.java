package com.darkan.scripts.impl.anachroniaagility;

import com.darkan.api.util.Area;
import com.darkan.api.world.WorldObject;
import com.darkan.api.world.WorldTile;

public enum AgilityNode {
	CLIFF(new WorldObject(113738, new WorldTile(5428, 2384)), new Area(new WorldTile(5417, 2330), new WorldTile(5454, 2383))),
	CLIFF2(new WorldObject(113737, new WorldTile(5426, 2388)), new Area(new WorldTile(5425, 2386), new WorldTile(5428, 2388))),
	RUINED_TEMP(new WorldObject(113736, new WorldTile(5425, 2398)), new Area(new WorldTile(5425, 2390), new WorldTile(5426, 2397))),
	RUINED_TEMP2(new WorldObject(113735, new WorldTile(5431, 2408)), new Area(new WorldTile(5425, 2403), new WorldTile(5431, 2407))),
	CAVE_ENTRANCE(new WorldObject(113734, new WorldTile(5431, 2418)), new WorldObject(113734, new WorldTile(5481, 2456)), new Area(new WorldTile(5431, 2413), new WorldTile(5432, 2417))),
	ROOTS(new WorldObject(113733, new WorldTile(5485, 2456)), new Area(new WorldTile(5482, 2456), new WorldTile(5484, 2456))),
	//End level 30
	//Start level 50
	RUINED_TEMP3(new WorldObject(113732, new WorldTile(5505, 2463)), new Area(new WorldTile(5491, 2456), new WorldTile(5505, 2462))),
	RUINED_TEMP4(new WorldObject(113731, new WorldTile(5505, 2469)), new Area(new WorldTile(5505, 2465), new WorldTile(5505, 2468))),
	RUINED_TEMP5(new WorldObject(113730, new WorldTile(5505, 2479)), new Area(new WorldTile(5505, 2476), new WorldTile(5505, 2478))),
	RUINED_TEMP6(new WorldObject(113729, new WorldTile(5529, 2492)), new Area(new WorldTile(5503, 2481), new WorldTile(5528, 2494))),
	RUINED_TEMP7(new WorldObject(113728, new WorldTile(5537, 2492)), new Area(new WorldTile(5532, 2492), new WorldTile(5536, 2492))),
	BONES(new WorldObject(113727, new WorldTile(5565, 2452)), new Area(new WorldTile(5544, 2494), new WorldTile(5567, 2450))),
	BONES2(new WorldObject(113726, new WorldTile(5575, 2453)), new Area(new WorldTile(5571, 2452), new WorldTile(5574, 2453))),
	BONES3(new WorldObject(113725, new WorldTile(5585, 2452)), new Area(new WorldTile(5580, 2452), new WorldTile(5584, 2453))),
	//End level 50
	//Start level 70
	CLIFF_3(new WorldObject(113724, new WorldTile(5591, 2448)), new Area(new WorldTile(5590, 2450), new WorldTile(5593, 2453))),
	RUINED_TEMP8(new WorldObject(113723, new WorldTile(5602, 2433)), new Area(new WorldTile(5588, 2447), new WorldTile(5602, 2430))),
	RUINED_TEMP9(new WorldObject(113722, new WorldTile(5609, 2433)), new Area(new WorldTile(5605, 2433), new WorldTile(5608, 2433))),
	RUINED_TEMP10(new WorldObject(113721, new WorldTile(5617, 2433)), new Area(new WorldTile(5614, 2433), new WorldTile(5616, 2433))),
	RUINED_TEMP11(new WorldObject(113720, new WorldTile(5627, 2433)), new Area(new WorldTile(5622, 2433), new WorldTile(5626, 2433))),
	ROOTS2(new WorldObject(113719, new WorldTile(5642, 2425)), new Area(new WorldTile(5643, 2427), new WorldTile(5628, 2435))),
	VINE(new WorldObject(113718, new WorldTile(5644, 2420)), new Area(new WorldTile(5642, 2424), new WorldTile(5643, 2420))),
	VINE2(new WorldObject(113717, new WorldTile(5653, 2399)), new Area(new WorldTile(5650, 2420), new WorldTile(5658, 2404))),
	//End level 70
	//Start level 85
	VINE3(new WorldObject(113716, new WorldTile(5655, 2371)), new Area(new WorldTile(5640, 2377), new WorldTile(5656, 2398))),
	ROOTS3(new WorldObject(113715, new WorldTile(5676, 2363)), new Area(new WorldTile(5655, 2370), new WorldTile(5675, 2357))),
	SUNKEN_COLUMN(new WorldObject(113714, new WorldTile(5696, 2339)), new Area(new WorldTile(5678, 2364), new WorldTile(5697, 2346))),
	BIG_BLOCK(new WorldObject(113713, new WorldTile(5693, 2317)), new Area(new WorldTile(5694, 2316), new WorldTile(5701, 2338))),
	ROOTS4(new WorldObject(113712, new WorldTile(5686, 2303)), new Area(new WorldTile(5692, 2319), new WorldTile(5686, 2309))),
	ROOTS5(new WorldObject(113711, new WorldTile(5684, 2292)), new Area(new WorldTile(5693, 2302), new WorldTile(5680, 2293))),
	VINE4(new WorldObject(113710, new WorldTile(5674, 2290)), new Area(new WorldTile(5684, 2291), new WorldTile(5680, 2290))),
	VINE5(new WorldObject(113709, new WorldTile(5663, 2288)), new Area(new WorldTile(5669, 2288), new WorldTile(5674, 2290))),
	BLOCK(new WorldObject(113708, new WorldTile(5627, 2287)), new Area(new WorldTile(5662, 2282), new WorldTile(5629, 2294))),
	RUINS(new WorldObject(113707, new WorldTile(5594, 2295)), new Area(new WorldTile(5626, 2299), new WorldTile(5596, 2282))),
	VINE6(new WorldObject(113706, new WorldTile(5581, 2295)), new Area(new WorldTile(5587, 2295), new WorldTile(5593, 2295))),
	BONE4(new WorldObject(113705, new WorldTile(5577, 2289)), new Area(new WorldTile(5578, 2289), new WorldTile(5580, 2295))),
	ROCK(new WorldObject(113704, new WorldTile(5563, 2272)), new Area(new WorldTile(5565, 2272), new WorldTile(5576, 2289))),
	ROOTS6(new WorldObject(113703, new WorldTile(5553, 2246)), new Area(new WorldTile(5562, 2272), new WorldTile(5550, 2249))),
	VINE7(new WorldObject(113702, new WorldTile(5548, 2238)), new Area(new WorldTile(5553, 2245), new WorldTile(5548, 2244))),
	VINE8(new WorldObject(113701, new WorldTile(5548, 2214)), new Area(new WorldTile(5543, 2237), new WorldTile(5555, 2220))),
	RUINS2(new WorldObject(113700, new WorldTile(5524, 2182)), new Area(new WorldTile(5527, 2213), new WorldTile(5555, 2181))),
	RUINS3(new WorldObject(113699, new WorldTile(5495, 2171)), new Area(new WorldTile(5523, 2183), new WorldTile(5502, 2166))),
	PLANK(new WorldObject(113698, new WorldTile(5483, 2171)), new Area(new WorldTile(5489, 2171), new WorldTile(5494, 2171))),
	RUINED_TEMP12(new WorldObject(113697, new WorldTile(5474, 2171)), new Area(new WorldTile(5475, 2171), new WorldTile(5482, 2171))),
	COLUMN(new WorldObject(113696, new WorldTile(5456, 2180)), new Area(new WorldTile(5450, 2165), new WorldTile(5473, 2179))),
	CLIFF3(new WorldObject(113695, new WorldTile(5437, 2217)), new Area(new WorldTile(5439, 2183), new WorldTile(5461, 2220))),
	TREE(new WorldObject(113694, new WorldTile(5390, 2240)), new Area(new WorldTile(5397, 2212), new WorldTile(5436, 2246))),
	CLIFF4(new WorldObject(113693, new WorldTile(5376, 2248)), new Area(new WorldTile(5389, 2240), new WorldTile(5375, 2249))),
	VINE9(new WorldObject(113692, new WorldTile(5363, 2282)), new Area(new WorldTile(5380, 2254), new WorldTile(5369, 2282))),
	ROOTS7(new WorldObject(113691, new WorldTile(5368, 2304)), new Area(new WorldTile(5355, 2279), new WorldTile(5367, 2304))),
	VINE10(new WorldObject(113690, new WorldTile(5394, 2320)), new Area(new WorldTile(5370, 2304), new WorldTile(5393, 2320))),
	CLIFF5(new WorldObject(113689, new WorldTile(5408, 2324)), new Area(new WorldTile(5400, 2319), new WorldTile(5409, 2323))),
	CLIFF6(new WorldObject(113688, new WorldTile(5411, 2325)), new Area(new WorldTile(5408, 2326), new WorldTile(5410, 2325))),
	TEMPLE_WALL(new WorldObject(113687, new WorldTile(5415, 2324)), new Area(new WorldTile(5415, 2326), new WorldTile(5413, 2324))),
	END(null, null, new Area(new WorldTile(5417, 2320), new WorldTile(5419, 2328)));
	
	private WorldObject object;
	private WorldObject reverseObj;
	private Area area;
	
	private AgilityNode(WorldObject object, Area area) {
		this(object, null, area);
	}

	private AgilityNode(WorldObject object, WorldObject reverseObj, Area area) {
		this.object = object;
		this.reverseObj = reverseObj;
		this.area = area;
	}

	public WorldObject getReverseObj() {
		return reverseObj;
	}

	public Area getArea() {
		return area;
	}

	public WorldObject getObject() {
		return object;
	}
}
