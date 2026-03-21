import java.util.*;

public class ShortestQueryInterval {

    /**
     * 找出每个文档中查询词序列按顺序出现的最短区间长度
     * @param documents 文档集合（每个文档是词序列）
     * @param query 查询词序列
     * @return 每个文档对应的最短区间长度（无则返回-1）
     */
    public List<Integer> findShortestIntervals(List<List<String>> documents, List<String> query) {
        List<Integer> results = new ArrayList<>();
        int m = query.size();
        if (m == 0) {
            // 特殊情况：查询为空，区间长度为0
            documents.forEach(doc -> results.add(0));
            return results;
        }

        // 预处理查询词：word -> 该词在query中出现的所有索引（按k递增）
        Map<String, List<Integer>> wordToKList = new HashMap<>();
        for (int k = 0; k < m; k++) {
            String word = query.get(k);
            wordToKList.computeIfAbsent(word, key -> new ArrayList<>()).add(k);
        }

        // 遍历每个文档
        for (List<String> doc : documents) {
            int docLen = doc.size();
            if (docLen == 0) {
                results.add(-1);
                continue;
            }

            // 初始化匹配状态：isMatched[k]表示第k个查询词是否匹配到有效组合
            boolean[] isMatched = new boolean[m];
            int[] currentPos = new int[m]; // 第k个查询词的位置
            int[] startPos = new int[m];   // 对应第0个查询词的位置
            int minLen = Integer.MAX_VALUE;

            // 遍历文档中的每个词和位置
            for (int pos = 0; pos < docLen; pos++) {
                String word = doc.get(pos);
                // 若当前词不是查询词，跳过
                if (!wordToKList.containsKey(word)) {
                    continue;
                }

                // 取出当前词对应的所有查询索引，逆序遍历（避免同一位置重复匹配）
                List<Integer> kList = wordToKList.get(word);
                for (int i = kList.size() - 1; i >= 0; i--) {
                    int k = kList.get(i);

                    if (k == 0) {
                        // 匹配到第0个查询词，直接更新状态（保留最新位置，后续可能更优）
                        isMatched[0] = true;
                        currentPos[0] = pos;
                        startPos[0] = pos;
                    } else {
                        // 匹配到第k个查询词，需依赖前一个查询词的匹配状态
                        if (isMatched[k - 1]) {
                            int newStart = startPos[k - 1];
                            int newCurrent = pos;
                            int newIntervalLen = newCurrent - newStart + 1;

                            // 若未匹配过或当前区间更短，更新状态
                            if (!isMatched[k] || newIntervalLen < (currentPos[k] - startPos[k] + 1)) {
                                isMatched[k] = true;
                                currentPos[k] = newCurrent;
                                startPos[k] = newStart;

                                // 若匹配到最后一个查询词，更新最短区间
                                if (k == m - 1) {
                                    minLen = Math.min(minLen, newIntervalLen);
                                }
                            }
                        }
                    }
                }
            }

            // 整理当前文档的结果
            results.add(minLen == Integer.MAX_VALUE ? -1 : minLen);
        }

        return results;
    }

    // 测试用例
    public static void main(String[] args) {
        ShortestQueryInterval solver = new ShortestQueryInterval();

        // 测试用例1：基础场景
        List<List<String>> documents1 = new ArrayList<>();
        documents1.add(Arrays.asList("a", "b", "c", "d", "e"));    // 预期4（a0→b1→d3）
        documents1.add(Arrays.asList("a", "c", "b", "d", "e"));    // 预期4（a0→b2→d3）
        documents1.add(Arrays.asList("x", "y", "z"));              // 预期-1
        List<String> query1 = Arrays.asList("a", "b", "d");
        System.out.println("测试用例1结果：" + solver.findShortestIntervals(documents1, query1));

        // 测试用例2：查询词重复
        List<List<String>> documents2 = new ArrayList<>();
        documents2.add(Arrays.asList("q0", "a", "q0", "b", "q1")); // 预期5（q0_0→q0_2→q1_4）
        List<String> query2 = Arrays.asList("q0", "q0", "q1");
        System.out.println("测试用例2结果：" + solver.findShortestIntervals(documents2, query2));

        // 测试用例3：查询词连续
        List<List<String>> documents3 = new ArrayList<>();
        documents3.add(Arrays.asList("a", "a", "b"));               // 预期3（a0→a1→b2）
        List<String> query3 = Arrays.asList("a", "a", "b");
        System.out.println("测试用例3结果：" + solver.findShortestIntervals(documents3, query3));

        // 测试用例4：查询词不连续但区间最短
        List<List<String>> documents4 = new ArrayList<>();
        documents4.add(Arrays.asList("a", "b", "a", "b"));          // 预期2（a0→b1 或 a2→b3）
        List<String> query4 = Arrays.asList("a", "b");
        System.out.println("测试用例4结果：" + solver.findShortestIntervals(documents4, query4));

        // 测试用例5：无法匹配所有查询词
        List<List<String>> documents5 = new ArrayList<>();
        documents5.add(Arrays.asList("a"));                         // 预期-1（查询需两个a）
        List<String> query5 = Arrays.asList("a", "a");
        System.out.println("测试用例5结果：" + solver.findShortestIntervals(documents5, query5));
    }
}