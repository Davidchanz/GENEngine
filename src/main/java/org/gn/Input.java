package org.gn;


import javax.swing.*;
import java.awt.event.*;

public class Input {
	private static boolean[] keys = new boolean[KeyEvent.KEY_LAST];
	private static boolean[] buttons = new boolean[2];
	private static double mouseX, mouseY;
	private static double scrollX, scrollY;
	private final KeyAdapter keyboard;
	private final MouseAdapter mouse;
	public static final int lb = 0;
	public static final int rb = 1;
	
	public Input() {
		keyboard = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				keys[e.getKeyCode()] = true;
			}

			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				keys[e.getKeyCode()] = false;
			}
		};
		
		mouse = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				mouseX = e.getXOnScreen();
				mouseY = e.getYOnScreen();
			}
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				super.mouseWheelMoved(e);
				scrollX += e.getScrollAmount();
				scrollY += e.getScrollAmount();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if(SwingUtilities.isLeftMouseButton(e))
					buttons[lb] = true;
				else if(SwingUtilities.isRightMouseButton(e))
					buttons[rb] = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				/*if(SwingUtilities.isLeftMouseButton(e))
					buttons[lb] = false;
				else if(SwingUtilities.isRightMouseButton(e))
					buttons[rb] = false;*/
			}
		};
	}
	
	public static boolean isKeyDown(int key) {
		return keys[key];
	}
	
	public static boolean isButtonDown(int button) {
		return buttons[button];
	}

	public static double getMouseX() {
		return mouseX;
	}

	public static double getMouseY() {
		return mouseY;
	}
	
	public static double getScrollX() {
		return scrollX;
	}

	public static double getScrollY() {
		return scrollY;
	}

	public KeyAdapter getKeyboardCallback() {
		return keyboard;
	}

	public MouseAdapter getMouseCallback() {
		return mouse;
	}
}