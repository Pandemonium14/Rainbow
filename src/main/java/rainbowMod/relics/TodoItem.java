package rainbowMod.relics;

import rainbowMod.TheRainbow;

import static rainbowMod.RainbowMod.makeID;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheRainbow.Enums.RAINBOW_CARD_COLOR);
    }
}
