package com.apocalypse.example.leetcode.difficulty;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/19
 */
public class Solution23 {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(4);
        node1.next = node2;
        ListNode node3 = new ListNode(5);
        node2.next = node3;

        ListNode node4 = new ListNode(1);
        ListNode node5 = new ListNode(3);
        node4.next = node5;
        ListNode node6 = new ListNode(4);
        node5.next = node6;

        ListNode node7 = new ListNode(2);
        ListNode node8 = new ListNode(6);
        node7.next = node8;

        ListNode[] lists = new ListNode[]{node1, node4, node7};

        ListNode listNode = mergeKLists(lists);

        System.out.println(listNode);
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        TreeMap<Integer, Integer> bucket = new TreeMap<>();
        for (int i = 0; i < lists.length; i++) {
            ListNode listNode = lists[i];
            while (listNode != null) {
                int val = listNode.val;
                if (bucket.containsKey(val)) {
                    bucket.put(val, bucket.get(val) + 1);
                } else {
                    bucket.put(val, 1);
                }
                listNode = listNode.next;
            }
        }
        ListNode first = new ListNode(0);
        ListNode currNode = first;
        for (Map.Entry<Integer, Integer> entry : bucket.entrySet()) {
            Integer val = entry.getKey();
            Integer count = bucket.get(val);
            int i = 0;
            while (i++ < count) {
                ListNode tempNode = new ListNode(val);
                currNode.next = tempNode;
                currNode = tempNode;
            }
        }
        return first.next;
    }
}

class ListNode {
    int val;
    ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(val);
        ListNode currentNode = this.next;
        while (currentNode != null) {
            sb.append(" -> ");
            sb.append(currentNode.val);
            currentNode = currentNode.next;
        }
        return sb.toString();
    }
}
