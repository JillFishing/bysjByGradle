package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author TG.SI@188.COM
 * @version 1.0
 * 19th Oct. 2020
 * 为SQL的where子句建模。
 *
 * */
public class TxtHelper {
    public final static String QUOTE_MARK="\"";
    public static String quoteMarked(String content){
        return QUOTE_MARK + content + QUOTE_MARK;
    }

    public static StringBuilder truncEnding(StringBuilder sb, int number){
        return sb.delete(sb.length() - number, sb.length());
    }

    public static String truncEnding(String sb, int number){
       return TxtHelper.truncEnding(new StringBuilder(sb), number).toString();
    }

    public static String readTxt(String filename)throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(filename));
        String line = bf.readLine();
        StringBuilder sb = new StringBuilder();

        while(line!=null){
            sb.append(line);
            line = bf.readLine();

        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String a = "987654321";
        System.out.println(TxtHelper.truncEnding(a,3));
        System.out.println(TxtHelper.truncEnding(a,4));
        System.out.println(TxtHelper.truncEnding(a,5));
    }
}
