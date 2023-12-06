import org.apache.commons.collections4.ListUtils
import org.apache.commons.lang3.StringUtils
import java.math.BigInteger

class Solution {

    fun calculateLowestLocation(data: String): BigInteger {
        var seeds: MutableList<BigInteger> = ArrayList()

        val linesByMapVariation = HashMap<MapVariation, List<String>>()
        var currentMapVariation = MapVariation.SEED_TO_SOIL
        for (line in data.lines()) {
            if (StringUtils.isBlank(line)) {
                continue
            }

            if (line.contains("seeds:")) {
                val lineWithSeedsPart = line.split(":").get(1)
                seeds = lineWithSeedsPart.split(" ").stream()
                    .filter { StringUtils.isNotBlank(it) }
                    .map { BigInteger(it) }
                    .toList()
                continue
            }

            if (line.contains("seed-to-soil map:")) {
                currentMapVariation = MapVariation.SEED_TO_SOIL
            } else if (line.contains("soil-to-fertilizer map:")) {
                currentMapVariation = MapVariation.SOIL_TO_FERTILIZER
            } else if (line.contains("fertilizer-to-water map:")) {
                currentMapVariation = MapVariation.FERTILIZER_TO_WATER
            } else if (line.contains("water-to-light map:")) {
                currentMapVariation = MapVariation.WATER_TO_LIGHT
            } else if (line.contains("light-to-temperature map:")) {
                currentMapVariation = MapVariation.LIGHT_TO_TEMPERATURE
            } else if (line.contains("temperature-to-humidity map:")) {
                currentMapVariation = MapVariation.TEMPERATURE_TO_HUMIDITY
            } else if (line.contains("humidity-to-location map:")) {
                currentMapVariation = MapVariation.HUMIDITY_TO_LOCATION
            } else {
                linesByMapVariation.merge(currentMapVariation, listOf(line), ListUtils::union)
            }
        }

        return determineLowestLocationForSeeds(seeds, linesByMapVariation)
    }

    private fun determineLowestLocationForSeeds(
        seeds: Collection<BigInteger>,
        linesByMapVariation: HashMap<MapVariation, List<String>>,
    ): BigInteger {
        return seeds.stream()
            .map { seed -> determineLocation(linesByMapVariation, seed) }
            .min(Comparator.naturalOrder()).get()
    }

    fun calculateLowestLocationFromSeedRange(data: String): BigInteger {
        val seedPairs = ArrayList<Pair<BigInteger, Int>>()

        val linesByMapVariation = HashMap<MapVariation, List<String>>()
        var currentMapVariation = MapVariation.SEED_TO_SOIL
        for (line in data.lines()) {
            if (StringUtils.isBlank(line)) {
                continue
            }

            if (line.contains("seeds:")) {
                val lineWithSeedsPart = line.split(":").get(1)
                val numbers = lineWithSeedsPart.split(" ").stream()
                    .filter { StringUtils.isNotBlank(it) }
                    .map { BigInteger(it) }
                    .toList()

                var startingNumber = BigInteger.ZERO
                var rangeLength: Int
                for ((index, number) in numbers.withIndex()) {
                    if (index % 2 == 0) {
                        startingNumber = number
                    } else {
                        rangeLength = number.toInt()
                        val pair = Pair(startingNumber, rangeLength)
                        seedPairs.add(pair)
                    }
                }
                continue
            }

            if (line.contains("seed-to-soil map:")) {
                currentMapVariation = MapVariation.SEED_TO_SOIL
            } else if (line.contains("soil-to-fertilizer map:")) {
                currentMapVariation = MapVariation.SOIL_TO_FERTILIZER
            } else if (line.contains("fertilizer-to-water map:")) {
                currentMapVariation = MapVariation.FERTILIZER_TO_WATER
            } else if (line.contains("water-to-light map:")) {
                currentMapVariation = MapVariation.WATER_TO_LIGHT
            } else if (line.contains("light-to-temperature map:")) {
                currentMapVariation = MapVariation.LIGHT_TO_TEMPERATURE
            } else if (line.contains("temperature-to-humidity map:")) {
                currentMapVariation = MapVariation.TEMPERATURE_TO_HUMIDITY
            } else if (line.contains("humidity-to-location map:")) {
                currentMapVariation = MapVariation.HUMIDITY_TO_LOCATION
            } else {
                linesByMapVariation.merge(currentMapVariation, listOf(line), ListUtils::union)
            }
        }

        return seedPairs.stream()
            .map { pair -> determineLowestLocationBySeedPair(pair, linesByMapVariation) }
            .min(Comparator.naturalOrder()).get()
    }

