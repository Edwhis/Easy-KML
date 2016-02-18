package easykml.reader;


import easykml.KmlShape;
import easykml.models.Point;
import easykml.shapes.Placemark;
import easykml.shapes.Polygon;
import easykml.shapes.Polyline;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Edvinas on 2016-02-14.
 */
public class KmlReader {


    private static final String TAG_PLACEMARK = "placemark";
    private static final String TAG_NAME = "name";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_COORDINATES = "coordinates";

    private List<KmlShape> mShapes = new ArrayList<KmlShape>();

    /**
     * Reads KML data from referred file.
     *
     * @param kmlPath Full KML file path
     * @return True - if read was successful.
     */
    public boolean read(String kmlPath) {

        try {
            String kml = readFileContent(kmlPath);
            Document doc = Jsoup.parse(kml, "", Parser.xmlParser());
            for (Element e : doc.select(TAG_PLACEMARK)) {
                mShapes.add(fromPlacemark(e));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Reads file content.
     * If file not exists - returns empty string.
     *
     * @param filePath File path
     * @return File content
     */
    public static String readFileContent(String filePath) {
        String fileText = "";
        try {
            File file = new File(filePath);
            if (file.exists() && !file.isDirectory()) {
                Scanner scanner = null;
                scanner = new Scanner(file);
                fileText = scanner.useDelimiter("\\A").next();
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        return fileText;
    }

    /**
     * Parses placemark element from KML file.
     *
     * @param element Placemark element.
     * @return Created KmlShape from placemark.
     */
    private KmlShape fromPlacemark(Element element) {
        String name = element.select(TAG_NAME).text();
        String description = element.select(TAG_DESCRIPTION).text();
        List<Point> coordinates = parseCoordinates(element);
        KmlShape shape;
        if (coordinates.size() == 1) {
            shape = new Placemark.Builder(coordinates.get(0)).name(name).description(description).build();
        } else if (coordinates.get(0).equals(coordinates.get(coordinates.size() - 1))) {
            shape = new Polygon.Builder(coordinates).name(name).description(description).build();
        } else {
            shape = new Polyline.Builder(coordinates).name(name).description(description).build();
        }
        return shape;
    }

    /**
     * Parses placemark coordinates from string.
     *
     * @param element Placemark element.
     * @return Coordinates array list.
     */
    private ArrayList<Point> parseCoordinates(Element element) {
        List<String> tracksString = new ArrayList<String>();
        ArrayList<Point> oneTrack = new ArrayList<Point>();
        for (Element e : element.select(TAG_COORDINATES)) {
            tracksString.add(e.toString().replace("<coordinates>", "").replace("</coordinates>", ""));
        }

        for (String aTracksString : tracksString) {

            ArrayList<String> oneTrackString = new ArrayList<String>(Arrays.asList(aTracksString.split("\\s+")));
            for (int k = 1; k < oneTrackString.size(); k++) {
                Point latLng = new Point(Double.parseDouble(oneTrackString.get(k).split(",")[0]),
                        Double.parseDouble(oneTrackString.get(k).split(",")[1]));
                oneTrack.add(latLng);
            }
        }
        return oneTrack;
    }


    /**
     * Returns all parsed shapes.
     *
     * @return KmlShape list.
     */
    public List<KmlShape> getShapes() {
        return mShapes;
    }

    /**
     * @return Parsed shapes count.
     */
    public int getShapesCount() {
        if (mShapes != null) {
            return mShapes.size();
        }
        return 0;
    }

    /**
     * Returns parsed placemarks.
     *
     * @return KmlShape list.
     */
    public List<KmlShape> getPlacemarks() {
        List<KmlShape> placemarks = new ArrayList<KmlShape>();
        for (KmlShape shape : mShapes) {
            if (shape instanceof Placemark) {
                placemarks.add(shape);
            }
        }
        return placemarks;
    }

    /**
     * Returns parsed polylines.
     *
     * @return KmlShape list.
     */
    public List<KmlShape> getPolylines() {
        List<KmlShape> polylines = new ArrayList<KmlShape>();
        for (KmlShape shape : mShapes) {
            if (shape instanceof Polyline) {
                polylines.add(shape);
            }
        }
        return polylines;
    }

    /**
     * Returns parsed polygons.
     *
     * @return KmlShape list.
     */
    public List<KmlShape> getPolygons() {
        List<KmlShape> polygons = new ArrayList<KmlShape>();
        for (KmlShape shape : mShapes) {
            if (shape instanceof Polygon) {
                polygons.add(shape);
            }
        }
        return polygons;
    }
}