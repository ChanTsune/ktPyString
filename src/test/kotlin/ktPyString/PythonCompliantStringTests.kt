package ktPyString

import kotlin.math.pow
import kotlin.test.*


class PythonCompliantStringTests {

    fun slice(i: Int?, j: Int?, k: Int? = null): Slice {
        return Slice(i, j, k)
    }

    fun divmod(x: Int, y: Int): Pair<Int, Int> {
        return (x / y) to (x % y)
    }

    fun pow(x: Int, y: Int): Int {
        return x.toDouble().pow(y).toInt()
    }

    operator fun Int.times(s: String): String {
        return s * this
    }

    val String.Companion.ASCII_LETTERS: String get() = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val String.Companion.DIGITS: String get() = "0123456789"
    fun String.startswith(p: Pair<String, String>, start: Int? = null, end: Int? = null): Boolean {
        return startswith(p.first, p.second, start = start, end = end)
    }

    fun String.startswith(p: Triple<String, String, String>, start: Int? = null, end: Int? = null): Boolean {
        return startswith(p.first, p.second, p.third, start = start, end = end)
    }

    fun String.startswith(p: List<String>, start: Int? = null, end: Int? = null): Boolean {
        return startswith(*p.toTypedArray(), start = start, end = end)
    }

    fun String.endswith(p: Pair<String, String>, start: Int? = null, end: Int? = null): Boolean {
        return endswith(p.first, p.second, start = start, end = end)
    }

    fun String.endswith(p: Triple<String, String, String>, start: Int? = null, end: Int? = null): Boolean {
        return endswith(p.first, p.second, p.third, start = start, end = end)
    }

    fun String.endswith(p: List<String>, start: Int? = null, end: Int? = null): Boolean {
        return endswith(*p.toTypedArray(), start = start, end = end)
    }

    fun String.join(iterable: Iterable<Char>): String = iterable.joinToString(separator = this)
    fun String.join(iterable: CharSequence): String = join(iterable.asIterable())

    operator fun <E> List<E>.get(slice: Slice): List<E> {
        var (start, _, step, loop) = slice.adjustIndex(size)
        val result = mutableListOf<E>()

        for (i in 0 until loop) {
            result.add(this[start])
            start += step
        }
        return result
    }

    fun <T> hash(x: T): T = x
    fun range(n: Int) = 0 until n

    operator fun <T> List<T>.times(n: Int): List<T> {
        val result = mutableListOf<T>()
        for (i in range(n)) {
            result.addAll(this)
        }
        return result
    }

    @Test
    fun test_count() {
        assertEquals(3, "aaa".count("a"))
        assertEquals(0, "aaa".count("b"))
        assertEquals(3, "aaa".count("a"))
        assertEquals(0, "aaa".count("b"))
        assertEquals(3, "aaa".count("a"))
        assertEquals(0, "aaa".count("b"))
        assertEquals(0, "aaa".count("b"))
        assertEquals(2, "aaa".count("a", 1))
        assertEquals(0, "aaa".count("a", 10))
        assertEquals(1, "aaa".count("a", -1))
        assertEquals(3, "aaa".count("a", -10))
        assertEquals(1, "aaa".count("a", 0, 1))
        assertEquals(3, "aaa".count("a", 0, 10))
        assertEquals(2, "aaa".count("a", 0, -1))
        assertEquals(0, "aaa".count("a", 0, -10))
        assertEquals(3, "aaa".count("", 1))
        assertEquals(1, "aaa".count("", 3))
        assertEquals(0, "aaa".count("", 10))
        assertEquals(2, "aaa".count("", -1))
        assertEquals(4, "aaa".count("", -10))
        assertEquals(1, "".count(""))
        assertEquals(0, "".count("", 1, 1))
        assertEquals(0, "".count("", Int.MAX_VALUE, 0))
        assertEquals(0, "".count("xx"))
        assertEquals(0, "".count("xx", 1, 1))
        assertEquals(0, "".count("xx", Int.MAX_VALUE, 0))
        var charset = listOf("", "a", "b")
        var digits = 7
        var base = charset.size
        var teststrings = mutableSetOf<String>()
        for (i in range(pow(base, digits))) {
            var i = i
            val entry = mutableListOf<String>()
            for (j in range(digits)) {
                val (_i, m) = divmod(i, base)
                i = _i
                entry.add(charset[m])
            }
            teststrings.add("".join(entry))
        }
        for (i in teststrings) {
            val n = i.length
            for (j in teststrings) {
                val r1 = i.count(j)
                val (r2, rem) = if (j.isNotEmpty()) {
                    divmod(n - i.replace(j, "").length, j.length)
                } else {
                    i.length + 1 to 0
                }
                if (rem or r1 != r2) {
                    assertEquals(rem, 0, "%s != 0 for %s" % Pair(rem, i))
                    assertEquals(r1, r2, "%s != %s for %s" % Triple(r1, r2, i))
                }
            }
        }
    }

    @Test
    fun test_find() {
        assertEquals(0, "abcdefghiabc".find("abc"))
        assertEquals(9, "abcdefghiabc".find("abc", 1))
        assertEquals(-1, "abcdefghiabc".find("def", 4))
        assertEquals(0, "abc".find("", 0))
        assertEquals(3, "abc".find("", 3))
        assertEquals(-1, "abc".find("", 4))
        assertEquals(2, "rrarrrrrrrrra".find("a"))
        assertEquals(12, "rrarrrrrrrrra".find("a", 4))
        assertEquals(-1, "rrarrrrrrrrra".find("a", 4, 6))
        assertEquals(12, "rrarrrrrrrrra".find("a", 4, null))
        assertEquals(2, "rrarrrrrrrrra".find("a", null, 6))
        assertEquals(0, "".find(""))
        assertEquals(-1, "".find("", 1, 1))
        assertEquals(-1, "".find("", Int.MAX_VALUE, 0))
        assertEquals(-1, "".find("xx"))
        assertEquals(-1, "".find("xx", 1, 1))
        assertEquals(-1, "".find("xx", Int.MAX_VALUE, 0))
        assertEquals(-1, "ab".find("xxx", Int.MAX_VALUE + 1, 0))
        var charset = listOf("", "a", "b", "c")
        var digits = 5
        var base = charset.size
        var teststrings = mutableSetOf<String>()
        for (i in range(pow(base, digits))) {
            var i = i
            val entry = mutableListOf<String>()
            for (j in range(digits)) {
                val (_i, m) = divmod(i, base)
                i = _i
                entry.add(charset[m])
            }
            teststrings.add("".join(entry))
        }

        for (i in teststrings) {
            for (j in teststrings) {
                val loc = i.find(j)
                val r1 = loc != -1
                val r2 = j in i
                assertEquals(r1, r2)
                if (loc != -1) {
                    assertEquals(i[loc, loc + j.length], j)
                }
            }
        }
    }

    @Test
    fun test_rfind() {
        assertEquals(9, "abcdefghiabc".rfind("abc"))
        assertEquals(12, "abcdefghiabc".rfind(""))
        assertEquals(0, "abcdefghiabc".rfind("abcd"))
        assertEquals(-1, "abcdefghiabc".rfind("abcz"))
        assertEquals(3, "abc".rfind("", 0))
        assertEquals(3, "abc".rfind("", 3))
        assertEquals(-1, "abc".rfind("", 4))
        assertEquals(12, "rrarrrrrrrrra".rfind("a"))
        assertEquals(12, "rrarrrrrrrrra".rfind("a", 4))
        assertEquals(-1, "rrarrrrrrrrra".rfind("a", 4, 6))
        assertEquals(12, "rrarrrrrrrrra".rfind("a", 4, null))
        assertEquals(2, "rrarrrrrrrrra".rfind("a", null, 6))

        var charset = listOf("", "a", "b", "c")
        var digits = 5
        var base = charset.size
        var teststrings = mutableSetOf<String>()
        for (i in range(pow(base, digits))) {
            var i = i
            val entry = mutableListOf<String>()
            for (j in range(digits)) {
                val (_i, m) = divmod(i, base)
                i = _i
                entry.add(charset[m])
            }
            teststrings.add("".join(entry))
        }

        for (i in teststrings) {
            for (j in teststrings) {
                val loc = i.rfind(j)
                val r1 = loc != -1
                val r2 = j in i
                assertEquals(r1, r2)
            }
        }
        assertEquals(-1, "ab".rfind("xxx", Int.MAX_VALUE + 1, 0))
        assertEquals(0, "<......\u043C...".rfind("<"))
    }

