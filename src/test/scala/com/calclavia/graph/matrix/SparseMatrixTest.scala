package com.calclavia.graph.matrix

import org.assertj.core.api.Assertions._
import org.junit.Test

/**
 * @author Calclavia
 */
class SparseMatrixTest {

	@Test
	def testConstruction() {
		val mat = new SparseMatrix[Int, Int]((0 until 5).toSet, (0 until 5).toSet)
		(0 until 5).foreach(x => (0 until 5).foreach(y => assertThat(mat(x, y)).isEqualTo(0)))
	}

	@Test
	def testApply() {
		val mat = new SparseMatrix[Int, Int]((0 until 5).toSet, (0 until 5).toSet)
		mat(2, 3) = 5
		assertThat(mat(2, 3)).isEqualTo(5)
	}

	@Test
	def testGet() {
		val mat = new SparseMatrix[Int, Int]((0 until 5).toSet, (0 until 5).toSet)
		mat(2, 3) = 5
		assertThat(5).isEqualTo(mat(2, 3))
	}
}
