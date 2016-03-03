package easykml.shapes;

import easykml.C;
import easykml.KmlShape;
import easykml.EkmlException;
import easykml.Utils;
import easykml.models.Point;

import java.util.List;


/**
 * Created by Edvinas on 2016-02-09.
 */
public final class Polygon extends Shape implements KmlShape {


    private String fillColor = C.FILL_COLOR;

    public Polygon() {
        super();
        setType(C.Type.POLYGON);
    }

    @Override
    public String toKml() throws EkmlException {
        if (mCoordinates == null) {
            throw new EkmlException("Coordinates can't be null");
        } else if (mCoordinates.size() < 3) {
            throw new EkmlException("Coordinates must contain at least 3 points");
        }
        return "<Placemark>\n" +
                (!super.addName().isEmpty() ? "\t"+super.addName() : "")  +
                (!super.addDescription().isEmpty() ? "\t"+super.addDescription() : "")  +
                "\t<Style>\n" +
                "\t\t<LineStyle>\n" +
                "\t\t\t<color>" + Utils.RGBtoBGR(mLineColor) + "</color>\n" +
                "\t\t\t<width>" + mLineWidth + "</width>\n" +
                "\t\t</LineStyle>\n" +
                "\t\t<PolyStyle>\n" +
                "\t\t\t<color>" + Utils.RGBtoBGR(fillColor) + "</color>\n" +
                "\t\t</PolyStyle>\n" +
                "\t</Style>\n" +
                "\t<Polygon>\n" +
                "\t\t<outerBoundaryIs>\n" +
                "\t\t\t<LinearRing>\n" +
                "\t\t\t\t<coordinates>\n" +
                "\t\t\t\t\t" + Utils.createCoordinatesString(mCoordinates, C.Type.POLYGON) + "\n" +
                "\t\t\t\t</coordinates>\n" +
                "\t\t\t</LinearRing>\n" +
                "\t\t</outerBoundaryIs>\n" +
                "\t</Polygon>\n" +
                "</Placemark>\n";
    }

    public static final class Builder extends Shape.Builder<Polygon, Builder> {
        public Builder(List<Point> coordinates) {
            super(coordinates);
        }

        protected Polygon createObject() {
            return new Polygon();
        }

        protected Builder thisObject() {
            return this;
        }

        public Builder fillColor(String color) {
            object.fillColor = color;
            return thisObject;
        }
    }
}
