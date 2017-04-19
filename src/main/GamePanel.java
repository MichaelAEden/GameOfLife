package main;

import gameState.GameStateManager;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GamePanel extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener
{
	//dimensions
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 756;
	public static final int SCALE = 1;
	
	//game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private static int currentFPS;
	private static int ticks = 0;
	
	//image
	private BufferedImage image;
	private Graphics2D g;
	
	//game state manager
	private GameStateManager gsm;
	
	//JFrame
	private JFrame frame;

	public GamePanel(JFrame frame)
	{
		super();
		
		this.frame = frame;
		
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		setFocusable(true);
		requestFocus();
	}

	//Called once the JPanel is finished loading
	//Creates thread
	public void addNotify()
	{
		super.addNotify();
		if (thread == null)
		{
			gsm = new GameStateManager();
			
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			thread.start();
		}
	}
	
	//Called from run()
	//Initializes and updates some variables
	private void init()
	{	
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)image.getGraphics();
		
		running = true;
	}

	//Called once the thread starts
	//Contains the game loop
	public void run() 
	{
		init();
		
		long milliStart = System.currentTimeMillis();
		
		long start;
		int elapsedTime;
		int sleepTime;
		int targetTime = (int)(1F / FPS * 1000);
		
		//GAME LOOP
		while(running)
		{	
			start = System.nanoTime();
			
			update();
			
			elapsedTime = (int)(System.nanoTime() - start) / 1000000;
			sleepTime = targetTime - elapsedTime;
			if (sleepTime < 0)
			{
				sleepTime = 0;
			}
			try
			{
				Thread.sleep(sleepTime);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			ticks++;
			
			if (System.currentTimeMillis() - milliStart > 1000)
			{
				milliStart = System.currentTimeMillis();
				currentFPS = ticks;
				ticks = 0;
				
				frame.setTitle("The Game Of Life : " + currentFPS + " FPS");
			}
		}
	}
	
	//Called every 1/60 of a second from run()
	//Updates the GameStateManager
	private void update() 
	{
		//updates 
		gsm.update();
		gsm.draw(g);
		
		//draws to screen
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		Graphics g2 = bs.getDrawGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		g2.dispose();
		bs.show();
		
	}
	
	public static int getTimeInTicks()
	{
		return ticks;
	}
	
	/*KeyListener and MouseListener Methods*/

	public void keyPressed(KeyEvent key) 
	{
		gsm.keyPressed(key.getKeyCode());
	}
	public void mouseClicked(MouseEvent mouse) 
	{
		gsm.mouseClicked(mouse);
	}
	public void mousePressed(MouseEvent mouse) 
	{
		gsm.mousePressed(mouse);
	}
	public void mouseReleased(MouseEvent mouse)
	{
		gsm.mouseReleased(mouse);
	}
	public void mouseMoved(MouseEvent mouse) 
	{
		gsm.mouseMoved(mouse);
	}
	public void mouseDragged(MouseEvent mouse)
	{
		gsm.mouseDragged(mouse);
	}
	
	public void keyTyped(KeyEvent key){}
	public void keyReleased(KeyEvent key) {}
	public void mouseEntered(MouseEvent mouse) {}
	public void mouseExited(MouseEvent mouse) {}
}
