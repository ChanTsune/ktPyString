package ktPyString

import kotlin.test.*

internal class KtPyStringKtTest {
    private val hello = "Hello World"
    private val empty = ""
    private val digits = "0123456789"
    @Test
    fun times() {
        assertEquals("Hello WorldHello World", hello * 2)
        assertEquals("", empty * 2)
        assertEquals("", hello * 0)
    }

    @Test
    fun slice() {
        assertEquals("Hello", hello[null, 5])
        assertEquals("ello", hello[1, 5])
        assertEquals(" W", hello[5, 7])
        assertEquals("orld", hello[7, null])

        val b = "Hello, world"
        assertEquals("Hello", b[null, 5])
        assertEquals("ello", b[1, 5])
        assertEquals(", ", b[5, 7])
        assertEquals("world", b[7, null])
        assertEquals("world", b[7, 12])
        assertEquals("world", b[7, 100])
        assertEquals("Hello", b[null, -7])
        assertEquals("ello", b[-11, -7])
        assertEquals(", ", b[-7, -5])
        assertEquals("world", b[-5, null])
        assertEquals("world", b[-5, 12])
        assertEquals("world", b[-5, 100])
        assertEquals("Hello", b[-100, 5])
    }

    @Test
    fun sliceEmpty() {
        assertEquals("", empty[null, 5])
        assertEquals("", empty[1, 5])
        assertEquals("", empty[5, 7])
        assertEquals("", empty[7, null])
    }

    @Test
    fun center() {
        val str = "12"
        assertEquals("  12 ", str.center(5))
        assertEquals("12", str.center(1))
        assertEquals("12", str.center(0))

        assertEquals("     ", empty.center(5))
        assertEquals("", empty.center(0))
    }

    @Test
    fun count() {
        assertEquals(5, "aaaaa".count("a"))
        assertEquals(2, "a".count(""))
        assertEquals(3, "ab".count(""))
        assertEquals(3, "bababba".count("ba"))

        val b = "mississippi"
        val i = "i"
        val p = "p"
        val w = "w"
        assertEquals(4, b.count("i"))
        assertEquals(2, b.count("ss"))
        assertEquals(0, b.count("w"))
        assertEquals(4, b.count(i))
        assertEquals(0, b.count(w))
        assertEquals(2, b.count("i", 6))
        assertEquals(2, b.count("p", 6))
        assertEquals(1, b.count("i", 1, 3))
        assertEquals(1, b.count("p", 7, 9))
        assertEquals(2, b.count(i, 6))
        assertEquals(2, b.count(p, 6))
        assertEquals(1, b.count(i, 1, 3))
        assertEquals(1, b.count(p, 7, 9))
    }

    @Test
    fun endswith() {
        assertTrue(digits.endswith("9"))
        assertTrue(digits.endswith("8", end = -1))
        assertFalse(digits.endswith("8", end = -2))
        assertFalse(digits.endswith("8"))
    }

    @Test
    fun expandtabs() {
        assertEquals("        ", "\t".expandtabs())
        assertEquals("    ", "\t".expandtabs(4))
        assertEquals("", empty.expandtabs())
    }

    @Test
    fun makeTable() {
    }

    @Test
    fun find() {
        assertEquals(2, digits.find("2"))
        assertEquals(4, digits.find("45"))
        assertEquals(0, digits.find(""))
        assertEquals(-1, digits.find("87"))

        val b = "mississippi"
        val i = "i"
        val w = "w"
        assertEquals(2, b.find("ss"))
        assertEquals(-1, b.find("w"))
        assertEquals(-1, b.find("mississippian"))
        assertEquals(1, b.find(i))
        assertEquals(-1, b.find(w))
        assertEquals(5, b.find("ss", 3))
        assertEquals(2, b.find("ss", 1, 7))
        assertEquals(-1, b.find("ss", 1, 3))
        assertEquals(7, b.find(i, 6))
        assertEquals(1, b.find(i, 1, 3))
        assertEquals(-1, b.find(w, 1, 3))
    }

    @Test
    fun index() {
        assertEquals(2, digits.index("2"))
        assertEquals(4, digits.index("45"))
        assertEquals(0, digits.index(""))
    }

    @Test(expected = Exception::class)
    fun indexNotFound() {
        assertEquals(0, digits.index("87"))
    }


    @Test
    fun join() {
        val list = listOf("1", "2", "3")
        assertEquals("1,2,3", ",".join(list))
        assertEquals("123", "".join(list))
    }

    @Test
    fun ljust() {
        assertEquals("ab   ", "ab".ljust(5))
        assertEquals("ab000", "ab".ljust(5, '0'))
        assertEquals("", empty.ljust(0))
        assertEquals("   ", empty.ljust(3))
    }

    @Test
    fun lower() {
    }

    @Test
    fun lstrip() {
        assertEquals("bc", "abc".lstrip("ac"))
        assertEquals("", empty.lstrip())
    }

    @Test
    fun maketrans() {
    }

    @Test
    fun partition() {
        val b = "mississippi"
        assertEquals(Triple("mi", "ss", "issippi"), b.partition("ss"))
        assertEquals(Triple("mississippi", "", ""), b.partition("w"))
    }

