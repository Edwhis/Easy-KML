package esapp;

import easykml.EkmlException;
import easykml.KmlShape;
import easykml.creator.KmlCreator;
import easykml.models.Point;
import easykml.reader.KmlReader;
import easykml.shapes.Placemark;
import easykml.shapes.Polygon;
import easykml.shapes.Polyline;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws EkmlException {
        Point p = new Point(54.953780, 23.754378);


        Placemark placemark = new Placemark.Builder(p)
                .name("Placemark name")
                .build();

        List<Point> polygonPoints = new ArrayList<>();
        polygonPoints.add(new Point(54.954454, 23.753660));
        polygonPoints.add(new Point(54.954034, 23.755130));
        polygonPoints.add(new Point(54.953487, 23.755028));
        polygonPoints.add(new Point(54.953551, 23.753522));

        Polygon polygon = new Polygon.Builder(polygonPoints)
                .description("Polygon description")
                .name("Polygon name")
                .fillColor("7D1EFF00")
                .lineColor("7DFF00D5")
                .lineWidth(5)
                .build();


        List<Point> polylinePoints = new ArrayList<>();
        polylinePoints.add(new Point(54.954578, 23.752608));
        polylinePoints.add(new Point(54.954089, 23.75254));
        polylinePoints.add(new Point(54.954030, 23.753567));


        Polyline polyline = new Polyline.Builder(polylinePoints)
                .name("Default polyline")
                .build();


        List<KmlShape> list = new ArrayList<>();
        list.add(polygon);
        list.add(polyline);

        KmlCreator.toFile(KmlCreator.makeKML(list), "KmlFile.kml");
        KmlCreator.append(placemark, "KmlFile.kml");

        KmlReader reader = new KmlReader();
        reader.read("KmlFile.kml");
        System.out.println(reader.getShapes());
    }
}
