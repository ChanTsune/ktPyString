package ktPyString

import kotlin.math.sign


operator fun String.times(n: Int): String = this.repeat(if (n > 0) n else 0)

operator fun String.get(slice: Slice): String {
    var (start, _, step, loop) = slice.adjustIndex(this.length)
    var result = ""

    for (i in 0 until loop) {
        result += this[start]
        start += step
    }
    return result
}

operator fun String.get(start: Int?, end: Int?, step: Int? = null): String = this[Slice(start, end, step)]

// capitalize  ... exist in kotlin

// casefoled ... Bothersome

fun String.center(width: Int, fillchar: Char = ' '): String {
    if (this.length >= width) {
        return this
    }
    var l: Int = width - length
    var r: Int = l / 2
    l = r + l % 2
    return fillchar.toString() * l + this + fillchar.toString() * r
}

fun String.count(sub: String, start: Int? = null, end: Int? = null): Int {
    var (start,end,_,length) = Slice(start, end).adjustIndex(this.length)
    if (sub.isEmpty()) {
        return length + 1
    }
    var n = this.find(sub, start, end)
    var c = 0
    while (n != -1) {
        c += 1
        n = this.find(sub, n + sub.length, end)
    }
    return c
}

fun String.endswith(suffix: String, start: Int? = null, end: Int? = null): Boolean = this[Slice(start, end)].endsWith(suffix)

fun String.expandtabs(tabsize: Int = 8): String = this.replace("\t", " " * tabsize)


fun makeTable(text:String,target:String): Map<Char,Int> {
    return mapOf()
}

fun String.find(sub: String, start: Int? = null, end: Int? = null): Int {
    if (sub.isEmpty()) {
        return 0
    }
    var (start,end,_,_) = Slice(start,end).adjustIndex(this.length)
    var fin = end - sub.length
    while (start <= fin) {
        if (this[start,start+sub.length] == sub) {
            return start
        }
        ++start
    }
    return -1
}

//fun String.format() {
//
//}

//fun String.format_map() {
//
//}

fun String.index(sub: String, start: Int? = null, end: Int? = null): Int {
    val tmp = this.find(sub, start, end)
    return if (tmp == -1) throw Exception("ValueError: substring not found") else tmp
}

// isalnum ...
// isalpha ...
// isascii ...
// isdecimal ...
// isdigit ...
// islower ...
// isnumeric ...
// isprintable ...
// isspace ...
// istitle ...
// isupper ...

fun String.join(iterable: List<String>): String {
    var result = ""
    var sep = ""
    for (item in iterable) {
        result += sep
        result += item
        sep = this
    }
    return result
}


fun String.ljust(width: Int, fillchar: Char = ' '): String {
    if (this.length >= width) {
        return this
    }
    val filllen = width - this.length
    return this + fillchar.toString() * filllen
}

fun String.lower(): String = this.toLowerCase()

fun String.lstrip(chars: String? = null): String {
    return if (chars != null) {
        this.dropWhile { c -> chars.indexOf(c) != -1 }
    } else {
        this.dropWhile { c -> c == ' ' } // 空白文字を除去するパターン
    }
}

fun String.maketrans(x:Map<Int, String?>): Map<Int, String> {
    var table: MutableMap<Int, String> = mutableMapOf()
    for ( (k, v) in x) {
        table[k] = v ?: ""
    }
    return table
}
// fun String.maketrans(x:Map<Char,String?>):Map<Int,String> {
//     var table: MutableMap<Int, String> = mutableMapOf()
//     for ( (k, v) in x) {
//         table[k.toInt()] = v ?: ""
//     }
//     return table
// }
fun String.maketrans(x:String,y:String,z:String=""):Map<Int,String> {
    var table: MutableMap<Int, String> = mutableMapOf()
    for (( k,v) in x zip y) {
        table[k.toInt()] = v.toString()
    }
    for (c in x) {
        table[c.toInt()] = ""
    }
    return table
}

fun String.partition(sep:String):Triple<String,String,String> {
    val tmp = this.split(sep,1)
    return if (tmp.size == 2) {
        Triple(tmp[0],sep,tmp[1])
    } else {
        Triple(this,"","")
    }
}

fun String.replace(old: String, new: String, maxcount: Int = Int.MAX_VALUE): String {
    return new.join(this.split(old,maxcount))
}

fun String.rfind(sub: String,start:Int?=null,end:Int?=null):Int {
    if (sub.isEmpty()) {
        return this.length
    }
    var (s, e, _, _) = Slice(start, end).adjustIndex(this.length)
    if ((e - s) < sub.length) {
        return -1;
    }
    s -= 1
    var fin = e - sub.length
    while (fin != s) {
        if (this[fin, fin + sub.length] == sub) {
            return fin
        }
        fin -= 1
    }
    return -1
}

fun String.rindex(sub:String,start:Int?=null,end:Int?=null):Int {
    val i = this.rfind(sub,start,end)
    if (i == -1) {
        throw Exception("ValueError: substring not found")
    }
    return i
}

fun String.rjust(width:Int, fillchar:Char=' '):String {
    return if (this.length >= width) {
        this
    } else {
        val filllen = width - this.length
        fillchar.toString() * filllen + this
    }
}

