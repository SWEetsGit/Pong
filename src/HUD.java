import java.awt.*;

public class HUD {
    public static void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 80));

        String scoreText = Main.playerScore + "   " + Main.opponentScore;
        int x = (Main.SCREEN_WIDTH - g.getFontMetrics().stringWidth(scoreText)) / 2;

        g.drawString(scoreText, x, 75);
    }
}
