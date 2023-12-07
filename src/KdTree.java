import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Need to update and fix
// - add rectangle property of each Node which is required for range and nearest neighbor methods.
// - implement range method
// - implement nearest method
// - private insert passes null argument as rectangle. Update to pass proper rectangle
public class KdTree{
    // Implement these methods for private tree2D class
    // size
    // add/insert
    // contains
    //private tree2D<Point2D> set;

    private Node root; //root of kdTree

    private static class Node{
        private Point2D p; //the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node lb; // the left/bottom subtree
        private Node rt; // the left/bottom subtree

        public Node(Point2D p,RectHV rect){
            this.p = p;
            this.rect = rect;
        }
    }

    private int sz = 0;

    private Iterable<Point2D> pointSet(){
        Queue<Point2D> q = new Queue<Point2D>();
        pointSet(root,q);
        return q;
    }

    private void pointSet(Node x,Queue<Point2D> q){
        if(x==null) return;
        pointSet(x.lb,q);
        q.enqueue(x.p);
        pointSet(x.rt,q);
    }

    public KdTree(){
//        set = new tree2D<Point2D>();
    } //Construct empty set of points

    public boolean isEmpty(){
        return sz==0;
    } //is the set empty?

    public int size(){
        return sz;
    } //# points in set

    public void insert(Point2D p){
        if(p==null)
            throw new IllegalArgumentException("Null argument");

//        RectHV currRec = new RectHV(0.0,0.0,1.0,1.0);
        root = insert(root,p,0);
        //set.add(p);
    } // add point to set

    private Node insert(Node x, Point2D p, int level){
        if(x==null){sz++; return new Node(p,null);}
        int cmp = compareTo(p,x.p,level);
        int nextLevel = (++level)%2;
        if      (cmp < 0) x.lb = insert(x.lb, p, nextLevel);
        else if (cmp > 0) x.rt = insert(x.rt,p,nextLevel);
        // If current dimension is equal compare other dimension. If equal, replace point, otherwise insert into right subtree.
        else {
            int cmp2 = compareTo(p,x.p,nextLevel);
            if(cmp2 == 0)
                x.p = p;
            else
                x.rt = insert(x.rt,p,nextLevel);
        }
        return x;
    }

    private double getCompareVal(Point2D p, int idx){
        assert (idx == 0) || (idx == 1);
        double thisKey;
        if(idx == 0) {
            thisKey = p.x();
        }
        else {
            thisKey = p.y();
        }
        return thisKey;
    }

    private int compareTo(Point2D p, Point2D that, int level){
//        int idx = level % 2;
//        int idx = level;
        double thisKey, thatKey;
        thisKey = getCompareVal(p, level);
        thatKey = getCompareVal(that, level);

/*        if(idx == 0) {
            thisKey = p.x();
            thatKey = that.x();
        }
        else {
            thisKey = p.y();
            thatKey = that.y();
        }*/
        if(thisKey < thatKey) return -1;
        if(thisKey > thatKey) return +1;
        return 0;
    }

    public boolean contains(Point2D p){
        if(p==null)
            throw new IllegalArgumentException("Null argument");
        return contains(root,p,0);
    }// does set contain point p?

    private boolean contains(Node x, Point2D p, int level){
        if (x==null) return false;
        int cmp = compareTo(p,x.p,level);
        int nextLevel = (++level)%2;
        if (cmp < 0) return contains(x.lb,p,nextLevel);
        if (cmp > 0) return contains(x.rt,p,nextLevel);
        else{
            int cmp2 = compareTo(p,x.p,nextLevel);
            if(cmp == 0)
                return true;
            else
                return contains(x.rt,p,nextLevel);
        }
    }

    //draw all points to standard draw
    public void draw(){
        for(Point2D p : pointSet()){
            p.draw();
//            StdDraw.point(p.x(),p.y());
        }
//        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect){
        if(rect == null)
            throw new IllegalArgumentException("Null argument");

        Queue<Point2D> q = new Queue<Point2D>();
        for(Point2D p : pointSet()){
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
        for(Point2D pSet : pointSet()){
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