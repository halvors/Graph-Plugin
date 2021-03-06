package com.calclavia.graph.matrix

import scala.Numeric.Implicits._

/**
 * A general sparse matrix with generic input and values.
 * @author Calclavia
 */
object SparseMatrix {
	def apply[V](rows: Int, columns: Int)(implicit n: Numeric[V]) = new SparseMatrix[Int, V]((0 until rows).toSet, (0 until columns).toSet)(n)
}

class SparseMatrix[K, V](val rows: Set[K], var columns: Set[K])(implicit n: Numeric[V]) {

	protected var mat = Map.empty[(K, K), V].withDefaultValue(n.zero)

	def apply(i: K, j: K): V = {
		assert(rows.contains(i) && columns.contains(j), "Matrix domain out of bounds!")
		return mat(i, j)
	}

	def update(i: K, j: K, value: V) {
		assert(rows.contains(i) && columns.contains(j), "Matrix domain out of bounds!")
		mat += (i, j) -> value
	}

	def *(B: SparseMatrix[K, V]): SparseMatrix[K, V] = {
		assert(columns == B.rows)
		val C = new SparseMatrix[K, V](rows, B.columns)
		for (row <- C.rows; column <- C.columns; aCol <- columns)
			C(row, column) += this(row, aCol) * B(aCol, column)
		return C
	}

	def *(scaler: V): SparseMatrix[K, V] = {
		val C = new SparseMatrix[K, V](rows, columns)
		C.mat = C.mat.mapValues(n.times(_, scaler))
		return C
	}

	def transpose: SparseMatrix[K, V] = {
		val A = new SparseMatrix[K, V](columns, rows)
		mat.foreach {
			case ((k1, k2), v) =>
				A(k2, k1) = v
		}
		return A
	}

	override def equals(obj: Any): Boolean = {
		obj match {
			case b: SparseMatrix[K, V] =>
				val a = this
				if (b.rows.equals(a.rows) && b.columns.equals(b.columns)) {
					return a.mat.equals(b.mat)
				}
		}
		return false
	}

	override def toString: String = {
		val sb = new StringBuilder
		sb.append("SparseMatrix [" + rows.size + "x" + columns.size + "]\n")
		/*
		val averageRowLabelLength = rows.map(_.toString.length).sum / rows.size
		(0 until averageRowLabelLength).foreach(i=>sb.append(" "))
		sb.append(" | ")
		//Print row labels
		columns.foreach(j => {
			val averageRowLength = rows.map(this(_, j)).collect { case Some(element) => element.toString.length }.sum / rows.size
			sb.append(j)
			(0 until averageRowLength).foreach(i=>sb.append(" "))
		})
		sb.append("\n")*/
		rows.foreach(i => {
			//Print column labels
			//			sb.append(i + " | ")
			columns.foreach(j => sb.append(this(i, j) + " "))
			sb.append("\n")
		})
		return sb.toString
	}
}
