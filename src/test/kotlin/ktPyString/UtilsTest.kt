package ktPyString

import ktPyString.utils.Quad
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest {
    @Test fun testQuadEquals() {
        assertEquals(Quad(1, 2, 3, 4), Quad(1, 2, 3, 4))
        assertEquals(Quad("", "a", 1, 5), Quad("", "a", 1, 5))
    }
    @Test fun testQuadAssign() {
        val quad = Quad("a", "b", 1, 2)
        val (a,b,o,t) = quad
        assertEquals("a", a)
        assertEquals("b", b)
        assertEquals(1, o)
        assertEquals(2, t)
    }
    @Test fun testQuadPropertys() {
        val quad = Quad("a", "b", 1, 2)
        assertEquals("a", quad.first)
        assertEquals("b", quad.second)
        assertEquals(1, quad.third)
        assertEquals(2, quad.fourth)
    }
    @Test fun testQuadToString(){
        val quad = Quad("a", "b", 1, 2)
        assertEquals("(a, b, 1, 2)", quad.toString())
    }
}
