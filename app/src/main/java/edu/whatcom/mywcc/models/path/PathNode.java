package edu.whatcom.mywcc.models.path;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.Collection;

public class PathNode extends LatLng {
    // so that we don't render any edges twice, each edge exists in only
    // one node's outgoing array, but in two nodes' adjacency arrays.
    public Collection<PathNode> outgoing = new ArrayList<>();
    private Collection<PathNode> adjacencies = new ArrayList<>();

    public PathNode(double lati, double longi) {
        super(lati, longi);
    }

    public void link(PathNode node) {
        outgoing.add(node);
        adjacencies.add(node);
        node.adjacencies.add(this);
    }
}
