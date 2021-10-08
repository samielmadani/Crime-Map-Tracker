package seng202.group7.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * A class used to serialize objects and store them in a file, and to deserialize them back into objects
 * @author Shaylin Simadari
 */
public final class Serializer {
    /**
     * Serializes a FilterConditions object into a file
     * @param file The file to write the FilterConditions object to
     */
    public static void serialize(File file, FilterConditions object){
        try (FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut)) {
            outputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes a FilterConditions object from a file
     * @param file The file from which to get the FilterConditions object
     */
    public static FilterConditions deserialize(File file){
        FilterConditions typedObj = null;
        try (FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fileIn)) {
            Object object = inputStream.readObject();

            typedObj = (FilterConditions) object;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return typedObj;
    }
}
