import java.util.ArrayList;

import org.lwjgl.LWJGLException;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class System {
	private ArrayList<Point> listOfPoints;
	private ArrayList<Stick> listOfSticks;
	

	private int width;
	private int height;
	private double gravity;
	private double friction;
	
	public System(int width,int height,double gravity,double friction)
	{
		this.listOfPoints = new ArrayList<Point>();
		listOfSticks =  new ArrayList<Stick>();
		this.width = width;
		this.height = height;
		this.gravity = gravity;
		this.friction = friction;
		
	}
	
	public void checkCollision()
	{
		for(int i=0;i<this.listOfPoints.size();i++)
			for(int k=i+1;k<this.listOfPoints.size();k++)
			{
				if(		Math.sqrt(Math.pow(this.listOfPoints.get(k).getX() -this.listOfPoints.get(i).getX() , 2) +  //this.listOfPoints.get(i).getX() == this.listOfPoints.get(k).getX() &&
						Math.pow(this.listOfPoints.get(k).getY() -this.listOfPoints.get(i).getY() , 2) ) < 40
						)
				{
					
					glBegin(GL_QUADS);
					glVertex2i(this.listOfPoints.get(i).getX()-5,this.listOfPoints.get(i).getY()-5);
					glVertex2i(this.listOfPoints.get(i).getX()+5,this.listOfPoints.get(i).getY()-5);
					glVertex2i(this.listOfPoints.get(i).getX()+5,this.listOfPoints.get(i).getY()+5);
					glVertex2i(this.listOfPoints.get(i).getX()-5,this.listOfPoints.get(i).getY()+5);
					glEnd();
					
					int cur_i_X = this.listOfPoints.get(i).getX();
					int cur_i_Y = this.listOfPoints.get(i).getY();
					int old_i_X = this.listOfPoints.get(i).getOldX();
					int old_i_Y = this.listOfPoints.get(i).getOldY();
					
					int cur_k_X = this.listOfPoints.get(k).getX();
					int cur_k_Y = this.listOfPoints.get(k).getY();
					int old_k_X = this.listOfPoints.get(k).getOldX();
					int old_k_Y = this.listOfPoints.get(k).getOldY();
					
				
					
					
					
					int pX = cur_i_X - cur_k_X;
					int pY = cur_i_Y - cur_k_Y;
					
					
					
					
					
					int speedIx = Math.abs(old_i_X - cur_i_X);
					int speedIy = Math.abs(old_i_Y - cur_i_Y);
					
					int speedKx = Math.abs(old_k_X - cur_k_X);
					int speedKy = Math.abs(old_k_Y - cur_k_Y);
					
					
					
					//pX /= divisor;
					//pY /= divisor;
					
					
					
					
					int iNewX = -pX + speedIx;
					int iNewY = -pY + speedIy;
					
					int kNewX = pX + speedKx;
					int kNewY = pY + speedKy;
					
					
					int p1X = cur_k_X+kNewX;
					int p1Y = cur_k_Y+kNewY;
					
					int p2X = cur_k_X+speedKx*-20;
					int p2Y = cur_k_Y+speedKy*-20;
					
					float finXVec_k = (p2X - p1X);
					float finYVec_k = (p2Y - p1Y);
					
					float divisor = finXVec_k / speedKx;
					
					finXVec_k /= divisor;
					finYVec_k /= divisor;
					
					//finXVec_i = finXVec_i/(finXVec_i/speedKx);
					//finYVec_i = finYVec_i/(finYVec_i/speedKy);
					
					this.listOfPoints.get(k).setOldX( cur_k_X + (int)finXVec_k);
					this.listOfPoints.get(k).setOldY( cur_k_Y + (int)finYVec_k);
				
					
					glColor3f(1.0f,1.0f,1.0f);
					Point.DrawCircle(p1X, p1Y, 4, 20);
					Point.DrawCircle(p2X, p2Y, 4, 20);
					
					glColor3f(1.0f,0.0f,1.0f);
					
					
				    glBegin(GL_LINES);
						glVertex2i(p1X,p1Y);
						glVertex2i(p2X,p2Y);
					glEnd();
						glColor3f(1.0f,1.0f,1.0f);
					
					glBegin(GL_LINES);
					glVertex2i(cur_k_X,cur_k_Y);
					glVertex2i(cur_k_X+kNewX,cur_k_Y+kNewY);
				glEnd();
					
					
					glColor3f(0.5f,0.5f,0.5f);
					
					glBegin(GL_LINES);
						glVertex2i(cur_i_X,cur_i_Y);
						glVertex2i(cur_i_X+iNewX,cur_i_Y+iNewY);
					glEnd();
					
					
					
					
				/*	int iOldX = this.listOfPoints.get(i).getOldX();
					int iOldY = this.listOfPoints.get(i).getOldY();
					int kOldX = this.listOfPoints.get(k).getOldX();
					int kOldY = this.listOfPoints.get(k).getOldY();
					
					
					this.listOfPoints.get(i).setOldX(kOldX);
					this.listOfPoints.get(i).setOldY(kOldY);
					
					this.listOfPoints.get(k).setOldX(iOldX);
					this.listOfPoints.get(k).setOldY(iOldY);
					*/
					
					
				}
					
			}
	}
	public void addPoint(Point p)
	{
		this.listOfPoints.add(p);
	}
	
	public void addStick(Stick s)
	{
		this.listOfSticks.add(s);
	}
	
	public void draw()
	{
		for(Point p:this.listOfPoints)
		{
			p.draw();
		}
		for(Stick s:this.listOfSticks)
		{
			s.draw();
		}
	}
	
	public void updatePosition()
	{
		
		for(Point p:this.listOfPoints)
		{
			int vx = (int)((p.getX() - p.getOldX()));
			int vy = (int)((p.getY() - p.getOldY()));
			
			p.setOldX(p.getX());
			p.setOldY(p.getY());
			
			p.setX(p.getX()+vx);
			p.setY(p.getY()+vy);
			p.setY(p.getY()+(int)this.gravity);
			
			if(p.getX() > this.width)
			{
				p.setX(width);
				p.setOldX(p.getX()+vx);
				
			}else if(p.getX() < 0)
			{
				p.setX(0);
				p.setOldX(p.getX()+vx);
			}
			
			if(p.getY() > this.height)
			{
				p.setY(height);
				p.setOldY(p.getY()+vy);
				
			}else if(p.getY() < 0)
			{
				p.setY(0);
				p.setOldY(p.getY()+vy);
			}
			
		}
		
		
		
		
		
	}
}
