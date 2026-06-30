/**
 * Starter template for a simple library management system demonstrating OOP.
 */
public class LibraryManagementSystem {
    // This wrapper class contains the methods/classes to complete.
}

/**
 * Represents an abstract library item.
 */
abstract class LibraryItem {
    private String title;
    private String itemId;
    private boolean checkedOut;

    public LibraryItem(String title, String itemId) {
        // TODO: Initialize fields
    }

    public String getTitle() {
        // TODO: Return title
        return null;
    }

    public String getItemId() {
        // TODO: Return itemId
        return null;
    }

    public boolean isCheckedOut() {
        // TODO: Return checkedOut status
        return false;
    }

    /**
     * Checks out the item. Throws IllegalStateException if the item is already checked out.
     */
    public void checkOut() {
        // TODO: Implement checkOut. Throw IllegalStateException if already checked out.
    }

    /**
     * Returns the item to the library. Throws IllegalStateException if the item is not currently checked out.
     */
    public void returnItem() {
        // TODO: Implement returnItem. Throw IllegalStateException if not checked out.
    }

    /**
     * Abstract method to be overridden by subclasses to return description details.
     */
    public abstract String getDetails();
}

/**
 * Represents a Book, which is a type of LibraryItem.
 */
class Book extends LibraryItem {
    private String author;
    private int pageCount;

    public Book(String title, String itemId, String author, int pageCount) {
        // TODO: Call superclass constructor and initialize Book-specific fields
        super(null, null);
    }

    public String getAuthor() {
        // TODO: Return author
        return null;
    }

    public int getPageCount() {
        // TODO: Return pageCount
        return 0;
    }

    /**
     * Returns a string in the format:
     * "Book: [title] by [author] ([pageCount] pages)"
     */
    @Override
    public String getDetails() {
        // TODO: Implement getDetails
        return null;
    }
}

/**
 * Represents a Journal, which is a type of LibraryItem.
 */
class Journal extends LibraryItem {
    private int issueNumber;

    public Journal(String title, String itemId, int issueNumber) {
        // TODO: Call superclass constructor and initialize Journal-specific fields
        super(null, null);
    }

    public int getIssueNumber() {
        // TODO: Return issueNumber
        return 0;
    }

    /**
     * Returns a string in the format:
     * "Journal: [title] (Issue #[issueNumber])"
     */
    @Override
    public String getDetails() {
        // TODO: Implement getDetails
        return null;
    }
}
