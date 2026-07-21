import javax.swing.JFrame;

public class Main{
public static void main(String[] args){
int row=21;
int column=19;
int areaGame=32;
int Width=row*areaGame;
int Height=column*areaGame;



JFrame window=new JFrame("Pac-Man Game");
window.setSize(Height,Width);
window.setLocationRelativeTo(null);
window.setResizable(false);
window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
Pacman pacman=new Pacman();
window.add(pacman);
window.pack();
pacman.requestFocus();
window.setVisible(true);
}
}
