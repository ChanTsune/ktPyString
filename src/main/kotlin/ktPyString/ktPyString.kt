package ktPyString

import ktPyString.properties.NumericType
import ktPyString.properties.numericType
import kotlin.math.sign


/**
 * Returns a string containing this char sequence repeated [n] times.
 * @param n How many repeat string.
 */
operator fun String.times(n: Int): String = this.repeat(if (n > 0) n else 0)

/**
 * Return a string sub char sequence specified [slice].
 * @param slice Specify sub sequence.
 */
operator fun String.get(slice: Slice): String {
    var (start, _, step, loop) = slice.adjustIndex(length)
    var result = ""

    for (i in 0 until loop) {
        result += this[start]
        start += step
    }
    return result
}

/**
 * Return a string sub char sequence specified [start], [end] and [step].
 * @param start indices specified start.
 * @param end indices specified stop.
 * @param step indices specified step.
 */
operator fun String.get(start: Int?, end: Int?, step: Int? = null): String = this[Slice(start, end, step)]

internal fun Char.repeat(n: Int): String = String(CharArray(n) { this })

// capitalize  ... exist in kotlin

// casefoled ... Bothersome

/**
 * Return centered in a string of length width.
 * Padding is done using the specified [fillchar] (default is an ASCII space).
 * The original string is returned if width is less than or equal to String.length.
 * @param width Padded width.
 * @param fillchar Padding character. default is an ASCII space.
 */
fun String.center(width: Int, fillchar: Char = ' '): String =
    if (length >= width)
        this
    else
    (width - length).let {
        val r = it / 2
        val l = r + it % 2
        fillchar.repeat(l) + this + fillchar.repeat(r)
    }

/**
 * Return the number of non-overlapping occurrences of substring sub in the range [start, end].
 * Optional arguments [start] and [end] are interpreted as in slice notation.
 * @param sub
 * @param start indices specified start.
 * @param end indices specified stop.
 */
fun String.count(sub: String, start: Int? = null, end: Int? = null): Int {
    val (s, e, _, length) = Slice(start, end).adjustIndex(length)
    if (sub.isEmpty()) {
        return length + 1
    }
    var n = this.find(sub, s, e)
    var c = 0
    while (n != -1) {
        c += 1
        n = this.find(sub, n + sub.length, e)
    }
    return c
}

/**
 * Return True if the string ends with the specified [suffix], otherwise return False.
 * With optional [start], test beginning at that position.
 * With optional [end], stop comparing at that position.
 */
fun String.endswith(suffix: String, start: Int? = null, end: Int? = null): Boolean =
    this[Slice(start, end)].endsWith(suffix)

/**
 * Return True if the string ends with the specified [suffixes], otherwise return False.
 * With optional [start], test beginning at that position.
 * With optional [end], stop comparing at that position.
 */
fun String.endswith(vararg suffixes: String, start: Int? = null, end: Int? = null): Boolean {
    val sub = this[Slice(start, end)]
    return suffixes.any { sub.endsWith(it) }
}

/**
 * Return a copy of the string where all tab characters are replaced by one or more spaces,
 * depending on the current column and the given tab size.
 * Tab positions occur every [tabsize] characters (default is 8, giving tab positions at columns 0, 8, 16 and so on).
 * To expand the string, the current column is set to zero and the string is examined character by character.
 * If the character is a tab (\t),
 * one or more space characters are inserted in the result until the current column is equal to the next tab position.
 * (The tab character itself is not copied.)
 * If the character is a newline (\n) or return (\r), it is copied and the current column is reset to zero.
 * Any other character is copied unchanged and the current column is incremented by one regardless of how the character is represented when printed.
 * @param tabsize Size of tab('\t').
 */
fun String.expandtabs(tabsize: Int = 8): String {
    var result = ""
    var linePos = 0
    for (ch in this) {
        if (ch == '\t') {
            if (tabsize > 0) {
                val incr = tabsize - (linePos % tabsize)
                linePos += incr
                result += " " * incr
            }
        }
        else {
            linePos++
            result += ch
            if (ch == '\n' || ch == '\r')
                linePos = 0
        }
    }
    return result
}

