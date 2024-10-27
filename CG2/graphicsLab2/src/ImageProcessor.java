import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImageProcessor {

    private JFrame frame;
    private JPanel panel;
    private JLabel imageLabel;
    private BufferedImage image;
    private BufferedImage processedImage;

    public ImageProcessor() {
        frame = new JFrame("Image Processor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        imageLabel = new JLabel();
        panel.add(imageLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10));
        frame.getContentPane().add(buttonPanel, BorderLayout.EAST);

        JButton openImageBtn = new JButton("Open Image");
        openImageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImage();
            }
        });
        buttonPanel.add(openImageBtn);

        JButton originalImageBtn = new JButton("Show Original");
        originalImageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOriginalImage();
            }
        });
        buttonPanel.add(originalImageBtn);

        JButton addConstBtn = new JButton("Add Constant");
        addConstBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addConstant();
            }
        });
        buttonPanel.add(addConstBtn);

        JButton multiplyConstBtn = new JButton("Multiply by Constant");
        multiplyConstBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                multiplyConstant();
            }
        });
        buttonPanel.add(multiplyConstBtn);

        JButton powerBtn = new JButton("Power Operation");
        powerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                powerOperation();
            }
        });
        buttonPanel.add(powerBtn);

        JButton logTransformBtn = new JButton("Logarithmic Transformation");
        logTransformBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logarithmicTransformation();
            }
        });
        buttonPanel.add(logTransformBtn);
        JButton negativeBtn = new JButton("Invert (Negative)");
        negativeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                negativeOperation();
            }
        });
        buttonPanel.add(negativeBtn);

        JButton histogramBtn = new JButton("Show Histogram");
        histogramBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHistogram();
            }
        });
        buttonPanel.add(histogramBtn);

        JButton equalizeHistBtn = new JButton("Equalize Histogram (Gray)");
        equalizeHistBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equalizeHistogram();
            }
        });
        buttonPanel.add(equalizeHistBtn);

        JButton equalizeRGBBtn = new JButton("Equalize RGB Histogram");
        equalizeRGBBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equalizeRGBHistogram();
            }
        });
        buttonPanel.add(equalizeRGBBtn);

        JButton equalizeHSVBtn = new JButton("Equalize HSV Histogram");
        equalizeHSVBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equalizeHSVHistogram();
            }
        });
        buttonPanel.add(equalizeHSVBtn);

        JButton contrastBtn = new JButton("Linear Contrast");
        contrastBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linearContrast();
            }
        });
        buttonPanel.add(contrastBtn);


        frame.setVisible(true);
    }

    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                image = ImageIO.read(selectedFile);
                processedImage = copyImage(image);
                displayImage(image);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error opening image.");
            }
        }
    }

    private void showOriginalImage() {
        if (image != null) {
            processedImage = copyImage(image);
            displayImage(image);
        }
    }

    private void addConstant() {
        if (processedImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.");
            return;
        }
        String input = JOptionPane.showInputDialog("Enter a constant value:");
        try {
            int constant = Integer.parseInt(input);
            for (int x = 0; x < processedImage.getWidth(); x++) {
                for (int y = 0; y < processedImage.getHeight(); y++) {
                    int rgb = processedImage.getRGB(x, y);
                    Color color = new Color(rgb);
                    int red = Math.min(255, Math.max(0, color.getRed() + constant));
                    int green = Math.min(255, Math.max(0, color.getGreen() + constant));
                    int blue = Math.min(255, Math.max(0, color.getBlue() + constant));
                    Color newColor = new Color(red, green, blue);
                    processedImage.setRGB(x, y, newColor.getRGB());
                }
            }
            displayImage(processedImage);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input.");
        }
    }

    private void multiplyConstant() {
        if (processedImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.");
            return;
        }
        String input = JOptionPane.showInputDialog("Enter a constant multiplier:");
        try {
            float constant = Float.parseFloat(input);
            for (int x = 0; x < processedImage.getWidth(); x++) {
                for (int y = 0; y < processedImage.getHeight(); y++) {
                    int rgb = processedImage.getRGB(x, y);
                    Color color = new Color(rgb);
                    int red = Math.min(255, (int) (color.getRed() * constant));
                    int green = Math.min(255, (int) (color.getGreen() * constant));
                    int blue = Math.min(255, (int) (color.getBlue() * constant));
                    Color newColor = new Color(red, green, blue);
                    processedImage.setRGB(x, y, newColor.getRGB());
                }
            }
            displayImage(processedImage);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input.");
        }
    }

    private void powerOperation() {
        if (processedImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.");
            return;
        }
        String input = JOptionPane.showInputDialog("Enter an exponent:");
        try {
            float exponent = Float.parseFloat(input);
            for (int x = 0; x < processedImage.getWidth(); x++) {
                for (int y = 0; y < processedImage.getHeight(); y++) {
                    int rgb = processedImage.getRGB(x, y);
                    Color color = new Color(rgb);
                    int red = Math.min(255, (int) (Math.pow(color.getRed() / 255.0, exponent) * 255));
                    int green = Math.min(255, (int) (Math.pow(color.getGreen() / 255.0, exponent) * 255));
                    int blue = Math.min(255, (int) (Math.pow(color.getBlue() / 255.0, exponent) * 255));
                    Color newColor = new Color(red, green, blue);
                    processedImage.setRGB(x, y, newColor.getRGB());
                }
            }
            displayImage(processedImage);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input.");
        }
    }

    private void logarithmicTransformation() {
        int width = processedImage.getWidth();
        int height = processedImage.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = processedImage.getRGB(x, y);
                int r = (int)(255 * (Math.log(1 + ((rgb >> 16) & 0xff)) / Math.log(256)));
                int g = (int)(255 * (Math.log(1 + ((rgb >> 8) & 0xff)) / Math.log(256)));
                int b = (int)(255 * (Math.log(1 + (rgb & 0xff)) / Math.log(256)));

                int newRgb = (r << 16) | (g << 8) | b;
                newImage.setRGB(x, y, newRgb);
            }
        }
        displayImage(newImage);
    }

    private void negativeOperation() {
        if (processedImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.");
            return;
        }
        for (int x = 0; x < processedImage.getWidth(); x++) {
            for (int y = 0; y < processedImage.getHeight(); y++) {
                int rgb = processedImage.getRGB(x, y);
                Color color = new Color(rgb);
                int red = 255 - color.getRed();
                int green = 255 - color.getGreen();
                int blue = 255 - color.getBlue();
                Color newColor = new Color(red, green, blue);
                processedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        displayImage(processedImage);
    }

    private void equalizeHistogram() {
        if (processedImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.");
            return;
        }

        int[] histogram = new int[256];
        Arrays.fill(histogram, 0);

        for (int x = 0; x < processedImage.getWidth(); x++) {
            for (int y = 0; y < processedImage.getHeight(); y++) {
                int rgb = processedImage.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                histogram[gray]++;
            }
        }

        int[] cdf = new int[256];
        cdf[0] = histogram[0];
        for (int i = 1; i < 256; i++) {
            cdf[i] = cdf[i - 1] + histogram[i];
        }

        float scale = 255.0f / (processedImage.getWidth() * processedImage.getHeight());
        int[] equalized = new int[256];
        for (int i = 0; i < 256; i++) {
            equalized[i] = Math.min(255, Math.round(cdf[i] * scale));
        }

        for (int x = 0; x < processedImage.getWidth(); x++) {
            for (int y = 0; y < processedImage.getHeight(); y++) {
                int rgb = processedImage.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                int newGray = equalized[gray];
                Color newColor = new Color(newGray, newGray, newGray);
                processedImage.setRGB(x, y, newColor.getRGB());
            }
        }

        displayImage(processedImage);

        showHistogram();
    }

    private void equalizeRGBHistogram() {
        if (processedImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.");
            return;
        }

        int[][] histograms = new int[3][256];
        Arrays.fill(histograms[0], 0);
        Arrays.fill(histograms[1], 0);
        Arrays.fill(histograms[2], 0);

        for (int x = 0; x < processedImage.getWidth(); x++) {
            for (int y = 0; y < processedImage.getHeight(); y++) {
                int rgb = processedImage.getRGB(x, y);
                Color color = new Color(rgb);
                histograms[0][color.getRed()]++;
                histograms[1][color.getGreen()]++;
                histograms[2][color.getBlue()]++;
            }
        }

        int[][] cdf = new int[3][256];
        for (int i = 0; i < 3; i++) {
            cdf[i][0] = histograms[i][0];
            for (int j = 1; j < 256; j++) {
                cdf[i][j] = cdf[i][j - 1] + histograms[i][j];
            }
        }

        int totalPixels = processedImage.getWidth() * processedImage.getHeight();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 256; j++) {
                cdf[i][j] = Math.min(255, Math.round((cdf[i][j] / (float) totalPixels) * 255));
            }
        }

        for (int x = 0; x < processedImage.getWidth(); x++) {
            for (int y = 0; y < processedImage.getHeight(); y++) {
                int rgb = processedImage.getRGB(x, y);
                Color color = new Color(rgb);
                int newR = cdf[0][color.getRed()];
                int newG = cdf[1][color.getGreen()];
                int newB = cdf[2][color.getBlue()];
                Color newColor = new Color(newR, newG, newB);
                processedImage.setRGB(x, y, newColor.getRGB());
            }
        }

        displayImage(processedImage);
        showHistogram();
    }


    private void equalizeHSVHistogram() {
        if (processedImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.");
            return;
        }

        int[] histogram = new int[256];
        Arrays.fill(histogram, 0);

        for (int x = 0; x < processedImage.getWidth(); x++) {
            for (int y = 0; y < processedImage.getHeight(); y++) {
                int rgb = processedImage.getRGB(x, y);
                Color color = new Color(rgb);
                float[] hsv = rgbToHsv(color.getRed(), color.getGreen(), color.getBlue());
                int value = (int) (hsv[2] * 255);
                histogram[value]++;
            }
        }

        int[] cdf = new int[256];
        cdf[0] = histogram[0];
        for (int i = 1; i < 256; i++) {
            cdf[i] = cdf[i - 1] + histogram[i];
        }

        int totalPixels = processedImage.getWidth() * processedImage.getHeight();
        for (int i = 0; i < 256; i++) {
            cdf[i] = Math.min(255, Math.round((cdf[i] / (float) totalPixels) * 255));
        }

        for (int x = 0; x < processedImage.getWidth(); x++) {
            for (int y = 0; y < processedImage.getHeight(); y++) {
                int rgb = processedImage.getRGB(x, y);
                Color color = new Color(rgb);
                float[] hsv = rgbToHsv(color.getRed(), color.getGreen(), color.getBlue());
                int newValue = cdf[(int) (hsv[2] * 255)];
                hsv[2] = newValue / 255.0f; // Update the Value

                Color newColor = hsvToRgb(hsv[0], hsv[1], hsv[2]);
                processedImage.setRGB(x, y, newColor.getRGB());
            }
        }

        displayImage(processedImage);
        showHistogram();
    }


    private float[] rgbToHsv(int r, int g, int b) {
        float[] hsv = new float[3];
        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));
        hsv[2] = max / 255f; // Value

        if (max == min) {
            hsv[0] = 0; // Hue
            hsv[1] = 0; // Saturation
        } else {
            float delta = max - min;
            hsv[1] = (max == 0) ? 0 : (delta / max); // Saturation

            if (max == r) {
                hsv[0] = (g - b) / delta + (g < b ? 6 : 0);
            } else if (max == g) {
                hsv[0] = (b - r) / delta + 2;
            } else {
                hsv[0] = (r - g) / delta + 4;
            }
            hsv[0] /= 6;
        }
        return hsv;
    }

    private Color hsvToRgb(float h, float s, float v) {
        int r, g, b;

        if (s == 0) {
            r = g = b = (int) (v * 255);
        } else {
            float i = h * 6;
            float f = i - (int) i;
            int p = (int) (v * (1 - s) * 255);
            int q = (int) (v * (1 - f * s) * 255);
            int t = (int) (v * (1 - (1 - f) * s) * 255);
            i = (int) i;

            switch ((int) i % 6) {
                case 0 -> {
                    r = (int) (v * 255);
                    g = t;
                    b = p;
                }
                case 1 -> {
                    r = q;
                    g = (int) (v * 255);
                    b = p;
                }
                case 2 -> {
                    r = p;
                    g = (int) (v * 255);
                    b = t;
                }
                case 3 -> {
                    r = p;
                    g = q;
                    b = (int) (v * 255);
                }
                case 4 -> {
                    r = t;
                    g = p;
                    b = (int) (v * 255);
                }
                case 5 -> {
                    r = (int) (v * 255);
                    g = p;
                    b = q;
                }
                default -> throw new IllegalStateException("Unexpected value: " + i);
            }
        }
        return new Color(r, g, b);
    }

    private void linearContrast() {
        if (processedImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.");
            return;
        }

        int minR = 255, maxR = 0;
        int minG = 255, maxG = 0;
        int minB = 255, maxB = 0;

        for (int x = 0; x < processedImage.getWidth(); x++) {
            for (int y = 0; y < processedImage.getHeight(); y++) {
                int rgb = processedImage.getRGB(x, y);
                Color color = new Color(rgb);
                minR = Math.min(minR, color.getRed());
                maxR = Math.max(maxR, color.getRed());
                minG = Math.min(minG, color.getGreen());
                maxG = Math.max(maxG, color.getGreen());
                minB = Math.min(minB, color.getBlue());
                maxB = Math.max(maxB, color.getBlue());
            }
        }

        for (int x = 0; x < processedImage.getWidth(); x++) {
            for (int y = 0; y < processedImage.getHeight(); y++) {
                int rgb = processedImage.getRGB(x, y);
                Color color = new Color(rgb);

                int newR = (color.getRed() - minR) * 255 / Math.max(1, (maxR - minR));
                int newG = (color.getGreen() - minG) * 255 / Math.max(1, (maxG - minG));
                int newB = (color.getBlue() - minB) * 255 / Math.max(1, (maxB - minB));

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                Color newColor = new Color(newR, newG, newB);
                processedImage.setRGB(x, y, newColor.getRGB());
            }
        }

        displayImage(processedImage);
    }


    private void showHistogram() {
        if (processedImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.");
            return;
        }

        int[] histogram = new int[256];
        Arrays.fill(histogram, 0);

        for (int x = 0; x < processedImage.getWidth(); x++) {
            for (int y = 0; y < processedImage.getHeight(); y++) {
                int rgb = processedImage.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                histogram[gray]++;
            }
        }

        int max = Arrays.stream(histogram).max().orElse(0);

        int histogramWidth = 512;  // Width of histogram image
        int histogramHeight = 200;  // Height of histogram image
        BufferedImage histogramImage = new BufferedImage(histogramWidth, histogramHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = histogramImage.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, histogramWidth, histogramHeight);

        g.setColor(Color.BLACK);
        for (int i = 0; i < histogram.length; i++) {
            int height = (int) ((double) histogram[i] / max * histogramHeight); // Scale to fit the height
            g.fillRect(i * 2, histogramHeight - height, 2, height); // Each bar is 2 pixels wide
        }

        g.dispose();
        JFrame histogramFrame = new JFrame("Histogram");
        histogramFrame.setSize(600, 300);
        histogramFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the histogram frame
        histogramFrame.getContentPane().add(new JLabel(new ImageIcon(histogramImage)));
        histogramFrame.setVisible(true);
    }


    private void displayImage(BufferedImage img) {
        ImageIcon icon = new ImageIcon(img);
        imageLabel.setIcon(icon);
        frame.pack();
    }

    private BufferedImage copyImage(BufferedImage original) {
        BufferedImage copy = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        Graphics g = copy.getGraphics();
        g.drawImage(original, 0, 0, null);
        g.dispose();
        return copy;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageProcessor::new);
    }
}
