package software.design.lab2.twitter.client.diagram

data class FrequencyDiagram(
    private val map: Map<Int, Int>,
) {
    fun countByDuration(duration: Int): Int? = map[duration]
}
