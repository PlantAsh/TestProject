package test;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 计算出字符串的最长对称字符串
 *
 * google输出最长字符串  goog
 * abcda输出最长字符串  aba/aca/ada
 * pop-upu输出最长字符串  pop/ppp/pup/upu
 * ahAs -ksikfsahtdAKGB,S,t输出最长字符串  asksksa/AsksksA/hsksksh/askiksa/hskiksh/AskiksA
 *
 * @author swu
 * @date 2020-08-10
 */
public class CheckSymmeryWord {

    public static void main(String[] args) {
        System.out.println("最终结果：" + check("ahAs -ksikfsahtdAKGB,S,t"));
        System.out.println("aaaa最终结果：" + check("aaaa"));
        System.out.println("google最终结果：" + check("google"));
        System.out.println("abcda最终结果：" + check("abcda"));
        System.out.println("pop-upu最终结果：" + check("pop-upu"));
    }

    /**
     * 开始计算
     *
     * @param word
     * @return
     */
    public static String check(String word) {
        if (StringUtils.isBlank(word)) {
            return "";
        }
        System.out.println("处理前：" + word);
        byte[] all = word.getBytes();
        StringBuilder back = new StringBuilder();
        for (byte b : all) {
            if ((b >= 65 && b <= 90) || (b >= 97 && b <= 122)) {
                back.append(new String(new byte[]{b}));
            }
        }
        if (StringUtils.isBlank(back)) {
            return "";
        }
        System.out.println("处理后：" + back);
        all = back.toString().getBytes();
        List<OneBean> list = new ArrayList<>();
        for (int i = 0; i < all.length; i++) {
            int second = i;
            for (int j = i + 1; j < all.length; j++) {
                if (all[i] == all[j]) {
                    second = j;
                    OneBean oneBean = new OneBean();
                    oneBean.setFirst(i);
                    oneBean.setSecond(second);
                    list.add(oneBean);
                }
            }
            OneBean bean = new OneBean();
            bean.setFirst(i);
            bean.setSecond(i);
            list.add(bean);
        }

        //计算对称
        Set<String> res = new HashSet<>();
        for (OneBean oneBean : list) {
            List<OneBean> backList = new ArrayList<>(list);
            backList.remove(oneBean);
            List<Integer> indexList = new LinkedList<>();
            indexList.add(oneBean.getFirst());
            indexList.add(oneBean.getSecond());
            symmery(indexList, backList, res, all);
        }

        List<String> finalList = new LinkedList<>();
        int length = 0;
        for (String s : res) {
            if (s.length() == length) {
                finalList.add(s);
            } else if (s.length() > length) {
                finalList.clear();
                finalList.add(s);
                length = s.length();
            }
        }

        return StringUtils.join(finalList, "/");
    }

    /**
     * 递归计算
     *
     * @param indexList
     * @param dataList
     * @param res
     */
    private static void symmery(List<Integer> indexList, List<OneBean> dataList, Set<String> res, byte[] all) {
        int index = (indexList.size() / 2);
        int first = indexList.get(index - 1);
        int second = indexList.get(index);
        if (first == second) {
            res.add(new String(new byte[]{all[first]}));
            return;
        }
        for (OneBean oneBean : dataList) {
            if ((oneBean.getSecond() - oneBean.getFirst()) >= (second - first)
                    || oneBean.getFirst() <= first || oneBean.getSecond() >= second) {
                splicing(indexList, res, all);
                continue;
            }
            if (oneBean.getFirst() == oneBean.getSecond()) {
                List<Integer> backIndex = new LinkedList<>(indexList);
                backIndex.add(index, oneBean.getFirst());
                splicing(backIndex, res, all);
                continue;
            }
            if (oneBean.getSecond() - oneBean.getFirst() == 1) {
                List<Integer> backIndex = new LinkedList<>(indexList);
                backIndex.add(index, oneBean.getFirst());
                backIndex.add(index + 1, oneBean.getSecond());
                splicing(backIndex, res, all);
                continue;
            }
            List<OneBean> backList = new ArrayList<>(dataList);
            backList.remove(oneBean);
            List<Integer> backIndex = new LinkedList<>(indexList);
            backIndex.add(index, oneBean.getFirst());
            backIndex.add(index + 1, oneBean.getSecond());
            symmery(backIndex, backList, res, all);
        }
    }

    /**
     * 拼接
     *
     * @param indexList
     * @param res
     * @param all
     */
    private static void splicing(List<Integer> indexList, Set<String> res, byte[] all) {
        StringBuilder back = new StringBuilder();
        for (Integer index : indexList) {
            back.append(new String(new byte[]{all[index]}));
        }
        res.add(back.toString());
    }

    static class OneBean implements Serializable {
        private static final long serialVersionUID = -1445365543850786033L;

        private int first;
        private int second;

        public int getFirst() {
            return first;
        }

        public void setFirst(int first) {
            this.first = first;
        }

        public int getSecond() {
            return second;
        }

        public void setSecond(int second) {
            this.second = second;
        }

    }

}
