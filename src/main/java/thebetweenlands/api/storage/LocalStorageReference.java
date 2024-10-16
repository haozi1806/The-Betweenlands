package thebetweenlands.api.storage;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import javax.annotation.Nullable;

public record LocalStorageReference(ChunkPos pos, StorageID id, @Nullable LocalRegion region, @Nullable ILocalStorageHandle handle) {

	/**
	 * Creates a new local storage reference.
	 * @param pos
	 * @param id
	 */
	public LocalStorageReference(ChunkPos pos, StorageID id, @Nullable LocalRegion region) {
		this(pos, id, region, null);
	}

	/**
	 * Creates a new local storage reference for a handle.
	 * @param handle
	 * @param id
	 * @param region
	 */
	public LocalStorageReference(ILocalStorageHandle handle, StorageID id, @Nullable LocalRegion region) {
		this(new ChunkPos(0, 0), id, region, handle);
	}

	/**
	 * Reads the reference from the specified NBT
	 * @param tag
	 * @return
	 */
	public static LocalStorageReference readFromNBT(CompoundTag tag) {
		ChunkPos pos = new ChunkPos(tag.getInt("x"), tag.getInt("z"));
		LocalRegion region = null;
		if(tag.contains("region")) {
			region = LocalRegion.readFromNBT(tag.getCompound("region"));
		}
		return new LocalStorageReference(pos, StorageID.readFromNBT(tag), region);
	}

	/**
	 * Writes this reference to the specified NBT
	 * @param tag
	 * @return
	 */
	public CompoundTag writeToNBT(CompoundTag tag) {
		this.id.writeToNBT(tag);
		tag.putInt("x", this.pos().x);
		tag.putInt("z", this.pos().z);
		if(this.region != null) {
			tag.put("region", this.region.writeToNBT(new CompoundTag()));
		}
		return tag;
	}

	/**
	 * Returns the {@link ILocalStorageHandle} this reference belongs to,
	 * if this reference is from a handle
	 * @return
	 */
	@Nullable
	public ILocalStorageHandle getHandle() {
		return this.handle;
	}

	/**
	 * Returns the reference ID string
	 * @return
	 */
	public StorageID getID() {
		return this.id;
	}

	/**
	 * Returns the region
	 * @return
	 */
	@Nullable
	public LocalRegion getRegion() {
		return this.region;
	}

	/**
	 * Returns whether this reference is assigned to a region
	 * @return
	 */
	public boolean hasRegion() {
		return this.region != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.pos.hashCode();
		result = prime * result + (this.handle == null ? 0 : this.handle.hashCode());
		result = prime * result + this.id.hashCode();
		result = prime * result + (this.region == null ? 0 : this.region.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalStorageReference other = (LocalStorageReference) obj;
		if (!pos.equals(other.pos))
			return false;
		if (handle == null) {
			if (other.handle != null)
				return false;
		} else if (!handle.equals(other.handle))
			return false;
		if (!id.equals(other.id))
			return false;
		if (region == null) {
			return other.region == null;
		} else return region.equals(other.region);
	}
}
