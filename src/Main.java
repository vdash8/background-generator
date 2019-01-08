import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        ImagePanel ip = new ImagePanel();
        f.add(ip);

        f.setTitle("Background Generator");
        f.setSize(5120, 2880);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private static class ImagePanel extends JPanel {

        private BufferedImage mBuf;

        public ImagePanel() {
            super();
            mBuf = new BufferedImage(5120, 2880, BufferedImage.TYPE_INT_ARGB);

            ImageGenerator imgen = new ImageGenerator(mBuf);
            imgen.generate();
        }

        public BufferedImage getBufferedImage() {
            return mBuf;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                File out = new File("back.png");
                ImageIO.write(mBuf, "png", out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(mBuf, 0, 0, null);

        }
    }


}
