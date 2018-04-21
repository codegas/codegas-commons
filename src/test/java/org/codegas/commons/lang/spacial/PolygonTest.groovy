package org.codegas.commons.lang.spacial

import spock.lang.Specification

class PolygonTest extends Specification {

   def "Exceptions are thrown if invalid vertices are used to construct a Polygon"() {
      when:
      new Polygon(vertices)

      then:
      thrown(IllegalArgumentException)

      where:
      vertices << [null, [], [new Point(0d, 0d)], [new Point(0d, 0d), new Point(1d, 1d)]]
   }

   def "it can be determined if two points are adjacent to each other in a Polygon"() {
      given: "A square"
      Polygon polygon = new Polygon([new Point(1d, 1d), new Point(2d, 1d), new Point(2d, 2d), new Point(1d, 2d)])

      expect:
      expectedResult == polygon.areVerticesAdjacent(point1, point2)

      where:
      expectedResult | point1            | point2
      false          | new Point(0d, 0d) | new Point(0d, 0d)
      false          | new Point(1d, 1d) | new Point(0d, 0d)
      false          | new Point(1d, 1d) | new Point(2d, 2d)
      true           | new Point(1d, 1d) | new Point(2d, 1d)
      true           | new Point(1d, 1d) | new Point(1d, 2d)
   }

   def "It can be determined if a Polygon is intersected by a LineSegment"() {
      given: "A square"
      Polygon polygon = new Polygon([new Point(1d, 1d), new Point(2d, 1d), new Point(2d, 2d), new Point(1d, 2d)])

      expect:
      expectedResult == polygon.isIntersectedBy(lineSegment)

      where:
      expectedResult | lineSegment
      false          | new LineSegment(new Point(0d, 0d), new Point(1d, 1d))
      false          | new LineSegment(new Point(0d, 2d), new Point(2d, 0d))
      false          | new LineSegment(new Point(0d, 1d), new Point(3d, 1d))
      true           | new LineSegment(new Point(0d, 0d), new Point(1.5d, 1.5d))
      true           | new LineSegment(new Point(1.5d, 0d), new Point(1.5d, 1.5d))
      false          | new LineSegment(new Point(1d, 1.5d), new Point(2d, 1.5d))
      false          | new LineSegment(new Point(1d, 1d), new Point(2d, 2d))
      false          | new LineSegment(new Point(1d, 2d), new Point(2d, 1d))
      false          | new LineSegment(new Point(1d, 1d), new Point(1.5d, 1.5d))
   }

   def "It can be determined if a Polygon contains a Point"() {
      given: "A square"
      Polygon polygon = new Polygon([new Point(1d, 1d), new Point(2d, 1d), new Point(2d, 2d), new Point(1d, 2d)])

      expect:
      expectedResult == polygon.contains(point)

      where:
      expectedResult | point
      false          | new Point(1.5d, 0.5d)
      false          | new Point(1.5d, 1d)
      true           | new Point(1.5d, 1.25d)
      true           | new Point(1.5d, 1.5d)
      true           | new Point(1.5d, 1.75d)
      false          | new Point(1.5d, 2d)
      false          | new Point(1.5d, 2.5d)
      false          | new Point(1d, 1d)
      false          | new Point(1d, 2d)
      false          | new Point(4d, 4d)
      false          | new Point(0d, 0d)
   }
}
