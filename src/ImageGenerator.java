import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.awt.Color;
import java.util.Random;

public class ImageGenerator {
    private int[] mPixels;
    private Random mRand;
    private int mWidth, mHeight;
    private int startingColor = 0xFF500050;

    public ImageGenerator(BufferedImage buf) {
        mPixels = ((DataBufferInt) buf.getRaster().getDataBuffer()).getData();
        mWidth = buf.getWidth();
        mHeight = buf.getHeight();
        mRand = new Random();
    }

    public void generate() {
        for (int i = 0; i < mWidth; i++) {
            mPixels[i] = startingColor;
            mPixels[i + (mHeight - 1)*mWidth] = startingColor;
            if (i == 0 || i == mWidth - 1) {
                for (int j = 0; j < mHeight; j++) {
                    mPixels[i + j*mWidth] = startingColor;
                }
            }
        }
        mPixels[0] = startingColor;
        for (int i = 1; i < mWidth; i++) {
            for (int j = 1; j < mHeight; j++) {
                mPixels[i + j * mWidth] = generatePixel(i, j);
            }
        }
    }

    private int generatePixel(int x, int y) {
        int referencePixel = mPixels[(x - 1) + y * mWidth];
        int referencePixel2 = mPixels[x + (y - 1) * mWidth];
        int r = getRedChannel(referencePixel), g = getGreenChannel(referencePixel), b = getBlueChannel(referencePixel),
                a = getAlphaChannel(referencePixel);
        int r2 = getRedChannel(referencePixel2), g2 = getGreenChannel(referencePixel2), b2 = getBlueChannel(referencePixel2),
                a2 = getAlphaChannel(referencePixel2);

        int newR = generateChannel((r + r2) / 2, 20, 10, 200);
        int newG = generateChannel((g + g2) / 2, 5, 0, 20);
        int newB = generateChannel((b + b2) / 2, 20, 10, 200);
        //byte newA = generateChannel(a, 10);

        return assembleColor((byte) a, newR, newG, newB);
    }

    private int assembleColor(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    private int getAlphaChannel(int referencePixel) {
        return (referencePixel & 0xFF000000) >> 24;
    }

    private int getBlueChannel(int referencePixel) {
        return (referencePixel & 0xFF);
    }

    private int getGreenChannel(int referencePixel) {
        return (referencePixel & 0xFF00) >> 8;
    }

    private int getRedChannel(int referencePixel) {
        return (referencePixel & 0xFF0000) >> 16;
    }

    private int generateChannel(int channelValue, int halfRange, int lo, int hi) {
        int min = Math.max(channelValue - halfRange, lo);
        int max = Math.min(channelValue + halfRange, hi);

        int val = (min + mRand.nextInt(max - min));
        int val2 = (min + mRand.nextInt(max - min));
        int val3 = (min + mRand.nextInt(max - min));


        return val;
    }
}