    @Test
    fun test_index() {
        assertEquals(0, "abcdefghiabc".index(""))
        assertEquals(3, "abcdefghiabc".index("def"))
        assertEquals(0, "abcdefghiabc".index("abc"))
        assertEquals(9, "abcdefghiabc".index("abc", 1))
        assertFailsWith<ValueError> {
            "abcdefghiabc".index("hib")
        }
        assertFailsWith<ValueError> {
            "abcdefghiab".index("abc", 1)
        }
        assertFailsWith<ValueError> {
            "abcdefghi".index("ghi", 8)
        }
        assertFailsWith<ValueError> {
            "abcdefghi".index("ghi", -1)
        }
        assertEquals(2, "rrarrrrrrrrra".index("a"))
        assertEquals(12, "rrarrrrrrrrra".index("a", 4))
        assertFailsWith<ValueError> {
            "rrarrrrrrrrra".index("a", 4, 6)
        }
        assertEquals(12, "rrarrrrrrrrra".index("a", 4, null))
        assertEquals(2, "rrarrrrrrrrra".index("a", null, 6))

    }

    @Test
    fun test_rindex() {
        assertEquals(12, "abcdefghiabc".rindex(""))
        assertEquals(3, "abcdefghiabc".rindex("def"))
        assertEquals(9, "abcdefghiabc".rindex("abc"))
        assertEquals(0, "abcdefghiabc".rindex("abc", 0, -1))
        assertFailsWith<ValueError> {
            "abcdefghiabc".rindex("hib")
        }
        assertFailsWith<ValueError> {
            "defghiabc".rindex("def", 1)
        }
        assertFailsWith<ValueError> {
            "defghiabc".rindex("abc", 0, -1)
        }
        assertFailsWith<ValueError> {
            "abcdefghi".rindex("ghi", 0, 8)
        }
        assertFailsWith<ValueError> {
            "abcdefghi".rindex("ghi", 0, -1)
        }
        assertEquals(12, "rrarrrrrrrrra".rindex("a"))
        assertEquals(12, "rrarrrrrrrrra".rindex("a", 4))
        assertFailsWith<ValueError> {
            "rrarrrrrrrrra".rindex("a", 4, 6)
        }
        assertEquals(12, "rrarrrrrrrrra".rindex("a", 4, null))
        assertEquals(2, "rrarrrrrrrrra".rindex("a", null, 6))

    }

    @Test
    fun test_lower() {
        assertEquals("hello", "HeLLo".lower())
        assertEquals("hello", "hello".lower())

    }

    @Test
    fun test_upper() {
        assertEquals("HELLO", "HeLLo".upper())
        assertEquals("HELLO", "HELLO".upper())

    }

    @Test
    fun test_expandtabs() {
        assertEquals("abc\rab      def\ng       hi", "abc\rab\tdef\ng\thi".expandtabs())
        assertEquals("abc\rab      def\ng       hi", "abc\rab\tdef\ng\thi".expandtabs(8))
        assertEquals("abc\rab  def\ng   hi", "abc\rab\tdef\ng\thi".expandtabs(4))
        assertEquals("abc\r\nab      def\ng       hi", "abc\r\nab\tdef\ng\thi".expandtabs())
        assertEquals("abc\r\nab      def\ng       hi", "abc\r\nab\tdef\ng\thi".expandtabs(8))
        assertEquals("abc\r\nab  def\ng   hi", "abc\r\nab\tdef\ng\thi".expandtabs(4))
        assertEquals("abc\r\nab\r\ndef\ng\r\nhi", "abc\r\nab\r\ndef\ng\r\nhi".expandtabs(4))
        assertEquals("abc\rab      def\ng       hi", "abc\rab\tdef\ng\thi".expandtabs(tabsize = 8))
        assertEquals("abc\rab  def\ng   hi", "abc\rab\tdef\ng\thi".expandtabs(tabsize = 4))
        assertEquals("  a\n b", " \ta\n\tb".expandtabs(1))

    }

    @Test
    fun test_split() {
        assertEquals(listOf("a", "b", "c", "d"), "a|b|c|d".split("|"))
        assertEquals(listOf("a|b|c|d"), "a|b|c|d".split("|", 0))
        assertEquals(listOf("a", "b|c|d"), "a|b|c|d".split("|", 1))
        assertEquals(listOf("a", "b", "c|d"), "a|b|c|d".split("|", 2))
        assertEquals(listOf("a", "b", "c", "d"), "a|b|c|d".split("|", 3))
        assertEquals(listOf("a", "b", "c", "d"), "a|b|c|d".split("|", 4))
        assertEquals(listOf("a", "b", "c", "d"), "a|b|c|d".split("|", Int.MAX_VALUE - 2))
        assertEquals(listOf("a|b|c|d"), "a|b|c|d".split("|", 0))
        assertEquals(listOf("a", "", "b||c||d"), "a||b||c||d".split("|", 2))
        assertEquals(listOf("abcd"), "abcd".split("|"))
        assertEquals(listOf(""), "".split("|"))
        assertEquals(listOf("endcase ", ""), "endcase |".split("|"))
        assertEquals(listOf("", " startcase"), "| startcase".split("|"))
        assertEquals(listOf("", "bothcase", ""), "|bothcase|".split("|"))
        assertEquals(listOf("a", "", "b\u0000c\u0000d"), "a\u0000\u0000b\u0000c\u0000d".split("\u0000", 2))
        assertEquals(listOf("a") * 20, ("a|" * 20)[slice(null, -1, null)].split("|"))
        assertEquals(listOf("a") * 15 + listOf("a|a|a|a|a"), ("a|" * 20)[slice(null, -1, null)].split("|", 15))
        assertEquals(listOf("a", "b", "c", "d"), "a//b//c//d".split("//"))
        assertEquals(listOf("a", "b//c//d"), "a//b//c//d".split("//", 1))
        assertEquals(listOf("a", "b", "c//d"), "a//b//c//d".split("//", 2))
        assertEquals(listOf("a", "b", "c", "d"), "a//b//c//d".split("//", 3))
        assertEquals(listOf("a", "b", "c", "d"), "a//b//c//d".split("//", 4))
        assertEquals(listOf("a", "b", "c", "d"), "a//b//c//d".split("//", Int.MAX_VALUE - 10))
        assertEquals(listOf("a//b//c//d"), "a//b//c//d".split("//", 0))
        assertEquals(listOf("a", "", "b////c////d"), "a////b////c////d".split("//", 2))
        assertEquals(listOf("endcase ", ""), "endcase test".split("test"))
        assertEquals(listOf("", " begincase"), "test begincase".split("test"))
        assertEquals(listOf("", " bothcase ", ""), "test bothcase test".split("test"))
        assertEquals(listOf("a", "bc"), "abbbc".split("bb"))
        assertEquals(listOf("", ""), "aaa".split("aaa"))
        assertEquals(listOf("aaa"), "aaa".split("aaa", 0))
        assertEquals(listOf("ab", "ab"), "abbaab".split("ba"))
        assertEquals(listOf("aaaa"), "aaaa".split("aab"))
        assertEquals(listOf(""), "".split("aaa"))
        assertEquals(listOf("aa"), "aa".split("aaa"))
        assertEquals(listOf("A", "bobb"), "Abbobbbobb".split("bbobb"))
        assertEquals(listOf("A", "B", ""), "AbbobbBbbobb".split("bbobb"))
        assertEquals(listOf("a") * 20, ("aBLAH" * 20)[slice(null, -4, null)].split("BLAH"))
        assertEquals(listOf("a") * 20, ("aBLAH" * 20)[slice(null, -4, null)].split("BLAH", 19))
        assertEquals(listOf("a") * 18 + listOf("aBLAHa"), ("aBLAH" * 20)[slice(null, -4, null)].split("BLAH", 18))
        assertEquals(listOf("a", "b", "c", "d"), "a|b|c|d".split(sep = "|"))
        assertEquals(listOf("a", "b|c|d"), "a|b|c|d".split("|", maxsplit = 1))
        assertEquals(listOf("a", "b|c|d"), "a|b|c|d".split(sep = "|", maxsplit = 1))
        assertEquals(listOf("a", "b|c|d"), "a|b|c|d".split(maxsplit = 1, sep = "|"))
        assertEquals(listOf("a", "b c d"), "a b c d".split(maxsplit = 1))

        assertFailsWith<ValueError> {
            "hello".split("")
        }
        assertFailsWith<ValueError> {
            "hello".split("", 0)
        }
    }

