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
    @Test fun testCenter() {
        val str = "12".center(5)
        assertEquals(5,str.length)
        assertEquals("  12 ",str)
    }
    @Test fun testCount() {
        assertEquals(5,"aaaaa".count("a"))
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
        assertEquals(3,"01234567890".find("45"))
        assertEquals(-1,"0123456789".find(""))
    }
    @Test fun testIndex() {
        assertEquals(2,"0123456789".find("2"))
        assertEquals(3,"01234567890".find("45"))
        assertEquals(-1,"0123456789".find(""))
    }
    @Test fun testJoin() {
        assertEquals("1,2,3",",".join(listOf("1","2","3")))
    }
    @Test fun testLjust() {
        assertEquals("ab   ","ab".ljust(5))
        assertEquals("ab000","ab".ljust(5))
    }
}
