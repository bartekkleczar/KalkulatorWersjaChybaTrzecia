package pl.klenczi.kalkulatorwersjachybatrzecia

fun calculate(expression: String){
    val numbers = setOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
    var sum = 0
    var component = ""
    for(i in expression){
        if(i.toString().toInt() in numbers){
            component = "$component$i"
        }
    }
}
