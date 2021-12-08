package com.lxj.shardingjdbc.log;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Xingjing.Li
 * @since 2021/11/30
 */
public class LogRecordContext  {
    private static final InheritableThreadLocal<Deque<Map<String, Object>>> THREAD_LOCAL = new InheritableThreadLocal<>();

    public static void putVariable(String key, Object val){
        Deque<Map<String, Object>> stack = THREAD_LOCAL.get();
//        Map<String, Object> map = THREAD_LOCAL.get();
        if (stack == null || stack.size() == 0){
            stack = new LinkedList<>();
            HashMap<String, Object> valMap = new HashMap<>();
            valMap.put(key, val);
            stack.addFirst(valMap);
            THREAD_LOCAL.set(stack);
        }else {
            HashMap<String, Object> valMap = new HashMap<>();
            valMap.put(key, val);
            stack.addFirst(valMap);
        }
    }

    public static Map<String, Object> getVariables(){
        return THREAD_LOCAL.get().getFirst();
    }

    public static void clear(){
        Deque<Map<String, Object>> stack = THREAD_LOCAL.get();
        stack.removeFirst();
        if (stack.size() == 0) {
            THREAD_LOCAL.remove();
        }
    }
}
