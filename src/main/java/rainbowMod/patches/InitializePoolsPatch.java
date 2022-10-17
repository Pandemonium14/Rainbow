package rainbowMod.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rainbowMod.RainbowMod;
import rainbowMod.TheRainbow;

@SpirePatch2(clz = AbstractDungeon.class, method = "initializeCardPools")
public class InitializePoolsPatch {

    @SpirePostfixPatch
    public static void rainbowPools(AbstractDungeon __instance) {
        if (AbstractDungeon.player.chosenClass == TheRainbow.Enums.THE_RAINBOW) {
            RainbowMod.optionsPanel.makePools(RainbowMod.selectedColors);
        }
    }
}
