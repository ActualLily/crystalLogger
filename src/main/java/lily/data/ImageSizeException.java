package lily.data;

public class ImageSizeException extends Exception {

    public ImageSizeException(int expectedWidth, int expectedHeight, int givenWidth, int givenHeight) {
        super("Expected " + expectedWidth + "x" + expectedHeight + ", got " + givenWidth + "x" + givenHeight);
    }

    public ImageSizeException() {
        super();
    }
}