    @Test
    fun test_rsplit() {
        assertEquals(listOf("a", "b", "c", "d"), "a|b|c|d".rsplit("|"))
        assertEquals(listOf("a|b|c", "d"), "a|b|c|d".rsplit("|", 1))
        assertEquals(listOf("a|b", "c", "d"), "a|b|c|d".rsplit("|", 2))
        assertEquals(listOf("a", "b", "c", "d"), "a|b|c|d".rsplit("|", 3))
        assertEquals(listOf("a", "b", "c", "d"), "a|b|c|d".rsplit("|", 4))
        assertEquals(listOf("a", "b", "c", "d"), "a|b|c|d".rsplit("|", Int.MAX_VALUE - 100))
        assertEquals(listOf("a|b|c|d"), "a|b|c|d".rsplit("|", 0))
        assertEquals(listOf("a||b||c", "", "d"), "a||b||c||d".rsplit("|", 2))
        assertEquals(listOf("abcd"), "abcd".rsplit("|"))
        assertEquals(listOf(""), "".rsplit("|"))
        assertEquals(listOf("", " begincase"), "| begincase".rsplit("|"))
        assertEquals(listOf("endcase ", ""), "endcase |".rsplit("|"))
        assertEquals(listOf("", "bothcase", ""), "|bothcase|".rsplit("|"))
        assertEquals(listOf("a\u0000\u0000b", "c", "d"), "a\u0000\u0000b\u0000c\u0000d".rsplit("\u0000", 2))
        assertEquals(listOf("a") * 20, ("a|" * 20)[slice(null, -1, null)].rsplit("|"))
        assertEquals(listOf("a|a|a|a|a") + listOf("a") * 15, ("a|" * 20)[slice(null, -1, null)].rsplit("|", 15))
        assertEquals(listOf("a", "b", "c", "d"), "a//b//c//d".rsplit("//"))
        assertEquals(listOf("a//b//c", "d"), "a//b//c//d".rsplit("//", 1))
        assertEquals(listOf("a//b", "c", "d"), "a//b//c//d".rsplit("//", 2))
        assertEquals(listOf("a", "b", "c", "d"), "a//b//c//d".rsplit("//", 3))
        assertEquals(listOf("a", "b", "c", "d"), "a//b//c//d".rsplit("//", 4))
        assertEquals(listOf("a", "b", "c", "d"), "a//b//c//d".rsplit("//", Int.MAX_VALUE - 5))
        assertEquals(listOf("a//b//c//d"), "a//b//c//d".rsplit("//", 0))
        assertEquals(listOf("a////b////c", "", "d"), "a////b////c////d".rsplit("//", 2))
        assertEquals(listOf("", " begincase"), "test begincase".rsplit("test"))
        assertEquals(listOf("endcase ", ""), "endcase test".rsplit("test"))
        assertEquals(listOf("", " bothcase ", ""), "test bothcase test".rsplit("test"))
        assertEquals(listOf("ab", "c"), "abbbc".rsplit("bb"))
        assertEquals(listOf("", ""), "aaa".rsplit("aaa"))
        assertEquals(listOf("aaa"), "aaa".rsplit("aaa", 0))
        assertEquals(listOf("ab", "ab"), "abbaab".rsplit("ba"))
        assertEquals(listOf("aaaa"), "aaaa".rsplit("aab"))
        assertEquals(listOf(""), "".rsplit("aaa"))
        assertEquals(listOf("aa"), "aa".rsplit("aaa"))
        assertEquals(listOf("bbob", "A"), "bbobbbobbA".rsplit("bbobb"))
        assertEquals(listOf("", "B", "A"), "bbobbBbbobbA".rsplit("bbobb"))
        assertEquals(listOf("a") * 20, ("aBLAH" * 20)[slice(null, -4, null)].rsplit("BLAH"))
        assertEquals(listOf("a") * 20, ("aBLAH" * 20)[slice(null, -4, null)].rsplit("BLAH", 19))
        assertEquals(listOf("aBLAHa") + listOf("a") * 18, ("aBLAH" * 20)[slice(null, -4, null)].rsplit("BLAH", 18))
        assertEquals(listOf("a", "b", "c", "d"), "a|b|c|d".rsplit(sep = "|"))
        assertEquals(listOf("a|b|c", "d"), "a|b|c|d".rsplit("|", maxsplit = 1))
        assertEquals(listOf("a|b|c", "d"), "a|b|c|d".rsplit(sep = "|", maxsplit = 1))
        assertEquals(listOf("a|b|c", "d"), "a|b|c|d".rsplit(maxsplit = 1, sep = "|"))
        assertEquals(listOf("a b c", "d"), "a b c d".rsplit(maxsplit = 1))

        assertFailsWith<ValueError> {
            "hello".rsplit("")
        }
        assertFailsWith<ValueError> {
            "hello".rsplit("", 0)
        }
    }

