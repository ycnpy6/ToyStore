package com.toystore.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Small utility to generate synthetic product thumbnail images.
 * Generates 400x300 PNGs with orange theme and product name text.
 */
public class ImageGenerator {
    public static void main(String[] args) throws IOException {
        int w = 400, h = 300;
        Map<String,String> files = new LinkedHashMap<>();
        files.put("images/lego_classic.jpg", "LEGO Classic");
        files.put("images/barbie_dreamhouse.jpg", "Barbie Dreamhouse");
        files.put("images/hotwheels_track.jpg", "Hot Wheels Track");
        files.put("images/nerf_blaster.jpg", "Nerf Blaster");
        files.put("images/wooden_puzzle.jpg", "Wooden Puzzle");
        files.put("images/rc_car.jpg", "RC Car");
        files.put("images/art_kit.jpg", "Art Supplies Kit");
        files.put("images/board_games.jpg", "Board Games");

        for (Map.Entry<String,String> e : files.entrySet()) {
            File out = new File(e.getKey());
            out.getParentFile().mkdirs();
            BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = img.createGraphics();

            // smooth
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // background gradient (orange to light)
            Color c1 = new Color(255, 140, 0);
            Color c2 = new Color(255, 220, 180);
            GradientPaint gp = new GradientPaint(0, 0, c1, 0, h, c2);
            g.setPaint(gp);
            g.fillRect(0, 0, w, h);

            // draw rounded panel
            g.setColor(new Color(255, 248, 240, 200));
            RoundRectangle2D rr = new RoundRectangle2D.Double(20, 20, w-40, h-70, 20, 20);
            g.fill(rr);

            // simple icon: circle with star-like spikes
            int cx = 80, cy = 110, radius = 48;
            g.setColor(new Color(255, 165, 0));
            g.fillOval(cx-radius, cy-radius, radius*2, radius*2);
            g.setColor(new Color(255, 230, 200));
            g.setStroke(new BasicStroke(3f));
            for (int i=0;i<8;i++) {
                double ang = Math.toRadians(i * 45);
                int x1 = (int)(cx + Math.cos(ang)*(radius-6));
                int y1 = (int)(cy + Math.sin(ang)*(radius-6));
                int x2 = (int)(cx + Math.cos(ang)*(radius+14));
                int y2 = (int)(cy + Math.sin(ang)*(radius+14));
                g.drawLine(x1,y1,x2,y2);
            }

            // product name
            g.setColor(new Color(60, 35, 20));
            Font font = new Font("Arial", Font.BOLD, 26);
            g.setFont(font);
            String name = e.getValue();
            FontMetrics fm = g.getFontMetrics();
            int tx = 160;
            int ty = 110;
            // Draw wrapped if long
            drawWrappedString(g, name, tx, ty, w - tx - 30, fm);

            // price tag area
            g.setColor(new Color(204, 85, 0));
            int tagW = 140, tagH = 40;
            int tagX = w - tagW - 30;
            int tagY = h - tagH - 30;
            g.fillRoundRect(tagX, tagY, tagW, tagH, 12, 12);
            g.setColor(Color.WHITE);
            Font pf = new Font("Arial", Font.BOLD, 18);
            g.setFont(pf);
            String price = String.format("%d.%02d DZD", (int)(Math.random()*50)+10, (int)(Math.random()*100));
            FontMetrics pfm = g.getFontMetrics();
            int px = tagX + (tagW - pfm.stringWidth(price)) / 2;
            int py = tagY + (tagH + pfm.getAscent()) / 2 - 4;
            g.drawString(price, px, py);

            g.dispose();

            ImageIO.write(img, "jpg", out);
            System.out.println("Generated " + out.getPath());
        }
    }

    private static void drawWrappedString(Graphics2D g, String text, int x, int y, int maxWidth, FontMetrics fm) {
        String[] words = text.split(" ");
        String line = "";
        int lineHeight = fm.getHeight();
        int curY = y;
        for (String w : words) {
            String test = line.isEmpty() ? w : line + " " + w;
            if (fm.stringWidth(test) > maxWidth) {
                g.drawString(line, x, curY);
                line = w;
                curY += lineHeight;
            } else {
                line = test;
            }
        }
        if (!line.isEmpty()) {
            g.drawString(line, x, curY);
        }
    }
}