    private fun determineLowestLocationBySeedPair(
        pair: Pair<BigInteger, Int>,
        linesByMapVariation: HashMap<MapVariation, List<String>>,
    ): BigInteger {
        val seeds = HashSet<BigInteger>()
        val startingPoint = pair.first
        for (i in 0..pair.second - 1) {
            seeds.add(startingPoint.add(i.toBigInteger()))
        }

        return ListUtils.partition(seeds.toList(), 100).stream()
            .map { it -> determineLowestLocationForSeeds(it, linesByMapVariation) }
            .min(Comparator.naturalOrder()).get()
    }

    private fun determineLocation(
        linesByMapVersion: HashMap<MapVariation, List<String>>,
        seed: BigInteger,
    ): BigInteger {
        val soil = translateValue(MapVariation.SEED_TO_SOIL, linesByMapVersion, seed)
        val fertilizer = translateValue(MapVariation.SOIL_TO_FERTILIZER, linesByMapVersion, soil)
        val water = translateValue(MapVariation.FERTILIZER_TO_WATER, linesByMapVersion, fertilizer)
        val light = translateValue(MapVariation.WATER_TO_LIGHT, linesByMapVersion, water)
        val temperature = translateValue(MapVariation.LIGHT_TO_TEMPERATURE, linesByMapVersion, light)
        val humidity = translateValue(MapVariation.TEMPERATURE_TO_HUMIDITY, linesByMapVersion, temperature)
        val location = translateValue(MapVariation.HUMIDITY_TO_LOCATION, linesByMapVersion, humidity)
        return location
    }

    private fun translateValue(
        mapVariation: MapVariation,
        linesByMapVersion: HashMap<MapVariation, List<String>>,
        value: BigInteger,
    ): BigInteger {
        val mappingLines = linesByMapVersion.getOrDefault(mapVariation, listOf())

        val mappingLineOptional = mappingLines.stream()
            .map { it.split(" ") }
            .filter { values -> isValueInBetweenValues(values, value) }
            .findFirst()

        return mappingLineOptional
            .map { it -> mapValueAccordingToMappingLine(it, value) }
            .orElse(value)
    }

    private fun mapValueAccordingToMappingLine(
        mappingValues: List<String>,
        value: BigInteger,
    ): BigInteger {
        val sourceStartingValue = mappingValues.get(1).toBigInteger()
        val counter = value.subtract(sourceStartingValue)
        val targetStaringValue = mappingValues.get(0).toBigInteger()
        return targetStaringValue.add(counter)
    }

    private fun isValueInBetweenValues(values: List<String>, value: BigInteger): Boolean {
        val minimumValue = values.get(1).toBigInteger()
        val maximumValue = minimumValue.add(values.get(2).toBigInteger())
        return value.compareTo(minimumValue) >= 0 && value.compareTo(maximumValue) <= 0
    }

    enum class MapVariation {

        SEED_TO_SOIL,
        SOIL_TO_FERTILIZER,
        FERTILIZER_TO_WATER,
        WATER_TO_LIGHT,
        LIGHT_TO_TEMPERATURE,
        TEMPERATURE_TO_HUMIDITY,
        HUMIDITY_TO_LOCATION,
    }
}
