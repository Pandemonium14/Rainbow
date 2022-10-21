package rainbowMod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rainbowMod.RainbowMod;

public class RainbowShellPower extends AbstractEasyPower {

    public static final String ID = RainbowMod.makeID("RainbowShellPower");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public RainbowShellPower(AbstractPlayer owner, int amount) {
        super(ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void updateDescription() {
        if (amount >= 2) {
            description = strings.DESCRIPTIONS[1] + amount + strings.DESCRIPTIONS[2];
        } else {
            description = strings.DESCRIPTIONS[0];
        }
    }


}
