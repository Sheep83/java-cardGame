package cardGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;

public class Menu extends KeyAdapter {
	
	private Game game;
	private Handler handler;
	public Rectangle playButton = new Rectangle(Game.WIDTH / 2 - 50, 800, 100, 50);
	
	public Menu(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
	}


	public void tick() {
//		if(soundPlayer.currentClip == null || soundPlayer.currentClip.isActive() == false) {
//		soundPlayer.playSound("title.wav");
//		}
		
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(Color.white);
//		g.drawRect(15, 15, 200, 32);
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		Font fnt1 = new Font("arial", Font.BOLD, 16);
		g.setFont(fnt0);
		g.drawString("cardGame", (100), (Game.HEIGHT/2-300));
		g.setFont(fnt1);
//		g.drawString("Space to Play.....", (Game.WIDTH/2-100), Game.HEIGHT/2);
		g2d.draw(playButton);
		g.drawString("Play", playButton.x + 32, playButton.y + 30);
		
	}
}
