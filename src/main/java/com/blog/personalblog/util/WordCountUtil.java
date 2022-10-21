package com.blog.personalblog.util;

/**
 * @author: SuperMan
 * @create: 2022-10-14
 **/
public class WordCountUtil {

    /**
     * 统计字数, 空格不统计
     * @param string
     * @return
     */
    public static long wordCount(String string) {
        if (string == null) {
            return 0;
        }
        long letterCount = 0L;
        long numCount = 0L;
        long otherCount = 0L;

        String str = string.trim();
        char[] chr = str.toCharArray();
        for(int i = 0; i < chr.length;i++){
            if(Character.isLetter(chr[i])){
                letterCount++;
            } else if(Character.isDigit(chr[i])){
                numCount ++;
            } else{
                otherCount ++;
            }
        }
        return letterCount + numCount + otherCount;
    }

}
