package io.squabbi.accm.models

class AccConfig(
    val capacity: IntArray,
    val coolDown: IntArray,
    val temperature: IntArray,
    val verbose: String,
    val resetUnplugged: String,
    val loopDelay: Int,
    val maxLogSize: Int,
    val onBootExit: String
)