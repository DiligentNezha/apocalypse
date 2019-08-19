package com.apocalypse.example.leetcode.medium;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/13
 */
public class Solution2 {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(4);
        node1.next = node2;
        ListNode node3 = new ListNode(5);
        node2.next = node3;

        ListNode node4 = new ListNode(5);
        ListNode node5 = new ListNode(6);
        node4.next = node5;
        ListNode node6 = new ListNode(5);
        node5.next = node6;

        System.out.println(node1);
        System.out.println(node4);

        System.out.println(addTwoNumbers(node1, node4));
        System.out.println(addTwoNumbers(new ListNode(0), new ListNode(0)));

        ListNode node7 = new ListNode(1);
        ListNode node8 = new ListNode(8);
        node7.next = node8;

        ListNode node9 = new ListNode(0);
        System.out.println(addTwoNumbers(node7, node9));

        System.out.println(addTwoNumbers(new ListNode(5), new ListNode(5)));
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int step = 0;
        boolean isFirst = false;
        ListNode firstNode = null;
        ListNode currentNode = null;
        ListNode preNode = currentNode;
        ListNode currentNode1 = l1;
        ListNode currentNode2 = l2;

        while (currentNode1 != null || currentNode2 != null) {
            int currentNode1Val = currentNode1 == null ? 0 : currentNode1.val;
            int currentNode2Val = currentNode2 == null ? 0 : currentNode2.val;

            int currentValue = currentNode1Val + currentNode2Val + step;
            currentNode = new ListNode(currentValue % 10);
            if (!isFirst) {
                isFirst = true;
                firstNode = currentNode;
                preNode = currentNode;
            }
            preNode.next = currentNode;
            preNode = currentNode;
            step = currentValue / 10;
            currentNode1 = currentNode1 == null ? null : currentNode1.next;
            currentNode2 = currentNode2 == null ? null : currentNode2.next;
        }
        if (firstNode.next == firstNode) {
            firstNode.next = null;
        }
        if (step > 0) {
            currentNode.next = new ListNode(step);
        }
        return firstNode;
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
