package edu.ufl.cise.cnt5106c.cnt5106c.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

/**
 *
 * @author Giacomo Benincasa    (giacomo@cise.ufl.edu)
 */
public class Message {
    private static final int MAX_LENGTH =  (int) Math.pow(2, 4 * 8);
    private int _length;
    private Type _type;
    private byte[] _payload;

    Message (Type type) throws Exception {
        this (1, type, null);
    }

    Message (int length, Type type, byte[] payload) throws Exception {
        if (length > MAX_LENGTH) {
            throw new Exception ("Messages of length greate than " + MAX_LENGTH);
        }
        
        _type = type;
    }

    private void writeObject(ObjectOutputStream oos)
        throws IOException {

        oos.write(_length);
        oos.write(_type.getValue());
        oos.write(_payload, 0, _payload.length);
    }

    private void readObject(ObjectInputStream ois)
        throws ClassNotFoundException, IOException {

        _length = ois.readInt();
        _type = Type.valueOf(ois.readByte());
        if (ois.read(_payload, 0, _length) < _length) {
            throw new IOException("payload bytes read are less than " + _length);
        }
    }
}