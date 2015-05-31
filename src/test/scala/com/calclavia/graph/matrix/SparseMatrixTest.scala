package com.calclavia.graph.matrix

import org.assertj.core.api.Assertions._
import org.junit.Test

/**
 * @author Calclavia
 */
class SparseMatrixTest {

	@Test
	def testPrint() {
		val mat = new SparseMatrix[Int, Int]((0 until 5).toSet, (0 until 5).toSet)
		assertThat("SparseMatrix [5x5]\n  | 0 1 2 3 4 \n0 | 0 0 0 0 0 \n1 | 0 0 0 0 0 \n2 | 0 0 0 0 0 \n3 | 0 0 0 0 0 \n4 | 0 0 0 0 0 \n").isEqualTo(mat.toString)
	}

	@Test
	def testApply() = {
		val mat = new SparseMatrix[Int, Int]((0 until 5).toSet, (0 until 5).toSet)
		mat(2, 3) = 5
		assertThat("SparseMatrix [5x5]\n  | 0 1 2 3 4 \n0 | 0 0 0 0 0 \n1 | 0 0 0 0 0 \n2 | 0 0 0 5 0 \n3 | 0 0 0 0 0 \n4 | 0 0 0 0 0 \n").isEqualTo(mat.toString)
	}

	@Test
	def testGet() = {
		val mat = new SparseMatrix[Int, Int]((0 until 5).toSet, (0 until 5).toSet)
		mat(2, 3) = 5
		assertThat(5).isEqualTo(mat(2, 3))
	}
}
