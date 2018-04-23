package org.codegas.commons.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.codegas.commons.lang.spacial.GeoPolygon;

@Converter
public class GeoPolygonStringConverter implements AttributeConverter<GeoPolygon, String> {

    @Override
    public String convertToDatabaseColumn(GeoPolygon attributeObject) {
        return attributeObject == null ? null : attributeObject.toString();
    }

    @Override
    public GeoPolygon convertToEntityAttribute(String dbData) {
        return GeoPolygon.fromString(dbData);
    }
}
