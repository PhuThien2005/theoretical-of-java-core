package no09_oop.practice.library_management_system;

/**
 * Reference solution for LibraryManagementSystemSolution.
 */
public class LibraryManagementSystemSolution {
    // Container class
}

/**
 * Abstract class encapsulating state and common behavior.
 * This demonstrates Encapsulation (private fields with controlled access via methods)
 * and Abstraction (defining general rules for checkout and abstract methods for details).
 */
abstract class LibraryItem {
    private String title;
    private String itemId;
    private boolean checkedOut;

    public LibraryItem(String title, String itemId) {
        if (title == null || itemId == null) {
            throw new IllegalArgumentException("Title and Item ID cannot be null");
        }
        this.title = title;
        this.itemId = itemId;
        this.checkedOut = false;
    }

    public String getTitle() {
        return title;
    }

    public String getItemId() {
        return itemId;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void checkOut() {
        if (checkedOut) {
            throw new IllegalStateException("Item " + itemId + " is already checked out.");
        }
        checkedOut = true;
    }

    public void returnItem() {
        if (!checkedOut) {
            throw new IllegalStateException("Item " + itemId + " is not checked out.");
        }
        checkedOut = false;
    }

    public abstract String getDetails();
}

/**
 * Book subclass extending LibraryItem.
 * This demonstrates Inheritance (inherits fields and checkout behavior from LibraryItem)
 * and Polymorphism (provides a specific implementation of getDetails()).
 */
class Book extends LibraryItem {
    private String author;
    private int pageCount;

    public Book(String title, String itemId, String author, int pageCount) {
        super(title, itemId);
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        this.author = author;
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public String getDetails() {
        return "Book: " + getTitle() + " by " + author + " (" + pageCount + " pages)";
    }
}

/**
 * Journal subclass extending LibraryItem.
 * Demonstrates another unique subclass extending the common base.
 */
class Journal extends LibraryItem {
    private int issueNumber;

    public Journal(String title, String itemId, int issueNumber) {
        super(title, itemId);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public String getDetails() {
        return "Journal: " + getTitle() + " (Issue #" + issueNumber + ")";
    }
}
