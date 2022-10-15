package rainbowMod.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

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
        numberPanel.max = 5;

        perkPanel = new PerkSelectPanel();
    }

    public void update() {
        numberPanel.update();
    }

    public void render(SpriteBatch sb) {
        numberPanel.render(sb);
        perkPanel.render(sb);
    }

}