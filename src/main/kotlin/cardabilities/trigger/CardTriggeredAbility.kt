package cardabilities.trigger

import cardabilities.CardAbility
import cardabilities.CardEffect

class CardTriggeredAbility(
        val trigger: CardTrigger,
        val effect: CardEffect
): CardAbility() {

}