package rainbowMod.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import rainbowMod.RainbowMod;
import rainbowMod.TheRainbow;

import java.util.ArrayList;

@SpirePatch2(clz = AbstractChest.class, method = "open")
public class RainbowCardsInChestsPatch {

    @SpirePostfixPatch
    public static void onOpenChest() {
        MapRoomNode node = AbstractDungeon.getCurrMapNode();
        if (AbstractDungeon.player.chosenClass == TheRainbow.Enums.THE_RAINBOW && isInRow(8,node)) {
            AbstractDungeon.combatRewardScreen.rewards.add(makeRainbowReward());
        }
    }

    public static RewardItem makeRainbowReward() {
        int blizz = AbstractDungeon.cardBlizzRandomizer;
        RewardItem reward = new RewardItem();
        AbstractDungeon.cardBlizzRandomizer = blizz;
        reward.cards.clear();

        ArrayList<AbstractCard> rainbowCards = new ArrayList<>(RainbowMod.rainbowCards);
        while (reward.cards.size() < 3) {
            int r = AbstractDungeon.cardRng.random(rainbowCards.size() - 1);
            AbstractCard c = rainbowCards.get(r);
            reward.cards.add(c.makeCopy());
            rainbowCards.remove(c);
        }
        return reward;
    }

    private static boolean isInRow(int row, MapRoomNode node) {
        ArrayList<ArrayList<MapRoomNode>> map = AbstractDungeon.map;
        ArrayList<MapRoomNode> floor = map.get(row);
        return floor.contains(node);
    }
}
