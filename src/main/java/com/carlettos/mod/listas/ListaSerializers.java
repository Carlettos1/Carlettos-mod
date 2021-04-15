package com.carlettos.mod.listas;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.math.vector.Vector3f;

public class ListaSerializers {
	public static final IDataSerializer<Vector3f> VECTOR3F = new IDataSerializer<Vector3f>() {

		@Override
		public void write(PacketBuffer buf, Vector3f value) {
			buf.writeFloat(value.getX());
			buf.writeFloat(value.getY());
			buf.writeFloat(value.getZ());
		}

		@Override
		public Vector3f read(PacketBuffer buf) {
			float x = buf.readFloat();
			float y = buf.readFloat();
			float z = buf.readFloat();
			return new Vector3f(x, y, z);
		}

		@Override
		public Vector3f copyValue(Vector3f value) {
			return new Vector3f(value.getX(), value.getY(), value.getZ());
		}
	};
	
	static {
		DataSerializers.registerSerializer(VECTOR3F);
	}
}
