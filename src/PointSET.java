import edu.princeton.cs.algs4.Point2D;
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
        set.add(p);
    } // add point to set

    public boolean contains(Point2D p){
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

//    public Iterable<Point2D> range(RectHV rect){} //all points that are inside (or on boundary of) the rectangle

//    public Point2D nearest(Point2D p){} // nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args){
        PointSET pSet = new PointSET();
        while(!StdIn.isEmpty()){
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x,y);
            pSet.insert(p);
        }

        StdDraw.setScale(-.05, 1.05);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.01);
        pSet.draw();
        StdDraw.show();

/*        while (true){
            if(StdDraw.isMousePressed()){
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n",x,y);
                Point2D p = new Point2D(x,y);
                pSet.insert(p);
                StdDraw.pause(5);
//                StdDraw.clear();
//                pSet.draw();
//                StdDraw.show();
            }
            else
                break;

        }
        StdDraw.setScale(-.05, 1.05);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.01);
        pSet.draw();
        StdDraw.show();*/
//        StdOut.println("Hello World!");
    }
}