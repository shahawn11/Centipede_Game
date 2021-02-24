import java.awt.FlowLayout;

import javax.swing.JFrame;


public class CentipedeApp extends JFrame {
	private int _width;
	private int _height;
	
	public CentipedeApp(String title){
        super(title);
        _width = CentipedeConstants.BOARD_WIDTH;
        _height = CentipedeConstants.BOARD_HEIGHT;
        this.setSize(_width, _height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(new GamePanel());
        this.setVisible(true);

    }
	
	public static void main(String[] args) {
		CentipedeApp app = new CentipedeApp("Centipede");
	}
}