    @Test
    fun test_replace() {
        assertEquals("", "".replace("", ""))
        assertEquals("A", "".replace("", "A"))
        assertEquals("", "".replace("A", ""))
        assertEquals("", "".replace("A", "A"))
        assertEquals("", "".replace("", "", 100))
        assertEquals("A", "".replace("", "A", 100))
        assertEquals("", "".replace("", "", Int.MAX_VALUE))
        assertEquals("A", "A".replace("", ""))
        assertEquals("*A*", "A".replace("", "*"))
        assertEquals("*1A*1", "A".replace("", "*1"))
        assertEquals("*-#A*-#", "A".replace("", "*-#"))
        assertEquals("*-A*-A*-", "AA".replace("", "*-"))
        assertEquals("*-A*-A*-", "AA".replace("", "*-", -1))
        assertEquals("*-A*-A*-", "AA".replace("", "*-", Int.MAX_VALUE))
        assertEquals("*-A*-A*-", "AA".replace("", "*-", 4))
        assertEquals("*-A*-A*-", "AA".replace("", "*-", 3))
        assertEquals("*-A*-A", "AA".replace("", "*-", 2))
        assertEquals("*-AA", "AA".replace("", "*-", 1))
        assertEquals("AA", "AA".replace("", "*-", 0))
        assertEquals("", "A".replace("A", ""))
        assertEquals("", "AAA".replace("A", ""))
        assertEquals("", "AAA".replace("A", "", -1))
        assertEquals("", "AAA".replace("A", "", Int.MAX_VALUE))
        assertEquals("", "AAA".replace("A", "", 4))
        assertEquals("", "AAA".replace("A", "", 3))
        assertEquals("A", "AAA".replace("A", "", 2))
        assertEquals("AA", "AAA".replace("A", "", 1))
        assertEquals("AAA", "AAA".replace("A", "", 0))
        assertEquals("", "AAAAAAAAAA".replace("A", ""))
        assertEquals("BCD", "ABACADA".replace("A", ""))
        assertEquals("BCD", "ABACADA".replace("A", "", -1))
        assertEquals("BCD", "ABACADA".replace("A", "", Int.MAX_VALUE))
        assertEquals("BCD", "ABACADA".replace("A", "", 5))
        assertEquals("BCD", "ABACADA".replace("A", "", 4))
        assertEquals("BCDA", "ABACADA".replace("A", "", 3))
        assertEquals("BCADA", "ABACADA".replace("A", "", 2))
        assertEquals("BACADA", "ABACADA".replace("A", "", 1))
        assertEquals("ABACADA", "ABACADA".replace("A", "", 0))
        assertEquals("BCD", "ABCAD".replace("A", ""))
        assertEquals("BCD", "ABCADAA".replace("A", ""))
        assertEquals("BCD", "BCD".replace("A", ""))
        assertEquals("*************", "*************".replace("A", ""))
        assertEquals("^A^", ("^" + ("A" * 1000) + "^").replace("A", "", 999))
        assertEquals("", "the".replace("the", ""))
        assertEquals("ater", "theater".replace("the", ""))
        assertEquals("", "thethe".replace("the", ""))
        assertEquals("", "thethethethe".replace("the", ""))
        assertEquals("aaaa", "theatheatheathea".replace("the", ""))
        assertEquals("that", "that".replace("the", ""))
        assertEquals("thaet", "thaet".replace("the", ""))
        assertEquals("here and re", "here and there".replace("the", ""))
        assertEquals("here and re and re", "here and there and there".replace("the", "", Int.MAX_VALUE))
        assertEquals("here and re and re", "here and there and there".replace("the", "", -1))
        assertEquals("here and re and re", "here and there and there".replace("the", "", 3))
        assertEquals("here and re and re", "here and there and there".replace("the", "", 2))
        assertEquals("here and re and there", "here and there and there".replace("the", "", 1))
        assertEquals("here and there and there", "here and there and there".replace("the", "", 0))
        assertEquals("here and re and re", "here and there and there".replace("the", ""))
        assertEquals("abc", "abc".replace("the", ""))
        assertEquals("abcdefg", "abcdefg".replace("the", ""))
        assertEquals("bob", "bbobob".replace("bob", ""))
        assertEquals("bobXbob", "bbobobXbbobob".replace("bob", ""))
        assertEquals("aaaaaaa", "aaaaaaabob".replace("bob", ""))
        assertEquals("aaaaaaa", "aaaaaaa".replace("bob", ""))
        assertEquals("Who goes there?", "Who goes there?".replace("o", "o"))
        assertEquals("WhO gOes there?", "Who goes there?".replace("o", "O"))
        assertEquals("WhO gOes there?", "Who goes there?".replace("o", "O", Int.MAX_VALUE))
        assertEquals("WhO gOes there?", "Who goes there?".replace("o", "O", -1))
        assertEquals("WhO gOes there?", "Who goes there?".replace("o", "O", 3))
        assertEquals("WhO gOes there?", "Who goes there?".replace("o", "O", 2))
        assertEquals("WhO goes there?", "Who goes there?".replace("o", "O", 1))
        assertEquals("Who goes there?", "Who goes there?".replace("o", "O", 0))
        assertEquals("Who goes there?", "Who goes there?".replace("a", "q"))
        assertEquals("who goes there?", "Who goes there?".replace("W", "w"))
        assertEquals("wwho goes there?ww", "WWho goes there?WW".replace("W", "w"))
        assertEquals("Who goes there!", "Who goes there?".replace("?", "!"))
        assertEquals("Who goes there!!", "Who goes there??".replace("?", "!"))
        assertEquals("Who goes there?", "Who goes there?".replace(".", "!"))
        assertEquals("Th** ** a t**sue", "This is a tissue".replace("is", "**"))
        assertEquals("Th** ** a t**sue", "This is a tissue".replace("is", "**", Int.MAX_VALUE))
        assertEquals("Th** ** a t**sue", "This is a tissue".replace("is", "**", -1))
        assertEquals("Th** ** a t**sue", "This is a tissue".replace("is", "**", 4))
        assertEquals("Th** ** a t**sue", "This is a tissue".replace("is", "**", 3))
        assertEquals("Th** ** a tissue", "This is a tissue".replace("is", "**", 2))
        assertEquals("Th** is a tissue", "This is a tissue".replace("is", "**", 1))
        assertEquals("This is a tissue", "This is a tissue".replace("is", "**", 0))
        assertEquals("cobob", "bobob".replace("bob", "cob"))
        assertEquals("cobobXcobocob", "bobobXbobobob".replace("bob", "cob"))
        assertEquals("bobob", "bobob".replace("bot", "bot"))
        assertEquals("ReyKKjaviKK", "Reykjavik".replace("k", "KK"))
        assertEquals("ReyKKjaviKK", "Reykjavik".replace("k", "KK", -1))
        assertEquals("ReyKKjaviKK", "Reykjavik".replace("k", "KK", Int.MAX_VALUE))
        assertEquals("ReyKKjaviKK", "Reykjavik".replace("k", "KK", 2))
        assertEquals("ReyKKjavik", "Reykjavik".replace("k", "KK", 1))
        assertEquals("Reykjavik", "Reykjavik".replace("k", "KK", 0))
        assertEquals("A----B----C----", "A.B.C.".replace(".", "----"))
        assertEquals("...\u043C......&lt;", "...\u043C......<".replace("<", "&lt;"))
        assertEquals("Reykjavik", "Reykjavik".replace("q", "KK"))
        assertEquals("ham, ham, eggs and ham", "spam, spam, eggs and spam".replace("spam", "ham"))
        assertEquals("ham, ham, eggs and ham", "spam, spam, eggs and spam".replace("spam", "ham", Int.MAX_VALUE))
        assertEquals("ham, ham, eggs and ham", "spam, spam, eggs and spam".replace("spam", "ham", -1))
        assertEquals("ham, ham, eggs and ham", "spam, spam, eggs and spam".replace("spam", "ham", 4))
        assertEquals("ham, ham, eggs and ham", "spam, spam, eggs and spam".replace("spam", "ham", 3))
        assertEquals("ham, ham, eggs and spam", "spam, spam, eggs and spam".replace("spam", "ham", 2))
        assertEquals("ham, spam, eggs and spam", "spam, spam, eggs and spam".replace("spam", "ham", 1))
        assertEquals("spam, spam, eggs and spam", "spam, spam, eggs and spam".replace("spam", "ham", 0))
        assertEquals("bobob", "bobobob".replace("bobob", "bob"))
        assertEquals("bobobXbobob", "bobobobXbobobob".replace("bobob", "bob"))
        assertEquals("BOBOBOB", "BOBOBOB".replace("bob", "bobby"))
        assertEquals("one@two!three!", "one!two!three!".replace("!", "@", 1))
        assertEquals("onetwothree", "one!two!three!".replace("!", ""))
        assertEquals("one@two@three!", "one!two!three!".replace("!", "@", 2))
        assertEquals("one@two@three@", "one!two!three!".replace("!", "@", 3))
        assertEquals("one@two@three@", "one!two!three!".replace("!", "@", 4))
        assertEquals("one!two!three!", "one!two!three!".replace("!", "@", 0))
        assertEquals("one@two@three@", "one!two!three!".replace("!", "@"))
        assertEquals("one!two!three!", "one!two!three!".replace("x", "@"))
        assertEquals("one!two!three!", "one!two!three!".replace("x", "@", 2))
        assertEquals("-a-b-c-", "abc".replace("", "-"))
        assertEquals("-a-b-c", "abc".replace("", "-", 3))
        assertEquals("abc", "abc".replace("", "-", 0))
        assertEquals("", "".replace("", ""))
        assertEquals("abc", "abc".replace("ab", "--", 0))
        assertEquals("abc", "abc".replace("xy", "--"))
        assertEquals("", "123".replace("123", ""))
        assertEquals("", "123123".replace("123", ""))
        assertEquals("x", "123x123".replace("123", ""))

    }

