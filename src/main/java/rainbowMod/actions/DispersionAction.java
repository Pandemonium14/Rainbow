package rainbowMod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import rainbowMod.RainbowMod;
import rainbowMod.TheRainbow;

import java.util.ArrayList;

public class DispersionAction extends AbstractGameAction {

    private final AbstractCard targetCard;
    private final boolean upgraded;

    public DispersionAction(AbstractCard c, boolean up) {
        upgraded = up;
        targetCard = c;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> allCards = CardLibrary.getAllCards();
        ArrayList<AbstractCard> sameCost = new ArrayList<>();
        ArrayList<AbstractCard> sameType = new ArrayList<>();
        ArrayList<AbstractCard> sameColor = new ArrayList<>();
        ArrayList<AbstractCard.CardColor> acceptedColors;
        if (AbstractDungeon.player.chosenClass == TheRainbow.Enums.THE_RAINBOW) {
            acceptedColors = new ArrayList<>(RainbowMod.selectedColors);
        } else {
            acceptedColors = new ArrayList<>();
            acceptedColors.add(AbstractDungeon.player.getCardColor());
        }
        acceptedColors.add(AbstractCard.CardColor.COLORLESS);

        if (!acceptedColors.contains(targetCard.color)) acceptedColors.add(targetCard.color);

        for (AbstractCard libraryCard : allCards) {
            AbstractCard c = libraryCard.makeCopy();
            if (acceptedColors.contains(c.color) && (targetCard.type == AbstractCard.CardType.STATUS || c.type != AbstractCard.CardType.STATUS) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                if (upgraded) {
                    c.upgrade();
                }

                if (c.cost == targetCard.cost) {
                    sameCost.add(c);
                }
                if (c.type == targetCard.type) {
                    sameType.add(c);
                }
                if (c.color == targetCard.color) {
                    sameColor.add(c);
                }
            }
        }


        AbstractCard cCost;
        AbstractCard cType;
        AbstractCard cColor;
        if (sameCost.size() > 0) {
            int r = AbstractDungeon.cardRng.random(sameCost.size() - 1);
            cCost = sameCost.get(r);
        } else {
            cCost = targetCard.makeCopy();
            if (upgraded) cCost.upgrade();
        }
        if (sameType.size() > 0) {
            int r = AbstractDungeon.cardRng.random(sameType.size() - 1);
            cType = sameType.get(r);
        } else {
            cType = targetCard.makeCopy();
            if (upgraded) cType.upgrade();
        }
        if (sameColor.size() > 0) {
            int r = AbstractDungeon.cardRng.random(sameColor.size() - 1);
            cColor = sameColor.get(r);
        } else {
            cColor = targetCard.makeCopy();
            if (upgraded) cColor.upgrade();
        }

        addToTop(new ExhaustSpecificCardAction(targetCard, AbstractDungeon.player.hand));

        addToTop(new MakeTempCardInHandAction(cColor));
        addToTop(new MakeTempCardInHandAction(cType));
        addToTop(new MakeTempCardInHandAction(cCost));

        isDone = true;
    }
}