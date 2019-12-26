package ktPyString

import ktPyString.utils.Quad
import kotlin.test.*

internal class SliceTest {

    private val slice = Slice(1)

    @Test
    fun testToString() {
        assertEquals("Slice(null, 1, null)", slice.toString())
    }

    @Test
    fun testSliceInit2() {
        val sliceObj = Slice(1,2)
        assertEquals(sliceObj, sliceObj)
    }

    @Test
    fun testSliceInit3() {
        val sliceObj = Slice(null, step=2)
        assertEquals(sliceObj, sliceObj)
    }

    @Test
    fun testSliceAdjustIndex() {
        val sliceObj = Slice(null,null,1)
        assertEquals(sliceObj.adjustIndex(20), Quad(0,20,1,20))
        val sliceObj2 = Slice(null,4,2)
        assertEquals(sliceObj2.adjustIndex(20), Quad(0,4,2,2))
    }
}
