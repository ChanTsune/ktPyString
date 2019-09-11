package ktPyString

import kotlin.test.*


class StringTest {
    @Test fun testMultiplication() {
        val str = "Hello World!"
        assertEquals(str * 2,"Hello World!Hello World!")
    }
    @Test fun testSlice() {
        assertEquals("abc"[1,2],"b")
    }
    @Test fun testCenter() {
        val str = "12".center(5)
        assertEquals(str.length,5)
        assertEquals(str,"  12 ")
    }
    @Test fun testCount() {
        assertEquals("aaaaa".count("a"),5)
    }
    @Test fun testEndswith() {
        assertTrue("1234567890".endswith("0"))
        assertTrue("1234567890".endswith("9",end = -2))
        assertFalse("1234567890".endswith("9"))
    }
    @Test fun testExpandtabs() {
        assertEquals("\t".expandtabs(),"        ")
        assertEquals("\t".expandtabs(4),"    ")
    }
    @Test fun testFind() {
        assertEquals("01234567890".find("2"),2)
        assertEquals("01234567890".find("45"),3)
        assertEquals("0123456789".find(""),-1)
    }
    @Test fun testIndex() {
        assertEquals("0123456789".find("2"),2)
        assertEquals("01234567890".find("45"),3)
        assertEquals("0123456789".find(""),-1)
    }
}
