package ktPyString


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
    var l: Int = width - length
    var r: Int = l / 2
    l = r + l % 2
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


fun String.split(suffix: String): List<String> {
    var splited: MutableList<String> = mutableListOf()
    return splited
}
