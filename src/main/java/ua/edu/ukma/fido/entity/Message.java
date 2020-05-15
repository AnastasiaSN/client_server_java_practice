package ua.edu.ukma.fido.entity;

import lombok.Data;
import ua.edu.ukma.fido.classes.Cipher;

import java.nio.ByteBuffer;

@Data
public class Message {
    Integer cType;
    Integer bUserId;
    String message;

    public static final int BYTES_WITHOUT_MESSAGE = Integer.BYTES + Integer.BYTES;

    public Message() { }

    public Message(Integer cType, Integer bUserId, String message) {
        this.cType = cType;
        this.bUserId = bUserId;
        this.message = message;
    }

    public byte[] toPacketPart() {
        return ByteBuffer.allocate(getMessageBytesLength())
                .putInt(cType)
                .putInt(bUserId)
                .put(message.getBytes()).array();
    }

    public int getMessageBytesLength() {
        return BYTES_WITHOUT_MESSAGE + getMessageBytes();
    }

    public Integer getMessageBytes() {
        return message.length();
    }

    public void encode() {
        message = Cipher.encode(message);
    }

    public void decode() {
        message = Cipher.decode(message);
    }
}