/**
 * Return the lowest index in the string where substring sub is found within the slice s[[start],[end]].
 * Optional arguments start and end are interpreted as in slice notation.
 * Return -1 if sub is not found.
 * @param sub Target substring.
 * @param start Start position.
 * @param end End position.
 */
fun String.find(sub: String, start: Int? = null, end: Int? = null): Int {
    if (sub.isEmpty()) {
        return 0
    }
    var (s, e, _, _) = Slice(start, end).adjustIndex(length)
    val fin = e - sub.length
    while (s <= fin) {
        if (this[s, s + sub.length] == sub) {
            return s
        }
        ++s
    }
    return -1
}

//fun String.format() {
//
//}

//fun String.format_map() {
//
//}

/**
 * Like find(), but raise Exception when the substring is not found.
 * @param sub Target substring.
 * @param start Start position.
 * @param end End position.
 */
fun String.index(sub: String, start: Int? = null, end: Int? = null): Int {
    val tmp = this.find(sub, start, end)
    return if (tmp == -1) throw Exception("ValueError: substring not found") else tmp
}

internal inline fun String.isX(empty: Boolean, conditional: (Char) -> Boolean): Boolean =
    if (this.isEmpty()) empty else this.all(conditional)


/**
 * Return True if all characters in the string are alphanumeric and there is at least one character, False otherwise.
 * A character c is alphanumeric if one of the following returns True: c.isalpha(), c.isdecimal(), c.isdigit(), or c.isnumeric().
 */
fun String.isalnum(): Boolean {
    return this.isX(false) {
        it.isLetterOrDigit() || it.category == CharCategory.LETTER_NUMBER
    }
}

/**
 * Return True if all characters in the string are alphabetic and there is at least one character, False otherwise.
 * Alphabetic characters are those characters defined in the Unicode character database as “Letter”,
 * i.e., those with general category property being one of “Lm”, “Lt”, “Lu”, “Ll”, or “Lo”. Note that this is different from the “Alphabetic” property defined in the Unicode Standard.
 */
fun String.isalpha(): Boolean {
    return this.isX(false) {
        it.isLetter()
    }
}

/**
 * Return True if the string is empty or all characters in the string are ASCII, False otherwise.
 * ASCII characters have code points in the range U+0000-U+007F.
 */
fun String.isascii(): Boolean {
    return this.isX(true) {
        it in '\u0000'..'\u0080'
    }
}

/**
 * Return True if all characters in the string are decimal characters and there is at least one character, False otherwise.
 * Decimal characters are those that can be used to form numbers in base 10,
 * e.g. U+0660, ARABIC-INDIC DIGIT ZERO.
 * Formally a decimal character is a character in the Unicode General Category “Nd”.
 */
fun String.isdecimal(): Boolean {
    return this.isX(false) {
        it.category == CharCategory.DECIMAL_DIGIT_NUMBER
    }
}

/**
 * Return True if all characters in the string are digits and there is at least one character, False otherwise.
 * Digits include decimal characters and digits that need special handling, such as the compatibility superscript digits.
 * This covers digits which cannot be used to form numbers in base 10, like the Kharosthi numbers.
 * Formally, a digit is a character that has the property value Numeric_Type=Digit or Numeric_Type=Decimal.
 */
fun String.isdigit(): Boolean {
    return this.isX(false) {
        it.category == CharCategory.LETTER_NUMBER ||
                it.category == CharCategory.DECIMAL_DIGIT_NUMBER
    }
}

/**
 * Return True if all cased characters in the string are lowercase and there is at least one cased character, False otherwise.
 */
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

/**
 * Return True if all characters in the string are numeric characters, and there is at least one character, False otherwise.
 * Numeric characters include digit characters, and all characters that have the Unicode numeric value property,
 * e.g. U+2155, VULGAR FRACTION ONE FIFTH.
 * Formally, numeric characters are those with the property value Numeric_Type=Digit, Numeric_Type=Decimal or Numeric_Type=Numeric.
 */
