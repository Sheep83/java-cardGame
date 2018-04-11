package cardGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Game extends Canvas implements Runnable{
	static final long serialVersionUID = 40685470597404756L;
	public Deck deck;
	public ArrayList<Player> players;
	private Thread thread;
	private boolean running = false;
	public Handler handler;
	public static final int WIDTH = 1280, HEIGHT = 1024;
	public static enum STATE {
		Menu,
		Game, 
		Setup
	};
	public static STATE gameState;
	private Menu menu;
	private SetupMenu setupMenu;
	private HUD hud;
	private BufferedImage background = null;
//	  public static ArrayList<String> initNames = new ArrayList<String>();
//	  public Player player, player1, player2;
//	  public Random random;
//	  public static boolean gameActive = true;
//	  public int outCount = 0;
//	  public Hand hand;
//	  // public Display display;
//
//	  //GAME CONSTRUCTOR
	  public Game(){
		new Window(WIDTH, HEIGHT, "cardGame", this);
		MouseControl mouseInput = new MouseControl(handler);
		this.addMouseListener(mouseInput);
		this.addMouseMotionListener(mouseInput);
//	    this.players = new ArrayList<Player>();
	    this.deck = new Deck();
	    this.handler = new Handler();
	    handler.addDeck(deck);
	    this.hud = new HUD(handler);
	    gameState = STATE.Menu;
	    menu = new Menu(this, handler);
	    setupMenu = new SetupMenu(this, handler);
	  }
	  
	  public synchronized void start() {
			
			thread = new Thread(this);
			thread.start();
			running = true;
		}
		
		public synchronized void stop() {
		
			try {
				thread.join();
				running = false;
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		public void run()
	    {
			this.requestFocus();
	        long lastTime = System.nanoTime();
	        double amountOfTicks = 60.0;
	        double ns = 1000000000 / amountOfTicks;
	        double delta = 0;
	        int updates = 0;
	        int frames = 0;
	        long timer = System.currentTimeMillis();
	        while(running){
	        	long now = System.nanoTime();
	            delta += (now - lastTime) / ns;
	            lastTime = now;
	            while(delta >=1){
	            	tick();
	            	updates ++;
	                delta--;
	                }
	            
	            if(running)
	            	render();
	                frames++;
	                            
	                if(System.currentTimeMillis() - timer > 1000){
	                	timer += 1000;
	                	System.out.println("FPS: "+ frames + "   Ticks: " + updates);
	                	updates = 0;
	                	frames = 0;
	                 }
	        }
	        stop();	
		}
		
		private void tick() {
			
			if(gameState == STATE.Menu) {
				menu.tick();
			}
			else if(gameState == STATE.Game) {
				handler.tick();
				hud.tick();
				
			}else if(gameState == STATE.Setup) {
				setupMenu.tick();
//				hud.tick();
				
			}
////				soundPlayer.stopSound();
////				soundPlayer.currentClip = null;
////				gameOverCheck();
//				if(Game.turn == TURN.Player) {
//					Player player = (Player)handler.getPlayer();
////					Game.camera.offsetX = (player.getX() + Game.WIDTH/2)*-1;
////					Game.camera.offsetY = (player.getY() + Game.HEIGHT/2)*-1;
////					System.out.println("offsetx: " + Game.camera.offsetX);
////					System.out.println("offsety: " + Game.camera.offsetY);
//					hud.tick();	
//					handler.tick();	
//				}else if(Game.turn == TURN.Enemy) {
//					Enemy enemy = (Enemy)handler.getEnemy();
////					Game.camera.setOffsetX(enemy.getX() + Game.WIDTH/2);
////					Game.camera.setOffsetY(enemy.getY() + Game.HEIGHT/2);
//					hud.tick();	
//					handler.tick();
//				}
////				hud.tick();	
////				handler.tick();
////				Player player = (Player)handler.getPlayer();
////				if(targetTile != null) {
//////					for(int i = 0; i < handler.tiles.size(); i++) {
//////						GameTile tile = (GameTile)handler.tiles.get(i);
////////						tile.setPath(false);
//////					}
//////					path = finder.findPath(player, player.getMapX(), player.getMapY(), targetTile.getMapX(), targetTile.getMapY());
////				}
////				path = finder.findPath(player, player.getMapX(), player.getMapY(), 16, 3);
//			}
		}
		
		private void render() {
			BufferStrategy bs = this.getBufferStrategy();
			if (bs == null) {
				this.createBufferStrategy(3);
				return;
			}
			Graphics g = bs.getDrawGraphics();
			
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			if(gameState == STATE.Game) {
				g.drawImage(background, 0, 0, this);
				handler.render(g);
				hud.render(g);	
			}else if (gameState == STATE.Menu) {
				menu.render(g);
			}else if (gameState == STATE.Setup) {
				setupMenu.render(g);
			}
			
			g.dispose();
			bs.show();
		}
		
		public static void setState(STATE state) {
			gameState = state;
		}
		
		public static void main(String[] args) {
			new Game();
		}
//
//	  public Player createPlayer(String name, Hand hand){
//	    Player player = new Player (name, hand);
//	    players.add(player);
//	    System.out.println("Player " + player.getName() + " created with a hand of " + player.getHandSize());
//	    return player;
//	  }
//	  public void createPlayers(ArrayList<String> initNames, Deck deck){
//	    for (String name : initNames){
//	      Hand hand = new Hand(deck, 0);
//	      this.createPlayer(name, hand);
//	    }
//	  }
//	  public ArrayList<Player> getPlayers(){
//	    System.out.println("There are " + players.size() + " players");
//	    System.out.println(players);
//	    return players;
//	  }
//	  public Player getPlayerByIndex(Integer index){
//	    return players.get(index);
//	  }
//	  public void allTakeCards(ArrayList<Player> players){
//	    if (players.size() > 1){
//	      for (int i=0;i<players.size()-1;i++) {
//	        Player takingPlayer = this.players.get(i);
//	        Player givingPlayer = this.players.get(i+1);
//	        // Random random = new Random();
//	        // int  n = random.nextInt(givingPlayer.getHandSize() + 0);
//	        // System.out.println(n);
//	        int n = viewer.chooseCard(takingPlayer, givingPlayer);
//	        takingPlayer.takeFromPlayer(givingPlayer, n);
//	        System.out.println(takingPlayer.getName() + " takes a card from " + givingPlayer.getName());
//	        if(n == 0 && givingPlayer.checkIfOut() == true){
//	          players.remove(givingPlayer);
//	          this.outCount +=1;
//	        }
//	        takingPlayer.dropPairs();
//	        if(takingPlayer.checkIfOut() == true){
//	          players.remove(takingPlayer);
//	          this.outCount +=1;
//	        }  
//	      }
//	      Player lastPlayer = this.players.get(this.players.size()-1);
//	      Player firstPlayer = this.players.get(0);
//	      int x = viewer.chooseCard(lastPlayer, firstPlayer);
//	      // Random random = new Random();
//	      // int  x = random.nextInt(firstPlayer.getHandSize() + 0);
//	      // System.out.println(x);
//	      if(x == 0 && firstPlayer.checkIfOut() == true){
//	        players.remove(firstPlayer);
//	        this.outCount +=1;
//	      }
//	      lastPlayer.takeFromPlayer(firstPlayer, x);
//	      System.out.println(lastPlayer.getName() + " takes a card from " + firstPlayer.getName());
//	      lastPlayer.dropPairs();
//	      if(lastPlayer.checkIfOut() == true){
//	        this.outCount +=1;
//	        players.remove(lastPlayer);
//	      }
//	    }else
//	    {
//	      this.gameOver(players);
//	    }
//	  }
//	  public void getAllHandSizes(){
//	    for (Player player : players){
//	      System.out.println(player.getHandSize());
//	    }
//	  }
//	  public Deck createDeck(){
//	    return new Deck();
//	  }
//	  public void showAllHands(ArrayList<Player> players){
//	    for (Player player : players){
//	      System.out.print(player.getName() + "'s Hand : ");
//	      player.hand.printCards();
//	      // System.out.println("");
//	    }
//
//	  }
//	  public void dropAllPairs(ArrayList<Player> players){
//	    for (Player player : players){
//	      player.dropPairs();
//	      System.out.println(player.getHandSize());
//	    }
//	  }
//	  public void gameOver(ArrayList<Player> players){
//	    System.out.println(" ");
//	    System.out.println("Game Over! " + players.get(0).getName() + " wins/loses :)");
//	  }
//
//
//	//MAIN ENTRY POINT
//	  public static void main(String[] args){
//	    while (gameActive == true){
//	      Game game = new Game();
//	      deck = game.createDeck();
//	      deck.dropQueens();
//	      // Display display = new Display();
//	      viewer = new Viewer();
//	    //Interface goes here
//	      int gameType = viewer.chooseGame();
//	      if (gameType == 1){
//	      initNames = viewer.gameStart();
//	      game.createPlayers(initNames, deck);
//	      deck.dealToAll(players);
//	      System.out.println(deck.getCardsLeft());
//	      game.dropAllPairs(players);
//	      game.dropAllPairs(players);
//	      while(game.outCount < initNames.size()-1){
//	        game.allTakeCards(players);
//	        game.showAllHands(players);
//	      }
//	      game.gameOver(players);
//	      if (viewer.playAgain() == false){
//	        gameActive = false;
//	      }
//	    }
//	  }
//	}
	}
