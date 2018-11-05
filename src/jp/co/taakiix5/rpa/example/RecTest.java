/* JNativeHook: Global keyboard and mouse hooking for Java.
 * Copyright (C) 2006-2016 Alexander Barker.  All Rights Received.
 * https://github.com/kwhat/jnativehook/
 *
 * JNativeHook is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JNativeHook is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jp.co.taakiix5.rpa.example;

import java.util.logging.Level;
import java.util.logging.Logger;

// Imports.
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;;
/**
 * A demonstration of how to use the JNativeHook library.
 *
 * @author	Alexander Barker (<a href="mailto:alex@1stleg.com">alex@1stleg.com</a>)
 * @version	2.0
 * @since	1.0
 *
 * @see GlobalScreenwewerwer
 * @see NativeKeyListener
 */
public class RecTest implements NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener{
	/** The Constant serialVersionUID. */
	private static NativeRecords record = new NativeRecords();

	/**
	 * Instantiates a new native hook demo.
	 */
	public static void main(String args[]) {

        Logger log = Logger.getLogger("");
        log.setLevel(Level.OFF);

		//フックされていなかったら
        if (!GlobalScreen.isNativeHookRegistered()) {
            try {
                //フックを登録
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException e) {
                //e.printStackTrace();
                System.exit(-1);
            }
        }

        //キー・リスナを登録
        RecTest demo = new RecTest();
        GlobalScreen.addNativeKeyListener(demo);
        GlobalScreen.addNativeMouseListener(demo);
        GlobalScreen.addNativeMouseMotionListener(demo);
        GlobalScreen.addNativeMouseWheelListener(demo);


	}

	/**
	 * @see org.jnativehook.keyboard.NativeKeyListener#nativeKeyPressed(org.jnativehook.keyboard.NativeKeyEvent)
	 */
	public void nativeKeyPressed(NativeKeyEvent e) {
		displayEventInfo(e);
	}

	/**
	 * @see org.jnativehook.keyboard.NativeKeyListener#nativeKeyReleased(org.jnativehook.keyboard.NativeKeyEvent)
	 */
	public void nativeKeyReleased(NativeKeyEvent e) {
		displayEventInfo(e);
	}

	/**
	 * @see org.jnativehook.keyboard.NativeKeyListener#nativeKeyTyped(org.jnativehook.keyboard.NativeKeyEvent)
	 */
	public void nativeKeyTyped(NativeKeyEvent e) {
		displayEventInfo(e);
	}

	/**
	 * @see org.jnativehook.mouse.NativeMouseListener#nativeMouseClicked(org.jnativehook.mouse.NativeMouseEvent)
	 */
	public void nativeMouseClicked(NativeMouseEvent e) {
		displayEventInfo(e);
	}

	/**
	 * @see org.jnativehook.mouse.NativeMouseListener#nativeMousePressed(org.jnativehook.mouse.NativeMouseEvent)
	 */
	public void nativeMousePressed(NativeMouseEvent e) {
		displayEventInfo(e);
	}

	/**
	 * @see org.jnativehook.mouse.NativeMouseListener#nativeMouseReleased(org.jnativehook.mouse.NativeMouseEvent)
	 */
	public void nativeMouseReleased(NativeMouseEvent e) {
		displayEventInfo(e);
	}

	/**
	 * @see org.jnativehook.mouse.NativeMouseMotionListener#nativeMouseMoved(org.jnativehook.mouse.NativeMouseEvent)
	 */
	public void nativeMouseMoved(NativeMouseEvent e) {
		displayEventInfo(e);
	}

	/**
	 * @see org.jnativehook.mouse.NativeMouseMotionListener#nativeMouseDragged(org.jnativehook.mouse.NativeMouseEvent)
	 */
	public void nativeMouseDragged(NativeMouseEvent e) {
		displayEventInfo(e);
	}

	/**
	 * @see org.jnativehook.mouse.NativeMouseWheelListener#nativeMouseWheelMoved(org.jnativehook.mouse.NativeMouseWheelEvent)
	 */
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		displayEventInfo(e);
	}

	/**
	 * Write information about the <code>NativeInputEvent</code> to the text
	 * window.
	 *
	 * @param e the native input event to display.
	 */
	private void displayEventInfo(final NativeInputEvent e) {
		//System.out.println(e.paramString());
		record.record(e);
	}

}

class NativeRecords {

	public NativeRecords() {

	}

	public void record(NativeInputEvent e) {
		if(e instanceof NativeMouseEvent) {
			mouseRecord((NativeMouseEvent)e);
		}

	}

	public void mouseRecord(NativeMouseEvent e) {

		switch(e.getID()) {
		case NativeMouseEvent.NATIVE_MOUSE_CLICKED:
			System.out.println("NATIVE_MOUSE_CLICKED");
			break;

		case NativeMouseEvent.NATIVE_MOUSE_PRESSED:
			System.out.println("NATIVE_MOUSE_PRESSED");
			break;

		case NativeMouseEvent.NATIVE_MOUSE_RELEASED:
			System.out.println("NATIVE_MOUSE_RELEASED");
			break;

		case NativeMouseEvent.NATIVE_MOUSE_MOVED:
			System.out.print("NATIVE_MOUSE_MOVED:");
			System.out.print(e.getX() + ",");
			System.out.print(e.getY());
			System.out.println("");
			break;

		case NativeMouseEvent.NATIVE_MOUSE_DRAGGED:
			System.out.println("NATIVE_MOUSE_DRAGGED");
			break;

		case NativeMouseEvent.NATIVE_MOUSE_WHEEL:
			System.out.println("NATIVE_MOUSE_WHEEL");
			break;

		default:
			System.out.println("unknown type");
			break;
		}

	}

}
