package src.CSE360App;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class EncryptionUtils {
	private static int IV_SIZE = 16;
	
	// Convert bytes to charArray
	public static char[] toCharArray(byte[] bytes) {		
        CharBuffer charBuffer = Charset.defaultCharset().decode(ByteBuffer.wrap(bytes));
        return Arrays.copyOf(charBuffer.array(), charBuffer.limit());
	}
	
	// Converts charArray to bytes
	static byte[] toByteArray(char[] chars) {		
        ByteBuffer byteBuffer = Charset.defaultCharset().encode(CharBuffer.wrap(chars));
        return Arrays.copyOf(byteBuffer.array(), byteBuffer.limit());
	}
	
	// Initialization vector is made
	public static byte[] getInitializationVector(char[] text) {
		char iv[] = new char[IV_SIZE];
		
		int textPointer = 0;
		int ivPointer = 0;
		while(ivPointer < IV_SIZE) {
			iv[ivPointer++] = text[textPointer++ % text.length];
		}
		
		return toByteArray(iv);
	}
	
	// Prints charArray
	public static void printCharArray(char[] chars) {
		for(char c : chars) {
			System.out.print(c);
		}
	}
}
