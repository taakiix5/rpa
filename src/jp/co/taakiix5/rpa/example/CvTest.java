package jp.co.taakiix5.rpa.example;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class CvTest {
	public static void main(String args[]) throws AWTException, IOException{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Robot robot = new Robot();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		BufferedImage img = robot.createScreenCapture(new Rectangle(screenSize));
		// BufferedImageToMat
		Mat im = bi2Mat(img);
		/*
		Mat im = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		im.put(0, 0, data);
		*/

		// Mat im = Imgcodecs.imread("test.jpg");					// 入力画像の取得
		Mat tmp = Imgcodecs.imread("tmp.jpg");					// テンプレート画像の取得
		Mat result = new Mat();

		Imgproc.matchTemplate(im, tmp, result, Imgproc.TM_CCOEFF_NORMED);	//テンプレートマッチング

		/*
		Imgproc.threshold(result, result, 0.8, 1.0, Imgproc.THRESH_TOZERO); 	// 検出結果から相関係数がしきい値以下の部分を削除
		// テンプレート画像の部分を元画像に赤色の矩形で囲む（複数）
		for (int i=0;i<result.rows();i++) {
			for (int j=0;j<result.cols();j++) {
				if (result.get(i, j)[0] > 0) {
					Imgproc.rectangle(im, new Point(j, i), new Point(j + tmp.cols(), i + tmp.rows()), new Scalar(0, 0, 255));
				}
			}
		}
		*/


		// 最も類似する一か所
		MinMaxLocResult mmlr = Core.minMaxLoc(result);
		// 閾値以下は描画しない
		if (mmlr.maxVal > 0.9) {
			int x = (int)mmlr.maxLoc.x;
			int y = (int)mmlr.maxLoc.y;
			int w = tmp.cols();
			int h = tmp.rows();
			Imgproc.rectangle(im, mmlr.maxLoc, new Point(x + w, y + h), new Scalar(0, 255, 255));

			int mx = x + (w/2);
			int my = y + (h/2);

		    // マウスをマッチした画像の中心に移動させる
		    robot.mouseMove( mx, my );
		    robot.delay(500);
		    // マウスの左クリック
		    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		    // 左クリック状態の解除
		    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

		}


		Imgcodecs.imwrite("test2.jpg", im);					// 画像の出力
	}

	public static Mat bi2Mat(BufferedImage image) throws IOException {
        // byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        // byte[] data = image.getRaster().getDataBuffer().getData();
        byte[] data = getBytesFromImage( image, null );
        System.out.println("bufferedimage:" + data.length);
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }

	/**
	* イメージ→バイト列に変換
	* @param img イメージデータ
	* @param format フォーマット名
	* @return バイト列
	*/
	public static byte[] getBytesFromImage(BufferedImage img, String format) throws IOException {

		if (format == null) {
			format = "png";
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, format, baos);
		return baos.toByteArray();
	}
}