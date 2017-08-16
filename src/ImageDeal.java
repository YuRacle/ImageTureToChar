import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by YuRacle on 2017/8/4.
 */
public class ImageDeal {

    public static void main(String[] args) throws IOException {
        ImageDeal charImage = new ImageDeal();
        String path = "F:\\code\\work\\ImageTureToChar\\srcPic2\\BadApple\\";
        for (int i = 20; i < 25; i++) {
            if (i < 10) {
                charImage.turnToChar(path + "東方 Bad Apple 影絵_0000" + i + ".jpg", "F:\\code\\work\\ImageTureToChar\\srcPicOut1\\" + "badapple000" + i + ".jpg");
            }else if(i < 100) {
                charImage.turnToChar(path + "東方 Bad Apple 影絵_000" + i + ".jpg", "F:\\code\\work\\ImageTureToChar\\srcPicOut1\\" + "badapple00" + i + ".jpg");
            }else if(i < 1000) {
                charImage.turnToChar(path + "東方 Bad Apple 影絵_00" + i + ".jpg", "F:\\code\\work\\ImageTureToChar\\srcPicOut1\\" + "badapple0" + i + ".jpg");
            }else {
                charImage.turnToChar(path + "東方 Bad Apple 影絵_0" + i + ".jpg", "F:\\code\\work\\ImageTureToChar\\srcPicOut1\\" + "badapple" + i + ".jpg");
            }
            System.out.println("success"+i);
        }
    }

    public void turnToChar(String path, String outPath) throws IOException {
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        List<List> w = new ArrayList<List>();
        for (int i = 0; i < height; i++) {
            List<Integer> h= new ArrayList<Integer>();
            for (int j = 0; j < width; j++) {
                int rgb = image.getRGB(j,i);
                int red = (0xff0000&rgb)>>16;
                int green = (0xff0000&rgb)>>8;
                int blue = (0xff0000&rgb);
                int avg = (red+green+blue)/3;
                if (avg >212)
                    avg = 255;
                else avg = 0;
                rgb = (avg<<16) + (avg<<8) +avg;
                image.setRGB(j, i, rgb);
                h.add(avg);
            }
            w.add(h);
        }
        BufferedImage charImage = new BufferedImage(width*2, height*2, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = charImage.createGraphics();
        g2d.setColor(new Color(255,255,255));
        g2d.setFont(new Font("", 0, 20));
        g2d.fillRect(0,0,width*2,height*2);
        g2d.setColor(new Color(0x000000));
        int xPix = 1;
        int yPix = 1;
        for (int i = 0; i < w.size(); i+=1) {
            for (int j = 0; j < w.get(i).size(); j+=1) {
                int pix = (int)w.get(i).get(j);
                if (pix ==0) {
                    g2d.drawString("X",xPix*20,yPix*20);
                }else if (pix ==255) {
                    g2d.drawString(".",xPix*20,yPix*20);
                }
                xPix++;
            }
            xPix = 0;
            yPix++;
        }
        file = new File(outPath);
        if (!file.exists()) {
            file.createNewFile();
        }
        ImageIO.write(charImage,"jpg",file);
    }


}
