package org.mdev.revolution.communication.packets.outgoing.moderation;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class CfhChatlogComposer extends ServerPacket {
    public CfhChatlogComposer() {
        super(ServerPacketHeader.CfhChatlogComposer);
        super.writeInt(6);
        super.writeString("sex_and_pii");
        super.writeInt(8);
        super.writeString("sexual_webcam_images");
        super.writeInt(1);
        super.writeString("mods");
        super.writeString("sexual_webcam_images_auto");
        super.writeInt(2);
        super.writeString("mods");
        super.writeString("explicit_sexual_talk");
        super.writeInt(3);
        super.writeString("mods");
        super.writeString("cybersex");
        super.writeInt(4);
        super.writeString("mods");
        super.writeString("cybersex_auto");
        super.writeInt(5);
        super.writeString("mods");
        super.writeString("meet_some");
        super.writeInt(6);
        super.writeString("mods");
        super.writeString("meet_irl");
        super.writeInt(7);
        super.writeString("mods");
        super.writeString("email_or_phone");
        super.writeInt(8);
        super.writeString("mods");
        super.writeString("scamming");
        super.writeInt(3);
        super.writeString("stealing");
        super.writeInt(9);
        super.writeString("mods");
        super.writeString("scamsites");
        super.writeInt(10);
        super.writeString("mods");
        super.writeString("selling_buying_accounts_or_furni");
        super.writeInt(11);
        super.writeString("mods");
        super.writeString("trolling");
        super.writeInt(11);
        super.writeString("hate_speech");
        super.writeInt(12);
        super.writeString("mods");
        super.writeString("violent_roleplay");
        super.writeInt(13);
        super.writeString("mods");
        super.writeString("swearing");
        super.writeInt(14);
        super.writeString("auto_reply");
        super.writeString("drugs");
        super.writeInt(15);
        super.writeString("mods");
        super.writeString("gambling");
        super.writeInt(16);
        super.writeString("mods");
        super.writeString("self_threatening");
        super.writeInt(17);
        super.writeString("mods");
        super.writeString("mild_staff_impersonation");
        super.writeInt(18);
        super.writeString("mods");
        super.writeString("severe_staff_impersonation");
        super.writeInt(19);
        super.writeString("mods");
        super.writeString("habbo_name");
        super.writeInt(20);
        super.writeString("mods");
        super.writeString("minors_access");
        super.writeInt(21);
        super.writeString("mods");
        super.writeString("bullying");
        super.writeInt(22);
        super.writeString("guardians");
        super.writeString("interruption");
        super.writeInt(2);
        super.writeString("flooding");
        super.writeInt(23);
        super.writeString("mods");
        super.writeString("doors");
        super.writeInt(24);
        super.writeString("mods");
        super.writeString("room");
        super.writeInt(1);
        super.writeString("room_report");
        super.writeInt(25);
        super.writeString("mods");
        super.writeString("help");
        super.writeInt(2);
        super.writeString("help_habbo");
        super.writeInt(26);
        super.writeString("auto_reply");
        super.writeString("help_payments");
        super.writeInt(27);
        super.writeString("auto_reply");
    }
}
