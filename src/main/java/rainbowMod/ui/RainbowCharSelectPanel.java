package rainbowMod.ui;

import basemod.BaseMod;
import basemod.helpers.BaseModCardTags;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import rainbowMod.RainbowMod;
import rainbowMod.TheRainbow;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class RainbowCharSelectPanel {

    private NumberSelectPanel numberPanel;
    private PerkSelectPanel perkPanel;

    public RainbowCharSelectPanel() {


        numberPanel = new NumberSelectPanel();
        numberPanel.x = Settings.WIDTH/2.0f - 200f;
        numberPanel.y = Settings.HEIGHT/2.0f + 100f;
        numberPanel.initializeHitboxes();
        numberPanel.min = 1;
        numberPanel.selectedNumber = 1;
        numberPanel.max = CardCrawlGame.characterManager.getAllCharacters().size() - 1;

        perkPanel = new PerkSelectPanel();
        perkPanel.x = Settings.WIDTH/2.0f + 300f;
        perkPanel.y = Settings.HEIGHT/2.0f + 300f;
        perkPanel.initializeHitboxes();
    }

    public void triggerPerks() {
        perkPanel.triggerPerks();
    }

    public void makePools(CardGroup startingDeck) {
        ArrayList<AbstractPlayer> selectedChars = new ArrayList<>();
        ArrayList<AbstractPlayer> charList = new ArrayList<>();
        for (AbstractPlayer ch : CardCrawlGame.characterManager.getAllCharacters()) {
            if (!(ch instanceof TheRainbow)) charList.add(ch);
        }
        while (selectedChars.size() < numberPanel.selectedNumber) {
            int r = AbstractDungeon.cardRng.random(charList.size()-1);
            selectedChars.add(charList.get(r));
            charList.remove(r);
        }

        AbstractDungeon.commonCardPool.clear();
        AbstractDungeon.uncommonCardPool.clear();
        AbstractDungeon.rareCardPool.clear();
        RainbowMod.selectedColors.clear();

        ArrayList<String> sumOfStartingDeck = new ArrayList<>();
        for (AbstractPlayer p : selectedChars) {
            AbstractCard.CardColor color = p.getCardColor();
            RainbowMod.selectedColors.add(color);
            ArrayList<AbstractCard> cardPool = new ArrayList<>();

            for (Map.Entry<String,AbstractCard> c : CardLibrary.cards.entrySet()) {
                if (c.getValue().color == color) {
                    cardPool.add(c.getValue());
                }
            }

            for (AbstractCard c : cardPool) {
                if (c.rarity == AbstractCard.CardRarity.UNCOMMON) {
                    AbstractDungeon.uncommonCardPool.group.add(c);
                } else if (c.rarity == AbstractCard.CardRarity.COMMON) {
                    AbstractDungeon.commonCardPool.group.add(c);
                } else {
                    AbstractDungeon.rareCardPool.group.add(c);
                }
            }

            sumOfStartingDeck.addAll(p.getStartingDeck());
        }

        makeStartingDeck(sumOfStartingDeck, startingDeck);
    }

    private void makeStartingDeck(ArrayList<String> sumOfDeckStrings, CardGroup startingDeck) {
        ArrayList<AbstractCard> sumOfDeck = new ArrayList<>();
        for (String id : sumOfDeckStrings) {
            sumOfDeck.add(CardLibrary.getCard(id));
        }
        startingDeck.clear();
        int strikeCount = 0;
        int defendCount = 0;
        int otherCount = 0;
        while (startingDeck.size() < 10) {
            int r = AbstractDungeon.cardRng.random(sumOfDeck.size()-1);
            AbstractCard c = sumOfDeck.get(r);
            if ((c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(BaseModCardTags.BASIC_STRIKE)) && strikeCount < 4) {
                startingDeck.addToBottom(c.makeCopy());
                strikeCount ++;
            } else if ((c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.hasTag(BaseModCardTags.BASIC_DEFEND)) && defendCount < 4) {
                startingDeck.group.add(strikeCount, c.makeCopy());
                defendCount ++;
            } else if (otherCount < 2) {
                startingDeck.addToTop(c.makeCopy());
                otherCount++;
            }
            sumOfDeck.remove(c);
        }
    }

    public void makePools(ArrayList<AbstractCard.CardColor> colors) {

        AbstractDungeon.commonCardPool.clear();
        AbstractDungeon.uncommonCardPool.clear();
        AbstractDungeon.rareCardPool.clear();

        for (AbstractCard.CardColor color : colors) {
            ArrayList<AbstractCard> cardPool = new ArrayList<>();
            for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
                if (c.getValue().color == color) {
                    cardPool.add(c.getValue());
                }
            }

            for (AbstractCard c : cardPool) {
                if (c.rarity == AbstractCard.CardRarity.UNCOMMON) {
                    AbstractDungeon.uncommonCardPool.group.add(c);
                } else if (c.rarity == AbstractCard.CardRarity.COMMON) {
                    AbstractDungeon.commonCardPool.group.add(c);
                } else {
                    AbstractDungeon.rareCardPool.group.add(c);
                }
            }
        }
    }

    public void update() {
        numberPanel.update();
        perkPanel.receiveTotalPoints(numberPanel.selectedNumber);
        perkPanel.update();
    }

    public void render(SpriteBatch sb) {
        numberPanel.render(sb);
        perkPanel.render(sb);
    }

}