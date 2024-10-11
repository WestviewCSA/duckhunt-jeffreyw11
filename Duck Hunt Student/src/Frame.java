import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	blueSus Blue = new blueSus();
	Background b = new Background();
	meteor met1 = new meteor();
	meteor met2 = new meteor();
	meteor met3 = new meteor();
	redSus red = new redSus();
	Font Font1 = new Font("Serif", Font.BOLD, 20);
	Font Font2 = new Font("Serif", Font.BOLD, 50);
	bullet bullet = new bullet();
	
	boolean shot = false;
	int shots=0;
	int Tscore=0;
	int roundTimer=30;
	int score=0;
	int Tshots=0;
	int time;
	int roundN=1;
	String fall = "N";
	String acc = "";
	boolean over = false;
	boolean hit = false;
	int xPos = 560;
	int yPos = 400;


	//init any variables, objects etc for the start of the 
	public void init() {
		System.out.println("init");
		met1.setScale(0.5,0.5);
		met2.setScale(0.5,0.5);
		met3.setScale(0.5,0.5);
		met1.setVx(2);
		met2.setVx(4);
		met2.setY(100);
		met3.setY(150);
		met3.setVx(3);
		red.setXY(-40, 10);
		red.setX(-20);
		red.setScale(0.2, 0.2);
		red.setWidthHeight(65, 65);
		red.setVx(2);
		Blue.setScale(0.5,0.5);
		Blue.setXY(0,560);
		Blue.setVy(0);
		Blue.setVx(5);
		xPos = 560;
		yPos = 400;
		fall = "N";
		bullet.setXY(10000, 10000);
		bullet.setScale(2.5,2.5);
	}
	public void falling() {
		System.out.println("falling");
	    int redX = red.getX();
	    int blueX = Blue.getX()+90;
	    int distance = Math.abs(blueX - redX);
	    
	    if (distance <= 10) {
	        Blue.setVx(0);
	    } 
	    else if (blueX < redX) {
	        Blue.setVx(20);
	    } 
	    else {
	        Blue.setVx(-20); 
	    }
		
		if(red.getY()>=570&&distance<40) {
		    	Blue.setVy(14);
		    	Blue.setVx(0);
		    	}
	}
	public void reset() {
		System.out.println("reset");
		int location;
		
		red.changePicture("/imgs/redimposter.gif");
		boolean isPositive = Math.random() < 0.5;
	    if (isPositive) {
	        red.setVy((int)(Math.random() * 7) + 8); // 8 to 14
	    } else {
	        red.setVy((int)(Math.random() * -7) - 8); // -8 to -14
	    }
	    if (Math.random() < 0.5) {
		    location = 1200;
		    red.setVx(-2);
		} else {
		    location = -40;
		    red.setVx(2);
		}
	    Blue.setVx(5);
	    Blue.setVy(0);
	    Blue.setXY(0,560);
		
		red.setXY(location, (int)(Math.random() * 301));
		fall = "N";
	}

	public void gameOver() {
		System.out.println("game over");
		if(Tshots!=0) {
			acc = "Accuracy: "+ (int)100* Tscore / Tshots + "%";
		}
		else {
			acc = "You did not shoot and failed the colony";
		}
		over = true;
		shots=0;
		Tscore=0;
		roundTimer=30;
		score=0;
		Tshots=0;
		time=0;
		roundN=1;
		fall = "N";
	}
	
	public void restart() {
		System.out.println("restart");
		xPos=10000;
		yPos=10000;
		over=true;
		over=false;
		init();
	}
	
		
	public void roundOver(boolean win) {
		System.out.println("roundover");
		Tscore=score+Tscore;
		Tshots=Tshots+shots;
		score=0;
		shots=0;	
		over = false;
		roundTimer=30+5*(roundN-1);
		if(win==true) {
			roundN++;
			init();
		}
		else if(win==false) {
			gameOver();
		}
		
	}
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		time+=20;
		
		if(time%1000==0) {
			roundTimer-=1;
			//what to do after complete round
		}
		
		
		b.paint(g);
		if(shot==true) {
			bullet.paint(g);
			shot=false;
		}
		
		g.setColor(Color.gray);
		g.fillRect(300, 780, 600, 70);//center box
		g.fillRect(100, 780, 100, 70);//left box
		g.fillRect(970, 780, 160, 70);//right box
		g.setColor(new Color(20, 13, 7));
		g.fillRect(300, 780, 600, 4);//center trim tops
		g.fillRect(300, 847, 600, 4);
		g.fillRect(100, 780, 100, 4);//left trim tops
		g.fillRect(970, 780, 160, 4);
		g.fillRect(100, 847, 100, 4);//right top trims
		g.fillRect(970, 847, 160, 4);
		
		g.fillRect(300, 780, 4, 70);//center box trim sides
		g.fillRect(897, 780, 4, 70);
		g.fillRect(100, 780, 4, 70);//left box trim sides
		g.fillRect(197, 780, 4, 70);
		g.fillRect(970, 780, 4, 70);//right box trim sides
		g.fillRect(1127, 780, 4, 70);
		
		g.setFont(Font2);
		g.drawString("ROUND "+roundN, 500, 830);
		
		g.setFont(Font1);
		g.drawString(""+roundTimer, 860, 820);
		g.drawString("Amount to kill:", 320, 805);
		g.drawString(""+(5+5*(roundN-1)), 375, 832);
		g.drawString("Score: "+(score), 1015, 820);
		g.drawString("Shots: "+shots, 115, 820);
		
		
		
		Blue.paint(g);
		
		
		met1.paint(g);
		met2.paint(g);
		met3.paint(g);
	
		if(met1.getX()>1200) {
			met1.setX(-200);
		}
		if(met2.getX()>1200) {
			met2.setX(-200);
		}
		if(met3.getX()>1200) {
			met3.setX(-200);
		}
		

		red.paint(g);
		//layer objects in order
		if(red.getY()>1400||Blue.getY()>900) {
			reset();
		}
		if((red.getY()<0||red.getY()>400)&&red.getVx()!=0) {
			red.setVy(red.getVy()*-1);
			boolean isPositive = Math.random() < 0.5;
		    if (isPositive) {
		        red.setVx((int)(Math.random() * 4) + 3); 
		    } else {
		        red.setVx((int)(Math.random() * -4) - 3); 
		    }
		}
		
		if(Blue.getX()<-1||Blue.getX()>1100) {
			Blue.setVx(Blue.getVx()*-1);
		}
		
		if(fall=="Y") {
			falling();
		}
		if(red.getX()>1250) {
			red.setX(-40);
		}
		if(red.getX()<-50) {
			red.setX(1240);
		}
		
		if(score==5+5*(roundN-1)&&red.getY()>840) {
			roundOver(true);
		}
		
		if(roundTimer<0) {
			t.stop();
			roundOver(false);
		}
		
		if(over==true) {
			g.drawString("GAME OVER", xPos, yPos);
			g.drawString(""+ acc, xPos, yPos+20);
			g.drawString("SPACEBAR to restart", xPos, yPos+40);
		}
	}
	
	public static void main(String[] arg) {
		Frame f = new Frame();
	}
	
	
	
	
	public Frame() {
		JFrame f = new JFrame("Duck Hunt");
		f.setSize(new Dimension(1200, 900));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		
		init();
		
		
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	Timer t = new Timer(16, this);
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub
	
	StdAudio.playInBackground("/audio/laser.wav");
	bullet.setXY(m.getX()-105, m.getY()-110);shot=true;
	Rectangle rMouse = new Rectangle(m.getX(), m.getY(), 20,20);//guess and check size for mouse
	
	Rectangle rMain = new Rectangle(red.getX(), red.getY(), red.getWidth(),red.getHeight());
	shots+=1;
	if(rMouse.intersects(rMain)) {
		if(red.getVx()!=0) {
			score++;
		}
		fall="Y";
		red.setVy(14);
		red.setVx(0);
		red.changePicture("/imgs/redfall.png");
		StdAudio.playInBackground("/audio/impostersound.wav");
	}
	
	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());
		
		if(arg0.getKeyCode()==32&&over==true) {
			if(!t.isRunning()) {
				t.start();
				restart();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
