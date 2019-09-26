package ktPyString

import kotlin.test.*


class StringTest {
    @Test fun testMultiplication() {
        val str = "Hello World!"
        assertEquals("Hello World!Hello World!",str * 2)
    }
    @Test fun testSlice() {
        val b = "Hello, world"
        assertEquals("Hello", b[null,5])
        assertEquals("ello", b[1,5])
        assertEquals(", ", b[5,7])
        assertEquals("world",b[7,null])
        
    }
    @Test fun testExtendedGetSlice() {
        var b = ""
        for(i in 0..255) {
            b += i.toString()
        }
        var indices = arrayOf(0, null, 1, 3, 19, 100, 54775807, -1, -2, -31, -100)
        for (start in indices) {
            for (stop in indices) {
                for(step in indices) {
                    println(start)
                    println(stop)
                    println(step)
                    println(b[start,stop,step])
                    assertEquals(b[start,stop,step], b[start,stop,step])
                }
            }
        }
    }
    @Test fun testCenter() {
        val str = "12".center(5)
        assertEquals(5,str.length)
        assertEquals("  12 ",str)
    }
    @Test fun testCount() {
        assertEquals(5,"aaaaa".count("a"))
        assertEquals(2,"a".count(""))
        assertEquals(3,"ab".count(""))
    }
    @Test fun testEndswith() {
        assertTrue("1234567890".endswith("0"))
        assertTrue("1234567890".endswith("9",end = -2))
        assertFalse("1234567890".endswith("9"))
    }
    @Test fun testExpandtabs() {
        assertEquals("        ","\t".expandtabs())
        assertEquals("    ","\t".expandtabs(4))
    }
    @Test fun testFind() {
        assertEquals(2,"01234567890".find("2"))
        assertEquals(4,"01234567890".find("45"))
        assertEquals(0,"0123456789".find(""))
    }
    @Test fun testIndex() {
        assertEquals(2,"0123456789".find("2"))
        assertEquals(4,"01234567890".find("45"))
        assertEquals(0,"0123456789".find(""))
    }
    @Test fun testJoin() {
        assertEquals("1,2,3",",".join(listOf("1","2","3")))
    }
    @Test fun testLjust() {
        assertEquals("ab   ","ab".ljust(5))
        assertEquals("ab000","ab".ljust(5))
    }
    @Test fun test_endswith(){
        var b = "hello"
        assertFalse("".endswith("anything"))
        assertTrue(b.endswith("hello"))
        assertTrue(b.endswith("llo"))
        assertTrue(b.endswith("o"))
        assertFalse(b.endswith("whello"))
        assertFalse(b.endswith("no"))
    }
    @Test fun test_getslice(){
        var b = "Hello, world"
        assertEquals("Hello",b[null,5])
        assertEquals("ello",b[1,5])
        assertEquals(", ",b[5,7])
        assertEquals("world",b[7,null])
        assertEquals("world",b[7,12])
        assertEquals("world",b[7,100])
        assertEquals("Hello",b[null,-7])
        assertEquals("ello",b[-11,-7])
        assertEquals(", ",b[-7,-5])
        assertEquals("world",b[-5,null])
        assertEquals("world",b[-5,12])
        assertEquals("world",b[-5,100])
        assertEquals("Hello",b[-100,5])
    }
    @Test fun test_rpartition(){
        var b = "mississippi"
        assertEquals(Triple("missi", "ss", "ippi"),b.rpartition("ss"))
        assertEquals(Triple("mississipp", "i", ""),b.rpartition("i"))
        assertEquals(Triple("", "", "mississippi"),b.rpartition("w"))
    }
    @Test fun test_replace(){
        var b = "mississippi"
        assertEquals("massassappa",b.replace("i", "a"))
        assertEquals("mixixippi",b.replace("ss", "x"))
    }
    @Test fun test_rfind(){
        var b = "mississippi"
        var i = "i"
        var w = "w"
        assertEquals(5,b.rfind("ss"))
        assertEquals(-1,b.rfind("w"))
        assertEquals(-1,b.rfind("mississippian"))
        assertEquals(10,b.rfind(i))
        assertEquals(-1,b.rfind(w))
        assertEquals(5,b.rfind("ss", 3))
        assertEquals(2,b.rfind("ss", 0, 6))
        assertEquals(1,b.rfind(i, 1, 3))
        assertEquals(7,b.rfind(i, 3, 9))
        assertEquals(-1,b.rfind(w, 1, 3))
    }
    @Test fun test_split_unicodewhitespace(){
        var b = "\t\n\u000b\u000c\r\u001c\u001d\u001e\u001f"
        assertEquals(listOf("a\u01cb"), "a\u01cb".split())
        assertEquals(listOf("a\u01db"), "a\u01db".split())
        assertEquals(listOf("a\u01eb"), "a\u01eb".split())
        assertEquals(listOf("a\u01fb"), "a\u01fb".split())
        assertEquals(listOf("\u001c\u001d\u001e\u001f"),b.split())
    }
    @Test fun test_strip(){
        assertEquals("abc".strip("ac"),"b")
        assertEquals("abc".lstrip("ac"),"bc")
        assertEquals("abc".rstrip("ac"),"ab")
    }
    @Test fun test_partition(){
        var b = "mississippi"
        assertEquals(Triple("mi", "ss", "issippi"),b.partition("ss"))
        assertEquals(Triple("mississippi", "", ""),b.partition("w"))
    }
    @Test fun test_none_arguments(){
        var b = "hello"
        var h = "h"
        var l = "l"
        var o = "o"
        var x = "x"
        assertEquals(2,b.find(l, null))
        assertEquals(3,b.find(l, -2, null))
        assertEquals(2,b.find(l, null, -2))
        assertEquals(0,b.find(h, null, null))
        assertEquals(3,b.rfind(l, null))
        assertEquals(3,b.rfind(l, -2, null))
        assertEquals(2,b.rfind(l, null, -2))
        assertEquals(0,b.rfind(h, null, null))
        assertEquals(2,b.count(l, null))
        assertEquals(1,b.count(l, -2, null))
        assertEquals(1,b.count(l, null, -2))
        assertEquals(0,b.count(x, null, null))
        assertTrue(b.endswith(o, null))
        assertTrue(b.endswith(o, -2, null))
        assertTrue(b.endswith(l, null, -2))
        assertFalse(b.endswith(x, null, null))
        assertTrue(b.startswith(h, null))
        assertTrue(b.startswith(l, -2, null))
        assertTrue(b.startswith(h, null, -2))
        assertFalse(b.startswith(x, null, null))
    }
//    @Test fun test_maketrans(){
//        var transtable = "\x00\x01\x02\x03\x04\x05\x06\x07\x08\t\n\x0b\x0c\r\x0e\x0f\x10\x11\x12\x13\x14\x15\x16\x17\x18\x19\x1a\x1b\x1c\x1d\x1e\x1f !"#$%&\"()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\x7f\x80\x81\x82\x83\x84\x85\x86\x87\x88\x89\x8a\x8b\x8c\x8d\x8e\x8f\x90\x91\x92\x93\x94\x95\x96\x97\x98\x99\x9a\x9b\x9c\x9d\x9e\x9f\xa0¡¢£¤¥¦§¨©ª«¬\xad®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüxyz"
//        assertEquals(transtable,String.maketrans('abc', 'xyz'))
//        assertEquals(transtable,String.maketrans('ýþÿ', 'xyz'))
//    }
    @Test fun test_repeat(){
        var b = "abc"
        assertEquals("abcabc",b * 3)
        assertEquals("",b * 0)
        assertEquals("",b * -1)
    }
    @Test fun test_startswith(){
        var b = "hello"
        assertFalse("".startswith("anything"))
        assertTrue(b.startswith("hello"))
        assertTrue(b.startswith("hel"))
        assertTrue(b.startswith("h"))
        assertFalse(b.startswith("hellow"))
        assertFalse(b.startswith("ha"))
    }
    @Test fun test_count(){
        var b = "mississippi"
        var i = "i"
        var p = "p"
        var w = "w"
        assertEquals(4,b.count("i"))
        assertEquals(2,b.count("ss"))
        assertEquals(0,b.count("w"))
        assertEquals(4,b.count(i))
        assertEquals(0,b.count(w))
        assertEquals(2,b.count("i", 6))
        assertEquals(2,b.count("p", 6))
        assertEquals(1,b.count("i", 1, 3))
        assertEquals(1,b.count("p", 7, 9))
        assertEquals(2,b.count(i, 6))
        assertEquals(2,b.count(p, 6))
        assertEquals(1,b.count(i, 1, 3))
        assertEquals(1,b.count(p, 7, 9))
    }
    @Test fun test_join(){
        var s1 = "abcd"
        var s2 = "abcd"

    }
    @Test fun test_find(){
        var b = "mississippi"
        var i = "i"
        var w = "w"
        assertEquals(2,b.find("ss"))
        assertEquals(-1,b.find("w"))
        assertEquals(-1,b.find("mississippian"))
        assertEquals(1,b.find(i))
        assertEquals(-1,b.find(w))
        assertEquals(5,b.find("ss", 3))
        assertEquals(2,b.find("ss", 1, 7))
        assertEquals(-1,b.find("ss", 1, 3))
        assertEquals(7,b.find(i, 6))
        assertEquals(1,b.find(i, 1, 3))
        assertEquals(-1,b.find(w, 1, 3))
    }
    @Test fun test_just(){
        var b = "abc"
        assertEquals("--abc--",b.center(7, '-'))
        assertEquals("abc----",b.ljust(7, '-'))
        assertEquals("----abc",b.rjust(7, '-'))
    }
    @Test fun test_rsplit_unicodewhitespace(){
        var b = "\t\n\u000b\u000c\r\u001c\u001d\u001e\u001f"
        assertEquals(listOf("\u001c\u001d\u001e\u001f"),b.rsplit())
    }
}