fun String.isnumeric(): Boolean {
    return this.isX(false) {
        it.category == CharCategory.LETTER_NUMBER ||
                it.category == CharCategory.DECIMAL_DIGIT_NUMBER ||
                it.category == CharCategory.OTHER_NUMBER ||
                it.numericType != NumericType.NOT_NUMERIC
    }
}

/**
 * Return True if all characters in the string are printable or the string is empty, False otherwise.
 * Nonprintable characters are those characters defined in the Unicode character database as “Other” or “Separator”, excepting the ASCII space (0x20) which is considered printable.
 */
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

/**
 * Return True if there are only whitespace characters in the string and there is at least one character, False otherwise.
 * A character is whitespace if in the Unicode character database, either its general category is Zs (“Separator, space”), or its bidirectional class is one of WS, B, or S.
 */
fun String.isspace(): Boolean {
    return this.isX(false) {
        it.isWhiteSpace()
    }
}

private fun Char.isTitle(): Boolean = this == this.toTitleCase()

/**
 * Return True if the string is a titlecased string and there is at least one character,
 * for example uppercase characters may only follow uncased characters and lowercase characters only cased ones.
 * Return False otherwise.
 */
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

/**
 * Return True if all cased characters in the string are uppercase and there is at least one cased character, False otherwise.
 */
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

/**
 * Return a string which is the concatenation of the strings in [iterable].
 * @param iterable
 */
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

/**
 * Return the string left justified in a string of length width.
 * Padding is done using the specified fillchar (default is an ASCII space).
 * The original string is returned if width is less than or equal to String.length.
 * @param width Padded width.
 * @param fillchar Padding character. default is an ASCII space.
 */
fun String.ljust(width: Int, fillchar: Char = ' '): String =
    if (length >= width) this else this + fillchar.repeat(width - length)

/**
 * Return a copy of the string with all the cased characters converted to lowercase.
 */
fun String.lower(): String = this.toLowerCase()

/**
 * Return a copy of the string with leading characters removed.
 * The chars argument is a string specifying the set of characters to be removed.
 * If omitted or None, the chars argument defaults to removing whitespace.
 * The chars argument is not a prefix; rather, all combinations of its values are stripped.
 * @param chars Specifying the set of characters to be removed.
 */
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
/**
 * Split the string at the first occurrence of sep, and return a Triple containing the part before the separator, the separator itself, and the part after the separator.
 * If the separator is not found, return a Triple containing the string itself, followed by two empty strings.
 * @param sep Separator.
 */
fun String.partition(sep: String): Triple<String, String, String> {
    val tmp = this.split(sep, 1)
    return if (tmp.size == 2) {
        Triple(tmp[0], sep, tmp[1])
    } else {
        Triple(this, "", "")
    }
}

/**
 * Return a copy of the string with all occurrences of substring old replaced by new.
 * If the optional argument count is given, only the first count occurrences are replaced.
 * @param old Old String.
 * @param new New string.
 * @param maxcount　Maximum number of replacements.
 */
fun String.replace(old: String, new: String, maxcount: Int = Int.MAX_VALUE): String {
    return new.join(this.split(old, maxcount))
}

/**
 * Return the highest index in the string where substring [sub] is found, such that sub is contained within s[start:end].
 * Optional arguments start and end are interpreted as in slice notation. Return -1 on failure.
 * @param sub Target substring.
 * @param start Start position.
 * @param end End position.
 */
