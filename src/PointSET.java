import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.TreeSet;

public class PointSET{
    private TreeSet<Point2D> set;

    public PointSET(){
        set = new TreeSet<Point2D>();
    } //Construct empty set of points

    public boolean isEmpty(){
        return set.size()==0;
    } //is the set empty?

    public int size(){
        return set.size();
    } //# points in set

    public void insert(Point2D p){
        if(p==null)
            throw new IllegalArgumentException("Null argument");

        set.add(p);
    } // add point to set

    public boolean contains(Point2D p){
        if(p==null)
            throw new IllegalArgumentException("Null argument");
        return set.contains(p);
    }// does set contain point p?

    //draw all points to standard draw
    public void draw(){
        for(Point2D p : set){
            p.draw();
//            StdDraw.point(p.x(),p.y());
        }
//        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect){
        if(rect == null)
            throw new IllegalArgumentException("Null argument");

        Queue<Point2D> q = new Queue<Point2D>();
        for(Point2D p : set){
            if(rect.contains(p))
                q.enqueue(p);
        }
        return q;
    } //all points that are inside (or on boundary of) the rectangle

    public Point2D nearest(Point2D p){
        if(p==null)
            throw new IllegalArgumentException("Null argument");

        if(this.isEmpty())
            return null;

        double minDist = Double.POSITIVE_INFINITY;
        Point2D minP = null;
        for(Point2D pSet : set){
            double currDist = pSet.distanceSquaredTo(p);
            if(currDist < minDist){
                minDist = currDist;
                minP = pSet;
            }
        }
        return minP;
    } // nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args){
        PointSET pSet = new PointSET();
        StdOut.println("pSet.isEmpty() = " + pSet.isEmpty());
        StdOut.println("pSet.size() = " + pSet.size());
        Point2D p = new Point2D(2.0,1.0);
        StdOut.println("pSet.contains(Point(1.0,2.0)) = " + pSet.contains(p));
        StdOut.println("pSet.nearest(Point(1.0,2.0))) = " + pSet.nearest(p));
        while(!StdIn.isEmpty()){
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            p = new Point2D(x,y);
//            Point2D p = new Point2D(x,y);
            pSet.insert(p);
        }

        Point2D pPert = new Point2D(p.x()+1e-4,p.y()+1e-4);
        StdOut.println("pSet.isEmpty() = " + pSet.isEmpty());
        StdOut.println("pSet.size() = " + pSet.size());
        StdOut.println("pSet.contains(Point(last added point)) = " + pSet.contains(p));
        StdOut.println("pSet.nearest(last added point) = " + pSet.nearest(p));
        StdOut.println("pSet.contains(Point(last added point + delX,delY)) = " + pSet.contains(pPert));
        StdOut.println("pSet.nearest(last added point + delX,delY) = " + pSet.nearest(pPert));

//        StdDraw.setScale(-.05, 1.05);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.01);
        pSet.draw();
        StdDraw.show();
    }
}