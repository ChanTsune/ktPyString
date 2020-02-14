package ktPyString

import ktPyString.properties.NumericType
import ktPyString.properties.numericType
import kotlin.math.sign


operator fun String.times(n: Int): String = this.repeat(if (n > 0) n else 0)

operator fun String.get(slice: Slice): String {
    var (start, _, step, loop) = slice.adjustIndex(this.length)
    var result = ""

    for (i in 0 until loop) {
        result += this[start]
        start += step
    }
    return result
}

operator fun String.get(start: Int?, end: Int?, step: Int? = null): String = this[Slice(start, end, step)]

// capitalize  ... exist in kotlin

// casefoled ... Bothersome

fun String.center(width: Int, fillchar: Char = ' '): String {
    if (this.length >= width) {
        return this
    }
    var l: Int = width - length
    val r: Int = l / 2
    l = r + l % 2
    return fillchar.toString() * l + this + fillchar.toString() * r
}

fun String.count(sub: String, start: Int? = null, end: Int? = null): Int {
    val (start, end, _, length) = Slice(start, end).adjustIndex(this.length)
    if (sub.isEmpty()) {
        return length + 1
    }
    var n = this.find(sub, start, end)
    var c = 0
    while (n != -1) {
        c += 1
        n = this.find(sub, n + sub.length, end)
    }
    return c
}

fun String.endswith(suffix: String, start: Int? = null, end: Int? = null): Boolean =
    this[Slice(start, end)].endsWith(suffix)

fun String.expandtabs(tabsize: Int = 8): String = this.replace("\t", " " * tabsize)

//
//fun makeTable(text: String, target: String): Map<Char, Int> {
//    return mapOf()
//}

fun String.find(sub: String, start: Int? = null, end: Int? = null): Int {
    if (sub.isEmpty()) {
        return 0
    }
    var (start, end, _, _) = Slice(start, end).adjustIndex(this.length)
    val fin = end - sub.length
    while (start <= fin) {
        if (this[start, start + sub.length] == sub) {
            return start
        }
        ++start
    }
    return -1
}

//fun String.format() {
//
//}

//fun String.format_map() {
//
//}

fun String.index(sub: String, start: Int? = null, end: Int? = null): Int {
    val tmp = this.find(sub, start, end)
    return if (tmp == -1) throw Exception("ValueError: substring not found") else tmp
}

private fun String.isX(empty: Boolean, conditional: (Char) -> Boolean): Boolean {
    if (this.isEmpty()) {
        return empty
    }
    for (i in this) {
        if (!conditional(i)) {
            return false
        }
    }
    return true
}

fun String.isalnum(): Boolean {
    return this.isX(false) {
        it.isLetterOrDigit() || it.category == CharCategory.LETTER_NUMBER
    }
}

fun String.isalpha(): Boolean {
    return this.isX(false) {
        it.isLetter()
    }
}

fun String.isascii(): Boolean {
    return this.isX(true) {
        it in '\u0000'..'\u0080'
    }
}

fun String.isdecimal(): Boolean {
    return this.isX(false) {
        it.category == CharCategory.DECIMAL_DIGIT_NUMBER
    }
}

fun String.isdigit(): Boolean {
    return this.isX(false) {
        it.category == CharCategory.LETTER_NUMBER ||
                it.category == CharCategory.DECIMAL_DIGIT_NUMBER
    }
}

fun String.islower(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    var hasCase = false
    for (chr in this) {
        if (chr.isCased()) {
            if (!chr.isLowerCase()) {
                return false
            }
            hasCase = true
        }
    }
    return hasCase
}

fun String.isnumeric(): Boolean {
    return this.isX(false) {
        it.category == CharCategory.LETTER_NUMBER ||
                it.category == CharCategory.DECIMAL_DIGIT_NUMBER ||
                it.category == CharCategory.OTHER_NUMBER ||
                it.numericType != NumericType.NOT_NUMERIC
    }
}

fun String.isprintable(): Boolean {
    val otherTypes = listOf(
        CharCategory.OTHER_LETTER,
        CharCategory.OTHER_NUMBER,
        CharCategory.OTHER_PUNCTUATION,
        CharCategory.OTHER_SYMBOL
    )
    val separatorTypes = listOf(
        CharCategory.LINE_SEPARATOR,
        CharCategory.SPACE_SEPARATOR,
        CharCategory.PARAGRAPH_SEPARATOR
    )
    val maybeDisPrintable = otherTypes + separatorTypes
    return this.isX(true) {
        if (maybeDisPrintable.contains(it.category)) {
            it == ' '
        } else {
            true
        }
    }
}