fun String.rfind(sub: String, start: Int? = null, end: Int? = null): Int {
    if (sub.isEmpty()) {
        return length
    }
    var (s, e, _, _) = Slice(start, end).adjustIndex(length)
    if ((e - s) < sub.length) {
        return -1
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

/**
 * Like rfind() but raises [Exception] when the substring sub is not found.
 * @param sub Target substring.
 * @param start Start position.
 * @param end End positions.
 */
fun String.rindex(sub: String, start: Int? = null, end: Int? = null): Int {
    val i = this.rfind(sub, start, end)
    return if (i == -1) throw Exception("ValueError: substring not found") else i
}

/**
 * Return the string right justified in a string of length width.
 * Padding is done using the specified fillchar (default is an ASCII space).
 * The original string is returned if width is less than or equal to String.length.
 * @param width Padded width.
 * @param fillchar Padding character. default is an ASCII space.
 */
fun String.rjust(width: Int, fillchar: Char = ' '): String =
    if (length >= width) this else fillchar.repeat(width - length) + this

/**
 * Split the string at the last occurrence of [sep], and return a Triple containing the part before the separator, the separator itself, and the part after the separator.
 * If the separator is not found, return a Triple containing two empty strings, followed by the string itself.
 * @param sep Separator.
 */
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
    val sepLen = sep.length
    var maxSplit = maxsplit
    while (maxSplit > 0) {
        var index = this.rfind(sep, 0, prevIndex)
        if (index == -1) {
            break
        }
        index += sepLen
        result.add(0, this[index, prevIndex])
        index -= sepLen

        index -= 1
        prevIndex = index + 1

        maxSplit -= 1

        if (maxSplit <= 0) {
            break
        }
    }
    result.add(0, this[0, prevIndex])
    return result
}

private fun String._rsplit(maxsplit: Int): List<String> {
    return this.reversed()._split(maxsplit).map { str -> str.reversed() }.reversed()
}

/**
 * Return a list of the words in the string, using [sep] as the delimiter string.
 * If [maxsplit] is given, at most [maxsplit] splits are done, the rightmost ones.
 * If [sep] is not specified or null, any whitespace string is a separator.
 * Except for splitting from the right, rsplit() behaves like split() which is described in detail below.
 * @param sep Separator.
 * @param maxsplit Maximum number of divisions.
 */
fun String.rsplit(sep: String? = null, maxsplit: Int = -1): List<String> {
    val maxSplit = if (maxsplit.sign == -1) Int.MAX_VALUE else maxsplit
    return if (sep != null && sep.isNotEmpty()) {
        this._rsplit(sep, maxSplit)
    } else {
        this._rsplit(maxSplit)
    }
}

/**
 * Return a copy of the string with trailing characters removed.
 * The [chars] argument is a string specifying the set of characters to be removed.
 * If omitted or None, the chars argument defaults to removing whitespace.
 * The chars argument is not a suffix; rather, all combinations of its values are stripped.
 * @param chars Specifying the set of characters to be removed.
 */
fun String.rstrip(chars: String? = null): String {
    return if (chars != null) {
        dropLastWhile { c -> chars.contains(c) }
    } else {
        dropLastWhile { c -> c.isWhiteSpace() }
    }
}

private fun String._split(sep: String, maxsplit: Int): List<String> {
    var maxSplit = maxsplit
    val result: MutableList<String> = mutableListOf()
    var prevIndex = 0
    val sepLen = sep.length
    while (maxSplit > 0) {
        val index = this.find(sep, prevIndex)
        if (index == -1) {
            break
        }
        result.add(this[prevIndex, index])
        prevIndex = index + sepLen

        maxSplit -= 1
    }
    result.add(this[prevIndex, null])
    return result
}

private fun String._split(maxsplit: Int): List<String> {
    val result: MutableList<String> = mutableListOf()
    var maxSplit = maxsplit
    var len = 0
    val strLength = this.length
    var strIndex = 0
    while (strLength > strIndex && maxSplit > 0) {
        val chr = this[strIndex]
        if (chr.isWhiteSpace()) {
            if (len != 0) {
                result.add(this[strIndex - len, strIndex])
                maxSplit -= 1
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

/**
 * Return a list of the words in the string, using [sep] as the delimiter string.
 * If [maxsplit] is given, at most [maxsplit] splits are done (thus, the list will have at most [maxsplit]+1 elements).
 * If [maxsplit] is not specified or -1, then there is no limit on the number of splits (all possible splits are made).
 * If [sep] is given, consecutive delimiters are not grouped together and are deemed to delimit empty strings (for example, '1,,2'.split(',') returns ['1', '', '2']).
 * The [sep] argument may consist of multiple characters (for example, '1<>2<>3'.split('<>') returns ['1', '2', '3']).
 * Splitting an empty string with a specified separator returns [''].
 * @param sep separator.
 * @param maxsplit Maximum number of divisions.
 */
fun String.split(sep: String? = null, maxsplit: Int = -1): List<String> {
    val maxSplit = if (maxsplit.sign == -1) Int.MAX_VALUE else maxsplit
    return if (sep != null && sep.isNotEmpty()) {
        this._split(sep, maxSplit)
    } else {
        this._split(maxSplit)
    }
}

private fun Char.isRowBoundary(): Boolean {
    return listOf(
        0xa, 0xb, 0xc, 0xd,
        0x1c, 0x1d, 0x1e,
        0x85, 0x2028, 0x2029
    ).contains(this.toInt())
}

/**
 * Return a list of the lines in the string, breaking at line boundaries.
 * Line breaks are not included in the resulting list unless [keepends] is given and true.
 * This method splits on the following line boundaries.
 * In particular, the boundaries are a superset of universal newlines.
 * @param keepends If true was given keep line breaks. Default is false.
 */
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

/**
 * Return True if string starts with the [prefix], otherwise return False.
 * With optional [start], test string beginning at that position.
 * With optional [end], stop comparing string at that position.
 */
fun String.startswith(prefix: String, start: Int? = null, end: Int? = null): Boolean =
    this[Slice(start, end)].startsWith(prefix)

/**
 * Return True if string starts with the [prefixes], otherwise return False.
 * With optional [start], test string beginning at that position.
 * With optional [end], stop comparing string at that position.
 */
fun String.startswith(vararg  prefixes: String, start: Int? = null, end: Int? = null): Boolean {
    val sub = this[Slice(start, end)]
    return prefixes.any{ sub.startsWith(it) }
}

/**
 * Return a copy of the string with the leading and trailing characters removed.
 * The chars argument is a string specifying the set of characters to be removed.
 * If omitted or None, the chars argument defaults to removing whitespace.
 * The chars argument is not a prefix or suffix; rather, all combinations of its values are stripped.
 * @param chars Specifying the set of characters to be removed.
 */
fun String.strip(chars: String? = null): String = this.lstrip(chars).rstrip(chars)

/**
 * Return a copy of the string with uppercase characters converted to lowercase and vice versa.
 * Note that it is not necessarily true that s.swapcase().swapcase() == s.
 */
fun String.swapcase(): String = this.map { c ->
    when {
        c.isLowerCase() -> c.toUpperCase()
        c.isUpperCase() -> c.toLowerCase()
        else -> c
    }.toString()
}.reduce { s1, s2 -> s1 + s2 }

/**
 * Return a titlecased version of the string where words start with an uppercase character and the remaining characters are lowercase.
 */
fun String.title(): String {
    var titled = ""
    var prevCased = false
    for (c in this) {
        val cIsCased = c.isCased()
        titled += if (prevCased) {
            when {
                cIsCased && !c.isLowerCase() -> c.toLowerCase()
                else -> c
            }
        } else {
            when {
                c.isTitle() -> c
                else -> c.toTitleCase()
            }
        }
        prevCased = cIsCased
    }
    return titled
}

//fun String.translate(table: Map<Int, String>): String {
//    return this
//}
/**
 * Return a copy of the string with all the cased characters converted to uppercase.
 * Note that s.upper().isupper() might be False if s contains uncased characters or if the Unicode category of the resulting character(s) is not “Lu” (Letter, uppercase), but e.g. “Lt” (Letter, titlecase).
 * The uppercasing algorithm used is described in section 3.13 of the Unicode Standard.
 */
fun String.upper() = this.toUpperCase()

/**
 * Return a copy of the string left filled with ASCII '0' digits to make a string of length [width].
 * A leading sign prefix ('+'/'-') is handled by inserting the padding after the sign character rather than before.
 * The original string is returned if [width] is less than or equal to String.length.
 */
fun String.zfill(width: Int): String {
    return if (this.isEmpty() || (this[0] != '-' && this[0] != '+')) {
        this.rjust(width, '0')
    } else {
        this[0] + this[1, null].rjust(width - 1, '0')
    }
}

private fun Char.isWhiteSpace(): Boolean =
    "\u0020\u00A0\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200A\u200B\u3000\uFEFF\u0009".contains(this)

private fun Char.isCased(): Boolean =
    this.isUpperCase() || this.isLowerCase() || this.isTitleCase()
