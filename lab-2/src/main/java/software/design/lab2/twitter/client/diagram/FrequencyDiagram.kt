package software.design.lab2.twitter.client.diagram

class FrequencyDiagram(
    private val map: Map<Int, Int>,
) {
    fun countByDuration(duration: Int): Int? = map[duration]
}
