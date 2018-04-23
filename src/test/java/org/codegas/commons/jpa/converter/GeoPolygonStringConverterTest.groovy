package org.codegas.commons.jpa.converter

import org.codegas.commons.lang.spacial.GeoPoint
import org.codegas.commons.lang.spacial.GeoPolygon
import spock.lang.Specification

class GeoPolygonStringConverterTest extends Specification {

   static geoPolygonConverter = new GeoPolygonStringConverter()

   def "A GeoPolygon can be converted to a String and back again"() {
      given:
      def geoPolygon = new GeoPolygon([new GeoPoint(0, 0), new GeoPoint(0, 1), new GeoPoint(1, 0)])

      when:
      def string = geoPolygonConverter.convertToDatabaseColumn(geoPolygon)

      then:
      !string.isEmpty()

      when:
      def convertedGeoPolygon = geoPolygonConverter.convertToEntityAttribute(string)

      then:
      convertedGeoPolygon == geoPolygon
   }
}
