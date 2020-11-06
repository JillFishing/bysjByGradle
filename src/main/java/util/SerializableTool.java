package util;
//201902104050 姜瑞临
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class SerializableTool {

    public static void storeXML(Object obj, OutputStream out)
    {
        XMLEncoder encoder = new XMLEncoder(out);
        encoder.writeObject(obj);
        encoder.flush();
        encoder.close();
    }


    public static Object loadXML(String file) throws IOException {
        ObjectInputStream objectInput =
                    new ObjectInputStream(
                            new FileInputStream(file));

            XMLDecoder decoder = new XMLDecoder(objectInput);
            Object obj = decoder.readObject();
            decoder.close();
        return obj;
    }
}
