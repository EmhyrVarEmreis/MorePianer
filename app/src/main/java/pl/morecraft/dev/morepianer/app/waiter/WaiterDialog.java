package pl.morecraft.dev.morepianer.app.waiter;

import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static pl.morecraft.dev.morepianer.app.waiter.WaiterControl.*;

public final class WaiterDialog extends JDialog {

    private ImageIcon imageIcon;

    public WaiterDialog() {

        try {
            imageIcon = new ImageIcon(LoadingPanel.class.getResource(IMG_LOADING));
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error("Unable to load image: " + IMG_LOADING, e);
            System.exit(0);
        }

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        //this.setModal(true);
        //this.setModalityType(ModalityType.APPLICATION_MODAL);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        this.setLayout(new GridLayout(1, 1));
        this.getContentPane().add(new LoadingPanel());

        this.setBackground(new Color(0, 0, 0, 0));

        Thread waiter = new WaiterForInitiationThread();
        waiter.start();

        this.setVisible(true);
    }

    private class LoadingPanel extends JPanel {

        private LoadingPanel() {
            this.setOpaque(false);
            this.setLayout(new GridBagLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            imageIcon.paintIcon(this, g2d, 0, 0);
            g2d.dispose();

        }

    }

    private class WaiterForInitiationThread extends Thread {

        @Override
        public void run() {
            int i = 0;
            while (i < WAITER_TRYINGS) {
                try {
                    sleep(WAITER_SLEEP);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    System.exit(0);
                }
                if (IS_INITIALISED) {
                    dispose();
                }
                i++;
            }
            if (!IS_INITIALISED) {
                LoggerFactory.getLogger(getClass()).error("Application not initialized; Exiting...");
                System.exit(0);
            }
        }

    }

}