    @Test
    fun test_removeprefix() {
        assertEquals("am", "spam".removeprefix("sp"))
        assertEquals("spamspam", "spamspamspam".removeprefix("spam"))
        assertEquals("spam", "spam".removeprefix("python"))
        assertEquals("spam", "spam".removeprefix("spider"))
        assertEquals("spam", "spam".removeprefix("spam and eggs"))
        assertEquals("", "".removeprefix(""))
        assertEquals("", "".removeprefix("abcde"))
        assertEquals("abcde", "abcde".removeprefix(""))
        assertEquals("", "abcde".removeprefix("abcde"))
    }

    @Test
    fun test_removesuffix() {
        assertEquals("sp", "spam".removesuffix("am"))
        assertEquals("spamspam", "spamspamspam".removesuffix("spam"))
        assertEquals("spam", "spam".removesuffix("python"))
        assertEquals("spam", "spam".removesuffix("blam"))
        assertEquals("spam", "spam".removesuffix("eggs and spam"))
        assertEquals("", "".removesuffix(""))
        assertEquals("", "".removesuffix("abcde"))
        assertEquals("abcde", "abcde".removesuffix(""))
        assertEquals("", "abcde".removesuffix("abcde"))

    }

//    @Test
//    fun test_capitalize() {
//        assertEquals(" hello ", " hello ".capitalize())
//        assertEquals("Hello ", "Hello ".capitalize())
//        assertEquals("Hello ", "hello ".capitalize())
//        assertEquals("Aaaa", "aaaa".capitalize())
//        assertEquals("Aaaa", "AaAa".capitalize())
//    }

    @Test
    fun test_additional_split() {
        assertEquals(listOf("this", "is", "the", "split", "function"), "this is the split function".split())
        assertEquals(listOf("a", "b", "c", "d"), "a b c d ".split())
        assertEquals(listOf("a", "b c d"), "a b c d".split(null, 1))
        assertEquals(listOf("a", "b", "c d"), "a b c d".split(null, 2))
        assertEquals(listOf("a", "b", "c", "d"), "a b c d".split(null, 3))
        assertEquals(listOf("a", "b", "c", "d"), "a b c d".split(null, 4))
        assertEquals(listOf("a", "b", "c", "d"), "a b c d".split(null, Int.MAX_VALUE - 1))
        assertEquals(listOf("a b c d"), "a b c d".split(null, 0))
        assertEquals(listOf("a b c d"), "  a b c d".split(null, 0))
        assertEquals(listOf("a", "b", "c  d"), "a  b  c  d".split(null, 2))
        assertEquals(listOf(), "         ".split())
        assertEquals(listOf("a"), "  a    ".split())
        assertEquals(listOf("a", "b"), "  a    b   ".split())
        assertEquals(listOf("a", "b   "), "  a    b   ".split(null, 1))
        assertEquals(listOf("a    b   c   "), "  a    b   c   ".split(null, 0))
        assertEquals(listOf("a", "b   c   "), "  a    b   c   ".split(null, 1))
        assertEquals(listOf("a", "b", "c   "), "  a    b   c   ".split(null, 2))
        assertEquals(listOf("a", "b", "c"), "  a    b   c   ".split(null, 3))
        assertEquals(listOf("a", "b"), "\n\ta \t\r b \u000B ".split())
        var aaa = " a " * 20
        assertEquals(listOf("a") * 20, aaa.split())
        assertEquals(listOf("a") + listOf(aaa[slice(4, null, null)]), aaa.split(null, 1))
        assertEquals(listOf("a") * 19 + listOf("a "), aaa.split(null, 19))
        for (b in listOf("arf\tbarf", "arf\nbarf", "arf\rbarf", "arf\u000cbarf", "arf\u000Bbarf")) {
            assertEquals(listOf("arf", "barf"), b.split())
            assertEquals(listOf("arf", "barf"), b.split(null))
            assertEquals(listOf("arf", "barf"), b.split(null, 2))
        }
    }

    @Test
    fun test_additional_rsplit() {
        assertEquals(listOf("this", "is", "the", "rsplit", "function"), "this is the rsplit function".rsplit())
        assertEquals(listOf("a", "b", "c", "d"), "a b c d ".rsplit())
        assertEquals(listOf("a b c", "d"), "a b c d".rsplit(null, 1))
        assertEquals(listOf("a b", "c", "d"), "a b c d".rsplit(null, 2))
        assertEquals(listOf("a", "b", "c", "d"), "a b c d".rsplit(null, 3))
        assertEquals(listOf("a", "b", "c", "d"), "a b c d".rsplit(null, 4))
        assertEquals(listOf("a", "b", "c", "d"), "a b c d".rsplit(null, Int.MAX_VALUE - 20))
        assertEquals(listOf("a b c d"), "a b c d".rsplit(null, 0))
        assertEquals(listOf("a b c d"), "a b c d  ".rsplit(null, 0))
        assertEquals(listOf("a  b", "c", "d"), "a  b  c  d".rsplit(null, 2))
        assertEquals(listOf(), "         ".rsplit())
        assertEquals(listOf("a"), "  a    ".rsplit())
        assertEquals(listOf("a", "b"), "  a    b   ".rsplit())
        assertEquals(listOf("  a", "b"), "  a    b   ".rsplit(null, 1))
        assertEquals(listOf("  a    b   c"), "  a    b   c   ".rsplit(null, 0))
        assertEquals(listOf("  a    b", "c"), "  a    b   c   ".rsplit(null, 1))
        assertEquals(listOf("  a", "b", "c"), "  a    b   c   ".rsplit(null, 2))
        assertEquals(listOf("a", "b", "c"), "  a    b   c   ".rsplit(null, 3))
        assertEquals(listOf("a", "b"), "\n\ta \t\r b \u000B ".rsplit(null, 88))
        var aaa = " a " * 20
        assertEquals(listOf("a") * 20, aaa.rsplit())
        assertEquals(listOf(aaa[slice(null, -4, null)]) + listOf("a"), aaa.rsplit(null, 1))
        assertEquals(listOf(" a  a") + listOf("a") * 18, aaa.rsplit(null, 18))
        for (b in listOf("arf\tbarf", "arf\nbarf", "arf\rbarf", "arf\u000cbarf", "arf\u000Bbarf")) {
            assertEquals(listOf("arf", "barf"), b.rsplit())
            assertEquals(listOf("arf", "barf"), b.rsplit(null))
            assertEquals(listOf("arf", "barf"), b.rsplit(null, 2))
        }
    }

    @Test
    fun test_strip_whitespace() {
        assertEquals("hello", "   hello   ".strip())
        assertEquals("hello   ", "   hello   ".lstrip())
        assertEquals("   hello", "   hello   ".rstrip())
        assertEquals("hello", "hello".strip())
        var b = " \t\n\r\u000c\u000Babc \t\n\r\u000c\u000B"
        assertEquals("abc", b.strip())
        assertEquals("abc \t\n\r\u000c\u000B", b.lstrip())
        assertEquals(" \t\n\r\u000c\u000Babc", b.rstrip())
        assertEquals("hello", "   hello   ".strip(null))
        assertEquals("hello   ", "   hello   ".lstrip(null))
        assertEquals("   hello", "   hello   ".rstrip(null))
        assertEquals("hello", "hello".strip(null))
    }

