package org.codegas.commons.lang.spacial

import org.codegas.commons.lang.compare.Comparison
import spock.lang.Specification

class PointTest extends Specification {
   private static final DOUBLE_EQUALITY_THRESHOLD = 0.001d

   def "The determinant of two Points is calculated correctly"() {
      given:
      Point p = new Point(1d, 1d)
      Point q = point2
      Point r = point3

      expect:
      Comparison.areValuesClose(Point.determinant(p, q, r), expectedResult, DOUBLE_EQUALITY_THRESHOLD)

      where:
      point2            | point3            | expectedResult | scenario
      new Point(1d, 1d) | new Point(1d, 1d) | 0d             | "Co-linear"
      new Point(1d, 1d) | new Point(1d, 2d) | 0d             | "Co-linear"
      new Point(1d, 2d) | new Point(1d, 1d) | 0d             | "Co-linear"
      new Point(1d, 2d) | new Point(1d, 2d) | 0d             | "Co-linear"
      new Point(1d, 2d) | new Point(1d, 3d) | 0d             | "Co-linear"
      new Point(1d, 2d) | new Point(2d, 1d) | -1d            | "Clockwise"
      new Point(1d, 2d) | new Point(0d, 1d) | 1d             | "Counterclockwise"
   }

   def "The distance between two points can be determined"() {
      given:
      Point point = new Point(1d, 1d)

      expect:
      Comparison.areValuesClose(point.distanceTo(otherPoint), expectedDistance, DOUBLE_EQUALITY_THRESHOLD)

      where:
      expectedDistance | otherPoint
      0d               | new Point(1d, 1d)
      1d               | new Point(0d, 1d)
      1d               | new Point(1d, 0d)
      1d               | new Point(1d, 2d)
      1d               | new Point(2d, 1d)
      1.414d           | new Point(0d, 0d)
      1.414d           | new Point(2d, 2d)
   }
}
