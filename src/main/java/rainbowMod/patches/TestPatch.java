package rainbowMod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;


public class TestPatch   {
    private static ShaderProgram shader = null;

    private static void initShader() {
        if (shader == null) {
            try {
                shader = new ShaderProgram(Gdx.files.internal("RainbowModResources/shaders/rainbowShader/vertex.vs"),
                        Gdx.files.internal("RainbowModResources/shaders/rainbowShader/fragment.fs"));
                if (!shader.isCompiled()) {
                    System.err.println(shader.getLog());
                }
                if (shader.getLog().length() > 0) {
                    System.out.println(shader.getLog());
                }
            } catch (GdxRuntimeException e) {
                System.out.println("ERROR: Failed to rainbow select shader:");
                e.printStackTrace();
            }
        }
    }

    @SpirePatch2(clz = CharacterOption.class, method = "render")
    public static class Shader {
        public static void Prefix(CharacterOption __instance, SpriteBatch sb) {
            initShader();
            if (shader != null && __instance.name.equals("The Ironclad")) {
                sb.setShader(shader);
                shader.setUniformf("u_time", CharacterOptionFields.time.get(__instance));
                shader.setUniformf("u_strength", 0.8f);
                shader.setUniformf("u_speed", 0.1f);
                shader.setUniformf("u_angle", 0f);
                shader.setUniformf("u_width", 90f);
            }
        }

        public static void Postfix(SpriteBatch sb) {
            sb.setShader(null);
        }
    }

    @SpirePatch2(clz = CharacterOption.class, method = "update")
    public static class Timer {
        public static void Prefix(CharacterOption __instance) {
            CharacterOptionFields.time.set(__instance, CharacterOptionFields.time.get(__instance) + Gdx.graphics.getDeltaTime());
        }
    }

    @SpirePatch(clz = CharacterOption.class, method = SpirePatch.CLASS)
    public static class CharacterOptionFields {
        public static SpireField<Float> time = new SpireField(() -> 0f);

    }

}
