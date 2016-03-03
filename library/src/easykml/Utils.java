package easykml;

import easykml.models.Point;

import java.util.List;

/**
 * Created by Edvinas on 2016-02-09.
 */
public class Utils {

    /**
     * Creates string from coordinates array.
     * If figure type is polygon and first and last coordinates are not equal - adds first coordinate to the end to close shape.
     *
     * @param coordinatesList Coordinates array
     * @param figureType      Figure type
     * @return Coordinates string
     */
    public static String createCoordinatesString(List<Point> coordinatesList, C.Type figureType) {
        if (coordinatesList.size() <= 0) return "";
        String coordinates = "";

        for (Point coordinate : coordinatesList)
            coordinates += coordinate.toString() + " ";

        if (figureType == C.Type.POLYGON && !coordinatesList.get(0).equals(coordinatesList.get(coordinatesList.size() - 1))) {
            Point firstCoordinate = coordinatesList.get(0);
            coordinates += firstCoordinate.toString() + " ";
        }

        return coordinates;
    }

    /**
     * Converts RGB (ARBG) color to BGR (ABGR).
     * If color is without alpha - makes alpha "FF".
     *
     * @param color RGB/ARGB color string
     * @return Converted color
     */
    public static String RGBtoBGR(String color) {
        if (color.length() == 6) {
            color = "FF" + color;
        }
        if (color.length() == 8) {
            return color.substring(0, 2) + color.substring(6, 8) + color.substring(4, 6) + color.substring(2, 4);
        }
        return color;
    }

}
