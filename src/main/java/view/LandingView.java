package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The Landing View for navigating to Login or Signup.
 */
public class LandingView extends JPanel {
    private final String viewName = "landing";

    public LandingView(ActionListener loginListener, ActionListener signupListener) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Welcome to AI CHAT!");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginButton = createStyledButton("Login", new Color(66, 133, 244), Color.WHITE, false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(loginListener);

        JButton signupButton = createStyledButton("Signup", Color.WHITE, Color.BLACK, true);
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.addActionListener(signupListener);

        add(Box.createVerticalStrut(20));
        add(title);
        add(Box.createVerticalStrut(30));
        add(loginButton);
        add(Box.createVerticalStrut(20));
        add(signupButton);
    }

    /**
     * Helper method to create styled buttons.
     */
    private JButton createStyledButton(String text, Color bgColor, Color fgColor, boolean withBorder) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                if (withBorder) {
                    g2.setColor(fgColor);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                }

                g2.setColor(fgColor);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - fm.getDescent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(300, 40));
        button.setMaximumSize(new Dimension(300, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public String getViewName() {
        return viewName;
    }
}
