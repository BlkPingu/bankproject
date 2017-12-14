package verarbeitung;

import java.io.*;

public class Serializer {

    public static byte[] serialize(Bank bank) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(bank);
            }
            return b.toByteArray();
        }
    }

    public static Object deserialize(File file) throws IOException, ClassNotFoundException {
        try(FileInputStream fis = new FileInputStream(file)){
            try(ObjectInputStream o = new ObjectInputStream(fis)){
                return o.readObject();
            }
        }
    }

}
