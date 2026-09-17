import java.util.ArrayList;
import java.util.List;

/** Provides shared validation rules for geometric objects. */
final class ImprovedShapeValidation {
    private ImprovedShapeValidation() {
    }

    static double requireNonNegative(double value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative");
        }
        return value;
    }

    static <T> T requireNotNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
        return value;
    }
}

/** Defines the common data and behavior shared by all geometric shapes. */
abstract class ImprovedGeometricObject implements Comparable<ImprovedGeometricObject>, Cloneable {
    private final boolean filled;
    private final String color;
    private final double thickness;

    protected ImprovedGeometricObject(boolean filled, String color, double thickness) {
        this.filled = filled;
        this.color = color;
        this.thickness = thickness;
    }

    /** Returns the area calculated from this shape's dimensions. */
    public abstract double calculateArea();

    public final boolean isFilled() {
        return filled;
    }

    public final String getColor() {
        return color;
    }

    public final double getThickness() {
        return thickness;
    }

    @Override
    public final int compareTo(ImprovedGeometricObject other) {
        ImprovedShapeValidation.requireNotNull(other, "Shape to compare");
        return Double.compare(calculateArea(), other.calculateArea());
    }

    @Override
    public ImprovedGeometricObject clone() {
        try {
            return (ImprovedGeometricObject) super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError("Cloning is supported", exception);
        }
    }
}

/** Represents a circle with a fixed radius. */
final class ImprovedCircle extends ImprovedGeometricObject {
    private final double radius;

    public ImprovedCircle(boolean filled, String color, double thickness, double radius) {
        super(filled, color, thickness);
        this.radius = ImprovedShapeValidation.requireNonNegative(radius, "Radius");
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public ImprovedCircle clone() {
        return (ImprovedCircle) super.clone();
    }
}

/** Represents a rectangle with a fixed width and height. */
final class ImprovedRectangle extends ImprovedGeometricObject {
    private final double width;
    private final double height;

    public ImprovedRectangle(boolean filled, String color, double thickness, double width, double height) {
        super(filled, color, thickness);
        this.width = ImprovedShapeValidation.requireNonNegative(width, "Width");
        this.height = ImprovedShapeValidation.requireNonNegative(height, "Height");
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

    @Override
    public ImprovedRectangle clone() {
        return (ImprovedRectangle) super.clone();
    }
}

/** Stores shapes and provides operations across the complete drawing. */
final class ImprovedDrawing {
    private final List<ImprovedGeometricObject> shapes = new ArrayList<>();

    public void add(ImprovedGeometricObject shape) {
        shapes.add(ImprovedShapeValidation.requireNotNull(shape, "Shape"));
    }

    /** Returns the shape at the requested position in the drawing. */
    public ImprovedGeometricObject getShape(int index) {
        if (index < 0 || index >= shapes.size()) {
            throw new IndexOutOfBoundsException("Shape index is outside the drawing");
        }
        return shapes.get(index);
    }

    /** Returns the combined area of every shape in the drawing. */
    public double calculateArea() {
        double totalArea = 0;
        for (ImprovedGeometricObject shape : shapes) {
            totalArea += shape.calculateArea();
        }
        return totalArea;
    }

    /** Returns how many shapes in the drawing are filled. */
    public int countFilled() {
        int filledCount = 0;
        for (ImprovedGeometricObject shape : shapes) {
            if (shape.isFilled()) {
                filledCount++;
            }
        }
        return filledCount;
    }

    /** Returns a readable description of the area comparison between two shapes. */
    public String describeComparison(ImprovedGeometricObject firstShape,
                                     ImprovedGeometricObject secondShape) {
        int comparison = firstShape.compareTo(secondShape);
        if (comparison < 0) {
            return "The first shape is smaller than the second shape.";
        }
        if (comparison > 0) {
            return "The first shape is larger than the second shape.";
        }
        return "The shapes have equal areas.";
    }
}

public class improvedTask {
    private static final String SECTION_SEPARATOR = "\n------------------------------";
    private static final int ORIGINAL_CIRCLE_INDEX = 0;
    private static final int FIRST_RECTANGLE_INDEX = 2;
    private static final int SECOND_RECTANGLE_INDEX = 3;

    public static void main(String[] args) {
        ImprovedDrawing drawing = createSampleDrawing();
        ImprovedCircle originalCircle = (ImprovedCircle) drawing.getShape(ORIGINAL_CIRCLE_INDEX);
        ImprovedGeometricObject firstRectangle = drawing.getShape(FIRST_RECTANGLE_INDEX);
        ImprovedGeometricObject secondRectangle = drawing.getShape(SECOND_RECTANGLE_INDEX);

        printDrawingSummary(drawing);
        printComparison(drawing, firstRectangle, secondRectangle);
        printCloneDetails(originalCircle);
    }

    private static ImprovedDrawing createSampleDrawing() {
        ImprovedDrawing drawing = new ImprovedDrawing();
        drawing.add(new ImprovedCircle(false, "yellow", 3.5, 5));
        drawing.add(new ImprovedCircle(true, "orange", 1.5, 9));
        drawing.add(new ImprovedRectangle(true, "Blue", 1.5, 6, 9));
        drawing.add(new ImprovedRectangle(false, "green", 3.6, 6, 4));
        drawing.add(new ImprovedRectangle(false, "orange", 4.5, 3, 8));
        return drawing;
    }

    private static void printDrawingSummary(ImprovedDrawing drawing) {
        System.out.println("Drawing Summary");
        System.out.println(SECTION_SEPARATOR);
        System.out.println("Total area: " + drawing.calculateArea());
        System.out.println("Filled shapes: " + drawing.countFilled());
    }

    private static void printComparison(ImprovedDrawing drawing,
                                        ImprovedGeometricObject firstShape,
                                        ImprovedGeometricObject secondShape) {
        System.out.println(SECTION_SEPARATOR);
        System.out.println("Shape Comparison");
        System.out.println(drawing.describeComparison(firstShape, secondShape));
    }

    private static void printCloneDetails(ImprovedCircle originalCircle) {
        ImprovedCircle clonedCircle = originalCircle.clone();
        System.out.println(SECTION_SEPARATOR);
        System.out.println("Clone Details");
        System.out.println("Original radius: " + originalCircle.getRadius());
        System.out.println("Clone radius: " + clonedCircle.getRadius());
        System.out.println("Are they the same object? " + (originalCircle == clonedCircle));
    }
}
