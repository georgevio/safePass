package com.java.password.randoms;

import java.security.SecureRandom;

/**
 * Just return SAFE random strings
 * with letters and numbers if you want.
 * Be careful: it returns ONLY small letters
 * @author john
 *
 */
public class RandomString {

  private static final char[] symbols;

  static {
    StringBuilder tmp = new StringBuilder();
   // for (char ch = '0'; ch <= '9'; ++ch)
   //   tmp.append(ch);
    for (char ch = 'a'; ch <= 'z'; ++ch)
      tmp.append(ch);
    symbols = tmp.toString().toCharArray();
  }   


  private final SecureRandom random = new SecureRandom();
  
  private final char[] buf;

  public RandomString(int length) {
    if (length < 1)
      throw new IllegalArgumentException("length < 1: " + length);
    buf = new char[length];
  }

  public String nextString() {
    for (int idx = 0; idx < buf.length; ++idx) 
      buf[idx] = symbols[random.nextInt(symbols.length)];
    return new String(buf);
  }
}