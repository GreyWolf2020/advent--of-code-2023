fun main() {
    fun part1(input: List<String>): Int = input.map {
        val digits = it.filter { it.isDigit() }
        if (digits.length >= 1) {
            val firstDigit = digits.first()
            val lastDigit = digits.last()
            firstDigit.toString() + lastDigit.toString()
        } else ""
    }
        .map {
            if (it.isBlank() || it.isEmpty())
                0
            else it.toInt()
        }
        .sum()

    fun part2(input: List<String>): Int {
        val dictionary = getNumsDictionary()
        val sumOfCalibValues = input.map { singleLine ->
            val allDigits = mutableListOf<String>()
            var head = 0
            while (head <= singleLine.lastIndex) {
                var tail = head
                while (tail <= singleLine.lastIndex && (tail - head) < 6 ) {
                    val (isPrefix, contains) = dictionary.isPreIsContained(singleLine.substring(head .. tail))
                    if (!isPrefix) {
                        break
                    }
                    if (contains) {
                        allDigits.add(singleLine.substring(head .. tail) )
                        break
                    }
                    tail++
                }
                head++
            }
            if (allDigits.size == 0) {
                ""
            } else {
                val first = allDigits.first()
                val last = allDigits.last()
                first.toIntegers() + last.toIntegers()
            }
        }.map {
            if (it.isBlank() || it.isEmpty())
                0
            else it.toInt()
        }.sum()
        return sumOfCalibValues
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

class TrieNode<Key>(var key: Key?, parent: TrieNode<Key>?) {
    val children: HashMap<Key, TrieNode<Key>> = HashMap()
    var isTerminating = false
}

class Trie<Key> {
    private val root = TrieNode<Key>(key = null, parent = null)
    fun insert(list: List<Key>) {
        var current = root
        list.forEach { element ->
            if (current.children[element] == null) {
                current.children[element] = TrieNode(element, current)
            }
            current = current.children[element]!!
        }
        current.isTerminating = true
    }
    fun contains(list: List<Key>): Boolean {
        var current = root
        list.forEach { element ->
            val child = current.children[element] ?: return false
            current = child
        }
        return current.isTerminating
    }

    fun isPrefixIsContained(list: List<Key>): Pair<Boolean, Boolean> {
        var current = root
        list.forEach { element ->
            val child = current.children[element] ?: return Pair(false, current.isTerminating)
            current = child
        }
        return Pair(true, current.isTerminating)
    }
}

fun Trie<Char>.insert(string: String) {
    insert(string.toList())
}

fun Trie<Char>.contains(string: String): Boolean {
    return contains(string.toList())
}

fun Trie<Char>.isPreIsContained(string: String): Pair<Boolean, Boolean> {
    return isPrefixIsContained(string.toList())
}

fun getNumsDictionary(): Trie<Char> = Trie<Char>().apply {
    insert("zero")
    insert("one")
    insert("two")
    insert("three")
    insert("four")
    insert("five")
    insert("six")
    insert("seven")
    insert("eight")
    insert("nine")
    for (num in 0 .. 9)
        insert(num.toString())
}

fun String.toIntegers(): String {
    val mapOfDigits = mapOf(
        "zero" to "0",
        "0" to "0",
        "one" to "1",
        "1" to "1",
        "two" to "2",
        "2" to "2",
        "three" to "3",
        "3" to "3",
        "four" to "4",
        "4" to "4",
        "five" to "5",
        "5" to "5",
        "six" to "6",
        "6" to "6",
        "seven" to "7",
        "7" to "7",
        "eight" to "8",
        "8" to "8",
        "nine" to "9",
        "9" to "9",
    )
    return mapOfDigits[this] ?: ""
}