fun String.isspace(): Boolean {
    return this.isX(false) {
        it.isWhiteSpace()
    }
}

private fun Char.isTitle(): Boolean = this == this.toTitleCase()

fun String.istitle(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    var prevCased = false
    for (chr in this) {
        if (!prevCased) {
            if (!chr.isTitle()) {
                return false
            }
        } else if (chr.isCased()) {
            if (!chr.isLowerCase()) {
                return false
            }
        }
        prevCased = chr.isCased()
    }
    return true
}

fun String.isupper(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    var hasCase = false
    for (chr in this) {
        if (chr.isCased()) {
            if (!chr.isUpperCase()) {
                return false
            }
            hasCase = true
        }
    }
    return hasCase
}

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
    return if (chars != null) {
        this.dropWhile { c -> chars.contains(c) }
    } else {
        this.dropWhile { c -> c.isWhiteSpace() } // 空白文字を除去するパターン
    }
}

//fun String.maketrans(x: Map<Int, String?>): Map<Int, String> {
//    var table: MutableMap<Int, String> = mutableMapOf()
//    for ((k, v) in x) {
//        table[k] = v ?: ""
//    }
//    return table
//}

// fun String.maketrans(x:Map<Char,String?>):Map<Int,String> {
//     var table: MutableMap<Int, String> = mutableMapOf()
//     for ( (k, v) in x) {
//         table[k.toInt()] = v ?: ""
//     }
//     return table
// }
//fun String.maketrans(x: String, y: String, z: String = ""): Map<Int, String> {
//    var table: MutableMap<Int, String> = mutableMapOf()
//    for ((k, v) in x zip y) {
//        table[k.toInt()] = v.toString()
//    }
//    for (c in x) {
//        table[c.toInt()] = ""
//    }
//    return table
//}

fun String.partition(sep: String): Triple<String, String, String> {
    val tmp = this.split(sep, 1)
    return if (tmp.size == 2) {
        Triple(tmp[0], sep, tmp[1])
    } else {
        Triple(this, "", "")
    }
}

fun String.replace(old: String, new: String, maxcount: Int = Int.MAX_VALUE): String {
    return new.join(this.split(old, maxcount))
}

fun String.rfind(sub: String, start: Int? = null, end: Int? = null): Int {
    if (sub.isEmpty()) {
        return this.length
    }
    var (s, e, _, _) = Slice(start, end).adjustIndex(this.length)
    if ((e - s) < sub.length) {
        return -1;
    }
    s -= 1
    var fin = e - sub.length
    while (fin != s) {
        if (this[fin, fin + sub.length] == sub) {
            return fin
        }
        fin -= 1
    }
    return -1
}

fun String.rindex(sub: String, start: Int? = null, end: Int? = null): Int {
    val i = this.rfind(sub, start, end)
    return if (i == -1) throw Exception("ValueError: substring not found") else i
}

fun String.rjust(width: Int, fillchar: Char = ' '): String {
    return if (this.length >= width) {
        this
    } else {
        val filllen = width - this.length
        fillchar.toString() * filllen + this
    }
}

fun String.rpartition(sep: String): Triple<String, String, String> {
    val tmp = this.rsplit(sep, 1)
    return if (tmp.size == 2) {
        Triple(tmp[0], sep, tmp[1])
    } else {
        Triple("", "", this)
    }
}

private fun String._rsplit(sep: String, maxsplit: Int): List<String> {
    val result: MutableList<String> = mutableListOf()
    var prevIndex = Int.MAX_VALUE
    val sep_len = sep.length
    var maxsplit = maxsplit
    while (maxsplit > 0) {
        var index = this.rfind(sep, 0, prevIndex)
        if (index == -1) {
            break
        }
        index += sep_len
        result.add(0, this[index, prevIndex])
        index -= sep_len

        index -= 1
        prevIndex = index + 1

        maxsplit -= 1

        if (maxsplit <= 0) {
            break
        }
    }
    result.add(0, this[0, prevIndex])
    return result
}

private fun String._rsplit(maxsplit: Int): List<String> {
    return this.reversed()._split(maxsplit).map { str -> str.reversed() }.reversed()
}

