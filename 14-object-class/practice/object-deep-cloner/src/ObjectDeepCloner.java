/**
 * Starter template for exploring object cloning.
 */
public class ObjectDeepCloner {
    // Wrapper class
}

/**
 * Represents an Address.
 */
class Address implements Cloneable {
    private String street;
    private String city;

    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO: Implement clone by calling super.clone()
        return null;
    }
}

/**
 * Represents a User which references a mutable Address object and a String[] array.
 */
class User implements Cloneable {
    private String name;
    private Address address;
    private String[] skills;

    public User(String name, Address address, String[] skills) {
        this.name = name;
        this.address = address;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    /**
     * Performs a deep clone of the User object.
     * 
     * Requirements:
     * - Call super.clone() to get the shallow cloned object.
     * - Deep clone the mutable `address` field.
     * - Deep clone the mutable `skills` array.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO: Implement deep cloning
        return null;
    }
}
