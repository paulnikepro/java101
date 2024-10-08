class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode previousNode = null;     // This will eventually become the new head
        ListNode currentNode = head;      // Start with the current head of the list

        while (currentNode != null) {
            ListNode nextNode = currentNode.next;  // Save the next node in the list
            currentNode.next = previousNode;       // Reverse the current node's pointer
            previousNode = currentNode;            // Move previousNode forward
            currentNode = nextNode;
        }

        return previousNode;
    }

    // Helper method
    public static void printList(ListNode head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }

        ListNode currentNode = head;
        while (currentNode != null) {
            System.out.print(currentNode.val + " ");
            currentNode = currentNode.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Solution solution = new Solution();

        // Example 1: head = [1,2,3,4]
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);

        System.out.print("Original list1: ");
        printList(head1);
        ListNode reversed1 = solution.reverseList(head1);
        System.out.print("Reversed list1: ");
        printList(reversed1);

        // Example 2: head = [3,2,1]
        ListNode head2 = new ListNode(3);
        head2.next = new ListNode(2);
        head2.next.next = new ListNode(1);

        System.out.print("Original list2: ");
        printList(head2);
        ListNode reversed2 = solution.reverseList(head2);
        System.out.print("Reversed list2: ");
        printList(reversed2);

        // Example 3: head = []
        ListNode head3 = null;
        System.out.print("Original list3: ");
        printList(head3);
        ListNode reversed3 = solution.reverseList(head3);
        System.out.print("Reversed list3: ");
        printList(reversed3);
    }
}