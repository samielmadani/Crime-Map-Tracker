package seng202.group7.data;

import java.io.*;

public final class Serializer {
    /**
     * Serializes a FilterConditions object into a file
     * @param file The file to write the FilterConditions object to
     */
    public static <T extends Serializable> void serialize(File file, T object){
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(object);
            outputStream.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes a FilterConditions object from a file
     * @param file The file from which to get the FilterConditions object
     */
    public static <T extends Serializable> T deserialize(File file){
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fileIn);
            T object = (T) inputStream.readObject();
            inputStream.close();
            fileIn.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
