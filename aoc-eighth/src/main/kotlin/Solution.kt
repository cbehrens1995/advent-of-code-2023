import org.apache.commons.math3.util.ArithmeticUtils
import java.util.concurrent.atomic.AtomicInteger

class Solution {
    fun calculateSteps(data: String): Int {
        val instructionsByNode = HashMap<String, Pair<String, String>>()

        var navigationString = ""
        for ((index, line) in data.lines().withIndex()) {
            if (index == 0) {
                navigationString = line
                continue
            }

            if (line.isNotBlank()) {
                fillInstructionsByNode(line, instructionsByNode)
            }
        }

        val counter = AtomicInteger()
        var currentNode = "AAA"

        while (currentNode != "ZZZ") {
            currentNode = followPath(instructionsByNode, currentNode, counter, navigationString)
        }

        return counter.get()
    }

    fun calculateStepsForSimultaneousApproach(data: String): Long {
        val instructionsByNode = HashMap<String, Pair<String, String>>()

        var navigationString = ""
        for ((index, line) in data.lines().withIndex()) {
            if (index == 0) {
                navigationString = line
                continue
            }

            if (line.isNotBlank()) {
                fillInstructionsByNode(line, instructionsByNode)
            }
        }

        val startingNodes = instructionsByNode.keys.stream()
            .filter { it.endsWith(suffix = "A", ignoreCase = true) }
            .toList()

        val counters = ArrayList<Int>()
        for (startingNode in startingNodes) {
            val counter = AtomicInteger()
            var currentNode = startingNode

            while (!currentNode.endsWith("Z")) {
                currentNode = followPath(instructionsByNode, currentNode, counter, navigationString)
            }

            counters.add(counter.get())
        }

        return calculateLeastCommonMultiple(counters)
    }

    private fun calculateLeastCommonMultiple(counters: ArrayList<Int>): Long {
        var lcmValue = 1L
        for (counter in counters) {
            lcmValue = ArithmeticUtils.lcm(lcmValue, counter.toLong())
        }

        return lcmValue
    }

    private fun followPath(
        instructionsByNode: HashMap<String, Pair<String, String>>,
        currentNode: String,
        counter: AtomicInteger,
        navigationString: String,
    ): String {
        val maxLength = navigationString.length

        var position = counter.getAndIncrement()
        while (position >= maxLength) {
            position = position - maxLength
        }

        return getNextNodeByPosition(navigationString, position, instructionsByNode, currentNode)
    }

    private fun getNextNodeByPosition(
        navigationString: String,
        position: Int,
        instructionsByNode: Map<String, Pair<String, String>>,
        currentNode: String,
    ): String {
        val instructions = instructionsByNode[currentNode]!!
        val navigation = navigationString[position]
        if (navigation == 'L') {
            return instructions.first
        }

        return instructions.second
    }

    private fun fillInstructionsByNode(line: String, instructionsByNode: HashMap<String, Pair<String, String>>) {
        val lineParts = line.split(" = ")
        val node = lineParts[0]
        val instruction = lineParts[1]

        val instructions = instruction.removeSurrounding("(", ")").split(", ")
        instructionsByNode.put(node, Pair(instructions[0], instructions[1]))
    }
}
