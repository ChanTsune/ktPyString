
class Slice(stop: Int?) {
    var start: Int? = null
    var stop: Int? = stop
    var step: Int? = null
    constructor(start: Int?, stop: Int?, step: Int? = null) : this(stop) {
        this.start = start
        this.step = step
    }
    override fun toString(): String = "Slice($start, $stop, $step)"

    fun adjustIndex(length: Int): Triple<Int, Int, Int> {
        fun _PyLong_Sign(n: Int): Int =
            when {
                n == 0 -> 0
                n > 0 -> 1
                else -> -1
            }

        var step: Int = this.step ?: 1
        var start: Int
        var stop: Int
        var upper: Int
        var lower: Int

        // Convert step to an integer; raise for zero step.
        var step_sign: Int = _PyLong_Sign(step)
        if (step_sign == 0) {
            throw Exception("ValueError: slice step cannot be zero")
        }
        var step_is_negative: Boolean = step_sign < 0

        /* Find lower and upper bounds for start and stop. */
        if (step_is_negative) {
            lower = -1
            upper = length + lower
        } else {
            lower = 0
            upper = length
        }

        // Compute start.
        if (this.start == null) {
            start = if (step_is_negative) upper else lower
        } else {
            start = this.start!!

            if (_PyLong_Sign(start) < 0) {
                start += length

                if (start < lower /* Py_LT */) {
                    start = lower
                }
            } else {
                if (start > upper /* Py_GT */) {
                    start = upper
                }
            }
        }

        // Compute stop.
        if (this.stop == null) {
            stop = if (step_is_negative) lower else upper
        } else {
            stop = this.stop!!

            if (_PyLong_Sign(stop) < 0) {
                stop += length
                if (stop < lower /* Py_LT */) {
                    stop = lower
                }
            } else {
                if (stop > upper /* Py_GT */) {
                    stop = upper
                }
            }
        }
        return Triple<Int, Int, Int>(start, stop, step)
    }
}

operator fun String.times(n: Int): String = this.repeat(n)

operator fun String.get(slice: Slice): String {
    var (start, stop, step) = slice.adjustIndex(this.length)
    if (step == 1) {
        return this.substring(start, stop)
    } else {
        var result = ""
        var loop = 0
        if (step < 0) {
            if (stop < start) {
                loop = (start - stop - 1) / (-step) + 1
            }
        } else {
            if (start < stop) {
                loop = (stop - start - 1) / step + 1
            }
        }
        for (i in 0 until loop) {
            result += this[start]
            start += step
        }
        return result
    }
}

operator fun String.get(start: Int?, end: Int?, step: Int? = null): String = this[Slice(start, end, step)]

// capitalize  ... exist in kotlin

// casefoled ... Bothersome

fun String.center(width: Int, fillchar: Char = ' '): String {
    if (this.length >= width) {
        return this
    }
    var r: Int = width - length
    var l: Int = r / 2
    r = l + r % 2
    return fillchar.toString() * l + this + fillchar.toString() * r
}

fun String.count(sub: String, start: Int? = null, end: Int? = null): Int {
    return 0
}

fun String.endswith(suffix: String, start: Int? = null, end: Int? = null): Boolean = this[Slice(start, end)].endsWith(suffix)

fun String.expandtabs(tabsize: Int = 8): String = this.replace("\t", " " * tabsize)

fun String.find(sub: String, start: Int? = null, end: Int? = null): Int {
    return -1
}

fun String.index(sub: String, start: Int? = null, end: Int? = null): Int {
    val tmp = this.find(sub, start, end)
    return if (tmp == -1) throw Exception("ValueError: substring not found") else tmp
}

fun String.replace(old: String, new: String, maxcount: Int = Int.MAX_VALUE): String {
    return ""
}

// isalnum ...
// isalpha ...
// isascii ...
// isdecimal ...
// isdigit ...
// islower ...
// isnumeric ...
// isprintable ...
// isspace ...
// istitle ...
// isupper ...

fun String.join(iterable: List<String>): String {
    var result = ""
    var sep = ""
    for (item in iterable) {
        result += sep
        result += item
        sep = this
    }
    return result
}


fun String.ljust(width: Int, fillchar: Char = ' '): String {
    if (this.length >= width) {
        return this
    }
    val filllen = width - this.length
    return this + fillchar.toString() * filllen
}

fun String.lower(): String = this.toLowerCase()

fun String.lstrip(chars: String? = null): String {
    if (chars != null) {
        val str: String = chars
        return this.dropWhile { c -> str.indexOf(c) != -1 }
    }
    return this.dropWhile { c -> c == ' ' } // 空白文字を除去するパターン
}

fun String.maketrans(x:Map<Int, String?>): Map<Int, String> {
    var table: MutableMap<Int, String> = mutableMapOf()
    for ( (k, v) in x) {
        table[k] = v ?: ""
    }
    return table
}
// fun String.maketrans(x:Map<Char,String?>):Map<Int,String> {
//     var table: MutableMap<Int, String> = mutableMapOf()
//     for ( (k, v) in x) {
//         table[k.toInt()] = v ?: ""
//     }
//     return table
// }
fun String.maketrans(x:String,y:String,z:String=""):Map<Int,String> {
    var table: MutableMap<Int, String> = mutableMapOf()
    for (( k,v) in x zip y) {
        table[k.toInt()] = v.toString()
    }
    for (c in x) {
        table[c.toInt()] = ""
    }
    return table
}

fun String.partition(sep:String):Triple<String,String,String> {
    // val s = this.split(sep,1)
    // if s.size == 3 {
        return Triple("", "", "")
    // }
}

fun sample() {
    println("=== start sample ===")
    var s = "01234567890"
    println(s[null, null, -1])
    println(s[null, null, -2])
    println(s[0, 0, 2])
    println(s[2, 5])
    println(s[2, 5, 2])
    println(s[-20, -2])
    var str = "abc abc abc"
    println(str.capitalize())
    println("a".center(4))
    println("\t".expandtabs())
    println("=== end sample ===")
}

fun _main() {
    println("hw".split("a"))
    val slice = Slice(0, 10, -1)
    var slice2 = Slice(0, -2)
    var slice3 = Slice(-5, -2, -2)
    var slice4 = Slice(null, 5, -1)
    println(slice.adjustIndex(20))
    println(slice2.adjustIndex(10))
    println(slice3.adjustIndex(10))
    println(slice4.adjustIndex(10))
    println("Hello, World!")
    println("a" * 10)
    var a = "a"
    a *= 10
    println(a)
    sample()
}

fun main(args: Array<String>) {
    var lst = listOf("1","2","3")
    println(",".join(lst))
    println('a'.toInt())
}

fun String.split(suffix: String): List<String> {
    var splited: MutableList<String> = mutableListOf()
    return splited
}
