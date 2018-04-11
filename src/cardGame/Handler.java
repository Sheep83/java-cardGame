package cardGame;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Handler {
	
	LinkedList<GameObject> players = new LinkedList<GameObject>();
	LinkedList<GameObject> objects = new LinkedList<GameObject>();
	Deck deck;
	
	private Random r = new Random();
	
	public void tick() {
		for (int i = 0; i < players.size(); i++) {
			GameObject tempObject = players.get(i);
			tempObject.tick();
		}
	}
	
	public void render(Graphics g) {
		
		for (int i = 0; i < players.size(); i++) {
			GameObject tempObject = players.get(i);
			tempObject.render(g);
		}
		
	}
	
		
	public void addPlayer(GameObject player) {
		this.players.add(player);
		
	}
	
	public void addObject(GameObject object) {
		this.objects.add(object);
		
	}
	
	public void addDeck(Deck deck) {
		this.deck = deck;
	}
//	public Player getPlayer() {
//		for(int i = 0; i < objects.size(); i++) {
//			if(objects.get(i).getId() == ID.Player) {
//				return objects.get(i);
//			}
//		}
//		return null;
//	}
	
	
	public int getPlayerCount() {
		return this.players.size();
	}
	
	public void reset() {
		for(int i = 0; i < players.size(); i++) {
			players.remove(i);
		}
	}
	
}
