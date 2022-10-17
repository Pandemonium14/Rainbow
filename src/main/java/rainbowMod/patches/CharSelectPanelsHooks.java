package rainbowMod.patches;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import rainbowMod.RainbowMod;
import rainbowMod.TheRainbow;

public class CharSelectPanelsHooks {

    @SpirePatch2(clz = CharacterOption.class, method = "update")
    public static class UpdateHook {

        @SpirePostfixPatch
        public static void updateCharOption(CharacterOption __instance) {
            if (__instance.c instanceof TheRainbow) {
                RainbowMod.optionsPanel.update();
            } else if (__instance.selected) {
                RainbowMod.characterTickboxes.update(__instance.c);
            }
        }
    }

    @SpirePatch2(clz = CharacterOption.class, method = "renderInfo")
    public static class RenderHook {

        @SpireInsertPatch(rloc = 3)
        public static void updateCharOption(CharacterOption __instance, SpriteBatch sb) {
            if (__instance.c instanceof TheRainbow) {
                RainbowMod.optionsPanel.render(sb);
            } else if (__instance.selected){
                RainbowMod.characterTickboxes.render(sb);
            }
        }
    }
}