    @Test
    fun test_strip() {
        assertEquals("hello", "xyzzyhelloxyzzy".strip("xyz"))
        assertEquals("helloxyzzy", "xyzzyhelloxyzzy".lstrip("xyz"))
        assertEquals("xyzzyhello", "xyzzyhelloxyzzy".rstrip("xyz"))
        assertEquals("hello", "hello".strip("xyz"))
        assertEquals("", "mississippi".strip("mississippi"))
        assertEquals("mississipp", "mississippi".strip("i"))

    }

    @Test
    fun test_ljust() {
        assertEquals("abc       ", "abc".ljust(10))
        assertEquals("abc   ", "abc".ljust(6))
        assertEquals("abc", "abc".ljust(3))
        assertEquals("abc", "abc".ljust(2))
        assertEquals("abc*******", "abc".ljust(10, '*'))

    }

    @Test
    fun test_rjust() {
        assertEquals("       abc", "abc".rjust(10))
        assertEquals("   abc", "abc".rjust(6))
        assertEquals("abc", "abc".rjust(3))
        assertEquals("abc", "abc".rjust(2))
        assertEquals("*******abc", "abc".rjust(10, '*'))

    }

    @Test
    fun test_center() {
        assertEquals("   abc    ", "abc".center(10))
        assertEquals(" abc  ", "abc".center(6))
        assertEquals("abc", "abc".center(3))
        assertEquals("abc", "abc".center(2))
        assertEquals("***abc****", "abc".center(10, '*'))

    }

    @Test
    fun test_swapcase() {
        assertEquals("hEllO CoMPuTErS", "HeLLo cOmpUteRs".swapcase())

    }

    @Test
    fun test_zfill() {
        assertEquals("123", "123".zfill(2))
        assertEquals("123", "123".zfill(3))
        assertEquals("0123", "123".zfill(4))
        assertEquals("+123", "+123".zfill(3))
        assertEquals("+123", "+123".zfill(4))
        assertEquals("+0123", "+123".zfill(5))
        assertEquals("-123", "-123".zfill(3))
        assertEquals("-123", "-123".zfill(4))
        assertEquals("-0123", "-123".zfill(5))
        assertEquals("000", "".zfill(3))
        assertEquals("34", "34".zfill(1))
        assertEquals("0034", "34".zfill(4))
    }

    @Test
    fun test_islower() {
        assertEquals(false, "".islower())
        assertEquals(true, "a".islower())
        assertEquals(false, "A".islower())
        assertEquals(false, "\n".islower())
        assertEquals(true, "abc".islower())
        assertEquals(false, "aBc".islower())
        assertEquals(true, "abc\n".islower())
    }

    @Test
    fun test_isupper() {
        assertEquals(false, "".isupper())
        assertEquals(false, "a".isupper())
        assertEquals(true, "A".isupper())
        assertEquals(false, "\n".isupper())
        assertEquals(true, "ABC".isupper())
        assertEquals(false, "AbC".isupper())
        assertEquals(true, "ABC\n".isupper())
    }

    @Test
    fun test_istitle() {
        assertEquals(false, "".istitle())
        assertEquals(false, "a".istitle())
        assertEquals(true, "A".istitle())
        assertEquals(false, "\n".istitle())
        assertEquals(true, "A Titlecased Line".istitle())
        assertEquals(true, "A\nTitlecased Line".istitle())
        assertEquals(true, "A Titlecased, Line".istitle())
        assertEquals(false, "Not a capitalized String".istitle())
        assertEquals(false, "Not\ta Titlecase String".istitle())
        assertEquals(false, "Not--a Titlecase String".istitle())
        assertEquals(false, "NOT".istitle())
    }

    @Test
    fun test_isspace() {
        assertEquals(false, "".isspace())
        assertEquals(false, "a".isspace())
        assertEquals(true, " ".isspace())
        assertEquals(true, "\t".isspace())
        assertEquals(true, "\r".isspace())
        assertEquals(true, "\n".isspace())
        assertEquals(true, " \t\r\n".isspace())
        assertEquals(false, " \t\r\na".isspace())
    }

    @Test
    fun test_isalpha() {
        assertEquals(false, "".isalpha())
        assertEquals(true, "a".isalpha())
        assertEquals(true, "A".isalpha())
        assertEquals(false, "\n".isalpha())
        assertEquals(true, "abc".isalpha())
        assertEquals(false, "aBc123".isalpha())
        assertEquals(false, "abc\n".isalpha())
    }

    @Test
    fun test_isalnum() {
        assertEquals(false, "".isalnum())
        assertEquals(true, "a".isalnum())
        assertEquals(true, "A".isalnum())
        assertEquals(false, "\n".isalnum())
        assertEquals(true, "123abc456".isalnum())
        assertEquals(true, "a1b3c".isalnum())
        assertEquals(false, "aBc000 ".isalnum())
        assertEquals(false, "abc\n".isalnum())
    }

    @Test
    fun test_isascii() {
        assertEquals(true, "".isascii())
        assertEquals(true, "\u0000".isascii())
        assertEquals(true, "".isascii())
        assertEquals(true, "\u0000".isascii())
        assertEquals(false, "\u0080".isascii())
        assertEquals(false, "\u00E9".isascii())
        for (p in range(8)) {
            assertEquals(true, (" " * p + "").isascii())
            assertEquals(false, (" " * p + "\u0080").isascii())
            assertEquals(true, (" " * p + "" + " " * 8).isascii())
            assertEquals(false, (" " * p + "\u0080" + " " * 8).isascii())
        }
    }

    @Test
    fun test_isdigit() {
        assertEquals(false, "".isdigit())
        assertEquals(false, "a".isdigit())
        assertEquals(true, "0".isdigit())
        assertEquals(true, "0123456789".isdigit())
        assertEquals(false, "0123456789a".isdigit())
    }

    @Test
    fun test_title() {
        assertEquals(" Hello ", " hello ".title())
        assertEquals("Hello ", "hello ".title())
        assertEquals("Hello ", "Hello ".title())
        assertEquals("Format This As Title String", "fOrMaT thIs aS titLe String".title())
        assertEquals("Format,This-As*Title;String", "fOrMaT,thIs-aS*titLe;String".title())
        assertEquals("Getint", "getInt".title())
    }

    @Test
    fun test_splitlines() {
        assertEquals(listOf("abc", "def", "", "ghi"), "abc\ndef\n\rghi".splitlines())
        assertEquals(listOf("abc", "def", "", "ghi"), "abc\ndef\n\r\nghi".splitlines())
        assertEquals(listOf("abc", "def", "ghi"), "abc\ndef\r\nghi".splitlines())
        assertEquals(listOf("abc", "def", "ghi"), "abc\ndef\r\nghi\n".splitlines())
        assertEquals(listOf("abc", "def", "ghi", ""), "abc\ndef\r\nghi\n\r".splitlines())
        assertEquals(listOf("", "abc", "def", "ghi", ""), "\nabc\ndef\r\nghi\n\r".splitlines())
        assertEquals(listOf("", "abc", "def", "ghi", ""), "\nabc\ndef\r\nghi\n\r".splitlines(false))
        assertEquals(listOf("\n", "abc\n", "def\r\n", "ghi\n", "\r"), "\nabc\ndef\r\nghi\n\r".splitlines(true))
        assertEquals(listOf("", "abc", "def", "ghi", ""), "\nabc\ndef\r\nghi\n\r".splitlines(keepends = false))
        assertEquals(
            listOf("\n", "abc\n", "def\r\n", "ghi\n", "\r"),
            "\nabc\ndef\r\nghi\n\r".splitlines(keepends = true)
        )
    }

