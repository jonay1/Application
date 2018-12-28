package com.wolf.app.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 图片验证码
 * 
 * @author jonay
 *
 */
public class Captcha {
	private int width = 90;// 定义图片的width
	private int height = 20;// 定义图片的height
	private int xx = 15;
	private int fontHeight = 18;
	private int codeY = 16;

	private static Captcha instance = null;

	private Captcha() {
	}

	public static Captcha getInstance() {
		if (instance == null) {
			instance = new Captcha();
		}
		return instance;
	}

	public void output(String randomCode, OutputStream outStream) {
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// Graphics2D gd = buffImg.createGraphics();
		// Graphics2D gd = (Graphics2D) buffImg.getGraphics();
		Graphics gd = buffImg.getGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);

		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
		// 设置字体。
		gd.setFont(font);

		// 画边框。
		// gd.setColor(Color.BLACK);
		// gd.drawRect(0, 0, width - 1, height - 1);

		// 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
		gd.setColor(Color.BLACK);
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gd.drawLine(x, y, x + xl, y + yl);
		}

		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		// String randomCode = new StringBuffer();
		char[] chars = randomCode.toCharArray();
		int red = 0, green = 0, blue = 0;
		for (int i = 0; i < chars.length; i++) {
			// 得到随机产生的验证码数字。
			String code = String.valueOf(chars[i]);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);

			// 用随机产生的颜色将验证码绘制到图像中。
			gd.setColor(new Color(red, green, blue));
			gd.drawString(code, (i + 1) * xx, codeY);
		}
		try {
			ImageIO.write(buffImg, "jpeg", outStream);
		} catch (IOException e) {
		} finally {
			
		}
	}
}
