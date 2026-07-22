package no14_object_class.practice.object_deep_cloner;

/**
 * Reference solution for ObjectDeepClonerSolution.
 * 
 * Deep cloning rules:
 * 1. Implement `Cloneable` interface.
 * 2. Override `clone()` and change access visibility from protected to public.
 * 3. First call `super.clone()` to allocate the correct subclass type instance.
 * 4. Deep clone any mutable reference fields (like arrays, collections, or custom class instances)
 *    so the cloned object does not share reference state with the original.
 */
public class ObjectDeepClonerSolution {
    // Wrapper class
}

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
        // Address has only String primitives/immutables, so a shallow super.clone() is sufficient
        return super.clone();
    }
}

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        // Step 1: Obtain shallow clone
        User clonedUser = (User) super.clone();
        
        // Step 2: Deep clone the Address object (if present)
        if (this.address != null) {
            clonedUser.address = (Address) this.address.clone();
        }
        
        // Step 3: Deep clone the String array (if present)
        if (this.skills != null) {
            clonedUser.skills = this.skills.clone();
        }
        
        return clonedUser;
    }
}