fun String.rsplit(sep: String? = null, maxsplit: Int = -1): List<String> {
    var maxsplit = if (maxsplit.sign == -1) Int.MAX_VALUE else maxsplit
    return if (sep != null && sep.isNotEmpty()) {
        this._rsplit(sep, maxsplit)
    } else {
        this._rsplit(maxsplit)
    }
}

fun String.rstrip(chars: String? = null): String {
    return if (chars != null) {
        dropLastWhile { c -> chars.contains(c) }
    } else {
        dropLastWhile { c -> c.isWhiteSpace() }
    }
}

private fun String._split(sep: String, maxsplit: Int): List<String> {
    var maxsplit = maxsplit
    val result: MutableList<String> = mutableListOf()
    var prevIndex = 0
    val sepLen = sep.length
    while (maxsplit > 0) {
        val index = this.find(sep, prevIndex)
        if (index == -1) {
            break
        }
        result.add(this[prevIndex, index])
        prevIndex = index + sepLen

        maxsplit -= 1
    }
    result.add(this[prevIndex, null])
    return result
}

private fun String._split(maxsplit: Int): List<String> {
    val result: MutableList<String> = mutableListOf()
    var maxsplit = maxsplit
    var len = 0
    val strLength = this.length
    var strIndex = 0
    while (strLength > strIndex && maxsplit > 0) {
        val chr = this[strIndex]
        if (chr.isWhiteSpace()) {
            if (len != 0) {
                result.add(this[strIndex - len, strIndex])
                maxsplit -= 1
                len = 0
            }
        } else {
            len += 1
        }
        strIndex += 1
    }
    val tmp = this[strIndex - len, null].lstrip()
    if (tmp.isNotEmpty()) {
        result.add(tmp)
    }
    return result
}

fun String.split(sep: String? = null, maxsplit: Int = -1): List<String> {
    val maxsplit = if (maxsplit.sign == -1) Int.MAX_VALUE else maxsplit
    return if (sep != null && sep.isNotEmpty()) {
        this._split(sep, maxsplit)
    } else {
        this._split(maxsplit)
    }
}

private fun Char.isRowBoundary(): Boolean {
    return listOf(
        0xa, 0xb, 0xc, 0xd,
        0x1c, 0x1d, 0x1e,
        0x85, 0x2028, 0x2029
    ).contains(this.toInt())
}

fun String.splitlines(keepends: Boolean = false): List<String> {
    val splited: MutableList<String> = mutableListOf()
    val len = this.length
    var i = 0
    var j = 0
    while (i < len) {
        while (i < len && !this[i].isRowBoundary())
            ++i
        var eol = i
        if (i < len) {
            if (this[i] == '\r' && i + 1 < len && this[i + 1] == '\n') {
                i += 2
            } else {
                ++i
            }
            if (keepends)
                eol = i
        }
        splited.add(this[j, eol])
        j = i
    }
    if (j < len) {
        splited.add(this[j, len])
    }
    return splited
}

fun String.startswith(prefix: String, start: Int? = null, end: Int? = null): Boolean =
    this[start, end].startsWith(prefix)

fun String.strip(chars: String? = null): String = this.lstrip(chars).rstrip(chars)

fun String.swapcase(): String = this.map { c ->
    when {
        c.isLowerCase() -> c.toUpperCase()
        c.isUpperCase() -> c.toLowerCase()
        else -> c
    }.toString()
}.reduce { s1, s2 -> s1 + s2 }

fun String.title(): String {
    var titled = ""
    var prevCased = false
    for (c in this) {
        val cIsCased = c.isCased()
        titled += if (prevCased) {
            if (cIsCased && !c.isLowerCase()) {
                c.toLowerCase()
            } else {
                c
            }
        } else {
            if (c.isTitle()) {
                c
            } else {
                c.toTitleCase()
            }
        }
        prevCased = cIsCased
    }
    return titled
}

//fun String.translate(table: Map<Int, String>): String {
//    return this
//}

fun String.upper() = this.toUpperCase()

fun String.zfill(width: Int): String {
    return if (this.isEmpty() || (this[0] != '-' && this[0] != '+')) {
        this.rjust(width, '0')
    } else {
        this[0] + this[1, null].rjust(width - 1, '0')
    }
}

private fun Char.isWhiteSpace(): Boolean {
    return "\u0020\u00A0\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200A\u200B\u3000\uFEFF\u0009".contains(this)
}

private fun Char.isCased(): Boolean =
    this.isUpperCase() || this.isLowerCase() || this.isTitleCase()