    @Test
    fun test_hash() {
        var a = "DNSSEC"
        var b = ""
        for (c in a) {
            b = b + c
            hash(b)
        }
        assertEquals(hash(a), hash(b))
    }

//    @Test
//    fun test_capitalize_nonascii() {
//        assertEquals("\u1FFC\u1FF3\u1FF3\u1FF3", "\u1FF3\u1FF3\u1FFC\u1FFC".capitalize())
//        assertEquals("\u24C5\u24E8\u24E3\u24D7\u24DE\u24DD", "\u24C5\u24CE\u24C9\u24BD\u24C4\u24C3".capitalize())
//        assertEquals("\u24C5\u24E8\u24E3\u24D7\u24DE\u24DD", "\u24DF\u24E8\u24E3\u24D7\u24DE\u24DD".capitalize())
//        assertEquals("\u2160\u2171\u2172", "\u2160\u2161\u2162".capitalize())
//        assertEquals("\u2160\u2171\u2172", "\u2170\u2171\u2172".capitalize())
//        assertEquals("\u019B\u1D00\u1D86\u0221\u1FB7", "\u019B\u1D00\u1D86\u0221\u1FB7".capitalize())
//    }

    @Test
    fun test_startswith() {
        assertEquals(true, "hello".startswith("he"))
        assertEquals(true, "hello".startswith("hello"))
        assertEquals(false, "hello".startswith("hello world"))
        assertEquals(true, "hello".startswith(""))
        assertEquals(false, "hello".startswith("ello"))
        assertEquals(true, "hello".startswith("ello", 1))
        assertEquals(true, "hello".startswith("o", 4))
        assertEquals(false, "hello".startswith("o", 5))
        assertEquals(true, "hello".startswith("", 5))
        assertEquals(false, "hello".startswith("lo", 6))
        assertEquals(true, "helloworld".startswith("lowo", 3))
        assertEquals(true, "helloworld".startswith("lowo", 3, 7))
        assertEquals(false, "helloworld".startswith("lowo", 3, 6))
        assertEquals(true, "".startswith("", 0, 1))
        assertEquals(true, "".startswith("", 0, 0))
        assertEquals(false, "".startswith("", 1, 0))
        assertEquals(true, "hello".startswith("he", 0, -1))
        assertEquals(true, "hello".startswith("he", -53, -1))
        assertEquals(false, "hello".startswith("hello", 0, -1))
        assertEquals(false, "hello".startswith("hello world", -1, -10))
        assertEquals(false, "hello".startswith("ello", -5))
        assertEquals(true, "hello".startswith("ello", -4))
        assertEquals(false, "hello".startswith("o", -2))
        assertEquals(true, "hello".startswith("o", -1))
        assertEquals(true, "hello".startswith("", -3, -3))
        assertEquals(false, "hello".startswith("lo", -9))
        assertEquals(true, "hello".startswith(Pair("he", "ha")))
        assertEquals(false, "hello".startswith(Pair("lo", "llo")))
        assertEquals(true, "hello".startswith(Pair("hellox", "hello")))
        assertEquals(false, "hello".startswith(listOf()))
        assertEquals(true, "helloworld".startswith(Triple("hellowo", "rld", "lowo"), 3))
        assertEquals(false, "helloworld".startswith(Triple("hellowo", "ello", "rld"), 3))
        assertEquals(true, "hello".startswith(Pair("lo", "he"), 0, -1))
        assertEquals(false, "hello".startswith(Pair("he", "hel"), 0, 1))
        assertEquals(true, "hello".startswith(Pair("he", "hel"), 0, 2))
    }

    @Test
    fun test_endswith() {
        assertEquals(true, "hello".endswith("lo"))
        assertEquals(false, "hello".endswith("he"))
        assertEquals(true, "hello".endswith(""))
        assertEquals(false, "hello".endswith("hello world"))
        assertEquals(false, "helloworld".endswith("worl"))
        assertEquals(true, "helloworld".endswith("worl", 3, 9))
        assertEquals(true, "helloworld".endswith("world", 3, 12))
        assertEquals(true, "helloworld".endswith("lowo", 1, 7))
        assertEquals(true, "helloworld".endswith("lowo", 2, 7))
        assertEquals(true, "helloworld".endswith("lowo", 3, 7))
        assertEquals(false, "helloworld".endswith("lowo", 4, 7))
        assertEquals(false, "helloworld".endswith("lowo", 3, 8))
        assertEquals(false, "ab".endswith("ab", 0, 1))
        assertEquals(false, "ab".endswith("ab", 0, 0))
        assertEquals(true, "".endswith("", 0, 1))
        assertEquals(true, "".endswith("", 0, 0))
        assertEquals(false, "".endswith("", 1, 0))
        assertEquals(true, "hello".endswith("lo", -2))
        assertEquals(false, "hello".endswith("he", -2))
        assertEquals(true, "hello".endswith("", -3, -3))
        assertEquals(false, "hello".endswith("hello world", -10, -2))
        assertEquals(false, "helloworld".endswith("worl", -6))
        assertEquals(true, "helloworld".endswith("worl", -5, -1))
        assertEquals(true, "helloworld".endswith("worl", -5, 9))
        assertEquals(true, "helloworld".endswith("world", -7, 12))
        assertEquals(true, "helloworld".endswith("lowo", -99, -3))
        assertEquals(true, "helloworld".endswith("lowo", -8, -3))
        assertEquals(true, "helloworld".endswith("lowo", -7, -3))
        assertEquals(false, "helloworld".endswith("lowo", 3, -4))
        assertEquals(false, "helloworld".endswith("lowo", -8, -2))
        assertEquals(false, "hello".endswith(Pair("he", "ha")))
        assertEquals(true, "hello".endswith(Pair("lo", "llo")))
        assertEquals(true, "hello".endswith(Pair("hellox", "hello")))
        assertEquals(false, "hello".endswith(listOf()))
        assertEquals(true, "helloworld".endswith(Triple("hellowo", "rld", "lowo"), 3))
        assertEquals(false, "helloworld".endswith(Triple("hellowo", "ello", "rld"), 3, -1))
        assertEquals(true, "hello".endswith(Pair("hell", "ell"), 0, -1))
        assertEquals(false, "hello".endswith(Pair("he", "hel"), 0, 1))
        assertEquals(true, "hello".endswith(Pair("he", "hell"), 0, 4))
    }

    @Test
    fun test___contains__() {
        assertEquals(true, "".contains(""))
        assertEquals(true, "abc".contains(""))
        assertEquals(false, "abc".contains("\u0000"))
        assertEquals(true, "\u0000abc".contains("\u0000"))
        assertEquals(true, "abc\u0000".contains("\u0000"))
        assertEquals(true, "\u0000abc".contains("a"))
        assertEquals(true, "asdf".contains("asdf"))
        assertEquals(false, "asd".contains("asdf"))
        assertEquals(false, "".contains("asdf"))
    }

    @Test
    fun test_subscript() {
        assertEquals('a', "abc".get(0))
//        assertEquals('c', "abc".get(-1))
        assertEquals('a', "abc".get(0))
        assertEquals("abc", "abc".get(slice(0, 3)))
        assertEquals("abc", "abc".get(slice(0, 1000)))
        assertEquals("a", "abc".get(slice(0, 1)))
        assertEquals("", "abc".get(slice(0, 0)))
    }

