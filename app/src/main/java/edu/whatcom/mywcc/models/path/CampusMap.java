package edu.whatcom.mywcc.models.path;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.whatcom.mywcc.models.Building;

public class CampusMap {
    public Collection<PathNode> nodes = new ArrayList<>();
    public Map<Building, PathNode> buildings = new HashMap<>();

    public static CampusMap createWCCCampusMap() {
        CampusMap map = new CampusMap();

        PathNode bakerDoorway = map.addNode(48.794392, -122.492943);
        PathNode syreCafe = map.addNode(48.794068, -122.492945);
        PathNode syreMain = map.addNode(48.794072, -122.493438);
        PathNode laidlawBack = map.addNode(48.794296, -122.493606);
        PathNode courtyardLower = map.addNode(48.794254, -122.493432);
        PathNode heinerMain = map.addNode(48.794750, -122.493000);

        bakerDoorway.link(syreCafe);
        bakerDoorway.link(courtyardLower);
        bakerDoorway.link(heinerMain);
        courtyardLower.link(laidlawBack);
        courtyardLower.link(syreMain);
        courtyardLower.link(syreCafe);
        courtyardLower.link(heinerMain);
        syreMain.link(syreCafe);

        PathNode courtyardUpper = map.addNode(48.794772, -122.493415);
        courtyardUpper.link(courtyardLower);
        courtyardUpper.link(heinerMain);

        PathNode laidlawFront = map.addNode(48.794794, -122.493758);
        PathNode laidlawMain = map.addNode(48.794706, -122.493750);
        courtyardUpper.link(laidlawFront);
        laidlawMain.link(laidlawFront);

        PathNode kulshanSidewalk = map.addNode(48.795208, -122.493803);
        laidlawFront.link(kulshanSidewalk); // crosswalk

        PathNode kulshanMain = map.addNode(48.795378, -122.493594); // near the granite ball
        PathNode kulshanBack = map.addNode(48.795900, -122.493601); // northeast corner
        PathNode kulshanCorner = map.addNode(48.795898, -122.494016); // cafe
        PathNode kulshanSidewalkCorner = map.addNode(48.795213, -122.494067);
        kulshanSidewalk.link(kulshanSidewalkCorner);
        kulshanSidewalk.link(kulshanMain);
        kulshanCorner.link(kulshanBack);
        kulshanCorner.link(kulshanSidewalkCorner);

        PathNode caskulCircle = map.addNode(48.795936, -122.494546); // circle between cascade and kulshan
        PathNode kellyFront = map.addNode(48.796212, -122.494579);
        kulshanCorner.link(caskulCircle);
        caskulCircle.link(kellyFront);

        PathNode crosswalk2 = map.addNode(48.795130, -122.494798);
        kulshanSidewalkCorner.link(crosswalk2);

        PathNode syreInt = map.addNode(48.793844, -122.493228); // interior
        PathNode bakerInt = map.addNode(48.794370, -122.492733);
        PathNode laidlawInt = map.addNode(48.794455, -122.493745);
        PathNode heinerInt = map.addNode(48.794736, -122.492778);
        PathNode kulshanInt = map.addNode(48.795652, -122.493607);
        PathNode kellyInt = map.addNode(48.796216, -122.494219);
        map.buildings.put(Building.getById("SYR"), syreInt);
        map.buildings.put(Building.getById("BKR"), bakerInt);
        map.buildings.put(Building.getById("LDC"), laidlawInt);
        map.buildings.put(Building.getById("HNR"), heinerInt);
        map.buildings.put(Building.getById("KUL"), kulshanInt);
        map.buildings.put(Building.getById("KLY"), kellyInt);
        syreInt.link(syreCafe);
        syreInt.link(syreMain);
        bakerInt.link(bakerDoorway);
        laidlawInt.link(laidlawBack);
        laidlawInt.link(laidlawMain);
        heinerInt.link(heinerMain);
        kulshanInt.link(kulshanBack);
        kulshanInt.link(kulshanMain);
        kellyInt.link(kellyFront);

        return map;
    }

    public PathNode addNode(double lati, double longi) {
        PathNode node = new PathNode(lati, longi);
        nodes.add(node);
        return node;
    }

    public List<LatLng> path(PathNode from, PathNode to) {
        for(PathNode n : nodes) {
            n.shortestLink = null;
            n.shortestPath = Double.POSITIVE_INFINITY;
            n.known = false;
        }

        from.shortestPath = 0;
        from.shortestLink = null;

        PathNode n;
        while((n = shortestUnknownNode()) != null) {
            n.known = true;
            if(n == to) {
                List<LatLng> p = new ArrayList<>();
                PathNode l = n;
                while(l != null) {
                    p.add(l);
                    l = l.shortestLink;
                }
                return p;
            }
            for(PathNode a : n.adjacencies) {
                double l = n.shortestPath + n.distanceTo(a);
                if(l < a.shortestPath) {
                    if(a.known) {
                        throw new RuntimeException("somehow we found a shorter path? are negative lengths afoot?");
                    }
                    a.shortestPath = l;
                    a.shortestLink = n;
                }
            }
        }

        return null;
    }

    private PathNode shortestUnknownNode() {
        PathNode cur = null;
        double length = Double.POSITIVE_INFINITY;
        for(PathNode n : nodes) {
            if(!n.known && n.shortestPath < length) {
                cur = n;
                length = cur.shortestPath;
            }
        }
        return cur;
    }
}
