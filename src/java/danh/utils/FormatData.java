/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author apple
 */
public class FormatData {

    public static String formatPrice(String price) {

        return price.replaceAll("\\D", "");
    }

    public static String fixTagName(String content) {
        //lists open tags
        List<String> stack = new ArrayList<>();
        //lists position open tags
        List<Integer> li = new ArrayList<>();
        //lists extra tag
        List<String> addTag = new ArrayList<>();

        int sz = content.length();
        int mark[] = new int[sz];
        Arrays.fill(mark, -1);

        int i = 0;
        while (i < content.length()) {
            if (content.charAt(i) == '<') {
                int j = i + 1;

                String tagTmp = "" + content.charAt(i);

                while (j < content.length() && content.charAt(j) != '>') {
                    tagTmp = tagTmp + content.charAt(j);
                    j++;
                }

                int curEnd = j;
                tagTmp = tagTmp + '>';
                i = j + 1;
                String tag = getTagName(tagTmp);

                if (tag != null) {
                    if (tag.charAt(0) != '/') {
                        stack.add(tag);
                        li.add(curEnd);
                    } else {
                        while (stack.size() > 0) {
                            if (stack.get(stack.size() - 1).equals(tag.substring(1))) {
                                stack.remove(stack.size() - 1);
                                li.remove(li.size() - 1);
                                break;
                            } else {
                                //need to
                                addTag.add(stack.get(stack.size() - 1));
                                mark[li.get(li.size() - 1)] = addTag.size() - 1;
                                //remove
                                stack.remove(stack.size() - 1);
                                li.remove(li.size() - 1);
                            }
                        }
                    }
                }
            } else {
                i++;
            }
        }
        while (stack.size() > 0) {
            addTag.add(stack.get(stack.size() - 1));
            mark[li.get(li.size() - 1)] = addTag.size() - 1;
            stack.remove(stack.size() - 1);
            li.remove(li.size() - 1);
        }
        String newContent = "";

        for (int j = 0; j < content.length(); j++) {
            newContent = newContent + content.charAt(j);
            if (mark[j] != -1) {
                newContent = newContent + "</" + addTag.get(mark[j]) + ">";
            }
        }

        return "<document>" + newContent + "</document>";
    }

    private static String getTagName(String content) {
        if (content.charAt(content.length() - 2) == '/') {
            return null;
        }
        String res = "";
        int i = 1;
        if (content.charAt(i) == '/') {
            res = res + '/';
            i++;
        }

        while (isChar(content.charAt(i))) {
            res = res + content.charAt(i);
            i++;
        }

        if (res.length() == 0 || (res.length() == 1 && res.charAt(0) == '/')) {
            return null;
        }
        return res;
    }

    private static boolean isChar(char x) {
        return (x >= 'a' && x <= 'z');
    }

    public static List<Integer> getFrameSize(int minSize, int maxSize) {
        Random ran = new Random();
        List<Integer> list = new ArrayList();
        for (int i = 0; i < 3; i++) {
            int x = (int) (Math.random() * ((maxSize - minSize) + 1)) + minSize;
            list.add(x);
        }
        return list;
    }
}
