package com.apocalypse.example.leetcode;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/14
 */
public class Solution21 {
    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        node1.next = node2;
        ListNode node3 = new ListNode(4);
        node2.next = node3;

        ListNode node4 = new ListNode(1);
        ListNode node5 = new ListNode(3);
        node4.next = node5;
        ListNode node6 = new ListNode(4);
        node5.next = node6;

        ListNode listNode = mergeTwoLists(node1, node4);
        System.out.println(listNode);
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head1 = l1;
        ListNode head2 = l2;

        ListNode firstNode = new ListNode(0);
        ListNode curr = firstNode;
        while (head1 != null || head2 != null) {
            if (head1 != null && head2 != null) {
                if (head1.val <= head2.val) {
                    ListNode next = new ListNode(head1.val);
                    curr.next = next;
                    curr = next;
                    head1 = head1.next;
                } else {
                    ListNode next = new ListNode(head2.val);
                    curr.next = next;
                    curr = next;
                    head2 = head2.next;
                }
            } else {
                if (head1 == null) {
                    ListNode next = new ListNode(head2.val);
                    curr.next = next;
                    curr = next;
                    head2 = head2.next;
                } else {
                    ListNode next = new ListNode(head1.val);
                    curr.next = next;
                    curr = next;
                    head1 = head1.next;
                }
            }
        }
        return firstNode.next;
    }

}
