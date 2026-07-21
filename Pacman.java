import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class Pacman extends JPanel implements ActionListener,KeyListener{
private int row=21;
private int column=19;
private int areaGame=32;
private int Width=column*areaGame;
private int Height=row*areaGame;

private Image wall;
private Image blueGhost;
private Image redGhost;
private Image orangeGhost;
private Image pinkGhost;
private Image leftPacman;
private Image rightPacman;
private Image upPacman;
private Image downPacman;

HashSet<Block> walls;
HashSet<Block> ghosts;
Block pacmans;
HashSet<Block> foods;

public class Block{
int x;
int y;
int Width;
int Height;
int startX;
int startY;
Image image;
char direction;
int velocityX=0;
int velocityY=0;
char[] ghostDirection={'w','s','d','a'};
Random random=new Random();
char newGhostDirection;
int pacmanLife=100;
int ghostDamage=25;
int score=0;

public Block(int x,int y,int Width,int Height,Image image){
this.x=x;
this.y=y;
this.Width=Width;
this.Height=Height;
this.startX=x;
this.startY=y;
this.image=image;
}

public void updateDirection(char direction){
this.direction=direction;
updateVelocity();
}

public void updateVelocity(){
switch(this.direction){
case 'w':
this.velocityX=0;
this.velocityY=-areaGame/4;
break;
case 'a':
this.velocityY=0;
this.velocityX=-areaGame/4;
break;
case 's':
this.velocityY=areaGame/4;
this.velocityX=0;
break;
case 'd':
this.velocityX=areaGame/4;
this.velocityY=0;
break;
}
}

public void resetPosition(){

this.x=this.startX;
this.y=this.startY;
}


}

//Constructor
public Pacman(){
setPreferredSize(new Dimension(Width,Height));//Change the size of the window
setBackground(Color.BLACK);//Set the color to Black
addKeyListener(this);
setFocusable(true);

//Load Images
wall=new ImageIcon(getClass().getResource("Images/wall.png")).getImage();
blueGhost=new ImageIcon(getClass().getResource("Images/blueGhost.png")).getImage();
redGhost=new ImageIcon(getClass().getResource("Images/redGhost.png")).getImage();
orangeGhost=new ImageIcon(getClass().getResource("Images/orangeGhost.png")).getImage();
pinkGhost=new ImageIcon(getClass().getResource("Images/pinkGhost.png")).getImage();
upPacman=new ImageIcon(getClass().getResource("Images/pacmanUp.png")).getImage();
downPacman=new ImageIcon(getClass().getResource("Images/pacmanDown.png")).getImage();
rightPacman=new ImageIcon(getClass().getResource("Images/pacmanRight.png")).getImage();
leftPacman=new ImageIcon(getClass().getResource("Images/pacmanLeft.png")).getImage();

Timer gameLoop=new Timer(42,this);
gameMap(map);


gameLoop.start();
for(Block ghost:ghosts){
ghost.newGhostDirection=ghost.ghostDirection[ghost.random.nextInt(4)];
ghost.updateDirection(ghost.newGhostDirection);
}
}

String[] map={
"XXXXXXXXXXXXXXXXXXX",
"X        X        X",
"X XX XXX X XXX XX X",
"X                 X",
"X XX X XXXXX X XX X",
"X    X       X    X",
"XXXX XXXX XXXX XXXX",
"OOOX X       X XOOO",
"XXXX X XXrXX X XXXX",
"X       bpo       X",
"XXXX X XXXXX X XXXX",
"OOOX X       X XOOO",
"XXXX X XXXXX X XXXX",
"X        X        X",
"X XX XXX X XXX XX X",
"X  X     P   X    X",
"XX X X XXXXX X X XX",
"X  X     X   X    X",
"X XXXXXX X XXXXXX X",
"X                 X",
"XXXXXXXXXXXXXXXXXXX",
};


String[] map1={
"XXXXXXXXXXXXXXXXXXX",
"X O      X      O X",
"X XXXXX XXX XXXXX X",
"X     X   X     X X",
"X XXX X X X XXX X X",
"X   X X   X   X   X",
"X X X XXXXX X X XXX",
"X X   r p   X X   X",
"X X XXXbXXX X X XXX",
"X X X  o  X X X   X",
"X X X XXXXX X X X X",
"X X       P X X X X",
"X XXX XXXXX X X XXX",
"X   X     X X X   X",
"XXX X XXX X X XXX X",
"X   X   X X X   X X",
"X XXXXX X X X XXX X",
"X X   X   X X   OOX",
"X       X   X     X",
"XXXXX X X X X XXXXX",
"XXXXXXXXXXXXXXXXXXX",
};
Block pacman;


public void gameMap(String[] map){
walls=new HashSet<Block>();
foods=new HashSet<Block>();
ghosts=new HashSet<Block>();


for(int i=0;i<row;i++){
for(int j=0;j<column;j++){
String currentCharacter=map[i];
char mapIndex=currentCharacter.charAt(j);

int x=j*areaGame;
int y=i*areaGame;

switch(mapIndex){
case 'X':
Block wallSpawn=new Block(x,y,areaGame,areaGame,wall);
walls.add(wallSpawn);
break;
case 'b':
Block blueGhostSpawn=new Block(x,y,areaGame,areaGame,blueGhost);
ghosts.add(blueGhostSpawn);
break;
case 'p':
Block pinkGhostSpawn=new Block(x,y,areaGame,areaGame,pinkGhost);
ghosts.add(pinkGhostSpawn);
break;
case 'r':
Block redGhostSpawn=new Block(x,y,areaGame,areaGame,redGhost);
ghosts.add(redGhostSpawn);
break;
case 'o':
Block orangeGhostSpawn=new Block(x,y,areaGame,areaGame,orangeGhost);
ghosts.add(orangeGhostSpawn);
break;
case 'P':
pacman=new Block(x,y,areaGame,areaGame,rightPacman);
break;
case ' ':
Block food=new Block(x+14,14+y,4,4,null);
foods.add(food);
break;
}
}
}
}
@Override
public void paintComponent(Graphics g){
super.paintComponent(g);
spawn(g);
}

public void spawn(Graphics g){
g.drawImage(pacman.image,pacman.x,pacman.y,areaGame,areaGame,null);
for(Block ghost:ghosts){
g.drawImage(ghost.image,ghost.x,ghost.y,ghost.Width,ghost.Height,null);
}
g.setColor(Color.WHITE);
for(Block food:foods){
g.fillRect(food.x,food.y,food.Width,food.Height);
}
for(Block wall:walls){
g.drawImage(wall.image,wall.x,wall.y,wall.Width,wall.Height,null);
}
g.setFont(new Font("Times New Roman",Font.BOLD,16));
g.drawString("Life Points: "+String.valueOf(pacman.pacmanLife),areaGame/2,areaGame/2);
g.drawString("Score: "+String.valueOf(pacman.score),areaGame,areaGame);

if((pacman.pacmanLife==0)){
g.drawString("Pacman Death!!!",Width/2,Height/2);
g.drawString("Score: "+String.valueOf(pacman.score),Width,Height);
}
}

@Override
public void actionPerformed(ActionEvent e){
if(!(pacman.pacmanLife==0)){
move();
repaint();
}
}
@Override
public void keyTyped(KeyEvent e){
}
public void keyReleased(KeyEvent e){
}
public void keyPressed(KeyEvent e){

switch(e.getKeyCode()){
case 87:
pacman.updateDirection('w');
pacman.image=upPacman;
break;
case 65:
pacman.updateDirection('a');
pacman.image=leftPacman;
break;
case 68:
pacman.updateDirection('d');
pacman.image=rightPacman;
break;
case 83:
pacman.updateDirection('s');
pacman.image=downPacman;
break;
}
}

public void move(){
pacman.x+=pacman.velocityX;
pacman.y+=pacman.velocityY;

for(Block wall:walls){
if(collision(wall,pacman)){
pacman.x-=pacman.velocityX;
pacman.y-=pacman.velocityY;
break;
}
}

for(Block ghost:ghosts){
ghost.x+=ghost.velocityX;
ghost.y+=ghost.velocityY;
if(ghost.y==9*areaGame && ghost.direction!='w' && ghost.direction!='s')
ghost.updateDirection(ghost.ghostDirection[ghost.random.nextInt(2)]);
for(Block wall:walls){
if(collision(ghost,wall)||ghost.x<=0 || ghost.x +ghost.Width>=Width){
ghost.x-=ghost.velocityX;
ghost.y-=ghost.velocityY;
ghost.newGhostDirection=ghost.ghostDirection[ghost.random.nextInt(4)];
ghost.updateDirection(ghost.newGhostDirection);
}
}
}

for(Block ghost:ghosts){

if(collision(pacman,ghost)){
pacman.resetPosition();
ghost.resetPosition();
if(pacman.pacmanLife>0)
pacman.pacmanLife-=ghost.ghostDamage;
}
}
for(Block food:foods){
if(collision(food,pacman)){
foods.remove(food);
pacman.score++;
break;
}if(foods.size()==0){
gameMap(map1);
}
}
}

public boolean collision(Block object1,Block object2){

return  object1.x<object2.x + object2.Width&&
        object1.x+object1.Width>object2.x  &&
        object1.y<object2.y+object2.Height &&
        object1.y+object1.Height>object2.y;
}
}

