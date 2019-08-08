
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
        var step:Int = this.step ?: 1
        var start:Int
        var stop:Int
        var upper:Int
        var lower:Int

        // Convert step to an integer; raise for zero step.
        var step_sign:Int = _PyLong_Sign(step)
        if (step_sign == 0) {
            throw Exception("ValueError: slice step cannot be zero")
        }
        var step_is_negative:Boolean = step_sign < 0

        /* Find lower and upper bounds for start and stop. */
        if (step_is_negative) {
            lower = -1
            upper = length + lower
        }
        else {
            lower = 0
            upper = length
        }

        // Compute start.
        if (this.start == null) {
            start = if (step_is_negative) upper else lower
        }
        else {
            start = this.start!!

            if (_PyLong_Sign(start) < 0) {
                start += length

                if (start < lower /* Py_LT */) {
                    start = lower
                }
            }
            else {
                if (start > upper /* Py_GT */) {
                    start = upper
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
                if (stop < lower /* Py_LT */) {
                    stop = lower
                }
            }
            else {
                if (stop > upper /* Py_GT */) {
                    stop = upper
                }
            }
        }
        return Triple<Int,Int,Int>(start,stop,step)
    }
}

operator fun String.times(n:Int):String{
    return this.repeat(n)
}

// capitalize  ... exist in kotlin

// casefoled ... Bothersome

fun String.center(width:Int,fillchar:Char=' '):String{
    if (this.length >= width) {
        return this
    }
    var l:Int = 0
    var r:Int = 0
    return fillchar.toString() * l + this + fillchar.toString() * r
}


fun sample() {
    println("=== start sample ===")
    var str = "abc abc abc"
    println(str.capitalize())
    println()
    println("=== end sample ===")
} 


fun main(args: Array<String>) {
    println("hw".split("a"))
    val slice = Slice(0,10,-1)
    var slice2 = Slice(0,-2)
    var slice3 = Slice(-5,-2,-2)
    println(slice.adjustIndex(20))
    println(slice2.adjustIndex(10))
    println(slice3.adjustIndex(10))
    println("Hello, World!")
    println("a" * 10)
    var a = "a"
    a *= 10
    println(a)
    sample()
}

fun String.split(suffix:String):List<String> {
    var splited:MutableList<String> = mutableListOf()
    return splited
}