fun String.rpartition(sep:String) : Triple<String,String,String> {
    val tmp = this.rsplit(sep,1)
    return if (tmp.size == 2) {
        Triple(tmp[0],sep,tmp[1])
    } else {
        Triple("","",this)
    }
}

private fun String._rsplit(sep:String, maxsplit: Int): List<String> {
    var result: MutableList<String> = mutableListOf()
    var index = 0
    var prev_index = Int.MAX_VALUE
    var sep_len = sep.length
    var maxsplit = maxsplit
    while (maxsplit > 0) {
        index = this.rfind(sep, 0, prev_index)
        if (index == -1) {
            break
        }
        index += sep_len
        result.add(0, this[index, prev_index])
        index -= sep_len

        index -= 1
        prev_index = index + 1

        maxsplit -= 1

        if (maxsplit <= 0) {
            break
        }
    }
    result.add(0,this[0, prev_index])
    return result
}

private fun String._rsplit(maxsplit: Int): List<String> {
    val result:MutableList<String> = mutableListOf()
    var index = this.length - 1
    var len = 0
    var maxsplit = maxsplit
    for (chr in this.reversed()) {
        if (chr.isWhitespace()) {
            if (len != 0) {
                result.add(0,this[index, len])
                maxsplit -= 1
                index -= len
            }
            index -= 1
            len = 0
        } else {
            len += 1
        }
        if (maxsplit <= 0){
            break
        }
    }
    val tmp = this[0, index + 1].rstrip()
    if (tmp.isNotEmpty()) {
        result.add(0,tmp)
    }
    return result
}

fun String.rsplit(sep:String?=null, maxsplit:Int=-1) : List<String> {
    var maxsplit = if (maxsplit.sign == -1) Int.MAX_VALUE else maxsplit
    return if(sep != null && sep.isNotEmpty()) {
        this._rsplit(sep,maxsplit)
    } else {
        this._rsplit(maxsplit)
    }
}

fun String.rstrip(chars:String?=null) : String {
    return if (chars != null) {
        dropLastWhile { c -> chars.contains(c) }
    } else {
        dropLastWhile { c -> c.isWhiteSpace() }
    }
}

private fun String._split(sep:String,maxsplit:Int):List<String> {
    var maxsplit = maxsplit
    val result: MutableList<String> = mutableListOf()
    var index = 0
    var prevIndex = 0
    val sepLen = sep.length
    while (maxsplit > 0) {
        index = this.find(sep, prevIndex)
        if (index == -1) {
            break
        }
        result.add(this[prevIndex, index])
        prevIndex = index + sepLen

        maxsplit -= 1
    }
    result.add(this[prevIndex, null])
    return result
}
private fun String._split(maxsplit: Int):List<String> {
    val result: MutableList<String> = mutableListOf()
    var maxsplit = maxsplit
    var index = 0
    var len = 0
    for (chr in this) {
        if (chr.isWhitespace()) {
            if (len != 0) {
                result.add(this[index, len])
                maxsplit -= 1
                index += len
            }
            index += 1
            len = 0
        } else {
            len += 1
        }
        if (maxsplit == 0) {
            break
        }
    }
    val tmp = this[index, null].lstrip()
    if (tmp.isNotEmpty()) {
        result.add(tmp)
    }
    return result
}

fun String.split(sep: String?=null, maxsplit:Int=-1): List<String> {
    var maxsplit = if (maxsplit.sign == -1) Int.MAX_VALUE else maxsplit
    return if(sep != null && sep.isNotEmpty()) {
        this._split(sep, maxsplit)
    } else {
        this._split(maxsplit)
    }
}

fun String.splitlines(keepends:Boolean=false) :List<String> {
    var splited:MutableList<String> = mutableListOf()
    return splited
}

fun String.startswith(prefix:String,start:Int?=null,end:Int?=null):Boolean = this[start,end].startsWith(prefix)

fun String.strip(chars: String?=null):String = this.lstrip(chars).rstrip(chars)

fun String.swapcase():String {
    var tmp = ""
    for (c in this) {
        tmp += if (c.isLowerCase()) {
            c.toUpperCase()
        } else if (c.isUpperCase()) {
            c.toLowerCase()
        } else {
            c
        }
    }
    return tmp
}

fun String.title():String {
    var titled = ""
    var prevCased = false
    for (c in this) {
        if (!prevCased) {
            titled += if (c.isTitleCase()) {
                c.toTitleCase()
            } else {
                c
            }
        } else {
            titled += if (c.isCased()) {
                if (!c.isLowerCase()) {
                    c.toLowerCase()
                } else {
                    c
                }
            } else {
                c
            }
        }
        prevCased = c.isCased()
    }
    return titled
}

fun String.translate(table:Map<Int,String>):String {
    return this
}

fun String.upper():String {
    return this
}

fun String.zfill(width:Int):String {
    return if(this.isEmpty() || (this[0] != '-' && this[0] != '+')){
        this.rjust(width,'0')
    } else {
        this[0] + this[1,null].rjust(width-1,'0')
    }
}

private fun Char.isWhiteSpace():Boolean {
    return "\u0020\u00A0\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200A\u200B\u3000\uFEFF\u0009".contains(this)
}

private fun Char.isCased():Boolean =
    this.isUpperCase() || this.isLowerCase() || this.isTitleCase()
