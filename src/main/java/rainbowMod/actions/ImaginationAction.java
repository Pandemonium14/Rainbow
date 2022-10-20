package rainbowMod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rainbowMod.powers.ImaginationPower;

import java.util.ArrayList;

public class ImaginationAction extends AbstractGameAction {

    public ImaginationAction() {

    }

    @Override
    public void update() {
        ArrayList<AbstractCard.CardColor> colorsInHand = new ArrayList<>();
        ArrayList<AbstractCard> eligibleCards = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (!colorsInHand.contains(c.color)) {
                colorsInHand.add(c.color);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (!colorsInHand.contains(c.color)) {
                eligibleCards.add(c);
            }
        }
        if (eligibleCards.size()>0) {
            int r = AbstractDungeon.cardRng.random(eligibleCards.size() - 1);
            AbstractCard cardToDraw = eligibleCards.get(r);
            if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                AbstractDungeon.player.drawPile.moveToHand(cardToDraw);
            } else {
                AbstractDungeon.player.drawPile.moveToDiscardPile(cardToDraw);
            }
        }
        isDone = true;
    }
}
