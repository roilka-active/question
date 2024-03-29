package training.tools.javase.collection.entity;

import java.util.Objects;

public class Item implements Comparable<Item> {

    private String description;
    private int partNumber;

    public Item(String aDescription,int aPartNumber){
        description = aDescription;
        partNumber = aPartNumber;
    }


    public String getDescription() {
        return description;
    }

    public int getPartNumber() {
        return partNumber;
    }

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", partNumber=" + partNumber +
                '}';
    }

    public boolean equals(Object otherObject){
        if (this == otherObject){
            return true;
        }
        if (otherObject == null){
            return false;
        }
        if (getClass() != otherObject.getClass()){
            return false;
        }
        Item other =(Item) otherObject;
        return Objects.equals(this.description, other.description) && partNumber == other.partNumber;
    }

    public int hashCode(){
        return Objects.hash(description,partNumber);
    }
    @Override
    public int compareTo(Item other){
        return Integer.compare(partNumber, other.partNumber);
    }
}
