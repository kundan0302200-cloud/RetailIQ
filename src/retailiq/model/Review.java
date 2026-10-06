package retailiq.model;

/**
 * Represents a customer product review.
 */
public class Review {
    private int productId;
    private String reviewText;
    private int rating;

    public Review(int productId, String reviewText, int rating) {
        this.productId = productId;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("[Product #%d] (Rating: %d/5): \"%s\"", productId, rating, reviewText);
    }
}
