package rainbowMod.patches;


import BattleTowers.events.TowerEvent;
import BattleTowers.events.phases.CombatPhase;
import BattleTowers.towers.BattleTower;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import rainbowMod.TheRainbow;

import java.util.ArrayList;
import java.util.Locale;

@SpirePatch2(cls = "BattleTowers.events.TowerEvent", method = "dropReward", optional = true)
public class BattleTowersPatch {

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(BattleTower.class, "getContents");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }

    @SpireInsertPatch(locator = Locator.class, localvars = {"combatPhase"})
    public static void addRainbowCards(TowerEvent __instance, CombatPhase combatPhase) {
        if (AbstractDungeon.player.chosenClass == TheRainbow.Enums.THE_RAINBOW) {
            AbstractMonster.EnemyType type = ReflectionHacks.getPrivate(combatPhase, CombatPhase.class, "type");
            if (type == AbstractMonster.EnemyType.ELITE) {
                RewardItem rainbowCards = RainbowCardsInChestsPatch.makeRainbowReward();
                AbstractDungeon.getCurrRoom().rewards.add(rainbowCards);
            }
        }
    }
}