    @Test
    fun replace() {
        val b = "mississippi"
        assertEquals("massassappa", b.replace("i", "a"))
        assertEquals("mixixippi", b.replace("ss", "x"))
    }

    @Test
    fun rfind() {
        val b = "mississippi"
        val i = "i"
        val w = "w"
        assertEquals(5, b.rfind("ss"))
        assertEquals(-1, b.rfind("w"))
        assertEquals(-1, b.rfind("mississippian"))
        assertEquals(10, b.rfind(i))
        assertEquals(-1, b.rfind(w))
        assertEquals(5, b.rfind("ss", 3))
        assertEquals(2, b.rfind("ss", 0, 6))
        assertEquals(1, b.rfind(i, 1, 3))
        assertEquals(7, b.rfind(i, 3, 9))
        assertEquals(-1, b.rfind(w, 1, 3))
    }

    @Test
    fun rindex() {
    }

    @Test
    fun rjust() {
        val b = "abc"
        assertEquals("----abc", b.rjust(7, '-'))
    }

    @Test
    fun rpartition() {
        val b = "mississippi"
        assertEquals(Triple("missi", "ss", "ippi"), b.rpartition("ss"))
        assertEquals(Triple("mississipp", "i", ""), b.rpartition("i"))
        assertEquals(Triple("", "", "mississippi"), b.rpartition("w"))
    }

    @Test
    fun rsplit() {
        assertEquals(listOf("a", "b", "c", "d", ""), "a|b|c|d|".rsplit("|"))
        assertEquals(listOf("a", "b", "c", "d", ""), "a,b,c,d,".rsplit(","))
        assertEquals(listOf("a,b,c,d,"), "a,b,c,d,".rsplit())
        assertEquals(listOf("a,b,c", "d", ""), "a,b,c,d,".rsplit(",", 2))
        assertEquals(listOf("a,b,c,d,"), "a,b,c,d,".rsplit(",", 0))
        assertEquals(listOf("aabbxx", "bb", "ddbb"), "aabbxxaabbaaddbb".rsplit("aa", 2))
    }

    @Test
    fun rstrip() {
        assertEquals("ab", "abc".rstrip("ac"))
        assertEquals("", empty.rstrip())
    }

    @Test
    fun split() {
        val str = "1 1 1 1 1"
        assertEquals(listOf("1", "1", "1", "1", "1"), str.split())
    }

    @Test
    fun splitlines() {
    }

    @Test
    fun startswith() {
        val b = "hello"
        assertFalse("".startswith("anything"))
        assertTrue(b.startswith("hello"))
        assertTrue(b.startswith("hel"))
        assertTrue(b.startswith("h"))
        assertFalse(b.startswith("hellow"))
        assertFalse(b.startswith("ha"))
    }

    @Test
    fun strip() {
        assertEquals("b", "abc".strip("ac"))
        assertEquals("", empty.strip())
    }

    @Test
    fun swapcase() {
    }

    @Test
    fun title() {
    }

    @Test
    fun translate() {
    }

    @Test
    fun upper() {
    }

    @Test
    fun zfill() {
        val b = "100"
        val c = "-100"
        val d = "+100"
        val e = "=100"
        assertEquals("00100", b.zfill(5))
        assertEquals("100", b.zfill(3))
        assertEquals("100", b.zfill(2))
        assertEquals("-0100", c.zfill(5))
        assertEquals("-100", c.zfill(4))
        assertEquals("-100", c.zfill(3))
        assertEquals("-100", c.zfill(2))
        assertEquals("+0100", d.zfill(5))
        assertEquals("0=100", e.zfill(5))
        assertEquals("00000", empty.zfill(5))
        assertEquals("100", b.zfill(0))
        assertEquals("-100", c.zfill(0))
        assertEquals("", empty.zfill(0))
    }

    @Test
    fun test_none_arguments() {
        val b = "hello"
        val h = "h"
        val l = "l"
        val o = "o"
        val x = "x"
        assertEquals(2, b.find(l, null))
        assertEquals(3, b.find(l, -2, null))
        assertEquals(2, b.find(l, null, -2))
        assertEquals(0, b.find(h, null, null))
        assertEquals(3, b.rfind(l, null))
        assertEquals(3, b.rfind(l, -2, null))
        assertEquals(2, b.rfind(l, null, -2))
        assertEquals(0, b.rfind(h, null, null))
        assertEquals(2, b.count(l, null))
        assertEquals(1, b.count(l, -2, null))
        assertEquals(1, b.count(l, null, -2))
        assertEquals(0, b.count(x, null, null))
        assertTrue(b.endswith(o, null))
        assertTrue(b.endswith(o, -2, null))
        assertTrue(b.endswith(l, null, -2))
        assertFalse(b.endswith(x, null, null))
        assertTrue(b.startswith(h, null))
        assertTrue(b.startswith(l, -2, null))
        assertTrue(b.startswith(h, null, -2))
        assertFalse(b.startswith(x, null, null))
    }
}
