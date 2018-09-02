import cardsets.CardSetCompiler

fun main(args : Array<String>) {
    val set1 = CardSetCompiler("Set1").compileSet()
    set1?.cards?.forEach { card ->
        print(card)
    }

}