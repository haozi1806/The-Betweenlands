package thebetweenlands.common.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityDungeonDoorRunes extends TileEntity implements ITickable {

	public int top_code = 0, mid_code = 0, bottom_code = 0;
	public int top_state = 0, mid_state = 0, bottom_state = 0;
	public int top_state_prev = 0, mid_state_prev = 0, bottom_state_prev = 0;
	public int top_rotate = 0, mid_rotate = 0, bottom_rotate = 0;

	public TileEntityDungeonDoorRunes() {
		super();
	}

	@Override
	public void update() {
		if(top_state_prev != top_state) {
			top_rotate+=4;
			if (top_rotate > 90) {
				top_rotate = 0;
				top_state_prev = top_state;
			}
		}
		
		if(mid_state_prev != mid_state) {
			mid_rotate+=4;
			if (mid_rotate > 90) {
				mid_rotate = 0;
				mid_state_prev = mid_state;
			}
		}
		
		if(bottom_state_prev != bottom_state) {
			bottom_rotate+=4;
			if (bottom_rotate > 90) {
				bottom_rotate = 0;
				bottom_state_prev = bottom_state;
			}
		}
	}
	
	public void cycleTopState() {
		top_state_prev = top_state;
		top_state++;
		if(top_state > 7)
			top_state = 0;
	}
	
	public void cycleMidState() {
		mid_state_prev = mid_state;
		mid_state++;
		if(mid_state > 7)
			mid_state = 0;
	}

	public void cycleBottomState() {
		bottom_state_prev = bottom_state;
		bottom_state++;
		if(bottom_state > 7)
			bottom_state = 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		top_code = nbt.getInteger("top_code");
		mid_code = nbt.getInteger("mid_code");
		bottom_code = nbt.getInteger("bottom_code");
		top_state = nbt.getInteger("top_state");
		mid_state = nbt.getInteger("mid_state");
		bottom_state = nbt.getInteger("bottom_state");
		top_state_prev = nbt.getInteger("top_state_prev");
		mid_state_prev = nbt.getInteger("mid_state_prev");
		bottom_state_prev = nbt.getInteger("bottom_state_prev");

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("top_code", top_code);
		nbt.setInteger("mid_code", mid_code);
		nbt.setInteger("bottom_code", bottom_code);
		nbt.setInteger("top_state", top_state);
		nbt.setInteger("mid_state", mid_state);
		nbt.setInteger("bottom_state", bottom_state);
		nbt.setInteger("top_state_prev", top_state_prev);
		nbt.setInteger("mid_state_prev", mid_state_prev);
		nbt.setInteger("bottom_state_prev", bottom_state_prev);
		return nbt;
	}

	@Override
    public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();
        return writeToNBT(nbt);
    }

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new SPacketUpdateTileEntity(getPos(), 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
	}

}
