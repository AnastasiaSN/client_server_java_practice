package ua.edu.ukma.fido.entity;

import com.github.snksoft.crc.CRC;
import com.google.common.primitives.UnsignedLong;
import lombok.Data;

import java.nio.ByteBuffer;

@Data
public class Packet {
    public final static Byte bMagic = 0x13;

    Byte bSrc;
    UnsignedLong bPktId;
    Integer wLen;
    Message bMsq;

    Short wCrc16_1;
    Short wCrc16_2;

    public Packet(Byte bSrc, UnsignedLong bPktId, Message bMsq) {
        this.bSrc = bSrc;
        this.bPktId = bPktId;

        this.bMsq = bMsq;
        wLen = bMsq.getMessage().length();
    }

    public Packet(byte[] encodedPacket) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(encodedPacket);

        Byte expectedBMagic = buffer.get();
        System.out.println(bMagic);
        if (!expectedBMagic.equals(bMagic))
            throw new Exception("Unexpected bMagic");

        bSrc = buffer.get();
        bPktId = UnsignedLong.fromLongBits(buffer.getLong());
        wLen = buffer.getInt();

        wCrc16_1 = buffer.getShort();

        bMsq = new Message();
        bMsq.setCType(buffer.getInt());
        bMsq.setBUserId(buffer.getInt());
        byte[] messageBody = new byte[wLen];
        buffer.get(messageBody);
        bMsq.setMessage(new String(messageBody));
        bMsq.decode();

        wCrc16_2 = buffer.getShort();
    }

    public byte[] toPacket() {
        Message message = getBMsq();

        message.encode();

        Integer packetPartFirstLength = bMagic.BYTES + bSrc.BYTES + Long.BYTES + wLen.BYTES;
        byte[] packetPartFirst = ByteBuffer.allocate(packetPartFirstLength)
                                    .put(bMagic)
                                    .put(bSrc)
                                    .putLong(bPktId.longValue())
                                    .putInt(wLen)
                                    .array();

        wCrc16_1 = (short) CRC.calculateCRC(CRC.Parameters.CRC16, packetPartFirst);

        Integer packetPartSecondLength = message.getMessageBytesLength();
        byte[] packetPartSecond = ByteBuffer.allocate(packetPartSecondLength)
                                    .put(message.toPacketPart())
                                    .array();

        wCrc16_2 = (short) CRC.calculateCRC(CRC.Parameters.CRC16, packetPartSecond);

        Integer packetLength = packetPartFirstLength + wCrc16_1.BYTES + packetPartSecondLength + wCrc16_2.BYTES;

        return ByteBuffer.allocate(packetLength).put(packetPartFirst).putShort(wCrc16_1).put(packetPartSecond).putShort(wCrc16_2).array();
    }
}
