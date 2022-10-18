package rainbowMod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import rainbowMod.TheRainbow;

import static rainbowMod.RainbowMod.makeID;

public class HealingPerkRelic extends AbstractEasyRelic {
    public static final String ID = makeID("HealingPerkRelic");


    public HealingPerkRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL, TheRainbow.Enums.RAINBOW_CARD_COLOR);
    }

    public void onVictory() {
        if (AbstractDungeon.actNum == 1) {
            this.flash();// 23
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 24
            AbstractPlayer p = AbstractDungeon.player;// 25
            if (p.currentHealth > 0) {// 26
                p.heal(8);// 27
            }
        }
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (AbstractDungeon.actNum != 1) {
            usedUp();
        }
    }
}
