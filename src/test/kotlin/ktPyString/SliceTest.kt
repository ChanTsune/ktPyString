package ktPyString

import kotlin.test.Test
import kotlin.test.assertEquals

class SliceTest {
    @Test fun testSliceInit() {
        val sliceObj = Slice(1)
        assertEquals(sliceObj, sliceObj)
    }
    @Test fun testSliceInit2() {
        val sliceObj = Slice(1,2)
        assertEquals(sliceObj, sliceObj)
    }
    @Test fun testSliceInit3() {
        val sliceObj = Slice(null, step=2)
        assertEquals(sliceObj, sliceObj)
    }
    @Test fun testSliceAdjustIndex() {
        val sliceObj = Slice(null,null,1)
        assertEquals(sliceObj.adjustIndex(20), Triple(0,20,1))
        val sliceObj2 = Slice(null,4,2)
        assertEquals(sliceObj2.adjustIndex(20), Triple(0,4,2))
    }
}
