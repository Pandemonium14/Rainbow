package rainbowMod.cards;

import com.megacrit.cardcrawl.actions.watcher.OmniscienceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rainbowMod.RainbowMod;

public class LaserFocus extends AbstractEasyCard {

    public static final String ID = RainbowMod.makeID("LaserFocus");

    public LaserFocus() {
        super(ID, 2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
        exhaust = true;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for (int i = 0 ; i < magicNumber ; i++) {
            addToBot(new OmniscienceAction(1));
        }
    }
}
