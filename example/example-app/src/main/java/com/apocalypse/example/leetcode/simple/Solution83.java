package com.apocalypse.example.leetcode.simple;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/19
 */
public class Solution83 {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(1);
        node1.next = node2;
        ListNode node3 = new ListNode(2);
        node2.next = node3;
        ListNode node4 = new ListNode(3);
        node3.next = node4;
        ListNode node5 = new ListNode(3);
        node4.next = node5;

        System.out.println(deleteDuplicates(node1));

    }

    public static ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode curr = head;
        while (curr != null) {
            if (pre.val == curr.val) {
                pre.next = curr.next;
                curr = curr.next;
            } else {
                pre = curr;
                curr = curr.next;
            }
        }
        return dummy.next;
    }
}
