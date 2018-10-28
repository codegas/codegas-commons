package org.codegas.commons.lang.spacial

import spock.lang.Specification

class LineSegmentTest extends Specification {

   def "It can be determined if two LineSegments intersect exclusive of their endpoints"() {
      expect:
      expectedResult == lineSegment1.intersectsExclusive(lineSegment2)
      expectedResult == lineSegment2.intersectsExclusive(lineSegment1)

      where:
      expectedResult | lineSegment1             | lineSegment2
      false          | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0d, 2d), p(2d, 0d))
      true           | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0d, 0d), p(1d, 1d))
      false          | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0d, 0d), p(0.5d, 0.5d))
      false          | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0.5d, 0.5d), p(0.5d, 0.5d))
      false          | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0.5d, 0.5d), p(1d, 1d))
      false          | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0d, 0d), p(1d, 0d))
      false          | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0d, 0d), p(0d, 1d))
      false          | ls(p(0d, 1d), p(1d, 0d)) | ls(p(2d, -1d), p(3d, -2d))
      false          | ls(p(0d, 1d), p(1d, 0d)) | ls(p(1d, 0d), p(2d, -1d))
      false          | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0d, 0d), p(0.5d, 0.5d))
   }

   def "It can be determined if two LineSegments intersect inclusive of their endpoints"() {
      expect:
      expectedResult == lineSegment1.intersectsInclusive(lineSegment2)
      expectedResult == lineSegment2.intersectsInclusive(lineSegment1)

      where:
      expectedResult | lineSegment1             | lineSegment2
      false          | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0d, 2d), p(2d, 0d))
      true           | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0d, 0d), p(1d, 1d))
      true           | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0d, 0d), p(0.5d, 0.5d))
      true           | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0.5d, 0.5d), p(0.5d, 0.5d))
      false          | ls(p(0d, 1d), p(1d, 0d)) | ls(p(2d, 1d), p(2d, 1d))
      true           | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0.5d, 0.5d), p(1d, 1d))
      true           | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0d, 0d), p(1d, 0d))
      true           | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0d, 0d), p(0d, 1d))
      false          | ls(p(0d, 1d), p(1d, 0d)) | ls(p(2d, -1d), p(3d, -2d))
      true           | ls(p(0d, 1d), p(1d, 0d)) | ls(p(1d, 0d), p(2d, -1d))
      true           | ls(p(0d, 1d), p(1d, 0d)) | ls(p(0d, 0d), p(0.5d, 0.5d))
   }

   def "It can be determined if a LineSegment contains a Point exclusive of its endpoints"() {
      given: "A Line Segment"
      LineSegment lineSegment = new LineSegment(new Point(1d, 1d), new Point(2d, 2d))

      expect:
      lineSegment.containsExclusive(point) == expectedResult

      where:
      expectedResult | point
      false          | new Point(0d, 0d)
      false          | new Point(3d, 3d)
      false          | new Point(1.5d, 2d)
      false          | new Point(1.5d, 1d)
      false          | new Point(1d, 1d)
      false          | new Point(2d, 2d)
      true           | new Point(1.5d, 1.5d)
   }

   def "It can be determined if a LineSegment contains a Point inclusive of its endpoints"() {
      given: "A Line Segment"
      LineSegment lineSegment = new LineSegment(new Point(1d, 1d), new Point(2d, 2d))

      expect:
      lineSegment.containsInclusive(point) == expectedResult

      where:
      expectedResult | point
      false          | new Point(0d, 0d)
      false          | new Point(3d, 3d)
      false          | new Point(1.5d, 2d)
      false          | new Point(1.5d, 1d)
      true           | new Point(1d, 1d)
      true           | new Point(2d, 2d)
      true           | new Point(1.5d, 1.5d)
   }

   private static Point p(double x, double y) {
      new Point(x, y);
   }

   private static LineSegment ls(Point p1, Point p2) {
      new LineSegment(p1, p2);
   }
}
