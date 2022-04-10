// --== CS400 File Header Information ==--
// Name: Christopher Yang
// Email: cyang397@wisc.edu
// Team: BG
// TA: Brianna Cochran
// Lecturer: Gary Dahl
// Notes to Grader: None
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestRedBlackTree {

	/**
	 *  test case 1: parent's sibling is black and is on opposite side from violating node
	 */
	@Test
	void testCaseOne() {
		try {
			RedBlackTree<String> rbt = new RedBlackTree<String>();
			String c = "C";
			String b = "B";
			String d = "D";
			rbt.insert(c);
			rbt.insert(b);
			rbt.insert(d);
			rbt.root.rightChild.isBlack = true;
			String a = "A";
			rbt.insert(a);
			String actual = rbt.root.toString();
			String expected = "[B, A, C, D]";
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			e.getMessage();
			e.printStackTrace();
		}
		try { 
			RedBlackTree<String> rbt = new RedBlackTree<String>();
			String c = "C";
			String b = "B";
			String a = "A";
			String d = "D";
			rbt.insert(b);
			rbt.insert(a);
			rbt.insert(c);
			rbt.root.leftChild.isBlack = true;
			rbt.insert(d);
			String actual = rbt.root.toString();
			String expected = "[C, B, D, A]";
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	/**
	 *  test case 2: parent's sibling is black and is on same side from violating node
	 */
	@Test
	void testCaseTwo() {
		try {
			RedBlackTree<String> rbt = new RedBlackTree<String>();
			//System.out.println(rbt.toString());
			String c = "C";
			String b = "B";
			String d = "D";
			String a = "A";
			rbt.insert(c);
			rbt.insert(a);
			rbt.insert(d);
			rbt.root.rightChild.isBlack = true;
			rbt.insert(b);
			String actual = rbt.root.toString();
			//System.out.println("Actual: " + rbt.root.toString());
			String expected = "[B, A, C, D]";
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			e.getMessage();
			e.printStackTrace();
		}
		try {
			RedBlackTree<String> rbt = new RedBlackTree<String>();
			String b = "B";
			String d = "D";
			String a = "A";
			String c = "C";
			rbt.insert(b);
			rbt.insert(a);
			rbt.insert(d);
			rbt.root.leftChild.isBlack = true;
			rbt.insert(c);
			String actual = rbt.root.toString();
			//System.out.println("Actual: " + rbt.root.toString());
			String expected = "[C, B, D, A]";
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	/**
	 *  test case 3: parent's sibling is red
	 */
	@Test
	void testCaseThree() {
		try {
			RedBlackTree<String> rbt = new RedBlackTree<String>();
			//System.out.println(rbt.toString());
			String c = "C";
			String b = "B";
			String d = "D";
			String a = "A";
			rbt.insert(c);
			rbt.insert(b);
			rbt.insert(d);
			rbt.insert(a);
			System.out.println(rbt.root.leftChild.isBlack);
			System.out.println(rbt.root.rightChild.isBlack);
			//System.out.println("Actual:" + rbt.toString());
			boolean actual = rbt.root.leftChild.isBlack && rbt.root.rightChild.isBlack;
			boolean expected = true;
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			e.getMessage();
			e.printStackTrace();
		}
		try {
			RedBlackTree<String> rbt = new RedBlackTree<String>();
			//System.out.println(rbt.toString());
			String c = "C";
			String b = "B";
			String d = "D";
			String a = "A";
			rbt.insert(b);
			rbt.insert(a);
			rbt.insert(c);
			rbt.insert(d);
			//System.out.println("Actual:" + rbt.toString());
			boolean actual = rbt.root.leftChild.isBlack && rbt.root.rightChild.isBlack;
			boolean expected = true;
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			e.getMessage();
			e.printStackTrace();
		}
		try {
			RedBlackTree<String> rbt = new RedBlackTree<String>();
			//System.out.println(rbt.toString());
			String c = "C";
			String b = "B";
			String d = "D";
			String a = "A";
			rbt.insert(c);
			rbt.insert(a);
			rbt.insert(d);
			rbt.insert(b);
			//System.out.println("Actual:" + rbt.toString());
			boolean actual = rbt.root.leftChild.isBlack && rbt.root.rightChild.isBlack;
			boolean expected = true;
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			e.getMessage();
			e.printStackTrace();
		}
		try {
			RedBlackTree<String> rbt = new RedBlackTree<String>();
			//System.out.println(rbt.toString());
			String c = "C";
			String b = "B";
			String d = "D";
			String a = "A";
			rbt.insert(b);
			rbt.insert(a);
			rbt.insert(d);
			rbt.insert(c);
			//System.out.println("Actual:" + rbt.toString());
			boolean actual = rbt.root.leftChild.isBlack && rbt.root.rightChild.isBlack;
			boolean expected = true;
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	/**
	 *  test case 4: less than or equal to 3 nodes in tree
	 */
	@Test
	void testCaseFour() {
		try {
		RedBlackTree<String> rbt = new RedBlackTree<String>();
		String a = "A";
		String b = "B";
		rbt.insert(b);
		rbt.insert(a);
		String actual = rbt.root.toString();
		System.out.println("actual: " + rbt.root.toString());
		String expected = "[B, A]";
		assertEquals(expected, actual);
		} catch (NullPointerException e) {
			e.getMessage();
			e.printStackTrace();
		}

	}
}
