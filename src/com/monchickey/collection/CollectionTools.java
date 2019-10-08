package com.monchickey.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * java集合框架工具类
 * @author monchickey
 *
 */

public class CollectionTools {
    
    /**
     * 通过for循环遍历List中的元素并打印
     * @param userlist
     */
    public void printForList(List<User> userlist) {
        int size = userlist.size();
        
        for(int i = 0;i < size;i++) {
            User u = userlist.get(i);
            System.out.println(u.getId() + " : " + u.getUsername());
        }
    }
    
    /**
     * 通过foreach遍历list中的元素并打印
     * @param userlist
     */
    public void printForeachList(List<User> userlist) {
        for(User u:userlist) {
            System.out.println(u.getId() + " : " + u.getUsername());
        }
    }
    
    /**
     * 通过迭代器遍历list中的元素并打印
     * @param userlist
     */
    public void printIteratorList(List<User> userlist) {
        Iterator<User> it = userlist.iterator();
        while(it.hasNext()) {
            User u = it.next();
            System.out.println(u.getId() + " : " + u.getUsername());
        }
    }
    
    
    /**
     * 通过foreach遍历set中的元素并打印
     * @param userset
     */
    public void printForeachSet(Set<User> userset) {
        for(User u:userset) {
            System.out.println(u.getId() + " : " + u.getUsername());
        }
    }
    
    /**
     * 通过iterator遍历set中的元素并打印
     * @param userset
     */
    public void printIteratorSet(Set<User> userset) {
        Iterator<User> it = userset.iterator();
        while(it.hasNext()) {
            User u = it.next();
            
            System.out.println(u.getId() + " : " + u.getUsername());
        }
    }
    
    
    /**
     * 通过keyset方法遍历map中的元素并打印
     * @param usermap
     */
    public void printKeySetMap(Map<String, User> usermap) {
        //获取键的set集合
        Set<String> keyset = usermap.keySet();
        //map大小
        usermap.size();
        for(String key:keyset) {
            User u = usermap.get(key);
            System.out.println(u.getId() + " : " + u.getUsername());
        }
    }
    
    /**
     * 通过entryset方法遍历map中的元素并打印
     * @param usermap
     */
    public void printEntrySetMap(Map<String, User> usermap) {
        //返回键值对的set集合
        Set<Entry<String, User>> entryset = usermap.entrySet();
        for(Entry<String, User> entry:entryset) {
            //获取键
            System.out.print("key：" + entry.getKey());
            //获取值
            User u = entry.getValue();
            System.out.println(" " + u.getId() + " : " + u.getUsername());
        }
    }
    
    
    public static void main(String[] args) {
        
        CollectionTools cs = new CollectionTools();
        
        List<User> userlist = new ArrayList<User>();
        User u = new User(1, "zzy1", "123456");
        User u1 = new User(2, "zzy2", "123456");
        User u2 = new User(3, "zzy3", "123456");
        userlist.add(u);
        userlist.add(u1);
        userlist.add(u2);
        cs.printIteratorList(userlist);
        
        Set<User> userset = new HashSet<User>();
        userset.add(u);
        userset.add(u1);
        userset.add(u2);
        cs.printIteratorSet(userset);
        
        //TreeMap默认升序 HashMap是乱序
        Map<String, User> usermap = new TreeMap<String, User>();
        usermap.put("zzy", u);
        usermap.put("zzy1", u1);
        usermap.put("zzy2", u2);
        cs.printEntrySetMap(usermap);
        
        //集合对泛型类的升序排序
        Collections.sort(userlist, new UserComparator());
        cs.printForeachList(userlist);
        
    }
    
}
