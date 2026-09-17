

// Abstract base class implementing Comparable and Cloneable
abstract class GeometricObject implements Comparable<GeometricObject>, Cloneable {
    private boolean isFilled;
    private String color;
    private double thickness;

    public GeometricObject(boolean isFilled, String color, double thickness) {
        this.isFilled = isFilled;
        this.color = color;
        this.thickness = thickness;
    }

    // Abstract method to be implemented by subclasses
    public abstract double calculateArea();

    // Getters and setters
    public boolean isFilled() {
        return isFilled;
    }

    public String getColor() {
        return color;
    }

    public double getThickness() {
        return thickness;
    }

    // Comparable implementation - compare by area
    @Override
    public int compareTo(GeometricObject other) {
        return Double.compare(this.calculateArea(), other.calculateArea());
    }

    // Cloneable implementation
    @Override
    public GeometricObject clone() {
        try {
            return (GeometricObject) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

// Circle subclass
class Circle extends GeometricObject {
    private double radius;

    public Circle(boolean isFilled, String color, double thickness, double radius) {
        super(isFilled, color, thickness);
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    public double getRadius() {
        return radius;
    }

    // Override clone to make it public
    @Override
    public Circle clone() {
        return (Circle) super.clone();
    }
}

// Rectangle subclass
class Rectangle extends GeometricObject {
    private double width;
    private double height;

    public Rectangle(boolean isFilled, String color, double thickness, double width, double height) {
        super(isFilled, color, thickness);
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // Override clone to make it public
    @Override
    public Rectangle clone() {
        return (Rectangle) super.clone();
    }
}

// Drawing class to hold geometric objects
class Drawing {
    private GeometricObject[] shapes;
    private int count;

    public Drawing() {
        shapes = new GeometricObject[5]; // Maximum 5 shapes
        count = 0;
    }

    public void add(GeometricObject shape) {
        if (count < 5) {
            shapes[count++] = shape;
        } else {
            System.out.println("Drawing is full!");
        }
    }

    public double calculateArea() {
        double totalArea = 0;
        for (int i = 0; i < count; i++) {
            totalArea += shapes[i].calculateArea();
        }
        return totalArea;
    }

    public int countFilled() {
        int filledCount = 0;
        for (int i = 0; i < count; i++) {
            if (shapes[i].isFilled()) {
                filledCount++;
            }
        }
        return filledCount;
    }
}

// Main class to demonstrate functionality
public class Task {
    public static void main(String[] args) {
        Circle c1 = new Circle(false, "yellow", 3.5, 5);
        Circle c2 = new Circle(true, "orange", 1.5, 9);
        Rectangle r1 = new Rectangle(true, "Blue", 1.5, 6, 9);
        Rectangle r2 = new Rectangle(false, "green", 3.6, 6, 4);
        Rectangle r3 = new Rectangle(false, "orange", 4.5, 3, 8);

        Drawing d = new Drawing();

        d.add(c1);
        d.add(c2);
        d.add(r1);
        d.add(r2);
        d.add(r3);

        System.out.println("Total Area of Drawing is " + d.calculateArea());
        System.out.println("Number of Filled Figures in the drawing is " + d.countFilled());

        // Demonstrate Comparable
        System.out.println("\nComparing r1 and r2:");
        int comparison = r1.compareTo(r2);
        if (comparison < 0) {
            System.out.println("r1 is smaller than r2");
        } else if (comparison > 0) {
            System.out.println("r1 is larger than r2");
        } else {
            System.out.println("r1 and r2 are equal in area");
        }

        // Demonstrate Cloneable
        System.out.println("\nCloning c1:");
        Circle c1Clone = c1.clone();
        System.out.println("Original radius: " + c1.getRadius());
        System.out.println("Clone radius: " + c1Clone.getRadius());
        System.out.println("Are they the same object? " + (c1 == c1Clone));
    }
}