    @Test
    fun test_slice() {
        assertEquals("abc", "abc".get(slice(0, 1000)))
        assertEquals("abc", "abc".get(slice(0, 3)))
        assertEquals("ab", "abc".get(slice(0, 2)))
        assertEquals("bc", "abc".get(slice(1, 3)))
        assertEquals("b", "abc".get(slice(1, 2)))
        assertEquals("", "abc".get(slice(2, 2)))
        assertEquals("", "abc".get(slice(1000, 1000)))
        assertEquals("", "abc".get(slice(2000, 1000)))
        assertEquals("", "abc".get(slice(2, 1)))
    }

    @Test
    fun test_extended_getslice() {
        val s = String.ASCII_LETTERS + String.DIGITS
        var indices = listOf(0, null, 1, 3, 41, Int.MAX_VALUE, -1, -2, -37)
        for (start in indices) {
            for (stop in indices) {
                for (step in indices[slice(1, null, null)]) {
                    val L = s[slice(start, stop, step)]
                    assertEquals("".join(L), s.get(slice(start, stop, step)))
                }
            }
        }
    }

    @Test
    fun test_mul() {
        assertEquals("", "abc".times(-1))
        assertEquals("", "abc".times(0))
        assertEquals("abc", "abc".times(1))
        assertEquals("abcabcabc", "abc".times(3))
    }

    @Test
    fun test_join() {
        assertEquals("a b c d", " ".join(listOf("a", "b", "c", "d")))
        assertEquals("abcd", "".join(listOf("a", "b", "c", "d")))
        assertEquals("bd", "".join(listOf("", "b", "", "d")))
        assertEquals("ac", "".join(listOf("a", "", "c", "")))
        assertEquals("w x y z", " ".join(listOf("w", "x", "y", "z")))
        assertEquals("abc", "a".join(listOf("abc")))
        assertEquals("z", "a".join(listOf("z")))
        assertEquals("a.b.c", ".".join(listOf("a", "b", "c")))
        for (i in listOf(5, 25, 125)) {
            assertEquals(((("a" * i) + "-") * i)[slice(null, -1, null)], "-".join(listOf("a" * i) * i))
            assertEquals(((("a" * i) + "-") * i)[slice(null, -1, null)], "-".join(listOf("a" * i) * i))
        }
        assertEquals("a b c", " ".join(listOf("a", "b", "c")))
    }

//    @Test
//    fun test_formatting() {
//        assertEquals("+hello+", "+%s+".rem("hello"))
//        assertEquals("+10+", "+%d+".rem(10))
//        assertEquals("a", "%c".rem('a'))
//        assertEquals("a", "%c".rem('a'))
//        assertEquals("\"", "%c".rem(34))
//        assertEquals("$", "%c".rem(36))
//        assertEquals("10", "%d".rem(10))
//        assertEquals("", "%c".rem(127))
//        val longvalue = Int.MAX_VALUE + 10
//        val slongvalue = longvalue.toString()
//        assertEquals(" 42", "%3d".rem(42))
//        assertEquals("42", "%d".rem(42))
//        assertEquals(slongvalue, "%d".rem(longvalue))
//        assertNotFails {
//            "%d" % longvalue
//        }
//        assertEquals("0042.00", "%07.2f".rem(42.0))
//        assertFailsWith<ValueError> {
//            "%(foo".rem(null)
//        }
//        assertEquals("bar", "%((foo))s".rem(null))
//        assertEquals(103 * "a" + "x", "%sx".rem(103 * "a"))
//        assertFailsWith<ValueError> {
//            "%10".rem(listOf(42))
//        }
//        assertFailsWith<ValueError> {
//            "%%%df" % pow(2, 64).rem(3.2)
//        }
//        assertFailsWith<ValueError> {
//            "%%.%df" % pow(2, 64).rem(3.2)
//        }
//    }
//
//    @Test
//    fun test_floatformatting() {
//        for (prec in range(100)) {
//            val format = "%%.%df" % prec
//            var value = 0.01
//            for (x in range(60)) {
//                value = value * 3.14159265359 / 3.0 * 10.0
//                assertNotFails {
//                    format % value
//                }
//            }
//        }
//    }

    @Test
    fun test_inplace_rewrites() {
        assertEquals("a", "A".lower())
        assertEquals(true, "A".isupper())
        assertEquals("A", "a".upper())
        assertEquals(true, "a".islower())
        assertEquals("a", "A".replace("A", "a"))
        assertEquals(true, "A".isupper())
        assertEquals("A", "a".capitalize())
        assertEquals(true, "a".islower())
        assertEquals("A", "a".swapcase())
        assertEquals(true, "a".islower())
        assertEquals("A", "a".title())
        assertEquals(true, "a".islower())
    }

    @Test
    fun test_partition() {
        assertEquals(Triple("this is the par", "ti", "tion method"), "this is the partition method".partition("ti"))
        var S = "http://www.python.org"
        assertEquals(Triple("http", "://", "www.python.org"), S.partition("://"))
        assertEquals(Triple("http://www.python.org", "", ""), S.partition("?"))
        assertEquals(Triple("", "http://", "www.python.org"), S.partition("http://"))
        assertEquals(Triple("http://www.python.", "org", ""), S.partition("org"))
        assertFailsWith<ValueError> {
            S.partition("")
        }
    }

    @Test
    fun test_rpartition() {
        assertEquals(Triple("this is the rparti", "ti", "on method"), "this is the rpartition method".rpartition("ti"))
        var S = "http://www.python.org"
        assertEquals(Triple("http", "://", "www.python.org"), S.rpartition("://"))
        assertEquals(Triple("", "", "http://www.python.org"), S.rpartition("?"))
        assertEquals(Triple("", "http://", "www.python.org"), S.rpartition("http://"))
        assertEquals(Triple("http://www.python.", "org", ""), S.rpartition("org"))
        assertFailsWith<ValueError> {
            S.rpartition("")
        }
    }

    @Test
    fun test_none_arguments() {
        var s = "hello"
        assertEquals(2, s.find("l", null))
        assertEquals(3, s.find("l", -2, null))
        assertEquals(2, s.find("l", null, -2))
        assertEquals(0, s.find("h", null, null))
        assertEquals(3, s.rfind("l", null))
        assertEquals(3, s.rfind("l", -2, null))
        assertEquals(2, s.rfind("l", null, -2))
        assertEquals(0, s.rfind("h", null, null))
        assertEquals(2, s.index("l", null))
        assertEquals(3, s.index("l", -2, null))
        assertEquals(2, s.index("l", null, -2))
        assertEquals(0, s.index("h", null, null))
        assertEquals(3, s.rindex("l", null))
        assertEquals(3, s.rindex("l", -2, null))
        assertEquals(2, s.rindex("l", null, -2))
        assertEquals(0, s.rindex("h", null, null))
        assertEquals(2, s.count("l", null))
        assertEquals(1, s.count("l", -2, null))
        assertEquals(1, s.count("l", null, -2))
        assertEquals(0, s.count("x", null, null))
        assertEquals(true, s.endswith("o", null))
        assertEquals(true, s.endswith("lo", -2, null))
        assertEquals(true, s.endswith("l", null, -2))
        assertEquals(false, s.endswith("x", null, null))
        assertEquals(true, s.startswith("h", null))
        assertEquals(true, s.startswith("l", -2, null))
        assertEquals(true, s.startswith("h", null, -2))
        assertEquals(false, s.startswith("x", null, null))
    }
}

private fun String.removesuffix(suffix: String): String {
    return if (endswith(suffix)) substring(0, length - suffix.length) else this
}

private fun String.removeprefix(prefix: String): String {
    return if (startswith(prefix)) substring(prefix.length) else this
}

private operator fun String.rem(x: Any?): String {
    return format(x)
}

private operator fun <T, U> String.rem(pair: Pair<T, U>): String {
    return format(pair.first, pair.second)
}

private operator fun <T, U, V> String.rem(pair: Triple<T, U, V>): String {
    return format(pair.first, pair.second, pair.third)
}
