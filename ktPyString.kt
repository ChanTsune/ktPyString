
class Slice(stop:Int?) {
    var start:Int? = null
    var stop:Int? = stop
    var step:Int? = null
    constructor(start:Int?, stop:Int?, step:Int?=null):this(stop){
        this.start = start
        this.step = step
    }
    override fun toString():String {
        return "Slice(${start}, ${stop}, ${step})"
    }
    private fun _PyLong_Sign(n:Int):Int {
        return when {
            n == 0 -> 0
            n > 0 -> 1
            else -> -1
        }
    }
    fun adjustIndex(length:Int):Triple<Int,Int,Int> {
        var step:Int
        var start:Int
        var stop:Int
        var upper:Int
        var lower:Int
        var step_is_negative:Boolean
        var cmp_result:Boolean

        /* Convert step to an integer; raise for zero step. */
        if (this.step == null) {
            step = 1
            step_is_negative = false
        }
        else {
            var step_sign:Int
            step = this.step!!
            step_sign = _PyLong_Sign(step);
            if (step_sign == 0) {
                throw Exception("ValueError: slice step cannot be zero")
            }
            step_is_negative = step_sign < 0
        }
        // Find lower and upper bounds for start and stop.
        if (step_is_negative) {
            lower = -1
            upper = length + lower
        }
        else {
            lower = 0
            upper = length;
        }

        // Compute start.
        if (this.start == null) {
            start = if (step_is_negative) upper else lower
        }
        else {
            start = this.start!!

            if (_PyLong_Sign(start) < 0) {
                start += length

                cmp_result = start < lower // Py_LT
                if (cmp_result) {
                    start = lower;
                }
            }
            else {
                cmp_result = start > upper // Py_GT
                if (cmp_result) {
                    start = upper;
                }
            }
        }

        // Compute stop.
        if (this.stop == null) {
            stop = if (step_is_negative) lower else upper
        }
        else {
            stop = this.stop!!

            if (_PyLong_Sign(stop) < 0) {
                stop += length
                cmp_result = stop < lower // Py_LT
                if (cmp_result) {
                    stop = lower;
                }
            }
            else {
                cmp_result = stop > upper // Py_GT
                if (cmp_result) {
                    stop = upper;
                }
            }
        }
        return Triple<Int,Int,Int>(start,stop,step)
    }
}



fun main(args: Array<String>) {
    println("hw".split("a"))
    val slice = Slice(0)
    println(slice.adjustIndex(0))
    println("Hello, World!")
}

fun String.split(suffix:String):List<String> {
    var splited:MutableList<String> = mutableListOf()
    return splited
}
