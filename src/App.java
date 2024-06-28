import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame obj = new JFrame();
        Core coreGame = new Core();
        obj.setResizable(false);
        obj.setBounds(10, 10, 808, 600);
        obj.setTitle("Brick Breaker Project");
        //obj.setLocationRelativeTo(null);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(coreGame);
    }
}
